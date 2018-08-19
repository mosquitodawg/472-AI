import java.awt.Point;

public class winning { 
	private static board board;
	private String[][] boardClone;
	private final static int POINTS_FOR_LADDER=5;
	
	
	private static Point ladderPatterns[][]={ 			
			
		//Ladder in right direction
		{new Point(0,0), new Point(1,0), new Point(1,1), new Point(2,1), new Point(2,2)},
		{new Point(-1,0), new Point(0,0), new Point(0,1), new Point(1,1), new Point(1,2)},
		{new Point(-1,-1), new Point(0,-1), new Point(0,0), new Point(1,0), new Point(1,1)},
		{new Point(-2,-1), new Point(-1,-1), new Point(-1,0), new Point(0,0), new Point(0,1)},
		{new Point(-2,-2), new Point(-1,-2), new Point(-1,-1), new Point(0,-1), new Point(0,0)},
			
		//Ladder in left direction
		{new Point(0,0), new Point(-1,0), new Point(-1,1), new Point(-2,1), new Point(-2,2)},
		{new Point(1,0), new Point(0,0), new Point(0,1), new Point(-1,1), new Point(-1,2)},
		{new Point(1,-1), new Point(0,-1), new Point(0,0), new Point(-1,0), new Point(-1,1)},
		{new Point(2,-1), new Point(1,-1), new Point(1,0), new Point(0,0), new Point(0,1)},
		{new Point(2,-2),  new Point(1,-2), new Point(1,-1), new Point(0,-1), new Point(0,0)},};
	
	public winning(board _board){
		board=_board;
	}
	
	public boolean findLadder(String playerSymbol, String opposingPlayerSymbol, Point currPoint){
		String checkLadderPointSymbol;
		boardClone=board.getBoard();
		
		for(int i=0; i<ladderPatterns.length; i++){
			int ladderPoints=0;
			
			//All ladders have 5 points
			for(int j=0; j<POINTS_FOR_LADDER; j++){
				int xPos=currPoint.x + ladderPatterns[i][j].x;
				int yPos=currPoint.y + ladderPatterns[i][j].y;
				
				try{
					checkLadderPointSymbol=boardClone[yPos][xPos];
				}
				
				//Can check ladders that go off the board
				catch(Exception e){
					checkLadderPointSymbol="";
				}
				
				if(checkLadderPointSymbol.equals(playerSymbol)){
					ladderPoints++;
				}
				else{
					ladderPoints=0;
					break;
				}
			}
			
			if(ladderPoints==5){
				
				// obtain ladder matching pattern, and ladder direction
				Point[] ladder=new Point[5];
				
				String ladderDirection;
				if(i>4){
					ladderDirection="Left";
				}
				else{
					ladderDirection="Right";
				}
				
				for(int j=0; j<POINTS_FOR_LADDER; j++){
					ladder[j]=new Point( currPoint.x + ladderPatterns[i][j].x, currPoint.y + ladderPatterns[i][j].y);
				}
				
				//check if ladder touches bottom of triangle
				if(isPolarized(ladder, ladderDirection)){
					// ladder polarized: automatic win
					return true;
				}
				else{
					//if no neutralize, then win
					if(isNeutralized(ladder, ladderDirection, opposingPlayerSymbol,board)){
						return false;
					}
					else{
						return true;
					}
				}
			}
		}
			
		//If no 5 ladderPoints
		return false;
	}
	// check polarity
	
	public boolean isPolarized(Point[] ladder, String ladderDirection){
		
		//Bottom of triangle always polarized
		if((ladder[0].y==1)&&(ladder[1].y==1)){
			return true;
		}
		
		return false;
	}
	
	public static boolean isNeutralized(Point[] ladder, String ladderDirection, String opposingPlayerSymbol,board _board){
		String[][] boardClone= _board.getBoard();
		
		if(ladderDirection.equals("Left")){
			try{
			// neutralize point 1
			Point p1=new Point(ladder[0].x-2, ladder[0].y);
			// neutralize point 2
			Point p2=new Point(ladder[0].x, ladder[0].y+2);
			
			
			
			// if board points p1 and p2 do not contain player symbol
			if((boardClone[p1.y][p1.x].equals(opposingPlayerSymbol))&&
				(boardClone[p2.y][p2.x].equals(opposingPlayerSymbol))){
				// Ladder neutralized
				System.out.println("Ladder Neutralized!");
				return true;
			}
			}
			catch(Exception e){
				return false;
			}
		}
		else if(ladderDirection.equals("Right")){
			try{
				
				// Neutralize point 1
				Point p1=new Point(ladder[0].x+2, ladder[0].y);	
				// Neutralize point 2
				Point p2=new Point(ladder[0].x, ladder[0].y+2);	
			
				//If point 1 and 2 have opposing symbol
				if((boardClone[p1.y][p1.x].equals(opposingPlayerSymbol))&&
						(boardClone[p2.y][p2.x].equals(opposingPlayerSymbol))){
				
					//Ladder neutralized
					System.out.println("Ladder Neutralized!");
					return true;
				}
			}
			catch(Exception e){
				return false;
			}
		}
		return false;
	}
	public static int heuristic1(String playerSymbol, String opposingPlayerSymbol, board _board){
		int goodPoints=0;
		int badPoints=0;
		int tempPoints=0;
		
		int goodBad=0;
		
		
		String checkLadderPointSymbol;
		
		String[][] testBoard=_board.getBoard();
		
		Point focusPoint;
		
		for(int y=board.getBoardRows()-1 ; y>=1 ; y--){

			
			for(int x=y ; x<=board.getBoardCols()-y ; x++){
				
				tempPoints=0;
				
				focusPoint=new Point(x,y);
				
				String playerAtPosition="";
				
				if(playerSymbol.equals(testBoard[y][x])){
					goodBad=1;
					playerAtPosition=playerSymbol;
				}
				else if(opposingPlayerSymbol.equals(testBoard[y][x])){
					goodBad=-1;
					playerAtPosition=opposingPlayerSymbol;
				}
				else{
					goodBad=0;
				}
				
				
				for(int i=0; i<ladderPatterns.length; i++){
					int ladderPoints=0;
				
					outerloop2:
					//All ladders have 5 points
					for(int j=0; j<POINTS_FOR_LADDER; j++){
						int xPos=focusPoint.x+ ladderPatterns[i][j].x;
						int yPos=focusPoint.y+ ladderPatterns[i][j].y;
						
	
						
						try{
							checkLadderPointSymbol=testBoard[yPos][xPos];
						}
						
						//Can check ladders that go off the board
						catch(Exception e){
							checkLadderPointSymbol="";
						}
						
						if(checkLadderPointSymbol.equals(playerAtPosition)){
							ladderPoints=ladderPoints+1;
							
							
						}
						else if(!checkLadderPointSymbol.equalsIgnoreCase("-")){

							//tempPoints=0;
							ladderPoints=20;
							break outerloop2;	
						}
					}
					
					if(ladderPoints==1){
						tempPoints=+1;
					}
					else if(ladderPoints==2){
						tempPoints=+2;
					}
					else if(ladderPoints==3){
						tempPoints=+5;
					}
					else if(ladderPoints==4){
						tempPoints=+1000;
					}
					else if(ladderPoints==5){
						if(goodBad==-1){
							tempPoints+=90000000;
						}
						else if(goodBad==1){
							tempPoints=tempPoints+90000000;
						}
						if(isNeutralized(ladderPatterns[i], "Left", getOpposingPlayerSymbol(playerAtPosition),_board)||
								isNeutralized(ladderPatterns[i], "Right", getOpposingPlayerSymbol(playerAtPosition),_board)){
							if(goodBad==-1){
								tempPoints-=90000000;
							}
							else if(goodBad==1){
								tempPoints=-90000000;
							}
						}
						
					}			
					ladderPoints=0;
				
				if(goodBad==1){
					goodPoints=goodPoints+tempPoints;
				}
				else if(goodBad==-1){
					badPoints=badPoints+tempPoints;
				}
				tempPoints=0;
			}
				
		}	
		}
		return (goodPoints-badPoints);
	}
	public static String getOpposingPlayerSymbol(String symbol){
		if(symbol=="*"){
			return "O";
		}
		else{
			return "*";
		}
	}
}