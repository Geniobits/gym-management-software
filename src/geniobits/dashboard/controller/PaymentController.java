/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geniobits.dashboard.controller;

import classes.AutocompletionlTextField;
import classes.Search_Member;
import geniobits.dashboard.model.MembersModel;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;
import utils.Connect;

/**
 * FXML Controller class
 *
 * @author DESTINY
 */

    

public class PaymentController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField txtName;
    
    @FXML
    private AutocompletionlTextField txtMemNum;
    
     @FXML
    private TextField txtEmail;
     
    @FXML
    private TextField txtContact;
    
    @FXML
    private TextField txtAddress;
    
    @FXML
    private TextField txtPlan;
        
    @FXML
    private TextField txtTotalFees;
    
    @FXML
    private DatePicker dateReg;
    
    @FXML
    private DatePicker dateStart;
    
    @FXML
    private DatePicker dateEnd;
    
    @FXML
    private TextField txtPaid;
    
     @FXML
    private TextField txtDiscount;
    
    @FXML
    private TextField txtNowPaid;
    
    @FXML
    private Button btnCheck;
    
     @FXML
    private Button btnSave;
     
      Connection con=null;
     ResultSet rs=null;
     PreparedStatement pst=null;
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtName.setDisable(true);
        txtEmail.setDisable(true);
        dateStart.setDisable(true);
        txtPaid.setDisable(true);
        dateEnd.setDisable(true);
        dateReg.setDisable(true);
        txtTotalFees.setDisable(true);
        txtContact.setDisable(true);
        txtPlan.setDisable(true);
         txtAddress.setDisable(true);
         
         buildData();
        
        
    }    
    
     @FXML
    private void handleButtonAction(MouseEvent event) {        
       
        if (event.getSource() == btnCheck) {
            
            btnCheckAction();
        }else if (event.getSource() == btnSave) {
           btnSaveAction();
        }
    }

    private void btnCheckAction() {
    try {
		
					con=Connect.connectDb();
					String sql="Select * from members where id="+txtMemNum.getText().split(" ")[0]+"";
					pst=con.prepareStatement(sql);
					rs=pst.executeQuery();
					rs.next();
					{
						txtName.setText(rs.getString("full_name"));
						txtName.setDisable(true);
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		        	  
                                                Date today = formatter.parse(rs.getString("reg_date"));
                                                Instant instant = Instant.ofEpochMilli(today.getTime());
                                                LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                                                LocalDate localDate = localDateTime.toLocalDate();

						dateReg.setValue(localDate);                                             
						dateReg.setDisable(true);
                                                
						//reg_Date
                                                txtDiscount.setText(rs.getString("status"));
                                                 today = formatter.parse(rs.getString("start_date"));
                                               instant = Instant.ofEpochMilli(today.getTime());
                                                localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                                                localDate = localDateTime.toLocalDate();

						dateStart.setValue(localDate);
						dateStart.setDisable(true);
                                                
                                                today = formatter.parse(rs.getString("end_date"));
                                               instant = Instant.ofEpochMilli(today.getTime());
                                                localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                                                localDate = localDateTime.toLocalDate();
						dateEnd.setValue(localDate);;
						dateEnd.setDisable(true);
						
                                                txtEmail.setText(rs.getString(9));
						txtEmail.setDisable(true);
                                                txtAddress.setText(rs.getString(5));
						txtAddress.setDisable(true);
                                                
                                                txtContact.setText(rs.getString(17));
						txtContact.setDisable(true);
                                                
                                                txtPlan.setText(rs.getString(15));
						txtPlan.setDisable(true);
                                                txtPaid.setText(rs.getString(16));
						txtPaid.setDisable(true);
                                                String tfees=rs.getString(15);
                                                String[] items=tfees.split("_");
                                txtTotalFees.setText(items[4]);
                                txtTotalFees.setDisable(true);               																					
			        }					
				} catch (Exception e1) {
					//JOptionPane.showMessageDialog(null, e1
                                                 Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e1);
				}   
    }

    private void btnSaveAction() {
        
        String sql="update members set paid_fee=? , status=? where id="+txtMemNum.getText().split(" ")[0]+"";
			double fee=Double.parseDouble(txtPaid.getText().replace("rs", ""))+Double.parseDouble(txtNowPaid.getText());
                        try{
                        pst = con.prepareStatement(sql);
					pst.setDouble(1,fee);
                                        pst.setString(2,txtDiscount.getText());
                                        pst.execute();
                                        
                                        String UserName= "admin";
                                        String id="0";
                                                try{
                                                String sql1="SELECT * from user";
						pst=con.prepareStatement(sql1);
		                                rs=pst.executeQuery();
                                                
                                                UserName= rs.getString(3);
                                                id= rs.getString(1);
                                                }catch(Exception e7){
                                                    
                                                }
                                                Date det=new Date();
					SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
					String date=format.format(det);
                                                String msz="Member "+txtName.getText()+" is paid "+txtNowPaid.getText()+" to "+UserName+"("+id+")";
						String sql1="Insert into history(date,task) values(?,?)";
						pst=con.prepareStatement(sql1);
						pst.setString(1, date);
						pst.setString(2, msz);
						pst.execute();
                                        JOptionPane.showMessageDialog(null, "Success");
                                        
                        }catch(Exception e){
                                              Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e);
				
                                           JOptionPane.showMessageDialog(null, e);      
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
                
                m.setContactNum(contact_no);
                
                String email = rs.getString(6);   
                m.setEmail(email);
                txtMemNum.getEntries().add(membership_no+" "+full_name+" "+contact_no+" "+email);
                
             
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
    
}
