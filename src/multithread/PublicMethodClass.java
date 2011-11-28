package multithread;

public class PublicMethodClass {
	private static PublicMethodClass instance = new PublicMethodClass();
	
	public synchronized void printParam(String param1){
		
		System.out.println("current mills:" + System.currentTimeMillis()+ "-" + param1);
	}

	public static PublicMethodClass getInstance() {
		return instance;
	}

}
