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


public class MortgageWindow {
	private Player player;
	private ArrayList<Property> property;
	private Board board;

	private JFrame frame;

	public MortgageWindow(Player player,ArrayList<Property> property,Board board){
		this.player = player;
		this.property = property;
		this.board = board;

		frame = new JFrame("Mortgage");
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
		final JPanel tempPanel = new JPanel();

		tempPanel.setLayout(new GridBagLayout());
		tempPanel.setBackground(Color.white);

		final String[] tempproperty = new String[property.size()];
		for(int i = 0;i < property.size();i++){
			tempproperty[i] = property.get(i).getName();
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

		final JList<String> list = new JList<String>(tempproperty);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		c.insets = new Insets(10,100,10,10);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridheight = 2;
		c.gridwidth = 1;
		//c.ipady = 50;
		c.gridx = 0;
		c.gridy = 1;
		tempPanel.add(list, c);

		JButton upgrade = new JButton("Mortgage");
		c.insets = new Insets(10,10,10,100);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridheight = 1;
		c.gridwidth = 1;
		//c.ipady = 50;
		c.gridx = 1;
		c.gridy = 1;
		tempPanel.add(upgrade, c);

		JButton downgrade = new JButton("Unmortgage");
		c.insets = new Insets(10,10,10,100);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridheight = 1;
		c.gridwidth = 1;
		//c.ipady = 50;
		c.gridx = 1;
		c.gridy = 2;
		tempPanel.add(downgrade, c);

		createList(tempproperty,list);

		tempPanel.revalidate();
		tempPanel.repaint();

		upgrade.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if(board.canMortgage(player, property.get(index))){
					board.mortgageProperty(property.get(index));

					createList(tempproperty,list);
					tempPanel.revalidate();
					tempPanel.repaint();
				}
			}
		});
		downgrade.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if(board.canUnmortgage(player, property.get(index))){
					board.unmortgageProperty(property.get(index));

					createList(tempproperty,list);
					tempPanel.revalidate();
					tempPanel.repaint();
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
			if(property.get(i).getMortgaged()){
				str = "- m";
			}
			else{
				str = "";
			}
			tempList[i] = list[i] + str;
		}

		listGUI.setListData(tempList);
		listGUI.revalidate();
		listGUI.repaint();
	}
}
