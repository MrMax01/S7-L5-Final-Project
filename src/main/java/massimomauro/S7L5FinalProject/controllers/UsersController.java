package massimomauro.S7L5FinalProject.controllers;


/* -------------------------- USERS CRUD ------------------------------------

1. GET http://localhost:3001/users (+ query params opzionali)
2. POST http://localhost:3001/users (+ body)
3. GET http://localhost:3001/users/:id
4. PUT http://localhost:3001/users/:id (+ body)
5. DELETE http://localhost:3001/users/:id

*/

import massimomauro.S7L5FinalProject.entities.User;
import massimomauro.S7L5FinalProject.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    // 1. GET http://localhost:3001/users (+ query params opzionali)
    @GetMapping("")
    // @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public Page<User> getUser(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String orderBy){
        return usersService.getUsers(page, size, orderBy);
    }

    @GetMapping("/me")
    public UserDetails getProfile(@AuthenticationPrincipal UserDetails currentUser){
        return currentUser;
    };

    @PutMapping("/me")
    public UserDetails getProfile(@AuthenticationPrincipal User currentUser, @RequestBody User body){
        return usersService.findByIdAndUpdate(currentUser.getId(), body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void getProfile(@AuthenticationPrincipal User currentUser){
        usersService.findByIdAndDelete(currentUser.getId());
    };



    // 3. GET http://localhost:3001/users/:id
    @GetMapping("/{id}")
    public User findById(@PathVariable int id){
        return usersService.findById(id);
    }

    // 4. PUT http://localhost:3001/users/:id (+ body)
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EVENT_ORGANIZER')")
    public User findByIdAndUpdate(@PathVariable int id, @RequestBody User body){
        return usersService.findByIdAndUpdate(id, body);
    }

    // 5. DELETE http://localhost:3001/users/:id
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('EVENT_ORGANIZER')")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findByIdAndDelete(@PathVariable int id){
        usersService.findByIdAndDelete(id);
    }


}
