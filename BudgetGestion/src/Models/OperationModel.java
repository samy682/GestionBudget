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
import java.util.LinkedHashMap;
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
    
    
    
    public Operation getOperationByUser(int id){
        String query = "SELECT * FROM " + TABLE_NAME +" WHERE " + USER_ID_FIELD +" = " +id;
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
	
	
	
    public String[] getResultFromQueryCategories(String query) {
        ArrayList<String> categories = new ArrayList<>();   
        categories.add("Toute catégorie");
        this.connexion =  Database.getInstance().getConnection();
        
        try {
            Statement statement = this.connexion.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {                
                categories.add(resultSet.getString(CATEGORIE_FIELD));
            }
            
            statement.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        String str[] = new String[categories.size()]; 
        
        for (int j = 0; j < categories.size(); j++) { 
  
            // Assign each value to String array 
            str[j] = categories.get(j); 
        } 
        
        return str;
    }
    
    public String[] toutesLesCategorieDuUser(int iduser) {
        String query = "SELECT " + CATEGORIE_FIELD + " FROM " + TABLE_NAME +" WHERE " + USER_ID_FIELD + " = " + iduser + " GROUP BY " + CATEGORIE_FIELD;
        return getResultFromQueryCategories(query);
    }
    
    public String[] toutesLesCategorieRevenueDuUser(int iduser) {
        String query = "SELECT " + CATEGORIE_FIELD + " FROM " + TABLE_NAME +" WHERE " + USER_ID_FIELD + " = " + iduser + " AND montant > 0 GROUP BY " + CATEGORIE_FIELD;
        return getResultFromQueryCategories(query);
    }
    
    public String[] toutesLesCategorieDepenseDuUser(int iduser) {
        String query = "SELECT " + CATEGORIE_FIELD + " FROM " + TABLE_NAME +" WHERE " + USER_ID_FIELD + " = " + iduser + " AND montant < 0 GROUP BY " + CATEGORIE_FIELD;
        return getResultFromQueryCategories(query);
    }
    
    
    public String[] toutesLesAnneeDuUser(int iduser) {
        String query = "SELECT Year(date) as Annee FROM " + TABLE_NAME +" WHERE " + USER_ID_FIELD + " = " + iduser + " GROUP BY Annee ORDER BY Annee DESC";
        ArrayList<String> annee = new ArrayList<>();        
        this.connexion =  Database.getInstance().getConnection();
        
        try {
            Statement statement = this.connexion.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {                
                annee.add(resultSet.getString("Annee"));
            }
            
            statement.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        String str[] = new String[annee.size()]; 
        
        for (int j = 0; j < annee.size(); j++) { 
  
            // Assign each value to String array 
            str[j] = annee.get(j); 
        } 
        
        return str;
    }
    
    
    private LinkedHashMap<Integer,Double> getResultFromQueryMonth(String query) {
        LinkedHashMap<Integer,Double> moisSomme = new LinkedHashMap<>();        
        this.connexion =  Database.getInstance().getConnection();
        
        try {
            Statement statement = this.connexion.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {                
                moisSomme.put(resultSet.getInt("Mois"), resultSet.getDouble("Somme"));
            }
            
            statement.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }        
        return moisSomme;
    }
    
    public LinkedHashMap<Integer,Double> sommeMontantByMonthAndUser(String date, int iduser) {
        String query = "SELECT SUM(montant) as Somme, Month(date) as Mois FROM " + TABLE_NAME +" WHERE " + USER_ID_FIELD + " = " + iduser + " AND Year(date) = " + date + " GROUP BY Mois ORDER BY Mois ASC";
        return getResultFromQueryMonth(query);
    }
    
    public LinkedHashMap<Integer,Double> sommeMontantRevenueByMonthAndUser(String date, int iduser) {
        String query = "SELECT SUM(montant) as Somme, Month(date) as Mois FROM " + TABLE_NAME +" WHERE " + USER_ID_FIELD + " = " + iduser + " AND Year(date) = " + date + " AND montant > 0 GROUP BY Mois ORDER BY Mois ASC";
        return getResultFromQueryMonth(query);
    }
    
    public LinkedHashMap<Integer,Double> sommeMontantDepenseByMonthAndUser(String date, int iduser) {
        String query = "SELECT ABS(SUM(montant)) as Somme, Month(date) as Mois FROM " + TABLE_NAME +" WHERE " + USER_ID_FIELD + " = " + iduser + " AND Year(date) = " + date + " AND montant < 0 GROUP BY Mois ORDER BY Mois ASC";       
        return getResultFromQueryMonth(query);
    }
    
    public LinkedHashMap<Integer,Double> sommeMontantByMonthAndCategorieAndUser(String date, String cat, int iduser) {
        String query = "SELECT SUM(montant) as Somme, Month(date) as Mois FROM " + TABLE_NAME +" WHERE " + USER_ID_FIELD + " = " + iduser + " AND Year(date) = " + date + " AND categorie = \"" + cat + "\" GROUP BY Mois ORDER BY Mois ASC";
        return getResultFromQueryMonth(query);
    }
    
    public LinkedHashMap<Integer,Double> sommeMontantRevenueByMonthAndCategorieAndUser(String date, String cat, int iduser) {
        String query = "SELECT SUM(montant) as Somme, Month(date) as Mois FROM " + TABLE_NAME +" WHERE " + USER_ID_FIELD + " = " + iduser + " AND Year(date) = " + date + " AND categorie = \"" + cat + "\" AND montant > 0 GROUP BY Mois ORDER BY Mois ASC";
        return getResultFromQueryMonth(query);
    }
    
    public LinkedHashMap<Integer,Double> sommeMontantDepenseByMonthAndCategorieAndUser(String date, String cat, int iduser) {
        String query = "SELECT ABS(SUM(montant)) as Somme, Month(date) as Mois FROM " + TABLE_NAME +" WHERE " + USER_ID_FIELD + " = " + iduser + " AND Year(date) = " + date + " AND categorie = \"" + cat + "\" AND montant < 0 GROUP BY Mois ORDER BY Mois ASC";       
        return getResultFromQueryMonth(query);
    }
    
    
    
    private LinkedHashMap<Integer,Double> getResultFromQueryYear(String query){
        LinkedHashMap<Integer,Double> anneeSomme = new LinkedHashMap<>();        
        this.connexion =  Database.getInstance().getConnection();
        
        try {
            Statement statement = this.connexion.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {                
                anneeSomme.put(resultSet.getInt("Annee"), resultSet.getDouble("Somme"));
            }
            
            statement.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }        
        return anneeSomme;
    }
    
    public LinkedHashMap<Integer,Double> sommeMontantByYearAndUser( int iduser) {
        String query = "SELECT SUM(montant) as Somme, Year(date) as Annee FROM " + TABLE_NAME +" WHERE " + USER_ID_FIELD + " = " + iduser + " GROUP BY Annee ORDER BY Annee ASC";
        return getResultFromQueryYear(query);
    }
    
    public LinkedHashMap<Integer,Double> sommeMontantRevenueByYearAndUser( int iduser) {
        String query = "SELECT SUM(montant) as Somme, Year(date) as Annee FROM " + TABLE_NAME +" WHERE " + USER_ID_FIELD + " = " + iduser + " AND montant > 0 GROUP BY Annee ORDER BY Annee ASC";
        return getResultFromQueryYear(query);
    }
    
    public LinkedHashMap<Integer,Double> sommeMontantDepenseByYearAndUser( int iduser) {
        String query = "SELECT ABS(SUM(montant)) as Somme, Year(date) as Annee FROM " + TABLE_NAME +" WHERE " + USER_ID_FIELD + " = " + iduser + " AND montant < 0 GROUP BY Annee ORDER BY Annee ASC";
        return getResultFromQueryYear(query);
    }
    
    public LinkedHashMap<Integer,Double> sommeMontantByYearAndCategorieAndUser( String cat, int iduser) {
        String query = "SELECT SUM(montant) as Somme, Year(date) as Annee FROM " + TABLE_NAME +" WHERE " + USER_ID_FIELD + " = " + iduser + "AND categorie = \"" + cat + "\" GROUP BY Annee ORDER BY Annee ASC";
        return getResultFromQueryYear(query);
    }
    
    public LinkedHashMap<Integer,Double> sommeMontantRevenueByYearAndCategorieAndUser( String cat, int iduser) {
        String query = "SELECT SUM(montant) as Somme, Year(date) as Annee FROM " + TABLE_NAME +" WHERE " + USER_ID_FIELD + " = " + iduser + " AND categorie = \"" + cat + "\" AND montant > 0 GROUP BY Annee ORDER BY Annee ASC";
        return getResultFromQueryYear(query);
    }
    
    public LinkedHashMap<Integer,Double> sommeMontantDepenseByYearAndCategorieAndUser( String cat, int iduser) {
        String query = "SELECT ABS(SUM(montant)) as Somme, Year(date) as Annee FROM " + TABLE_NAME +" WHERE " + USER_ID_FIELD + " = " + iduser + " AND categorie = \"" + cat + "\" AND montant < 0 GROUP BY Annee ORDER BY Annee ASC";
        return getResultFromQueryYear(query);
    }



    private LinkedHashMap<Integer,Double> getResultFromQueryAverage(String query) {
        LinkedHashMap<Integer,Double> moisMoyenne = new LinkedHashMap<>();        
        this.connexion =  Database.getInstance().getConnection();
        
        try {
            Statement statement = this.connexion.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {                
                moisMoyenne.put(resultSet.getInt("Mois"), resultSet.getDouble("Moyenne"));
            }
            
            statement.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }        
        return moisMoyenne;
    }
    
    public LinkedHashMap<Integer,Double> moyenneMontantByUser( int iduser) {
        String query = "SELECT AVG(Somme) as Moyenne, Mois\n" +
        "FROM ( SELECT SUM(montant) as Somme, Month(date) as Mois, Year(date) as Annee\n" +
        "       FROM " + TABLE_NAME +"\n" +
        "       WHERE " + USER_ID_FIELD + " = " + iduser + "\n" +
        "       GROUP BY Mois, Annee\n" +
        "       ORDER BY Mois ASC) as inner_query\n" +
        "GROUP BY Mois ORDER BY Mois ASC";
        return getResultFromQueryAverage(query);
    }
    
    public LinkedHashMap<Integer,Double> moyenneMontantRevenueByUser( int iduser) {
        String query = "SELECT AVG(Somme) as Moyenne, Mois\n" +
        "FROM ( SELECT SUM(montant) as Somme, Month(date) as Mois, Year(date) as Annee\n" +
        "       FROM " + TABLE_NAME +"\n" +
        "       WHERE " + USER_ID_FIELD + " = " + iduser + " AND montant > 0\n" +
        "       GROUP BY Mois, Annee\n" +
        "       ORDER BY Mois ASC) as inner_query\n" +
        "GROUP BY Mois ORDER BY Mois ASC";
        return getResultFromQueryAverage(query);
    }
    
    public LinkedHashMap<Integer,Double> moyenneMontantDepenseByUser( int iduser) {
        String query = "SELECT ABS(AVG(Somme)) as Moyenne, Mois\n" +
        "FROM ( SELECT SUM(montant) as Somme, Month(date) as Mois, Year(date) as Annee\n" +
        "       FROM " + TABLE_NAME +"\n" +
        "       WHERE " + USER_ID_FIELD + " = " + iduser + " AND montant < 0\n" +
        "       GROUP BY Mois, Annee\n" +
        "       ORDER BY Mois ASC) as inner_query\n" +
        "GROUP BY Mois ORDER BY Mois ASC";
        return getResultFromQueryAverage(query);
    }
    
    public LinkedHashMap<Integer,Double> moyenneMontantByCategorieAndUser( String cat, int iduser) {
        String query = "SELECT AVG(Somme) as Moyenne, Mois\n" +
        "FROM ( SELECT SUM(montant) as Somme, Month(date) as Mois, Year(date) as Annee\n" +
        "       FROM " + TABLE_NAME +"\n" +
        "       WHERE " + USER_ID_FIELD + " = " + iduser + " AND categorie = \""+ cat +"\"\n" +
        "       GROUP BY Mois, Annee\n" +
        "       ORDER BY Mois ASC) as inner_query\n" +
        "GROUP BY Mois ORDER BY Mois ASC";
        return getResultFromQueryAverage(query);
    }
    
    public LinkedHashMap<Integer,Double> moyenneMontantRevenueByCategorieAndUser( String cat, int iduser) {
        String query = "SELECT AVG(Somme) as Moyenne, Mois\n" +
        "FROM ( SELECT SUM(montant) as Somme, Month(date) as Mois, Year(date) as Annee\n" +
        "       FROM " + TABLE_NAME +"\n" +
        "       WHERE " + USER_ID_FIELD + " = " + iduser + " AND montant > 0 AND categorie = \""+ cat +"\"\n" +
        "       GROUP BY Mois, Annee\n" +
        "       ORDER BY Mois ASC) as inner_query\n" +
        "GROUP BY Mois ORDER BY Mois ASC";
        return getResultFromQueryAverage(query);
    }
    
    public LinkedHashMap<Integer,Double> moyenneMontantDepenseByCategorieAndUser( String cat, int iduser) {
        String query = "SELECT ABS(AVG(Somme)) as Moyenne, Mois\n" +
        "FROM ( SELECT SUM(montant) as Somme, Month(date) as Mois, Year(date) as Annee\n" +
        "       FROM " + TABLE_NAME +"\n" +
        "       WHERE " + USER_ID_FIELD + " = " + iduser + " AND montant < 0 AND categorie = \""+ cat +"\"\n" +
        "       GROUP BY Mois, Annee\n" +
        "       ORDER BY Mois ASC) as inner_query\n" +
        "GROUP BY Mois ORDER BY Mois ASC";
        return getResultFromQueryAverage(query);
    }
    
    
    
    
    private LinkedHashMap<String,Double> getResultFromQueryPieCategories(String query) {
        LinkedHashMap<String,Double> moisSomme = new LinkedHashMap<>();        
        this.connexion =  Database.getInstance().getConnection();
        
        try {
            Statement statement = this.connexion.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {                
                moisSomme.put(resultSet.getString("categorie"), resultSet.getDouble("Somme"));
            }
            
            statement.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }        
        return moisSomme;
    }
    
    public LinkedHashMap<String,Double> distributionMontantRevenueByUser(int iduser) {
        String query = "SELECT SUM(montant) as Somme, categorie FROM " + TABLE_NAME +" WHERE " + USER_ID_FIELD + " = " + iduser + " AND  montant > 0 GROUP BY categorie ORDER BY Somme DESC";
        return getResultFromQueryPieCategories(query);
    }
    
    public LinkedHashMap<String,Double> distributionMontantDepenseByUser(int iduser) {
        String query = "SELECT ABS(SUM(montant)) as Somme, categorie FROM " + TABLE_NAME +" WHERE " + USER_ID_FIELD + " = " + iduser + " AND  montant < 0 GROUP BY categorie ORDER BY Somme DESC";
        return getResultFromQueryPieCategories(query);
    }
    
    public LinkedHashMap<String,Double> distributionMontantRevenueByYearAndUser(String date, int iduser) {
        String query = "SELECT SUM(montant) as Somme, categorie FROM " + TABLE_NAME +" WHERE " + USER_ID_FIELD + " = " + iduser + " AND  montant > 0 AND Year(date) = "+ date +" GROUP BY categorie ORDER BY Somme DESC";
        return getResultFromQueryPieCategories(query);
    }
    
    public LinkedHashMap<String,Double> distributionMontantDepenseByYearAndUser(String date, int iduser) {
        String query = "SELECT ABS(SUM(montant)) as Somme, categorie FROM " + TABLE_NAME +" WHERE " + USER_ID_FIELD + " = " + iduser + " AND  montant < 0 AND Year(date) = "+ date +" GROUP BY categorie ORDER BY Somme DESC";
        return getResultFromQueryPieCategories(query);
    }
	
	
	
    
    public ArrayList<Double> getAllYearByMonthByUser(int month,int iduser) {
        String query = "SELECT ABS(SUM(montant)) as Somme, Year(date) as Annee FROM " + TABLE_NAME +" WHERE " + USER_ID_FIELD + " = " + iduser + " AND Month(date) = " + month + " AND montant < 0 GROUP BY Annee ORDER BY Annee ASC";
        ArrayList<Double> moisSomme = new ArrayList<>();        
        this.connexion =  Database.getInstance().getConnection();
        
        try {
            Statement statement = this.connexion.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {                
                moisSomme.add(resultSet.getDouble("Somme"));
            }
            
            statement.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }        
        return moisSomme;
    }
    
    
}
