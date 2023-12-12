package lab11.graphs;

import edu.princeton.cs.algs4.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean find = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        Queue<Integer> french = new Queue<>();
        french.enqueue(s);
        marked[s] = true;
        announce();

        while (!french.isEmpty()) {
            int toRemove = french.dequeue();

            if (toRemove == t) {
                break;
            }

            for (int w : maze.adj(toRemove)) {
                if (marked[w]) {
                    continue;
                }
                french.enqueue(w);
                marked[w] = true;
                edgeTo[w] = toRemove;
                distTo[w] = distTo[toRemove] + 1;
                announce();
            }


        }

    }


    @Override
    public void solve() {
        bfs();
    }
}

