package com.ceac.demo1.repositories;

import com.ceac.demo1.entities.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserModel, Long>{
    Optional<UserModel> findByEmail(String email);
    boolean existsByEmail(String email);
}
