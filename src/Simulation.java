/**
 * 
 */

/**
 * @author Kai Bielenberg (kai.bielenberg@haw-hamburg.de)
 *
 */
public class Simulation {

    private static final int INITIAL_AMOUNT = Integer.MAX_VALUE;
    private static final int DURATION = 10000;
    /**
     * @param args
     */
    public static void main(String[] args) {
        Table table = Table.valueOf(0, 0, 0);
        Agent agent = Agent.valueOf(table, INITIAL_AMOUNT,INITIAL_AMOUNT,INITIAL_AMOUNT );
        Smoker smoker1 = Smoker.valueOf(table, "Tick", INITIAL_AMOUNT, 0,0);
        Smoker smoker2 = Smoker.valueOf(table, "Trick", 0, INITIAL_AMOUNT,0);
        Smoker smoker3 = Smoker.valueOf(table, "Track", 0, 0, INITIAL_AMOUNT);
        
      try {  
          smoker1.start();
          smoker2.start();
          smoker3.start();
        agent.start();

       } catch(Exception e) {
           e.printStackTrace();
       }
       try {
        Thread.sleep(DURATION);
    } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
       agent.interrupt();
       smoker1.interrupt();
       smoker2.interrupt();
       smoker3.interrupt();
       System.out.println("All smokers dead from cancer");
       
    }

}
