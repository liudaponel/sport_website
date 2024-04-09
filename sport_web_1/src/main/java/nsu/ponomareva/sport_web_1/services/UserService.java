package nsu.ponomareva.sport_web_1.services;

import nsu.ponomareva.sport_web_1.auth.AuthenticationService;
import nsu.ponomareva.sport_web_1.models.Role;
import nsu.ponomareva.sport_web_1.models.User;
import nsu.ponomareva.sport_web_1.repository.RoleRepository;
import nsu.ponomareva.sport_web_1.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    // Create a new user
    public User createUser(User user) {
        var userFromDB = userRepository.findByEmail(user.getEmail());
        if (userFromDB.isEmpty()) {
            return null;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName("Пользователь");
        user.setRole(role);
        return userRepository.save(user);
    }

    public boolean isPasswordCorrect(String email, String password) {
        var userFromDB = userRepository.findByEmail(email);
        if(userFromDB.isEmpty()){
            return false;
        }
        return passwordEncoder.matches(password, userFromDB.get().getPassword());
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Update user
    public User updateUser(Long id, User userDetails) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setFio(userDetails.getFio());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setPhone_number(userDetails.getPhone_number());
            existingUser.setPassword(userDetails.getPassword());
            return userRepository.save(existingUser);
        }
        return null;
    }

    // Delete all users
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    // Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
