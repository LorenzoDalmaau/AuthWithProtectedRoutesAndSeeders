package com.ceac.demo1.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table (name = "user")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false) // Indicamos que esta variable nunca deberá de estar vacía.
    private String firstName;

    @Column(nullable = false)// Indicamos que esta variable nunca deberá de estar vacía.
    private String lastName;

    @Column(nullable = false) // Indicamos que esta variable nunca deberá de estar vacía.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) /// <-- CLAVE: Esto permite a Spring Boot leer el JSON con la password pero no retorna la password.
    private String password;

    @Column(nullable = false)// Indicamos que esta variable nunca deberá de estar vacía.
    private String email;

    ///Getters and Setters

    ///  Get ID & Set ID
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    ///  Get firstName & Set firstName
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    ///  Get lastName & Set lastName
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    ///  Get firstName & Set firstName
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    ///  Get firstName & Set firstName
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
