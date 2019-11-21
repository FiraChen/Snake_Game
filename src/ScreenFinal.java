import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.BorderLayout;

class ScreenFinal extends JPanel {

  public ScreenFinal(int score, Boolean win) {
    this.setBackground(new Color(199, 214, 208));

    // if collision happends in the game
    if (!win) {
      JLabel finalScore = new JLabel("Oops! Game Over...\nYour score is: " + Integer.toString(score));
      finalScore.setFont(new Font("Serif", Font.PLAIN, 30));
      this.add(finalScore, BorderLayout.SOUTH);
    } else {
      // if user wins the game
      JLabel finalScore = new JLabel("Congratulation! You win the game!\nYour score is: " + Integer.toString(score));
      finalScore.setFont(new Font("Serif", Font.PLAIN, 30));
      this.add(finalScore, BorderLayout.SOUTH);
    }
  }
}
