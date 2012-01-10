

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


    private Table(int papers, int tobacco, int matches) {
        this.papers = papers;
        this.tobacco = tobacco;
        this.matches = matches;
    }

    public static Table valueOf(int papers, int tobacco, int matches) {
        return new Table(papers, tobacco, matches);
    }

    public synchronized void put(int papers, int tobacco, int matches) {

        this.tobacco += tobacco;
        this.papers += papers;
        this.matches += matches;
        System.out.println("Agent put: " + papers + " Papers, " +tobacco+ " tobacco and " + matches + " matches" );
        System.out.println("On Table after put: " + this.papers + " Papers, " + this.tobacco + " tobacco and " + this.matches + " matches" );
       
    }


    public synchronized void remove(int papers, int tobacco, int matches) {
        
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

   

}
