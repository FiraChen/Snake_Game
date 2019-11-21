import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;

public class Board extends JPanel {
  public Board() {
    this.setLayout(new GridLayout(20, 20));
    this.setPreferredSize(new Dimension(700, 700));

    // initialize total of 400 squares
    for (int i = 0; i < 400; i++) {
      JPanel square = new JPanel(new BorderLayout());

      int row = (i / 20) % 2;
      // draw the square using different colors
      if (row == 0) {
        if (i % 2 == 0) {
          square.setBackground(new Color(132, 191, 126));
        } else {
          square.setBackground(new Color(189, 224, 184));
        }
      } else {
        if (i % 2 == 0) {
          square.setBackground(new Color(189, 224, 184));
        } else {
          square.setBackground(new Color(132, 191, 126));
        }
      }

      this.add(square);
    }
  }
}
