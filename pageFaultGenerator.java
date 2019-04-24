
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class pageFaultGenerator {

	public static void main(String[] args) throws IOException {

		Scanner sc = new Scanner(System.in);
		int i1=0,highbit=128;
		//input a maximum frame size
		System.out.println("enter maximum frame size");
		int framenumber = sc.nextInt();	
		sc.close();
		 File file = new File("C:/Users/phoebe/Desktop/try.txt");
		 Scanner scan = new Scanner(file); 
		 int [] page = new int[10000];
		 int [][] frame_faults = new int[framenumber][2];
		 //size of memory is 8.
		 int [][] memory = new int[8][2];

	     
	      @SuppressWarnings("resource")
	    //random 1000 frame size and write it in txt file
		PrintWriter writer = new PrintWriter("C:/Users/phoebe/Desktop/try.txt");
		     for(int i=0;i<10000;i++){
		    	int k=(int)(Math.ceil(Math.random()*framenumber));
		    writer.println(k);
		     }
		     writer.close();
		     
		 	while(scan.hasNextLine()){
		    	 String s = scan.nextLine().trim();
		    	 page[i1] = Integer.parseInt(s);
		    	 i1++;
		    }
		     scan.close();
		   //check the page in memory reference. Increase its value if found, else reduce
		     for(int i :page){
		    	 boolean stillnotfound = false,dobreak =false;
		   //page found
		    	 while(!stillnotfound){
		    	 for(int []m:memory){
		    		 if(m[0]==i){
		    			 m[1]=m[1]|highbit;
		    			 stillnotfound = false;
		    			 dobreak=true;
		    			 break;
		    		 }
		    	 }
		    	 stillnotfound=true;
		     }
		   // page not found
		   if(stillnotfound == true && !dobreak){
		   // shift all the memory values by one position right
		    	 for(int []m:memory){
		    		 m[1]= m[1]>>1;
		    	 }
		   // find the least value and replace with the value in memory
		    	 int lowest = 0;
		    	 for(int i11 = 0;i11<7;i11++){
		    		 if(memory[i11][1] > memory[i11+1][1]){
		    			 lowest = i11+1;
		    		 }
		    	 }
		   //  assigning the value in to memory
		    	 memory[lowest][0]=i;
		    	 memory[lowest][1]=128;
		   // count the page fault
		    	 frame_faults[i-1][0]=i;
		    	 frame_faults[i-1][1]=frame_faults[i-1][1]+1;
		    	 }
		    
		   
		  }
		     
		     System.out.println("memory");
		     for(int []h:memory){
		    	 System.out.println(h[0]+"    "+h[1]);
		     } 
		     
		     System.out.println("page faults");
		     for(int []h:frame_faults){
		    	 if(h[0] >0){
		    	 System.out.println(h[0]+" : "+h[1]);
		    	 }
		     }    
	}
}
