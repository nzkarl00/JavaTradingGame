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
import javax.swing.DefaultListModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class MainWindow {

	private JFrame mainWindow;
	private GameManager manager;
	private DefaultListModel<Item> currentSell;
	private DefaultListModel<Item> currentBuy;
	private JLabel lblPlayerMoney;
	private JList<Item> listBuying;
	private JList<Item> listSelling;
	private JTextArea txtrBuyPrices;
	private JTextArea txtrSellPrices;
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
    
    public void closeWindow() {
		mainWindow.dispose();
	}
    
    public void populateStores() {
    	currentSell.clear();
    	currentBuy.clear();
    	for (Item i: manager.currentIsland.getStore().getSellables()) {
    		currentSell.addElement(i);
			}
    	for (Item j: manager.currentIsland.getStore().getBuyables()) {
    		currentBuy.addElement(j);
			}

	}
    
    public void updateStores() {
    	int sellIndex = listSelling.getSelectedIndex();
    	int buyIndex = listBuying.getSelectedIndex();
    	int lenBuy = currentBuy.getSize();
    	int lenSell = currentSell.getSize();
    	currentSell.clear();
    	currentBuy.clear();
    	for (Item i: manager.currentIsland.getStore().getSellables()) {
    		currentSell.addElement(i);
			}
    	for (Item j: manager.currentIsland.getStore().getBuyables()) {
    		currentBuy.addElement(j);
			}
    	if (lenBuy == currentBuy.getSize()) {
    		listBuying.setSelectedIndex(buyIndex);
    		}
    	if (lenSell == currentSell.getSize()) {
    	listSelling.setSelectedIndex(sellIndex);
    		}
    }
    
    public void updateMoney() {
    	lblPlayerMoney.setText(String.valueOf(manager.player.getMoney()));
    }
    
    public void updatePrices() {
    	txtrSellPrices.setText(manager.currentIsland.getStore().sellPriceList());
    	txtrBuyPrices.setText(manager.currentIsland.getStore().buyPriceList());
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
		currentSell = new DefaultListModel<Item>();
		currentBuy = new DefaultListModel<Item>();
		populateStores();
		
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
		
		JLabel lblCurrentLocation = new JLabel("Current Location:");
		lblCurrentLocation.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentLocation.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblCurrentLocation.setBounds(750, 100, 200, 25);
		mainWindow.getContentPane().add(lblCurrentLocation);
		
		JLabel lblActualLocation = new JLabel("Fractured Isle");
		lblActualLocation.setHorizontalAlignment(SwingConstants.CENTER);
		lblActualLocation.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		lblActualLocation.setBounds(750, 125, 200, 25);
		mainWindow.getContentPane().add(lblActualLocation);
		
		JLabel lblBalance = new JLabel("Balance:");
		lblBalance.setHorizontalAlignment(SwingConstants.CENTER);
		lblBalance.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblBalance.setBounds(750, 175, 200, 25);
		mainWindow.getContentPane().add(lblBalance);
		
		lblPlayerMoney = new JLabel("200");
		lblPlayerMoney.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		lblPlayerMoney.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayerMoney.setBounds(750, 200, 200, 25);
		mainWindow.getContentPane().add(lblPlayerMoney);
		
		JLabel lblShipStatus = new JLabel("Ship Status:");
		lblShipStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblShipStatus.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblShipStatus.setBounds(750, 250, 200, 25);
		mainWindow.getContentPane().add(lblShipStatus);
		
		JLabel lblShipHealth = new JLabel("0 / 0");
		lblShipHealth.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		lblShipHealth.setHorizontalAlignment(SwingConstants.CENTER);
		lblShipHealth.setBounds(750, 275, 200, 25);
		mainWindow.getContentPane().add(lblShipHealth);
		
		JLabel lblShipCapacity = new JLabel("0 / 0");
		lblShipCapacity.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		lblShipCapacity.setHorizontalAlignment(SwingConstants.CENTER);
		lblShipCapacity.setBounds(750, 300, 200, 25);
		mainWindow.getContentPane().add(lblShipCapacity);
		
		JLabel lblSelectRoute = new JLabel("Select Route:");
		lblSelectRoute.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblSelectRoute.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectRoute.setBounds(750, 375, 200, 25);
		mainWindow.getContentPane().add(lblSelectRoute);
		
		JList listSelectRoute = new JList();
		listSelectRoute.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSelectRoute.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		listSelectRoute.setBounds(750, 400, 200, 25);
		mainWindow.getContentPane().add(listSelectRoute);
		
		JButton btnSetSail = new JButton("Set Sail!");
		btnSetSail.setFont(new Font("Viner Hand ITC", Font.BOLD, 32));
		btnSetSail.setBounds(750, 450, 200, 200);
		mainWindow.getContentPane().add(btnSetSail);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 450, 730, 200);
		mainWindow.getContentPane().add(scrollPane);
		
		JTextArea txtrStoreOutput = new JTextArea();
		scrollPane.setViewportView(txtrStoreOutput);
		txtrStoreOutput.setEditable(false);
		txtrStoreOutput.setFont(new Font("Monospaced", Font.BOLD, 16));
		txtrStoreOutput.setForeground(Color.WHITE);
		txtrStoreOutput.setBackground(Color.BLACK);
		txtrStoreOutput.setText("Welcome to the store!");
		txtrStoreOutput.setRows(5);
		
		JLabel lblStoreSelling = new JLabel("Store is selling:");
		lblStoreSelling.setFont(new Font("Viner Hand ITC", Font.BOLD, 24));
		lblStoreSelling.setBounds(10, 10, 200, 40);
		mainWindow.getContentPane().add(lblStoreSelling);
		
		JLabel lblStoreBuying = new JLabel("Store is buying:");
		lblStoreBuying.setFont(new Font("Viner Hand ITC", Font.BOLD, 24));
		lblStoreBuying.setBounds(370, 10, 200, 40);
		mainWindow.getContentPane().add(lblStoreBuying);
		
		listBuying = new JList<Item>(currentBuy);
		listBuying.setVisibleRowCount(5);
		listBuying.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listBuying.setFont(new Font("Monospaced", Font.BOLD, 18));
		listBuying.setForeground(Color.WHITE);
		listBuying.setBackground(Color.BLACK);
		listBuying.setBounds(10, 55, 300, 150);
		mainWindow.getContentPane().add(listBuying);
		
		JButton btnBuy = new JButton("Buy");
		btnBuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listBuying.getSelectedValue() == null) {
					return;
				}
				String transaction = manager.buyItem((Item) listBuying.getSelectedValue());
				if (transaction != "fail")  {
					txtrStoreOutput.append("\n" + transaction);
					updateMoney();
				updateStores();
				updatePrices();
				}
			}
		});
		btnBuy.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		btnBuy.setBounds(10, 215, 350, 40);
		mainWindow.getContentPane().add(btnBuy);
		
		listSelling = new JList<Item>(currentSell);
		listSelling.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSelling.setVisibleRowCount(5);
		listSelling.setFont(new Font("Monospaced", Font.BOLD, 18));
		listSelling.setForeground(Color.WHITE);
		listSelling.setBackground(Color.BLACK);
		listSelling.setBounds(370, 55, 300, 150);
		mainWindow.getContentPane().add(listSelling);
		
		JButton btnSell = new JButton("Sell");
		btnSell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listSelling.getSelectedValue() == null) {
					return;
				}
				String transaction = manager.sellItem((Item) listSelling.getSelectedValue());
				if (transaction != "fail")  {
					txtrStoreOutput.append("\n" + transaction);
					updateMoney();
				updateStores();
				updatePrices();
				}
			}
		});
		btnSell.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		btnSell.setBounds(370, 215, 350, 40);
		mainWindow.getContentPane().add(btnSell);
		
		JLabel lblShipUpgrades = new JLabel("Ship Upgrades:");
		lblShipUpgrades.setFont(new Font("Viner Hand ITC", Font.BOLD, 24));
		lblShipUpgrades.setBounds(10, 280, 350, 25);
		mainWindow.getContentPane().add(lblShipUpgrades);
		
		JButton btnArmor1 = new JButton("Armor 1");
		btnArmor1.setBounds(10, 325, 100, 40);
		mainWindow.getContentPane().add(btnArmor1);
		
		JButton btnArmor2 = new JButton("Armor 2");
		btnArmor2.setBounds(135, 325, 100, 40);
		mainWindow.getContentPane().add(btnArmor2);
		
		JButton btnArmor3 = new JButton("Armor 3");
		btnArmor3.setBounds(260, 325, 100, 40);
		mainWindow.getContentPane().add(btnArmor3);
		
		JButton btnCannon1 = new JButton("Cannon 1");
		btnCannon1.setBounds(10, 390, 100, 40);
		mainWindow.getContentPane().add(btnCannon1);
		
		JButton btnCannon2 = new JButton("Cannon 2");
		btnCannon2.setBounds(135, 390, 100, 40);
		mainWindow.getContentPane().add(btnCannon2);
		
		JButton btnCannon3 = new JButton("Cannon 3");
		btnCannon3.setBounds(260, 390, 100, 40);
		mainWindow.getContentPane().add(btnCannon3);
		
		JLabel lblPlayerInfo = new JLabel("Player Info:");
		lblPlayerInfo.setFont(new Font("Viner Hand ITC", Font.BOLD, 24));
		lblPlayerInfo.setBounds(370, 280, 350, 25);
		mainWindow.getContentPane().add(lblPlayerInfo);
		
		JButton btnShipProperties = new JButton("View Ship Properties");
		btnShipProperties.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		btnShipProperties.setBounds(370, 325, 350, 25);
		mainWindow.getContentPane().add(btnShipProperties);
		
		JButton btnTransactionHistory = new JButton("View Transaction History");
		btnTransactionHistory.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		btnTransactionHistory.setBounds(370, 365, 350, 25);
		mainWindow.getContentPane().add(btnTransactionHistory);
		
		JButton btnIslandProperties = new JButton("View Island Properties");
		btnIslandProperties.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		btnIslandProperties.setBounds(370, 405, 350, 25);
		mainWindow.getContentPane().add(btnIslandProperties);
		
		txtrSellPrices = new JTextArea(manager.currentIsland.getStore().sellPriceList());
		txtrSellPrices.setEditable(false);
		txtrSellPrices.setRows(5);
		txtrSellPrices.setFont(new Font("Monospaced", Font.BOLD, 20));
		txtrSellPrices.setBackground(Color.BLACK);
		txtrSellPrices.setForeground(Color.WHITE);
		txtrSellPrices.setBounds(670, 55, 50, 150);
		mainWindow.getContentPane().add(txtrSellPrices);
		
		txtrBuyPrices = new JTextArea(manager.currentIsland.getStore().buyPriceList());
		txtrBuyPrices.setRows(5);
		txtrBuyPrices.setFont(new Font("Monospaced", Font.BOLD, 20));
		txtrBuyPrices.setForeground(Color.WHITE);
		txtrBuyPrices.setBackground(Color.BLACK);
		txtrBuyPrices.setBounds(310, 55, 50, 150);
		mainWindow.getContentPane().add(txtrBuyPrices);
	}
}
