import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

class ScreenOne extends JPanel {
	public ScreenOne() {
		this.setBackground(new Color(199, 214, 208));

		ImageIcon name = new ImageIcon("./src/material/name.png");
		//name.setImage(name.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
		ImageIcon snake = new ImageIcon("./src/material/snake.png");
		ImageIcon description = new ImageIcon("./src/material/description.png");
		ImageIcon control = new ImageIcon("./src/material/control.png");

		// set grid bag layout
		this.setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();;
    gc.fill = GridBagConstraints.BOTH;

		gc.gridx = 0;
    gc.gridy = 0;
    gc.gridwidth = 2;
    gc.gridheight = 1;
		JLabel nameLabel = new JLabel(name);
    this.add(nameLabel, gc);

		gc.gridx = 2;
    gc.gridy = 0;
    gc.gridwidth = 2;
    gc.gridheight = 1;
		JLabel snakeLabel = new JLabel(snake);
    this.add(snakeLabel, gc);

		gc.gridx = 0;
    gc.gridy = 1;
    gc.gridwidth = 4;
    gc.gridheight = 1;
		JLabel descriptionLabel = new JLabel(description);
    this.add(descriptionLabel, gc);

		gc.gridx = 0;
    gc.gridy = 2;
    gc.gridwidth = 4;
    gc.gridheight = 2;
		JLabel controlLabel = new JLabel(control);
    this.add(controlLabel, gc);
	}
}
