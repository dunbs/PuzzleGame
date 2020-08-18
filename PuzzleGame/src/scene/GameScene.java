/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import algorithm.Matrix;
import algorithm.TimeElapse;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author conqu
 */
public class GameScene extends JPanel implements Cloneable {

    private Matrix matrix;
    private JButton[][] buttons;
    private int maxSize;
    private int currentSize;
    private int moveCount;
    private JLabel moveCountLb;
    private TimeElapse timeElapse;

    public GameScene(int width, int height, JLabel moveCountLb, JLabel elapsedLb) {
        matrix = new Matrix();
        this.moveCountLb = moveCountLb;
        timeElapse = new TimeElapse(elapsedLb);
        this.setSize(width, height);
        this.setMaxSize();
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public void windowResize() {
        setMaxSize();
        if (currentSize == 0) {
            return;
        }
        setUpButton(currentSize);
        setButtonValue();
    }

    public void setNumberOfButton(int size) {
        this.currentSize = Math.min(size, this.maxSize);
    }
    
    public void newGame(int size) {
        this.currentSize = size;
        
        moveCount = 0;
        moveCountLb.setText("0");
        setUpButton();
    }
    
    public void setUpButton() {
        if (currentSize == 0) {
            return;
        }
        
        timeElapse.startCount();
        matrix.setNumberMatrix(currentSize);
        setUpButton(currentSize);
        setButtonValue();
    }

    private void win() {
        if (matrix.isCorrectOrder()) {
            timeElapse.stopCount();
            if (JOptionPane.showConfirmDialog(null, "Play Again?", "You Win!", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_OPTION) {
                setUpButton();
            }
        }
    }

    private void setUpButton(int numberOfButton) {
        this.removeAll();
        this.setLayout(new GridLayout(numberOfButton, numberOfButton));

        // get number
        buttons = new JButton[numberOfButton][numberOfButton];

        for (int i = 0; i < numberOfButton; i++) {
            for (int j = 0; j < numberOfButton; j++) {

                //Set new button
            //number 0 means empty
                JButton button = new JButton();

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton button = (JButton) e.getSource();
                        int width = getWidth() / numberOfButton;
                        int height = getHeight() / numberOfButton;
                        int x = button.getLocation().x / width;
                        int y = button.getLocation().y / height;
                        moveButton(x, y);
                    }
                });

                //Add button to scene
                this.add(button);

                //Add button to array
                buttons[i][j] = button;
            }
        }
    }

    private void setButtonValue() {
        Font font = getFontSizeButton(buttons[0][0]);
        int[][] num = matrix.getNumberMatrix();
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons.length; j++) {
                buttons[i][j].setText(num[i][j] == 0
                        ? "" : String.valueOf(num[i][j]));

                buttons[i][j].setFont(font);
            }
        }
    }

    private void moveButton(int x, int y) {
        Integer direction = matrix.getDirection(y, x);
        // No where to go
        if (direction == null) {
            return;
        }

        Point empty = (Point) matrix.getEmptyPosition().clone();
        Point target = matrix.move(direction);
        if (target == null) {
            return;
        }

        String targetNumber = buttons[target.x][target.y].getText();
        buttons[empty.x][empty.y].setText(targetNumber);
        
        buttons[target.x][target.y].setText("");

        this.moveCountLb.setText(String.valueOf(++moveCount));
        
        if (target.x == target.y && target.x == currentSize - 1) {
            win();
        }
    }

    /**
     * Set font size for button
     *
     * @param button
     * @return
     */
    private Font getFontSizeButton(JButton button) {
        int fontSize = this.getSize().height / currentSize;
        FontMetrics metric;
        Font font;
        String longestNum = String.valueOf(currentSize * currentSize);
        int width = this.getSize().width / (currentSize * 2);
        int height = this.getSize().height / (currentSize * 2);
        do {
            font = new Font("Arial", Font.PLAIN, fontSize--);
            metric = button.getFontMetrics(font);
        } while (metric.getHeight() + metric.getLeading() > height * 80 / 100
                || metric.stringWidth(longestNum) + metric.getLeading() > width * 80 / 100);
        return font;
    }

    /**
     * Set max number of buttons
     */
    private void setMaxSize() {
        // init
        int fontSize;
        int size = 2;
        FontMetrics metric;
        Font font;
        int width;
        int height;
        String longestNum;
        JPanel panel = this;

        // clone panel to modify without changing value of the actual panel
        try {
            panel = (JPanel) this.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(GameScene.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Add one button to panel for the layout to set the size of the button
        JButton button = new JButton();
        panel.add(button);
        System.out.println(panel + " " + this);
        do { // increase size until font size is smaller than default max font size
            size++;

            // max size of font size is half a button
            width = panel.getSize().width / (size * 2);
            height = panel.getSize().height / (size * 2);

            // to get width of the longest number
            longestNum = String.valueOf(size * size);

            // let the layout decide the size of the button
            panel.setLayout(new GridLayout(size, size));

            // Estimate highest possible font size
            fontSize = panel.getSize().height / size;

            // decrease fontSize until meet condition
            do {
                font = new Font("Arial", Font.PLAIN, fontSize--);
                metric = button.getFontMetrics(font);
            } while // Font size in pixel cannot excced 80% of half a button
                    (metric.getHeight() > height * 80 / 100
                    || metric.stringWidth(longestNum) > width * 80 / 100);

        } while (fontSize > 11); // -> default max font size is 12 (11 + 1)

        this.maxSize = --size;
    }
}
