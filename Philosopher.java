import java.util.Random;

class Philosopher implements Runnable {
	// each philosopher is a thread
	// random a number to represent how long a philosopher thinks before hungry
	private Random numGenerator = new Random();
	private int id;
	private DiningPhilosophersUsingMonitor monitor;
	
	public Philosopher (int id, DiningPhilosophersUsingMonitor monitor) {
		this.id = id;
		this.monitor = monitor;
	}
	
	public void run() {
		try {
			while (true) {
				think();
				monitor.pickUpChopsticks(id);
				eat();
				monitor.putDownChopsticks(id);
			}
		} catch (InterruptedException e) {
			System.out.println("Philosopher " + id + " was interrupted.\n");			
		}
	}

	private void think() throws InterruptedException {
		System.out.println("Philosopher " + id + " is thinking.\n");
		System.out.flush();
		Thread.sleep (numGenerator.nextInt(10));
	}
	
	private void eat() throws InterruptedException {
		Thread.sleep (numGenerator.nextInt(10));
	}
	

}
