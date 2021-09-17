/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.JPanel;
import java.awt.Graphics;
/**
 *
 * @author User
 */
public class Renderer extends JPanel {
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gameDisplay.game.repaint(g);
        
    }
}
