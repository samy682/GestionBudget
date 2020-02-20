/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionbudget;

import Entities.Operation;
import Entities.User;
import Models.OperationModel;
import Models.UserModel;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Salim
 */
public class GestionBudget {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        UserModel UserModele = new UserModel();
        List<User> listUser = new ArrayList<>();
        
        listUser = UserModele.getAllUsers();
        for(int i = 0; i<listUser.size();i++){
            System.out.println("nom : "+ listUser.get(i).getNom());
        }
        
        
    }
    /*Cette fonction ne marchera pas forcément si vous l'exécutez lol!
    ** Juste un aperçu de comment utiliser les fonctions
    ** Plus de détails pour les return value dans les classes Models
    ** Même principe avec User
    */
    private void test(){
        
        List<Operation> listOperations = new ArrayList<>();
        OperationModel operationModel = new OperationModel();
        
        Operation operation;
        Operation operation1 = new Operation("Lollol", "hey", 12.5, LocalDateTime.now(), 2);
        Operation operation2 = new Operation("Lol", "hey", 12.5, LocalDateTime.now(), 2);
        
        //Ajout
        operationModel.addOperation(operation1);
        
        //Récupérer la liste
        listOperations = operationModel.getAllOperations();
        
        //Récupérer une operation selon ID
        operation = operationModel.getOperationById(1);
        
        //Modifier (id doit exister dans la bdd)
        operationModel.updateOperation(2, operation2);
        //Suppression
        operationModel.deleteOperation(1);
    }
}
