
public class DiningPhilosophersUsingMonitor {
	// three state of a philosopher
	private enum State {THINKING, HUNGRY, EATING};	
	// The state of each philosopher
	private State[] philosopherState;
	
	public DiningPhilosophersUsingMonitor (int numPhilosophers) {
		philosopherState = new State[numPhilosophers];
		for (int i = 0; i < philosopherState.length; i++) {
			philosopherState[i] = State.THINKING;
		}
	}
	
	//a philosopher tries to pick up the chopsticks on his left and right, if the neigbours are eating, then wait 
	public synchronized void pickUpChopsticks(int philosopherId) throws InterruptedException {
		philosopherState[philosopherId] = State.HUNGRY;
		System.out.println("Philosopher " + philosopherId + " is hungry.\n");
		System.out.flush();
		while (someNeighborIsEating(philosopherId)) {
			wait();
		}
		philosopherState[philosopherId] = State.EATING;
		System.out.println("Philosopher " + philosopherId + " is eating.\n");
		System.out.flush();
	}

	//helper function to check whether the neighbors are eating
	private boolean someNeighborIsEating(int philosopherId) {
		if (philosopherState[(philosopherId + 1) % philosopherState.length] == State.EATING){
			return true;
		}
		if (philosopherState[(philosopherId + philosopherState.length - 1) % philosopherState.length] == State.EATING){
			return true;
		}
		return false;
	}

	//put down chopsticks after eating, and tell the other philosophers
	public synchronized void putDownChopsticks(int philosopherId) {
		philosopherState[philosopherId] = State.THINKING;
		notifyAll();
	}


	public static void main (String[] args) {
		//assume the number of philosophers is 5
		Philosopher[] philosophers = new Philosopher[5];
		DiningPhilosophersUsingMonitor monitor = new DiningPhilosophersUsingMonitor(5);
		
		for (int i = 0; i < 5; i++) {
			philosophers[i] = new Philosopher(i, monitor);
			new Thread(philosophers[i]).start();
		}
	}

}