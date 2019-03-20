package zadanie2;

public class Main extends Thread{
	public static void main(String[] args) throws Exception {
		Cable c = new Cable(20);
		Host a = new Host("A",c,3);
		Host b = new Host("B",c,9);
		Host s = new Host("S",c,15);
		a.start();
		b.start();
		s.start();
		while(true){
			c.printSignals();
			c.interval();
			Thread.sleep(300);
		}
	}
}