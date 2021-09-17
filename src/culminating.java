/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;
import java.awt.event.*;

class gameDisplay implements ActionListener {

    static int Width = 690;
    static int Height = 630;

    public static int ticks = 0;
    public static boolean gameOver = false; //checks if game is over
    public static boolean rendererOn = false; //checks if renderer is on
    public static boolean timerOn = false; // checks if timer is on
    public static Color background = new Color(31, 32, 37);
    public static int size = 30;
    public static int orient = 0; //direction the snake is facing
    public static Renderer renderer;
    public static boolean collision = false; //true if snake collides with anything
    public static boolean death = false; //ture is snake is dead
    public static Rectangle head, wall1 = new Rectangle(0, 0, Width, size), //walls of game
            wall2 = new Rectangle(0, 0, size, Height),
            wall3 = new Rectangle(Width - 2 * size, 0, size, Height),
            wall4 = new Rectangle(0, Height - 2 * size, Width, size);
    public static gameDisplay game;

    public static int count = 0;
    public static ArrayList<Rectangle> snake = new ArrayList<Rectangle>(); //snake body
    public static Rectangle apple; //apple that snake has to eat
    public static int appleX = 0;
    public static int appleY = 0;
    public static javax.swing.Timer timer;

    public static void pause(int i) {
        //makes game wait
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public gameDisplay() {
        //creates internal frame to run game
        JInternalFrame display = new JInternalFrame();
        timer = new javax.swing.Timer(250, this);
        timerOn = true;
        renderer = new Renderer();
        rendererOn = true;
        display.add(renderer);
        display.setSize(Width, Height);

        display.setResizable(false);
        display.setVisible(true);

        culminating.gamePanel.add(display); //adds internal frame to panel

        culminating.gamePanel.addKeyListener(new KeyListener() { //allows user keyboard input

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                move(e); //executes when user releases key

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }
        });
        culminating.gamePanel.setFocusable(true);
        culminating.gamePanel.requestFocusInWindow();

        head = new Rectangle(330, 300, size, size);
        snake.add(head);
        apple = apple();

        timer.start(); //start timer for game

    }

    public static Rectangle apple() {
        //sets random location for apple
        appleX = 30 * (((int) (Math.random() * 20)) + 1);
        appleY = 30 * (((int) (Math.random() * 18)) + 1);
        for (Rectangle r : snake) {
            //checks if apple is in the same location as the snake
            while (appleX == (int) r.getX() && appleY == (int) r.getY()) {
                appleX = 30 * (((int) (Math.random() * 20)) + 1);
                appleY = 30 * (((int) (Math.random() * 18)) + 1);

            }
        }
        
        Rectangle r = new Rectangle(appleX, appleY, size, size);
        return r; //returns a rectangle with the new location
    }

    public void repaint(Graphics g) {
        //sets the graphics of the game
        g.setColor(background);
        g.fillRect(0, 0, Width, Height);

        if (!collision) { //if snake has not collided with anything
            //paints walls
            g.setColor(Color.cyan);
            g.fillRect(0, 0, size, Height);
            g.fillRect(Width - 2 * size, 0, size, Height);
            g.fillRect(0, 0, Width, size);
            g.fillRect(0, Height - 2 * size, Width, size);
            for (Rectangle r : snake) {
                //paints snake
                g.setColor(Color.green.darker().darker());
                g.fillRect((int) r.getX(), (int) r.getY(), size, size);

            }

            g.setColor(Color.red);
            g.fillRect((int) apple.getX(), (int) apple.getY(), (int) apple.getWidth(), (int) apple.getHeight()); //paints apple
        } else if (!snake.isEmpty()) { //if snake has collided with something and has length >0

            g.setColor(background);
            g.fillRect(0, 0, Width, Height);
            for (Rectangle r : snake) {
                g.setColor(Color.green.darker().darker());
                g.fillRect((int) r.getX(), (int) r.getY(), size, size);
            }
            g.setColor(background);
            g.fillRect((int) snake.get(0).getX(), (int) snake.get(0).getY(), size, size);
            snake.remove(0); //paints snake and removes first unit for death animation
        } else if (death) { //if snake is dead (length = 0)
            //paints GAME OVER
            g.setColor(background);
            g.fillRect(0, 0, Width, Height);
            g.setColor(Color.red);
            g.fillRect(60, 120, 4 * size, size);
            g.fillRect(60, 120, size, 5 * size);
            g.fillRect(60, 240, 4 * size, size);
            g.fillRect(150, 210, size, 2 * size);
            g.fillRect(120, 180, 2 * size, size);
            g.fillRect(210, 120, size, 5 * size);
            g.fillRect(270, 120, size, 5 * size);
            g.fillRect(240, 120, size, size);
            g.fillRect(240, 180, size, size);
            g.fillRect(330, 120, size, 5 * size);
            g.fillRect(360, 120, size, size);
            g.fillRect(390, 150, size, 2 * size);
            g.fillRect(420, 120, size, size);
            g.fillRect(450, 120, size, 5 * size);
            g.fillRect(510, 120, size, 5 * size);
            g.fillRect(510, 120, 3 * size, size);
            g.fillRect(510, 180, 2 * size, size);
            g.fillRect(510, 240, 3 * size, size);
            g.fillRect(60, 330, 4 * size, size);
            g.fillRect(60, 330, size, 5 * size);
            g.fillRect(150, 330, size, 5 * size);
            g.fillRect(60, 450, 4 * size, size);
            g.fillRect(210, 330, size, 3 * size);
            g.fillRect(330, 330, size, 3 * size);
            g.fillRect(240, 420, size, size);
            g.fillRect(300, 420, size, size);
            g.fillRect(270, 450, size, size);
            g.fillRect(390, 330, size, 5 * size);
            g.fillRect(390, 330, 3 * size, size);
            g.fillRect(390, 390, 2 * size, size);
            g.fillRect(390, 450, 3 * size, size);
            g.fillRect(510, 330, size, 5 * size);
            g.fillRect(510, 330, 3 * size, size);
            g.fillRect(570, 330, size, 3 * size);
            g.fillRect(510, 390, 3 * size, size);
            g.fillRect(540, 420, size, size);
            g.fillRect(570, 450, size, size);

        } else {
            //paints default background
            g.setColor(background);
            g.fillRect(0, 0, Width, Height);

        }
    }

    public static void body(ArrayList<Rectangle> a) {
        //allows the snake to move properly and curve
        int x = (int) a.get(0).getX();
        int y = (int) a.get(0).getY();

        for (int i = 1; i < a.size(); i++) { //makes a unit of snake take its predecessor's position when movement occurs
            int tempx = (int) a.get(i).getX();
            int tempy = (int) a.get(i).getY();
            a.get(i).setLocation(x, y);
            x = tempx;
            y = tempy;

        }

    }

    public static void collisionDetect(ArrayList<Rectangle> a) {
        //detects if snake collides with wall
        if (a.get(0).intersects(wall1)
                || a.get(0).intersects(wall2)
                || a.get(0).intersects(wall3)
                || a.get(0).intersects(wall4)) {
            collision = true;
        }
        //detects if snake collides with itself
        for (int i = 1; i < a.size(); i++) {
            if (a.get(0).intersects(a.get(i))) {
                collision = true;
            }
        }

    }

    public static void appleTest(ArrayList<Rectangle> a) {
        //detects if snake got the apple
        if (a.get(0).getX() == appleX && a.get(0).getY() == appleY) {
            a.add(new Rectangle(660, 660, 30, 30));
            //makes new location for apple
            apple = apple();
            count++; //increases score
            culminating.scoreBoard.setText(String.valueOf(count));

        }

    }

    public static void move(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT: //move left when left arrow key is pressed
                if (!collision && orient != 2) { //if snake is not facing right
                    body(snake);
                    snake.get(0).setLocation((int) (snake.get(0).getX() - 30), (int) (snake.get(0).getY()));
                    appleTest(snake);
                    if (snake.size() != 1) {
                        orient = 4;
                    }
                    renderer.repaint();
                }
                collisionDetect(snake);
                break;
            case KeyEvent.VK_UP: //move up when up arrow key is pressed
                if (!collision && orient != 3) { //if snake is not facing down
                    body(snake);
                    snake.get(0).setLocation((int) snake.get(0).getX(), (int) snake.get(0).getY() - 30);
                    appleTest(snake); //test if snake got the apple
                    if (snake.size() != 1) {
                        orient = 1;
                    }
                    renderer.repaint();
                }
                collisionDetect(snake);
                break;
            case KeyEvent.VK_DOWN: //move down when down arrow key is pressed
                if (!collision && orient != 1) { //if snake is not facing up
                    body(snake);
                    snake.get(0).setLocation((int) snake.get(0).getX(), (int) snake.get(0).getY() + 30);
                    appleTest(snake);
                    if (snake.size() != 1) {
                        orient = 3;
                    }
                    renderer.repaint();
                }

                collisionDetect(snake);
                break;
            case KeyEvent.VK_RIGHT: //move right when right arrow key is pressed
                if (!collision && orient != 4) { //if snake is not facing left
                    body(snake);
                    snake.get(0).setLocation((int) snake.get(0).getX() + 30, (int) snake.get(0).getY());
                    appleTest(snake);
                    if (snake.size() != 1) {
                        orient = 2;
                    }
                    renderer.repaint();
                }
                collisionDetect(snake);
            default:
                renderer.repaint();
        }
        //System.out.println(snake.get(0).getX() + "" + snake.get(0).getY());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ticks++; //keeps track of time
        if (culminating.gameStart && !collision) {
            if (ticks % 2 == 0) {
                if ((int) apple.getWidth() > 0) {
                    //shrinks the apple slowly
                    apple.setRect(apple.getX() + 1.5, apple.getY() + 1.5, apple.getWidth() - 3, apple.getHeight() - 3);

                    renderer.repaint();

                }
            }
            if ((int) apple.getWidth() < 0) {
                //makes player lose if apple disappears
                collision = true;

            }

        }
        if (collision) {

            renderer.repaint();
            pause(750);
            if (ticks % 1 == 0) {
                if (!snake.isEmpty()) {
                    //death animation
                    renderer.repaint();

                }
                if (snake.isEmpty() && !gameOver) {
                    //displays GAME OVER screen 
                    renderer.repaint();
                    pause(750);
                    death = true;
                    //hides start game and back buttons
                    culminating.startButton.setVisible(false);
                    culminating.backButton.setVisible(false);
                    renderer.repaint();
                    pause(750);
                    //sets leaderboard button visible
                    culminating.leaderboardButton.setVisible(true);
                    culminating.leaderboardButton.setLocation((int) culminating.leaderboardButton.getBounds().getX() + 100, (int) culminating.leaderboardButton.getBounds().getY());
                    gameOver = true;

                }

            }
        }

    }

}

public class culminating extends javax.swing.JFrame {

    public static boolean gameStart = false;
    public static ArrayList<String> namesList = new ArrayList<String>();
    public static ArrayList<String> scoresList = new ArrayList<String>();

    /**
     * Creates new form culminating
     */
    public culminating() {
        initComponents();
        this.setResizable(false);
        leaderboardButton.setVisible(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        startButton = new javax.swing.JButton();
        gamePanel = new javax.swing.JPanel();
        backButton = new javax.swing.JButton();
        scoreBoard = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        leaderboardButton = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        startButton.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        startButton.setText("start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout gamePanelLayout = new javax.swing.GroupLayout(gamePanel);
        gamePanel.setLayout(gamePanelLayout);
        gamePanelLayout.setHorizontalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 666, Short.MAX_VALUE)
        );
        gamePanelLayout.setVerticalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 630, Short.MAX_VALUE)
        );

        backButton.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        scoreBoard.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        scoreBoard.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        scoreBoard.setText("0");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Score");

        leaderboardButton.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        leaderboardButton.setText("Go to Leaderboard");
        leaderboardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leaderboardButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(296, 296, 296)
                .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(leaderboardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(scoreBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(133, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(gamePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(153, 153, 153))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(78, 78, 78))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(gamePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(scoreBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(44, 44, 44))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(leaderboardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        // TODO add your handling code here:
        if (!gameStart) {
            scoreBoard.setEditable(false);
            //creates object and starts game
            gameDisplay.game = new gameDisplay();
            gameStart = true;

        }

    }//GEN-LAST:event_startButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:
        if (gameDisplay.timerOn) {
            gameDisplay.timer.stop(); //stops timer
        }
        if (gameDisplay.rendererOn) {
            gameDisplay.renderer.removeAll();
        }
        //switch screens to homescreen
        homescreen screen = new homescreen();
        screen.setVisible(true);
        this.setVisible(false);


    }//GEN-LAST:event_backButtonActionPerformed
    public static void addScore(String s, int i) {
        //adds player name and score to score file
        try {
            BufferedWriter write = new BufferedWriter(new FileWriter("C:\\Users\\admin\\Desktop\\scores.txt", true));
            write.write(s);
            write.newLine();
            write.write(String.valueOf(i));
            write.newLine();
            write.close();
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    public static void sort() {
        //sorts scores by descending order
        int size = scoresList.size();
        for (int step = 0; step < size - 1; step++) {
            int max = step;
            for (int i = step + 1; i < size; i++) {
                if (Integer.parseInt(scoresList.get(i)) > Integer.parseInt(scoresList.get(max))) {
                    max = i;
                }
            }
            String temp = scoresList.get(step);
            String temp2 = namesList.get(step);
            scoresList.set(step, scoresList.get(max));
            namesList.set(step, namesList.get(max));
            scoresList.set(max, temp);
            namesList.set(max, temp2);

        }
        
        for (int j = 0; j < namesList.size()-1; j++) { //remove duplicate names and scores
            for (int i = j + 1; i < namesList.size(); i++) {
                if (namesList.get(j).equals(namesList.get(i))){
                    namesList.remove(i);
                    scoresList.remove(i);
                    i--;
                    
                }
            }
        }
        
    }

    public static String printScore() {
        //returns string to be printed on leaderboard
        String s = "";
        try {
            BufferedReader read = new BufferedReader(new FileReader("C:\\Users\\admin\\Desktop\\scores.txt"));
            String line;
            int count = 0;
            while ((line = read.readLine()) != null) {
                if (count % 2 == 0) {
                    namesList.add(line); //adds names to array
                    count++;
                } else if (count % 2 != 0) {
                    scoresList.add(line); //adds scores to array
                    count++;
                }
            }
            read.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
        
        sort(); //sorts arrays by descending order
        for (int i = 0; i < 10; i++) {
            s = s + (i + 1) + ". " + namesList.get(i) + ": " + scoresList.get(i) + "\n"; //adds name and score to string 
        }
        namesList.clear();
        scoresList.clear();
        return s; //returns string with names and scores
    }
    private void leaderboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leaderboardButtonActionPerformed
        // TODO add your handling code here:
        //switch screens to leaderboard
        leaderboard screen = new leaderboard();
        screen.setVisible(true);
        this.setVisible(false);
        addScore(playerName.name, gameDisplay.count);
        if (playerName.name != null) {
        leaderboard.yourScoreLabel.setText(playerName.name + "'s Score: ");
        }
        leaderboard.lbDisplay.setText(printScore()); //displays names and scores on leaderboard
        leaderboard.lbDisplay.setEditable(false);
        leaderboard.yourScore.setText(String.valueOf(gameDisplay.count)); //displays current player score on leaderboard
        if (gameDisplay.timerOn) {
            gameDisplay.timer.stop(); //stops timer
        }
        if (gameDisplay.rendererOn) {
            gameDisplay.renderer.removeAll();
        }
        //resets game
        culminating.gameStart = false;
        gameDisplay.ticks = 0;
        gameDisplay.snake.clear();
        gameDisplay.orient = 0;
        gameDisplay.collision = false;
        gameDisplay.gameOver = false;
        gameDisplay.death = false;
        gameDisplay.count = 0;

    }//GEN-LAST:event_leaderboardButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(culminating.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(culminating.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(culminating.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(culminating.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new culminating().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton backButton;
    public static javax.swing.JPanel gamePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    public static javax.swing.JButton leaderboardButton;
    public static javax.swing.JTextField scoreBoard;
    public static javax.swing.JButton startButton;
    // End of variables declaration//GEN-END:variables
}
