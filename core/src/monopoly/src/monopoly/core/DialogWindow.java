package monopoly.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class DialogWindow {
	private String title;
	private String message;
	private Color color;

	private JFrame frame;
	private JPanel panel;

	//Constructor with no color
	public DialogWindow(String title,String message){
		this.title = title;
		this.message = message;
		color = Color.WHITE;

		frame = new JFrame(title);
		frame.setSize(800,100);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		//Updates the frame
		frame.revalidate();
		frame.repaint();

		//Adds the panel to the frame
		panel = createPanel();
		frame.add(panel);

		//Updates the frame
		frame.revalidate();
		frame.repaint();
	}
	//Constructor with color
	public DialogWindow(String title,String message,Color color){
		this.title = title;
		this.message = message;
		this.color = color;

		frame = new JFrame(title);
		frame.setSize(800,100);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		//Updates the frame
		frame.revalidate();
		frame.repaint();

		//Adds the panel to the frame
		panel = createPanel();
		frame.add(panel);

		//Updates the frame
		frame.revalidate();
		frame.repaint();
	}
	public JPanel createPanel(){
		JPanel tempPanel = new JPanel();

		tempPanel.setLayout(new GridBagLayout());
		tempPanel.setBackground(color);

		GridBagConstraints c = new GridBagConstraints();

		JLabel text = new JLabel();
		text.setText(message);
		text.setFont(new Font("Serif", Font.PLAIN, 20));
		text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		c.insets = new Insets(10,10,10,10);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		tempPanel.add(text, c);

		//Updates the tempPanel
		tempPanel.revalidate();
		tempPanel.repaint();

		return tempPanel;
	}
}
