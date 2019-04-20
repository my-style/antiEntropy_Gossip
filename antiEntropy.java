import java.util.Random;
//by wefree on 12-01/2018
//Simulation the anti-entropy by  average numbers.
//editor:IntelliJ IDEA 2018.2
//Supplement:Assuming that all nodes have been infected in the initial state.

public class antiEntropy {
    //the total nodes number:
    private static final int SIZE = 5000;
    // upper limit of random number:
    private static final int numberMax=10;
    // Threshold to determine whether two numbers are equal:
    private static final double Min_Error=0.001;

    //the number of nodes:
    private double nodes[] = new double[SIZE];

    //Propagation rounds:
    private int round = 0;
    private double aveBegin;
    private double aveEnd;

    //Initialize the state of nodes.
    //Assuming that all nodes have been infected
    public antiEntropy() {
        //Randomize all nodes according to the given range.
        for (int i = 0; i < SIZE; i++){
            nodes[i] = new Random().nextInt(numberMax);
        }
        aveBegin=getAve();
    }

    public void run() {
        System.out.println("The begin state of nodes:");
        outputCurrentState();
        System.out.println("=========================");
        boolean flag = true;
        while (flag) {
            flag=false;
            round++;
            for (int i = 0; i < nodes.length; i++) {
                        talk(i);
            }
            for (int i = 0; i < nodes.length; i++){
                if(Math.abs(nodes[i]-aveBegin)>Min_Error){
                    flag=true;
                }
            }
        }
        aveEnd=getAve();
        System.out.println("The end state of nodes:");
        outputCurrentState();
        System.out.println("=========================");
        output();
    }

    private void output() {
        System.out.println();
        System.out.println("NodesNumber:" + SIZE);
        System.out.println("Round:" + round);
        System.out.println("AveBegin:"+aveBegin);
        System.out.println("AveEnd:"+aveEnd);
    }

    //print the state of nodes.
    private  void outputCurrentState(){
        for(int i=0;i<SIZE;i++){
            System.out.println(i+"{"+nodes[i]+"}");
        }
    }

    //Calculate the average.
    private  double getAve(){
        double sum=0.0;
        for(int i=0;i<SIZE;i++){
            sum+=nodes[i];
        }
        return sum/(double)SIZE;
    }

    private void talk(int current) {
        //Random selection of a target to be infected.
        int target = new Random().nextInt(SIZE);
        //If the node is not infected:
        if (Math.abs(nodes[target]-nodes[current])>Min_Error) {
            double ave=(nodes[current]+nodes[target])/2.0;
            nodes[current]=nodes[target]=ave;
        } else {
        }
    }

    public static void main(String[] args) {
        new antiEntropy().run();
    }
}

