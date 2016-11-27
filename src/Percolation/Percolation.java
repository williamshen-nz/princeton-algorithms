package Percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    /* Fixing Backwash problem with two WQUUF objects:
       ==============================================
       simpleGraph - does not contain virtualBottom but contains virtualTop
       fullGraph - contains both virtualTop and virtualBottom

       Idea taken from: http://www.sigmainfy.com/blog/avoid-backwash-in-percolation.html
    */
    private WeightedQuickUnionUF simpleGraph;
    private WeightedQuickUnionUF fullGraph;

    private boolean[][] open;
    private int virtualTop;
    private int virtualBottom;
    private int size;

    /**
     * Constructor for a Percolation system
     * which we assume is a n by n grid
     *
     * @param n size of grid required
     */
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        // We initialise the instance variables
        this.open = new boolean[n][n];
        this.virtualTop = 0;
        this.virtualBottom = n * n + 1;
        this.simpleGraph = new WeightedQuickUnionUF(n * n + 1);
        this.fullGraph = new WeightedQuickUnionUF(n * n + 2);
        this.size = n;
    }

    /**
     * Open a site if it is not open already
     *
     * @param row   the row which site is at, 1 to n
     * @param col   the col which site is at, 1 to n
     */
    public void open(int row, int col) {
        if (!onGrid(row, col))
            throw new IndexOutOfBoundsException();

        // We break execution if site is already open
        if (isOpen(row, col)) return;

        // Set the open value for this site to true, minus 1 because array
        open[row - 1][col - 1] = true;

        int currIndex = getIndex(row, col);
        
        /* We now check around the site for any open sites */
        if (onGrid(row - 1, col) && isOpen(row - 1, col)) { // top
            simpleGraph.union(currIndex, getIndex(row - 1, col));
            fullGraph.union(currIndex, getIndex(row - 1, col));
        }

        if (onGrid(row, col + 1) && isOpen(row, col + 1)) { // right
            simpleGraph.union(currIndex, getIndex(row, col + 1));
            fullGraph.union(currIndex, getIndex(row, col + 1));
        }

        if (onGrid(row + 1, col) && isOpen(row + 1, col)) { // bottom
            simpleGraph.union(currIndex, getIndex(row + 1, col));
            fullGraph.union(currIndex, getIndex(row + 1, col));
        }

        if (onGrid(row, col - 1) && isOpen(row, col - 1)) { // left
            simpleGraph.union(currIndex, getIndex(row, col - 1));
            fullGraph.union(currIndex, getIndex(row, col - 1));
        }

        /* Check if site is on top row or bottom row,
           and hence link this site to virtual site(s) */
        if (row == 1) {  // top
            simpleGraph.union(currIndex, virtualTop);
            fullGraph.union(currIndex, virtualTop);
        }

        if (row == size)    // bottom
            fullGraph.union(currIndex, virtualBottom);
    }

    /**
     * Checks whether a site is open or not using the 2d array
     *
     * @return  whether site is open
     */
    public boolean isOpen(int row, int col) {
        if (!onGrid(row, col))
            throw new IndexOutOfBoundsException();

        return open[row - 1][col - 1];
    }

    /**
     * A full site is an open site that can be connected to an open site in the top
     * row via a chain of neighboring (left, right, up, down) open sites.
     *
     * @return      whether the site is full or not
     */
    public boolean isFull(int row, int col) {
        if (!onGrid(row, col))
            throw new IndexOutOfBoundsException();

        // We only check simple graph - we do not want backwash problems
        return simpleGraph.connected(virtualTop, getIndex(row, col));
    }

    /**
     * The system percolates if there is a full site in the bottom row
     *
     * @return  whether the system percolates or not
     */
    public boolean percolates() {
        return fullGraph.connected(virtualTop, virtualBottom);
    }

    /**
     * Checks whether the given site is on the grid or not, i.e. within the bounds
     * @return      whether the indicated site is on the grid
     */
    private boolean onGrid(int row, int col) {
        // We don't account for things starting at 0 here
        return row > 0 && row <= size && col > 0 && col <= size;
    }

    /**
     * Get index of a site relative to the fullGraph (i.e. quick union)
     */
    private int getIndex(int row, int col) {
        // Recall 0 index is used for virtual top site
        return (row - 1) * size + col;
    }
}