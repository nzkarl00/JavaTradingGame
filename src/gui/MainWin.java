package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import game.GameManager;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWin {

	private JFrame mainWin;
	private GameManager manager;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public MainWin(GameManager incomingManager) {
		manager = incomingManager;
		initialize();
		mainWin.setVisible(true);
	}
	
	public void closeWindow() {
		mainWin.dispose();
	}
	
	public void finishedWindow() {
		manager.closeMainWindow(this);
	}

	/**
	 * Initialize the contents of the mainWin.
	 */
	private void initialize() {
		mainWin = new JFrame();
		mainWin.setBounds(100, 100, 450, 300);
		mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWin.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Start Game");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton.setBounds(0, 0, 0, 0);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						manager.mainLoop();
					}
				});
			}
		});
		btnNewButton.setBounds(0, 0, 434, 261);
		mainWin.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("So it works now I guess");
		lblNewLabel.setBounds(96, 91, 221, 14);
		mainWin.getContentPane().add(lblNewLabel);
	}
}
