package zadanie2;

import java.util.Random;

public class Host extends Thread{
	private String message;
	private Cable c;
	private int id;
	boolean collision;
	int waitingTime=2;
	boolean success=true;
	boolean wait=false;
	
	public Host(String signal, Cable c, int id){
		this.message=signal;
		this.c=c;
		this.id=id;
		collision=true;
	}
	
	public String getMessage(){
		return message;
	}
	
	static Random rand = new Random();
	public void run() {
		while(true){
			if(collision&&!wait){
	        	try {
	        		if(success)
						waitingTime=2;
					Thread.sleep((rand.nextInt(waitingTime)+1)*300*Cable.signals.length);
					if(!success)
						waitingTime*=2;
					collision=false;
					continue;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	        }
			wait=false;
			if(!collision){
				for(int i=0; i<Cable.signals.length; i++){
					if(Cable.signals[id]==null){
						c.startSignal(id, message);
			        	try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					else if(!Cable.signals[id].getMessage().equals("@")){
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						collision=false;
						wait=true;
						break;
					}
					else{
						success=false;
						collision=true;
						break;
					}
	        	}
	        }
			if(!wait){
				if(!collision)
					success=true;
				collision=true;
			}
		}
	}
}