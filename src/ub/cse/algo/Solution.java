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
        int[] parent = new int[this.graph.size()];
        Arrays.fill(parent, -1);
        Stack<Integer> stack = new Stack<>();

        int startNode = 0;
        stack.push(startNode);
        Discovered[startNode] = 1;

        ArrayList<Integer> cycle = new ArrayList<>();

        int lastNode = -1;
        int firstNode = -1;

        // Use DFS algorithm to traverse the graph
        while (!stack.isEmpty()) {

            int node = stack.pop();

            boolean cycleFound = false;

            if (Explored[node] == 0) {
                Explored[node] = 1;

                // for each of the neighbor nodes, check whether it is explored
                for (int childNode : this.graph.get(node)) {
                    boolean explored = Explored[childNode] == 1;
                    boolean discovered = Discovered[childNode] == 1;
                    int parentNode = parent[node];

                    // if not explored and not discovered, mark it as discovered
                    if (!explored && !discovered) {
                        stack.push(childNode);
                        Discovered[childNode] = 1;
                        parent[childNode] = node;
                    }

                    // If discovered, update the path
                    if (!explored && discovered) {
                        parent[childNode] = node;
                    }

                    // Check if it is explored just before the current node (as which could consider a 'parent' node)
                    // (Note: a cycle in an undirected graph needs to have at least three nodes in it.)
                    if (explored && parentNode != -1) {
                        if (parentNode != childNode) {
                            firstNode = childNode;
                            lastNode = node;
                            cycleFound = true;
                            break;
                        }

                    }

                }
            }

            if (cycleFound) {
                break;
            }
        }

        while (lastNode != firstNode) {
            cycle.add(lastNode);
            lastNode = parent[lastNode];
        }

        if (firstNode != -1) {
            cycle.add(firstNode);
        }

        return cycle;
    }
}
