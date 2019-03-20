package zadanie2;

public class Signal {
	String message;
	String movement;
	public Signal(String message, String movement){
		this.message = message;
		this.movement=movement;
	}
	public void setMessage(String s){
		this.message=s;
	}
	public String getMessage(){
		return message;
	}
	public String getMovement(){
		return movement;
	}
}