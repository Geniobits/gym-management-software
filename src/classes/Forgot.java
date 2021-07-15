package classes;

import geniobits.dashboard.controller.HomeController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import utils.Connect;

public class Forgot extends JFrame {

	 Connection con=null;
	 ResultSet rs=null;
	 PreparedStatement pst=null;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JTextField txtFullname;
	private JTextField txtAnswer;
	private JTextField txtYourPass;
	private JTextField txtSecurity;
	private JTextField type;
        private String oldPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
            //here you can put the selected theme class name in JTattoo
            UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
 
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Forgot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Forgot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Forgot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Forgot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Forgot frame = new Forgot();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Forgot() {
            try {
            //here you can put the selected theme class name in JTattoo
            UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
 
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Forgot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Forgot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Forgot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Forgot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		setTitle("Forgot Password");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(450, 200, 340, 474);
                 URL iconURL = getClass().getResource("/geniobits/dashboard/images/logo.png");
// iconURL is null when not found
                                        ImageIcon icon = new ImageIcon(iconURL);
                                        setIconImage(icon.getImage());
		setLocationRelativeTo(this);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, new Color(255, 0, 0), new Color(0, 255, 0), new Color(0, 0, 255), new Color(255, 255, 0)), "Your Information", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(0, 136, 337, 309);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(10, 32, 111, 14);
		panel.add(lblUsername);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblFullName = new JLabel("Full Name");
		lblFullName.setBounds(10, 75, 111, 14);
		panel.add(lblFullName);
		lblFullName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblNewLabel = new JLabel("Security Question");
		lblNewLabel.setBounds(10, 159, 111, 19);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblAnswer = new JLabel("Answer");
		lblAnswer.setBounds(10, 202, 111, 14);
		panel.add(lblAnswer);
		lblAnswer.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblPassword = new JLabel("Your Password");
		lblPassword.setBounds(10, 246, 111, 14);
		panel.add(lblPassword);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		txtUsername = new JTextField();
		txtUsername.setBounds(130, 30, 163, 20);
		panel.add(txtUsername);
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtUsername.setColumns(10);
		
		txtFullname = new JTextField();
		txtFullname.setBounds(130, 73, 163, 20);
		panel.add(txtFullname);
		txtFullname.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtFullname.setEditable(false);
		txtFullname.setColumns(10);
		
		txtAnswer = new JTextField();
		txtAnswer.setBounds(130, 200, 164, 20);
		panel.add(txtAnswer);
		txtAnswer.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtAnswer.setColumns(10);
		
		txtYourPass = new JTextField();
		txtYourPass.setBounds(131, 244, 164, 20);
		panel.add(txtYourPass);
		txtYourPass.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtYourPass.setColumns(10);
		
		txtSecurity = new JTextField();
		txtSecurity.setBounds(130, 159, 163, 20);
		panel.add(txtSecurity);
		txtSecurity.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtSecurity.setEditable(false);
		txtSecurity.setColumns(10);
		
		JButton btnSearch = new JButton("");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				con=Connect.connectDb();
				String usernames=txtUsername.getText();
				String sql="Select full_name,security_question,type from login where username='"+usernames+"'";
			     try{
			         pst=con.prepareStatement(sql);
			         rs=pst.executeQuery();
			         while(rs.next())
			         {
			        	 txtFullname.setText(rs.getString(1));
			        	 txtSecurity.setText(rs.getString(2));
			        	 type.setText(rs.getString(3));
			        
			         }
			         
			     }
			     catch(Exception e)
			     {
			    	 JOptionPane.showMessageDialog(null, e);
			     }
			}
		});
		btnSearch.setBounds(296, 30, 38, 20);
		panel.add(btnSearch);
		btnSearch.setIcon(new ImageIcon(Forgot.class.getResource("/images/search member.png")));
		btnSearch.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JButton btnRetrive = new JButton("");
		btnRetrive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				con=Connect.connectDb();
				String usernames=txtUsername.getText();
				String sql="Select answer,password from login where username='"+usernames+"'";
			    
				String ans= txtAnswer.getText();
			
				
			     try{
			         pst=con.prepareStatement(sql);
			         rs=pst.executeQuery();
			       
			         rs.next();
			         if(ans.equals(rs.getString("answer")))
			         {  
                                     oldPass=rs.getString(2);
			        	txtYourPass.setText(rs.getString(2));
			         }
			     }
			     catch(Exception e)
			     {
			    	 JOptionPane.showMessageDialog(null, e);
			     }
				
			}
		});
		btnRetrive.setBounds(296, 197, 34, 23);
		panel.add(btnRetrive);
		btnRetrive.setIcon(new ImageIcon(Forgot.class.getResource("/images/password.png")));
		btnRetrive.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JButton btnBack = new JButton("Update");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                            
                           
                         try{
                                 con.close();
                                 con=Connect.connectDb();
                                 String sql2="update login set password=? where username='"+txtUsername.getText()+"' and password='"+oldPass+"'";
                                pst=con.prepareStatement(sql2);
                                pst.setString(1, txtYourPass.getText());
                        
                    //    System.out.println(rs.getString(7));
                                pst.execute();
                                rs.close();
                                pst.close();
                                con.close();
                                JOptionPane.showMessageDialog(null, "Password updated");
                                hide();
                            }catch(Exception e){
                                JOptionPane.showMessageDialog(null, "Something went wrong");
                                System.out.println(e);
                            }
                                                       
			}
		});
		btnBack.setBounds(120, 275, 101, 23);
		panel.add(btnBack);
		btnBack.setIcon(new ImageIcon("D:\\JavaWorkspace\\image\\back.png"));
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel label_1 = new JLabel("User Type");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_1.setBounds(10, 114, 112, 17);
		panel.add(label_1);
		
		type = new JTextField();
		type.setFont(new Font("Tahoma", Font.PLAIN, 13));
		type.setEditable(false);
		type.setColumns(10);
		type.setBounds(130, 113, 163, 20);
		panel.add(type);
		
		JLabel lblForgotPassword = new JLabel("Forgot Password");
		lblForgotPassword.setForeground(new Color(0, 128, 128));
		lblForgotPassword.setBounds(95, 99, 151, 26);
		contentPane.add(lblForgotPassword);
		lblForgotPassword.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Forgot.class.getResource("/images/forgotpass.png")));
		label.setBounds(95, 0, 120, 109);
		contentPane.add(label);
		
		
		
	}
}
