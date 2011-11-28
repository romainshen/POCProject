package multithread;

public class WorkThread extends Thread {
	public PublicMethodClass pmc = PublicMethodClass.getInstance();
	public void run() {		
        pmc.printParam(this.getName());
    }


}
