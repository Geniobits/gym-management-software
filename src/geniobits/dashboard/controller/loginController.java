package geniobits.dashboard.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import classes.Forgot;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.ConnectionUtil;
import utils.Connect;


/**
 *
 * @author oXCToo
 */
public class loginController implements Initializable {

    @FXML
    private Label lblErrors;

    @FXML
    public TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnSignin;
    
    
    @FXML
    private Button btnSignup;
    
    
    @FXML
    private Button close;
    
    @FXML
    private Label btnForgot;
  
    @FXML   // Create a combo box 
    private ComboBox<String> combo_box ;
                  
  
        // Set on action 
        
  

    /// -- 
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
	 ResultSet rs=null;
	 PreparedStatement pst=null;
         public String Hitory_User;

    @FXML
    public void handleButtonAction(MouseEvent event) {

        if (event.getSource() == btnSignin) {
            //login here
            if (logIn()=="Success") {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Home h = new Home(Hitory_User);
                try {
                     Stage stage2;
                    stage2 = new Stage();
                     h.start(stage2);
                } catch (Exception ex) {
                    Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }else{
                lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Username or Password incorrect");
            }
        }else if(event.getSource() == btnSignup){
             Scene scene;
              Stage stage2;
                    stage2 = new Stage();
            try {
                scene = new Scene(FXMLLoader.load(getClass().getResource("/geniobits/dashboard/controller/view/sign_up.fxml")));
                  stage2.initStyle(StageStyle.TRANSPARENT);
                stage2.setScene(scene);
                stage2.getIcons().add(new Image(this.getClass().getResourceAsStream("/geniobits/dashboard/images/logo.png")));
                    stage2.show();
            } catch (IOException ex) {
                Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
            }
                    
        }else if(event.getSource() == close){
             System.exit(0);            
            
        }else if(event.getSource() == btnForgot){
            
            Forgot forgot= new Forgot();				
	   forgot.show();
        }
    }
    
    private void close(MouseEvent event){
                 Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Server Error : Check");
        } else {
            lblErrors.setTextFill(Color.GREEN);
            lblErrors.setText("Server is up : Good to go");
            
              combo_box.setItems(FXCollections 
                              .observableArrayList("Admin","User")); 
                      
        // Label to display the selected menuitem 
       
        // Create action event 
        
            
        }
    }

    public loginController() {
        try{
        con.close();
        }catch(Exception e){
            
        }
        con=Connect.connectDb();

    }

    //we gonna use string to check for status
    private String logIn() {

        String uname=txtUsername.getText();
        Hitory_User=txtUsername.getText();
        String pasw=String.valueOf(txtPassword.getText());
     //query
        String sql="Select * from login where username='"+uname+"' and password='"+pasw+"'";
		
        try {
           pst=con.prepareStatement(sql);
	         rs=pst.executeQuery();
	          
            if (rs.next()) {
                        String UserType=rs.getString(7);   
                             try{
                                 con.close();
                                 con=Connect.connectDb();
                            String sql2="DROP TABLE user";
			pst=con.prepareStatement(sql2);
			
			pst.execute();
			rs.close();
			pst.close();
                       
                            }catch(Exception e){
                                System.out.println(e);
                            }
                            try{
                                con.close();
                                 con=Connect.connectDb();
                            String sql2="Insert into user(username,user_type) values(?,?)";
			pst=con.prepareStatement(sql2);
			pst.setString(1, Hitory_User);
                        pst.setString(2, UserType);
                    //    System.out.println(rs.getString(7));
			pst.execute();
			rs.close();
			pst.close();
                            }catch(Exception e){
                                System.out.println(e);
                            }
                            
			Date det=new Date();
			SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			String date=format.format(det);
			String msz="Logged in by "+Hitory_User;
			String sql1="Insert into history(date,task) values(?,?)";
			pst=con.prepareStatement(sql1);
			pst.setString(1, date);
			pst.setString(2, msz);
			pst.execute();
			rs.close();
			pst.close();
                        
                        con.close();
			
			return "Success";
		        
			
		}

	else {
                lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Password or username wrong");
		return "Error";
	}

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return "Exception";
        }

    }

}
