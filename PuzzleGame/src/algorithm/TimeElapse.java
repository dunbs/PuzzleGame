/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.Timer;


/**
 *
 * @author conqu
 */
public class TimeElapse implements ActionListener {

    private final Timer timer;
    private JLabel countDown;
    private int num;

    public TimeElapse(JLabel time) {
        this.countDown = time;
        timer = new Timer(1000, this);
    }

    public void startCount() {
        num = 0;
        timer.start();
    }

    public void stopCount() {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        num++;
        int minutes = num / 60;
        int seconds = num % 60;
        countDown.setText(String.format("%02d:%02d", minutes, seconds));
    }

}
