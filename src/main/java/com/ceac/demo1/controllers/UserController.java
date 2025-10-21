package com.ceac.demo1.controllers;

import com.ceac.demo1.entities.UserModel;
import com.ceac.demo1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

   /// Obtener todos los usuarios
    @GetMapping
    public List<UserModel> getUsers(){
        return this.userService.getUsers();
    }
    /// Crear usuario
    @PostMapping
    public UserModel saveUser(@RequestBody UserModel user){
        return this.userService.saveUser(user);
    }
    /// Obtener usuario
    @GetMapping(path = "/{id}")
    public Optional<UserModel> getUserbyId(@PathVariable Long id){
        return this.userService.getUserbyId(id);
    }
    /// Actualizar Usuario
    @PutMapping(path = "/{id}")
    public UserModel updateUserbyId(@PathVariable Long id, @RequestBody UserModel request){
        return  this.userService.updateUserbyId(id, request);
    }
    /// Eliminar Usuario
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteUserbyId(@PathVariable Long id){
        boolean ok =this.userService.deletebyId(id);

        if(ok){
            return ResponseEntity.ok("User with id" + id + "deleted");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with id " + id + " not found");
        }
    }

}
