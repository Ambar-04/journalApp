package net.engineeringdigest.journalApp.Service;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Service //in @Service also is same as @Component
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);


        if(user != null) {
            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(username)
                    .password(user.getPassword())
                    //**
                    .roles(user.getRoles().toArray(new String[0]))//used to convert a List<String> (or roles) to an array of String[].
//  The toArray() method has two variants:
//  Object[] toArray();  // Returns an Object[], which is not type-safe
//  T[] toArray(T[] a);  // Returns a properly typed array //Using .toArray(new String[0]) ensures that the output is String[], avoiding the need for casting from Object[].
//  The method .toArray(new String[0]) allows Java to allocate an optimally sized array internally.
//  If array size provided (0 here) is smaller than the list size, Java creates new array internally with the correct size.
//  List is dynamic in size, Java does not know the final size beforehand when converting it to an array. So, if the provided array size (0) is smaller than the list size, Java creates a new array of the correct size internally.
                    .build();

            return userDetails;
        }

        throw new UsernameNotFoundException("User not found with name: "+ username);
    }
}
