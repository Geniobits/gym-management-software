package classes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import utils.Connect;

public class Registration_Fee extends JFrame {
	
	Connection con=null;
	 ResultSet rs=null;
	 PreparedStatement pst=null;

	private JPanel contentPane;
	private JTextField txtFeeOfReg;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		 try {
	            //here you can put the selected theme class name in JTattoo
	            UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
	 
	        } catch (ClassNotFoundException ex) {
	            java.util.logging.Logger.getLogger(Registration_Fee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (InstantiationException ex) {
	            java.util.logging.Logger.getLogger(Registration_Fee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (IllegalAccessException ex) {
	            java.util.logging.Logger.getLogger(Registration_Fee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
	            java.util.logging.Logger.getLogger(Registration_Fee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registration_Fee frame = new Registration_Fee();
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
	public Registration_Fee() {
		setTitle("Registration Fee");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 317, 190);
		setLocationRelativeTo(this);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 311, 39);
		panel.setBackground(new Color(135, 206, 250));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Registration Fee");
		lblNewLabel.setBounds(94, 0, 163, 39);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 255, 127), 2));
		panel_1.setBounds(10, 100, 280, 52);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnSet = new JButton("Set");
		btnSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					con=Connect.connectDb();
					String sql="Insert into registration_fee(registration_fee) values("+txtFeeOfReg.getText()+")";
					pst=con.prepareStatement(sql);
					if(pst.execute())
					{
						JOptionPane.showMessageDialog(null, "Sorry Registration fee canot be inserted");
						
					}
					else {
						JOptionPane.showMessageDialog(null, "New Registration fee is Inserted");
					}
					pst.close();
					con.close();
					
					
				} catch (Exception e3) {
					JOptionPane.showMessageDialog(null, e3);
				}
				finally {
					try {
						pst.close();
						con.close();
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2);
					}
				}
			}
		});
		btnSet.setIcon(new ImageIcon(Registration_Fee.class.getResource("/images/paid.png")));
		btnSet.setBounds(41, 11, 97, 25);
		panel_1.add(btnSet);
		btnSet.setForeground(new Color(255, 69, 0));
		btnSet.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				
				
			}
		});
		btnCancel.setIcon(new ImageIcon(Registration_Fee.class.getResource("/images/cancel.png")));
		btnCancel.setBounds(148, 11, 102, 25);
		panel_1.add(btnCancel);
		btnCancel.setForeground(new Color(255, 69, 0));
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel lblSetYourNew = new JLabel("Set New Registration Fee for Gym");
		lblSetYourNew.setBounds(55, 50, 202, 23);
		contentPane.add(lblSetYourNew);
		lblSetYourNew.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		txtFeeOfReg = new JTextField();
		txtFeeOfReg.setBounds(66, 73, 181, 23);
		contentPane.add(txtFeeOfReg);
		txtFeeOfReg.setColumns(10);
	}

}
