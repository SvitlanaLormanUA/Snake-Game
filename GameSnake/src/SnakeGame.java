import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;


public class SnakeGame extends JPanel implements ActionListener {
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = FRAME_WIDTH;
    private static final int UNITS_SIZE = 20;
    private final int DELAY = 100;
    private static final int GAME_UNITS = (FRAME_HEIGHT * FRAME_WIDTH) / UNITS_SIZE;
    char direction = 'R';
    int bodyparts = 6;
    int applesEaten;
    boolean ateApple;
    boolean running = false;
    Timer timer;
    Random random;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int appleX;
    int appleY;


    SnakeGame() {
        random = new Random();
        this.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new AdapterToGame());
        startGame();
    }

    private void startGame() {

        ateApple = true;
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void newApple() {
        if (ateApple) {
            ateApple = false;
            appleX = random.nextInt(1, FRAME_WIDTH / UNITS_SIZE) * UNITS_SIZE;
            appleY = random.nextInt(1, FRAME_HEIGHT / UNITS_SIZE) * UNITS_SIZE;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
       if (running) {
           /*for (int i = 0; i < FRAME_HEIGHT / UNITS_SIZE; i++) {
                g.drawLine(i * UNITS_SIZE, 0, i * UNITS_SIZE, FRAME_HEIGHT);
                g.drawLine(0, i * UNITS_SIZE, FRAME_WIDTH, i * UNITS_SIZE);
            }*/
            g.setColor(Color.red);
            g.fillOval(FRAME_WIDTH/2, 30, UNITS_SIZE, UNITS_SIZE);



            g.setFont(new Font("MV Boli", Font.BOLD, 30));
            g.setColor(Color.red);
            g.drawString(" " + applesEaten, FRAME_WIDTH/2 + 10, 50);
            if(ateApple) {
                g.setColor(Color.BLACK);
            } else {
                g.setColor(Color.red);
            }
            g.fillOval(appleX, appleY, UNITS_SIZE, UNITS_SIZE);

            for (int i = 0; i < bodyparts; i++) {
                if (i == 0) {
                    g.setColor(new Color(16, 130, 57));
                } else {
                    g.setColor(new Color(21, 155, 73));
                }
                g.fillRect(x[i], y[i] , UNITS_SIZE, UNITS_SIZE);
            }
        } else {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        g.setFont(new Font("MV Boli", Font.BOLD, 50));
        g.setColor(Color.red);
        g.drawString("Game Over  " , FRAME_WIDTH/2 - 130, FRAME_HEIGHT/2);
        g.setFont(new Font("MV Boli", Font.PLAIN, 30));
        g.drawString("Total Score: " + applesEaten , FRAME_WIDTH/2 - 100, FRAME_HEIGHT/2 + 50);
    }

    private void appleEat() {
        applesEaten++;
        bodyparts++;
        ateApple = true;
        newApple();
    }

    private void checkForCollision() {
for (int i = bodyparts; i>0; i--) {
    if(x[0] == x[i] && y[0] == y[i]) {
        running = false;
    }
}
        if( (x[0]<0) || (x[0] > FRAME_WIDTH) || (y[0] < 0) || (y[0] > FRAME_WIDTH) ) {
            running = false;
        }

        if(!running) {
            timer.stop();
        }


    }

    private void move() {

        for (int i = bodyparts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            // up
            case 'U':
                y[0] = y[0] - UNITS_SIZE;
                break;
            // down
            case 'D':
                y[0] = y[0] + UNITS_SIZE;
                break;
            // left
            case 'L':
                x[0] = x[0] - UNITS_SIZE;
                break;
            // right
            case 'R':
                x[0] = x[0] + UNITS_SIZE;
                break;
        }
        //checkForCollision();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkForCollision();
        }
        repaint();
    }

    private void checkApple() {
      if(x[0] == appleX && y[0] == appleY) {
          appleEat();
      }
    }

    private class AdapterToGame implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_UP :
                    if(direction!='D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction!='U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if(direction!='R') {
                        direction='L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!='L'){
                        direction='R';
                    }
                    break;
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
}
