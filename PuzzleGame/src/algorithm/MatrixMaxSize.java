/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import scene.GameScene;

/**
 *
 * @author conqu
 */
public class MatrixMaxSize {

    public int getMaxSize(int width, int height) {
        int fontSize;
        int size = 2;
        FontMetrics metric;
        Font font;
        String longestNum;
        JPanel panel = new GameScene(width, height, null, null);
        JButton button = new JButton();
        panel.add(button);
        do {
            size++;
            width = panel.getSize().width / (size * 2);
            height = panel.getSize().height / (size * 2);
            longestNum = String.valueOf(size * size);
            panel.setLayout(new GridLayout(size, size));
            fontSize = panel.getSize().height / size;

            do {
                font = new Font("Arial", Font.PLAIN, fontSize--);
                metric = button.getFontMetrics(font);
            } while (metric.getHeight() > height * 80 / 100 || metric.stringWidth(longestNum) > width * 80 / 100);
//            System.out.println(fontSize + " " + size);
        } while (fontSize > 11);
        return size;
    }
}
