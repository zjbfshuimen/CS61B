package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Solver {

    private MinPQ<SearchNode> pq;
    private List<WorldState> solution;
    private int totMoves;

    public Solver(WorldState inoitial) {
        pq = new MinPQ<>();
        pq.insert(new SearchNode(inoitial, 0, null));
        while (true) {
            SearchNode node = pq.delMin();
            if (node.curr.isGoal()) {
                getAnswer(node);
                break;
            } else {
                for (WorldState neighbour: node.curr.neighbors()) {
                    if (node.prev == null || !neighbour.equals(node.prev.curr)) {
                        pq.insert(new SearchNode(neighbour, node.distSoFar + 1, node));

                    }
                }
            }
        }
    }
    private void getAnswer(SearchNode node) {
        totMoves = node.distSoFar;
        solution = new ArrayList<>();
        SearchNode p = node;
        while (p != null) {
            solution.add(p.curr);
            p = p.prev;
        }
    }


    public int moves() {
        return totMoves;
    }

    public Iterable<WorldState> solution() {
        return solution;
    }
    private Deque<SearchNode> worlds;
    private class SearchNode implements Comparable<SearchNode> {
        WorldState curr;
        int distSoFar;
        SearchNode prev;

        public SearchNode(WorldState curr, int distance, SearchNode prev) {
            this.curr = curr;
            distSoFar = distance;
            this.prev = prev;
        }
        public int getDistance() {
            return distSoFar;
        }
        public SearchNode getPrevious() {
            return prev;
        }
        public WorldState getState() {
            return curr;
        }
        @Override
        public int compareTo(SearchNode o) {
            return this.distSoFar + this.curr.estimatedDistanceToGoal()
                    - o.distSoFar - o.curr.estimatedDistanceToGoal();
        }



    }

}
