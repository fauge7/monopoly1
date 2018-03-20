package monopoly.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;


public class UpgradeWindow{
	private Player player;
	private ArrayList<Lot> lots;
	private Board board;

	private JFrame frame;

	public UpgradeWindow(Player player,ArrayList<Lot> lots,Board board){
		this.player = player;
		this.lots = lots;
		this.board = board;

		frame = new JFrame("Upgrade");
		frame.setSize(500,500);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		JPanel panel = createPanel();
		frame.add(panel);

		frame.revalidate();
		frame.repaint();
	}
	public JPanel createPanel(){
		JPanel tempPanel = new JPanel();

		tempPanel.setLayout(new GridBagLayout());
		tempPanel.setBackground(Color.WHITE);

		String[] tempLots = new String[lots.size()];
		for(int i = 0;i < lots.size();i++){
			tempLots[i] = lots.get(i).getName();
		}

		GridBagConstraints c = new GridBagConstraints();

		JLabel text = new JLabel("Select properties to manage.");
		text.setFont(new Font("Serif", Font.PLAIN, 18));
		text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		c.insets = new Insets(10,100,10,10);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridheight = 1;
		c.gridwidth = 2;
		//c.ipady = 50;
		c.gridx = 0;
		c.gridy = 0;
		tempPanel.add(text, c);

		c.gridwidth = 1;

		JList<String> list = new JList<String>(tempLots);
		createList(tempLots,list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		c.insets = new Insets(10,100,10,10);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridheight = 2;
		//c.ipady = 50;
		c.gridx = 0;
		c.gridy = 1;
		tempPanel.add(list, c);

		JButton upgrade = new JButton("Upgrade");
		c.insets = new Insets(10,10,10,100);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridheight = 1;
		//c.ipady = 50;
		c.gridx = 1;
		c.gridy = 1;
		tempPanel.add(upgrade, c);

		JButton downgrade = new JButton("Downgrade");
		c.insets = new Insets(10,10,10,100);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridheight = 1;
		//c.ipady = 50;
		c.gridx = 1;
		c.gridy = 2;
		tempPanel.add(downgrade, c);

		tempPanel.revalidate();
		tempPanel.repaint();

		upgrade.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if(board.canBuyHouse(player, lots.get(index))){
					board.buyHouse(player, lots.get(index));
					createList(tempLots,list);

					tempPanel.revalidate();
					tempPanel.repaint();

					frame.revalidate();
					frame.repaint();
				}
			}
		});
		downgrade.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if(board.canSellHouse(player, lots.get(index))){
					board.sellHouse(player, lots.get(index));
					createList(tempLots,list);

					tempPanel.revalidate();
					tempPanel.repaint();

					frame.revalidate();
					frame.repaint();
				}
			}
		});

		return tempPanel;
	}
	public void createList(String[] list,JList<String> listGUI){
		String[] tempList = new String[list.length];

		//Creates the tempList array
		for(int i = 0;i < tempList.length;i++){
			String str = new String();
			if(lots.get(i).getHouses() == 5){
				str = "1 hotel";
			}
			else if(lots.get(i).getHouses() == 1){
				str = lots.get(i).getHouses() + " house";
			}
			else{
				str = lots.get(i).getHouses() + " houses";
			}
			tempList[i] = list[i] + " - " + str;
		}

		listGUI.setListData(tempList);
	}
}
