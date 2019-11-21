import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.String;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;

public class CountDown extends JPanel {
  // set the game time
  private int interval = 31;
  private int delay = 1000;
  private int period = 1000;
  private Boolean paused = false;

  private Timer timer;
  private Model model;
  private JLabel count = new JLabel("");

  public CountDown(Model m) {
    model = m;
    this.setBackground(new Color(122, 168, 116));
    this.setPreferredSize(new Dimension(30, 30));

    count.setFont(new Font("Serif", Font.PLAIN, 18));
    timer = new Timer();

    // add the count on the top bar
    this.add(count);

    // what to do in every timer check
    timer.scheduleAtFixedRate(new TimerTask() {
        public void run() {
            count.setText(String.valueOf(setInterval()));
        }
    }, delay, period);
  }

  // set the time
  private int setInterval() {
      // time reaches 0, stop the timer
      if (interval == 1) {
        if (timer != null) {
          timer.cancel();
          timer = null;
        }

        // if time is up, go to next level
        model.nextLevel();
      }
      return --interval;
  }

  // reset the time
  public void reset() {
    paused = false;
    interval = 31;

    this.removeAll();
    this.repaint();
    this.revalidate();

    count = null;
    count = new JLabel("");
    count.setFont(new Font("Serif", Font.PLAIN, 18));
    this.add(count);

    timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
        public void run() {
          count.setText(String.valueOf(setInterval()));
        }
    }, delay, period);
  }

  // set the time to unlimited for level 3
  public void removeTimer() {
    if (timer != null) {
      timer.cancel();
      timer = null;
    }

    this.removeAll();
    this.repaint();
    this.revalidate();
    count = null;
    count = new JLabel("∞");
    count.setFont(new Font("Serif", Font.PLAIN, 18));
    this.add(count);
  }

  // pause the timer
  public void pause() {
    if (!paused) {
      paused = true;
      timer.cancel();
      timer = null;
    } else {
      // when the pause button pushed again, create new timer
      //   and restart counting down
      paused = false;
      timer = new Timer();
      timer.scheduleAtFixedRate(new TimerTask() {
          public void run() {
              count.setText(String.valueOf(setInterval()));
          }
      }, delay, period);
    }
  }
}
