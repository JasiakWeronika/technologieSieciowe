package zadanie2;

public class Cable{
	static Signal signals[];
	public Cable(int length){
		signals = new Signal[length];
	}
	public void startSignal(int id,String message){
			if(signals[id]==null)
				signals[id] = new Signal(message,"start");
	}
	
	public void printSignals(){
		for(int i=0; i<signals.length; i++){
			if(signals[i]==null)
				System.out.print("[ ]");
			else
				System.out.print("["+signals[i].getMessage()+"]");
		}
		System.out.print("\n");
	}
	
	public void interval(){
		boolean err=false;
		//sygna³y które przesuwaj¹ sie w prawo
		for(int i=signals.length-1; i>=0; i--){
			if(signals[i]!=null&&signals[i].getMovement().equals("right")){
				if(i+1<signals.length){
					if(!signals[i].getMessage().equals("@")){
						if(signals[i+1]==null){
							signals[i+1]=signals[i];
							signals[i]=null;
						}
						else{
							if(!err){
								if(signals[i+1].getMovement().equals("start"))
									signals[i+1].movement="right";
								signals[i+1].setMessage("@");
								err=true;
							}
						}
					}
					else{
						if(signals[i+1]==null){
							signals[i+1]=signals[i];
							signals[i]=null;
						}
						else if(signals[i+1]!=null&&!signals[i+1].getMessage().equals("@")){
							signals[i+1].setMessage("@");
							Signal temp = signals[i];
							signals[i]=signals[i+1];
							signals[i+1]=temp;
						}
						else{
								Signal temp = signals[i];
								signals[i]=signals[i+1];
								signals[i+1]=temp;
						}
						
					}
						
				}
				else
					signals[i]=null;
			}
		}
		err=false;
		//sygna³y które przesuwaj¹ siê w lewo
		for(int i=0; i<=signals.length-1; i++){
			if(signals[i]!=null&&signals[i].getMovement().equals("left")){
				if(i-1>=0){
					if(!signals[i].getMessage().equals("@")){
						if(signals[i-1]==null){
							signals[i-1]=signals[i];
							signals[i]=null;
						}
						else{
							if(!err){
								if(signals[i-1].getMovement().equals("start"))
									signals[i-1].movement="left";
								signals[i-1].setMessage("@");
								err=true;
							}
						}
					}
					else{
						if(signals[i-1]==null){
							signals[i-1]=signals[i];
							signals[i]=null;
						}
						else if(signals[i-1]!=null&&!signals[i-1].getMessage().equals("@")){
							signals[i-1].setMessage("@");
							Signal temp = signals[i];
							signals[i]=signals[i-1];
							signals[i-1]=temp;
						}
						else{
								Signal temp = signals[i];
								signals[i]=signals[i-1];
								signals[i-1]=temp;
						}
							
					}
				}
				else
					signals[i]=null;
			}
		}
		//sygna³y które zaczynaj¹ siê rozprzestrzeniaæ
		for(int i=signals.length-1; i>=0; i--){
			if(signals[i]!=null&&signals[i].getMovement().equals("start")){
				if(!signals[i].getMessage().equals("@")){
					if(i+1<signals.length){
						if(signals[i+1]==null){
							signals[i+1]= new Signal(signals[i].getMessage(),"right");
						}
					}
					if(i-1>=0){
						if(signals[i-1]==null){
							signals[i-1]= new Signal(signals[i].getMessage(),"left");
						}
					}
					signals[i]=null;
				}
				else{
					if(i+1<signals.length){
							signals[i+1]= new Signal(signals[i].getMessage(),"right");
					}
					if(i-1>=0){
							signals[i-1]= new Signal(signals[i].getMessage(),"left");
					}
					signals[i]=null;
				}
			}
		}
		
	}
}