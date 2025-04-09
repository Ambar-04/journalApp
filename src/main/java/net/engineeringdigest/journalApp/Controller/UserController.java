package net.engineeringdigest.journalApp.Controller;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Service.JournalEntryService;
import net.engineeringdigest.journalApp.Service.UserService;
import net.engineeringdigest.journalApp.Service.WeatherService;
import net.engineeringdigest.journalApp.api.Response.WeatherResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;


    //If I am logged in with any user that should come/stay logged in automatically when doing update
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        System.out.println("logged in user - "+ userName);
        User userInDb = userService.findByUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.createNewUser(userInDb);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUserByUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userService.deleteByUserName(userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/greeting/{myCity}")
    public ResponseEntity<String> greeting(@PathVariable String myCity){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather(myCity); // this will only give object of WeatherResponse, not temperature
        String greeting = "";
        if(weatherResponse != null){
            greeting = " ,the temperature today in " + myCity +" is "+ weatherResponse.getCurrent().getTemperature() +"°C";
        }

        return new ResponseEntity<>("Hi "+ authentication.getName() + greeting,HttpStatus.OK);
    }
}

