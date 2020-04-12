// library imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import lwjgl;
import scanner;

// main code class begins
public class Main {

    // width for screen is 300 pxls
    private final int B_WIDTH = 300;
    // height for screen is 300 pxls
    private final int B_HEIGHT = 300;
    // the size of the dot is 10 pxls
    private final int DOT_SIZE = 10;
    // all dots combined have the value of 900 pxls
    private final int ALL_DOTS = 900;
    // the rand position is 29
    private final int RAND_POS = 29;
    // the delay is 140
    private final int DELAY = 140;

    // x and y declares
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    // declare for the dot in the main compiled class
    private int dots;
    // the x value of the apple
    private int apple_x;
    // the y value of the apple
    private int apple_y;

    // moving left is false, based off the value of the main compiled class
    private boolean leftDirection = false;
    // moving right is true, based off the value of the main compiled class
    private boolean rightDirection = true;
    // moving up is false, based off the value of the main compiled class
    private boolean upDirection = false;
    // moving down is false, based off the value of the main compiled class
    private boolean downDirection = false;
    // the game loop is true, otherwise it does not run and it compiles nothing
    private boolean inGame = true;

      // the user's movable board is based off the value of this function (defined below), but the backend of the board is infinite
      public Board() {
        initBoard();
      }

      // the value of the board's public value function
      private void initBoard() {
        // the key listner is a new ta dapter
        addKeyListener(new TAdapter());
        // the background is black
        setBackground(Color.black);
        // the focusable is true
        setFocusable(true);
        // the size of the user's board is the combined value of the dimension of width and height
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        // loads all images we declare
        loadImages();
        // loads game
        initGame();
      }

      // the value of the image loading variable declared above
      private void loadImages() {
        // loads the image of the dot
        ImageIcon iid = new ImageIcon("src/recourses/dot.png");
        ball = iid.getImage();

        // loads the image of the apple
        ImageIcon iia = new ImageIcon("src/recourses/apple.png");
        apple = iia.getImage();

        // loads the image of the head
        ImageIcon iih = new ImageIcon("src/recourses/head.png");
        head = iih.getImage();
      }

      // game loop function value
      private void initGame() {
        // adds three dots after eating an apple
        dots = 3;
        // when an apple is eaten it adds the value
        for (int z = 0; z < dots; z++) {
          // x's value multiplies by 10 when apple is eaten
          x[z] = 50 - z * 10;
          // y's value is 50 when apple is eaten
          y[z] = 50;
        }

        // forces timer for game loop
        locateApple();

        // the timer delays the snake
        timer = new Timer(DELAY, this);
        // the timer starts
        timer.start();
      }

      // overrides the user's front for backend fixes
      @Override

      // adds colored graphics
      public void paintComponent(Graphics g) {
        // the paint has the graphics of g
        super.paintComponent(g);

        // loads drawing
        doDrawing(g);
      }

      // adds g graphics to the drawing function
      private void doDrawing(Graphics g) {

        // if the user is running the program, it enters the game loop
        if (inGame) {

          // it draws the image of the apple
          g.drawImage(apple, apple_x, apple_y, this);

          // the z axis allows the user to move
          for (int z = 0; z < dots; z++) {
            // if the user does not move, it enters a AFK loop
            if (z == 0) {
              // it draws the image of the head of the snake
              g.drawImage(head, x[z], y[z], this);
              // if the user dies, it enters this long loop, to kill off the program
            else {
              // it draws the image of a ball
              g.drawImage(ball, x[z], y[z], this);
              }
            }

            // it syncs the toolkit to the backend
            Toolkit.getDefaultToolkit().sync();

          }
          // it cuts the program using the function, defined below
          else {
            gameOver(g);
          }
        }
      }

      // the value of the game cut function
      private void gameOver() {

        // the string message of the game over
        String msg = "Game Over!";
        // font options
        Font small = new Font("Helvetica", Font.BOLD, 14);
        // font metrics
        FontMetrics metr = getFontMetrics(small);

        // the font color is white
        g.setColor(Color.white);
        // the font size is small
        g.setFont(small);
        // draws the msg string with our font options
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
      }

      // the value of the check apple function
      private void checkApple() {
        // if the x position and the y position are 0, it adds dot value
        if ((x[0] == apple_x) && (y[0] == apple_y)) {

          // here it adds the dot value for infinity
          dots++;
          // we declare a locate function for the apple
          locateApple();
        }
      }

      // here we add the detection of keys for moving
      private void move() {

        // we make z have the value of dots, and enter a direction loop
        for (int z = dots; z > 0; z--) {
          // z makes x and y go down 1
          x[z] = x[z - 1];
          x[y] = x[y - 1];
        }

        // if the user enters the left key, we lower dot size for x
        if (leftDirection) {
          x[0] -= DOT_SIZE;
        }
        // if the user enters the right key, we add dot size for x
        if (rightDirection) {
          x[0] += DOT_SIZE;
        }
        // if the user enters the up key, we lower dot size for y
        if (upDirection) {
          y[0] -= DOT_SIZE;
        }
        // if the user enters the down key, we lower dot size for y
        if (downDirection) {
          y[0] += DOT_SIZE;
        }
      }

    // collision detection function
    private void checkCollison() {

      // we once again enter the direction loop previously stated
      for (int z = dots; z > 0; z--) {

        // if AFK is true we know that they are not in game
        if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
          inGame = false;
        }
      }

      // if the y axis is more than the user can publicly access, it cuts the game, because that is not possible
      if (y[0] >= B_HEIGHT) {
        inGame = false;
      }

      // if the y axis is less than 0, the game cuts off because that is not possible
      if (y[0] < 0) {
        inGame = false;
      }

      // if the x axis is more than the user can publicly access, it cuts the game loop, because it is not possible
      if (x[0] >= B_WIDTH) {
        inGame = false;
      }

      // if the x axis is less than 0, it cuts off the game loop, because it is not possible
      if (x[0] < 0) {
        inGame = false;
      }

      // if the game loop is not running, it shuts down the program
      if (!inGame) {
        timer.stop();
      }
    }

  // the value of the locate apple function
  private void locateApple() {

    // the value of r is a random number times a random position
    int r = (int) (Math.random() * RAND_POS);
    // the x axis of the apple is r times the dot dot size
    apple_x = ((r * DOT_SIZE));

    // the value of r is a random number times a random position
    r = (int) (Math.random() * RAND_POS);
    // the y axis is r times dot size
    apple_y = ((r * DOT_SIZE));
  }

  // we add key movements using the t adapter library
  private class TAdapter extends KeyAdapter {

    // overrides key mapping from user
    @Override

    // the key pressed function
    public void keyPressed(KeyEvent e) {

      // the value of the "key" is based off what the user presses
      int key = e.getKeyCode();

      // if the key isnt the right key and has been pressed, it pushes the user left
      if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
        leftDirection = true;
        upDirection = false;
        downDirection = false;
      }

      // if the key isnt left and has been pressed, it pushes the user right
      if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
        rightDirection = true;
        upDirection = false;
        downDirection = false;
      }

      // if the key isnt down, and has been pressed, it pushes the user up
      if ((key == KeyEvent.VK_UP) && (!downDirection)) {
        upDirection = true;
        rightDirection = false;
        leftDirection = false;
      }

      // if the key isnt up, and has been pressed, it pushes the user down
      if ((key == KeyEvent.VK_DOWN) && (!downDirection)) {
        downDirection = true;
        rightDirection = false;
        leftDirection = false;
      }
    }
  }
}
