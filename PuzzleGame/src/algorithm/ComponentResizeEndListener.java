/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.Timer;

/**
 *
 * @author conqu
 */
public abstract class ComponentResizeEndListener extends ComponentAdapter implements ActionListener {

    private final Timer timer;

    public ComponentResizeEndListener() {
        timer = new Timer(100, this);
        timer.setRepeats(false);
    }

    @Override
    public void componentResized(ComponentEvent evt) {
        timer.restart();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        resizedTimeOut();
    }

    public abstract void resizedTimeOut();

}
