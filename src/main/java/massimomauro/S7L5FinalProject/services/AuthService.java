package massimomauro.S7L5FinalProject.services;

import massimomauro.S7L5FinalProject.entities.User;
import massimomauro.S7L5FinalProject.enums.Role;
import massimomauro.S7L5FinalProject.exceptions.BadRequestException;
import massimomauro.S7L5FinalProject.exceptions.UnauthorizedException;
import massimomauro.S7L5FinalProject.payloads.users.NewUserDTO;
import massimomauro.S7L5FinalProject.payloads.users.UserLoginDTO;
import massimomauro.S7L5FinalProject.repositories.UsersRepository;
import massimomauro.S7L5FinalProject.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthService {
    @Autowired
    private UsersService usersService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private UsersRepository usersRepository;

    public String authenticateUser(UserLoginDTO body){
        // 1. Verifichiamo che l'email dell'utente sia nel db
        User user = usersService.findByEmail(body.email());

        // 2. In caso affermativo, verifichiamo se la password corrisponde a quella trovata nel db
        if(bcrypt.matches(body.password(), user.getPassword())) {
            // 3. Se le credenziali sono OK --> Genero un JWT e lo restituisco
            return jwtTools.createToken(user);
        } else {
            // 4. Se le credenziali NON sono OK --> 401
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }

    public User registerUser(NewUserDTO body) throws IOException {

        // verifico se l'email è già utilizzata
        usersRepository.findByEmail(body.email()).ifPresent( user -> {
            throw new BadRequestException("L'email " + user.getEmail() + " è già utilizzata!");
        });

        User newUser = new User();

        newUser.setName(body.name());
        newUser.setSurname(body.surname());
        newUser.setPassword(bcrypt.encode(body.password())); // $2a$11$wQyZ17wrGu8AZeb2GCTcR.QOotbcVd9JwQnnCeqONWWP3wRi60tAO
        newUser.setEmail(body.email());
        newUser.setRole(body.role());
        User savedUser = usersRepository.save(newUser);
        return savedUser;
    }
}
