import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

import java.util.Random;

class Snake extends JPanel {
    private int dots = 1;
    private int[] pos = new int[400];

    private Boolean left = false;
    private Boolean right = true;
    private Boolean up = false;
    private Boolean down = false;

    public Snake() {
      // randomize starting ball location
      Random rand = new Random();

      //use this as head
      pos[0] = rand.nextInt(400);
      while (pos[0] % 20 >= 15) {
        pos[0] = rand.nextInt(400);
      }
    }

    public void turnLeft() {
      if (left) {
        left = false;
        down = true;
      } else if (right) {
        right = false;
        up = true;
      } else if (up) {
        up = false;
        left = true;
      } else if (down) {
        down = false;
        right = true;
      }
    }

    public void turnRight() {
      if (left) {
        left = false;
        up = true;
      } else if (right) {
        right = false;
        down = true;
      } else if (up) {
        up = false;
        right = true;
      } else if (down) {
        down = false;
        left = true;
      }
    }

    public void turnUp() {
      if (left) {
        left = false;
        up = true;
      } else if (right) {
        right = false;
        up = true;
      }
    }

    public void turnDown() {
      if (left) {
        left = false;
        down = true;
      } else if (right) {
        right = false;
        down = true;
      }
    }

    public int move() {
      for (int i = dots; i > 0; i--) {
        pos[i] = pos[(i - 1)];
      }
      if (left) {
        if (pos[0] % 20 == 0) {
          return 1;
        }
        pos[0] -= 1;
      }
      if (right) {
        if (pos[0] % 20 == 19) {
          return 1;
        }
        pos[0] += 1;
      }
      if (up) {
        if (pos[0] <= 19) {
          return 1;
        }
        pos[0] -= 20;
      }
      if (down) {
        if (pos[0] >= 380) {
          return 1;
        }
        pos[0] += 20;
      }

      for (int i = dots; i > 0; i--) {
        if ((i > 4) && (pos[0] == pos[i])) {
          return 1;
        }
      }
      return 0;
    }

    public int[] getPos() {
      return pos;
    }

    public void longer() {
      dots++;
    }

    public int getDots() {
      return dots;
    }
}
