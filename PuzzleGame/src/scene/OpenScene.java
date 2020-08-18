/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author conqu
 */
public class OpenScene extends JPanel {

    public OpenScene(int width, int height) {
        this.setSize(width, height);
        this.setLayout(new BorderLayout());
        JLabel title = new JLabel();
        title.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Tittle.png")));
        this.add(title, BorderLayout.CENTER);
    }
}
