/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Data.UserDB;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Jennifer
 */
public class User implements Serializable{
    
    private String login;
    private String pass;
    private char tipo;
    private String nif;
    private int maxProy;
    private String infoGeneral;
    
    public User(){
        login="";
        pass="";
        tipo=' ';
        nif="";
        infoGeneral="";
    }
    
    public User(String login, String pass, char tipo, String nif){
        this.login=login;
        this.pass=pass;
        this.tipo=tipo;
        this.nif=nif;
        this.infoGeneral="";
    }
    
    public User(String login, String pass, char tipo, String nif, String infoGeneral){
        this.login=login;
        this.pass=pass;
        this.tipo=tipo;
        this.nif=nif;
        this.infoGeneral=infoGeneral;
    }
    public User(String login, String pass, char tipo, String nif, String infoGeneral, int maxProy){
        this.login=login;
        this.pass=pass;
        this.tipo=tipo;
        this.nif=nif;
        this.infoGeneral=infoGeneral;
        this.maxProy = maxProy;
    }
    
    public String getLogin(){
        return login;
    }
    
    public String getPass(){
        return pass;
    }
    
    public char getTipo(){
        return tipo;
    }
    
    public String getNif(){
        return nif;
    }
     public void setNif(String nif){
        this.nif = nif;
    }
    
    public int getMaxProy(){
        return maxProy;
    }
    public void setMaxProy(int maxProy){
        this.maxProy= maxProy;
    }
    
    public String getInfoGeneral(){
        return infoGeneral;
    }
    
    public void setLogin(String login){
        this.login = login;
    }
    
    public void setPass(String pass){
        this.pass=pass;
    }
    
    public void setTipo(char tipo){
        this.tipo = tipo;
    }
    
    public void setInfoGeneral(String infoGeneral){
        this.infoGeneral=infoGeneral;
    }
    
    public static boolean exist(String login){
        return UserDB.exist(login);
    }
    
    public static void insert(User user){
        UserDB.insert(user);
    }
    
    public static void delete(User user){
        UserDB.delete(user);
    }
    public static void update(User user){
        UserDB.update(user);
    }
    public static ArrayList<String> getPosiblesJefes(){
        return UserDB.getPosiblesJefes();
    }
    
    public static String getJefe(String dni){
        return UserDB.getJefe(dni);
    }
}
