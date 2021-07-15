/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geniobits.dashboard.controller;


import classes.AutocompletionlTextField;
import classes.Search_Member;

import classes.Update_Fee_Value;

import geniobits.dashboard.model.FeesModel;
import geniobits.dashboard.model.MembersModel;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

import utils.Connect;

/**
 * FXML Controller class
 *
 * @author DESTINY
 */
public class DashboardStaffController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private Pane AttendanceToday;
     @FXML
    private Label attendanceTot;
     
     @FXML
    private Label totmemebres;
     
     @FXML
    private Label remPay;
     
     @FXML
    private Label donePay;
     
     @FXML
    private Pane totMems;
     
    
     
     @FXML
    private TableView<FeesModel> tbData;
     
      @FXML
    private Button cMachine1;
      
      @FXML
    private Button cMachine2;
      
      @FXML
    private Button btnSearch;
      
      @FXML
    private AutocompletionlTextField txtSearch;
     
     Connection con=null;
     ResultSet rs=null;
     PreparedStatement pst=null;
     
     
     @FXML
    public TableColumn<FeesModel,String> Id;

    @FXML
    public TableColumn<FeesModel,String> Name;

    @FXML
    public TableColumn<FeesModel,String> DateTime;
    
    @FXML
    public TableColumn<FeesModel,String> varifyMode;
    
   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Id.setCellValueFactory(new PropertyValueFactory<FeesModel,String>("id"));//"" same as in model
        Name.setCellValueFactory(new PropertyValueFactory<FeesModel,String>("gymPlan"));
        DateTime.setCellValueFactory(new PropertyValueFactory<FeesModel,String>("months"));
        varifyMode.setCellValueFactory(new PropertyValueFactory<FeesModel,String>("amount"));
         
           todayData();
           memData();          
           buildData();
           
           
        final Timeline timeline = new Timeline(
    new KeyFrame(
        Duration.minutes(30),
        event -> {
            Platform.runLater(new Runnable() {
            @Override
            public void run() {

                
                todayData();
                memData();                
                buildData();
            // Update UI here.
            }
        });
    }
    )
);
timeline.setCycleCount( Animation.INDEFINITE );
timeline.play();
           
    }    
    
    
    @FXML
    private void handleButtonAction(MouseEvent event) {        
       
        if (event.getSource() == AttendanceToday) {
             todayData();
        }else if (event.getSource() == totMems) {
              	memData();			
        }else if (event.getSource() == btnSearch) {
                String table_click=txtSearch.getText().split(" ")[0];
              searchData(table_click);
        }else if (event.getSource() == cMachine1) {
            
         Platform.runLater(new Runnable() {
    @Override
    public void run() {
         
           todayData();
           memData();           
           buildData();
        // Update UI here.
    }
});
       
    }else if (event.getSource() == cMachine2) {
           
            Platform.runLater(new Runnable() {
    @Override
    public void run() {
        
                                                                                                                                                                 
           todayData();
           memData();
          
           buildData();
        // Update UI here.
    }
});
        }
    }

    
    
    private ObservableList<FeesModel> data;
    
   
public void buildData(){        
     DateTime.setText("Entry Time");
   varifyMode.setText("Varify mode");
    data=FXCollections.observableArrayList();
        String sql="Select * from attendance";
        
   
        try{
        	con=Connect.connectDb();
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            while (rs.next()) {
                   FeesModel m = new FeesModel();
                    String sql2="Select * from members where id='"+rs.getString(2)+"' AND discription='staff'";
                    PreparedStatement pst2 = con.prepareStatement(sql2);
                    ResultSet rs2 = pst2.executeQuery();
                   String name="deleted";
                   try{
                       name=rs2.getString(3);
                       m.setId(rs.getString(2));                   
                
                m.setGymPlan(name);
                
                m.setMonths(rs.getString(4));
                
                m.setAmount("FingerPrint");          
//                
                data.add(m);
                tbData.setItems(data);
                        }catch(Exception e){
                           
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

public void searchData(String id){        
     DateTime.setText("Entry Time");
   varifyMode.setText("Varify mode");
    data=FXCollections.observableArrayList();
        String sql="Select * from attendance where user_id='"+id+"'";
        
   
        try{
        	con=Connect.connectDb();
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            while (rs.next()) {
                   FeesModel m = new FeesModel();
                 String sql2="Select * from members where id="+rs.getString(2)+" AND discription='staff'";
                    PreparedStatement pst2 = con.prepareStatement(sql2);
                    ResultSet rs2 = pst2.executeQuery();
                   try{
                m.setId(rs.getString(2));                   
                
                m.setGymPlan(rs2.getString(3));
                
                m.setMonths(rs.getString(4));
                
                m.setAmount("FingerPrint");          
//                
                data.add(m);
                tbData.setItems(data);
                   }catch(Exception e){
                       
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

       public void todayData(){       
           data=FXCollections.observableArrayList();
            DateTime.setText("Entry Time");
            varifyMode.setText("Varify mode");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
	Date date = new Date();
	
        String sql="Select * from attendance where date like '%"+dateFormat.format(date)+"%'";
   
        try{
        	con=Connect.connectDb();
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            int array[]=new int[500],i=0;
            while (rs.next()) {
                   FeesModel m = new FeesModel();
                    try{
                   String sql2="Select * from members where id="+rs.getString(2)+" AND discription='staff'";
                      PreparedStatement pst2 = con.prepareStatement(sql2);
                    ResultSet rs2 = pst2.executeQuery();
                   
                m.setId(rs2.getString(2));                   
                array[Integer.parseInt(rs2.getString(2))]=1;
                i=Integer.parseInt(rs2.getString(1));
                System.out.println("names"+rs2.getString(3));
                m.setGymPlan(rs2.getString(3));
         
                m.setMonths(rs.getString(4));
                
                m.setAmount("FingerPrint");
                
                pst2.close();
                rs2.close();
                data.add(m);
                tbData.setItems(data);
                }catch(Exception e){
                    // System.out.println(e);
                }
            }
                    pst.close();
 		    rs.close();
 		    con.close();
                    int j=0,count=0;
                    
                    while(j<=i){
                        count=count+array[j];
                        j++;
                    }
                    attendanceTot.setText(String.valueOf(count));
                    attendanceTot.setTextAlignment(TextAlignment.CENTER);
                    
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

   public void memData(){        
   
    data=FXCollections.observableArrayList();
        String sql="Select id,membership_no,full_name,reg_date,contact_no,plan,paid_fee from members where discription='staff'";
   DateTime.setText("Regestration Date");
   varifyMode.setText("Contact Num");
   
        try{
        	con=Connect.connectDb();
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            int i=0,j = 0,count=0;
            
            while (rs.next()) {
                   FeesModel m = new FeesModel();
                   
               m.setId(rs.getString(1));                   
                 
                 
                 j=Integer.parseInt(rs.getString(1));
                m.setGymPlan(rs.getString(3));
                
                m.setMonths(rs.getString(4));
                
                m.setAmount(rs.getString(5));
                count++;
                 txtSearch.getEntries().add(rs.getString(1)+" "+rs.getString(3)+" "+rs.getString(5));
               
//                pst2.close();
//                rs2.close();
                data.add(m);
                
            }
            totmemebres.setText(String.valueOf(count));
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
    
    public void getDataFromMachine1() {
       
             
    }
    
    public void getDataFromMachine2(){
   
    }
    
    
    

    
   
   
}
