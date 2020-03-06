package ub.cse.algo;

import java.util.*;

public class Solution {

    private HashMap<Integer, ArrayList<Integer>> graph;

    public Solution(HashMap<Integer, ArrayList<Integer>> graph) {
        this.graph = graph;
    }

    public ArrayList<Integer> findCycle() {
        int[] Explored = new int[this.graph.size()];
        int[] Discovered = new int[this.graph.size()];
        Stack<Integer> stack = new Stack<>();

        int startNode = 0;
        stack.push(startNode);
        Discovered[startNode] = 1;

        HashMap<Integer, ArrayList<Integer>> pathCollection = new HashMap<>();
        pathCollection.put(startNode, new ArrayList<>());

        ArrayList<Integer> path = new ArrayList<>();
        ArrayList<Integer> cycle = new ArrayList<>();

        int length = 0; // the length of the first cycle found


        // Use DFS algorithm to traverse the graph
        while (!stack.isEmpty()) {

            int node = stack.pop();

            ArrayList<Integer> previousPath = pathCollection.get(node);  // get the previous path
            int index = previousPath.size();
            previousPath.add(node);

            boolean cycleFound = false;

            if (Explored[node] == 0) {
                Explored[node] = 1;

                // for each of the neighbor nodes, check whether it is explored
                for (int adjNode : this.graph.get(node)) {
                    boolean explored = Explored[adjNode] == 1;
                    boolean discovered = Discovered[adjNode] == 1;


                    // if not explored and not discovered, mark it as discovered
                    if (!explored && !discovered) {
                        stack.push(adjNode);
                        Discovered[adjNode] = 1;
                        ArrayList<Integer> currentPath = new ArrayList<>(previousPath);
                        pathCollection.put(adjNode, currentPath); // Store the current path to the next node
                    }

                    // If discovered, update the path
                    if (!explored && discovered) {
                        ArrayList<Integer> currentPath = new ArrayList<>(previousPath);
                        pathCollection.put(adjNode, currentPath); // Store the current path to next node
                    }

                    // Check if it is explored just before the current node (as which could consider a 'parent' node)
                    // (Note: a cycle in an undirected graph needs to have at least three nodes in it.)
                    if (explored && previousPath.get(index - 1) != adjNode) {
                        int adjIndex = previousPath.indexOf(adjNode);
                        length = index - adjIndex; // the length of the cycle;
                        path = pathCollection.get(node);
                        cycleFound = true;
                        break;
                    }

                }
            }

            if (cycleFound) {
                break;
            }
        }

        int startIndex = path.size() - 1 - length;  // the length of the path minus the length of the cycle

        for (int i = startIndex; i < path.size(); i++) {
            cycle.add(path.get(i));
        }

        return cycle;
    }
}
