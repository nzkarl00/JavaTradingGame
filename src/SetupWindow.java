import java.awt.EventQueue;

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
	private JTextField textField;
	private static DefaultListModel<Ship> ships;
	
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
		// manager.closeSetupWindow(this);
	}
	
	public static void addShip(Ship ship) { 	
		ships.addElement(ship);
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
		
		textField = new JTextField();
		textField.setBounds(174, 30, 300, 25);
		window.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblDuration = new JLabel("Game duration:");
		lblDuration.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblDuration.setBounds(20, 95, 140, 25);
		window.getContentPane().add(lblDuration);
		
		JSlider slider = new JSlider();
		slider.setSnapToTicks(true);
		slider.setFont(new Font("Viner Hand ITC", Font.PLAIN, 11));
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setMinorTickSpacing(1);
		slider.setMinimum(20);
		slider.setMaximum(50);
		slider.setBounds(174, 95, 300, 40);
		window.getContentPane().add(slider);
		
		JLabel lblShip = new JLabel("Ship:");
		lblShip.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblShip.setBounds(20, 160, 78, 25);
		window.getContentPane().add(lblShip);
		
		JButton btnNewButton = new JButton("Confirm");
		btnNewButton.setBounds(344, 213, 130, 37);
		window.getContentPane().add(btnNewButton);
		
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
	}
}
