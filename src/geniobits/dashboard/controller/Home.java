package geniobits.dashboard.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import classes.Main;
import utils.MyPreloader;
import com.sun.javafx.application.LauncherImpl;
import java.awt.AWTException;
import java.awt.MediaTracker;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import utils.Connect;

/**
 *
 * @author Shubham Shirse
 */
public class Home extends Application {
    
     private double xOffset = 0;
    private double yOffset = 0;
    public String Hitory_User;
    private Connection con;
    private PreparedStatement pst;
    private boolean firstTime;
    private TrayIcon trayIcon;
    Home(String Hitory_User) {
        this.Hitory_User=Hitory_User;
         //To change body of generated methods, choose Tools | Templates.
    }

    public Home() {
         //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void start(Stage stage) throws Exception {
              
         Scene scene;
                     Parent root = FXMLLoader.load(getClass().getResource("/geniobits/dashboard/controller/view/Home.fxml"));
                       stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/geniobits/dashboard/images/logo.png")));
                    stage.initStyle(StageStyle.TRANSPARENT);
                     Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            //primaryStage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());
                   
                    
                    //stage.initStyle(StageStyle.UNDERDECORATED);
       
       //grab your root here
             root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        
        //move around here
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
        scene=new Scene(root);
       // createTrayIcon(stage);
       firstTime = true;
        Platform.setImplicitExit(false);
        stage.setResizable(true);
        stage.setScene(scene);
         stage.show();
    }
    
    public void createTrayIcon(final Stage stage) {
        if (SystemTray.isSupported()) {
            // get the SystemTray instance
            SystemTray tray = SystemTray.getSystemTray();
            // load an image
            java.awt.Image image = null;
            try {
               File pathToFile = new File(System.getProperty("user.dir")+"/src/geniobits/dashboard/images"+"/logo.png");
                image = ImageIO.read(pathToFile);
            } catch (IOException ex) {
                System.out.println(ex);
            }


            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    hide(stage);
                }
            });
            // create a action listener to listen for default action executed on the tray icon
            final ActionListener closeListener = new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    System.exit(0);
                }
            };

            ActionListener showListener = new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            stage.show();
                        }
                    });
                }
            };
            // create a popup menu
            PopupMenu popup = new PopupMenu();

            MenuItem showItem = new MenuItem("Show");
            showItem.addActionListener(showListener);
            popup.add(showItem);

            MenuItem closeItem = new MenuItem("Close");
            closeItem.addActionListener(closeListener);
            popup.add(closeItem);
            /// ... add other items
            // construct a TrayIcon
            trayIcon = new TrayIcon(image, "FitBit", popup);
            // set the TrayIcon properties
            trayIcon.addActionListener(showListener);
            // ...
            // add the tray image
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println(e);
            }
            // ...
        }
    }
    
   public void showProgramIsMinimizedMsg() {
        if (firstTime) {
            trayIcon.displayMessage("FitBit",
                    "FitBit Minimize To Tray",
                    TrayIcon.MessageType.INFO);
            firstTime = false;
        }
    }

    private void hide(final Stage stage) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (SystemTray.isSupported()) {
                    stage.hide();
                    showProgramIsMinimizedMsg();
                } else {
                    System.exit(0);
                }
            }
        });
    }

    
    @Override
   public void init() throws Exception {
      // Do some heavy lifting
        
       Stage stage = new Stage();
       start(stage);
      
      
   }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        LauncherImpl.launchApplication(
                Home.class, MyPreloader.class, args);
 
         try {
              UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
          } catch (ClassNotFoundException ex) {
              System.out.println(ex);
              Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
          } catch (InstantiationException ex) {
              System.out.println(ex);
              Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
          } catch (IllegalAccessException ex) {
              System.out.println(ex);
              Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
          } catch (UnsupportedLookAndFeelException ex) {
              System.out.println(ex);
              Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
    
   
    

    
}
