import java.util.Random;
//by wefree on 12-01/2018
//Simulation the gossip by  average numbers.
//editor:IntelliJ IDEA 2018.2
//Supplement:Assuming that a certain percentage of nodes have been infected,
//           and,the percentage is INT_RATE.

public class gossip {
    //the total nodes number:
    private static final int SIZE = 5000;
    // the decline rate of interest value:
    private static final double k = 1.1;
    // Infection rate in initial state:
    private static final double INT_RATE = 0.1;
    // upper limit of random number:
    private static final int numberMax=10;
    // Threshold to determine whether two numbers are equal:
    private static final double Min_Error=0.001;

    //the number of nodes:
    private double nodes[] = new double[SIZE];
    //the value of nodes' interest to talk:
    private double pros[] = new double[SIZE];
    //the frequency of gossip/talks:
    private int talksNumber=0;

    //Number of infected nodes, here is 500:
    private int noticed = (int) (SIZE * INT_RATE);
    //Propagation rounds:
    private int round = 0;
    //Assignment to infected nodes in the initial state:
    private double numberCenter=numberMax/2.0;
    private double aveBegin;
    private double aveEnd;

    //Initialize the state of nodes.
    //Assuming that a certain percentage of nodes have been infected.
    public gossip() {
        //Randomize all nodes according to the given range.
        for (int i = 0; i < SIZE; i++){
            nodes[i] = new Random().nextInt(numberMax);
        }
        //Randomly select the fixed ratio of nodes,
        // and initialize them to infected state.
        for (int i = 0; i < noticed; i++) {
            int index = new Random().nextInt(SIZE);
            nodes[index] = numberCenter;
            pros[index] = 1.0;
        }
        aveBegin=getAve();
    }

    public void run() {
        System.out.println("The begin state of nodes:");
        outputCurrentState();
        System.out.println("=========================");
        boolean flag = true;
        while (flag) {
            System.out.print(++round + " ");
            flag = false;
            for (int i = 0; i < nodes.length; i++) {
                //If this node is not isolated,
                if (pros[i] != 0) {
                    //A probability to be isolated.
                    //The smaller the pros, the greater the probability of isolation.
                    if (new Random().nextDouble() > pros[i]) {
                        pros[i] = 0;
                    } else {
                        // If, according to probability, the node is not isolated,
                        // it continues to propagate down.
                        flag = true;
                        talk(i);
                    }
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
        System.out.println("TalksNumber:" + talksNumber);
        System.out.println("AveBegin:"+aveBegin);
        System.out.println("AveEnd:"+aveEnd);
    }

    //print the state of nodes.
    private  void outputCurrentState(){
        for(int i=0;i<SIZE;i++){
            System.out.println(i+"{"+nodes[i]+","+pros[i]+"}");
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
        //If the node is not infected,
        // it will infect the node and change its interest value to 1,
        // increasing the number of talks by 1.
        if (Math.abs(nodes[target]-nodes[current])>Min_Error) {
            double ave=(nodes[current]+nodes[target])/2.0;
            nodes[current]=nodes[target]=ave;
            pros[target] = 1.0;
            talksNumber++;
        } else {
            //If the node has been infected,
            // the interest value of the original node decreases according to the parameter k.
            pros[current] /= k;
        }
    }

    public static void main(String[] args) {
        new gossip().run();
    }
}

