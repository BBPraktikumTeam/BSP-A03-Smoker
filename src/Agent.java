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

//    public void placePapers() {
//        if (this.papers - PUT_AMOUNT >= 0) {
//            this.papers -= PUT_AMOUNT;
//            table.placePapers(PUT_AMOUNT);
//        } else {
//            throw new RuntimeException("Out of Paper");
//        }
//    }
//
//    public void placeTobacco() {
//        if (this.tobacco - PUT_AMOUNT >= 0) {
//            this.tobacco -= PUT_AMOUNT;
//            table.placeTobacco(PUT_AMOUNT);
//        } else {
//            throw new RuntimeException("Out of Tobacco");
//        }
//    }
//
//    public void placeMatches() {
//        if (this.matches - PUT_AMOUNT >= 0) {
//            this.matches -= PUT_AMOUNT;
//            table.placeMatches(PUT_AMOUNT);
//        } else {
//            throw new RuntimeException("Out of Matches");
//        }
//    }

    public boolean hasAllIngredients() {
       return !(matches <= 0 || tobacco <= 0 || papers <= 0);
    }
    
    public void run() {
        int choose = chooseCase(DIFFERENT_CASES);
        while(hasAllIngredients()) {
        switch (choose) {
        case 1: if (this.papers - PUT_AMOUNT >= 0 && this.tobacco - PUT_AMOUNT >= 0) {
//            System.out.println("Put Paper and Tobacco");
        table.put(PUT_AMOUNT, PUT_AMOUNT, 0);
        this.tobacco -= PUT_AMOUNT;
        this.papers -= PUT_AMOUNT;
        }
//            placePapers();
//            placeTobacco();
            break;
          

        case 2:
            if (this.matches - PUT_AMOUNT >= 0 && this.tobacco - PUT_AMOUNT >= 0) {
//                System.out.println("Put Matches and Tobacco");
                table.put(0, PUT_AMOUNT, PUT_AMOUNT);
                this.matches -= PUT_AMOUNT;
                this.tobacco -= PUT_AMOUNT;
                }
//            placeMatches();
//            placeTobacco();
            break;
         
        case 3:
            if (this.matches - PUT_AMOUNT >= 0 && this.papers - PUT_AMOUNT >= 0) {
//                System.out.println("Put Paper and Matches");
                table.put(PUT_AMOUNT, 0, PUT_AMOUNT);
                this.matches -= PUT_AMOUNT;
                this.papers -= PUT_AMOUNT;
                }
//            placeMatches();
//            placePapers();
            break;
            
        default:
            throw new RuntimeException("Wrong Case");
        }
        choose = chooseCase(DIFFERENT_CASES);
//        System.out.println("Agent: I have " + papers + " papers, " + tobacco + " tobacco and " + matches + " matches left");
        }
    }

    public int chooseCase(int pMaximum) {
        return (int) ((Math.random() * pMaximum) + 1);
    }
}
