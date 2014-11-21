/**
 * @author Ben Grass
 * @version 1.0
 * 11/14/13
 * This runs statistics for each percolation object. 
 */
import java.util.Random;
class PercolationStats {
    
    /**
     * This initialized and creats a new PercolationStats object.
     * @param N Creates percolation object for N by N Grid
     * @param T T independent computational experiments
     */
    public PercolationStats(int N, int T) {
        Random randy = new Random();
        double[] results = new double[T];
        
        average = 0;
        standardDeviation = 0;
        confidenceLow = 0;
        confidenceHigh = 0;
        
        for (int x = 0; x < T; x++) {
            Percolation p = new Percolation(N);
            double c = 0;
            while (!p.percolates()) {
                int n1,n2;
                boolean alreadyOpened;
                
                do { 
                    n1 = randy.nextInt(N);
                    n2 = randy.nextInt(N);
                    alreadyOpened = p.isOpen(n1,n2);
                } while (alreadyOpened);
                
                p.open(n1,n2);
                c++;
            }
            results[x] = c/(N*N);
            average += c/(N*N);
        }
        
        average /= T;
        
        double top = 0.0;
        for (double d : results)
            top += Math.pow(d-average,2);
        standardDeviation = Math.sqrt(top/(T-1));
        
        confidenceLow = average - ((1.98*standardDeviation)/Math.sqrt(T));
        confidenceHigh =  average + ((1.98*standardDeviation)/Math.sqrt(T));
    }
    
    /**
     * Returns 95% low confidence level thingy
     * @return 95% low confidence level
     */
    public double confidenceLow() {
        return confidenceLow;
    }
    /**
     * Returns 95% high confidence level thingy
     * @return 95% high confidence level
     */
    public double confidenceHigh() {
        return confidenceHigh;
    }
    
    /**
     * Returns standard deviation
     * @return standard deviation
     */
    public double standardDeviation() {
        return standardDeviation;
    }
    
    /**
     * Returns average
     * @return average
     */
    public double mean() {
        return average;
    }
    
    
    
    
    /**
     * Returns string including all data from trials
     * @return string including all data from trials
     */
    public String toString() {
        String result = "";
        result += "Mean: " + ps.mean();
        result += "\nStdDev: " + ps.stddev();
        result += "\n95% Confidence Interval: " + ps.confidenceLow() + " - " + ps.confidenceHigh();
        return result;
    }
    
    
    private double average;
    private double standardDeviation;
    private double confidenceLow;
    private double confidenceHigh;
    
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(100,100);
        System.out.println(stats);
    }
}