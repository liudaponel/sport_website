package nsu.ponomareva.sport_web_1.services;

import nsu.ponomareva.sport_web_1.DTO.EventDTO;
import nsu.ponomareva.sport_web_1.DTO.UserDTO;
import nsu.ponomareva.sport_web_1.exceptions.CustomException;
import nsu.ponomareva.sport_web_1.models.Event;
import nsu.ponomareva.sport_web_1.models.User;
import nsu.ponomareva.sport_web_1.models.UserEvent;
import nsu.ponomareva.sport_web_1.repository.EventRepository;
import nsu.ponomareva.sport_web_1.repository.RoleRepository;
import nsu.ponomareva.sport_web_1.repository.UserRepository;
import nsu.ponomareva.sport_web_1.specifications.EventSpecification;
import nsu.ponomareva.sport_web_1.specifications.UserSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    // Create a new user
    public User createUser(UserDTO user) {
        var userFromDB = userRepository.findByEmail(user.getEmail());
        if (userFromDB.isPresent()) {
            throw new CustomException("Пользователь с такой почтой уже существует");
        }

        User newUser = new User();
        newUser.setFio(user.getFio());
        newUser.setEmail(user.getEmail());
        newUser.setPhone_number(user.getPhone_number());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(roleRepository.findById(user.getRole()).orElseThrow());
        return userRepository.save(newUser);
    }

    public boolean isPasswordCorrect(String email, String password) {
        var userFromDB = userRepository.findByEmail(email);
        if(userFromDB.isEmpty()){
            return false;
        }
        return passwordEncoder.matches(password, userFromDB.get().getPassword());
    }

    // Get all users
    public Page<User> getAllUsers(int page, int size) {
        Page<User> users = userRepository.findAll(PageRequest.of(page, size));
        return users;
    }

    // Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Update user
    public User updateUser(Long id, UserDTO userDetails) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setFio(userDetails.getFio());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setPhone_number(userDetails.getPhone_number());
            existingUser.setPassword(userDetails.getPassword());
            existingUser.setRole(roleRepository.findById(userDetails.getRole()).orElseThrow());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public void makeAdmin(Long id){
        User user = userRepository.findById(id).orElseThrow();
        user.setRole(roleRepository.findByName("Администратор"));
        userRepository.save(user);
    }

    // Delete all users
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    // Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<Event> getEvents(Long id){
        User user = userRepository.findById(id).orElseThrow();
        Set<UserEvent> ue = user.getRegistrations();
        List<Event> events = new ArrayList<>();
        for (UserEvent reg: ue) {
            events.add(reg.getEvent_id());
        }
        return events;
    }

    public Page<User> getWithFilters(UserDTO request, int page, int size) {
        Specification<User> spec = Specification.where(null);

        if (request.getFio() != null) {
            spec = spec.and(UserSpecification.hasFioLike(request.getFio()));
        }
        if (request.getEmail() != null) {
            spec = spec.and(UserSpecification.hasEmailLike(request.getEmail()));
        }
        if (request.getPhone_number() != null) {
            spec = spec.and(UserSpecification.hasPhoneLike(request.getPhone_number()));
        }
        if (request.getRole() != null) {
            spec = spec.and(UserSpecification.hasRole(request.getRole()));
        }

        return userRepository.findAll(spec, PageRequest.of(page, size));
    }
}
