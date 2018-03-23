package Connect4;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Gui {
    //load images
	final static ImageIcon blank = new ImageIcon("blank.png");  
	final static ImageIcon red = new ImageIcon("red.png");  
	final static ImageIcon yellow = new ImageIcon("yellow.png");  
	
	private final static JButton grid[][] = new JButton[7][6];
	
	static Game game = new Game();
	
	private static void createGui(){

		//set up main frame
		JFrame frame = new JFrame("Connect4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //set up menu
        JMenuBar menuBar;
        menuBar = new JMenuBar();
        JMenu menu;
        menu = new JMenu("game");
        
        @SuppressWarnings("serial")
		JMenuItem reset = new JMenuItem(new AbstractAction("reset") {
			public void actionPerformed(ActionEvent e) {
                System.out.println("reset");
                game.resetGrid();
            }
        });
        menu.add(reset);
        menuBar.add(menu);
        
        
        //make layout for frame and add it to it
        GridLayout mainGrid = new GridLayout(6,7);
        frame.setLayout(mainGrid);
        frame.setJMenuBar(menuBar);
        
        //create array of buttons
        for(int i = 0;i < 6;i++){
        	for(int j = 0; j < 7;j++){
        		//make new "space"
        		grid[j][i] = new JButton("");
        		//set action command to column number
        		grid[j][i].setActionCommand(j + "");
        		//add action listener for each button
        		grid[j][i].addActionListener(new ActionListener(){
        			  public void actionPerformed(ActionEvent e){
        				  //convert action command to int
        				  int coord = Integer.parseInt(e.getActionCommand());
        				  //check column isnt full
        				  if(game.getCols(coord) < 6){
        					  //add new piece to column with color based on whos turn it is by
        					  //changing the color of the icon
        					  grid[coord][5 - game.getCols(coord)].setIcon((game.getCurrentPlayer() == 1 ? red : yellow));
        					  //increment number of pieces in this column
        					  game.addPiece(coord);
        					  //swap player
        					  game.nextPlayer();
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
	
	public static void main(String[] Args){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGui();
            }
        });
	}
}
