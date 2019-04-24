package Banker;

public class Bankers{
    private int[][] need,allocate,max,available;
    private int processNo,resourceNo;
    
    public Bankers(int[][] allocate, int[][] max, int[][] available){
    	this.allocate = allocate;
    	this.max = max;
    	this.available = available;
    	//get the number of processes and number of resources
    	this.processNo = allocate.length;
    	this.resourceNo = allocate[0].length;
    	//calculating need matrix
    	need = new int[processNo][resourceNo];
        for(int i=0;i<processNo;i++)
            for(int j=0;j<resourceNo;j++)  
             need[i][j]=max[i][j]-allocate[i][j];
    }
    
    //helper function to check is all resources for process i can be allocated
    private boolean check(int i){
       for(int j=0;j<resourceNo;j++) 
       if(available[0][j]<need[i][j])
          return false;
    
    return true;
    }
 
    //check if all processes can be allocated without end in a deadlock
    public void isSafe(){
       boolean done[]=new boolean[processNo];
       int j=0;
 
       while(j<processNo){  //until all process allocated
       boolean allocated=false;
       for(int i=0;i<processNo;i++)
        if(!done[i] && check(i)){  //trying to allocate
            for(int k=0;k<resourceNo;k++)
            available[0][k]=available[0][k]-need[i][k]+max[i][k];
         System.out.println("Allocated process : "+i);
         allocated=done[i]=true;
               j++;
             }
          if(!allocated) break;  //if no allocation
       }
       if(j==processNo)  //if all processes are allocated
        System.out.println("All processes are allocated");
       else
        System.out.println("Can not allocate all processes");
    }
    
    //test data from lecture slide
    public static void main(String[] args) {
       int[][] allocate = {{'0', '0', '1','0'}, {'2', '0', '0','1'}, {'0', '1', '2','0'}};
       int[][] max = {{'2', '0', '1','1'}, {'3', '0', '1','1'}, {'2', '2', '2','0'}};
       int[][] available = {{'2', '1', '0','0'}};
       Bankers test = new Bankers(allocate,max,available);
       test.isSafe();
    }
}
