/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geniobits.dashboard.controller;

import static classes.config.getAdminKey;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import utils.Connect;

/**
 * FXML Controller class
 *
 * @author oXCToo
 */
public class regesterController implements Initializable {

    @FXML
    private TextField txtFullName;
     @FXML
    private TextField Answer;
     
     @FXML
    private TextField adminkey;
     
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    
    Connection con=null;
	 ResultSet rs=null;
	 PreparedStatement pst=null;
    
    @FXML
    private Button btnSave;
    @FXML
    private ComboBox<String> txtUserType;
    @FXML
    Label lblStatus;
@FXML
    private ComboBox<String> txtQuestion;



    @FXML
    TableView tblData;
    
    @FXML
    private Button close;

    /**
     * Initializes the controller class.
     */
    PreparedStatement preparedStatement;
    Connection connection;
    
    

    public regesterController() {
        
        //connection = (Connection) ConnectionUtil.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        adminkey.setVisible(false);
        // TODO
        txtUserType.getItems().addAll("Admin", "User");
        
        txtUserType.valueProperty().addListener(new ChangeListener<String>() {        
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                 //To change body of generated methods, choose Tools | Templates.
                String usertype=txtUserType.getSelectionModel().getSelectedItem();
            try{
            if(usertype.equals("Admin")){
                adminkey.setVisible(true);
            }else{
                adminkey.setVisible(false);
            }
            }catch(Exception e){
                
            }
                
            }
    });
        
        txtQuestion.getItems().addAll("What is your father name?", "What is your born city?", "Who is your Best Actor?");
        
        
        //fetColumnList();
        //fetRowList();

    }
    
    

    @FXML
    private void HandleEvents(MouseEvent event) {
        //check if not empty
        String result;
         if (event.getSource() == btnSave) {
        if (txtFullName.getText().isEmpty() || txtUsername.getText().isEmpty() || Answer.getText().isEmpty() ||txtPassword.getText().isEmpty() || txtUserType.getValue().equals(null)|| txtQuestion.getValue().equals(null)) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Enter all details");
        } else {
            if(txtUserType.getValue().toString().equals("Admin") && adminkey.getText().equals(getAdminKey())){
           result=saveData();
            }else if(txtUserType.getValue().toString().equals("User")){
                result=saveData();
            }else{
               result="failer" ;
            }
            if(result=="Success"){
             Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
            }else{
               lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Enter all details or check Admin Key"); 
            }
        }
         }else if(event.getSource() == close){
            Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
           
        }

    }
    
   

    private void clearFields() {
        txtFullName.clear();
        txtUsername.clear();
        Answer.clear();
    }

    private String saveData() {

        try {
             con = Connect.connectDb();
             
					String sql = " insert into login (username,password,full_name, security_question, answer,type)"
								+ " values (?, ?, ?, ?,?,?)";

	      	 java.sql.PreparedStatement preparedStmt = con.prepareStatement(sql);
		     preparedStmt.setString (1,txtUsername.getText());   
		     preparedStmt.setString (2,txtPassword.getText());
		     preparedStmt.setString (3,txtFullName.getText());
			 preparedStmt.setString	(4, (String)txtQuestion.getValue());
			 preparedStmt.setString(5, Answer.getText());
			 preparedStmt.setString(6,txtUserType.getValue().toString());
			 preparedStmt.execute();
		      JOptionPane.showMessageDialog(null, "You have successfully created new account");
		      preparedStmt.close();
            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Added Successfully");
            

            
            //clear fields
            clearFields();
             
            return "Success";

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText(ex.getMessage());
            return "Exception";
        }
    }

    

}
