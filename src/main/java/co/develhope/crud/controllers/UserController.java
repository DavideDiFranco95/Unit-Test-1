package co.develhope.crud.controllers;

import co.develhope.crud.entities.User;
import co.develhope.crud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/create")
    public @ResponseBody User createUser(@RequestBody User user){
        userRepository.saveAndFlush(user);
        return user;
    }
    @GetMapping("/{id}")
    public @ResponseBody User getUser(@PathVariable long id){
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }
    @GetMapping("")
    public @ResponseBody List<User> getAllUsers(){
        return userRepository.findAll();
    }
    @PutMapping("/{id}")
    public @ResponseBody User updateUser(@PathVariable long id, @RequestBody User user){
        User newUser = userRepository.findById(id).orElseThrow();
        newUser.setName(user.getName());
        newUser.setSurname(user.getSurname());
        newUser.setAge(user.getAge());
        newUser.setAddress(user.getAddress());

        return userRepository.saveAndFlush(newUser);

    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id){
        userRepository.deleteById(id);
    }
}
