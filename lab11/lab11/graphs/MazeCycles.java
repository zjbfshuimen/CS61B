package lab11.graphs;

import edu.princeton.cs.algs4.Stack;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s = 0;
    private Maze maze;
    private boolean findCycle = false;
    private int[] nodeTo;

    public MazeCycles(Maze m) {
        super(m);
        nodeTo = new int[m.V()];
        for (int i = 0; i < edgeTo.length; i++) {
            nodeTo[i] = edgeTo[i];
        }
        maze = m;
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    @Override
    public void solve() {
        Stack<Integer> french = new Stack<>();
        french.push(s);
        announce();

        while (!french.isEmpty() && !findCycle) {
            int toRemove = french.pop();

            if (!marked[toRemove]) {
                marked[toRemove] = true;
                announce();

                for (int w : maze.adj(toRemove)) {
                    if (!marked[w]) {
                        french.push(w);
                        distTo[w] = distTo[toRemove] + 1;
                        edgeTo[w] = toRemove;
                        announce();
                    }

                    if (marked[w] && edgeTo[toRemove] != w) {
                        findCycle = true;
                        edgeTo[w] = toRemove;
                        copyToCycle(w);
                        announce();

                        distTo = nodeTo;
                    }

                }
            }

        }

        // TODO: Your code here!
    }

    // Helper methods go here
    private void copyToCycle(int start) {
        int prev = edgeTo[start];
        nodeTo[start] = edgeTo[start];
        while (prev != start) {
            nodeTo[prev] = edgeTo[prev];
            prev = edgeTo[prev];
        }
    }
}

