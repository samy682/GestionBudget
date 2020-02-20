/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Salim
 * 
 * Classe d'outils pour la conversion des Date
 */
public final class Utils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
    
    /**
     ** Permet de convertir une date de type LocalDateTime
     *  en String
     * @Param Date
     */
    
    public static String getStringFormatDate(LocalDateTime date){
        return date.format(formatter);
    }
    
    /**
     ** Permet de convertir un String en type LocalDateTime
     *  
     * @Param Date de type String
     */
    
    public static LocalDateTime getDateFromString(String date){
        LocalDateTime locaDate  = LocalDateTime.parse(date, formatter);
        return locaDate;
    }
}
