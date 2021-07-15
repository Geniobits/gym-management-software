package classes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingConstants;
import javax.swing.UnsupportedLookAndFeelException;
import utils.Connect;

public class Update_Fee_Value extends JFrame {
	
	Connection con=null;
	 ResultSet rs=null;
	 PreparedStatement pst=null;

	public JButton btnUpdate;
	public JLabel lblUpdatingFee;
	private JPanel contentPane;
	private JTextField txtFeeIDn;
	private JTextField txtDur;
	private JTextField txtAmnt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
 
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Update_Fee_Value.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Update_Fee_Value.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Update_Fee_Value.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Update_Fee_Value.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Update_Fee_Value frame = new Update_Fee_Value();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
    private final JTextField txtPlan;

	/**
	 * Create the frame.
	 */
	public void updating_fees(String rowid)
	{
		con=Connect.connectDb();
		 String sql="select * from fees where id='"+rowid+"'";
	       try {
		    	pst=con.prepareStatement(sql);
		        rs=pst.executeQuery();
		        if(rs.next())
		        {
		        	txtFeeIDn.setText(rs.getString("id"));
		        	txtFeeIDn.setEnabled(false);
		        	txtDur.setText(rs.getString("duration"));
		                txtPlan.setText(rs.getString("plan"));
		        	txtAmnt.setText(rs.getString("amount"));
		        }
		       
	       }catch(Exception e)
	       {
	    	  JOptionPane.showMessageDialog(null, e); 
	       }
	       finally {
	    	   try {
	    		   pst.close();
	    		   rs.close();
	    		   con.close();
				
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, e2);
			}
	       }
	       
	
	}
        
        public void deleting_fees(String rowid){
		con=Connect.connectDb();
		 String sql="select * from fees where id='"+rowid+"'";
	       try {
		    	pst=con.prepareStatement(sql);
		        rs=pst.executeQuery();
		        if(rs.next())
		        {
		        	txtFeeIDn.setText(rs.getString("id"));
		        	txtFeeIDn.setEnabled(false);
		        	txtDur.setText(rs.getString("duration"));
		        	txtDur.setEnabled(false);
		        	txtAmnt.setText(rs.getString("amount"));
                                btnUpdate.setText("Delete");
		        }
		       
	       }catch(Exception e)
	       {
	    	  JOptionPane.showMessageDialog(null, e); 
	       }
	       finally {
	    	   try {
	    		   pst.close();
	    		   rs.close();
	    		   con.close();
				
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, e2);
			}
	       }
	       
	
	}
	

	public void ForInsert()
	{

		con=Connect.connectDb();
		 String sql1="select * from fees";
	       try {
		    	pst=con.prepareStatement(sql1);
		        rs=pst.executeQuery();
		        int ino=0;
		        int dur=0;
		        while(rs.next())
		        {
		        	ino=(rs.getInt("id"));
		        	dur=rs.getInt("duration");
		        }
		        pst.close();
		        rs.close();
		        con.close();
		        
		        txtFeeIDn.setText(String.valueOf(ino+1));
		    	txtFeeIDn.setEnabled(false);
	        	txtDur.setText(String.valueOf(dur+1));
	        	txtDur.setEnabled(true);
	       }catch(Exception e)
	       {
	    	  JOptionPane.showMessageDialog(null, e); 
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
	
	public Update_Fee_Value() {
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
                setTitle("Update fee");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 299, 219);
                
                
                URL iconURL = getClass().getResource("/geniobits/dashboard/images/logo.png");
// iconURL is null when not found
                                        ImageIcon icon = new ImageIcon(iconURL);
                                        setIconImage(icon.getImage());
                                        setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 293, 46);
		panel.setBackground(new Color(135, 206, 250));
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblUpdatingFee = new JLabel("      Updating Fee");
		lblUpdatingFee.setHorizontalAlignment(SwingConstants.CENTER);
		lblUpdatingFee.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblUpdatingFee.setBounds(32, 11, 214, 24);
		panel.add(lblUpdatingFee);
		
		JLabel lblFeeId = new JLabel("Fee ID:");
		lblFeeId.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFeeId.setBounds(10, 59, 65, 14);
		contentPane.add(lblFeeId);
		
		JLabel lblDuration = new JLabel("Duration:");
		lblDuration.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDuration.setBounds(10, 84, 65, 14);
		contentPane.add(lblDuration);
		
		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAmount.setBounds(10, 109, 65, 14);
		contentPane.add(lblAmount);
                
                JLabel lblPlan = new JLabel("Gym Plan");
		lblPlan.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPlan.setBounds(10, 132, 65, 14);
		contentPane.add(lblPlan);
		
		txtFeeIDn = new JTextField();
		txtFeeIDn.setBounds(85, 57, 180, 20);
		contentPane.add(txtFeeIDn);
		txtFeeIDn.setColumns(10);
		
		txtDur = new JTextField();
		txtDur.setColumns(10);
		txtDur.setBounds(85, 82, 126, 20);
		contentPane.add(txtDur);
		
		txtAmnt = new JTextField();
		txtAmnt.setColumns(10);
		txtAmnt.setBounds(85, 107, 180, 20);
		contentPane.add(txtAmnt);
                
                txtPlan = new JTextField();
		txtPlan.setColumns(10);
		txtPlan.setBounds(85, 133, 180, 20);
		contentPane.add(txtPlan);
		
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					con=Connect.connectDb();
				
						String sql=null;
						if(btnUpdate.getText().equals("Update")) 
						{
							sql="Update fees set amount='"+txtAmnt.getText()+"', plan='"+txtPlan.getText()+"', duration='"+ txtDur.getText()+"' where id="+txtFeeIDn.getText()+"";
							pst=con.prepareStatement(sql);
							if(pst.execute())
								{
									JOptionPane.showMessageDialog(null,"Sorry data canot be Updated");
								}
							else {
								JOptionPane.showMessageDialog(null, "Successfully Updated");
								}
							 pst.close();
				    		 con.close();
						}
						else if(btnUpdate.getText().equals("Save"))
							{
								sql="Insert into fees(duration,amount,plan) values("+txtDur.getText()+","+txtAmnt.getText()+",'"+txtPlan.getText()+"')";
                                                                
                                                        
								pst=con.prepareStatement(sql);
								if(pst.execute())
								{
									JOptionPane.showMessageDialog(null,"Sorry data canot be inserted");
								}
								else {
										JOptionPane.showMessageDialog(null, "Successfully inserted");
									}
							 pst.close();
				    		 con.close();
							}
                                                else if(btnUpdate.getText().equals("Delete")){
                                                    sql="DELETE FROM `fees` WHERE `id`="+txtFeeIDn.getText()+"";
								pst=con.prepareStatement(sql);
								if(pst.execute())
								{
									JOptionPane.showMessageDialog(null,"Sorry data canot be deleted");
								}
								else {
										JOptionPane.showMessageDialog(null, "Successfully Deleted");
									}
							 pst.close();
				    		 con.close();
                                                }
						else
						{
							
						}
						
				}catch (Exception ee) {
					JOptionPane.showMessageDialog(null, ee);
				}
				finally {
			    	   try {
			    		   pst.close();
			    		   con.close();
			    		  
						
					} catch (Exception e2) {
						//JOptionPane.showMessageDialog(null, e2);
					}
			       }
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnUpdate.setIcon(new ImageIcon(Update_Fee_Value.class.getResource("/images/save.png")));
		btnUpdate.setBounds(85, 165, 85, 23);
		contentPane.add(btnUpdate);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCancel.setIcon(new ImageIcon(Update_Fee_Value.class.getResource("/images/cancel.png")));
		btnCancel.setBounds(180, 165, 85, 23);
		contentPane.add(btnCancel);
		
		JLabel lblInMonth = new JLabel("( in month)");
		lblInMonth.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblInMonth.setBounds(211, 85, 62, 14);
		contentPane.add(lblInMonth);
	}
}
