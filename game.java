import java.awt.Point; 
import java.util.Scanner;

public class game {

	private boolean gameover=false;
	private int playerTurn;
	private String typed;
	static player players[];
	
	player[] setGameType(int gameType, board board, winning detectWin){
	
		if(gameType == 1){
			players=new player[2];
			//instantiate human players
			players[0]=new player(board,"Player 1", '*');
			players[1]=new player(board,"Player 2", 'O');
		}
		if(gameType == 2){
			players=new player[2];
			//instantiate human and ai
			players[0]=new player(board,"Player 1", '*');
			players[1]=new aiPlayer(board,"Player 2", 'O');
		}
		if(gameType == 3){
			players=new player[2];
			//instantiate ai and human
			players[0]=new aiPlayer(board,"Player 1", '*');
			players[1]=new player(board,"Player 2", 'O');
		}
		if(gameType == 4){
			players=new player[2];
			//instantiate ai players
			players[0]=new aiPlayer(board,"Player 1", '*');
			players[1]=new aiPlayer(board,"Player 2", 'O');
		}
		
		else if(gameType <1||gameType>4) {
			System.out.println();
			System.out.println("Not available now, try again!");
			System.out.println();
		}
		return players;
		
	}
	
	public int displayMainMenu(){
		try{
		System.out.println("-------------------------------------");
		System.out.println("Welcome to the Polarized Ladder Game!");			
		System.out.println("Select game type:");
		System.out.println("-------------------------------------");
		System.out.println("1: Human vs Human");
		System.out.println("2: Human vs Computer");
		System.out.println("3: Computer vs Human");
		System.out.println("4: Computer vs Computer");
		System.out.println("-------------------------------------");

		Scanner input=new Scanner(System.in);
		typed=input.next();
		int number=Integer.parseInt(typed);
	
		return number;
		}
		catch(Exception e){
			System.out.println();
			System.out.println("Invalid input, try again!");
			System.out.println();
			return displayMainMenu();
		}
	}

	private void startHumanGame(board board, winning detectWin, player [] _players){
		while(!gameover){

			//Display board at start
			board.displayBoard();			

			// Check if draw
			if(board.boardIsFull()){		
				System.out.println("It is a draw!\n");
				gameover=true;
				break;
			}

			//player turn
			if(playerTurn==0){	
				nextPlayerMove(_players, detectWin, board, playerTurn);

			} else {
				nextPlayerMove(_players, detectWin, board, playerTurn);
			}
			System.out.println("-------------------------------------------------------------------------------------------------------------------");
		}

		System.out.print("Game over! Thanks for playing!");
	}
	
	private void nextPlayerMove(player[] _players, winning detectWin, board board, int playerTurn){
		Point playerMove=new Point();

		while(!(_players[playerTurn].setDisc(playerMove.y, playerMove.x)))
		{
			playerMove=_players[playerTurn].doPlayerTurn(_players[playerTurn]);
		}

		//opposing player
		int oPlayerTurn=getNextPlayerTurn(playerTurn);
		
		if( detectWin.findLadder(_players[playerTurn].getPlayerSymbol(), 
				_players[oPlayerTurn].getPlayerSymbol(), playerMove)){

			printGameOver( _players[playerTurn], board);
			gameover=true;
		}

		setNextPlayerTurn(playerTurn);
		
	}    

	public int getNextPlayerTurn(int playerTurn){
		int nextPlayer=0;
		if(playerTurn == 0)
			nextPlayer =1;
		
		else {
			if(playerTurn == 1)
				nextPlayer =0;
		}
		return nextPlayer;
	}

	private static void printGameOver(player player, board board){

		board.displayBoard();
		System.out.println( player.getPlayerName()+" Won!" );
	}

	public void setNextPlayerTurn(int _playerTurn){
		if(_playerTurn == 0)
			playerTurn=1;
		else 
			if(_playerTurn == 1)
			playerTurn=0;
		
	}

	public static void main(String[] args){
			
	//Instantiate constructors
    game game=new game();
    board board= new board();
    winning findWin= new winning(board);
    
    //Making sure user chooses a game type that is available
    do{
    	int gameType=game.displayMainMenu();
    	players=game.setGameType(gameType, board, findWin);
    }
    while(players==null);
    
    game.startHumanGame(board, findWin, players);
	}
}