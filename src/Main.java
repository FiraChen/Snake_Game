import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.io.IOException;

import java.util.Scanner;


public class Main {
	public static void main(String[] args){
		// create the main frame
		JFrame frame = new JFrame("Snake");

		// create model to notify view
		Model model = new Model(frame);

		// create the key control and connect with view and model
		KeyControl keys = new KeyControl(model);

		frame.addKeyListener(keys);
    frame.setFocusable(true);
    frame.setFocusTraversalKeysEnabled(false);

		// show the first screen when the game start
		ScreenOne initialScreen = new ScreenOne();
		frame.add(initialScreen);

		/*JPanel top = new TopBar("1");
		frame.add(top, BorderLayout.NORTH);*/

    // create the window
    frame.setMinimumSize(new Dimension(1280, 800));
		frame.setPreferredSize(new Dimension(1280, 800));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
