/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geniobits.dashboard.controller;

import com.calendarfx.view.CalendarView;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utils.Connect;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author oXCToo
 */

public class HomeController implements Initializable {

    @FXML
    private Label label;

    private CalendarView calendar;

    @FXML
    private GridPane pnlHost;

    @FXML
    private VBox pnl_scroll;

    @FXML
    private Button close;

    @FXML
    private Button maximize;

    @FXML
    private Button minimize;

    @FXML
    private Button fullscreen;

    @FXML
    private JFXButton dashb;

    @FXML
    private JFXButton sdashb;

    @FXML
    private JFXButton fees;

    @FXML
    private JFXButton members;

    @FXML
    private JFXButton prod;

    @FXML
    private JFXButton reports;
    @FXML
    private JFXButton messages;

    @FXML
    private JFXButton history;
    @FXML
    private JFXButton paym;

    @FXML
    private JFXButton enq;

    @FXML
    private JFXButton logout;

    private double xOffset = 0;
    private double yOffset = 0;
    private double height = 0;
    private double width = 0;
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;


    @FXML
    private void handleButtonAction(MouseEvent event) {
        refreshNodes();
        if (event.getSource() == close) {
            System.exit(0);
        } else if (event.getSource() == dashb) {
            deselect();
            refreshNodes();
        } else if (event.getSource() == maximize) {
            Node node = (Node) event.getSource();
            Stage primaryStage = (Stage) node.getScene().getWindow();

            primaryStage.setWidth(width);
            primaryStage.setHeight(823.0);

            primaryStage.setX(xOffset);
            primaryStage.setY(yOffset);
        } else if (event.getSource() == minimize) {
            Node node = (Node) event.getSource();
            Stage primaryStage = (Stage) node.getScene().getWindow();
            primaryStage.setIconified(true);
        } else if (event.getSource() == fullscreen) {

            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            Node node = (Node) event.getSource();
            Stage primaryStage = (Stage) node.getScene().getWindow();
            xOffset = primaryStage.getX();
            yOffset = primaryStage.getY();
            height = primaryStage.getHeight();
            width = primaryStage.getWidth();
            primaryStage.setX(bounds.getMinX());
            primaryStage.setY(bounds.getMinY());
            //primaryStage.setWidth(bounds.getWidth());
            primaryStage.setHeight(bounds.getHeight());
        } else if (event.getSource() == sdashb) {
            deselect();
            sdashb.setStyle("-fx-background-color:#bfadf8;");
            sdashNodes();
        } else if (event.getSource() == fees) {
            deselect();
            fees.setStyle("-fx-background-color:#bfadf8;");
            String UserName = "admin2";
            String id = "0";
            try {
                con = Connect.connectDb();
                String sql1 = "SELECT * from user";
                pst = con.prepareStatement(sql1);
                rs = pst.executeQuery();

                UserName = rs.getString(2);
                id = rs.getString(1);
                pst.close();
                rs.close();
                con.close();
            } catch (Exception e7) {

            } finally {
                try {
                    pst.close();
                    rs.close();
                    con.close();
                } catch (Exception e) {

                }
            }
            if (UserName.equals("Admin")) {
                feesNodes();
            } else {
                JOptionPane.showMessageDialog(null, "Only Admin is allowed to perform this operation");
            }
        } else if (event.getSource() == members) {
            deselect();
            members.setStyle("-fx-background-color:#bfadf8;");
            membersNodes();
        } else if (event.getSource() == prod) {
            deselect();
            prod.setStyle("-fx-background-color:#bfadf8;");
            productNodes();
        } else if (event.getSource() == reports) {
            deselect();
            reports.setStyle("-fx-background-color:#bfadf8;");
            reportNodes();
        } else if (event.getSource() == messages) {
            deselect();
            messages.setStyle("-fx-background-color:#bfadf8;");
            messageNodes();
        } else if (event.getSource() == history) {
            deselect();
            history.setStyle("-fx-background-color:#bfadf8;");
            String UserName = "admin2";
            String id = "0";
            try {
                con = Connect.connectDb();
                String sql1 = "SELECT * from user";
                pst = con.prepareStatement(sql1);
                rs = pst.executeQuery();

                UserName = rs.getString(2);
                System.out.println(UserName);
                id = rs.getString(1);
                pst.close();
                rs.close();
                con.close();
            } catch (Exception e7) {
                System.out.println("homehis" + e7);
            } finally {
                try {
                    pst.close();
                    rs.close();
                    con.close();
                } catch (Exception e) {

                }
            }
            if (UserName.equals("Admin")) {
                historyNodes();
            } else {
                JOptionPane.showMessageDialog(null, "Only Admin is allowed to perform this operation");
            }
        } else if (event.getSource() == paym) {
            deselect();
            paym.setStyle("-fx-background-color:#bfadf8;");
            paymntNodes();
        } else if (event.getSource() == enq) {
            deselect();
            enq.setStyle("-fx-background-color:#bfadf8;");
            TimeTableNodes();
        } else if (event.getSource() == logout) {
            deselect();
            logout.setStyle("-fx-background-color:#bfadf8;");
            //close current stage
            Node node = (Node) event.getSource();
            Stage stage2 = (Stage) node.getScene().getWindow();
            stage2.close();
            try {
                //load FXML layout
                Parent root = FXMLLoader.load(getClass().getResource(
                        "/geniobits/dashboard/controller/view/Login.fxml"));
                //create scene
                Scene scene = new Scene(root);
                //create stage
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                System.out.println(e.toString());
            }

            //grab your root here

        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO


        refreshNodes();


    }

    private boolean firstTime;
    private TrayIcon trayIcon;

    private void deselect() {
        dashb.setStyle("-fx-background-color:#f0f8ff;");
        sdashb.setStyle("-fx-background-color:#f0f8ff;");
        prod.setStyle("-fx-background-color:#f0f8ff;");
        fees.setStyle("-fx-background-color:#f0f8ff;");
        members.setStyle("-fx-background-color:#f0f8ff;");
        reports.setStyle("-fx-background-color:#f0f8ff;");
        messages.setStyle("-fx-background-color:#f0f8ff;");
        history.setStyle("-fx-background-color:#f0f8ff;");
        paym.setStyle("-fx-background-color:#f0f8ff;");
        enq.setStyle("-fx-background-color:#f0f8ff;");
        logout.setStyle("-fx-background-color:#f0f8ff;");

    }

    private void refreshNodes() {


        pnl_scroll.getChildren().clear();

        // #bfadf8
        dashb.setStyle("-fx-background-color:#bfadf8;");


        Node[] nodes = new Node[15];

        for (int i = 0; i < 1; i++) {
            try {
                nodes[i] = (Node) FXMLLoader.load(getClass().getResource("view/Dashboard.fxml"));
                pnl_scroll.getChildren().add(nodes[i]);
                //   loadCalendar();

            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void membersNodes() {
        pnl_scroll.getChildren().clear();
        try {
            Node node = (Node) FXMLLoader.load(getClass().getResource("view/members.fxml"));
            pnl_scroll.getChildren().add(node);
            //   loadCalendar();

        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void messageNodes() {


        pnl_scroll.getChildren().clear();


        Node[] nodes = new Node[15];

        for (int i = 0; i < 1; i++) {
            try {
                nodes[i] = (Node) FXMLLoader.load(getClass().getResource("view/Message.fxml"));
                pnl_scroll.getChildren().add(nodes[i]);
                //   loadCalendar();

            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void TimeTableNodes() {


        pnl_scroll.getChildren().clear();


        Node[] nodes = new Node[15];

        for (int i = 0; i < 1; i++) {
            try {
                nodes[i] = (Node) FXMLLoader.load(getClass().getResource("view/Timetable.fxml"));
                pnl_scroll.getChildren().add(nodes[i]);
                //   loadCalendar();

            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }


    private void productNodes() {


        pnl_scroll.getChildren().clear();


        Node[] nodes = new Node[15];

        for (int i = 0; i < 1; i++) {
            try {
                nodes[i] = (Node) FXMLLoader.load(getClass().getResource("view/product.fxml"));
                pnl_scroll.getChildren().add(nodes[i]);
                //   loadCalendar();

            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void feesNodes() {


        pnl_scroll.getChildren().clear();


        Node[] nodes = new Node[15];

        for (int i = 0; i < 1; i++) {
            try {
                nodes[i] = (Node) FXMLLoader.load(getClass().getResource("view/fees.fxml"));
                pnl_scroll.getChildren().add(nodes[i]);
                //   loadCalendar();

            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void createTrayIcon(final Stage stage) {
        if (SystemTray.isSupported()) {
            // get the SystemTray instance
            SystemTray tray = SystemTray.getSystemTray();
            // load an image
            java.awt.Image image = null;
            try {
                File pathToFile = new File(System.getProperty("user.dir") + "/src/geniobits/dashboard/images" + "/logo.png");
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

    private void historyNodes() {


        pnl_scroll.getChildren().clear();


        Node[] nodes = new Node[15];

        for (int i = 0; i < 1; i++) {
            try {
                nodes[i] = (Node) FXMLLoader.load(getClass().getResource("view/history.fxml"));
                pnl_scroll.getChildren().add(nodes[i]);
                //   loadCalendar();

            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void paymntNodes() {


        pnl_scroll.getChildren().clear();


        Node[] nodes = new Node[15];

        for (int i = 0; i < 1; i++) {
            try {
                nodes[i] = (Node) FXMLLoader.load(getClass().getResource("view/payment.fxml"));
                pnl_scroll.getChildren().add(nodes[i]);
                //   loadCalendar();

            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void reportNodes() {


        pnl_scroll.getChildren().clear();


        Node[] nodes = new Node[15];

        for (int i = 0; i < 1; i++) {
            try {
                nodes[i] = (Node) FXMLLoader.load(getClass().getResource("view/report.fxml"));
                pnl_scroll.getChildren().add(nodes[i]);
                //   loadCalendar();

            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void sdashNodes() {
        pnl_scroll.getChildren().clear();
        Node[] nodes = new Node[15];

        for (int i = 0; i < 1; i++) {
            try {
                nodes[i] = (Node) FXMLLoader.load(getClass().getResource("view/dashboardStaff.fxml"));
                pnl_scroll.getChildren().add(nodes[i]);
                //   loadCalendar();

            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }


}
