package gui;

import javax.swing.JPanel;

import game.GameManager;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class IslandDialog extends JPanel {
	
	private GameManager manager;

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
		
		JLabel lblFIRoutes = new JLabel("Routes from location:");
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
		
		JLabel lblSRRoutes = new JLabel("Routes from location:");
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
		
		JLabel lblPCRoutes = new JLabel("Routes from location:");
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
		
		JLabel lblVIRoutes = new JLabel("Routes from location:");
		lblVIRoutes.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblVIRoutes.setBounds(700, 260, 146, 25);
		add(lblVIRoutes);
		
		JTextArea txtrFIBuy = new JTextArea(manager.viewBuyingPrices(0));
		txtrFIBuy.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrFIBuy.setBackground(UIManager.getColor("Button.background"));
		txtrFIBuy.setBounds(10, 76, 115, 133);
		add(txtrFIBuy);
		
		JTextArea txtrFISell = new JTextArea(manager.viewSellingPrices(0));
		txtrFISell.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrFISell.setBackground(UIManager.getColor("Button.background"));
		txtrFISell.setBounds(135, 76, 115, 133);
		add(txtrFISell);
		
		JTextArea txtrFIRoutes = new JTextArea(manager.currentIsland.getRoutesToIslandDescriptions(manager.player.getShip().getSpeed(), manager.islands.get(0)).toString());
		txtrFIRoutes.setLineWrap(true);
		txtrFIRoutes.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrFIRoutes.setBackground(UIManager.getColor("Button.background"));
		txtrFIRoutes.setBounds(260, 76, 146, 133);
		add(txtrFIRoutes);
		
		JTextArea txtrSRBuy = new JTextArea(manager.viewBuyingPrices(1));
		txtrSRBuy.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrSRBuy.setBackground(UIManager.getColor("Button.background"));
		txtrSRBuy.setBounds(450, 76, 115, 133);
		add(txtrSRBuy);
		
		JTextArea txtrSRSell = new JTextArea(manager.viewSellingPrices(1));
		txtrSRSell.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrSRSell.setBackground(UIManager.getColor("Button.background"));
		txtrSRSell.setBounds(575, 76, 115, 133);
		add(txtrSRSell);
		
		JTextArea txtrSRRoutes = new JTextArea(manager.currentIsland.getRoutesToIslandDescriptions(manager.player.getShip().getSpeed(), manager.islands.get(1)).toString());
		txtrSRRoutes.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrSRRoutes.setBackground(UIManager.getColor("Button.background"));
		txtrSRRoutes.setBounds(700, 76, 146, 133);
		add(txtrSRRoutes);
		
		JTextArea txtrPCBuy = new JTextArea(manager.viewBuyingPrices(2));
		txtrPCBuy.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrPCBuy.setBackground(UIManager.getColor("Button.background"));
		txtrPCBuy.setBounds(10, 296, 115, 133);
		add(txtrPCBuy);
		
		JTextArea txtrPCSell = new JTextArea(manager.viewSellingPrices(2));
		txtrPCSell.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrPCSell.setBackground(UIManager.getColor("Button.background"));
		txtrPCSell.setBounds(135, 296, 115, 133);
		add(txtrPCSell);
		
		JTextArea txtrPCRoutes = new JTextArea(manager.currentIsland.getRoutesToIslandDescriptions(manager.player.getShip().getSpeed(), manager.islands.get(2)).toString());
		txtrPCRoutes.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrPCRoutes.setBackground(UIManager.getColor("Button.background"));
		txtrPCRoutes.setBounds(260, 296, 146, 133);
		add(txtrPCRoutes);
		
		JTextArea txtrVIBuy = new JTextArea(manager.viewBuyingPrices(3));
		txtrVIBuy.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrVIBuy.setBackground(UIManager.getColor("Button.background"));
		txtrVIBuy.setBounds(450, 296, 115, 133);
		add(txtrVIBuy);
		
		JTextArea txtrVISell = new JTextArea(manager.viewSellingPrices(3));
		txtrVISell.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrVISell.setBackground(UIManager.getColor("Button.background"));
		txtrVISell.setBounds(575, 296, 115, 133);
		add(txtrVISell);
		
		JTextArea txtrVIRoutes = new JTextArea(manager.currentIsland.getRoutesToIslandDescriptions(manager.player.getShip().getSpeed(), manager.islands.get(3)).toString());
		txtrVIRoutes.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrVIRoutes.setBackground(UIManager.getColor("Button.background"));
		txtrVIRoutes.setBounds(700, 296, 146, 133);
		add(txtrVIRoutes);

	}
}
