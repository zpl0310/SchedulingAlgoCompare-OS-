import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class roundAuto {
    public static void main(String[] args) throws IOException {
        int q;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter Time Quantum:");
        q = scan.nextInt();

    	//Change file path here
    	String filename = "C:/Users/phoebe/Desktop/testl.txt";

        Scanner sc = new Scanner(new File(filename));
        int totalWaiting = 0; //total waiting time
        int totalTurnAround = 0; //total turn around time
        int numSwitch = 0; //number of context switch
        int sum = 0; //sum of remaining burst time
        int totalResponseTime = 0;

        List<Integer> lines = new ArrayList<>();
        while (sc.hasNextLine()) {
            lines.add(Integer.parseInt(sc.nextLine()));
        }
        int n = lines.size();
        int[] bt = new int[n]; //burst time
        int[] wt = new int[n]; //waiting time
        int[] tat = new int[n]; //turn around time
        int[] origBurst = new int[n]; //original burst time
        int[] rt = new int[n]; //response time

        for (int i = 0; i < n; i++) {
            bt[i] = lines.get(i);
            origBurst[i] = bt[i];
            sum += bt[i];
        }

        for (int i = 0; i < n; i++) {
            wt[i] = 0;
        }

        int timer = 0;
        while (sum != 0) {
            for (int i = 0; i < n; i++) {           
                boolean hasOthers = false;
                if (bt[i] > q) {
                    bt[i] -= q;
                    sum -= q;
                    timer += q;
                    if ((origBurst[i]-bt[i])<=q){
                    	rt[i] = timer-q;
                    }  
                    //System.out.println(timer);
                    for (int j = 0; j < n; j++) {
                        if ((j != i) && (bt[j] != 0)) {
                            wt[j] += q;
                            hasOthers = true;                          
                        }
                    }
                } else if (bt[i] > 0 && bt[i] <= q) {
                	timer += bt[i];
                    //System.out.println(timer);
                    for (int j = 0; j < n; j++) {
                        if ((j != i) && (bt[j] != 0)) {
                            wt[j] += bt[i];
                            hasOthers = true;                           
                        }
                    }
                    sum -= bt[i];
                    int sub = timer - bt[i];
                    bt[i] = 0;
                    if ((origBurst[i]-bt[i])<=q){
                    	rt[i] = sub;
                    }
                    
  
                } else {
                    //do nothing
                }
                if (hasOthers) {
                    numSwitch++;
                }
            }
        }


        for (int i = 0; i < n; i++) {
            tat[i] = wt[i] + origBurst[i];
        }
        System.out.println("Process\t\tBT\tWT\tTAT\tRT");
        for (int i = 0; i < n; i++) {
            System.out.println("Process" + (i + 1) + "\t" + origBurst[i] + "\t" + wt[i] + "\t" + tat[i] + "\t" + rt[i]);
        }

        for (int i = 0; i < n; i++) {
            totalWaiting += wt[i];
        }
        for (int i = 0; i < n; i++) {
            totalTurnAround += tat[i];
        }
        for (int i=0;i<n;i++){
        	totalResponseTime += rt[i];
        }

        double avgWaiting = (double) totalWaiting / n;
        double avgTurnAround = (double) totalTurnAround / n;
        double avgResponse = (double) totalResponseTime /n;
        System.out.printf("Average Waiting Time = %.3f ms\n", avgWaiting);
        System.out.printf("Average Turnover Time = %.3f ms\n", avgTurnAround);
        System.out.printf("Average Response Time = %.3f ms\n", avgResponse);
        System.out.println("Number of Context Switch = " + numSwitch);
        
    }
}