package com.software2.apirest.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.software2.apirest.modelo.User;

@RestController
@RequestMapping("/api/users")
public class User_Controller {
    //Metodos que se van a implementar
    private List<User> users;

    public User_Controller(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            User[] usersArray= objectMapper.readValue(new  ClassPathResource("user.json").getFile(), User[].class);
            users =new ArrayList<>(Arrays.asList(usersArray));
        } catch (Exception e) {
            e.printStackTrace();
            users= new ArrayList<>();
        }
    }
    @GetMapping
    public List<User> getAllUsers(){
        return users;
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);

    }
      @PostMapping
    public User createUser(@RequestBody User user) {
        users.add(user);
        return user;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = getUserById(id);
        if (user != null) {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            return user;
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        users.removeIf(user -> user.getId().equals(id));
    }
    

}
