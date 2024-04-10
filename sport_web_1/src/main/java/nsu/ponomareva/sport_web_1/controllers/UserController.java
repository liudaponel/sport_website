package nsu.ponomareva.sport_web_1.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import nsu.ponomareva.sport_web_1.models.User;
import nsu.ponomareva.sport_web_1.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import nsu.ponomareva.sport_web_1.exceptions.CustomException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserService userService;

    // Create a new user
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public User createUser(@RequestBody @Valid User user) {
        User createdUser = userService.createUser(user);
        if(createdUser == null){
            throw new CustomException("Пользователь с такими данными уже существует");
        }
        return null;
    }

//    // LogIn user
//    @CrossOrigin(origins = "http://localhost:3000")
//    @PostMapping("/login")
//    public boolean LogInUser(@RequestBody @Valid User user) {
//        if(userService.checkPassword(user)){
//            throw new CustomException("Введены неверные данные или пароль");
//        }
//        return true;
//    }

    // Get all users
//    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<User> getAllUsers() {
        logger.info("getting");
        return userService.getAllUsers();
    }

    // Get user by ID
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Update user by ID
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails);
    }

    // Delete all users
    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping
    public String deleteAllUsers() {
        userService.deleteAllUsers();
        return "All users have been deleted successfully.";
    }

    // Delete user by ID
    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}