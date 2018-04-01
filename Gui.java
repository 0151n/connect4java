
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Gui {

    //private static int cols[] = new int[7];
    private static int[] dir;
    private static int player = 0;
    private final static JButton grid[][] = new JButton[7][6];
    private static boolean live = true;
    private static JMenu turn;

    //load images ------------ Warning will need to be changed.
    final static ImageIcon blank = new ImageIcon("C:\\Users\\peter\\Documents\\NetBeansProjects\\Connect4\\src\\blank.png");
    final static ImageIcon red = new ImageIcon("C:\\Users\\peter\\Documents\\NetBeansProjects\\Connect4\\src\\red.png");
    final static ImageIcon yellow = new ImageIcon("C:\\Users\\peter\\Documents\\NetBeansProjects\\Connect4\\src\\yellow.png");

    private static void createGui() {

        //set up main frame
        JFrame frame = new JFrame("Connect4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //set up menu
        JMenuBar menuBar;
        menuBar = new JMenuBar();
        JMenu menu;
        menu = new JMenu("Options");
        turn = new JMenu("Turn: Yellow");
        JMenuItem reset = new JMenuItem(new AbstractAction("reset game") {
            public void actionPerformed(ActionEvent e) {
                System.out.println("reset");
                resetGrid();
            }
        });
        menu.add(reset);
        menuBar.add(menu);
        menuBar.add(turn);

        //make layout for frame and add it to it
        GridLayout mainGrid = new GridLayout(6, 7);
        frame.setLayout(mainGrid);
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
                        if (live) {
                            int i = Integer.parseInt(e.getActionCommand());

                            for (int j = 5; j >= 0; j--) {
                                if (grid[i][j].getIcon().equals(blank)) {
                                    grid[i][j].setIcon((player == 1 ? red : yellow));

                                    // Insert win check here
                                    dir = new int[7];
                                    if(checkWin(i, j, -1))
                                        break;
                                    System.out.println("====================");
                                    if (live) {
                                        //swap player
                                        player = (player == 1 ? 0 : 1);

                                        if (player == 1) {
                                            turn.setText("Turn: Red");
                                        } else {
                                            turn.setText("Turn: Yellow");
                                        }  
                                    }
                                    break;
                                }
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

    private static boolean checkWin(int i, int j, int D) {
        System.out.println("checkWin run as " + (player == 1 ? "red" : "yellow") + " with D = " + D);
        if (D == -1) {
            // Middle Blocks
            System.out.print("Pri-checks:");
            try {

                if (grid[i + 1][j - 1].getIcon().equals((player == 1 ? red : yellow))) {
                    dir[0]++;
                    System.out.print(" " + 0);
                }

            } catch (Exception e) {
                System.out.println("Exception Here");
            }
            try {

                if (grid[i + 1][j].getIcon().equals((player == 1 ? red : yellow))) {
                    dir[1]++;
                    System.out.print(" " + 1);
                }
            } catch (Exception e) {
                System.out.println("Exception Here");
            }
            try {

                if (grid[i + 1][j + 1].getIcon().equals((player == 1 ? red : yellow))) {
                    dir[2]++;
                    System.out.print(" " + 2);
                }
            } catch (Exception e) {
            }
            try {

                if (grid[i][j + 1].getIcon().equals((player == 1 ? red : yellow))) {
                    dir[3]++;
                    System.out.print(" " + 3);
                }
            } catch (Exception e) {
                System.out.println("Exception Here");
            }
            try {

                if (grid[i - 1][j + 1].getIcon().equals((player == 1 ? red : yellow))) {
                    dir[4]++;
                    System.out.print(" " + 4);
                }
            } catch (Exception e) {
                System.out.println("Exception Here");
            }
            try {

                if (grid[i - 1][j].getIcon().equals((player == 1 ? red : yellow))) {
                    dir[5]++;
                    System.out.print(" " + 5);
                }
            } catch (Exception e) {
                System.out.println("Exception Here");
            }
            try {

                if (grid[i - 1][j - 1].getIcon().equals((player == 1 ? red : yellow))) {
                    dir[6]++;
                    System.out.print(" " + 6);
                }
            } catch (Exception e) {
                System.out.println("Exception Here");
            }
            System.out.println();
            for (int d = 0; d < dir.length; d++) {
                if (dir[d] == 1) {
                    checkWin(i, j, d);
                }
                System.out.println("Dir[" + d + "] = " + dir[d]);
            }
            for (int z = 0; z < 3; z++) {
                if (dir[z] + dir[z + 4] > 3 || dir[3] > 3) {
                    System.out.println((player == 1 ? "red" : "yellow") + " wins");
                    live = false;
                    String text = (player == 1 ? "Red" : "Yellow") + " wins.\nPlay again?";
                    String title = "Game Over";
                    int optionType = JOptionPane.OK_CANCEL_OPTION;
                    int result = JOptionPane.showConfirmDialog(null, text, title, optionType);
                    if (result == JOptionPane.OK_OPTION) {
                        resetGrid();
                    }

                    return true;
                }
            }
        } else {

            try {
                System.out.println("Second type Check with D = " + D);
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
                        if (grid[i-1][j].getIcon().equals((player == 1 ? red : yellow))) {
                            dir[D]++;
                            checkWin(i-1, j, D);
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
                System.out.println("Exception Here Last");
            }

        }
        return false;
    }

    private static void resetGrid() {
        //reset all icons to blank
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                grid[j][i].setIcon(blank);
            }
        }
        player = 0;
        live = true;
        turn.setText("Turn: Yellow");
        //reset all columns to 0
        //for(int j = 0; j < 7;j++)cols[j] = 0;
    }

    public static void main(String[] Args) {
        System.out.println("Hello There");
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGui();
            }
        });
    }
}
