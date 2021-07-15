package classes;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import utils.Connect;

public class Message extends JFrame {

	Connection con=null;
	 ResultSet rs=null;
	 PreparedStatement pst=null;
	 
	 private int total_noo=0;
	 double deducted_blnce=0;
	 int no_of_character=0;
	 
	private JPanel contentPane;
	private JTable table;
	private JTextField phn_no;
	private JTextArea textArea ;
	private JLabel label_1;
	private JLabel characters;
	private JLabel deducted_blnc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		 try {
	            //here you can put the selected theme class name in JTattoo
	            UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
	 
	        } catch (ClassNotFoundException ex) {
	            java.util.logging.Logger.getLogger(Message.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (InstantiationException ex) {
	            java.util.logging.Logger.getLogger(Message.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (IllegalAccessException ex) {
	            java.util.logging.Logger.getLogger(Message.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
	            java.util.logging.Logger.getLogger(Message.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Message frame = new Message();
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
	
	public void Get_Message()
	{				
		String sql="Select contact_no as'Numbers' from members";
        try{
        	con=Connect.connectDb();
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            
            table.setModel(DbUtils.resultSetToTableModel(rs));
            pst.close();
 		    rs.close();
 		    con.close();
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e);
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
	public Message() {
		setResizable(false);
		setTitle("Deep Da Gym || Messaging");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 666, 564);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("ProgressBar.selectionBackground"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.setBounds(7, 212, 490, 174);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int d=table.getSelectedRow();
				if(d>0)
				{
				String no=table.getModel().getValueAt(d,0).toString();
				String mszs=table.getModel().getValueAt(d,1).toString();
				phn_no.setText(no);
				textArea.setText(mszs);
				}
				else {
					JOptionPane.showMessageDialog(table, "Please select at least one row");
				}
			}
			
		});
		table.setOpaque(false);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Number", "Message"
			}
		));
		scrollPane.setViewportView(table);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int p=JOptionPane.showConfirmDialog(null,"Are you sure to send message" , "Sending Message Confirmation", JOptionPane.YES_NO_OPTION);
				if(p==0)
				{
					JOptionPane.showMessageDialog(null, "Messages are sending");
				}
				else {
					
				}
			}
		});
		btnSend.setIcon(new ImageIcon(Message.class.getResource("/images/send Msz.png")));
		btnSend.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSend.setBounds(182, 491, 97, 23);
		contentPane.add(btnSend);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.setModel(new DefaultTableModel(null, new String[] {"Number", "Message"}));
				label_1.setText(null);
				textArea.setText(null);
				phn_no.setText(null);
			}
		});
		btnReset.setIcon(new ImageIcon(Message.class.getResource("/images/exit.png")));
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnReset.setBounds(295, 491, 97, 23);
		contentPane.add(btnReset);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setIcon(new ImageIcon(Message.class.getResource("/images/cancel.png")));
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCancel.setBounds(402, 491, 96, 23);
		contentPane.add(btnCancel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 255, 0)));
		panel_1.setBounds(5, 138, 617, 39);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblEnterReceip = new JLabel("Enter Recipient Numbers");
		lblEnterReceip.setBounds(10, 11, 182, 23);
		panel_1.add(lblEnterReceip);
		lblEnterReceip.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		phn_no = new JTextField();
		phn_no.setBounds(227, 11, 238, 23);
		panel_1.add(phn_no);
		phn_no.setFont(new Font("Tahoma", Font.BOLD, 12));
		phn_no.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(502, 11, 105, 23);
		panel_1.add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				  DefaultTableModel table1 = (DefaultTableModel)table.getModel();
				  
				try {	 
					String j=phn_no.getText();
					String msz=textArea.getText();
					if(j.equals(""))
					{
						JOptionPane.showMessageDialog(null, "Please Enter a number");
					}
					else if(msz.equals("")) {
						JOptionPane.showMessageDialog(null, "Empty Message");
					}
				  
					else {
					Long num=Long.parseLong(j);
			        table1.addRow(new Object[]{num,msz});
			        total_noo=total_noo+1;
			        String ss=String.valueOf(total_noo);
			        
			        label_1.setText(ss);
					}
				}
				catch(Exception ee)
				{
					JOptionPane.showMessageDialog(null, ee);
				}
			}
		});
		btnAdd.setIcon(new ImageIcon(Message.class.getResource("/images/add Msz.png")));
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(139, 0, 0), new Color(220, 20, 60), new Color(0, 139, 139), new Color(0, 255, 0)));
		panel.setBounds(506, 212, 117, 174);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel table1 = (DefaultTableModel)table.getModel();
				int c=table.getSelectedRow();
				if(c>0)
				{
					table1.removeRow(c);
				}
				else {
					JOptionPane.showMessageDialog(null, "Please Select at least one row");
				}
			}
		});
		btnClear.setBounds(6, 140, 105, 23);
		panel.add(btnClear);
		btnClear.setIcon(new ImageIcon(Message.class.getResource("/images/delete.png")));
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JButton btnClear_1 = new JButton("Clear All");
		btnClear_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.setModel(new DefaultTableModel(null, new String[] {"Number", "Message"}));
				}
		});
		btnClear_1.setBounds(6, 106, 105, 23);
		panel.add(btnClear_1);
		btnClear_1.setIcon(new ImageIcon(Message.class.getResource("/images/clear all.png")));
		btnClear_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 DefaultTableModel table1 = (DefaultTableModel)table.getModel();
				 int d= table.getSelectedRow();
				 if(d>0)
				 {
				 table1.setValueAt(phn_no.getText(), d, 0);
				 table1.setValueAt(textArea.getText(), d, 1);
				 }
				 else
				 {
					 
				 }
			}
		});
		btnUpdate.setBounds(6, 16, 105, 23);
		panel.add(btnUpdate);
		btnUpdate.setIcon(new ImageIcon(Message.class.getResource("/images/update.png")));
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JButton btnUpdateAll = new JButton("Update All");
		btnUpdateAll.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnUpdateAll.setBounds(6, 50, 105, 23);
		panel.add(btnUpdateAll);
		
		JLabel lblComposeMessage = new JLabel("Compose Message");
		lblComposeMessage.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblComposeMessage.setBounds(27, 391, 135, 23);
		contentPane.add(lblComposeMessage);
		
		JLabel lblTotalNumbers = new JLabel("Total Numbers:");
		lblTotalNumbers.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTotalNumbers.setBounds(7, 188, 114, 23);
		contentPane.add(lblTotalNumbers);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(172, 389, 326, 91);
		contentPane.add(scrollPane_1);
		
		
		textArea = new JTextArea();
		textArea.addKeyListener(new KeyAdapter() {
		
			public void keyTyped(KeyEvent e) {
				String k=textArea.getText();
				no_of_character=k.length();
				
				if((no_of_character%10)==0)
				{
					deducted_blnce=deducted_blnce+1;
					deducted_blnc.setText(String.valueOf(deducted_blnce));
					
				}
				else {
					deducted_blnc.setText(String.valueOf(deducted_blnce));
					deducted_blnce=deducted_blnce+0.10;
					
				}
				characters.setText(String.valueOf(no_of_character));
				
				
		
			}
			
		});
		textArea.setFont(new Font("Monospaced", Font.BOLD, 14));
		scrollPane_1.setViewportView(textArea);
		
		JLabel lblMessaging = new JLabel("Send");
		lblMessaging.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblMessaging.setBounds(196, 27, 76, 32);
		contentPane.add(lblMessaging);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("D:\\mmssss.png"));
		lblNewLabel.setBounds(274, 0, 76, 77);
		contentPane.add(lblNewLabel);
		
		JLabel lblMessages = new JLabel("Messages");
		lblMessages.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblMessages.setBounds(360, 27, 112, 32);
		contentPane.add(lblMessages);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("D:\\ddd.png"));
		label.setBounds(7, 0, 135, 138);
		contentPane.add(label);
		
		label_1 = new JLabel("");
		label_1.setForeground(new Color(255, 69, 0));
		label_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_1.setBounds(113, 188, 49, 19);
		contentPane.add(label_1);
		
		JButton btnImportNoFrom = new JButton("Import all numbers from members contact");
		btnImportNoFrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String sql="Select contact_no as'Numbers' from members";
		        try{
		        	DefaultTableModel table1 = (DefaultTableModel)table.getModel();
		        	String msz=textArea.getText();
		        	String number;
		        	con=Connect.connectDb();
		            pst=con.prepareStatement(sql);
		            rs=pst.executeQuery();
		            int a=0;
		            while(rs.next())
		            {
		            	number=rs.getString(1);
		            	 table1.addRow(new Object[]{number,msz});
		            	 a=a+1;
		            }
		            label_1.setText(String.valueOf(a));
		            pst.close();
		 		    rs.close();
		 		    con.close();
		        }
		        catch(Exception e1)
		        {
		            JOptionPane.showMessageDialog(null,e1);
		        }
		       finally{
		    	   try {
		    		   pst.close();
		    		   rs.close();
		    		   con.close();
		    	   }
		        	catch(Exception e2){
		        		
		        	}
		        }
			
				
			}
		});
		btnImportNoFrom.setBounds(307, 182, 315, 23);
		contentPane.add(btnImportNoFrom);
		btnImportNoFrom.setHorizontalAlignment(SwingConstants.TRAILING);
		btnImportNoFrom.setHorizontalTextPosition(SwingConstants.CENTER);
		btnImportNoFrom.setToolTipText("");
		btnImportNoFrom.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel lblCharacter = new JLabel("Character Used");
		lblCharacter.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCharacter.setBounds(508, 397, 111, 17);
		contentPane.add(lblCharacter);
		
		characters = new JLabel("");
		characters.setForeground(new Color(255, 69, 0));
		characters.setFont(new Font("Tahoma", Font.BOLD, 12));
		characters.setBounds(507, 417, 61, 23);
		contentPane.add(characters);
		
		JLabel lblDeductedBalance = new JLabel("Deducted Balance ");
		lblDeductedBalance.setVerticalAlignment(SwingConstants.TOP);
		lblDeductedBalance.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeductedBalance.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDeductedBalance.setBounds(506, 445, 126, 23);
		contentPane.add(lblDeductedBalance);
		
		deducted_blnc = new JLabel("");
		deducted_blnc.setForeground(new Color(255, 69, 0));
		deducted_blnc.setFont(new Font("Tahoma", Font.BOLD, 12));
		deducted_blnc.setBounds(518, 479, 97, 23);
		contentPane.add(deducted_blnc);
		
		JLabel lblFor = new JLabel("For");
		lblFor.setVerticalAlignment(SwingConstants.TOP);
		lblFor.setHorizontalAlignment(SwingConstants.CENTER);
		lblFor.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFor.setBounds(622, 445, 28, 23);
		contentPane.add(lblFor);
		
		JLabel lblPerMessage = new JLabel("Per Message");
		lblPerMessage.setVerticalAlignment(SwingConstants.TOP);
		lblPerMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblPerMessage.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPerMessage.setBounds(508, 461, 88, 19);
		contentPane.add(lblPerMessage);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(30, 144, 255));
		panel_2.setBounds(0, 0, 660, 77);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		//Get_Message();
	}
}
