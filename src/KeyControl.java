import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.*;
import java.awt.event.*;

public class KeyControl implements KeyListener {
  Model model;

  public KeyControl(Model mod) {
    model = mod;
  }

	public void keyPressed(KeyEvent e) {
  }

	public void keyTyped(KeyEvent e) {
  }

  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode()== KeyEvent.VK_RIGHT) {
			model.turnRight();
		} else if (e.getKeyCode()== KeyEvent.VK_LEFT) {
			model.turnLeft();
		} else if (e.getKeyCode()== KeyEvent.VK_UP) {
			model.turnUp();
		} else if (e.getKeyCode()== KeyEvent.VK_DOWN) {
			model.turnDown();
		} else if (e.getKeyCode()== KeyEvent.VK_1) {
			model.switchScreen(1);
		} else if (e.getKeyCode()== KeyEvent.VK_2) {
			model.switchScreen(2);
		} else if (e.getKeyCode()== KeyEvent.VK_3) {
			model.switchScreen(3);
		} else if (e.getKeyCode()== KeyEvent.VK_P) {
			model.pause();
		} else if (e.getKeyCode()== KeyEvent.VK_R) {
			model.returnToStart();
		} else if (e.getKeyCode()== KeyEvent.VK_Q) {
			model.quit(false);
		}
	}
}
