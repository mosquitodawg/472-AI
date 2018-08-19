import java.awt.Point;

public class board {

	final static int BOARD_ROWS=8;
	final static int BOARD_COLS=14;

	private String[][] board=new String[BOARD_ROWS][BOARD_COLS];

	private Point boardPosition;
	private Point tempBoardPosition;

	int boardEmptySpaces;
	
	public static int getBoardRows(){
		return BOARD_ROWS;
	}
	public static int getBoardCols(){
		return BOARD_COLS;
	}
	
	
	
	//Initialize board
	public board(){
		
		for(int i=0; i < BOARD_ROWS; i++){
			for(int j=0; j < BOARD_COLS; j++){
				
				// Below numbers and left of letters
				if((j==0)&&(i==0)){
					board[i][j]=" ";
				}
				
				// x axis(Letters)
				else if((i==0)&&(j>=1)){
					board[i][j]=Integer.toString(j);
					board[i][j]=Character.toString(Character.forDigit(j+9, Character.MAX_RADIX)).toUpperCase();
				} 
				
				// y axis(Numbers)
				else if((j==0)&&(i>=1)){
					board[i][j]=Integer.toString(i);
				} 
				
				// initialize board space
				else if((i>=1)&&(j>=1)){
					board[i][j]="";
				}
			}
		}
		resetBoard();
	}
	
	//Puts dashes to represent board
	public void resetBoard(){
		boardEmptySpaces=49;
		for(int i=BOARD_ROWS-1 ; i>=1 ; i--){

			for(int x=i ; x<=BOARD_COLS-i ; x++){
				board[i][x]="-";
			}
		}
	}
	
	public void displayBoard()
	{
		for(int i=BOARD_ROWS-1 ; i>=0; i--){
			for(int j=0; j<BOARD_COLS; j++){
				System.out.print(board[i][j]);
				System.out.print("	");
			}			
			System.out.println();
		}
	}
	
	//Puts player symbol in target position
	public boolean setPosition(int i, int j, String symbol){
		if(isLegalPosition(i, j)){
			boardPosition=new Point(i, j);
			
			board[i][j]=symbol;
			
			boardEmptySpaces--;
			
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean setTempPos(int i, int j, String symbol,String[][] _board){
		if(isLegalPosition(i, j)){
			tempBoardPosition=new Point(i, j);
			
			_board[i][j]=symbol;
			
			return true;
		}
		else {
			return false;
		}
	}
	
	public void UnSetTempPos(int i, int j, String symbol,String[][] _board){
		
		tempBoardPosition=new Point(i, j);
			
		_board[i][j]="-";
			
		
	}
	
	
	//Returns true if legal position, false otherwise
	private boolean isLegalPosition(int i, int j){
		
		try
		{
			if(!board[i][j].equals("-")){
				return false;
			}
			else {
				return true;
			}
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public boolean boardIsFull(){
		if(boardEmptySpaces == 0){
			return true;
		} 
		else {
			return false;
		}
	}

	
	
	public board(String[][] _board){
		for(int i=0; i < BOARD_ROWS; i++){
			for(int j=0; j < BOARD_COLS; j++){
				
				// Below numbers and left of letters
				if((j==0)&&(i==0)){
					board[i][j]=" ";
				}
				
				// x axis(Letters)
				else if((i==0)&&(j>=1)){
					board[i][j]=Integer.toString(j);
					board[i][j]=Character.toString(Character.forDigit(j+9, Character.MAX_RADIX)).toUpperCase();
				} 
				
				// y axis(Numbers)
				else if((j==0)&&(i>=1)){
					board[i][j]=Integer.toString(i);
				} 
				
				// initialize board space
				else if((i>=1)&&(j>=1)){
					board[i][j]="";
				}
			}
		}
		
		for(int i=BOARD_ROWS-1 ; i>=1 ; i--){

			for(int x=i ; x<=BOARD_COLS-i ; x++){
				board[i][x]=_board[i][x];
				
			}
		}
	}
	
	public String[][] getBoard(){	
		return board.clone();
	}
	public int getEmptySpaces(){
		return boardEmptySpaces;
	}
	public Point getBoardPosition(){
		return boardPosition;
	}
	public void setBoardPosition(Point _point){
		boardPosition=_point;
	}
	public Point getTempBoardPosition(){
		return tempBoardPosition;
	}
}