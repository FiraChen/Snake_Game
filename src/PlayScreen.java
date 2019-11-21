import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;

import java.util.Timer;
import java.util.TimerTask;

class PlayScreen extends JPanel {
  // used to specify the moving speed
  private final int FPS_one = 30;
  private final int FPS_two = 50;
  private final int FPS_three = 70;

  private JPanel board;
  private JPanel panel;
  private JLabel apple;
  private Snake snake;
  private Model model;
  private Timer timer;
  private TimerTask task;

  // the position of apples
  private int[] pos_one = {34, 125, 241, 92, 356};
  private int[] pos_two = {72, 48, 92, 127, 125, 176, 223, 242, 281, 25, 371, 322};
  private int[] pos_three = {111, 27, 41, 62, 86, 136, 107, 157, 235, 244, 276, 302, 331, 376, 312, 95, 124, 296};
  private int lastPos = 0;
  private int lastHead = 0;
  private int curApple = 0;
  private int shouldMove = 5;

  // record the current level
  private int level = 0;
  private Boolean pause = false;

  private ImageIcon eyes = new ImageIcon("./src/material/eyes.png");
  private JLabel eyeLabel = new JLabel(eyes);

  public PlayScreen(Model m, int lvl) {
    model = m;
    level = lvl;

    this.setBackground(new Color(199, 214, 208));

    board = new Board();

    // apple to be displayed on the board
    ImageIcon fruit = new ImageIcon("./src/material/apple2.png");
    fruit.setImage(fruit.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));

    // get the position of apples for corresponding level
    int[] pos = pos_one;
    if (level == 2) {
      pos = pos_two;
    } else if (level == 3) {
      pos = pos_three;
    }

    apple = new JLabel(fruit);
    panel = (JPanel)board.getComponent(pos[curApple]);
    panel.add(apple);

    this.add(board);
  }

  // check if the snake catch the apple
  private void checkApple(int head) {
    int size = 5;
    int[] pos = pos_one;
    if (level == 2) {
      size = 12;
      pos = pos_two;
    } else if (level == 3) {
      size = 18;
      pos = pos_three;
    }

    if (curApple < size) {
      if (pos[curApple] == head) {
        // let the model update the score
        model.updateScore(level);
        model.updateApple();

        // snake becomes longer
        snake.longer();

        // remove current apple
        panel = (JPanel)board.getComponent(pos[curApple]);
        panel.removeAll();
        panel.revalidate();
        panel.repaint();

        // display the next apple
        curApple++;
        if (curApple < size) {
          panel = (JPanel)board.getComponent(pos[curApple]);
          panel.add(apple);
          panel.revalidate();
          panel.repaint();
        }
      }
    }

    // the snake got all the apple!
    if (curApple == size && level == 3) {
      curApple = 0;
      timer.cancel();
      model.nextLevel();
    }
  }

  // redraw the square that the snake has passed
  private void redrawSquare() {
    // get the corresponding square
    panel = (JPanel)board.getComponent(lastPos);
    int row = (lastPos / 20) % 2;
    if (row == 0) {
      if (lastPos % 2 == 0) {
        panel.setBackground(new Color(132, 191, 126));
      } else {
        panel.setBackground(new Color(189, 224, 184));
      }
    } else {
      if (lastPos % 2 == 0) {
        panel.setBackground(new Color(189, 224, 184));
      } else {
        panel.setBackground(new Color(132, 191, 126));
      }
    }
  }

  // draw the snake on the board
  private void drawSnake() {
    int currentFPS = FPS_one;
    if (level == 2) {
      currentFPS = FPS_two;
    } else if (level == 3) {
      currentFPS = FPS_three;
    }

    int[] snakePos = snake.getPos();;

    if (level == 1) {
      // get snake initial position
      lastPos = snakePos[0];
      panel = (JPanel)board.getComponent(snakePos[0]);
      panel.setBackground(new Color(179, 153, 121));
      panel.add(eyeLabel);
    } else {
      // get position of snake
      int length = snake.getDots();
      lastPos = snakePos[length - 1];
      lastHead = snakePos[0];

      for (int i = 0; i < length; i++) {
        panel = (JPanel)board.getComponent(snakePos[i]);
        panel.setBackground(new Color(179, 153, 121));

        if (i == 0) {
          panel.add(eyeLabel);
        }
      }
    }

    // set the timer
    timer = new Timer();
    task = new TimerTask()  {
      // array to store snake position
      int[] snakePos;

      @Override
      public void run() {
        // let the snake move
        if (shouldMove == 0) {
          // redraw the last square
          redrawSquare();
          panel = (JPanel)board.getComponent(lastHead);
          panel.removeAll();
          panel.revalidate();
          panel.repaint();

          int colli = snake.move();
          shouldMove = 5;

          if (colli == 1) {
            collision();
            return;
          }
        }

        // get position of snake
        snakePos = snake.getPos();
        int length = snake.getDots();
        lastPos = snakePos[length - 1];
        lastHead = snakePos[0];

        for (int i = 0; i < length; i++) {
          panel = (JPanel)board.getComponent(snakePos[i]);
          panel.setBackground(new Color(179, 153, 121));

          if (i == 0) {
            panel.add(eyeLabel);
          }
        }

        checkApple(snakePos[0]);
        shouldMove--;
      }
    };
    timer.schedule(task, 0, (1000/currentFPS));
  }

  // turn the snake to left
  public void turnLeft() {
    snake.turnLeft();
  }

  // turn the snake to right
  public void turnRight() {
    if (lastPos < 10 || lastPos > 390) {
      // collision
      collision();
    } else {
      snake.turnRight();
    }
  }

  // turn the snake to left
  public void turnUp() {
    if (lastPos < 10) {
      // collision
      collision();
    } else {
      snake.turnUp();
    }
  }

  // turn the snake to right
  public void turnDown() {
    if (lastPos > 390) {
      // collision
      collision();
    } else {
      snake.turnDown();
    }
  }

  // pause the game
  public void pause() {
    int currentFPS = FPS_one;
    if (level == 2) {
      currentFPS = FPS_two;
    } else if (level == 3) {
      currentFPS = FPS_three;
    }

    // pause hit again
    if (pause) {
      timer = new Timer();
      task = new TimerTask()  {
        // array to store snake position
        int[] snakePos;

        @Override
        public void run() {
          // let the snake move
          if (shouldMove == 0) {
            // redraw the last square
            redrawSquare();
            panel = (JPanel)board.getComponent(lastHead);
            panel.removeAll();
            panel.revalidate();
            panel.repaint();

            int colli = snake.move();
            shouldMove = 5;

            if (colli == 1) {
              collision();
              return;
            }
          }

          // get position of snake
          snakePos = snake.getPos();
          int length = snake.getDots();
          lastPos = snakePos[length - 1];
          lastHead = snakePos[0];

          for (int i = 0; i < length; i++) {
            panel = (JPanel)board.getComponent(snakePos[i]);
            panel.setBackground(new Color(179, 153, 121));

            if (i == 0) {
              panel.add(eyeLabel);
            }
          }

          checkApple(snakePos[0]);
          shouldMove--;
        }
      };
      timer.schedule(task, 0, (1000/currentFPS));
      pause = false;
    } else {
      timer.cancel();
      timer = null;
      pause = true;
    }
  }

  // quit the game
  public void quit() {
    timer.cancel();
    timer = null;
  }

  public void collision() {
    timer.cancel();
    timer = null;
    model.gameOver();
  }

  public void connectSnake(Snake sna) {
    snake = sna;
    drawSnake();
  }
}
