/**
 * @author Ben Grass
 * @version 1.0
 * 10/2/14
 * This represents a percolation object. Wooot woooot.
 */
import java.util.Scanner;
public class Percolate {
    private final int N;
    private final int MAX_VAL;
    private WeightedQuickUnionUF percolate;
    private WeightedQuickUnionUF percolate2;
    private boolean percolateChart[][];
    
    
    
    /**
     * Initializes a Percolate object where all sites are false.
     * @param N Size of number of sites in a line, Percolate object has N^2 sites.
     */
    public Percolate(int N) {
        this.MAX_VAL = N*N + 2;
        this.N = N;
        percolate = new WeightedQuickUnionUF(MAX_VAL);
        percolateChart = new boolean[N+2][N+2];
        percolate2 = new WeightedQuickUnionUF(MAX_VAL-1);
        
        
        for(int i = 0; i < N; i++) {
            percolate.union(i,MAX_VAL-2);
            percolate.union(i,MAX_VAL-2);
        }
        
        for(int i = N*(N-1); i < N*N; i++){
            percolate.union(i,MAX_VAL-1);
        }
    }
    
    
    /**
     * Opens at site at (i,j)
     * @param i Row of the site to open
     * @param j Column of the site to open
     */
    public void open(int i, int j) {
        percolateChart[i+1][j+1] = true;
        if(percolateChart[i][j+1] && percolateChart[i+1][j+1]){
            percolate.union(((i-1)*N +(j)),((i*N)+j));
            percolate2.union(((i-1)*N +(j)),((i*N)+j));
            
        } 
        if(percolateChart[i+1][j] && percolateChart[i+1][j+1]){
            percolate.union(((i)*N +(j-1)),((i*N)+j));
            percolate2.union(((i)*N +(j-1)),((i*N)+j));
        }
        if(percolateChart[i+1][j+2] && percolateChart[i+1][j+1]) {
            percolate.union(((i)*N +(j+1)),((i*N)+j));
            percolate2.union(((i)*N +(j+1)),((i*N)+j));
        }
        if(percolateChart[i+2][j+1] && percolateChart[i+1][j+1]) {
            percolate.union(((i+1)*N +(j)),((i*N)+j));
            percolate2.union(((i+1)*N +(j)),((i*N)+j));
        }
        
    }
    
    /**
     * Checks for an open site at (i,j)
     * @param i Row for site to check if open
     * @param j Column for site to check if open
     * @return Is site at (i,j) open?
     */
    public boolean isOpen(int i, int j) {
        return percolateChart[i+1][j+1];
    }
    
    /**
     * Checks for an full site at (i,j)
     * @param i Row for site to check if open
     * @param j Column for site to check if open
     * @return Is site at (i,j) full?
     */
    public boolean isFull(int i, int j) {
        boolean full = percolate2.connected(MAX_VAL-2,((i*N)+j))&&percolateChart[i+1][j+2];
        return full;
    }
    /**
     * Checks if the object percolates
     * @return Does system percolate?
     */    
    public boolean percolates() {
        return percolate.connected(MAX_VAL-2,MAX_VAL-1);
    }
    
    /**
     * Creates a string of the board
     * @return String of the percolation object (board)
     */
    public String toString() {
        String retString = "";
        for(int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if(percolateChart[i][j]) retString += "*";
                else retString += "-";
            }
            retString += "\n";
        }
        return retString;
    }
    
    public static void main(String[] args) {
        Percolate n = new Percolate(6);
        Scanner scanny = new Scanner(System.in);
        while(!n.percolates()) {
            System.out.println(n);
            System.out.println("Enter a loc (x val space y val): ");
            int x = scanny.nextInt();
            int y = scanny.nextInt();
            n.open(x,y);
            System.out.println();
            
        }
        System.out.println(n + "IT PERCOLATES!");
        System.out.println(n.isFull(5,0));
    }

    
}