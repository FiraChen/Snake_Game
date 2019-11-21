import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.BorderLayout;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class Model {
  private int checked = 0;
  private int score = 0;
  private int curLevel = 0;
  private Boolean inGame = false;
  private Boolean inFinal = false;

  private JFrame frame;
  private TopBar top;
  private ScreenOne one;
  private PlayScreen pscreen;
  private ScreenFinal last;
  private Snake snake;

  public Model(JFrame frm) {
    frame = frm;
    top = new TopBar(this, Integer.toString(curLevel));
    snake = new Snake();
  }

  public void returnToStart() {
    otherSound();
    inFinal = false;
    curLevel = 0;
    if (inGame) {
      top.pauseTimer();
    }

    // remove all the contant on the current screen
    frame.getContentPane().removeAll();

    // mark the game ended
    if (inGame){
      inGame = false;
      pscreen.quit();
    }
    snake = null;
    snake = new Snake();

    // clear the score
    score = 0;
    top.updateScore(score);
    checked = 0;
    top.updateApple(checked);

    // show the initial screen
    one = new ScreenOne();
    frame.add(one);
    frame.revalidate();
    frame.repaint();
  }

  public void switchScreen(int level) {
    levelSound();
    if (inFinal) {
      return;
    }
    frame.getContentPane().removeAll();

    // create the screen for the required level
    if (level == 1 && !inGame) {
      pscreen = new PlayScreen(this, 1);
      pscreen.connectSnake(snake);
      curLevel = 1;
      top.updateLevel(1);
    } else if (level == 2 && !inGame) {
      pscreen = new PlayScreen(this, 2);
      pscreen.connectSnake(snake);
      curLevel = 2;
      top.updateLevel(2);
    } else if (level == 3 && !inGame) {
      pscreen = new PlayScreen(this, 3);
      pscreen.connectSnake(snake);
      curLevel = 3;

      // level three has unlimited time
      top.updateLevel(3);
      top.removeTimer();
    }

		frame.add(top, BorderLayout.NORTH);
    frame.add(pscreen);
    frame.revalidate();
    frame.repaint();

    inGame = true;
  }

  // turn the snake leftward
  public void turnLeft() {
    rightLeftSound();
    if (curLevel == 1 || curLevel == 2 || curLevel == 3) {
      pscreen.turnLeft();
    }
  }

  // turn the snake to the right
  public void turnRight() {
    rightLeftSound();
    if (curLevel == 1 || curLevel == 2 || curLevel == 3) {
      pscreen.turnRight();
    }
  }

  // turn the snake up
  public void turnUp() {
    rightLeftSound();
    if (curLevel == 1 || curLevel == 2 || curLevel == 3) {
      pscreen.turnUp();
    }
  }

  // turn the snake to the down
  public void turnDown() {
    rightLeftSound();
    if (curLevel == 1 || curLevel == 2 || curLevel == 3) {
      pscreen.turnDown();
    }
  }

  // pause the game
  public void pause() {
    otherSound();

    if (curLevel == 1 || curLevel == 2 || curLevel == 3) {
      pscreen.pause();

      if (curLevel == 1 || curLevel == 2) {
        top.pauseTimer();
      }
    }
  }

  // quit the game
  public void quit(Boolean win) {
    otherSound();

    if (!inGame) {
      return;
    }

    inGame = false;
    inFinal = true;

    if (curLevel == 1 || curLevel == 2 || curLevel == 3) {
      pscreen.quit();
    }
    frame.getContentPane().removeAll();

    snake = null;
    snake = new Snake();

    // display the final screen
    last = new ScreenFinal(score, win);
    frame.add(last);

    // clear the score
    score = 0;
    top.updateScore(score);
    checked = 0;
    top.updateApple(checked);

    frame.revalidate();
    frame.repaint();
  }

  // level up
  public void updateScore(int level) {
    score += level;
    top.updateScore(score);
  }

  // more apples are eaten
  public void updateApple() {
    checked++;
    top.updateApple(checked);
  }

  // go to the next level
  public void nextLevel() {
    frame.getContentPane().removeAll();
    frame.add(top, BorderLayout.NORTH);

    pscreen.quit();
    pscreen = null;

    if (curLevel == 1) {
      curLevel = 2;
      pscreen = new PlayScreen(this, 2);
      top.updateLevel(2);

      pscreen.connectSnake(snake);
      frame.add(pscreen);
    } else if (curLevel == 2) {
      curLevel = 3;
      pscreen = new PlayScreen(this, 3);
      top.updateLevel(3);

      pscreen.connectSnake(snake);
      top.removeTimer();
      frame.add(pscreen);
    } else if (curLevel == 3) {
      // if it's the last level, quit the game
      quit(true);
      return;
    }
    frame.revalidate();
    frame.repaint();
  }

  // snake dies, game over
  public void gameOver() {
    if (curLevel != 3) {
      top.removeTimer();
    }
    inGame = false;
    inFinal = true;
    curLevel = 0;

    overSound();

    // display the last screen
    frame.getContentPane().removeAll();
    last = null;
    last = new ScreenFinal(score, false);
    frame.add(last, BorderLayout.CENTER);

    // create a new snake
    snake = null;
    snake = new Snake();

    // clear the score
    score = 0;
    top.updateScore(score);
    checked = 0;
    top.updateApple(checked);

    frame.revalidate();
    frame.repaint();
  }

  // game over sound play
  private void overSound() {
    Clip background;
    try {
      File file = new File("src/sound/gameOver.wav");
      background = AudioSystem.getClip();
      background.open(AudioSystem.getAudioInputStream(file));
      background.start();
    } catch (UnsupportedAudioFileException e) {
       e.printStackTrace();
    } catch (IOException e) {
       e.printStackTrace();
    } catch (LineUnavailableException e) {
       e.printStackTrace();
    }
  }

  private void rightLeftSound() {
    Clip background;
    try {
      File file = new File("src/sound/turn.wav");
      background = AudioSystem.getClip();
      background.open(AudioSystem.getAudioInputStream(file));
      background.start();
    } catch (UnsupportedAudioFileException e) {
       e.printStackTrace();
    } catch (IOException e) {
       e.printStackTrace();
    } catch (LineUnavailableException e) {
       e.printStackTrace();
    }
  }

  private void levelSound() {
    Clip background;
    try {
      File file = new File("src/sound/pauseReturn.wav");
      background = AudioSystem.getClip();
      background.open(AudioSystem.getAudioInputStream(file));
      background.start();
    } catch (UnsupportedAudioFileException e) {
       e.printStackTrace();
    } catch (IOException e) {
       e.printStackTrace();
    } catch (LineUnavailableException e) {
       e.printStackTrace();
    }
  }

  private void otherSound() {
    Clip background;
    try {
      File file = new File("src/sound/123.wav");
      background = AudioSystem.getClip();
      background.open(AudioSystem.getAudioInputStream(file));
      background.start();
    } catch (UnsupportedAudioFileException e) {
       e.printStackTrace();
    } catch (IOException e) {
       e.printStackTrace();
    } catch (LineUnavailableException e) {
       e.printStackTrace();
    }
  }
}
