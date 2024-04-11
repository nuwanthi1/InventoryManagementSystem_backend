package com.Inventory.management.Inventory.Management.System.controller;
import com.Inventory.management.Inventory.Management.System.model.ERole;
import com.Inventory.management.Inventory.Management.System.model.User;
import com.Inventory.management.Inventory.Management.System.payload.request.UserCreationRequest;
import com.Inventory.management.Inventory.Management.System.payload.response.MessageResponse;
import com.Inventory.management.Inventory.Management.System.repository.UserRepo;
import com.Inventory.management.Inventory.Management.System.security.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

     @Autowired
     private UserRepo userRepo;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreationRequest request) {
        User createdUser = userService.createUser(request);
        return ResponseEntity.ok(createdUser);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getUser/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/updateUser/{username}")
    public ResponseEntity<MessageResponse> updateUser(@PathVariable String username, @RequestBody UserCreationRequest request) {
        userService.updateUser(username, request);
        return ResponseEntity.ok(new MessageResponse("User updated successfully"));
    }

    @GetMapping("/checkUsername/{username}")
    public ResponseEntity<Boolean> checkUsernameExists(@PathVariable String username) {
        boolean exists = userRepo.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/checkEmail/{email}")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        boolean exists = userRepo.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/deleteUser/{username}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok(new MessageResponse("User deleted successfully"));
    }

    @GetMapping("/allUsernames")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<String> getAllUsernames() {
        return userService.getAllUsernames();
    }
}
