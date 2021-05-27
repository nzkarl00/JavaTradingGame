package gui;
import game.*;
import islands.IslandRoute;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
/**
 * The main window of the game, a GUI for the player to perform all functions in the game.
 * 
 * Functions of the main window include:
 * Calling item purchases and sales
 * Displaying prices of goods at the current store
 * Displaying all player and ship information
 * Selecting routes and sailing to a new island
 * Printing all actions taken in the game and results of the actions into a text window
 * Providing means to open other information windows
 * 
 * Variables:
 * mainWindow: JFrame containing everything in the main window
 * manager: GameManager that handles every call from the main window, then uses other classes for game logic
 * currentSell: DefaultListModel<Item> that contains the current items the store of the current island is selling
 * currentBuy: DefaultListModel<Item> that contains the current items the store of the current island is buying
 * lblPlayerMoney: JLabel to display the player's current money at all times
 * listBuying: JList<Item> that displays currentBuy
 * listSelling: JList<Item> that displays currentSell
 * txtrBuyPrices: JTextArea to hold all of the prices of items being purchased by the store of the current island
 * txtrSellPrices: JTextArea to hold all of the prices of items being sold by the store of the current island
 * lblShipCapacity: JLabel that displays the current and total ship capacity at all times
 * comboSelectRoute: JComboBox<String> for the player to select the route they wish to travel
 * lblActualLocation: JLabel to display the current location of the player
 * lblShipHealth: JLabel to display player's ship health state
 * lblCurrentDays: JLabel to display days remaining in the game
 * btnRepairShip: JButton to call a method for repairing the ship
 * btnSetSail: JButton to call a method for traveling to a new island
 * txtrStoreOutptut: JTextArea that print events of the game as they happen, including purchases, sales and encounters
 * @author Seagull
 *
 */
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
	private JLabel lblShipCapacity;
	private JComboBox<String> comboSelectRoute;
	private JLabel lblActualLocation;
	private JLabel lblShipHealth;
	private JLabel lblCurrentDays;
	private JButton btnRepairShip;
	private JButton btnSetSail;
	private JTextArea txtrStoreOutput;
	
	/**
	 * Create the application.
	 */
	public MainWindow(GameManager incomingManager) {
		manager = incomingManager;
		initialize();
		mainWindow.setVisible(true);
	}
    
	/**
	 * Closes the application.
	 */
    public void closeWindow() {
		mainWindow.dispose();
	}
    
    /**
     * Populates currentBuy and currentSell with up to date information from the current island, called after sailing to a new island.
     */
    public void populateStores() {
    	currentSell.clear();
    	currentBuy.clear();
    	for (Item i: manager.getPlayer().getCurrentIsland().getStore().getSellables()) {	//Iterates over sellable items in the store of the current island
    		currentSell.addElement(i);
			}
    	for (Item j: manager.getPlayer().getCurrentIsland().getStore().getBuyables()) {		//Iterates over buyable items in the store of the current island
    		currentBuy.addElement(j);
			}

	}
    
    /**
     * Updates the routes in comboSelectRoute with all the routes from the current island, called after sailing to a new island.
     */
    public void updateRoutes() {
    	comboSelectRoute.removeAllItems();
    	for (IslandRoute i: manager.getPlayer().getCurrentIsland().getRoutes()) {	//Iterates over the routes from the current island
    		if (i.getDaysToTravel(manager.getPlayer().getShip().getSpeed()) <= manager.daysLeft) {	//Only adds routes with enough time left in the game to sail
    		comboSelectRoute.addItem(i.shortString(manager.getPlayer().getShip().getSpeed()));
    		}
		}
    }
    
    /**
     * Updates currentSell and currentBuy with elements from the buying and selling lists from the current island.
     * Maintains the selected index if the lists are unchanged, called after every transaction.
     */
    public void updateStores() {
    	int sellIndex = listSelling.getSelectedIndex();
    	int buyIndex = listBuying.getSelectedIndex();
    	int lenBuy = currentBuy.getSize();
    	int lenSell = currentSell.getSize();
    	currentSell.clear();
    	currentBuy.clear();
    	for (Item i: manager.getPlayer().getCurrentIsland().getStore().getSellables()) {	//Iterates over the routes from the current island
    		currentSell.addElement(i);
			}
    	for (Item j: manager.getPlayer().getCurrentIsland().getStore().getBuyables()) {		//Only adds routes with enough time left in the game to sail
    		currentBuy.addElement(j);
			}
    	if (lenBuy == currentBuy.getSize()) {
    		listBuying.setSelectedIndex(buyIndex);
    		}
    	if (lenSell == currentSell.getSize()) {
    	listSelling.setSelectedIndex(sellIndex);
    		}
    }
    
    /**
     * Updates lblPlayerMoney with the current value, called after every purchase.
     */
    public void updateMoney() {
    	lblPlayerMoney.setText("$" + String.valueOf(manager.getPlayer().getMoney()));
    }
    
    /**
     * Updates the prices in txtrSellPrices and txtrBuyPrices with the current values, called after every transaction.
     */
    public void updatePrices() {
    	txtrSellPrices.setText(manager.getPlayer().getCurrentIsland().getStore().sellPriceList());	//Gets the prices of all sellables at current island store
    	txtrBuyPrices.setText(manager.getPlayer().getCurrentIsland().getStore().buyPriceList());	//Gets the prices of all buyables at the current island store
    }
    
    /**
     * Updates lblShipCapacity with the current capacity, called after every transaction
     */
    public void updateCapacity() {
    	lblShipCapacity.setText("Capacity: " + (manager.getPlayer().getShip().getMaxCapacity() - manager.getPlayer().getShip().getRemainingCapacity())
    			+ " / " +  manager.getPlayer().getShip().getMaxCapacity()); 	//Maximum capacity of player ship - remaining capacity of current ship to get current capacity
    }
    
    /**
     * Updates the health state of the ship in lblShipHealth, called after traveling to a new island.
     */
    public void updateHealth() {
    	if (manager.getPlayer().getShip().getDamageState() == true) {
    		lblShipHealth.setText("Damaged");
    		btnRepairShip.setEnabled(true);
    		btnSetSail.setEnabled(false);
    		btnSetSail.setText("Ship Damaged!");
    		btnSetSail.setFont(new Font("Viner Hand ITC", Font.BOLD, 24));
    	} else {
    		lblShipHealth.setText("Fully repaired");
    		btnSetSail.setEnabled(true);
    		btnSetSail.setText("Set Sail!");
    		btnSetSail.setFont(new Font("Viner Hand ITC", Font.BOLD, 32));
    	}
    }
    
    /**
     * Updates the number of days left in the game, called after traveling to a new island.
     */
    public void updateDays() {
    	lblCurrentDays.setText(String.valueOf(manager.daysLeft));
    }
    
    /**
     * Prints text passed into the method into txtrStoreOutput.
     * @param text String text to be printed
     */
    public void printEncounter(String text) {
    	txtrStoreOutput.append(text);
    }
    
    /**
     * Checks to see if the game has ended, called after moving to a new island.
     */
    public void checkGameEnd() {
    	String endCheck = manager.checkGameEnd();
    	if (endCheck != "continue") {
    		if (endCheck.contains("pirates")) {
    			//Game end popup for pirate ending
    			int n = JOptionPane.showOptionDialog(mainWindow, endCheck + "\n You lost" + "\nPlay again?", 
    					"Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
    			if (n == 0) {
    				Main.main(null);
    				closeWindow();
    			} else {
    				System.exit(0);
    			}
    		} else if (endCheck.contains("money")) {
    			//Game end popup for out of money ending
    			int n = JOptionPane.showOptionDialog(mainWindow, endCheck + "\nYour score was: " + manager.getPlayer().getMoney() 
    					+ "\nPlay again?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
        		if (n == 0) {
        			Main.main(null);
        			closeWindow();
        		} else {
        			System.exit(0);
        		}
    		} else {
    			//Game end popup for time ending
    			int n = JOptionPane.showOptionDialog(mainWindow, endCheck + "\nYour goods were automatically sold\nYour score was: "
    			//Current money + asset value
    			+ (manager.getPlayer().getMoney() + manager.getPlayer().getShip().getGoodsValue(manager.getPlayer().getCurrentIsland().getStore())) 
    			+ "\nPlay again?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
        		if (n == 0) {
        			Main.main(null);
        			closeWindow();
        		} else {
        			System.exit(0);
        		}
    		}
    	}
    }
	/**
	 * Initialize the contents of the mainWindow.
	 */
	private void initialize() {
				
		mainWindow = new JFrame();
		mainWindow.setTitle(manager.getPlayer().getName() + "'s Trading Adventure");
		mainWindow.setResizable(false);
		mainWindow.setBounds(100, 100, 1000, 700);
		mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainWindow.getContentPane().setLayout(null);
		currentSell = new DefaultListModel<Item>();
		currentBuy = new DefaultListModel<Item>();
		populateStores();
		
		JLabel lblDaysRemaining = new JLabel("Days Remaining:");
		lblDaysRemaining.setHorizontalAlignment(SwingConstants.CENTER);
		lblDaysRemaining.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblDaysRemaining.setBounds(750, 25, 200, 25);
		mainWindow.getContentPane().add(lblDaysRemaining);
		
		lblCurrentDays = new JLabel(String.valueOf(manager.daysLeft));
		lblCurrentDays.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentDays.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		lblCurrentDays.setBounds(750, 50, 206, 25);
		mainWindow.getContentPane().add(lblCurrentDays);
		
		JLabel lblCurrentLocation = new JLabel("Current Location:");
		lblCurrentLocation.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentLocation.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblCurrentLocation.setBounds(750, 100, 200, 25);
		mainWindow.getContentPane().add(lblCurrentLocation);
		
		lblActualLocation = new JLabel("Fractured Isle");
		lblActualLocation.setHorizontalAlignment(SwingConstants.CENTER);
		lblActualLocation.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		lblActualLocation.setBounds(750, 125, 200, 25);
		mainWindow.getContentPane().add(lblActualLocation);
		
		JLabel lblBalance = new JLabel("Balance:");
		lblBalance.setHorizontalAlignment(SwingConstants.CENTER);
		lblBalance.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblBalance.setBounds(750, 175, 200, 25);
		mainWindow.getContentPane().add(lblBalance);
		
		lblPlayerMoney = new JLabel("$500");
		lblPlayerMoney.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		lblPlayerMoney.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayerMoney.setBounds(750, 200, 200, 25);
		mainWindow.getContentPane().add(lblPlayerMoney);
		
		JLabel lblShipStatus = new JLabel("Ship Status:");
		lblShipStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblShipStatus.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblShipStatus.setBounds(750, 250, 200, 25);
		mainWindow.getContentPane().add(lblShipStatus);
		
		lblShipHealth = new JLabel("Fully Repaired");
		lblShipHealth.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		lblShipHealth.setHorizontalAlignment(SwingConstants.CENTER);
		lblShipHealth.setBounds(750, 310, 200, 25);
		mainWindow.getContentPane().add(lblShipHealth);
		
		lblShipCapacity = new JLabel("Capacity: " + (manager.getPlayer().getShip().getMaxCapacity() - manager.getPlayer().getShip().getRemainingCapacity()) + " / " +  manager.getPlayer().getShip().getMaxCapacity());
		lblShipCapacity.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		lblShipCapacity.setHorizontalAlignment(SwingConstants.CENTER);
		lblShipCapacity.setBounds(750, 280, 200, 25);
		mainWindow.getContentPane().add(lblShipCapacity);
		
		JLabel lblSelectRoute = new JLabel("Select Route:");
		lblSelectRoute.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblSelectRoute.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectRoute.setBounds(750, 375, 200, 25);
		mainWindow.getContentPane().add(lblSelectRoute);
		
		JLabel lblDailyCrewCost = new JLabel("Daily crew cost: " + String.valueOf(manager.getPlayer().getShip().getCrewTravelCost(1)));
		lblDailyCrewCost.setHorizontalAlignment(SwingConstants.CENTER);
		lblDailyCrewCost.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblDailyCrewCost.setBounds(730, 625, 244, 25);
		mainWindow.getContentPane().add(lblDailyCrewCost);
		
		btnSetSail = new JButton("Set Sail!");
		btnSetSail.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manager.sailToIsland(manager.getPlayer().getCurrentIsland().getRoutes().get(comboSelectRoute.getSelectedIndex()), manager.notifier);
				lblActualLocation.setText(manager.getPlayer().getCurrentIsland().getName());	//Updates actual location label
				updateRoutes();
				populateStores();
				updateMoney();
				updateDays();
				updateHealth();
				updatePrices();
				checkGameEnd();
			}
		});
		btnSetSail.setFont(new Font("Viner Hand ITC", Font.BOLD, 32));
		btnSetSail.setBounds(730, 450, 244, 170);
		mainWindow.getContentPane().add(btnSetSail);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 450, 710, 200);
		mainWindow.getContentPane().add(scrollPane);
		
		txtrStoreOutput = new JTextArea();
		scrollPane.setViewportView(txtrStoreOutput);
		txtrStoreOutput.setEditable(false);
		txtrStoreOutput.setFont(new Font("Monospaced", Font.BOLD, 16));
		txtrStoreOutput.setForeground(Color.WHITE);
		txtrStoreOutput.setBackground(Color.BLACK);
		txtrStoreOutput.setText("Welcome to the store!\n");
		txtrStoreOutput.setRows(5);
		
		JLabel lblStoreSelling = new JLabel("Store is selling:");
		lblStoreSelling.setFont(new Font("Viner Hand ITC", Font.BOLD, 24));
		lblStoreSelling.setBounds(10, 10, 350, 40);
		mainWindow.getContentPane().add(lblStoreSelling);
		
		JLabel lblStoreBuying = new JLabel("Store is buying:");
		lblStoreBuying.setFont(new Font("Viner Hand ITC", Font.BOLD, 24));
		lblStoreBuying.setBounds(370, 10, 350, 40);
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
			@Override
			public void actionPerformed(ActionEvent e) {
				if (listBuying.getSelectedValue() == null) {
					return;
				}
				String transaction = manager.buyItem(listBuying.getSelectedValue());
				if (transaction != "fail")  {	//Print transaction text if it was successful
					txtrStoreOutput.append(transaction + "\n");
					updateMoney();
					updateCapacity();
				updateStores();
				updatePrices();
				checkGameEnd();
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
			@Override
			public void actionPerformed(ActionEvent e) {
				if (listSelling.getSelectedValue() == null) {	//Print transaction text if it was successful
					return;
				}
				String transaction = manager.sellItem(listSelling.getSelectedValue());
				if (transaction != "fail")  {
					txtrStoreOutput.append(transaction + "\n");
					updateMoney();
					updateCapacity();
				updateStores();
				updatePrices();
				checkGameEnd();
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
		
		JButton btnCannon1 = new JButton("Cannon 1");
		btnCannon1.setFont(new Font("Viner Hand ITC", Font.BOLD, 13));
		btnCannon1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String transaction = manager.upgrades.purchaseItem(manager.upgradeableItems.get(0), manager.getPlayer()); //Buy first cannon upgrade
				if (transaction != "fail")  {
					txtrStoreOutput.append(transaction + "\n");
					updateMoney();
					updatePrices();
					checkGameEnd();
					btnCannon1.setEnabled(false);
				}
			}
		});
		btnCannon1.setBounds(10, 339, 110, 91);
		mainWindow.getContentPane().add(btnCannon1);
		
		JButton btnCannon2 = new JButton("Cannon 2");
		btnCannon2.setFont(new Font("Viner Hand ITC", Font.BOLD, 13));
		btnCannon2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String transaction = manager.upgrades.purchaseItem(manager.upgradeableItems.get(1), manager.getPlayer());	//Buy second cannon upgrade
				if (transaction != "fail")  {
					txtrStoreOutput.append(transaction + "\n");
					updateMoney();
					updatePrices();
					checkGameEnd();
					btnCannon2.setEnabled(false);
				}
			}
		});
		btnCannon2.setBounds(130, 339, 110, 91);
		mainWindow.getContentPane().add(btnCannon2);
		
		JButton btnCannon3 = new JButton("Cannon 3 ");
		btnCannon3.setFont(new Font("Viner Hand ITC", Font.BOLD, 13));
		btnCannon3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String transaction = manager.upgrades.purchaseItem(manager.upgradeableItems.get(2), manager.getPlayer());	//Buy third cannon upgrade
				if (transaction != "fail")  {
					txtrStoreOutput.append(transaction + "\n");
					updateMoney();
					updatePrices();
					checkGameEnd();
					btnCannon3.setEnabled(false);
				}
			}
		});
		btnCannon3.setBounds(250, 339, 110, 91);
		mainWindow.getContentPane().add(btnCannon3);
		
		JLabel lblPlayerInfo = new JLabel("Player Info:");
		lblPlayerInfo.setFont(new Font("Viner Hand ITC", Font.BOLD, 24));
		lblPlayerInfo.setBounds(370, 280, 350, 25);
		mainWindow.getContentPane().add(lblPlayerInfo);
		
		JButton btnShipProperties = new JButton("View Ship Properties");
		btnShipProperties.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				JOptionPane.showMessageDialog(mainWindow, manager.getPlayer().getShip().toString());
			}
		});
		btnShipProperties.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		btnShipProperties.setBounds(370, 325, 350, 25);
		mainWindow.getContentPane().add(btnShipProperties);
		
		JButton btnTransactionHistory = new JButton("View Transaction History");
		btnTransactionHistory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea txtrTransactions = new JTextArea(manager.getTransactionHistory());
				txtrTransactions.setBackground(UIManager.getColor("Button.background"));
				txtrTransactions.setFont(new Font("monospace", Font.BOLD, 11));
				JScrollPane spTransactions = new JScrollPane(txtrTransactions);
				spTransactions.setPreferredSize(new Dimension(300, 400));
				JOptionPane.showMessageDialog(mainWindow, spTransactions);
			}
		});
		btnTransactionHistory.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		btnTransactionHistory.setBounds(370, 365, 350, 25);
		mainWindow.getContentPane().add(btnTransactionHistory);
		
		JButton btnIslandProperties = new JButton("View Islands");
		btnIslandProperties.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel islandDialog = new IslandDialog(manager);
				islandDialog.setPreferredSize(new Dimension(860, 666));
				JOptionPane.showMessageDialog(mainWindow, islandDialog);
			}
		});
		btnIslandProperties.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		btnIslandProperties.setBounds(370, 405, 350, 25);
		mainWindow.getContentPane().add(btnIslandProperties);
		
		txtrSellPrices = new JTextArea(manager.getPlayer().getCurrentIsland().getStore().sellPriceList());
		txtrSellPrices.setEditable(false);
		txtrSellPrices.setRows(5);
		txtrSellPrices.setFont(new Font("Monospaced", Font.BOLD, 20));
		txtrSellPrices.setBackground(Color.BLACK);
		txtrSellPrices.setForeground(Color.WHITE);
		txtrSellPrices.setBounds(670, 55, 50, 150);
		mainWindow.getContentPane().add(txtrSellPrices);
		
		txtrBuyPrices = new JTextArea(manager.getPlayer().getCurrentIsland().getStore().buyPriceList());
		txtrBuyPrices.setRows(5);
		txtrBuyPrices.setFont(new Font("Monospaced", Font.BOLD, 20));
		txtrBuyPrices.setForeground(Color.WHITE);
		txtrBuyPrices.setBackground(Color.BLACK);
		txtrBuyPrices.setBounds(310, 55, 50, 150);
		mainWindow.getContentPane().add(txtrBuyPrices);
		
		JLabel lblCannonOnePrice = new JLabel("$105");
		lblCannonOnePrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblCannonOnePrice.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		lblCannonOnePrice.setBounds(10, 316, 110, 25);
		mainWindow.getContentPane().add(lblCannonOnePrice);
		
		JLabel lblCannonTwoPrice = new JLabel("$150");
		lblCannonTwoPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblCannonTwoPrice.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		lblCannonTwoPrice.setBounds(130, 316, 110, 25);
		mainWindow.getContentPane().add(lblCannonTwoPrice);
		
		JLabel lblCannonThreePrice = new JLabel("$225");
		lblCannonThreePrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblCannonThreePrice.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		lblCannonThreePrice.setBounds(250, 316, 110, 25);
		mainWindow.getContentPane().add(lblCannonThreePrice);
		
		comboSelectRoute = new JComboBox<String>();
		updateRoutes();
		comboSelectRoute.setBounds(730, 405, 244, 25);
		mainWindow.getContentPane().add(comboSelectRoute);
		
		btnRepairShip = new JButton("Repair Ship - $50");
		btnRepairShip.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String transaction = manager.repairShip();
				if (transaction != "fail")  { // If transaction was successful
					txtrStoreOutput.append(transaction + "\n");
					updateMoney();
					btnRepairShip.setEnabled(false);
					updateHealth();
					checkGameEnd();
				}
			}
		});
		btnRepairShip.setEnabled(false);
		btnRepairShip.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		btnRepairShip.setBounds(730, 340, 244, 23);
		mainWindow.getContentPane().add(btnRepairShip);
	}
}
