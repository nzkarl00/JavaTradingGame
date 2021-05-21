package gui;
import java.awt.EventQueue;
import game.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.JSlider;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SetupWindow {

	private JFrame window;
	private GameManager manager;
	private JTextField txtfldName;
	private int index;
	
	/**
	 * Create the application.
	 */
	public SetupWindow(GameManager incomingManager) {
		manager = incomingManager;
		initialize();
		window.setVisible(true);
	}
	
	public void closeWindow() {
		window.dispose();
	}
	
	public void finishedWindow() {
		manager.closeSetupWindow(this);
	}
	
	/**
	 * Initialize the contents of the window.
	 */
	private void initialize() {
		window = new JFrame();
		window.setTitle("SENG201 Trading Game Setup");
		window.setBounds(100, 100, 500, 300);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setLayout(null);
		
		JLabel lblName = new JLabel("Trader name:");
		lblName.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblName.setBounds(20, 30, 140, 25);
		window.getContentPane().add(lblName);
		
		txtfldName = new JTextField();
		txtfldName.setBounds(174, 30, 300, 25);
		window.getContentPane().add(txtfldName);
		txtfldName.setColumns(10);
		
		JLabel lblDuration = new JLabel("Game duration:");
		lblDuration.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblDuration.setBounds(20, 95, 140, 25);
		window.getContentPane().add(lblDuration);
		
		JSlider sldrDuration = new JSlider();
		sldrDuration.setValue(20);
		sldrDuration.setSnapToTicks(true);
		sldrDuration.setFont(new Font("Viner Hand ITC", Font.PLAIN, 11));
		sldrDuration.setPaintLabels(true);
		sldrDuration.setMajorTickSpacing(10);
		sldrDuration.setPaintTicks(true);
		sldrDuration.setMinorTickSpacing(1);
		sldrDuration.setMinimum(20);
		sldrDuration.setMaximum(50);
		sldrDuration.setBounds(174, 95, 300, 40);
		window.getContentPane().add(sldrDuration);
		
		JLabel lblShip = new JLabel("Ship:");
		lblShip.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblShip.setBounds(20, 160, 78, 25);
		window.getContentPane().add(lblShip);

		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeWindow();
			}
		});
		btnCancel.setBounds(10, 213, 130, 37);
		window.getContentPane().add(btnCancel);
		
		ButtonGroup shipSelection = new ButtonGroup();
		
		JRadioButton rdbtnSloop = new JRadioButton("");
		rdbtnSloop.setBounds(100, 172, 21, 23);
		window.getContentPane().add(rdbtnSloop);
		shipSelection.add(rdbtnSloop);
		
		JRadioButton rdbtnBrigantine = new JRadioButton("");
		rdbtnBrigantine.setBounds(200, 172, 21, 23);
		window.getContentPane().add(rdbtnBrigantine);
		shipSelection.add(rdbtnBrigantine);
		
		JRadioButton rdbtnGalleon = new JRadioButton("");
		rdbtnGalleon.setBounds(300, 172, 21, 23);
		window.getContentPane().add(rdbtnGalleon);
		shipSelection.add(rdbtnGalleon);
		
		JRadioButton rdbtnCaravel = new JRadioButton("");
		rdbtnCaravel.setBounds(400, 172, 21, 23);
		window.getContentPane().add(rdbtnCaravel);
		shipSelection.add(rdbtnCaravel);
		
		
		JLabel lblSloop = new JLabel("Sloop");
		lblSloop.setHorizontalAlignment(SwingConstants.CENTER);
		lblSloop.setFont(new Font("Viner Hand ITC", Font.PLAIN, 14));
		lblSloop.setBounds(90, 150, 40, 14);
		window.getContentPane().add(lblSloop);
		
		JLabel lblBrigantine = new JLabel("Brigantine");
		lblBrigantine.setHorizontalAlignment(SwingConstants.CENTER);
		lblBrigantine.setFont(new Font("Viner Hand ITC", Font.PLAIN, 14));
		lblBrigantine.setBounds(170, 150, 80, 14);
		window.getContentPane().add(lblBrigantine);
		
		JLabel lblGalleon = new JLabel("Galleon");
		lblGalleon.setHorizontalAlignment(SwingConstants.CENTER);
		lblGalleon.setFont(new Font("Viner Hand ITC", Font.PLAIN, 14));
		lblGalleon.setBounds(280, 150, 60, 14);
		window.getContentPane().add(lblGalleon);
		
		JLabel lblCaravel = new JLabel("Caravel");
		lblCaravel.setFont(new Font("Viner Hand ITC", Font.PLAIN, 14));
		lblCaravel.setBounds(380, 150, 60, 14);
		window.getContentPane().add(lblCaravel);
		
		JLabel lblNameError = new JLabel("");
		lblNameError.setFont(new Font("Yu Gothic UI", Font.PLAIN, 10));
		lblNameError.setForeground(Color.RED);
		lblNameError.setBounds(174, 55, 300, 14);
		window.getContentPane().add(lblNameError);
		
		JLabel lblShipError = new JLabel("");
		lblShipError.setFont(new Font("Yu Gothic UI", Font.PLAIN, 10));
		lblShipError.setHorizontalAlignment(SwingConstants.CENTER);
		lblShipError.setForeground(Color.RED);
		lblShipError.setBounds(187, 192, 147, 25);
		window.getContentPane().add(lblShipError);
		
		JButton btnNewButton = new JButton("Confirm");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtfldName.getText().matches("^[A-Za-z ]{3,15}$") == false) {
					lblNameError.setText("Name must contain 3-15 non-special alphabetical charcters");
					return;
				} else if (shipSelection.getSelection() == null) {
					lblShipError.setText("Please select a ship");
					lblNameError.setText("");
					return;
				} else {
					if (rdbtnSloop.isSelected()) {
						index = 1;
					} else if (rdbtnBrigantine.isSelected()) {
						index = 2;
					} else if (rdbtnGalleon.isSelected()) {
						index = 3;
					} else {
						index = 4;
					}
				}
				manager.configureAdventure(txtfldName.getText(), sldrDuration.getValue(), index);
				finishedWindow();
			}
		});
		btnNewButton.setBounds(344, 213, 130, 37);
		window.getContentPane().add(btnNewButton);
	}
	
}
