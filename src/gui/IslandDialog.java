package gui;

import javax.swing.JPanel;

import game.GameManager;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * Panel to be put in a popup window displaying the current state of every island in the game.
 * Displays all buying and selling prices, as well as all routes to each island from the current.
 */
public class IslandDialog extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5680641284238776584L;
	private GameManager manager;
	private JTextArea txtrFIRoutes;
	private JTextArea txtrSRRoutes;
	private JTextArea txtrPCRoutes;
	private JTextArea txtrVIRoutes;
	private JTextArea txtrSERoutes;
	private JTextArea txtrFIBuy;
	private JTextArea txtrFISell;
	private JTextArea txtrSRBuy;
	private JTextArea txtrSRSell;
	private JTextArea txtrPCBuy;
	private JTextArea txtrPCSell;
	private JTextArea txtrVIBuy;
	private JTextArea txtrVISell;
	private JTextArea txtrSEBuy;
	private JTextArea txtrSESell;
	
	/**
	 * Updates every route in the panel to reflect routes from the current island.
	 */
	public void updateAllRoutes() {
		txtrFIRoutes.setText(manager.getPlayer().getCurrentIsland().getRoutesToIslandDescriptions(manager.getPlayer().getShip().getSpeed(), manager.islands.get(0)).toString());
		txtrSRRoutes.setText(manager.getPlayer().getCurrentIsland().getRoutesToIslandDescriptions(manager.getPlayer().getShip().getSpeed(), manager.islands.get(1)).toString());
		txtrPCRoutes.setText(manager.getPlayer().getCurrentIsland().getRoutesToIslandDescriptions(manager.getPlayer().getShip().getSpeed(), manager.islands.get(2)).toString());
		txtrVIRoutes.setText(manager.getPlayer().getCurrentIsland().getRoutesToIslandDescriptions(manager.getPlayer().getShip().getSpeed(), manager.islands.get(3)).toString());
		txtrSERoutes.setText(manager.getPlayer().getCurrentIsland().getRoutesToIslandDescriptions(manager.getPlayer().getShip().getSpeed(), manager.islands.get(4)).toString());
	}
	
	/**
	 * Updates every price in the panel to reflect the current values.
	 */
	public void updateAllPrices() {
		txtrFIBuy.setText(manager.viewSellingPrices(0));
		txtrSRBuy.setText(manager.viewSellingPrices(1));
		txtrPCBuy.setText(manager.viewSellingPrices(2));
		txtrVIBuy.setText(manager.viewSellingPrices(3));
		txtrSEBuy.setText(manager.viewSellingPrices(4));
		txtrFISell.setText(manager.viewBuyingPrices(0));
		txtrSRSell.setText(manager.viewBuyingPrices(1));
		txtrPCSell.setText(manager.viewBuyingPrices(2));
		txtrVISell.setText(manager.viewBuyingPrices(3));
		txtrSESell.setText(manager.viewBuyingPrices(4));
	}

	/**
	 * Create the panel.
	 */
	public IslandDialog(GameManager incomingManager) {
		manager = incomingManager;
		setLayout(null);
		
		JLabel lblFracturedIsle = new JLabel("Fractured Isle");
		lblFracturedIsle.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblFracturedIsle.setBounds(10, 10, 200, 25);
		add(lblFracturedIsle);
		
		JLabel lblPenlyCay = new JLabel("Penly Cay");
		lblPenlyCay.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblPenlyCay.setBounds(10, 230, 200, 25);
		add(lblPenlyCay);
		
		JLabel lblValganIsland = new JLabel("Valgan Island");
		lblValganIsland.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblValganIsland.setBounds(450, 230, 200, 25);
		add(lblValganIsland);
		
		JLabel lblSmithscordRefuge = new JLabel("Smithscord Refuge");
		lblSmithscordRefuge.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblSmithscordRefuge.setBounds(450, 10, 200, 25);
		add(lblSmithscordRefuge);
		
		JLabel lblFIBuy = new JLabel("Buying:");
		lblFIBuy.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblFIBuy.setBounds(10, 40, 115, 25);
		add(lblFIBuy);
		
		JLabel lblFISell = new JLabel("Selling:");
		lblFISell.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblFISell.setBounds(135, 40, 115, 25);
		add(lblFISell);
		
		JLabel lblFIRoutes = new JLabel("Routes:");
		lblFIRoutes.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblFIRoutes.setBounds(260, 40, 146, 25);
		add(lblFIRoutes);
		
		JLabel lblSRBuy = new JLabel("Buying:");
		lblSRBuy.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblSRBuy.setBounds(450, 40, 115, 25);
		add(lblSRBuy);
		
		JLabel lblSRSell = new JLabel("Selling:");
		lblSRSell.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblSRSell.setBounds(575, 40, 115, 25);
		add(lblSRSell);
		
		JLabel lblSRRoutes = new JLabel("Routes:");
		lblSRRoutes.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblSRRoutes.setBounds(700, 40, 146, 25);
		add(lblSRRoutes);
		
		JLabel lblPCBuy = new JLabel("Buying:");
		lblPCBuy.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblPCBuy.setBounds(10, 260, 115, 25);
		add(lblPCBuy);
		
		JLabel lblPCSell = new JLabel("Selling:");
		lblPCSell.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblPCSell.setBounds(135, 260, 115, 25);
		add(lblPCSell);
		
		JLabel lblPCRoutes = new JLabel("Routes:");
		lblPCRoutes.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblPCRoutes.setBounds(260, 260, 146, 25);
		add(lblPCRoutes);
		
		JLabel lblVIBuy = new JLabel("Buying:");
		lblVIBuy.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblVIBuy.setBounds(450, 260, 115, 25);
		add(lblVIBuy);
		
		JLabel lblVISell = new JLabel("Selling:");
		lblVISell.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblVISell.setBounds(575, 260, 115, 25);
		add(lblVISell);
		
		JLabel lblVIRoutes = new JLabel("Routes:");
		lblVIRoutes.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblVIRoutes.setBounds(700, 260, 146, 25);
		add(lblVIRoutes);
		
		JLabel lblSEBuy = new JLabel("Buying:");
		lblSEBuy.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblSEBuy.setBounds(217, 476, 115, 25);
		add(lblSEBuy);
		
		JLabel lblSESell = new JLabel("Selling:");
		lblSESell.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblSESell.setBounds(342, 476, 115, 25);
		add(lblSESell);
		
		JLabel lblSERoutes = new JLabel("Routes:");
		lblSERoutes.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblSERoutes.setBounds(467, 476, 146, 25);
		add(lblSERoutes);
		
		txtrFIBuy = new JTextArea(manager.viewSellingPrices(0));
		txtrFIBuy.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrFIBuy.setBackground(UIManager.getColor("Button.background"));
		txtrFIBuy.setBounds(10, 76, 115, 133);
		add(txtrFIBuy);
		
		txtrFISell = new JTextArea(manager.viewBuyingPrices(0));
		txtrFISell.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrFISell.setBackground(UIManager.getColor("Button.background"));
		txtrFISell.setBounds(135, 76, 115, 133);
		add(txtrFISell);
		
		txtrFIRoutes = new JTextArea(manager.getPlayer().getCurrentIsland().getRoutesToIslandDescriptions(manager.getPlayer().getShip().getSpeed(), manager.islands.get(0)).toString());
		txtrFIRoutes.setLineWrap(true);
		txtrFIRoutes.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrFIRoutes.setBackground(UIManager.getColor("Button.background"));
		txtrFIRoutes.setBounds(260, 76, 155, 133);
		add(txtrFIRoutes);
		
		txtrSRBuy = new JTextArea(manager.viewSellingPrices(1));
		txtrSRBuy.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrSRBuy.setBackground(UIManager.getColor("Button.background"));
		txtrSRBuy.setBounds(450, 76, 115, 133);
		add(txtrSRBuy);
		
		txtrSRSell = new JTextArea(manager.viewBuyingPrices(1));
		txtrSRSell.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrSRSell.setBackground(UIManager.getColor("Button.background"));
		txtrSRSell.setBounds(575, 76, 115, 133);
		add(txtrSRSell);
		
		txtrSRRoutes = new JTextArea(manager.getPlayer().getCurrentIsland().getRoutesToIslandDescriptions(manager.getPlayer().getShip().getSpeed(), manager.islands.get(1)).toString());
		txtrSRRoutes.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrSRRoutes.setBackground(UIManager.getColor("Button.background"));
		txtrSRRoutes.setBounds(700, 76, 155, 133);
		add(txtrSRRoutes);
		
		txtrPCBuy = new JTextArea(manager.viewSellingPrices(2));
		txtrPCBuy.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrPCBuy.setBackground(UIManager.getColor("Button.background"));
		txtrPCBuy.setBounds(10, 296, 115, 133);
		add(txtrPCBuy);
		
		txtrPCSell = new JTextArea(manager.viewBuyingPrices(2));
		txtrPCSell.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrPCSell.setBackground(UIManager.getColor("Button.background"));
		txtrPCSell.setBounds(135, 296, 115, 133);
		add(txtrPCSell);
		
		txtrPCRoutes = new JTextArea(manager.getPlayer().getCurrentIsland().getRoutesToIslandDescriptions(manager.getPlayer().getShip().getSpeed(), manager.islands.get(2)).toString());
		txtrPCRoutes.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrPCRoutes.setBackground(UIManager.getColor("Button.background"));
		txtrPCRoutes.setBounds(260, 296, 155, 133);
		add(txtrPCRoutes);
		
		txtrVIBuy = new JTextArea(manager.viewSellingPrices(3));
		txtrVIBuy.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrVIBuy.setBackground(UIManager.getColor("Button.background"));
		txtrVIBuy.setBounds(450, 296, 115, 133);
		add(txtrVIBuy);
		
		txtrVISell = new JTextArea(manager.viewBuyingPrices(3));
		txtrVISell.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrVISell.setBackground(UIManager.getColor("Button.background"));
		txtrVISell.setBounds(575, 296, 115, 133);
		add(txtrVISell);
		
		txtrVIRoutes = new JTextArea(manager.getPlayer().getCurrentIsland().getRoutesToIslandDescriptions(manager.getPlayer().getShip().getSpeed(), manager.islands.get(3)).toString());
		txtrVIRoutes.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrVIRoutes.setBackground(UIManager.getColor("Button.background"));
		txtrVIRoutes.setBounds(700, 296, 155, 133);
		add(txtrVIRoutes);
		
		txtrSEBuy = new JTextArea(manager.viewSellingPrices(4));
		txtrSEBuy.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrSEBuy.setBackground(UIManager.getColor("Button.background"));
		txtrSEBuy.setBounds(217, 516, 115, 133);
		add(txtrSEBuy);
		
		txtrSESell = new JTextArea(manager.viewBuyingPrices(4));
		txtrSESell.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrSESell.setBackground(UIManager.getColor("Button.background"));
		txtrSESell.setBounds(342, 516, 115, 133);
		add(txtrSESell);
		
		txtrSERoutes = new JTextArea(manager.getPlayer().getCurrentIsland().getRoutesToIslandDescriptions(manager.getPlayer().getShip().getSpeed(), manager.islands.get(4)).toString());
		txtrSERoutes.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrSERoutes.setBackground(UIManager.getColor("Button.background"));
		txtrSERoutes.setBounds(467, 516, 155, 133);
		add(txtrSERoutes);
		
		JLabel lblStockstallEnclave = new JLabel("Stockstall Enclave");
		lblStockstallEnclave.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblStockstallEnclave.setBounds(217, 440, 200, 25);
		add(lblStockstallEnclave);
		
		updateAllRoutes();
		updateAllPrices();

	}
}
