package numba;

import java.util.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This game imitates the classic number tiles game, but with an image instead of numbers. 
 * 
 * @author bryndon
 * @version 1.0
 * @since 2016
 */
public class PictureNumba extends JFrame implements ActionListener
{
	private JButton[][] buttons;
	private JPanel buttonPanel = new JPanel();
	private ArrayList numList = new ArrayList();
	private ArrayList order = new ArrayList();
	private static PictureNumba f;
	private int numba=9;
	private int size = 3;
	private int	clicks = 0;
	private String user;
	HashMap<Integer, ImageIcon> pictMap = new HashMap<Integer,ImageIcon>();
	
	/*
	 * Sets up the HashMap of images and creates the GUI mainframe
	 */
	public PictureNumba()
	{
		pictMap.put(1, new ImageIcon("sq2.fw.png"));
		pictMap.put(2, new ImageIcon("sq3.fw.png"));
		pictMap.put(3, new ImageIcon("sq4.fw.png"));
		pictMap.put(4, new ImageIcon("sq5.fw.png"));
		pictMap.put(5, new ImageIcon("sq6.fw.png"));
		pictMap.put(6, new ImageIcon("sq7.fw.png"));
		pictMap.put(7, new ImageIcon("sq8.fw.png"));
		pictMap.put(8, new ImageIcon("sq9.fw.png"));
		pictMap.put(0, new ImageIcon(""));


		add(buttonPanel);
		addPict();
		JMenuBar mnu = new JMenuBar();
		setJMenuBar(mnu);
			JMenu change = new JMenu("Change");
				JMenuItem pic = new JMenuItem("Reset");
					pic.addActionListener(this);
					pic.setActionCommand("pic");
			change.add(pic);
		mnu.add(change);
		
	}

	/*
	 * For every button click, checks to see if
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		String arg = e.getActionCommand();
		for(int a = 0; a<buttons.length;a++)
		{
			for(int b = 0; b<buttons[0].length; b++)
			{
				if(e.getSource() == buttons[a][b]&&!(buttons[a][b].getText().equals("0")))
				{
					String match = buttons[a][b].getText();
					clicks++;
					if(a-1 >= 0 && buttons[a-1][b].getText().equals("0"))						//checks upper
					{
						changing(buttons[a-1][b],buttons[a][b]);
						buttons[a][b].setText(buttons[a-1][b].getText());
						buttons[a-1][b].setText(match);
					}

					if(b-1 >= 0 && buttons[a][b-1].getText().equals("0"))						//checks left
					{
						changing(buttons[a][b-1],buttons[a][b]);
						buttons[a][b].setText(buttons[a][b-1].getText());
						buttons[a][b-1].setText(match);
					}

					if(b+1<buttons[0].length && buttons[a][b+1].getText().equals("0"))			//checks right
					{
						changing(buttons[a][b+1],buttons[a][b]);
						buttons[a][b].setText(buttons[a][b+1].getText());
						buttons[a][b+1].setText(match);
					}

					if(a+1 < buttons.length && buttons[a+1][b].getText().equals("0"))				//checks lower
					{
						changing(buttons[a+1][b],buttons[a][b]);
						buttons[a][b].setText(buttons[a+1][b].getText());
						buttons[a+1][b].setText(match);
					}

					checkWon();

				}
				//hardcode to win
				/*if(e.getSource()== buttons[2][2])
				{
					int jokes=0;
					for(int g=0;g<buttons.length;g++){
						for(int h=0;h<buttons.length;h++){
							System.out.println(jokes);
							buttons[g][h].setText(order.get(jokes).toString());
							buttons[g][h].setIcon(pictMap.get(jokes));
							jokes++;
						}
					}
				}*/
			}
		}
		if(arg=="pic"){
			removeButton();
			size = 3;
			addPict();
		}
	}
	
	/*
	 * Switches the blank button and the picture button
	 */
	public void changing(JButton oldBlank, JButton newBlank){
		int newBl = Integer.parseInt(newBlank.getText());
		oldBlank.setIcon(pictMap.get(newBl));
		newBlank.setIcon(pictMap.get(0));
	}
	
	/*
	 * Sets up the board with randomized sorting of the picture array
	 */
	public void addPict(){
		buttonPanel.setLayout(new GridLayout(size,size));
		buttons = new JButton[size][size];
	
		for(int i =0;i<9;i++)
		{
			numList.add(i,i);
			order.add(i,i);
		}
		Collections.shuffle(numList);
		int y=0;
		for(int g = 0; g<buttons.length; g++)
		{
			for(int h = 0; h <buttons[0].length; h++)
			{
				buttons[g][h] = new JButton(numList.get(y)+"",pictMap.get(numList.get(y)));
				buttonPanel.add(buttons[g][h]);
				buttons[g][h].setBackground(Color.white);
				buttons[g][h].setForeground(Color.white);
				buttons[g][h].addActionListener(this);
				y++;
			}
		}
		invalidate();
		validate();
	}
	
	
	/*
	 * Clears the board and also clears order of the winning order
	 */
	public void removeButton()
	{
		for(int s = 0; s<buttons.length;s++)
		{
			for(int t = 0; t<buttons[0].length;t++)
			{
				buttonPanel.remove(buttons[s][t]);
			}
		}
		numList.clear();
		order.clear();
		invalidate();
		validate();
	}
	
	/*
	 * Checks the button array to see if it is in order. If it is, calls playAgain
	 */
	public void checkWon()
	{
		int j = 0;
		int win = 0;
		for(int a = 0; a<buttons.length;a++)
		{
			for(int b = 0; b<buttons[0].length; b++)
			{
				if(buttons[a][b].getText().equals(order.get(j).toString()))
				{
					win++;
				}
				j++;
			}
		}

		if(win == numba-1||win==numba)
		{
			buttons[2][2].setText("");
			playAgain();
		}
	}
	
	/*
	 * Asks player if they want to play again, instantiates a new game if yes. Exits if no
	 */
	public void playAgain()
	{

		if(JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Play again my friend", JOptionPane.YES_NO_OPTION)==0)
		{
			f.dispose();
			f = new PictureNumba();
			f.setTitle("Numbers Tiles");
			f.setBounds(200,200,575,650);
			f.setVisible(true);
			clicks=0;
		}
		else
		{
			System.exit(0);
		}
	}
	
	
	public static void main(String[] args)
	{
		f = new PictureNumba();
		f.setTitle("Numbers Tiles");
		f.setBounds(200,200,575,650);
		f.setVisible(true);
	}
}
