/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Database.Database;
import Entities.Operation;
import Utils.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Salim
 */
public class OperationModel {
   
    private static final String TABLE_NAME = "operations";
    
    private static final String DEPENSE_ID_FIELD = "id";
    private static final String CATEGORIE_FIELD = "categorie";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String MONTANT_FIELD = "montant";
    private static final String DATE_FIELD = "date";
    private static final String USER_ID_FIELD = "user_id";
    
    private Connection connexion;

    public OperationModel() {
    }
    
    /*
    **  Ajout d'une opération
    **
    ** @param Operation
    **  
    ** Retourne 1 si l'ajout a été effectué
    ** Sinon 0
    */
    
    public int addOperation(Operation operation){
        int response = 0;
        
        this.connexion =  Database.getInstance().getConnection();
    
        String query = "INSERT INTO "+ TABLE_NAME + "(" + CATEGORIE_FIELD +"," + DESCRIPTION_FIELD +"," + MONTANT_FIELD + ", " + DATE_FIELD +
                "," + USER_ID_FIELD + ") VALUES" +
                "('" + operation.getCategorie()+ "','" + operation.getDescription()+ "'," + operation.getMontant() +",('" + Utils.getStringFormatDate(operation.getDate()) + "')," + operation.getUser_id() +");";
    
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
    ** récupérer toutes les operations
    ** Retourne la liste de toutes les operations
    ** 
    ** 
    */
    
    public List<Operation> getAllOperations(){
        List<Operation> operationsList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        int id, id_user;
        String categorie, description, dateString;
        double montant;
        Operation operation = new Operation();
        
        this.connexion =  Database.getInstance().getConnection();
    
        try {
            Statement statement = this.connexion.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {                
                id = resultSet.getInt(DEPENSE_ID_FIELD);
                categorie = resultSet.getString(CATEGORIE_FIELD);
                description = resultSet.getString(DESCRIPTION_FIELD);
                montant = resultSet.getDouble(MONTANT_FIELD);
                id_user = resultSet.getInt(USER_ID_FIELD);
                dateString = resultSet.getString(DATE_FIELD);
                
                operation.setId(id);
                operation.setCategorie(categorie);
                operation.setDescription(description);
                operation.setMontant(montant);
                operation.setDate(Utils.getDateFromString(dateString));
                
                operationsList.add(operation);
            }
            
            statement.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return operationsList;
    }
    
    
    /*
    **  Récupérer une operation selon son id
    **
    ** @param id
    **  
    ** Retourne une opération si il existe
    ** Sinon retourne null
    */
    
    public Operation getOperationById(int id){
        String query = "SELECT * FROM " + TABLE_NAME +" WHERE " + DEPENSE_ID_FIELD +" = " +id;
        int id_user;
        String categorie, description, dateString;
        double montant;
        
        Operation operation = null;
        
        this.connexion =  Database.getInstance().getConnection();
    
        try {
            Statement statement = this.connexion.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            operation = new Operation();
            while (resultSet.next()) {                
                id = resultSet.getInt(DEPENSE_ID_FIELD);
                categorie = resultSet.getString(CATEGORIE_FIELD);
                description = resultSet.getString(DESCRIPTION_FIELD);
                montant = resultSet.getDouble(MONTANT_FIELD);
                id_user = resultSet.getInt(USER_ID_FIELD);
                dateString = resultSet.getString(DATE_FIELD);
                
                operation.setId(id);
                operation.setCategorie(categorie);
                operation.setDescription(description);
                operation.setMontant(montant);
                operation.setDate(Utils.getDateFromString(dateString));
            }
            
            statement.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return operation;
    }
    
    /*
    **  Suppression d'une operatiob
    **
    ** @param id de l'opération à supprimer
    **  
    ** Retourne 1 si la suppression a été effectuée
    ** Sinon 0
    */
    
    public int deleteOperation(int id) {
        int response = 0;

        String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + DEPENSE_ID_FIELD + "=" + id;
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
    **  Suppression d'une operatiob
    **
    ** @param id de l'opération à modifier
    ** @param newOperation les nouvelles valeurs à affecter
    ** Retourne 1 si la modification a été effectuée
    ** Sinon 0
    */
    
    public int updateOperation(int oldId, Operation newOperation) {
        int response = 0;
        
        this.connexion =  Database.getInstance().getConnection();
    
        String query = "UPDATE "+ TABLE_NAME + 
                " SET " + CATEGORIE_FIELD + "='" + newOperation.getCategorie()+"', "
                + DESCRIPTION_FIELD + "='" + newOperation.getDescription()+"', "
                + MONTANT_FIELD + "='" + newOperation.getMontant()+"', "
                + DATE_FIELD + "=('" + Utils.getStringFormatDate(newOperation.getDate())+"'), "
                + USER_ID_FIELD + "=" + newOperation.getUser_id()+" "
                + "WHERE " + DEPENSE_ID_FIELD + "=" + oldId;
        
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
}
