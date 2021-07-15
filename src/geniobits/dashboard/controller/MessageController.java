/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geniobits.dashboard.controller;
import classes.Search_Member;
//import classes.emailClass;
//import classes.smsClass;
import geniobits.dashboard.model.FeesModel;
import javafx.scene.control.TextArea;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import utils.Connect;

/**
 * FXML Controller class
 *
 * @author DESTINY
 */

    

public class MessageController implements Initializable {

    /**
     * Initializes the controller class.
     */
   
    
    @FXML
    private TextArea message;
    
    @FXML
    private TextField subject;
    
    @FXML
    private Button btnAll;
    
     @FXML
    private Button btnSms;
     
      @FXML
    private Button btnEmail;
       @FXML
    private Button btnSettings;
        @FXML
    private TableView<FeesModel> tbData;
        
        
        @FXML
    public TableColumn<FeesModel,String> Id;

    @FXML
    public TableColumn<FeesModel,String> Name;

    @FXML
    public TableColumn<FeesModel,String> Email;
    
    @FXML
    public TableColumn<FeesModel,String> contact;
     
      Connection con=null;
     ResultSet rs=null;
     PreparedStatement pst=null;
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
         Id.setCellValueFactory(new PropertyValueFactory<FeesModel,String>("id"));//"" same as in model
        Name.setCellValueFactory(new PropertyValueFactory<FeesModel,String>("gymPlan"));
        Email.setCellValueFactory(new PropertyValueFactory<FeesModel,String>("amount"));
         contact.setCellValueFactory(new PropertyValueFactory<FeesModel,String>("months"));
         tbData.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        buildData();
        
    }    
    
     @FXML
    private void handleButtonAction(MouseEvent event) {        
       
        if (event.getSource() == btnAll) {
            tbData.getSelectionModel().selectAll();
            
        }else if (event.getSource() == btnSms || event.getSource() == btnEmail) {
           ObservableList<FeesModel> selectedItems = tbData.getSelectionModel().getSelectedItems();
            
            if(!selectedItems.isEmpty()){
                FeesModel m = new FeesModel();
                ArrayList<String> selectedIDs = new ArrayList<String>();

                int size= selectedItems.size();
                int count;
                String names = null;
                String contact = "";
                String email = null;

                for(count=0;count<size;count++){
                   m=selectedItems.get(count);
                   if(names!=null){
                        names=names+","+m.getGymPlan();
                        contact = contact + ","+m.getMonths();
                        email = email + ","+m.getAmount();
                   }else{
                       names=m.getGymPlan();
                       contact=m.getMonths();
                       email = m.getAmount();
                   }
              }
              if(event.getSource() == btnSms){
               
                  if(!message.getText().isEmpty() || !subject.getText().isEmpty()){
                      if(message.getText().length()<140){
//                            smsClass wt = new smsClass(contact,subject.getText().trim()+" "+message.getText().trim(), m.getId());
//
//                            String temp = wt.sendCampaign();
//                           
                               
                                String UserName= "admin";
                                        String id="0";
                                        Date det=new Date();
					SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
					String date=format.format(det);
                                                try{
                                                    con=Connect.connectDb();
                                                String sql1="SELECT * from user";
						pst=con.prepareStatement(sql1);
		                                rs=pst.executeQuery();
                                                
                                                UserName= rs.getString(3);
                                                id= rs.getString(1);
                                                String msz=size +" Messages("+message.getText()+") sent by " +UserName+"("+id+")";
						String sql11="Insert into history(date,task) values(?,?)";
						pst=con.prepareStatement(sql11);
						pst.setString(1, date);
						pst.setString(2, msz);
						pst.execute();
                                                con.close();
                                                }catch(Exception e7){
                                                    
                                                }
                               
                                Alert fail= new Alert(Alert.AlertType.INFORMATION);
                                fail.setHeaderText("Information");
                               // fail.setContentText(temp);                              
                                fail.showAndWait();
                      }else{
                          Alert fail= new Alert(Alert.AlertType.INFORMATION);
                        fail.setHeaderText("Failure");
                        fail.setContentText("You message is more than 140 characters");
                        fail.showAndWait();
                      }
                }else{
                        Alert fail= new Alert(Alert.AlertType.INFORMATION);
                        fail.setHeaderText("Failure");
                        fail.setContentText("You haven't type any message or subject");
                        fail.showAndWait();
                }
             
              
              }else if(event.getSource() == btnEmail){
                System.out.println(" email"+names+email);
                if(!message.getText().isEmpty() || !subject.getText().isEmpty()){
                   // emailClass wt = new emailClass(message.getText().trim(), email,subject.getText().trim());

                 //   String temp = wt.sendCampaign();
                    String UserName= "admin";
                                        String id="0";
                                        Date det=new Date();
					SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
					String date=format.format(det);
                                                try{
                                                    con=Connect.connectDb();
                                                String sql1="SELECT * from user";
						pst=con.prepareStatement(sql1);
		                                rs=pst.executeQuery();
                                                
                                                UserName= rs.getString(3);
                                                id= rs.getString(1);
                                                String msz=size +" Email("+message.getText()+") sent by " +UserName+"("+id+")";
						String sql11="Insert into history(date,task) values(?,?)";
						pst=con.prepareStatement(sql11);
						pst.setString(1, date);
						pst.setString(2, msz);
						pst.execute();
                                                con.close();
                                                }catch(Exception e7){
                                                    
                                                }
//                    if(temp.equals("sent")){
//                        Alert fail= new Alert(Alert.AlertType.INFORMATION);
//                        fail.setHeaderText("Success");
//                        fail.setContentText("Email sent");
//                        fail.showAndWait();
//                    }
//                    else{
//                         Alert fail= new Alert(Alert.AlertType.INFORMATION);
//                        fail.setHeaderText("Failure");
//                        fail.setContentText("Email not sent");
//                        fail.showAndWait();
//                    }
                }else{
                        Alert fail= new Alert(Alert.AlertType.INFORMATION);
                        fail.setHeaderText("Failure");
                        fail.setContentText("You haven't type any message or subject");
                        fail.showAndWait();
                }
                
              }  
             
            }else{
                 Alert fail= new Alert(Alert.AlertType.INFORMATION);
                 fail.setHeaderText("Failure");
                 fail.setContentText("You haven't Selected anyone");
                 fail.showAndWait();
            } 
        }else if(event.getSource()== btnSettings){
             try {
                     FXMLLoader loader = new FXMLLoader(getClass().getResource("/geniobits/dashboard/controller/view/defaultMessages.fxml"));
                     Stage stage = new Stage(StageStyle.TRANSPARENT);
                     stage.setScene(new Scene((Pane) loader.load()));
                     stage.show();
                } catch (Exception ex) {
                    Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }

    

   

   
    private ObservableList<FeesModel> data;
    
   public void buildData(){        
   
        data = FXCollections.observableArrayList();
        String sql="Select id,full_name,contact_no,email from members";
   
   
        try{
        	con=Connect.connectDb();
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            int i=0,j = 0,count=0;
            
            while (rs.next()) {
                   FeesModel m = new FeesModel();
                   
               m.setId(rs.getString(1));                   
                 
                 
                 j=Integer.parseInt(rs.getString(1));
                m.setGymPlan(rs.getString(2));
                
                m.setMonths(rs.getString(3));
                
                m.setAmount(rs.getString(4));
                count++;
                
//                pst2.close();
//                rs2.close();
                data.add(m);
                
            }
            
          //  rs.next();
            tbData.setItems(data);
         
            
//;
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
   
  
    
}
