package classes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import utils.Connect;

public class Authentication extends JDialog {

	
	Connection con=null;
	 ResultSet rs=null;
	 PreparedStatement pst=null;
	 
	private final JPanel contentPanel = new JPanel();
	private JPasswordField pass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
	           UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
	 
	        } catch (ClassNotFoundException ex) {
	            java.util.logging.Logger.getLogger(Authentication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (InstantiationException ex) {
	            java.util.logging.Logger.getLogger(Authentication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (IllegalAccessException ex) {
	            java.util.logging.Logger.getLogger(Authentication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
	            java.util.logging.Logger.getLogger(Authentication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        }
		try {
			Authentication dialog = new Authentication();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Authentication() {
		setTitle("Authenticataion");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 320, 191);
		getContentPane().setLayout(null);
		contentPanel.setBounds(433, 0, 1, 260);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 109, 310, 49);
			getContentPane().add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("Confirm");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							con=Connect.connectDb();
							String pasw=String.valueOf(pass.getPassword());
					                
							String sql="Select * from login where password='"+pasw+"'";
							
						         pst=con.prepareStatement(sql);
						         rs=pst.executeQuery();
						          
							if(rs.next())
							{
								JOptionPane.showMessageDialog(null, "Ok you have done");
								hide();
								
								
								
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Sorry we can't recognize you");
							}
							pst.close();
							rs.close();
							con.close();
							
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null, e2);
						}finally {
							try {
								pst.close();
								rs.close();
								con.close();
								
								
							} catch (Exception e3) {
								JOptionPane.showMessageDialog(null, e3);
							}
						}
						
					}
				});
				okButton.setFont(new Font("Tahoma", Font.BOLD, 14));
				okButton.setIcon(new ImageIcon(Authentication.class.getResource("/images/confirm.png")));
				okButton.setBounds(46, 11, 113, 28);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setFont(new Font("Tahoma", Font.BOLD, 14));
				cancelButton.setIcon(new ImageIcon(Authentication.class.getResource("/images/cancel.png")));
				cancelButton.setBounds(177, 11, 98, 27);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 314, 39);
		panel.setBackground(new Color(0, 128, 128));
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblPasswordAuthentication = new JLabel("Password Authentication");
		lblPasswordAuthentication.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblPasswordAuthentication.setBounds(49, 0, 230, 35);
		panel.add(lblPasswordAuthentication);
		
		JLabel lblPleaseEnterYour = new JLabel("Please enter your password to confirm this task");
		lblPleaseEnterYour.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPleaseEnterYour.setBounds(11, 38, 299, 32);
		getContentPane().add(lblPleaseEnterYour);
		
		pass = new JPasswordField();
		pass.setBounds(41, 71, 232, 27);
		getContentPane().add(pass);
		setLocationRelativeTo(null);
		setLocation(500, 290);
	}
}
