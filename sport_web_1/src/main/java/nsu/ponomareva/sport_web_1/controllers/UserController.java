package nsu.ponomareva.sport_web_1.controllers;

import nsu.ponomareva.sport_web_1.DTO.UserDTO;
import nsu.ponomareva.sport_web_1.models.Event;
import nsu.ponomareva.sport_web_1.models.User;
import nsu.ponomareva.sport_web_1.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import nsu.ponomareva.sport_web_1.exceptions.CustomException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "${url_frontend}")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserService userService;

    // Create a new user
    @PostMapping
    public User createUser(@RequestBody UserDTO user) {
        User createdUser = userService.createUser(user);
        if(createdUser == null){
            throw new CustomException("Пользователь с такими данными уже существует");
        }
        return null;
    }

    // Get all users
    @GetMapping
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "6") int size) {
        return userService.getAllUsers(page, size);
    }

    // Get user by ID
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Update user by ID
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserDTO userDetails) {
        return userService.updateUser(id, userDetails);
    }

    @PostMapping("/make_admin/{id}")
    public void makeAdmin(@PathVariable Long id){
        userService.makeAdmin(id);
    }

    // Delete all users
    @DeleteMapping
    public String deleteAllUsers() {
        userService.deleteAllUsers();
        return "All users have been deleted successfully.";
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{user_id}/events")
    public List<Event> getEvents(@PathVariable Long user_id){
        return userService.getEvents(user_id);
    }

    @PostMapping("/filters")
    public Page<User> getWithFilters(@RequestBody UserDTO request,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "6") int size){
        return userService.getWithFilters(request, page, size);
    }
}