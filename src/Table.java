import javax.management.monitor.Monitor;

/**
 * 
 */

/**
 * @author Kai Bielenberg (kai.bielenberg@haw-hamburg.de)
 * 
 */
public class Table {
    int papers;
    int tobacco;
    int matches;

    boolean somethingChanged = false;

    private Table(int papers, int tobacco, int matches) {
        this.papers = papers;
        this.tobacco = tobacco;
        this.matches = matches;
    }

    public static Table valueOf(int papers, int tobacco, int matches) {
        return new Table(papers, tobacco, matches);
    }

    public synchronized void put(int papers, int tobacco, int matches) {
        if (somethingChanged) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Agent cant place Tobacco");
                e.printStackTrace();
            }
        }
        somethingChanged = true;
        this.tobacco += tobacco;
        this.papers += papers;
        this.matches += matches;
        System.out.println("Agent put: " + papers + " Papers, " +tobacco+ " tobacco and " + matches + " matches" );
        System.out.println("On Table after put: " + this.papers + " Papers, " + this.tobacco + " tobacco and " + this.matches + " matches" );
        notifyAll();
    }

    // public synchronized void placeTobacco(int tobacco) {
    // if (somethingChanged) {
    // try {
    // wait();
    // } catch (InterruptedException e) {
    // System.out.println("Agent cant place Tobacco");
    // e.printStackTrace();
    // }
    // }
    // somethingChanged = true;
    // this.tobacco += tobacco;
    // System.out.println("Tobacco placed");
    // notify();
    // }
    //
    // public synchronized void placePapers(int papers) {
    // if (somethingChanged) {
    //
    // try {
    // wait();
    // } catch (InterruptedException e) {
    // System.out.println("Agent cant place Papers");
    // e.printStackTrace();
    // }
    // }
    // somethingChanged = true;
    // this.papers += papers;
    // System.out.println("Papers placed");
    // notify();
    // }
    //
    // public synchronized void placeMatches(int matches) {
    // if (somethingChanged) {
    // try {
    // wait();
    // } catch (InterruptedException e) {
    // System.out.println("Agent cant place Matches");
    // e.printStackTrace();
    // }
    // }
    // somethingChanged = true;
    // this.matches += matches;
    // System.out.println("Matches placed");
    // notify();
    // }

    public synchronized void remove(int papers, int tobacco, int matches) {
        if (!somethingChanged) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        somethingChanged = false;
        
        if (this.papers - papers >= 0 && this.tobacco - tobacco >= 0 && this.matches - matches >= 0) {
            this.papers -= papers;
            this.tobacco -= tobacco;
            this.matches -= matches;
            System.out.println("Smoker removed " + papers + " Papers, " + tobacco + " tobacco and " + matches + " matches" );
            System.out.println("On Table after remove: " + this.papers + " Papers, " + this.tobacco + " tobacco and " + this.matches + " matches" );
            
        } else {
            System.out.println("Something was missing");
        }
//        notifyAll();
        
    }
    
    public boolean hasPapers(){
        return !(papers <= 0);
    }
    
    public boolean hasTobacco(){
        return !(tobacco <= 0);
    }
    
    public boolean hasMatches(){
        return !(matches <= 0);
    }

    // public synchronized void removePapers(int tobacco) {
    // if (!somethingChanged) {
    // try {
    // wait();
    // } catch (InterruptedException e) {
    //
    // e.printStackTrace();
    // }
    // }
    // somethingChanged = false;
    // if (this.papers - papers >= 0) {
    // System.out.println("Papers removed");
    // this.papers -= papers;
    // } else {
    // System.out.println("Not enough Papers");
    // }
    // notify();
    // }
    //
    // public synchronized void removeTobacco(int tobacco) {
    // if (!somethingChanged) {
    // try {
    // wait();
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }
    // }
    // somethingChanged = false;
    // if (this.tobacco - tobacco >= 0) {
    // System.out.println("Tobacco removed");
    // this.tobacco -= tobacco;
    // } else {
    // System.out.println("Not enough Tobacco");
    // }
    // notify();
    // }
    //
    // public synchronized void removeMatches(int matches) {
    // if (!somethingChanged) {
    // try {
    // wait();
    // } catch (InterruptedException e) {
    //
    // e.printStackTrace();
    // }
    // }
    // somethingChanged = false;
    // if (this.matches - matches >= 0) {
    //
    // System.out.println("Matches removed");
    // this.matches -= matches;
    // } else {
    // System.out.println("Not enough Matches");
    // }
    // notify();
    // }

}
