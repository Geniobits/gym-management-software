/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geniobits.dashboard.controller;


import classes.Search_Member;
import classes.Update_Fee_Value;
import geniobits.dashboard.model.FeesModel;
import geniobits.dashboard.model.MembersModel;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import utils.Connect;

/**
 * FXML Controller class
 *
 * @author DESTINY
 */
public class FeesController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private Pane add;
     
     @FXML
    private Pane update;
     
     @FXML
    private Pane delete;
     
     @FXML
    private Pane refresh;
     
     @FXML
    private TableView<FeesModel> Fee_Table;
     
     Connection con=null;
     ResultSet rs=null;
     PreparedStatement pst=null;
     
     
     @FXML
    public TableColumn<FeesModel,String> Id;

    @FXML
    public TableColumn<FeesModel,String> GymPlan;

    @FXML
    public TableColumn<FeesModel,String> months;
    
    @FXML
    public TableColumn<FeesModel,String> amount;
    
   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Id.setCellValueFactory(new PropertyValueFactory<FeesModel,String>("id"));//"" same as in model
        GymPlan.setCellValueFactory(new PropertyValueFactory<FeesModel,String>("gymPlan"));
        months.setCellValueFactory(new PropertyValueFactory<FeesModel,String>("months"));
         amount.setCellValueFactory(new PropertyValueFactory<FeesModel,String>("amount"));
        
       buildData();
    }    
    
    
    @FXML
    private void handleButtonAction(MouseEvent event) {        
       
        if (event.getSource() == add) {
            Update_Fee_Value uffv= new Update_Fee_Value();
				uffv.btnUpdate.setText("Save");
				uffv.lblUpdatingFee.setText("Insert new Duration & Fee");
				uffv.ForInsert();
				uffv.show();
        }else if (event.getSource() == update) {
            FeesModel f = Fee_Table.getSelectionModel().getSelectedItem();					
                                        if(f!=null){
                                            String table_click=f.getId();
					Update_Fee_Value ufv= new Update_Fee_Value();
					ufv.updating_fees(table_click);
					ufv.show();
                                        }else{
                                            JOptionPane.showMessageDialog(null, "Please select fee");
                                        }
				
        }else if (event.getSource() == delete) {
           FeesModel f = Fee_Table.getSelectionModel().getSelectedItem();					
                                        if(f!=null){
                                            String table_click=f.getId();
					Update_Fee_Value ufv= new Update_Fee_Value();
					ufv.deleting_fees(table_click);
					ufv.show();
                                        }else{
                                            JOptionPane.showMessageDialog(null, "Please select fee");
                                        }
        }else if (event.getSource() == refresh) {
            buildData();
        }
    }

    
    
    private ObservableList<FeesModel> data;

public void buildData(){        
    data = FXCollections.observableArrayList();
    
        String sql="Select * from fees";
   
        try{
        	con=Connect.connectDb();
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            while (rs.next()) {
                   FeesModel m = new FeesModel();
                   
                m.setId(rs.getString(1));                   
                 
                m.setGymPlan(rs.getString(2));
                
                m.setMonths(rs.getString(3));
                
                m.setAmount(rs.getString(4));                
                
                data.add(m);
                Fee_Table.setItems(data);
            }
                    pst.close();
 		    rs.close();
 		    con.close();            
        }
        catch(Exception e)
        {
          // Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e);
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
}
