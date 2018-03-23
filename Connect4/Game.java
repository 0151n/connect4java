package Connect4;

import java.util.Arrays;

public class Game {
	private int cols[] = new int[7];
	private int player = 0;
    private int grid[][] = new int[7][6];
    
    public Game(){
    	resetGrid();
    }
    public void resetGrid(){
		//reset all icons to blank
		for(int i = 0;i < 6;i++){
        	for(int j = 0; j < 7;j++){
        		grid[j][i] = -1;
        	}
        }
		//reset all columns to 0
		for(int j = 0; j < 7;j++)cols[j] = 0;
	}
	public int getCols(int coord) {
		try { 
			return cols[coord];
		} catch(ArrayIndexOutOfBoundsException exception){
			System.out.println("-----Invalid Column Index-----");
		}
		return 10; // return as if column is full to avoid further errors
	}
	public int getCurrentPlayer(){
		return player;
	}
	public int nextPlayer(){
		player = (player == 0 ? 1 : 0);
		return player;
	}
	public void addPiece(int index) {

		if(cols[index] < 6){
			grid[index][cols[index]] = player;
			cols[index]++;
		}
	}
	@Override
	public String toString() {
		return "Game [grid=" + Arrays.toString(grid) + "]";
	}
}
