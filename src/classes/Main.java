package classes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import geniobits.dashboard.controller.HomeController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.Connect;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static classes.config.getID;

//import service.QuartzManager;
//import service.ZKKQJob;
//import service.uploadService;
//import uitl.ZkemSDK;

/**
 *
 * @author Shubham Shirse
 */
public class Main extends Application {
    
      private double xOffset = 0;
    private double yOffset = 0;
    private Connection con;
    private PreparedStatement pst;
    
    private Thread sendToMachine;
    private boolean isOk;
    private String response="false";
    private String hash;
    private String salt;
    
    
     ResultSet rs=null;
     
     
    @Override
    public void start(Stage stage) throws Exception {
       
        
      Parent root = FXMLLoader.load(getClass().getResource("/geniobits/dashboard/controller/view/Login.fxml"));
      stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/geniobits/dashboard/images/logo.png")));
      stage.initStyle(StageStyle.UNDECORATED);
      stage.setMaximized(false);

        //grab your root here
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        //sorry about that - Windows defender issue.
        //move around here
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        Scene scene = new Scene(root);
        stage.setScene(scene);

        if(true){
        stage.show();
        }else{
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Error");
                alert.setContentText("Check your INTERNET connection working. CHECK YOUR EMAIL for Verification or contact SUPPORT.");
                alert.showAndWait();
             System.out.println("in else");
            System.exit(0);
        }
        
    }
    
    @Override
     public void init(){
        getLisense();
        
        upload();
        if(!isSent()){
            birthDay();
            memEndDay();
            System.out.println("sent messages");
            memEndTwoDay();
            
            try{    
                                       Date today = new Date();
                                        today = Calendar.getInstance().getTime();
                                        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
                                        String date=format.format(today);
                                                    con=Connect.connectDb();
                                                    String sql11="Insert into messages_sent(tdate) values(?)";
                                                    pst=con.prepareStatement(sql11);
                                                    pst.setString(1, date);
                                                    pst.execute();
                                                    con.close();
                                                }catch(Exception e7){
                                                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e7);
                                                }
        }
       sendToMachine = new Thread(new Runnable() {
    public void run() {
         
         getDataFromMachine();
        
    }
         });
       
      
         sendToMachine.start();
     }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(Main.class, args);
          try {
              UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
          } catch (ClassNotFoundException ex) {
              Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
          } catch (InstantiationException ex) {
              Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
          } catch (IllegalAccessException ex) {
              Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
          } catch (UnsupportedLookAndFeelException ex) {
              Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
          }
//          ZKKQJob job = new ZKKQJob();
//		String job_name ="zkkkq_job"; 
//		try {  
//			
//            QuartzManager.addJob(job_name,job,"0 0/30 * 1/1 * ? *");
//		}catch (Exception e) {
//			System.out.println(e);
//		}
         
        
    }
    
    public void getDataFromMachine() {
        // stuff here
      
    }
    
    
    public void upload(){
            try {


                HttpURLConnection conn = null;
                DataOutputStream dos = null;
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";
                int bytesRead, bytesAvailable, bufferSize;
                byte[] buffer;
                int maxBufferSize = 1 * 1024 * 1024;
                String sourceFileUri = System.getProperty("user.dir")+"/"+getID()+"_gym_mgmt_system.db";
                File sourceFile = new File(sourceFileUri);

               

                    try {
                        String upLoadServerUri = "http://gym.geniobits.com/csvUpload.php";

                        // open a URL connection to the Servlet
                        FileInputStream fileInputStream = new FileInputStream(
                                sourceFile);
                        URL url = new URL(upLoadServerUri);

                        // Open a HTTP connection to the URL
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true); // Allow Inputs
                        conn.setDoOutput(true); // Allow Outputs
                        conn.setUseCaches(false); // Don't use a Cached Copy
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Connection", "Keep-Alive");
                        conn.setRequestProperty("ENCTYPE",
                                "multipart/form-data");
                        conn.setRequestProperty("Content-Type",
                                "multipart/form-data;boundary=" + boundary);
                        conn.setRequestProperty("bill", sourceFileUri);

                        dos = new DataOutputStream(conn.getOutputStream());

                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"bill\";filename=\""
                                + sourceFileUri + "\"" + lineEnd);

                        dos.writeBytes(lineEnd);

                        // create a buffer of maximum size
                        bytesAvailable = fileInputStream.available();

                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        buffer = new byte[bufferSize];

                        // read file and write it into form...
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                        while (bytesRead > 0) {

                            dos.write(buffer, 0, bufferSize);
                            bytesAvailable = fileInputStream.available();
                            bufferSize = Math
                                    .min(bytesAvailable, maxBufferSize);
                            bytesRead = fileInputStream.read(buffer, 0,
                                    bufferSize);

                        }

                        // send multipart form data necesssary after file
                        // data...
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + twoHyphens
                                + lineEnd);

                        // Responses from the server (code and message)
                        int serverResponseCode = conn.getResponseCode();
                        String serverResponseMessage = conn
                                .getResponseMessage();

                        if (serverResponseCode == 200) {

                            // messageText.setText(msg);
                            //Toast.makeText(ctx, "File Upload Complete.",
                            //      Toast.LENGTH_SHORT).show();

                            // recursiveDelete(mDirectory1);

                        }else{
                            scheduleJob();
                        }

                        // close the streams //
                        fileInputStream.close();
                        dos.flush();
                        dos.close();

                    } catch (Exception e) {

                        // dialog.dismiss();
                       System.out.println(e);
                        scheduleJob();
                    }
                    // dialog.dismiss();

                // End else block


            } catch (Exception ex) {
                // dialog.dismiss();
                scheduleJob();
 System.out.println(ex);
                ex.printStackTrace();
            }

}

    private void scheduleJob() {
//         uploadService job = new uploadService();
//		String job_name ="upload_job"; 
//		try {  
//			
//            QuartzManager.addJob(job_name,job,"0 0 0/5 1/1 * ? *");
//		}catch (Exception e) {
//			System.out.println(e);
//		}
        
         }
    
    public void getLisense(){
         InetAddress ip;
         String Mac;
        try {
            ip = InetAddress.getLocalHost();

            System.out.println("The mac Address of this machine is :" + ip.getHostAddress());

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();

            System.out.print("The mac address is : ");

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++){
                sb.append(String.format("%02X%s", mac[i],(i< mac.length - 1)?"-":""));
            }

            System.out.println(sb.toString());
            Mac=sb.toString();
            String id=getID();
            String salt="#best@geniobits";
            Mac=SHA1(Mac);
            String hash = SHA1(id+Mac+salt);
            String urlParameters =
        "id=" + URLEncoder.encode(id, "UTF-8") +
        "&lkey=" + URLEncoder.encode(Mac, "UTF-8")+
        "&hash=" + URLEncoder.encode(hash, "UTF-8");
            String response=executePost("http://gym.geniobits.com/lisense.php",urlParameters);
            System.out.println(response);
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        
        
        
    }
    
    public boolean checkLisense() throws NoSuchAlgorithmException, UnsupportedEncodingException{
        InetAddress ip;
         String Mac;
        try {
            ip = InetAddress.getLocalHost();

            System.out.println("The mac Address of this machine is :" + ip.getHostAddress());

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();

            System.out.print("The mac address is : ");

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++){
                sb.append(String.format("%02X%s", mac[i],(i< mac.length - 1)?"-":""));
            }
            System.out.println(sb.toString());
            Mac=sb.toString();
            String id=getID();
          salt="#best@geniobits";
            Mac=SHA1(Mac);            
            hash = SHA1(id+Mac+salt);
            String urlParameters =
        "id=" + URLEncoder.encode(id, "UTF-8") +
        "&lkey=" + URLEncoder.encode(Mac, "UTF-8")+
        "&hash=" + URLEncoder.encode(hash, "UTF-8");
           response=executePost("http://gym.geniobits.com/clisense.php",urlParameters);     
           System.out.println("response: "+response);
        } 
        catch (Exception e) {
            e.printStackTrace();
            response="false";            
        } 
        System.out.println(response);
            if(response.contains(SHA1("yes"+salt+hash))){               
                return true;
            }else{
                return false;
            }
    }
    
    public static String executePost(String targetURL, String urlParameters) {
  HttpURLConnection connection = null;

  try {
    //Create connection
    URL url = new URL(targetURL);
    connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("POST");
    connection.setRequestProperty("Content-Type", 
        "application/x-www-form-urlencoded");

    connection.setRequestProperty("Content-Length", 
        Integer.toString(urlParameters.getBytes().length));
    connection.setRequestProperty("Content-Language", "en-US");  

    connection.setUseCaches(false);
    connection.setDoOutput(true);

    //Send request
    DataOutputStream wr = new DataOutputStream (
        connection.getOutputStream());
    wr.writeBytes(urlParameters);
    wr.close();

    //Get Response  
    InputStream is = connection.getInputStream();
    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
    String line;
    while ((line = rd.readLine()) != null) {
      response.append(line);
      response.append('\r');
    }
    rd.close();
    return response.toString();
  } catch (Exception e) {
    e.printStackTrace();
    return "cantconnect";
  } finally {
    if (connection != null) {
      connection.disconnect();
    }
  }
}
    
    
    public String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] textBytes = text.getBytes("iso-8859-1");
        md.update(textBytes, 0, textBytes.length);
        byte[] sha1hash = md.digest();
       
        return convertToHex(sha1hash);
    }
    private String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }
    
    
    public void memEndDay(){        
         String sql="Select type,sub,body from messages";
         String body="Your Membership with gym is ended today. Please renew your gym membership to continue gym.\n Thank you!";
                                        String sub="Gym Membership Ended";
                                        try{
                                                con=Connect.connectDb();
                                            pst=con.prepareStatement(sql);
                                            rs=pst.executeQuery();
                                            int i=0,j = 0,count=0;

                                            while (rs.next()) {

                                                   if(rs.getString("type").equals("end")){
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
                                               Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e);
                                            }

                                            try{
                                                pst.close();
                                                        rs.close();
                                                        con.close();
                                            }catch(Exception e){

                                            }
            Date today = new Date();
            today = Calendar.getInstance().getTime();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            String date=format.format(today);
            System.out.println(date);
   
        String sql2="Select id,full_name,end_date,contact_no,plan,paid_fee,email,machine from members where end_date='"+date+"'";
        String names="";  
        try{
        	con=Connect.connectDb();
            pst=con.prepareStatement(sql2);
            rs=pst.executeQuery();
              
            while (rs.next()) {
                
               System.out.println(rs.getString(1));
            
//                emailClass wt = new emailClass("Hii "+rs.getString(2)+",\n"+body+"\n Your Membership with gym is ending on"+ date +". Please renew your gym membership to continue gym.\n Thank you!",  rs.getString(7),sub);
//                String temp = wt.sendCampaign();
//                System.out.println(temp);
                
//                 smsClass wt2 = new smsClass(rs.getString(4), "Hii "+rs.getString(2)+body+" Your Membership with gym is ending on"+ date +". Please renew it.\n Thank you!", rs.getString(1));
//        
//                  String temp2 = wt2.sendCampaign();
                 names = names+","+rs.getString(2);
                    
                            try{
                                deleteFromMachine(rs.getString(8),rs.getString(1),rs.getString(2));
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
      
        finally{
        try{
            pst.close();
 		    rs.close();
 		    con.close();
        }catch(Exception e){
            
        }
         }
       if(names!=""){
                                        Date det=new Date();
					SimpleDateFormat format2=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
					String date2=format2.format(det);
                                                try{
                                                    con=Connect.connectDb();
                                                
                                                String msz="End regestration message and Email sent to "+ names;
						String sql11="Insert into history(date,task) values(?,?)";
						pst=con.prepareStatement(sql11);
						pst.setString(1, date2);
						pst.setString(2, msz);
						pst.execute();
                                                con.close();
                                                }catch(Exception e7){
                                                    
                                                }
       }
        
	}
    
     public void birthDay(){        
         String sql="Select type,sub,body from messages";
         String body="Many Many happy returns of the day";
                                        String sub="Happy Birthday!";
                                        try{
                                                con=Connect.connectDb();
                                            pst=con.prepareStatement(sql);
                                            rs=pst.executeQuery();
                                            int i=0,j = 0,count=0;

                                            while (rs.next()) {

                                                   if(rs.getString("type").equals("DOB")){
                                                       if(rs.getString("body")!=""){
                                                       body=rs.getString("body");
                                                       sub=rs.getString("sub");}
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
            Date today = new Date();
            today = Calendar.getInstance().getTime();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            String date=format.format(today);
            System.out.println(date);
   
        String sql2="Select id,full_name,end_date,contact_no,plan,paid_fee,email,machine from members where dob='"+date+"'";
        String names="";
        try{
        	con=Connect.connectDb();
            pst=con.prepareStatement(sql2);
            rs=pst.executeQuery();
  
            while (rs.next()) {
                
               System.out.println(rs.getString(1));
            
//                emailClass wt = new emailClass("Hii "+rs.getString(2)+",\n"+body, rs.getString(7),sub);
//                String temp = wt.sendCampaign();
//                System.out.println(temp);
                
//                 smsClass wt2 = new smsClass(rs.getString(4), "Hii "+rs.getString(2)+", "+body, rs.getString(1));
//        
//                  String temp2 = wt2.sendCampaign();
//                
                 names= names+","+rs.getString(2);
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
        try{
            pst.close();
 		    rs.close();
 		    con.close();
        }catch(Exception e){
            
        }
        }
                                        if(names!=""){
                                        Date det=new Date();
					SimpleDateFormat format2=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
					String date2=format2.format(det);
                                                try{
                                                    con=Connect.connectDb();

                                                    String msz="Birthday wishes message and Email sent to "+ names;
                                                    String sql11="Insert into history(date,task) values(?,?)";
                                                    pst=con.prepareStatement(sql11);
                                                    pst.setString(1, date2);
                                                    pst.setString(2, msz);
                                                    pst.execute();
                                                    con.close();
                                                }catch(Exception e7){
                                                    
                                                }
                                        }
	}
   
   
   public void memEndTwoDay(){    
       String sql="Select type,sub,body from messages";
         String body="Your Membership with gym is ended today. Please renew your gym membership to continue gym.\n Thank you!";
                                        String sub="Gym Membership Ended";
                                        try{
                                                con=Connect.connectDb();
                                            pst=con.prepareStatement(sql);
                                            rs=pst.executeQuery();
                                            int i=0,j = 0,count=0;

                                            while (rs.next()) {

                                                   if(rs.getString("type").equals("end")){
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
                                               Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e);
                                            }

                                            try{
                                                pst.close();
                                                        rs.close();
                                                        con.close();
                                            }catch(Exception e){

                                            }
   Date today = new Date();
   today = Calendar.getInstance().getTime();
   SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
   String date=format.format(today);
   System.out.println(date);
   String sepdate[]=date.split("-");
   int sepday=Integer.parseInt(sepdate[2])+2;
   String day;
   if(sepday<10){
       day="0"+String.valueOf(sepday);
   }else{
      day=String.valueOf(sepday);
   }
        String sql2="Select id,full_name,end_date,contact_no,plan,paid_fee,email,machine from members where end_date='"+sepdate[0]+"-"+sepdate[1]+"-"+day+"'";
String names="";
        try{
        	con=Connect.connectDb();
            pst=con.prepareStatement(sql2);
            rs=pst.executeQuery();
  
            while (rs.next()) {
                
               
               System.out.println(rs.getString(1));
//                emailClass wt = new emailClass("Hii \n"+rs.getString(2)+body+"\n Your Membership with gym is ending on"+ sepdate[0]+"-"+sepdate[1]+"-"+day +". Please renew your gym membership to continue gym.\n Thank you!", rs.getString(7),sub);
//                String temp = wt.sendCampaign();
//                
//                 smsClass wt2 = new smsClass(rs.getString(4), "Hii "+rs.getString(2)+body+" Your Membership with gym is ending on"+ sepdate[0]+"-"+sepdate[1]+"-"+day +". Please renew your gym membership to continue gym. Thank you!", rs.getString(1));
//        
//                  String temp2 = wt2.sendCampaign();
                  names=names+","+rs.getString(2);
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
        try{
            pst.close();
 		    rs.close();
 		    con.close();
        }catch(Exception e){
            
        }
        }
        if(names!=""){
         Date det=new Date();
					SimpleDateFormat format2=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
					String date2=format2.format(det);
                                                try{
                                                    con=Connect.connectDb();
                                                
                                                String msz="Membership end message and Email sent to "+ names;
						String sql11="Insert into history(date,task) values(?,?)";
						pst=con.prepareStatement(sql11);
						pst.setString(1, date2);
						pst.setString(2, msz);
						pst.execute();
                                                con.close();
                                                }catch(Exception e7){
                                                    
                                                }
        }
	}
   
   public void deleteFromMachine(String Mnum,String id,String name) {
        // stuff here
      
       
    }

    private boolean isSent() {
        Date today = new Date();
            today = Calendar.getInstance().getTime();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            String date=format.format(today);
            System.out.println(date);
            String sql2="Select * from messages_sent where tdate='"+date+"'";
            Boolean sent=false;
            try{
        	con=Connect.connectDb();
                pst=con.prepareStatement(sql2);
                rs=pst.executeQuery();

                while (rs.next()) {
                    sent=true;
                   
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
        try{
            pst.close();
 		    rs.close();
 		    con.close();
        }catch(Exception e){
            
        }
        }
            return sent;
    }
}
