import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class round {
    public static void main(String[] args) throws IOException {
    	//Change time quantum here!
    	int q = 5; 
    	
    	//Change file path here!
    	String filename = "C:/Users/phoebe/Desktop/test.txt";
    	
        Scanner sc = new Scanner(new File(filename));       
        int totalWaiting = 0; //total waiting time
        int totalTurnAround = 0; //total turn around time
        int numSwitch = 0; //number of context switch
        int sum = 0; //total time

        //System.out.println("Enter Number of Process:");
        //int n = scan.nextInt();

        //System.out.println("Enter Time Quantum:");
        //q = scan.nextInt();
        
        q=5;
        List<Integer> lines = new ArrayList<Integer>();	
        while (sc.hasNextLine()){
        	lines.add(Integer.parseInt(sc.nextLine()));
        }
        int n = lines.size();
        int[] bt = new int[n]; //burst time
        int[] wt = new int[n]; //waiting time
        int[] tat = new int[n]; //turn around time
        int[] origBurst = new int[n]; //original burst time

        for (int i = 0; i < n; i++) {
            bt[i] = lines.get(i);
        }

        for (int i = 0; i < n; i++) {
            origBurst[i] = bt[i];
        }
        for (int i = 0; i < n; i++) {
            wt[i] = 0;
        }

        do {
            for (int i = 0; i < n; i++) {
                boolean hasOthers = false;
                if (bt[i] > q) {
                    bt[i] -= q;
                    for (int j = 0; j < n; j++) {
                        if ((j != i) && (bt[j] != 0)) {
                            wt[j] += q;
                            hasOthers = true;
                        }
                    }
                } else if (bt[i] > 0 && bt[i] <= q) {
                    for (int j = 0; j < n; j++) {
                        if ((j != i) && (bt[j] != 0)) {
                            wt[j] += bt[i];
                            hasOthers = true;
                        }
                    }
                    bt[i] = 0;
                } else {
                    //do nothing
                }
                if (hasOthers) {
                    numSwitch++;
                }
            }
            sum = 0;
            for (int i = 0; i < n; i++) {
                sum = sum + bt[i];
            }
        }
        while (sum != 0);

        for (int i = 0; i < n; i++) {
            tat[i] = wt[i] + origBurst[i];
        }
        System.out.println("Process\t\tBT\tWT\tTAT");
        for (int i = 0; i < n; i++) {
            System.out.println("Process" + (i + 1) + "\t" + origBurst[i] + "\t" + wt[i] + "\t" + tat[i]);
        }

        for (int i = 0; i < n; i++) {
            totalWaiting += wt[i];
        }
        for (int i = 0; i < n; i++) {
            totalTurnAround += tat[i];
        }
        float avgWaiting = totalWaiting / n;
        float avgTurnAround = totalTurnAround / n;
        System.out.printf("Average Waiting Time = %.3f ms\n", avgWaiting);
        System.out.printf("Average Turnover Time = %.3f ms\n", avgTurnAround);
        System.out.println("Number of Context Switch = " + numSwitch);
    }
}