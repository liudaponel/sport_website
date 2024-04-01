package nsu.ponomareva.sport_web_1.services;

import nsu.ponomareva.sport_web_1.models.Role;
import nsu.ponomareva.sport_web_1.models.User;
import nsu.ponomareva.sport_web_1.repository.RoleRepository;
import nsu.ponomareva.sport_web_1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Create a new user
    public User createUser(User user) {
        User userFromDB = userRepository.findUserByInfo(user.getFio(), user.getEmail(), user.getPhone_number());

        if (userFromDB != null) {
            return null;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName("Пользователь");
        user.setRole(role);
        return userRepository.save(user);
    }

    public boolean checkPassword(User user) {
        User userFromDB = userRepository.findUserByInfo(user.getFio(), user.getEmail(), user.getPhone_number(), passwordEncoder.encode(user.getPassword()));

        if(userFromDB != null){
            return true;
        }

        return false;
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
