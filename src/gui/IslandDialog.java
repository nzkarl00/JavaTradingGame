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
		lblValganIsland.setBounds(310, 230, 200, 25);
		add(lblValganIsland);
		
		JLabel lblSmithscordRefuge = new JLabel("Smithscord Refuge");
		lblSmithscordRefuge.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		lblSmithscordRefuge.setBounds(310, 10, 200, 25);
		add(lblSmithscordRefuge);
		
		JLabel lblFIBuy = new JLabel("Buying:");
		lblFIBuy.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblFIBuy.setBounds(10, 40, 75, 25);
		add(lblFIBuy);
		
		JLabel lblFISell = new JLabel("Selling:");
		lblFISell.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblFISell.setBounds(110, 40, 75, 25);
		add(lblFISell);
		
		JLabel lblFIRoutes = new JLabel("Routes:");
		lblFIRoutes.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblFIRoutes.setBounds(210, 40, 75, 25);
		add(lblFIRoutes);
		
		JLabel lblSRBuy = new JLabel("Buying:");
		lblSRBuy.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblSRBuy.setBounds(310, 40, 75, 25);
		add(lblSRBuy);
		
		JLabel lblSRSell = new JLabel("Selling:");
		lblSRSell.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblSRSell.setBounds(410, 40, 75, 25);
		add(lblSRSell);
		
		JLabel lblSRRoutes = new JLabel("Routes:");
		lblSRRoutes.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblSRRoutes.setBounds(510, 40, 75, 25);
		add(lblSRRoutes);
		
		JLabel lblPCBuy = new JLabel("Buying:");
		lblPCBuy.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblPCBuy.setBounds(10, 260, 75, 25);
		add(lblPCBuy);
		
		JLabel lblPCSell = new JLabel("Selling:");
		lblPCSell.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblPCSell.setBounds(110, 260, 75, 25);
		add(lblPCSell);
		
		JLabel lblPCRoutes = new JLabel("Routes:");
		lblPCRoutes.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblPCRoutes.setBounds(210, 260, 75, 25);
		add(lblPCRoutes);
		
		JLabel lblVIBuy = new JLabel("Buying:");
		lblVIBuy.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblVIBuy.setBounds(310, 260, 75, 25);
		add(lblVIBuy);
		
		JLabel lblVISell = new JLabel("Selling:");
		lblVISell.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblVISell.setBounds(410, 260, 75, 25);
		add(lblVISell);
		
		JLabel lblVIRoutes = new JLabel("Routes:");
		lblVIRoutes.setFont(new Font("Viner Hand ITC", Font.BOLD, 14));
		lblVIRoutes.setBounds(510, 260, 75, 25);
		add(lblVIRoutes);
		
		JTextArea txtrFIBuy = new JTextArea(manager.viewBuyingPrices(0));
		txtrFIBuy.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrFIBuy.setBackground(UIManager.getColor("Button.background"));
		txtrFIBuy.setBounds(10, 76, 75, 133);
		add(txtrFIBuy);
		
		JTextArea txtrFISell = new JTextArea(manager.viewSellingPrices(0));
		txtrFISell.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrFISell.setBackground(UIManager.getColor("Button.background"));
		txtrFISell.setBounds(110, 76, 75, 133);
		add(txtrFISell);
		
		JTextArea txtrFIRoutes = new JTextArea(manager.currentIsland.getRoutesDescriptions(manager.player.getShip().getSpeed()).toString());
		txtrFIRoutes.setLineWrap(true);
		txtrFIRoutes.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrFIRoutes.setBackground(UIManager.getColor("Button.background"));
		txtrFIRoutes.setBounds(210, 76, 75, 133);
		add(txtrFIRoutes);
		
		JTextArea txtrSRBuy = new JTextArea(manager.viewBuyingPrices(1));
		txtrSRBuy.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrSRBuy.setBackground(UIManager.getColor("Button.background"));
		txtrSRBuy.setBounds(310, 76, 75, 133);
		add(txtrSRBuy);
		
		JTextArea txtrSRSell = new JTextArea(manager.viewSellingPrices(1));
		txtrSRSell.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrSRSell.setBackground(UIManager.getColor("Button.background"));
		txtrSRSell.setBounds(410, 76, 75, 133);
		add(txtrSRSell);
		
		JTextArea txtrSRRoutes = new JTextArea(manager.viewBuyingPrices(1));
		txtrSRRoutes.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrSRRoutes.setBackground(UIManager.getColor("Button.background"));
		txtrSRRoutes.setBounds(510, 76, 75, 133);
		add(txtrSRRoutes);
		
		JTextArea txtrPCBuy = new JTextArea(manager.viewBuyingPrices(2));
		txtrPCBuy.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrPCBuy.setBackground(UIManager.getColor("Button.background"));
		txtrPCBuy.setBounds(10, 296, 75, 133);
		add(txtrPCBuy);
		
		JTextArea txtrPCSell = new JTextArea(manager.viewSellingPrices(2));
		txtrPCSell.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrPCSell.setBackground(UIManager.getColor("Button.background"));
		txtrPCSell.setBounds(110, 296, 75, 133);
		add(txtrPCSell);
		
		JTextArea txtrPCRoutes = new JTextArea(manager.viewBuyingPrices(2));
		txtrPCRoutes.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrPCRoutes.setBackground(UIManager.getColor("Button.background"));
		txtrPCRoutes.setBounds(210, 296, 75, 133);
		add(txtrPCRoutes);
		
		JTextArea txtrVIBuy = new JTextArea(manager.viewBuyingPrices(3));
		txtrVIBuy.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrVIBuy.setBackground(UIManager.getColor("Button.background"));
		txtrVIBuy.setBounds(310, 296, 75, 133);
		add(txtrVIBuy);
		
		JTextArea txtrVISell = new JTextArea(manager.viewSellingPrices(3));
		txtrVISell.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrVISell.setBackground(UIManager.getColor("Button.background"));
		txtrVISell.setBounds(410, 296, 75, 133);
		add(txtrVISell);
		
		JTextArea txtrVIRoutes = new JTextArea(manager.viewBuyingPrices(3));
		txtrVIRoutes.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtrVIRoutes.setBackground(UIManager.getColor("Button.background"));
		txtrVIRoutes.setBounds(510, 296, 75, 133);
		add(txtrVIRoutes);

	}
}
