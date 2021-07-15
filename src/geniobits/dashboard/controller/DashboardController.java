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
public class DashboardController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private Pane AttendanceToday;
     
      @FXML
    private Pane planEnd;
      
     @FXML
    private Label attendanceTot;
     
     @FXML
    private Label endPlan;
     
     @FXML
    private Label totmemebres;
     
     @FXML
    private Label remPay;
     
     @FXML
    private Label donePay;
     
     
     
     @FXML
    private Pane totMems;
     
     @FXML
    private Pane donePayments;
     
     @FXML
    private Pane remFees;
     
     @FXML
    private TableView<FeesModel> tbData;
     
      @FXML
    private Button cMachine1;
      
      @FXML
    private Button cMachine2;
     
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
    
     @FXML
    private Button btnSearch;
      
      @FXML
    private AutocompletionlTextField txtSearch;
    
   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Id.setCellValueFactory(new PropertyValueFactory<FeesModel,String>("id"));//"" same as in model
        Name.setCellValueFactory(new PropertyValueFactory<FeesModel,String>("gymPlan"));
        DateTime.setCellValueFactory(new PropertyValueFactory<FeesModel,String>("months"));
         varifyMode.setCellValueFactory(new PropertyValueFactory<FeesModel,String>("amount"));
         
           todayData();
           planEndData();	
           memData();
           feesRemData();
           feesPaidData();
           buildData();
           
           
           
        final Timeline timeline = new Timeline(
        new KeyFrame(
        Duration.minutes(30),
        event -> {
            Platform.runLater(new Runnable() {
            @Override
            public void run() {

                getDataFromMachine1();
                getDataFromMachine2();
               
                memData();
                feesRemData();
                feesPaidData(); 
                planEndData();	
                todayData();
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
        }else if (event.getSource() == planEnd) {
              	planEndData();		
        }else if (event.getSource() == donePayments) {
           feesPaidData();
        }else if (event.getSource() == btnSearch) {
                String table_click=txtSearch.getText().split(" ")[0];
              searchData(table_click);
        }else if (event.getSource() == remFees) {
            feesRemData();
        }else if (event.getSource() == cMachine1) {
            
         Platform.runLater(new Runnable() {
    @Override
    public void run() {
         getDataFromMachine1();
          
           memData();
           feesRemData();
           feesPaidData();
            planEndData();
            todayData();
            buildData();
        // Update UI here.
    }
});
       
    }else if (event.getSource() == cMachine2) {
           
            Platform.runLater(new Runnable() {
    @Override
    public void run() {
        
            getDataFromMachine2();                                                                                                                                                      
           
           memData();
           feesRemData();
           planEndData();
           feesPaidData();
           
           todayData();
           buildData();
        // Update UI here.
    }

                
            });
        }
    }

    
    private void planEndData() {
        ObservableList<FeesModel> data;
        data=FXCollections.observableArrayList();
        Date today = new Date();
            today = Calendar.getInstance().getTime();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            String date=format.format(today);
            System.out.println(date);
   
            String sql="Select id,full_name,end_date,contact_no,plan,paid_fee,email,machine from members where end_date<='"+date+"'";
        
            DateTime.setText("End Date");
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
                   m.setGymPlan(rs.getString(2));
                
                   m.setMonths(rs.getString(3));
                   txtSearch.getEntries().add(rs.getString(1)+" "+rs.getString(3)+" "+rs.getString(5));
               
                   m.setAmount(rs.getString(4));
                   count++;
                
//                 pst2.close();
//                 rs2.close();
                   data.add(m);
                
            }
                    endPlan.setText(String.valueOf(count));
                    //  rs.next();
                    tbData.setItems(data);
                    pst.close();
 		    rs.close();
 		    con.close();            
        }
        catch(Exception e)
        {
                    System.out.println(e);
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e);
        }
      
        try{
                    pst.close();
 		    rs.close();
 		    con.close();
        }catch(Exception e){
            
        }
    }
    
    
   
public void buildData(){        
    ObservableList<FeesModel> data;
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
                    String sql2="Select * from members where id="+rs.getString(2)+"";
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
    ObservableList<FeesModel> data;
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
                   String sql2="Select * from members where id="+rs.getString(2);
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
           ObservableList<FeesModel> data;
           data=FXCollections.observableArrayList();
           DateTime.setText("Entry Time");
           varifyMode.setText("Varify mode");
           DateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
           Date date = new Date();
           String dates[]=dateFormat.format(date).split("-");
        
           System.out.println("date today:"+dateFormat.format(date));
           
//           String month;
//           if(dates[1].equals("10")){
//                month=dates[1];
//           }else{
//                month=dates[1].replace("0", "");
//           }
           
           String sql="Select * from attendance where date like '%"+dateFormat.format(date)+"%'";
   
           try{
        	con=Connect.connectDb();
                pst=con.prepareStatement(sql);
                rs=pst.executeQuery();
                int array[]=new int[500],i=0;
                while (rs.next()) {
                   FeesModel m = new FeesModel();
                   String name="";
                   try{
                    String sql2="Select * from members where id="+rs.getString(2)+"";
                    PreparedStatement pst2 = con.prepareStatement(sql2);
                    ResultSet rs2 = pst2.executeQuery(); 
                    m.setId(rs.getString(2));                   
                    array[Integer.parseInt(rs.getString(2))]=1;
                    i=Integer.parseInt(rs2.getString(1));
                
                    m.setGymPlan(rs2.getString(3));
                    name=rs2.getString(3);  
                    m.setMonths(rs.getString(4));    
                    m.setAmount("FingerPrint"); 
                    pst2.close();
                    rs2.close();
                    data.add(m);
                        tbData.setItems(data);
                    }catch(Exception e){
                        System.out.println(e);
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
                        //Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e);

               }
        
	}        

   public void memData(){        
        ObservableList<FeesModel> data;
        data=FXCollections.observableArrayList();
        String sql="Select id,membership_no,full_name,reg_date,contact_no,plan,paid_fee from members";
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
                    txtSearch.getEntries().add(rs.getString(1)+" "+rs.getString(3)+" "+rs.getString(5));
               
                    m.setAmount(rs.getString(5));
                    count++;
                
//                pst2.close();
//                rs2.close();
                    data.add(m);
                
            }
                    totmemebres.setText(String.valueOf(count));
                  //  rs.next();
                    tbData.setItems(data);
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
   
  
   
   
   public void feesRemData(){        
   ObservableList<FeesModel> data;
    data=FXCollections.observableArrayList();
        String sql="Select id,membership_no,full_name,reg_date,contact_no,plan,paid_fee,discription,status from members";
        DateTime.setText("FeesRem");
        varifyMode.setText("Plan");
   
        try{
        	con=Connect.connectDb();
                pst=con.prepareStatement(sql);
                rs=pst.executeQuery();
                int i=0,j = 0,count=0;
            
            while (rs.next()) {
                 if(!rs.getString(8).contains("staff")){
                    FeesModel m = new FeesModel();
                    double dif;
                    try{
                    double totFee= Double.parseDouble(rs.getString(6).split("_")[4].replace("rs", ""));
                        if(rs.getString("status").contains("no")){
                        dif=totFee-Double.parseDouble(rs.getString(7));
                        }else{
                            try{
                                Double inDis=Double.parseDouble(rs.getString("status"))+Double.parseDouble(rs.getString(7));
                                dif=totFee-inDis;
                            }catch(Exception e){
                                dif=totFee-Double.parseDouble(rs.getString(7));
                            }
                        }
                   }catch(Exception e){
                      dif=0;
                   }
                   if(dif<=0){
                       
                   }else{
                        m.setId(rs.getString(1));                   
                                 
                
                        m.setGymPlan(rs.getString(3));
                
                        m.setMonths(String.valueOf(dif)+" Discount:"+rs.getString("status"));
                
                        m.setAmount(rs.getString(6));
                        count++;
                        data.add(m);
                   }
//                pst2.close();
//                rs2.close();
                
            }  
            }
            remPay.setText(String.valueOf(count));
          //  rs.next();
            tbData.setItems(data);
         
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
   
    public void feesPaidData(){        
   ObservableList<FeesModel> data;
    data=FXCollections.observableArrayList();
        String sql="Select id,membership_no,full_name,reg_date,contact_no,plan,paid_fee,discription,status from members";
   DateTime.setText("Paid Fees");
   varifyMode.setText("Plan");
   
        try{
        	con=Connect.connectDb();
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            int i=0,j = 0,count=0;
            
            while (rs.next()) {
                if(!rs.getString(8).contains("staff")){
                   FeesModel m = new FeesModel();
                   double dif;
                   
                   try{
                       
                      
                   double totFee= Double.parseDouble(rs.getString(6).split("_")[4].replace("rs", ""));
                    if(rs.getString("status").contains("no")){
                   dif=totFee-Double.parseDouble(rs.getString(7));
                    }else{
                            try{
                                Double inDis=Double.parseDouble(rs.getString("status"))+Double.parseDouble(rs.getString(7));
                                dif=totFee-inDis;
                            }catch(Exception e){
                                dif=totFee-Double.parseDouble(rs.getString(7));
                            }
                    }
                   }catch(Exception e){
                       dif=0;
                           //  Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e);
     
                       
                   }
                   if(dif<=0){
                       m.setId(rs.getString(1));                   
                                 
                
                m.setGymPlan(rs.getString(3));
                
                m.setMonths(rs.getString(7)+" Discount:"+rs.getString("status"));
                
                m.setAmount(rs.getString(6));
                count++;
                 data.add(m);
                   }
//                pst2.close();
//                rs2.close();
               
            }  
            }
            donePay.setText(String.valueOf(count));
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
        // stuff here
        
             
    }
    
    public void getDataFromMachine2(){
      
    }
  
}
