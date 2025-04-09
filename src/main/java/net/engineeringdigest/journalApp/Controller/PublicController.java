package net.engineeringdigest.journalApp.Controller;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;
    @GetMapping("/health-check")
    public String healthCheck(){
        return "Ok";
    }

    @PostMapping("/create-user")
    public ResponseEntity<User> createNewUser(@RequestBody User user){
        return new ResponseEntity<>(userService.createNewUser(user), HttpStatus.OK);
    }
}
