/**
 * 
 */

/**
 * @author Kai Bielenberg (kai.bielenberg@haw-hamburg.de)
 * 
 */
public class Smoker extends Thread {

    
    int papers;
    int tobacco;
    int matches;
    String name;
    Table table;

    private static final int REMOVE_AMOUNT = 1;
    private static final int SMOKING_DURATION = 500;
    private static final int ROLL_DURATION = 1000;
    private static final int INGREDIENTS_USED_FOR_CIGARETTE = 1;

    private Smoker(Table table, String name, int paper, int tobacco, int matches) {
        this.papers = paper;
        this.tobacco = tobacco;
        this.matches = matches;
        this.table = table;
        this.name = name;
    }

    public static Smoker valueOf(Table table,String name, int paper, int tobacco,
            int matches) {
        return new Smoker(table,name, paper, tobacco, matches);
    }


    public void run() {

        while(!this.isInterrupted()){
            if (!hasAllIngredients()) {
                try {
                    
                    getMissingIngredients();
                } catch (Exception e) {
                    System.out.println(name + " doesn't get Ingredients");
                    e.printStackTrace();
                }
            } else {
                try {
                    
                    rollCigarette();
                } catch (Exception e) {
                    System.out.println(name + " cant't roll");
                    e.printStackTrace();
                }
                try {
                  
                    smokeCigarette();
                } catch (Exception e) {
                    System.out.println(name + " cant't smoke");
                    e.printStackTrace();
                }
              
            }
        }

    }

    /**
     * @throws InterruptedException 
     * 
     */
    private void smokeCigarette() throws InterruptedException {
        try {
            Thread.sleep(SMOKING_DURATION);
        } catch (InterruptedException e) {
            this.interrupt();
        }
       
        synchronized(table){
            System.out.println(name + " finished smoking");
            table.notifyAll();
        }
    }

    /**
     * @throws InterruptedException 
     * 
     */
    private void rollCigarette() throws InterruptedException {
            decrementIngredients();
     try{
            Thread.sleep(ROLL_DURATION);
    } catch (InterruptedException e) {
        this.interrupt();
    }
    }

    /**
     * 
     */
    private void decrementIngredients() {
        this.papers -= INGREDIENTS_USED_FOR_CIGARETTE;
        this.tobacco -= INGREDIENTS_USED_FOR_CIGARETTE;
        this.matches -= INGREDIENTS_USED_FOR_CIGARETTE;
    }

    /**
     * 
     */
    private void getMissingIngredients() {
       if (tobacco == 0 && papers == 0 && table.hasTobacco() && table.hasPapers()) {
           try {
               table.remove(REMOVE_AMOUNT,REMOVE_AMOUNT,0);
               tobacco += REMOVE_AMOUNT;
               papers += REMOVE_AMOUNT;
           } catch (Exception e) {
               System.out.println(name + " doesn't get Tobacco");
               e.printStackTrace();
               
           }
       }
       else if (papers == 0 && matches == 0 && table.hasPapers() && table.hasMatches()) {
           try {
               table.remove(REMOVE_AMOUNT,0,REMOVE_AMOUNT);
               papers += REMOVE_AMOUNT;
               matches += REMOVE_AMOUNT;
           } catch (Exception e) {
               System.out.println(name + " doesn't get Papers");
               e.printStackTrace();
           }
       }
       else if (matches == 0 && tobacco == 0 && table.hasMatches() && table.hasTobacco()) {
           try {
               table.remove(0, REMOVE_AMOUNT,REMOVE_AMOUNT);
               matches += REMOVE_AMOUNT;
               tobacco += REMOVE_AMOUNT;
           } catch (Exception e) {
               System.out.println(name + " doesn't get Matches");
               e.printStackTrace();
           }
       } else {
               synchronized(table) {
                   try {
                    table.wait();
                } catch (InterruptedException e) {
                    this.interrupt();
                }
               }
       }

    }

    public boolean hasAllIngredients() {
        return !(matches <= 0 || tobacco <= 0 || papers <= 0);
    }

}
