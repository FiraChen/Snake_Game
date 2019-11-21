import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;

class TopBar extends JPanel {
	private Model model;
	private JLabel fruitCount;
	private JLabel levelName;
	private JLabel scoreCount;
	private CountDown timer;

	public TopBar(Model m, String level) {
		model = m;

		setBackground(new Color(122, 168, 116));
		setPreferredSize(new Dimension(1200, 60));

		// count how many apple has been eaten
		ImageIcon fruit = new ImageIcon("./src/material/fruit.png");
		fruit.setImage(fruit.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		fruitCount = new JLabel("0");
		fruitCount.setFont(new Font("Serif", Font.PLAIN, 18));

		// count the score
		ImageIcon trophy = new ImageIcon("./src/material/trophy.png");
		trophy.setImage(trophy.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		scoreCount = new JLabel("0");
		scoreCount.setFont(new Font("Serif", Font.PLAIN, 18));

		// display the current level
		levelName = new JLabel("Level " + level);
		levelName.setFont(new Font("Serif", Font.PLAIN, 20));

		// display the time
		ImageIcon timerIcon = new ImageIcon("./src/material/timer.png");
		timerIcon.setImage(timerIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		timer = new CountDown(model);
		timer.pause();

		// use GridBagLayout
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;

		gc.gridx = 0;
		gc.gridy = 1;
		gc.weightx = 1;
		gc.weighty = 1;
		JLabel fruitLabel = new JLabel(fruit);
		this.add(fruitLabel, gc);

		gc.gridx = 2;
		gc.gridy = 1;
		gc.weightx = 1;
		gc.weighty = 1;
		this.add(fruitCount, gc);

		gc.gridx = 4;
		gc.gridy = 1;
		gc.weightx = 1;
		gc.weighty = 1;
		JLabel trophyLabel = new JLabel(trophy);
		this.add(trophyLabel, gc);

		gc.gridx = 5;
		gc.gridy = 1;
		gc.weightx = 1;
		gc.weighty = 1;
		this.add(scoreCount, gc);

		gc.gridx = 6;
		gc.gridy = 1;
		gc.weightx = 1;
		gc.weighty = 1;
		JLabel timerLabel = new JLabel(timerIcon);
		this.add(timerLabel, gc);

		gc.gridx = 7;
		gc.gridy = 1;
		gc.weightx = 1;
		gc.weighty = 1;
		this.add(timer, gc);

		gc.gridx = 10;
		gc.gridy = 1;
		gc.weightx = 2;
		gc.weighty = 1;
		this.add(levelName, gc);
	}

	// update the current score using the current level as
	//		points need to be added
	public void updateScore (int score) {
		scoreCount.setText(String.valueOf(score));
	}

	// increment the number of apple
	public void updateApple(int apple) {
		fruitCount.setText(String.valueOf(apple));
	}

	// display next level
	public void updateLevel (int level) {
		levelName.setText("Level " + String.valueOf(level));
		timer.reset();
	}

	public void removeTimer() {
    timer.removeTimer();
  }

	public void pauseTimer() {
		timer.pause();
	}
}
