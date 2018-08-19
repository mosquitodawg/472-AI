import java.awt.Point;
public class valueOfMove { 
	
	Point move;
	
	private int value;
	
	valueOfMove(Point _move, int _value){
		setMove(_move);
		setValue(_value);
	}
	
	valueOfMove(valueOfMove v){
		setMove(v.getMove());
		setValue(v.getValue());
	}
	
	valueOfMove(){
		
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	public void setMove(Point _move){
		move=_move;
	}
	
	public Point getMove(){
		return move;
	}
	

}
