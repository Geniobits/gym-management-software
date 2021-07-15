/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geniobits.dashboard.controller;



import classes.AutocompletionlTextField;
import classes.Search_Member;
import geniobits.dashboard.model.FeesModel;
import geniobits.dashboard.model.MembersModel;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

import utils.Connect;

/**
 * FXML Controller class
 *
 * @author DESTINY
 */
public class MembersController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private Pane addMember;
     
     @FXML
    private Pane delete_user;
     
     @FXML
    private Pane update_user;
     
     @FXML
    private Pane search_user;
     
     @FXML
    private TableView<MembersModel> Member_Table;
     
     Connection con=null;
     ResultSet rs=null;
     PreparedStatement pst=null;
      @FXML 
	  private AutocompletionlTextField txtSearch;
       
     @FXML
    private Button btnSearch;
     
     @FXML
    public TableColumn<MembersModel,String> memberId;

    @FXML
    public TableColumn<MembersModel,String> memberNum;

    @FXML
    public TableColumn<MembersModel,String> fullName;
    
    @FXML
    public TableColumn<MembersModel,String> regDate;
    
    @FXML
    public TableColumn<MembersModel,String> contactNum;
    
    @FXML
    public TableColumn<MembersModel,String> email;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        memberId.setCellValueFactory(new PropertyValueFactory<MembersModel,String>("memberId"));//"" same as in model
        memberNum.setCellValueFactory(new PropertyValueFactory<MembersModel,String>("memberNum"));
        fullName.setCellValueFactory(new PropertyValueFactory<MembersModel,String>("FullName"));
         regDate.setCellValueFactory(new PropertyValueFactory<MembersModel,String>("regDate"));
        contactNum.setCellValueFactory(new PropertyValueFactory<MembersModel,String>("contactNum"));
        email.setCellValueFactory(new PropertyValueFactory<MembersModel,String>("Email"));
        buildData();
    }    
    
    
    @FXML
    private void handleButtonAction(MouseEvent event) {        
       
        if (event.getSource() == addMember) {
             
                try {
                     FXMLLoader loader = new FXMLLoader(getClass().getResource("/geniobits/dashboard/controller/view/customer.fxml"));
                     Stage stage = new Stage(StageStyle.TRANSPARENT);
                     stage.setScene(new Scene((Pane) loader.load()));
                     EditMembersController controller = 
                     loader.<EditMembersController>getController();
                     stage.show();
                } catch (Exception ex) {
                    Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
        }else if (event.getSource() == delete_user) {
            MembersModel f = Member_Table.getSelectionModel().getSelectedItem();					
                                        if(f!=null){
                                             String table_click=f.getMemberId();
                                             try {
                                        con.close();
					con=Connect.connectDb();
					String sql="Delete from members where id="+table_click+"";
					pst=con.prepareStatement(sql);
					if(pst.execute())
					{
						Alert fail= new Alert(Alert.AlertType.INFORMATION);
                                                fail.setHeaderText("Failure");
                                                fail.setContentText("Member cant be deleted");
                                                fail.showAndWait();
					}
					else
					{
						Alert fail= new Alert(Alert.AlertType.INFORMATION);
                                                fail.setHeaderText("Success");
                                                fail.setContentText("Delete Successful");
                                                fail.showAndWait();
                                                buildData();
                                                delete(table_click);
                                                
                                        }
					pst.close();
					con.close();
					
				} catch (Exception e2) {
					
                                      //  Logger.getLogger(Add_Product.class.getName()).log(Level.SEVERE, null, e2);
				}
                                finally{
    	   try {
    		   pst.close();
    		   rs.close();
    		   con.close();
    	   }
        	catch(Exception e){
        		
        	}
        }
                                             
                                        }else{
                                                Alert fail= new Alert(Alert.AlertType.INFORMATION);
                                                fail.setHeaderText("Failure");
                                                fail.setContentText("You haven't Selected anyone");
                                                fail.showAndWait();
                                        }
                                             
        }else if (event.getSource() == update_user) {
                MembersModel f = Member_Table.getSelectionModel().getSelectedItem();					
                                        if(f!=null){
                                             String table_click=f.getMemberId();
                                        
           
                try {
                     FXMLLoader loader = new FXMLLoader(getClass().getResource("/geniobits/dashboard/controller/view/customer.fxml"));
                     Stage stage = new Stage(StageStyle.TRANSPARENT);
                     stage.setScene(new Scene((Pane) loader.load()));
                     EditMembersController controller = 
                     loader.<EditMembersController>getController();
                     controller.updating(table_click);
                     stage.show(); 
                  
                    
                } catch (Exception ex) {
                    Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                                        }else{
                                                Alert fail= new Alert(Alert.AlertType.INFORMATION);
                                                fail.setHeaderText("Failure");
                                                fail.setContentText("You haven't Selected anyone");
                                                fail.showAndWait();
                                        }
        }else if (event.getSource() == search_user) {
            buildData();
        }else if (event.getSource() == btnSearch) {
                String table_click=txtSearch.getText().split(" ")[0];
                                        
           
                try {
                     FXMLLoader loader = new FXMLLoader(getClass().getResource("/geniobits/dashboard/controller/view/customer.fxml"));
                     Stage stage = new Stage(StageStyle.TRANSPARENT);
                     stage.setScene(new Scene((Pane) loader.load()));
                     EditMembersController controller = 
                     loader.<EditMembersController>getController();
                     controller.updating(table_click);
                     stage.show(); 
                  
                    
                } catch (Exception ex) {
                    Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }

    
    
    private ObservableList<MembersModel> data;

public void buildData(){        
    data = FXCollections.observableArrayList();
    
        String sql="Select id,membership_no,full_name,reg_date,contact_no,email from members";
   
        try{
        	con=Connect.connectDb();
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            while (rs.next()) {
                   MembersModel m = new MembersModel();
                   
                m.setMemberId(rs.getString(1));                   
                String membership_no = rs.getString(1);
                
                m.setMemberNum(membership_no);
                
                String full_name = rs.getString(3);
                
                
                m.setFullName(full_name);
                
                String reg_date = rs.getString(4);   
                m.setRegDate(reg_date);
                
                String contact_no = rs.getString(5);   
                txtSearch.getEntries().add(membership_no+" "+full_name+" "+contact_no);
                m.setContactNum(contact_no);
                
                String email = rs.getString(6);   
                m.setEmail(email);
                data.add(m);
             Member_Table.setItems(data);
            }
          //  rs.next();
         
            
//;
                    pst.close();
 		    rs.close();
 		    con.close();
            
        }
        catch(Exception e)
        {
           Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e);
        }
       finally{
    	   try {
    		   pst.close();
    		   rs.close();
    		   con.close();
    	   }
        	catch(Exception e){
        		
        	}
        }
        
	}

public void delete(String id){
    String machine2="1";
     try {
                    
                     
                
		con=Connect.connectDb();
     String sql="select * from members where id='"+id+"'";
		    	pst=con.prepareStatement(sql);
		        rs=pst.executeQuery();
		        if(rs.next())
		        {
		        	
		        	  
                		  machine2=rs.getString("machine");
		        	  
                                    pst.close();
		    		   rs.close();
		    		   con.close();
					
				
				}
		        
	       }catch(Exception e)
	       {
	    	   JOptionPane.showMessageDialog(null, e);
                  //   Logger.getLogger(Add_Product.class.getName()).log(Level.SEVERE, null, e);
	       }
	      finally{
    	   try {
    		   pst.close();
    		   rs.close();
    		   con.close();
    	   }
        	catch(Exception e){
        		
        	}
        } 
     
     final String machine=machine2;
        Thread deleteToMachine = new Thread(new Runnable() {
         public void run() {
             // stuff here
            

         }
     });
}   
    
    
}
