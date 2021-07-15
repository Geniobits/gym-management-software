/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geniobits.dashboard.controller;


import classes.Search_Member;


import classes.Update_Fee_Value;
import geniobits.dashboard.model.FeesModel;
import geniobits.dashboard.model.HistoryModel;
import geniobits.dashboard.model.MembersModel;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class HistoryController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private Pane recent;
     
     @FXML
    private Pane remove_all;
     
     @FXML
    private Pane delete;
     
     @FXML
    private Pane refresh;
     
     @FXML
    private TableView<HistoryModel> History_Table;
     
     Connection con=null;
     ResultSet rs=null;
     PreparedStatement pst=null;
     
     
     @FXML
    public TableColumn<HistoryModel,String> Id;

    @FXML
    public TableColumn<HistoryModel,String> Time;

    @FXML
    public TableColumn<HistoryModel,String> Task;
    
   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Id.setCellValueFactory(new PropertyValueFactory<HistoryModel,String>("id"));//"" same as in model
        Time.setCellValueFactory(new PropertyValueFactory<HistoryModel,String>("time"));
        Task.setCellValueFactory(new PropertyValueFactory<HistoryModel,String>("task"));
         
        buildData();
    }    
    
    
    @FXML
    private void handleButtonAction(MouseEvent event) {        
       
        if (event.getSource() == recent) {
            recentData();
            
              }else if (event.getSource() == remove_all) {
            try {
					con=Connect.connectDb();
					String sql = "DROP TABLE history";
					pst=con.prepareStatement(sql);
					pst.execute();
					
					if(pst.execute())
					{
						JOptionPane.showMessageDialog(null, "Sorry history isnot cleared");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "All history is cleared");
					}
					
					
				}catch(Exception ee)
				{
					//JOptionPane.showMessageDialog(null, "Something Went Wrong");
                                       //  java.util.logging.Logger.getLogger(Sell_Product.class.getName()).log(java.util.logging.Level.SEVERE, null, ee);
				}
				
        }else if (event.getSource() == delete) {
           HistoryModel f = History_Table.getSelectionModel().getSelectedItem();					
                                        if(f!=null){
                                            String table_click=f.getId();
					con=Connect.connectDb();
                                        String sql="Delete from history where id='"+table_click+"'";
                                        try{
					pst=con.prepareStatement(sql);
					if(pst.execute())
					{
						JOptionPane.showMessageDialog(null, "Sorry history is not cleared");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Selected history is cleared");
                        
					}
					}
					catch(Exception e){
                                                JOptionPane.showMessageDialog(null, "Error Ocured");
                                        }
                                        }else {
						JOptionPane.showMessageDialog(null, "Pleasse select any history");					
					}
        }else if (event.getSource() == refresh) {
            buildData();
        }
    }

    
    
    private ObservableList<HistoryModel> data;

public void buildData(){        
    data = FXCollections.observableArrayList();
    
        String sql="Select * from history";
   
        try{
        	con=Connect.connectDb();
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            while (rs.next()) {
                   HistoryModel m = new HistoryModel();
                   
                m.setId(rs.getString(1));                   
                 
                m.setTime(rs.getString(2));
                
                m.setTask(rs.getString(3));
                
                
                
                data.add(m);
                History_Table.setItems(data);
            }
 
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

public void recentData(){        
    data = FXCollections.observableArrayList();
    
        String sql="Select * from history order by id desc";
   
        try{
        	con=Connect.connectDb();
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            while (rs.next()) {
                   HistoryModel m = new HistoryModel();
                   
                m.setId(rs.getString(1));                   
                 
                m.setTime(rs.getString(2));
                
                m.setTask(rs.getString(3));
                
                
                
                data.add(m);
                History_Table.setItems(data);
            }
 
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
    
    
    
}
