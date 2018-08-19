import java.awt.Point;
import java.util.*;




public class aiPlayer extends player { 

	static Point helperPoint=new Point();
	
	static Point bestPoint;
	private valueOfMove bestValue=new valueOfMove();
	
	public aiPlayer(){
		
	}
	public aiPlayer(board _board, String _playerName, char _playerSymbol){
		super(_board, _playerName, _playerSymbol);	
	}
	
	public Point doPlayerTurn(player player){
		
		
		System.out.print("Enter your next move player "+getPlayerName()+" : ");
		
		int col;
		int row;
		
		Point move=new Point();
		
		valueOfMove a=new valueOfMove(helperPoint, -1000000000);
		valueOfMove b=new valueOfMove(helperPoint, 1000000000);
		
		long startTime=System.currentTimeMillis();
		
		
		if(board.getEmptySpaces()>48){
			
			move=minimax(board, 1,true,a,b).getMove();
		}
		else if(board.getEmptySpaces()>22){
		
			move=minimax(board, 3,true,a,b).getMove();
		}
		else if(board.getEmptySpaces()>12){
			move=minimax(board, 4,true,a,b).getMove();
		}
		else{
			move=minimax(board, 5,true,a,b).getMove();
		}
		
		long endTime=System.currentTimeMillis();
		
		System.out.println("Time: "+(endTime-startTime)/1000.0+" seconds");
		
		col=move.x;
		
		row=move.y;
		
		Point disc=new Point(row, col);
		return disc;	

	}
	
	public ArrayList<board> allMoves(board _board, String _symbol){
		
		
		
		ArrayList<board> allMoves = new ArrayList<board>();
		
	
		
		for(int i=board.getBoardRows()-1 ; i>=1 ; i--){
			for(int x=i ; x<=board.getBoardCols()-i ; x++){
				
				board tempBoard=new board(_board.getBoard());
				
				if(tempBoard.setPosition(i,x,_symbol)){
					allMoves.add(tempBoard);
				}
			}
		}
		//Collections.shuffle(allMoves);
		return allMoves;
	}
	
	public valueOfMove minimax(board _board,int _depth, boolean max, valueOfMove a, valueOfMove b){
		
		int depth=_depth;
		
		if(depth>board.getEmptySpaces()){
			depth=board.getEmptySpaces();
		}
		
		if(depth==0){

			if(max==false){
				valueOfMove bestValue2=new valueOfMove(_board.getBoardPosition(),winning.heuristic1(String.valueOf(getPlayerSymbol()), String.valueOf(getOpposingPlayerSymbol()),_board));
				return bestValue2;
			}
			else{
				valueOfMove bestValue2=new valueOfMove(_board.getBoardPosition(),winning.heuristic1( String.valueOf(getOpposingPlayerSymbol()),String.valueOf(getPlayerSymbol()),_board));
				return bestValue2;
			}

		}
		//max player turn
		if(max==true){
			//valueOfMove bestValue=new valueOfMove();
			bestValue.setValue(Integer.MIN_VALUE);
			bestValue.setMove(helperPoint);
			
			valueOfMove v=new valueOfMove();
		
			for(board x:allMoves(_board, String.valueOf(getPlayerSymbol()))){
				
				v=minimax(x,depth-1, false, a, b);
				
				if(v.getValue()>bestValue.getValue()){
					bestValue=v;
				}
				
				valueOfMove a2=a;
				
				if(bestValue.getValue()>a2.getValue()){
					a2=new valueOfMove(v);
				}
				
				if(b.getValue()<=a2.getValue()){
					break;
				}
				
			}
			return bestValue;
		}
		//min player turn
		else{
			//valueOfMove bestValue=new valueOfMove();
			bestValue.setValue(Integer.MAX_VALUE);
			bestValue.setMove(helperPoint);
			
			valueOfMove v=new valueOfMove();
			
			for(board x:allMoves(_board, String.valueOf(getOpposingPlayerSymbol()))){
				v=minimax(x,depth-1, true,a,b);
				
				if(v.getValue()<bestValue.getValue()){
					bestValue=v;
				}

				valueOfMove b2=b;
				
				if(bestValue.getValue()<b2.getValue()){
					b2=new valueOfMove(v);
				}
				if(b2.getValue()<=a.getValue()){
					break;
				}
			}
			return bestValue;
		}
	}
	
}