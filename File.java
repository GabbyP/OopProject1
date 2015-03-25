/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;
import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author justinguerra
 */
public class File {
    
    private String          userID; 
    private String          balance;
    private String          str;
    private String          entireFile;
    private String[]        rentedDVD;
    private String[]        rentedBluRay;
    private String[]        purchased;
    private List<String>    allrentedDVD;
    private List<String>    allrentedBluRay;
    private List<String>    allUsers; 
    private int             entireFileLength;
    
    public File(){
        readEntireFile(); 
        parseDVDTitles();
        parseBluRayTitles();
        parseUsers(); 
    }
    
    private Boolean searchFile(String string){
        Boolean found = true; 
        
        try {
         BufferedReader in = new BufferedReader
         (new FileReader("./transactionFile.txt"));
         
         do{
             
            str = in.readLine();
            
         }while (!str.contains(string) && !str.contains("eof"));   
         
         }
         catch (IOException e) {
             e.getMessage(); 
         }
        
        if (str.contains(string))
             return found;
         else
             return !found; 
    }
    
    private void readEntireFile(){ 
        try {
        entireFile = new String(Files.readAllBytes(Paths.get("./transactionFile.txt")));
        entireFileLength = entireFile.length(); 
        }catch(IOException e){
            e.getMessage();
        }
    }
    
    private void parseUsers(){
        
        allUsers = new ArrayList<>();
        
        for (int i = 0; i < entireFileLength; i++)
        {
            if(entireFile.charAt(i) == '#'){
                i = putInUserList(i); 
            }
        }
    }
    
    private int putInUserList(int index){
        int         secondIndex;
        String      temp;
        
        temp = entireFile.substring(index);
        
        secondIndex = temp.indexOf(";"); 
        
        temp = temp.substring(0, secondIndex + 1);
        
        allUsers.add(temp);
        
        return index + secondIndex;  
    }
    
    public List<String> getUserList(){
        return allUsers; 
    }
    
    private void parseDVDTitles(){
        
        allrentedDVD = new ArrayList<>();
        
        for (int i = 0; i < entireFileLength; i++)
        {
            if(entireFile.charAt(i) == '/'){
                i = putInDVDList(i); 
            }
        }
    }
    
    private int putInDVDList(int index){
        int         secondIndex;
        int         end = 0; 
        String      temp;
        String[]    list; 
        String      delim = "[,]";
        
        temp = entireFile.substring(index + 1);
        
        secondIndex = temp.indexOf("/"); 
        
        temp = temp.substring(0, secondIndex);
        
        list = temp.split(delim);
        
        for(int i = 0; i < list.length; i++){
            allrentedDVD.add(list[i]);
        }
       
        for (int i = (index + secondIndex); i < entireFileLength; i++)
        {
            if(entireFile.charAt(i) == ';'){
                end = i; 
                break; 
            }
        }
        
        return end; 
    }
    
    public List<String> getRentedDVDList(){
        return allrentedDVD; 
    }
    
    public int sumOfDVDRented(String title){
        int count = 0; 
        List<String> temp = new ArrayList<>();
        
        temp = getRentedDVDList();
        
        for(int i = 0; i < temp.size(); i++){
            if (title.contains(temp.get(i)))
                count ++; 
        }
            
        return count; 
    }
    
    private void parseBluRayTitles(){
        
        allrentedBluRay = new ArrayList<>();
        
        for (int i = 0; i < entireFileLength; i++)
        {
            if(entireFile.charAt(i) == '@'){
                i = putInBluRayList(i); 
            }
        }
    }
    
    private int putInBluRayList(int index){
        int         secondIndex;
        int         end = 0; 
        String      temp;
        String[]    list; 
        String      delim = "[,]";
        
        temp = entireFile.substring(index + 1);
        
        secondIndex = temp.indexOf("@"); 
        
        temp = temp.substring(0, secondIndex);
        
        list = temp.split(delim);
        
        for(int i = 0; i < list.length; i++){
            allrentedBluRay.add(list[i]);
        }
       
        for (int i = (index + secondIndex); i < entireFileLength; i++)
        {
            if(entireFile.charAt(i) == ';'){
                end = i; 
                break; 
            }
        }
        
        return end; 
    }
    
    public List<String> getRentedBluRayList(){
        return allrentedBluRay; 
    }
    
    public int sumOfBluRayRented(String title){
        int count = 0; 
        List<String> temp = new ArrayList<>();
        
        temp = getRentedBluRayList();
        
        for(int i = 0; i < temp.size(); i++){
            if (title.contains(temp.get(i)))
                count ++; 
        }
            
        return count; 
    }
    
    public void findUser(String ID){

         if(searchFile(ID)){ 
             setUserID();
             setBalance();
             setRentedDVD();
             setRentedBluRay(); 
             setPurchased();
         }
         else
            throw new IllegalArgumentException ("Not a valid User");
    }

    public void setUserID(){
        int index;
        String temp; 
 
        index = (str.indexOf("#"));
        
        temp = str.substring(index + 1);
         
        index = temp.indexOf("#");
         
        userID = temp.substring(0, index);

    }
    
    public String getUserID(){
        return userID; 
    }
    
    public void setBalance(){
        int index;
        String temp; 
       
        index = (str.indexOf("$"));
        
        temp = str.substring(index + 1);
         
        index = temp.indexOf("$");
         
        balance = temp.substring(0, index);
    }
    
    public String getBalance(){
        return balance; 
    }
    
     public void setRentedDVD(){
        int index; 
        String temp;
        String delim = "[,]";
        
        index = (str.indexOf("/") + 1);
        
         temp = str.substring(index);
         
         index = temp.indexOf("/");
         
         temp = temp.substring(0, index);
         
         rentedDVD = temp.split(delim); 
    }
    
    public String[] getRentedDVD(){
        return rentedDVD; 
    }
    
    public void setRentedBluRay(){
        int index; 
        String temp;
        String delim = "[,]";
        
        index = (str.indexOf("@") + 1);
        
         temp = str.substring(index);
         
         index = temp.indexOf("@");
         
         temp = temp.substring(0, index);
         
         rentedBluRay = temp.split(delim); 
    }
    
    public String[] getRentedBluRay(){
        return rentedBluRay; 
    }
    
    public void setPurchased(){
        int index; 
        String temp; 
        String delim = "[,]"; 
        
        index = (str.indexOf("%") + 1);
        
         temp = str.substring(index);
         
         index = temp.indexOf("%");
         
         temp = temp.substring(0, index);
         
         purchased = temp.split(delim); 
    }
    
    public String[] getPurchased(){
        return purchased; 
    }
    
    public void updateUserInfo(String ID, String bal, String renteddvd, String rentedbluray, String bought){ // Will concatenate all strings together so that they can be written to the file. 
        String oldUserInfo, newUserInfo, updatedUserInfo; 
        
        if(!searchFile(ID)){
            allUsers.add(formatString(ID, bal, renteddvd, rentedbluray, bought)); 
        }
        else{
            findUser(ID);
            oldUserInfo = formatString(userID, balance, catStringArray(rentedDVD), catStringArray(rentedBluRay), catStringArray(purchased)); 
            newUserInfo = formatString(userID, updateBalance(bal), updateDVD(renteddvd), updateBluRay(rentedbluray), updatePurchased(bought));
 
            if (!oldUserInfo.equals(newUserInfo)){
                updatedUserInfo = newUserInfo; 
            for(int i = 0; i < allUsers.size(); i++){
                    if(oldUserInfo.equals(allUsers.get(i))){
                        allUsers.set(i, updatedUserInfo); 
                        break;
                    }
                }
            }
        }       
    }
    
    private String updateBalance(String str){
        
        if (!balance.equals(str)){
            return str; 
        }
        else
            return balance; 
    }
    
    private String updateDVD(String str){
        
        if (!catStringArray(rentedDVD).contains(str)){
            return catStringArray(rentedDVD) + "," + str;
        }
        else
            return catStringArray(rentedDVD); 
    }
    
    private String updateBluRay(String str){
        if (!catStringArray(rentedBluRay).contains(str)){
            return catStringArray(rentedBluRay) + "," + str;
        }
        else
            return catStringArray(rentedBluRay); 
    }
    
    private String updatePurchased(String str){
        if (!catStringArray(purchased).contains(str)){
            return catStringArray(purchased) + "," + str;
        }
        else
            return catStringArray(purchased); 
    }
    
    private String catStringArray(String[] stringArray){ // This will add new titles from your array into a string that can be writen to the file. 
        String temp = ""; 
        
        for(int i = 0; i < stringArray.length; i++){
            
            if(temp.isEmpty())
                temp = stringArray[i];
            
            else if(i < (stringArray.length - 1) && i > 0)
                temp = temp + "," + stringArray[i];
 
            else if (i == (stringArray.length - 1))
                temp = temp + "," + stringArray[i]; 
        }
        
        return temp; 
    }
 
    public String formatString(String ID, String balance, String renteddvd, String rentedbluray, String purchased){ // The function that concatenate the Strings and adds proper stamps
        
        String id, b, rdvd, rbluray, p;
        
            id = "#" + ID + "#"; 
            b = "$" + balance + "$"; 
            rdvd = "/" + renteddvd + "/"; 
            rbluray = "@" + rentedbluray + "@"; 
            p = "%" + purchased + "%"; 
            return id + " " + b + " " + rdvd + " " + rbluray + " " + p + ";";
    }

    public void updateFile(){ // appends the new string to the file. 
        
        try {
         BufferedWriter out = new BufferedWriter(new FileWriter("./transactionFile.txt", false));
         out.write(allUsers.get(0));
         for(int i = 1; i < allUsers.size(); i++)
            out.write("\n" + allUsers.get(i));
         
         out.write("\neof"); 
         out.close();
        }
        catch (IOException e) {
          e.getMessage(); 
        }
    }
    
}
