/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geniobits.dashboard.controller;



import classes.Main;

import com.toedter.calendar.JDateChooser;
import geniobits.dashboard.model.MembersModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import utils.Connect;
/**
 *
 * @author DESTINY
 */



public class EditMembersController implements Initializable {
    
    @FXML
    private TextField txtmemberno;
    @FXML
    private Label lblUpdate;
    @FXML
	  private TextField txtEmail;
	@FXML 
	  private TextField textContact;
        @FXML
	  private TextField txtPaidfee;
	@FXML 
        private TextField txtAddress;
	@FXML
        private TextField txtFullname;
	
	@FXML
        private ChoiceBox cmbxGender;
	@FXML
        private TextField txtDiscription ;
        
        @FXML
        private TextField txtDiscount ;
        
	@FXML
        private DatePicker startDate;
	@FXML
        private DatePicker registeredDate;
	@FXML
        private DatePicker endDate;
	@FXML
        private DatePicker dob;
	@FXML
        private ChoiceBox cmbxMachineNumber;
        @FXML
        private ChoiceBox cmbxPlan;
         @FXML
        private ChoiceBox cmbxDuration;
	@FXML
        private ChoiceBox cmbxFeesmode;
  
    @FXML
    private Button chhose;
     @FXML
    private Button close;
     
    
     private double xOffset = 0;
    private double yOffset = 0;
    
    @FXML
    private Button btnSave;

      @FXML
    private ImageView image;
    
      private File selectedFile;
      
      Connection con=null;
	 ResultSet rs=null;
	 PreparedStatement pst=null;
	 
	 private String updatedMember;
         
         private String filename;
	 private String gender=null;
	 private String contryVal=null;
   
	 
         
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cmbxGender.getItems().addAll("Male","Female");
        cmbxFeesmode.getItems().addAll("Monthly", "Quartly","Half Yearly","Yearly");
        cmbxMachineNumber.getItems().addAll("1","2","Both");
        Date input = new Date();
        LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();	        	 
	startDate.setValue(date);
        registeredDate.setValue(date);
        txtDiscount.setText("no");
                      
                   String sql="Select * from fees";
   
        try{
        	con=Connect.connectDb();
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            while (rs.next()) {
                cmbxDuration.getItems().add(rs.getString(2)+"_for_"+rs.getString(3)+"_months_"+rs.getString(4)+"rs");
            }
            rs.close();
            pst.close();
            con.close();
        }catch(Exception e){
            
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
        try{
                con=Connect.connectDb();		
		String sql1="Select id from members";
		pst=con.prepareStatement(sql1);
		rs=pst.executeQuery();
		int b=0;
		while(rs.next())
		{
			b=rs.getInt("id");
		}
		b=b+1;
		String bb=String.valueOf(b);
		txtmemberno.setText(bb);
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
                    //    Logger.getLogger(Add_Product.class.getName()).log(Level.SEVERE, null, e);
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
		cmbxDuration.valueProperty().addListener(new ChangeListener<String>() {        
                  @Override
                   public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
				String item=cmbxDuration.getSelectionModel().getSelectedItem().toString();
				String[] items=item.split("_");
                                System.out.println(items[2]);
					LocalDate ldate=startDate.getValue();
                                        Date d = Date.from(ldate.atStartOfDay(ZoneId.systemDefault()).toInstant());
					int month=d.getMonth();
                                        
					month=month+Integer.valueOf(items[2]);
					int day=d.getDay();
					day=day+4;
					d.setMonth(month);
					 LocalDate dates = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();	        	 
					endDate.setValue(dates);
					
					
			}
		});
                
                                  
    }  
    
     @FXML
    private void handleButtonAction(MouseEvent event) throws FileNotFoundException {        
       
        if (event.getSource() == btnSave) {
           // if(btnSave.getText().equals("Save")){
             btnSaveAction();
           
           // btnCheckAction();
        }else if (event.getSource() == chhose) {
           
        FileChooser fileChooser = new FileChooser();

        Stage stage = new Stage();
       
        selectedFile = fileChooser.showOpenDialog(stage);
        
        image.setImage(new javafx.scene.image.Image(new FileInputStream(selectedFile.getAbsolutePath())));
          //btnSaveAction();
        }else if(event.getSource() == close){
             Stage stage = (Stage) close.getScene().getWindow();
  // do what you have to do
  stage.close();          
            
        }
    }
    
    /**
	 * Create the frame.
	 */
	public void updating(String idno)
	{       
             
            
	       try {
                    
                     
                btnSave.setText("Update");
                lblUpdate.setText("Update Member");
		con=Connect.connectDb();
		 String sql="select * from members where id='"+idno+"'";
		    	pst=con.prepareStatement(sql);
		        rs=pst.executeQuery();
		        if(rs.next())
		        {
		        	
		        	  txtmemberno.setText(rs.getString("id"));
		        	  txtFullname.setText(rs.getString("full_name"));
		        	  txtEmail.setText(rs.getString("email"));
                                  cmbxGender.getSelectionModel().select(rs.getString("gender"));
                                  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        	  txtAddress.setText(rs.getString("address"));
                                  LocalDate localDate = LocalDate.parse(rs.getString("start_date"), formatter);
		        	  startDate.setValue(localDate);
		        	  endDate.setValue(LocalDate.parse(rs.getString("end_date"),formatter));
		        	  registeredDate.setValue(LocalDate.parse(rs.getString("reg_date"),formatter));
                		  cmbxMachineNumber.getSelectionModel().select(rs.getString("machine"));
		        	  txtPaidfee.setText(rs.getString("paid_fee"));
                                  cmbxFeesmode.getSelectionModel().select(rs.getString("fees_mode"));
		        	  cmbxDuration.getSelectionModel().select(rs.getString("plan"));
		        	  txtDiscription.setText(rs.getString("discription"));
                                  try{
                                  txtDiscount.setText(rs.getString("status"));
                                  }  catch (Exception e) {
                                        e.printStackTrace();        
                                    }
		        	 dob.setValue(LocalDate.parse(rs.getString("dob").replace(" ", ""),formatter));
		        	  textContact.setText(rs.getString("contact_no"));
		        	  try{

                                       // Blob foto = rs.getBlob("img");
                                        InputStream is = rs.getBinaryStream("img");
                                        Image img = new Image(is) ; // false = no background loading
                                        is.close();
                                        image.setImage(img);
                                    } catch (Exception e) {
                                        e.printStackTrace();        
                                    }
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
		
		
		
	}
        
       
        
	
        
        Thread sendToMachine = new Thread(new Runnable() {
    public void run() {
        // stuff here
      
       
    }
});

    private void btnSaveAction() {
      //  if(txtFullname.getText()!=null || cmbxGender.getSelectionModel().getSelectedItem().toString() !=null || !textContact.getText().isEmpty() || !txtEmail.getText().isEmpty()){
				con=Connect.connectDb();
				String sql=null;
				if(btnSave.getText().equals("Save"))
				{
				sql="Insert into members(membership_no,full_name,gender,address,country,weight,"+
				"status,email,reg_date,start_date,end_date,fees_mode,discription,plan,paid_fee,"+
				"contact_no,dob,occupation,img,machine,id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				}
				else if(btnSave.getText().equals("Update"))
				{
				
					sql="update members set membership_no=?,full_name=?,gender=?,address=?,country=?,weight=?,"+ 
							"status=?,email=?,reg_date=?,start_date=?,end_date=?,fees_mode=?,discription=?,plan=?,paid_fee=?,"+ 
							"contact_no=?,dob=?,occupation=?,img=?,machine=? where id="+txtmemberno.getText()+"";
				}
				else {
					
				}
				try{    
					pst = con.prepareStatement(sql);
					pst.setString (1,txtmemberno.getText());   
					pst.setString (2, txtFullname.getText());
					pst.setString (3,cmbxGender.getSelectionModel().getSelectedItem().toString());
					pst.setString (4,txtAddress.getText());
					
					contryVal="India";
					pst.setString (5, contryVal);
					pst.setString (6, "70");
					
					String statusVal="no";	
					pst.setString (7, txtDiscount.getText());
					pst.setString (8, txtEmail.getText());
                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        	
					pst.setString (9, registeredDate.getValue().format(formatter));
					pst.setString (10, startDate.getValue().format(formatter));
					pst.setString (11, endDate.getValue().format(formatter));
					
					String feesModeVal=cmbxFeesmode.getSelectionModel().getSelectedItem().toString();				
					pst.setString (12, feesModeVal);
                                        String machinenum = cmbxMachineNumber.getSelectionModel().getSelectedItem().toString();
					pst.setString (13, txtDiscription.getText());
                                        String durationVal="basic_1000rs";
					try{
					durationVal=cmbxDuration.getSelectionModel().getSelectedItem().toString();
                                        }catch(Exception e22){
                                            
                                        }
					pst.setString(14, durationVal);
					pst.setFloat(15, Float.parseFloat(txtPaidfee.getText()));
					pst.setString(16, textContact.getText());
					pst.setString (17, dob.getValue().format(formatter));
					pst.setString(18, "person");
                                        pst.setString(20, machinenum);
                                        if(btnSave.getText().equals("Save"))
				{
                                        pst.setString(21, txtmemberno.getText());
                                }
					
					if(selectedFile!=null)
		            {
					FileInputStream fis=new FileInputStream(selectedFile);
		            pst.setBinaryStream(19,fis,(int)selectedFile.length());
                            }else{
                                               //   Logger.getLogger(Add_Product.class.getName()).log(Level.SEVERE, null, "selected file is null");
			    
                                        }
					pst.execute();
                                        
                                        try{
                                            
                                            sendToMachine.start();
                                            
                                        }catch(Exception e3){
                                            
                                        }
					
					Date det=new Date();
					SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
					String date=format.format(det);
					Home log=new Home();
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
					if(btnSave.getText().equals("Update"))
					{
                                             Stage stage = (Stage) btnSave.getScene().getWindow();
  // do what you have to do
  stage.close();
                                                        Alert alert = new Alert(AlertType.INFORMATION);
                                                        alert.setHeaderText("Succes");
                                                        alert.setContentText("Customer succesfully updated!");
                                                        alert.showAndWait();
                                                try{
                                            
                                            sendToMachine.start();
                                            
                                        }catch(Exception e3){
                                            
                                        }
                                                
						/* For Storing History in database*/
                                                
						String msz="Member "+txtFullname.getText()+" is updated by "+UserName+"("+id+")";
						String sql1="Insert into history(date,task) values(?,?)";
						pst=con.prepareStatement(sql1);
						pst.setString(1, date);
						pst.setString(2, msz);
						pst.execute();
                                                con.close();
					}
					else if(btnSave.getText().equals("Save"))
					{
                                           Stage stage = (Stage) btnSave.getScene().getWindow();
  // do what you have to do
                                        stage.close();
					Alert alert = new Alert(AlertType.INFORMATION);
                                        alert.setHeaderText("Succes");
                                        alert.setContentText("Customer succesfully created!");
                                        alert.showAndWait();
                                        String body="Your gym regestration successful";
                                        String sub="Gym Regestration Succesfull";
                                         String sql2="Select type,sub,body from messages";
                                        try{
                                                con=Connect.connectDb();
                                            pst=con.prepareStatement(sql2);
                                            rs=pst.executeQuery();
                                            

                                            while (rs.next()) {

                                                   if(!rs.getString("type").contains("reg")){
                                                   } else {
                                                       body=rs.getString("body");
                                                       sub=rs.getString("sub");
                                                    }
                                            }

                                                    pst.close();
                                                    rs.close();
                                                    con.close();

                                            }
                                            catch(Exception e)
                                            {
                                               Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, "1"+e);
                                            }

                                            try{
                                                pst.close();
                                                        rs.close();
                                                        con.close();
                                            }catch(Exception e){

                                            }
                                       
                                            final String body2=body.trim().replace("\n", "");
                                            final String sub2=sub.trim().replace("\n", "");;
                                          //  System.out.print(body2+sub2);
                                            Platform.runLater(new Runnable() {                          
                                            @Override
                                            public void run() {
                                                try{
//                                                     emailClass wt = new emailClass(body2+" You have selected "+cmbxDuration.getSelectionModel().getSelectedItem().toString()+" plan and paid amount of "+txtPaidfee.getText().toString()+"rs. Your plan will end on "+endDate.getValue().format(formatter).toString()+"." , txtEmail.getText().toString(),sub2);        
//                                                    String temp = wt.sendCampaign();
//                                           
//                                                    smsClass wt2 = new smsClass(textContact.getText().toString(),sub2+" "+body2+" You have selected "+cmbxDuration.getSelectionModel().getSelectedItem().toString()+" plan and paid amount of "+txtPaidfee.getText().toString()+"rs. Your plan will end on "+endDate.getValue().format(formatter).toString()+"." ,txtmemberno.getText().toString() );
//
//                                                    String temp2 = wt2.sendCampaign();
                                               }catch(Exception e){
                                                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e);
                                                }
                                            }
                                             });
                                            
//                                             con.close();
//                                        con=Connect.connectDb();
//                                        try{
//                                            
//                                            String msz="Member "+txtFullname.getText()+" is added by "+UserName+"("+id+")";
//                                            String sql1="Insert into history(date,task) values(?,?)";
//
//                                            pst=con.prepareStatement(sql1);
//                                            pst.setString(1, date);
//                                            pst.setString(2, msz);
//                                            pst.execute();
//                                            con.close();
//                                            
//                                        }catch(Exception e){
//                                               Logger.getLogger(Add_Product.class.getName()).log(Level.SEVERE, null, e);  
//                                        }
                                           
                                        try{
                                            
                                          //  sendToMachine.start();
                                            
                                        }catch(Exception e3){
                                            
                                        }
                                       
                                        
					}
					else {
						
					}
           		     }catch(NullPointerException e2){
                                  Alert fail= new Alert(AlertType.INFORMATION);
                                  fail.setHeaderText("Failure");
                                  fail.setContentText("You haven't typed something");
                                  fail.showAndWait();
                             }
			     catch(Exception e1)
			     {
			    	 Alert fail= new Alert(AlertType.INFORMATION);
                                  fail.setHeaderText("Failure");
                                  fail.setContentText("Something wrong happened");
                                  fail.showAndWait();
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
