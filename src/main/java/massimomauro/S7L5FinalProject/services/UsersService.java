package massimomauro.S7L5FinalProject.services;

import massimomauro.S7L5FinalProject.entities.User;
import massimomauro.S7L5FinalProject.exceptions.NotFoundException;
import massimomauro.S7L5FinalProject.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.io.IOException;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;



    public Page<User> getUsers(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        return usersRepository.findAll(pageable);
    }

    public User findById(int id) throws NotFoundException {
        return usersRepository.findById(id).orElseThrow( ()  -> new NotFoundException(id));
    }

    public void findByIdAndDelete(int id) throws NotFoundException{
        User found = this.findById(id);
        usersRepository.delete(found);
    }

    public User findByIdAndUpdate(int id, User body) throws NotFoundException{
        User found = this.findById(id);
        found.setSurname(body.getSurname());
        found.setName(body.getName());
        return usersRepository.save(found);
    }

    public User findByEmail(String email){
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }

}
