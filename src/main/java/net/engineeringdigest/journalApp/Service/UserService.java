package net.engineeringdigest.journalApp.Service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Repository.JournalEntryRepository;
import net.engineeringdigest.journalApp.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //**
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //**
    public User createNewUser(User user){
        //To save new user's password in encoded form

            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            log.debug("HAHA");
            log.info("HAHA");
//            log.warn("HAHA");
//            log.error("HAHA");
            return user;

    }

    public void createNewAdmin(User user){
        //To save new user's password in encoded form
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }
    public void saveUser(User user){

        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> getById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public void deleteByUserName(String username){
        userRepository.deleteByUserName(username);
    }

    public User findByUserName(String username){
        return userRepository.findByUserName(username);
    }

}
