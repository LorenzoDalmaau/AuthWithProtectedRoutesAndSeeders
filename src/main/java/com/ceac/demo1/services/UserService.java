package com.ceac.demo1.services;

import com.ceac.demo1.entities.UserModel;
import com.ceac.demo1.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

///  Avisar que esta no es la manera real de hacer JWT.

@Service
public class UserService {
    @Autowired
    IUserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    ///  Constructor del servicio de usuarios
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /// Obtener todos los usuarios de la BD
    public ArrayList<UserModel> getUsers () {
        return (ArrayList<UserModel>) userRepository.findAll();
    }

    /// Crear Usuario
    public UserModel saveUser(UserModel user){
        user.setPassword(encoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    /// Obtener Usuario
    public Optional<UserModel> getUserbyId(Long id){
        return userRepository.findById(id);
    }

    /// Actualizar Usuario
    public UserModel updateUserbyId (Long id, UserModel request){
        /// 1. Guardar usuario variable
        UserModel user = userRepository.findById(id).orElseThrow(); // Busca el usuario por ID o lanza la excepción si no existe.

        /// 2. Modificar usuario
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(encoder.encode(request.getPassword()));
        }

        return userRepository.save(user);
    }

    /// Eliminar Usuario
    public Boolean deletebyId(Long id){
        try{
            if(userRepository.existsById(id)) {
                userRepository.deleteById(id);
                return true;
            }
        }catch (Exception e){
            return false;
        }
        return null;
    }

    ///  Auth helpers
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<UserModel> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean matches(String raw, String hashed) {
        return encoder.matches(raw, hashed);
    }
}
