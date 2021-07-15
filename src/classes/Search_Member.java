package classes;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;

import utils.Connect;

public class Search_Member extends JFrame {

	Connection con=null;
	 ResultSet rs=null;
	 PreparedStatement pst=null;
	
	public JButton btnDelete;
	public JLabel lblSearchMember;
	private JPanel contentPane;
	private JTextField txtFullname;
	private JTextField txtMno;
	public String comboMachine;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
            //here you can put the selected theme class name in JTattoo
            UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
 
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Search_Member.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Search_Member.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Search_Member.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Search_Member.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Search_Member frame = new Search_Member();
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
	public Search_Member() {
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
		setTitle("FitBit || Adding New Member");
                URL iconURL = getClass().getResource("/geniobits/dashboard/images/logo.png");
// iconURL is null when not found
                                        ImageIcon icon = new ImageIcon(iconURL);
                                        setIconImage(icon.getImage());
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 200, 860, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblHeadFullName = new JLabel("Full Name");
		lblHeadFullName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblHeadFullName.setBounds(10, 68, 94, 22);
		contentPane.add(lblHeadFullName);
		
		txtFullname = new JTextField();
		txtFullname.setBounds(114, 70, 194, 20);
		contentPane.add(txtFullname);
		txtFullname.setColumns(10);
		
		txtMno = new JTextField();
		txtMno.setBounds(517, 70, 94, 20);
		contentPane.add(txtMno);
		txtMno.setColumns(10);
		
		JLabel lblId = new JLabel("Membership No:");
		lblId.setToolTipText("");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblId.setBounds(350, 71, 144, 23);
		contentPane.add(lblId);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 854, 54);
		panel.setBackground(new Color(135, 206, 250));
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblSearchMember = new JLabel("Search Member");
		lblSearchMember.setBounds(345, 11, 220, 28);
		panel.add(lblSearchMember);
		lblSearchMember.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearchMember.setFont(new Font("Times New Roman", Font.BOLD, 24));
		
		JLabel lblFullName = new JLabel("Full Name");
		lblFullName.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblFullName.setBounds(10, 124, 104, 14);
		contentPane.add(lblFullName);
		
		JLabel lblMembershipNo = new JLabel("Membership No");
		lblMembershipNo.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblMembershipNo.setBounds(10, 149, 104, 14);
		contentPane.add(lblMembershipNo);
		
		JLabel lblWeight = new JLabel("Weight");
		lblWeight.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblWeight.setBounds(10, 292, 104, 23);
		contentPane.add(lblWeight);
		
		JLabel lblContactNo = new JLabel("Contact No");
		lblContactNo.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblContactNo.setBounds(10, 236, 104, 14);
		contentPane.add(lblContactNo);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblEmail.setBounds(317, 128, 107, 14);
		contentPane.add(lblEmail);
		
		JLabel lblRegisteredDate = new JLabel("Registered Date");
		lblRegisteredDate.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblRegisteredDate.setBounds(317, 153, 107, 14);
		contentPane.add(lblRegisteredDate);
		
		JLabel lblStartDate = new JLabel("Start Date");
		lblStartDate.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblStartDate.setBounds(317, 178, 107, 14);
		contentPane.add(lblStartDate);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblStatus.setBounds(317, 203, 107, 14);
		contentPane.add(lblStatus);
		
		JLabel lblCountry = new JLabel("Country");
		lblCountry.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblCountry.setBounds(10, 261, 101, 20);
		contentPane.add(lblCountry);
		
		JLabel slblPhoto = new JLabel("");
		slblPhoto.setBounds(705, 88, 139, 162);
		contentPane.add(slblPhoto);
		
		JLabel slblFullname = new JLabel("");
		slblFullname.setForeground(new Color(220, 20, 60));
		slblFullname.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		slblFullname.setBounds(124, 124, 104, 14);
		contentPane.add(slblFullname);
		
		JLabel slblMembershipno = new JLabel("");
		slblMembershipno.setForeground(new Color(220, 20, 60));
		slblMembershipno.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		slblMembershipno.setBounds(124, 149, 104, 14);
		contentPane.add(slblMembershipno);
		
		JLabel slblGender = new JLabel("");
		slblGender.setForeground(new Color(220, 20, 60));
		slblGender.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		slblGender.setBounds(124, 174, 104, 20);
		contentPane.add(slblGender);
		
		JLabel slblAge = new JLabel("");
		slblAge.setForeground(new Color(220, 20, 60));
		slblAge.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		slblAge.setBounds(124, 208, 101, 20);
		contentPane.add(slblAge);
		
		JLabel slblContact = new JLabel("");
		slblContact.setForeground(new Color(220, 20, 60));
		slblContact.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		slblContact.setBounds(124, 236, 104, 14);
		contentPane.add(slblContact);
		
		JLabel slblCountry = new JLabel("");
		slblCountry.setForeground(new Color(220, 20, 60));
		slblCountry.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		slblCountry.setBounds(124, 261, 101, 20);
		contentPane.add(slblCountry);
		
		JLabel slblWeight = new JLabel("");
		slblWeight.setForeground(new Color(220, 20, 60));
		slblWeight.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		slblWeight.setBounds(124, 292, 104, 14);
		contentPane.add(slblWeight);
		
		JLabel slblEmail = new JLabel("");
		slblEmail.setForeground(new Color(220, 20, 60));
		slblEmail.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		slblEmail.setBounds(434, 128, 136, 14);
		contentPane.add(slblEmail);
		
		JLabel slblRegistereddate = new JLabel("");
		slblRegistereddate.setForeground(new Color(220, 20, 60));
		slblRegistereddate.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		slblRegistereddate.setBounds(434, 153, 136, 14);
		contentPane.add(slblRegistereddate);
		
		JLabel slblStartdate = new JLabel("");
		slblStartdate.setForeground(new Color(220, 20, 60));
		slblStartdate.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		slblStartdate.setBounds(434, 178, 136, 14);
		contentPane.add(slblStartdate);
		
		JLabel slblStatus = new JLabel("");
		slblStatus.setForeground(new Color(220, 20, 60));
		slblStatus.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		slblStatus.setBounds(434, 203, 136, 14);
		contentPane.add(slblStatus);
		
		JLabel slblOccupation = new JLabel("");
		slblOccupation.setForeground(new Color(220, 20, 60));
		slblOccupation.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		slblOccupation.setBounds(434, 231, 136, 14);
		contentPane.add(slblOccupation);
		
		JLabel slblFeesmode = new JLabel("");
		slblFeesmode.setForeground(new Color(220, 20, 60));
		slblFeesmode.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		slblFeesmode.setBounds(434, 259, 136, 14);
		contentPane.add(slblFeesmode);
		
		JLabel slblDuration = new JLabel("");
		slblDuration.setForeground(new Color(220, 20, 60));
		slblDuration.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		slblDuration.setBounds(434, 292, 136, 14);
		contentPane.add(slblDuration);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setIcon(new ImageIcon(Search_Member.class.getResource("/images/cancel.png")));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		btnCancel.setForeground(new Color(0, 206, 209));
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnCancel.setBounds(705, 500, 106, 23);
		contentPane.add(btnCancel);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                            if(txtMno.getText().isEmpty()){
                                txtMno.setText("0");
                            }
				String sql="Select * from members where id="+txtMno.getText()+" or full_name='"+txtFullname.getText()+"'";
				try {
					con=Connect.connectDb();
					pst=con.prepareStatement(sql);
					rs=pst.executeQuery();
					//rs.next();
					
						slblMembershipno.setText(rs.getString("id"));
						slblFullname.setText(rs.getString("full_name"));
						slblGender.setText(rs.getString("gender"));
						slblCountry.setText(rs.getString("country"));
						slblContact.setText(rs.getString("contact_no"));
						slblOccupation.setText(rs.getString("occupation"));
						slblFeesmode.setText(rs.getString("fees_mode"));
						slblRegistereddate.setText(rs.getString("reg_date"));
						slblEmail.setText(rs.getString("email"));
						slblDuration.setText(rs.getString("plan"));
						slblStartdate.setText(rs.getString("start_date"));
						slblStatus.setText(rs.getString("status"));
						slblWeight.setText(rs.getString("weight"));
						comboMachine=rs.getString("machine");
						SimpleDateFormat format= new SimpleDateFormat("yyyy-MMMMMM-dd");
						
						slblAge.setText(rs.getString("dob"));
						try{
						byte[] imagebytes = rs.getBytes("img");
			        	Image image=getToolkit().createImage(imagebytes);
			        	Image newImage=image.getScaledInstance(slblPhoto.getWidth(), slblPhoto.getHeight(), Image.SCALE_SMOOTH);
				    	ImageIcon icon=new ImageIcon(newImage);
				    	slblPhoto.setIcon(icon);
                                                }catch(Exception ee){
                                                    
                                                }
				    	
						
					
                                        pst.close();
						rs.close();
						con.close();
					
				}catch(Exception ee)
				{
					JOptionPane.showMessageDialog(null, "Not Found");
                             }
				finally {
					try {
						pst.close();
						rs.close();
						con.close();
						
					} catch (Exception e2) {
						//JOptionPane.showMessageDialog(null, e2);
					}
				}
			}
		});
		btnSearch.setIcon(new ImageIcon(Search_Member.class.getResource("/images/search1.png")));
		btnSearch.setForeground(new Color(0, 206, 209));
		btnSearch.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnSearch.setBounds(638, 67, 107, 23);
		contentPane.add(btnSearch);
		
		JLabel lblOccupation = new JLabel("Occupation");
		lblOccupation.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblOccupation.setBounds(317, 231, 107, 14);
		contentPane.add(lblOccupation);
		
		JLabel lblFeesMode = new JLabel("Fees Mode");
		lblFeesMode.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblFeesMode.setBounds(317, 259, 107, 14);
		contentPane.add(lblFeesMode);
		
		JLabel lblDuration = new JLabel("Duration");
		lblDuration.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblDuration.setBounds(317, 292, 107, 22);
		contentPane.add(lblDuration);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblGender.setBounds(10, 174, 104, 20);
		contentPane.add(lblGender);
		
		JLabel lblAge = new JLabel("DOB");
		lblAge.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblAge.setBounds(10, 208, 101, 20);
		contentPane.add(lblAge);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
                                        con.close();
					con=Connect.connectDb();
					String sql="Delete from members where id="+txtMno.getText()+"";
					pst=con.prepareStatement(sql);
					if(pst.execute())
					{
						JOptionPane.showMessageDialog(null, "Sorry member can't be deleted");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Deleted successfully");
                                                
						
					}
					pst.close();
					con.close();
					
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2);
                                       }
                                try{
				con.close();
                                }catch(Exception e2){
                                    
                                }
			}
		});
		btnDelete.setEnabled(false);
		btnDelete.setIcon(new ImageIcon(Search_Member.class.getResource("/images/delete user.png")));
		btnDelete.setForeground(new Color(0, 206, 209));
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnDelete.setBounds(592, 317, 106, 23);
		contentPane.add(btnDelete);
		
                
                
	
	}
        Thread sendToMachine = new Thread(new Runnable() {
    public void run() {
        // stuff here
      
       
    }
});
          
}
