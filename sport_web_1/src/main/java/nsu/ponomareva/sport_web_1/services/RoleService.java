package nsu.ponomareva.sport_web_1.services;

import nsu.ponomareva.sport_web_1.models.Role;
import nsu.ponomareva.sport_web_1.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    // Create a new role
    public Role createRole(Role role) {
        Role roleFromDB = roleRepository.findByName(role.getName());

        if (roleFromDB != null) {
            return null;
        }

        return roleRepository.save(role);
    }

    // Get all roles
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Get role by ID
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    // Update role
    public Role updateRole(Long id, String name) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            Role existingUser = role.get();
            existingUser.setName(name);
            return roleRepository.save(existingUser);
        }
        return null;
    }

    // Delete all roles
    public void deleteAllRoles() {
        roleRepository.deleteAll();
    }

    // Delete role
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

}
