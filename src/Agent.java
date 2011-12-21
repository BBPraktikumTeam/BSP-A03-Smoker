/**
 * 
 */

/**
 * @author Kai Bielenberg (kai.bielenberg@haw-hamburg.de)
 * 
 */
public class Agent extends Thread {
    int papers;
    int tobacco;
    int matches;
    Table table;

    private final int DIFFERENT_CASES = 3;
    private final int PUT_AMOUNT = 1;

    private Agent(Table table, int paper, int tobacco, int matches) {
        this.papers = paper;
        this.tobacco = tobacco;
        this.matches = matches;
        this.table = table;
    }

    public static Agent valueOf(Table table, int paper, int tobacco, int matches) {
        return new Agent(table, paper, tobacco, matches);
    }

    public boolean hasAllIngredients() {
       return !(matches <= 0 || tobacco <= 0 || papers <= 0);
    }
    
    public void run() {
        int choose = chooseCase(DIFFERENT_CASES);
        
        while(!this.isInterrupted()){
        switch (choose) {
        case 1: if (this.papers - PUT_AMOUNT >= 0 && this.tobacco - PUT_AMOUNT >= 0) {
//            System.out.println("Put Paper and Tobacco");
        table.put(PUT_AMOUNT, PUT_AMOUNT, 0);
     
        this.tobacco -= PUT_AMOUNT;
        this.papers -= PUT_AMOUNT;
        }
            break;          
        case 2:
            if (this.matches - PUT_AMOUNT >= 0 && this.tobacco - PUT_AMOUNT >= 0) {
//                System.out.println("Put Matches and Tobacco");
                table.put(0, PUT_AMOUNT, PUT_AMOUNT);
                this.matches -= PUT_AMOUNT;
                this.tobacco -= PUT_AMOUNT;
                }
            break;
         
        case 3:
            if (this.matches - PUT_AMOUNT >= 0 && this.papers - PUT_AMOUNT >= 0) {
//                System.out.println("Put Paper and Matches");
                table.put(PUT_AMOUNT, 0, PUT_AMOUNT);
                this.matches -= PUT_AMOUNT;
                this.papers -= PUT_AMOUNT;
                }
            break;
            
        default:
            throw new RuntimeException("Wrong Case");
        }
        synchronized(table) {
            table.notifyAll();
            try {
                table.wait();
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
        choose = chooseCase(DIFFERENT_CASES);

        }
    }

    public int chooseCase(int pMaximum) {
        return (int) ((Math.random() * pMaximum) + 1);
    }
}
