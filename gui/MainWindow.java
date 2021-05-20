package gui;
import java.awt.EventQueue;
import game.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.WindowEvent;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.JList;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.DropMode;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;

public class MainWindow {

	private JFrame mainWindow;
	private GameManager manager;
	/**
	 * Create the application.
	 */
	public MainWindow(GameManager incomingManager) {
		manager = incomingManager;
		initialize();
		mainWindow.setVisible(true);
	}
	
    public void worker() {
    	SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				manager.mainLoop();
				return null;
			}
		};
		worker.execute();
    }
	
	/**
	 * Initialize the contents of the mainWindow.
	 */
	private void initialize() {
				
		mainWindow = new JFrame();
		mainWindow.setTitle("SENG201 Trading Game");
		mainWindow.setResizable(false);
		mainWindow.setBounds(100, 100, 1000, 700);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.getContentPane().setLayout(null);
		
		JLabel lblDaysRemaining = new JLabel("Days Remaining:");
		lblDaysRemaining.setHorizontalAlignment(SwingConstants.CENTER);
		lblDaysRemaining.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblDaysRemaining.setBounds(750, 25, 200, 25);
		mainWindow.getContentPane().add(lblDaysRemaining);
		
		JLabel lblCurrentDays = new JLabel(String.valueOf(manager.daysLeft));
		lblCurrentDays.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentDays.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		lblCurrentDays.setBounds(750, 50, 206, 25);
		mainWindow.getContentPane().add(lblCurrentDays);
		
		JLabel lblNewLabel = new JLabel("Current Location:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblNewLabel.setBounds(750, 100, 200, 25);
		mainWindow.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Fractured Isle");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		lblNewLabel_1.setBounds(750, 125, 200, 25);
		mainWindow.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Balance:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblNewLabel_2.setBounds(750, 175, 200, 25);
		mainWindow.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("200");
		lblNewLabel_3.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(750, 200, 200, 25);
		mainWindow.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Ship Health:");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblNewLabel_4.setBounds(750, 250, 200, 25);
		mainWindow.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("%");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(750, 275, 200, 25);
		mainWindow.getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Select Route:");
		lblNewLabel_6.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setBounds(750, 375, 200, 25);
		mainWindow.getContentPane().add(lblNewLabel_6);
		
		JList list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		list.setBounds(750, 400, 200, 25);
		mainWindow.getContentPane().add(list);
		
		JButton btnNewButton = new JButton("Set Sail!");
		btnNewButton.setFont(new Font("Viner Hand ITC", Font.BOLD, 32));
		btnNewButton.setBounds(750, 450, 200, 200);
		mainWindow.getContentPane().add(btnNewButton);
		
		JTextArea txtrWelcomeToThe = new JTextArea();
		txtrWelcomeToThe.setEditable(false);
		txtrWelcomeToThe.setFont(new Font("Monospaced", Font.BOLD, 16));
		txtrWelcomeToThe.setForeground(Color.WHITE);
		txtrWelcomeToThe.setBackground(Color.BLACK);
		txtrWelcomeToThe.setText("Welcome to the store!");
		txtrWelcomeToThe.setRows(5);
		txtrWelcomeToThe.setBounds(10, 450, 730, 200);
		mainWindow.getContentPane().add(txtrWelcomeToThe);
		
		JLabel lblNewLabel_7 = new JLabel("Store is buying:");
		lblNewLabel_7.setFont(new Font("Viner Hand ITC", Font.BOLD, 24));
		lblNewLabel_7.setBounds(10, 10, 200, 40);
		mainWindow.getContentPane().add(lblNewLabel_7);
		
		JLabel lblNewLabel_7_1 = new JLabel("Store is selling:");
		lblNewLabel_7_1.setFont(new Font("Viner Hand ITC", Font.BOLD, 24));
		lblNewLabel_7_1.setBounds(370, 10, 200, 40);
		mainWindow.getContentPane().add(lblNewLabel_7_1);
		
		JButton btnNewButton_1 = new JButton("Buy");
		btnNewButton_1.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		btnNewButton_1.setBounds(10, 215, 350, 40);
		mainWindow.getContentPane().add(btnNewButton_1);
		
		JList list_1 = new JList();
		list_1.setVisibleRowCount(5);
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_1.setFont(new Font("Monospaced", Font.BOLD, 18));
		list_1.setModel(new AbstractListModel() {
			String[] values = new String[] {" Silk", " Wine", " Linen", " Saffron", " Cinnamon"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list_1.setForeground(Color.WHITE);
		list_1.setBackground(Color.BLACK);
		list_1.setBounds(10, 55, 350, 150);
		mainWindow.getContentPane().add(list_1);
		
		JList list_1_1 = new JList();
		list_1_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_1_1.setVisibleRowCount(5);
		list_1_1.setFont(new Font("Monospaced", Font.BOLD, 18));
		list_1_1.setModel(new AbstractListModel() {
			String[] values = new String[] {" Silk", " Wine", " Linen", " Saffron", " Cinnamon"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list_1_1.setForeground(Color.WHITE);
		list_1_1.setBackground(Color.BLACK);
		list_1_1.setBounds(370, 53, 350, 150);
		mainWindow.getContentPane().add(list_1_1);
		
		JButton btnNewButton_2 = new JButton("Sell");
		btnNewButton_2.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		btnNewButton_2.setBounds(370, 215, 350, 40);
		mainWindow.getContentPane().add(btnNewButton_2);
		
		JLabel lblNewLabel_8 = new JLabel("Ship Upgrades:");
		lblNewLabel_8.setFont(new Font("Viner Hand ITC", Font.BOLD, 24));
		lblNewLabel_8.setBounds(10, 280, 350, 25);
		mainWindow.getContentPane().add(lblNewLabel_8);
		
		JButton btnNewButton_3 = new JButton("Armor 1");
		btnNewButton_3.setBounds(10, 325, 100, 40);
		mainWindow.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Armor 2");
		btnNewButton_4.setBounds(135, 325, 100, 40);
		mainWindow.getContentPane().add(btnNewButton_4);
		
		JButton btnNewButton_4_1 = new JButton("Armor 3");
		btnNewButton_4_1.setBounds(260, 325, 100, 40);
		mainWindow.getContentPane().add(btnNewButton_4_1);
		
		JButton btnNewButton_3_1 = new JButton("Cannon 1");
		btnNewButton_3_1.setBounds(10, 390, 100, 40);
		mainWindow.getContentPane().add(btnNewButton_3_1);
		
		JButton btnNewButton_3_2 = new JButton("Cannon 2");
		btnNewButton_3_2.setBounds(135, 390, 100, 40);
		mainWindow.getContentPane().add(btnNewButton_3_2);
		
		JButton btnNewButton_3_3 = new JButton("Cannon 3");
		btnNewButton_3_3.setBounds(260, 390, 100, 40);
		mainWindow.getContentPane().add(btnNewButton_3_3);
		
		JLabel lblNewLabel_8_1 = new JLabel("Player Info:");
		lblNewLabel_8_1.setFont(new Font("Viner Hand ITC", Font.BOLD, 24));
		lblNewLabel_8_1.setBounds(370, 280, 350, 25);
		mainWindow.getContentPane().add(lblNewLabel_8_1);
		
		JButton btnNewButton_5 = new JButton("View Ship Properties");
		btnNewButton_5.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		btnNewButton_5.setBounds(370, 325, 350, 25);
		mainWindow.getContentPane().add(btnNewButton_5);
		
		JButton btnNewButton_5_1 = new JButton("View Transaction History");
		btnNewButton_5_1.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		btnNewButton_5_1.setBounds(370, 365, 350, 25);
		mainWindow.getContentPane().add(btnNewButton_5_1);
		
		JButton btnNewButton_5_2 = new JButton("View Island Properties");
		btnNewButton_5_2.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		btnNewButton_5_2.setBounds(370, 405, 350, 25);
		mainWindow.getContentPane().add(btnNewButton_5_2);
		
		
	}
}
