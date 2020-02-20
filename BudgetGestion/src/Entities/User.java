/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author Salim
 */
public class User {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private LocalDateTime createAt;

    public User() {
    }

    public User(String nom, String prenom, String email, String password, LocalDateTime createAt) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.createAt = createAt;
    }

    public User(int id, String nom, String prenom, String email, String password, LocalDateTime createAt) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.createAt = createAt;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
    
    
}
