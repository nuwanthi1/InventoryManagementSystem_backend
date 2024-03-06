package com.Inventory.management.Inventory.Management.System.controller;



import com.Inventory.management.Inventory.Management.System.model.User;
import com.Inventory.management.Inventory.Management.System.repository.UserRepo;
import com.Inventory.management.Inventory.Management.System.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    UserRepo userRepository;

   /* @GetMapping("/user-profile")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<User> getUserProfile(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();


        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    } */
}
  /*  @GetMapping("/homepage")
    public String allAccess() {
        return "This is Home Page.";
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
    public String userAccess() {
        return "This is DashBoard Page.";
    }

    @GetMapping("/manage-page")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "This is Manage Page.";
    }
*/

