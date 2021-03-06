
/**
 * Challenge 8
 * Java Gui program to simulate the Connect4 board game.
 * Group: 33
 *
 * @author Oisin O'Halloran : 17225477, Peter Roe : 17238544
 * @version 01/04/2018
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Gui {

    //array used in win check, needs to be global as win check is recursive
    private static int[] dir;
    //variable to hold id of current player
    private static int player = 0;
    //grid of buttons to be used for board
    private final static JButton grid[][] = new JButton[7][6];
    private static JMenu turn;
    private static JMenu results;
    //total number of games
    private static int games = 0;
    //wins by red
    private static int redWins = 0;
    //wins by yellow
    private static int yellowWins = 0;
    
    //images
    static ImageIcon blank = new ImageIcon();
    static ImageIcon red = new ImageIcon();
    static ImageIcon yellow = new ImageIcon();

    /**
     * Method used to create and run the game.
     */
    private static void createGui() {
        //load images from files
        blank = new ImageIcon(Gui.class.getClassLoader().getResource("imgs/blank.png"));
        yellow = new ImageIcon(Gui.class.getClassLoader().getResource("imgs/yellow.png"));
        red = new ImageIcon(Gui.class.getClassLoader().getResource("imgs/red.png"));
        //set up main frame
        JFrame frame = new JFrame("Connect4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //set up menu
        JMenuBar menuBar;
        menuBar = new JMenuBar();
        
        //menu section for game options
        JMenu menu;
        menu = new JMenu("Options");
        
        //menu section to show current player
        turn = new JMenu("Turn: Yellow");
        turn.setOpaque(true);
        turn.setBackground(Color.yellow);
        
        //meu option to show results
        results = new JMenu("  Games: " + games + "   Red: " + redWins + "   Yellow: " + yellowWins);

        //event listner for menu buttons
        JMenuItem reset = new JMenuItem(new AbstractAction("reset game") {
            public void actionPerformed(ActionEvent e) {
                resetGrid();
            }
        });
        JMenuItem quit = new JMenuItem(new AbstractAction("quit") {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        //add elements to menu bars
        menu.add(reset);
        menu.add(quit);
        menuBar.add(menu);
        menuBar.add(turn);
        menuBar.add(results);

        //make layout for frame and add it to it
        GridLayout mainGrid = new GridLayout(6, 7);
        frame.setLayout(mainGrid);
        //add menubar to frame
        frame.setJMenuBar(menuBar);

        //create array of buttons
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                //make new "space"
                grid[j][i] = new JButton("");
                //set action command to column number
                grid[j][i].setActionCommand(j + "");
                //add action listener for each button
                grid[j][i].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //convert action command to int

                        int i = Integer.parseInt(e.getActionCommand());

                        for (int j = 5; j >= 0; j--) {
                            if (grid[i][j].getIcon().equals(blank)) {
                                grid[i][j].setIcon((player == 1 ? red : yellow));

                                // Insert win check here
                                //reset dir array
                                dir = new int[7];
                                if (checkWin(i, j, -1)) {
                                    break;
                                }
                                System.out.println("====================");

                                //swap player
                                player = (player == 1 ? 0 : 1);

                                if (player == 1) {
                                    turn.setText("Turn: Red");
                                    turn.setBackground(Color.red);
                                } else {
                                    turn.setText("Turn: Yellow");
                                    turn.setBackground(Color.yellow);
                                }

                                break;
                            }

                        }
                    }
                });
                //set icon to blank
                grid[j][i].setIcon(blank);
                //set size to size of icon
                grid[j][i].setPreferredSize(new Dimension(55, 55));
                //remove border
                grid[j][i].setBorder(BorderFactory.createEmptyBorder());
                //add button to frame
                frame.add(grid[j][i]);
            }
        }
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Method used to check for if winning conditions are present.
     *
     * @param i coordinate for button to check.
     * @param j coordinate for button to check.
     * @param D specific direction to check. -1 for all directions.
     * @return true if the game is over.
     */
    private static boolean checkWin(int i, int j, int D) {
        System.out.println("checkWin run as " + (player == 1 ? "red" : "yellow") + " with D = " + D);
        if (D == -1) {
            //top right adjacent block
            try {

                if (grid[i + 1][j - 1].getIcon().equals((player == 1 ? red : yellow))) {
                    dir[0]++;
                }

            } catch (Exception e) {
            }
            //right adjacent block
            try {

                if (grid[i + 1][j].getIcon().equals((player == 1 ? red : yellow))) {
                    dir[1]++;
                }
            } catch (Exception e) {
            }
            //bottom right adjacent block
            try {

                if (grid[i + 1][j + 1].getIcon().equals((player == 1 ? red : yellow))) {
                    dir[2]++;
                }
            } catch (Exception e) {
            }
            //bottom adjacent block
            try {
                if (grid[i][j + 1].getIcon().equals((player == 1 ? red : yellow))) {
                    dir[3]++;
                }
            } catch (Exception e) {
            }
            //bottom left adjacent block
            try {

                if (grid[i - 1][j + 1].getIcon().equals((player == 1 ? red : yellow))) {
                    dir[4]++;
                }
            } catch (Exception e) {
            }
            //left adjacent block
            try {

                if (grid[i - 1][j].getIcon().equals((player == 1 ? red : yellow))) {
                    dir[5]++;
                }
            } catch (Exception e) {
            }
            //top left adjacent block
            try {
                if (grid[i - 1][j - 1].getIcon().equals((player == 1 ? red : yellow))) {
                    dir[6]++;
                }
            } catch (Exception e) {
            }
            System.out.println();
            for (int d = 0; d < dir.length; d++) {
                if (dir[d] == 1) {
                    checkWin(i, j, d);
                }
            }
            // Check for winning condition
            for (int z = 0; z < 3; z++) {
                if (dir[z] + dir[z + 4] > 3 || dir[3] > 3) {
                    System.out.println((player == 1 ? "red" : "yellow") + " wins");
                    String text = (player == 1 ? "Red" : "Yellow") + " wins.\nPlay again?";
                    String title = "Game Over";
                    int optionType = JOptionPane.OK_CANCEL_OPTION;
                    int result = JOptionPane.showConfirmDialog(null, text, title, optionType);
                    if (result == JOptionPane.OK_OPTION) {
                        //update results
                        games++;
                        if(player == 1)redWins++;
                        else yellowWins++;
                        resetGrid();
                    } else {
                        System.exit(0);
                    }

                    return true;
                }
            }
        } else {

            try {
                switch (D) {
                    case 0:
                        if (grid[i + 1][j - 1].getIcon().equals((player == 1 ? red : yellow))) {
                            dir[D]++;
                            checkWin(i + 1, j - 1, D);
                        }
                        break;
                    case 1:
                        if (grid[i + 1][j].getIcon().equals((player == 1 ? red : yellow))) {
                            dir[D]++;
                            checkWin(i + 1, j, D);
                        }
                        break;
                    case 2:
                        if (grid[i + 1][j + 1].getIcon().equals((player == 1 ? red : yellow))) {
                            dir[D]++;
                            checkWin(i + 1, j + 1, D);
                        }
                        break;
                    case 3:
                        if (grid[i][j + 1].getIcon().equals((player == 1 ? red : yellow))) {
                            dir[D]++;
                            checkWin(i, j + 1, D);
                        }
                        break;
                    case 4:
                        if (grid[i - 1][j + 1].getIcon().equals((player == 1 ? red : yellow))) {
                            dir[D]++;
                            checkWin(i - 1, j + 1, D);
                        }
                        break;
                    case 5:
                        if (grid[i - 1][j].getIcon().equals((player == 1 ? red : yellow))) {
                            dir[D]++;
                            checkWin(i - 1, j, D);
                        }
                        break;
                    case 6:
                        if (grid[i - 1][j - 1].getIcon().equals((player == 1 ? red : yellow))) {
                            dir[D]++;
                            checkWin(i - 1, j - 1, D);
                        }
                        break;
                    default:
                        break;
                }

            } catch (Exception e) {
            }

        }
        return false;
    }

    /**
     * Method for reseting the game.
     */
    private static void resetGrid() {
        //reset all icons to blank
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                grid[j][i].setIcon(blank);
            }
        }
        //reset player to yellow
        player = 0;
        //update gui
        turn.setText("Turn: Yellow");
        turn.setBackground(Color.yellow);
        //update results
        results.setText("Games: " + games + " Red: " + redWins + " Yellow: " + yellowWins);
    }

    /**
     * Main method used to run Connect4 game.
     */
    public static void main(String[] Args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //run gui method
                createGui();
            }
        });
    }
}
