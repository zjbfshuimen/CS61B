package hw4.puzzle;

import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

public class Solver {

    private MinPQ<SearchNode> pq;
    private List<WorldState> Solutions;
    private int totMoves;

    public Solver(WorldState inoitial) {
        pq = new MinPQ<>();
        pq.insert(new SearchNode(inoitial, 0, null));
        while (true) {
            SearchNode node = pq.delMin();
            if (node.curr.isGoal()){
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
        Solutions = new ArrayList<>();
        SearchNode p = node;
        while (p != null) {
            Solutions.add(p.curr);
            p = p.prev;
        }
    }


    public int moves() {
        return totMoves;
    }

    public Iterable<WorldState> solution() {
        return Solutions;
    }
    private Deque<SearchNode> worlds;
    private class SearchNode implements Comparable<SearchNode>{
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
