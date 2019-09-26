package numba;

import java.util.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class NumTile extends JFrame implements ActionListener
{
	JButton[][] buttons;
	JPanel buttonPanel = new JPanel();
	ArrayList numList;
	ArrayList order;
	static NumTile f;
	int numba=9;
	int size = 3;
	int	clicks;
	String user;

	
	public NumTile()
	{
		clicks = 0;
		order = new ArrayList();
		numList = new ArrayList();

		add(buttonPanel);
		addNum();
		JMenuBar mnu = new JMenuBar();
		setJMenuBar(mnu);
			JMenu change = new JMenu("Change");
				JMenuItem three = new JMenuItem("3x3");
					three.addActionListener(this);
					three.setActionCommand("three");
				JMenuItem four = new JMenuItem("4x4");
					four.addActionListener(this);
					four.setActionCommand("four");
				JMenuItem five = new JMenuItem("5x5");
					five.addActionListener(this);
					five.setActionCommand("five");
			change.add(three);
			change.add(four);
			change.add(five);

		mnu.add(change);

		
	}

	public void actionPerformed(ActionEvent e)
	{
		int jokes=0;
		String arg = e.getActionCommand();
		for(int a = 0; a<buttons.length;a++)
		{
			for(int b = 0; b<buttons[0].length; b++)
			{
				if(e.getSource() == buttons[a][b]&&!(buttons[a][b].getText().equals("")))
				{
					String match = buttons[a][b].getText();
					clicks++;
					if(a-1 >= 0 && buttons[a-1][b].getText().equals(""))						//upper
					{
						buttons[a][b].setText(buttons[a-1][b].getText());
						buttons[a-1][b].setText(match);
					}

					if(b-1 >= 0 && buttons[a][b-1].getText().equals(""))						//left
					{
						buttons[a][b].setText(buttons[a][b-1].getText());
						buttons[a][b-1].setText(match);
					}

					if(b+1<buttons[0].length && buttons[a][b+1].getText().equals(""))			//right on man
					{
						buttons[a][b].setText(buttons[a][b+1].getText());
						buttons[a][b+1].setText(match);
					}

					if(a+1 < buttons.length && buttons[a+1][b].getText().equals(""))				//lower
					{
						buttons[a][b].setText(buttons[a+1][b].getText());
						buttons[a+1][b].setText(match);
					}

					checkWon();

				}
				//hardcode to win
				/*if(e.getSource()== buttons[2][2])
				{
					buttons[a][b].setText(order.get(jokes).toString());
					jokes++;
				}*/
			}
		}

		if(arg == "three")
		{
			removeButton();
			size = 3;
			numba = 9;
			addNum();
		}
		if(arg== "four")
		{
			removeButton();
			size = 4;
			numba = 16;
			addNum();
		}
		if(arg == "five")
		{
			removeButton();
			size = 5;
			numba = 25;
			addNum();
		}

	}
	
	/**
	 * Clears 
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
	public void addNum()
	{

		buttonPanel.setLayout(new GridLayout(size,size));
		buttons = new JButton[size][size];

		int y = 0;
		for(int g = 0; g<buttons.length; g++)
		{
			for(int h = 0; h <buttons[0].length; h++)
			{
				buttons[g][h] = new JButton();
				buttonPanel.add(buttons[g][h]);
				buttons[g][h].addActionListener(this);
			}
		}
		int j = 0;
		for(int i =0;i<numba;i++)
		{
			numList.add(i,i+1);
			order.add(i,i+1);
		}
		numList.set(numList.indexOf(numba), "");
		order.set(order.indexOf(numba), "");
		Collections.shuffle(numList);
		for(int g = 0; g<buttons.length; g++)
		{
			for(int h = 0; h <buttons[0].length; h++)
			{
				buttons[g][h].setText(numList.get(j).toString());
				j++;
			}
		}
	}
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
	public void playAgain()
	{

		if(JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Play again my friend", JOptionPane.YES_NO_OPTION)==0)
		{
			f.dispose();
			f = new NumTile();
			f.setTitle("Numbers Tiles or whatever");
			f.setBounds(300,300,400,400);
			f.setVisible(true);
		}
		else
		{
			System.exit(0);
		}
	}
	public static void main(String[] args)
	{
		f = new NumTile();
		f.setTitle("Numbers Tiles or whatever");
		f.setBounds(300,300,400,400);
		f.setVisible(true);
	}
}
