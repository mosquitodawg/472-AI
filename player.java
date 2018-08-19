import java.awt.Point;
import java.util.Scanner;
 

public class player {

	
	private String playerName;	
	protected char playerSymbol;
	
	static board board;
	
	public player(){
		
	}
	public player(board _board, String _playerName, char _playerSymbol){
		board=_board;
		playerName= _playerName;
		playerSymbol= _playerSymbol;	
	}
	
	public Point doPlayerTurn(player player){
		
		System.out.print("Enter your next move player "+playerName+" : ");
		
		Scanner input=new Scanner(System.in);	
		
		try{
			String playerMove 	= input.nextLine();
			
			//First character entered
			String rowTemp= playerMove.substring(0,1);	
			int row= Integer.parseInt(rowTemp);
			
			//Second character entered
			char [] colTemp=playerMove.toUpperCase().toCharArray();
			int col= Character.getNumericValue( colTemp[1] )- 9;
	
			Point disc=new Point(col, row);
			System.out.println(col+" "+row);
			return disc;
		}
		
		//For array out of bounds
		catch(Exception e)
		{
			System.out.println("Example of input : 1a");
			return doPlayerTurn(player);
		}
	}
	
	public boolean setDisc(int i, int j){
		return board.setPosition(i, j, String.valueOf(playerSymbol));
		}

	public String getPlayerName(){
		return playerName;
	}

	public String getPlayerSymbol(){
		return String.valueOf(playerSymbol);
	}
	public char getOpposingPlayerSymbol(){
		if(playerSymbol=='*'){
			return 'O';
		}
		else{
			return '*';
		}
	}
}