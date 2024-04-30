package nsu.ponomareva.sport_web_1.controllers;

import jakarta.validation.Valid;
import nsu.ponomareva.sport_web_1.models.Role;
import nsu.ponomareva.sport_web_1.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import nsu.ponomareva.sport_web_1.exceptions.CustomException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "${url_frontend}")
public class RoleController {
    @Autowired
    private RoleService roleService;

    // Create a new role
    @PostMapping
    public Role createRole(@RequestBody @Valid Role role) {
        Role createdRole = roleService.createRole(role);
        if(createdRole == null){
            throw new CustomException("Роль с таким названием уже существует");
        }
        return createdRole;
    }

    // Get all roles
    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    // Get role by ID
    @GetMapping("/{id}")
    public Optional<Role> getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    // Update role by ID
    @PutMapping("/{id}")
    public Role updateRole(@PathVariable Long id, @RequestBody Role roleDetails) {
        return roleService.updateRole(id, roleDetails.getName());
    }

    // Delete all roles
    @DeleteMapping
    public String deleteAllRoles() {
        roleService.deleteAllRoles();
        return "All roles have been deleted successfully.";
    }

    // Delete role by ID
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }
}