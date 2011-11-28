package multithread;

public class MultiThreadTester {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WorkThread wt1 = new WorkThread();
		WorkThread wt2 = new WorkThread();
		wt1.start();
		wt2.start();

	}
}
