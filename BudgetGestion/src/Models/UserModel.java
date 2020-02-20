/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.Connection;
import Database.Database;
import Entities.User;
import Utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Salim
 */
public class UserModel {
    
    static final String TABLE_NAME = "users";
    //Champ de la table User
    static final String ID_FIELD = "id";
    static final String NOM_FIELD = "nom";
    static final String PRENOM_FIELD = "prenom";
    static final String MAIL_FIELD = "email";
    static final String PASSWORD_FIELD = "password";
    static final String CREATE_AT_FIELD = "created_at";
    
    private Connection connexion;

    public UserModel() {
    }
    
    
    /*
    **  Ajout utilisateur
    **
    ** @param user Utilisateur à ajouter 
    ** Retourne 1 si il a été ajoué
    ** Sinon 0
    */
    
    public int addUser(User user){
        int response = 0;
        
        this.connexion =  Database.getInstance().getConnection();
    
        String query = "INSERT INTO "+ TABLE_NAME + " (" + NOM_FIELD +","+ PRENOM_FIELD +"," + MAIL_FIELD + "," + PASSWORD_FIELD +
                "," + CREATE_AT_FIELD + ") VALUES" +
                "('" + user.getNom() + "','" + user.getPrenom() + "','" + user.getEmail() +"','" + user.getPassword() +"',('" + Utils.getStringFormatDate(user.getCreateAt()) + "'));";
        System.out.println(query);
        try {
            Statement statement = this.connexion.createStatement();
            response = statement.executeUpdate(query);
            System.out.println("Ajout réussi");
            
            statement.close();
            this.connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return response;
    }
    
    
    /*
    **  Récupérer tous les utilisateurs
    **
    ** Retourne une liste d'utilisateur
    */
    
    public List<User> getAllUsers(){
        List<User> usersList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        int id;
        String nom, prenom, mail, date,password;
        User user = new User();
        
        this.connexion =  Database.getInstance().getConnection();
    
        try {
            Statement statement = this.connexion.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {                
                id = resultSet.getInt(ID_FIELD);
                nom = resultSet.getString(NOM_FIELD);
                prenom = resultSet.getString(PRENOM_FIELD);
                mail = resultSet.getString(MAIL_FIELD);
                date = resultSet.getString(CREATE_AT_FIELD);
                password = resultSet.getString(PASSWORD_FIELD);
                user.setId(id);
                user.setNom(nom);
                user.setPrenom(prenom);
                user.setEmail(mail);
                user.setPassword(password);
                user.setCreateAt(Utils.getDateFromString(date));
                
                usersList.add(user);
            }
            
            statement.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return usersList;
    }
    
    
    /*
    **  Get récupérer un utilisateur selon son ID
    **
    ** @param id de l'utilisateur à récupérer
    ** Retourne l'utilisateur si il a été trouvé
    ** Sinon null
    */
    
    public User getUserById(int id){
        String query = "SELECT * FROM " + TABLE_NAME +" WHERE " + ID_FIELD +" = " +id;
        String nom, prenom, mail, date;
        User user = new User();
        
        this.connexion =  Database.getInstance().getConnection();
    
        try {
            Statement statement = this.connexion.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {                
                id = resultSet.getInt(ID_FIELD);
                nom = resultSet.getString(NOM_FIELD);
                prenom = resultSet.getString(PRENOM_FIELD);
                mail = resultSet.getString(MAIL_FIELD);
                date = resultSet.getString(CREATE_AT_FIELD);
                
                user.setId(id);
                user.setNom(nom);
                user.setPrenom(prenom);
                user.setEmail(mail);
                user.setCreateAt(Utils.getDateFromString(date));
                
            }
            
            statement.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public User getUserbyNamePassword(String name, String mdp){
        String query = "SELECT * FROM " + TABLE_NAME +" WHERE " + NOM_FIELD +" = '" +name+"' AND " + PASSWORD_FIELD+ " = '" +mdp+"'";
        String nom, prenom, mail, date, pass;
        int id;
        User user = new User();
        
        this.connexion =  Database.getInstance().getConnection();
    
        try {
            Statement statement = this.connexion.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {                
                id = resultSet.getInt(ID_FIELD);
                nom = resultSet.getString(NOM_FIELD);
                prenom = resultSet.getString(PRENOM_FIELD);
                mail = resultSet.getString(MAIL_FIELD);
                date = resultSet.getString(CREATE_AT_FIELD);
                pass = resultSet.getString(PASSWORD_FIELD);
                user.setId(id);
                user.setNom(nom);
                user.setPrenom(prenom);
                user.setEmail(mail);
                user.setPassword(pass);
                user.setCreateAt(Utils.getDateFromString(date));
                
            }
            
            statement.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    
    
    /*
    **  Suppression d'un user
    **
    ** @param id de l'opération à supprimer
    ** Retourne 1 si la modification a été effectuée
    ** Sinon 0
    */
    
    public int deleteUser(int id) {
        int response = 0;

        String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_FIELD + "=" + id;
        this.connexion = Database.getInstance().getConnection();

        try {
            Statement statement = this.connexion.createStatement();
            response = statement.executeUpdate(deleteQuery);
            System.out.println("Suppression réussie");

            statement.close();
            this.connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return response;
    }
    
    
    /*
    **  Modification d'un user
    **
    ** @param id de l'opération à modifier
    ** @param newUserles nouvelles valeurs à affecter
    ** Retourne 1 si la modification a été effectuée
    ** Sinon 0
    */
    
    public int updateUser(int oldId, User newUser) {
        int response = 0;
        
        this.connexion =  Database.getInstance().getConnection();
    
        String query = "UPDATE "+ TABLE_NAME + 
                " SET " + NOM_FIELD + "='" + newUser.getNom() +"', "
                + PRENOM_FIELD + "='" + newUser.getPrenom() +"', "
                + MAIL_FIELD + "='" + newUser.getEmail() +"', "
                + PASSWORD_FIELD + "='" + newUser.getPassword() +"', "
                + CREATE_AT_FIELD + "=('" + Utils.getStringFormatDate(newUser.getCreateAt()) +"') "
                + "WHERE " + ID_FIELD + "=" + oldId;
        System.out.println(query);
        try {
            Statement statement = this.connexion.createStatement();
            response = statement.executeUpdate(query);
            System.out.println("Modification réussie");
            
            statement.close();
            this.connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return response;
    }
    
    
    public int maxID() throws SQLException
    {
    	String query = "SELECT max(id) FROM users ";
    	Statement statement = this.connexion.createStatement();
        int response = statement.executeUpdate(query); 
    	return response;
    }
}
