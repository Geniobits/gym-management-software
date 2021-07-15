/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geniobits.dashboard.controller;

import geniobits.dashboard.model.FeesModel;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.Connect;

/**
 * FXML Controller class
 *
 * @author DESTINY
 */
public class DefaultMessagesController implements Initializable {

    /**
     * Initializes the controller class.
     */
     
    @FXML
    private TextArea messageReg;
    
    @FXML
    private TextField subjectReg;
    
    @FXML
    private TextArea messageEnd;
    
    @FXML
    private TextField subjectEnd;
    
    @FXML
    private TextArea messageDOB;
    
    @FXML
    private TextField subjectDOB;
    
    @FXML
    private Button btnSave;
    
    @FXML
    private Button close;
    
      Connection con=null;
     ResultSet rs=null;
     PreparedStatement pst=null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String sql="Select type,sub,body from messages";
   
   
        try{
        	con=Connect.connectDb();
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            int i=0,j = 0,count=0;
            
            while (rs.next()) {
                  
                   if(rs.getString("type").equals("reg")){
                       messageReg.setText(rs.getString("body"));
                       subjectReg.setText(rs.getString("sub"));
                   }else if(rs.getString("type").equals("end")){
                        messageEnd.setText(rs.getString("body"));
                       subjectEnd.setText(rs.getString("sub"));
                   }else if(rs.getString("type").equals("DOB")){
                        messageDOB.setText(rs.getString("body"));
                       subjectDOB.setText(rs.getString("sub"));
                   }
            }
                
            pst.close();
 		    rs.close();
 		    con.close();
            
        }
        catch(Exception e)
        {
           Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e);
        }
      
        try{
            pst.close();
 		    rs.close();
 		    con.close();
        }catch(Exception e){
            
        }
    }    
    
      @FXML
    private void handleButtonAction(MouseEvent event) throws FileNotFoundException {        
        if(event.getSource() == btnSave){
            if(messageReg.getText().length()<140 && messageEnd.getText().length()<140 && messageDOB.getText().length()<140){
                cleanMessages();
                insertReg();
                insertEnd();
                insertDob();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Succes");
                alert.setContentText("Messages succesfully updated !");
                alert.showAndWait();
            }else{
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Failed");
                alert.setContentText("Messages should be less than 140 characters !");
                alert.showAndWait();
            }
        }else if(event.getSource() == close){
             Stage stage = (Stage) close.getScene().getWindow();
                // do what you have to do
                stage.close();
        }
       
    }

    private void cleanMessages() {
        try{
                                 con.close();
                                 con=Connect.connectDb();
                            String sql2="DROP TABLE messages";
			pst=con.prepareStatement(sql2);
			
			pst.execute();
			rs.close();
			pst.close();
                       
                            }catch(Exception e){
                                System.out.println(e);
                            }
                            
    }

    private void insertReg() {
        try{
                                con.close();
                                 con=Connect.connectDb();
                            String sql2="Insert into messages(type,sub,body) values(?,?,?)";
			pst=con.prepareStatement(sql2);
			pst.setString(1, "reg");
                        String sub = subjectReg.getText();
                        sub= sub.trim();
                        pst.setString(2,sub );
                        pst.setString(3, messageReg.getText().trim());
                    //    System.out.println(rs.getString(7));
			pst.execute();
			rs.close();
			pst.close();
                            }catch(Exception e){
                                System.out.println(e);
                            }
    }

    private void insertEnd() {
   try{
                                con.close();
                                 con=Connect.connectDb();
                            String sql2="Insert into messages(type,sub,body) values(?,?,?)";
			pst=con.prepareStatement(sql2);
			pst.setString(1, "end");
                        pst.setString(2, subjectEnd.getText().trim());
                        pst.setString(3, messageEnd.getText().trim());
                    //    System.out.println(rs.getString(7));
			pst.execute();
			rs.close();
			pst.close();
                            }catch(Exception e){
                                System.out.println(e);
                            } }

    private void insertDob() {
        try{
                                con.close();
                                 con=Connect.connectDb();
                            String sql2="Insert into messages(type,sub,body) values(?,?,?)";
			pst=con.prepareStatement(sql2);
			pst.setString(1, "DOB");
                        pst.setString(2, subjectDOB.getText().trim());
                        pst.setString(3, messageDOB.getText().trim());
                    //    System.out.println(rs.getString(7));
			pst.execute();
			rs.close();
			pst.close();
                            }catch(Exception e){
                                System.out.println(e);
                            }
    }
    
}
