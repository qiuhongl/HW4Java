package ub.cse.algo;

import java.util.*;

public class Solution {

    private HashMap<Integer, ArrayList<Integer>> graph;

    public Solution(HashMap<Integer, ArrayList<Integer>> graph) {
        this.graph = graph;
    }

    public ArrayList<Integer> findCycle() {

        // A set to remember the nodes visited
        Set<Integer> visited = new HashSet<>();

//        // A list of all the connected components of the graph
//        ArrayList<ArrayList<Integer>> allCCs = new ArrayList<>();

        // Output
        ArrayList<Integer> cycle = new ArrayList<>();

        int size = this.graph.size();

        int[] Explored = new int[size];
        int[] Discovered = new int[size];
        int[] parent = new int[size];
        Arrays.fill(parent, -1);

        // For each graph, run DFS algorithm to find a cycle
        while (visited.size() != size) {

            int startNode = -1;

            // take an arbitrary node to start
            for (int i = 0; i < size; i++) {
                if (Discovered[i] == 0) {
                    startNode = i;
                    break;
                }
            }

            if (startNode == -1) {
                break;
            }
            
            visited.add(startNode);
            Discovered[startNode] = 1;

//            // generate a connected component of the graph
//            ArrayList<Integer> graph = new ArrayList<>();
//            graph.add(startNode);

            Stack<Integer> stack = new Stack<>();
            stack.push(startNode);

            int lastNode = -1;
            int firstNode = -1;

            boolean cycleFound = false;

            // Use DFS algorithm to traverse the graph
            while (!stack.isEmpty()) {

                int node = stack.pop();

                if (Explored[node] == 0) {
                    Explored[node] = 1;

                    // for each of the neighbor nodes, check whether it is explored
                    for (Integer childNode : this.graph.get(node)) {
                        boolean explored = Explored[childNode] == 1;
                        boolean discovered = Discovered[childNode] == 1;
                        int parentNode = parent[node];

                        // if not explored and not discovered, mark it as discovered
                        if (!explored && !discovered) {
                            stack.push(childNode);
                            Discovered[childNode] = 1;
                            // the parent of the childNode is what it takes to get to the childNode
                            parent[childNode] = node;

//                            // connecting the CC
//                            graph.add(childNode);
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

//                // add the CCs to the CC list.
//                allCCs.add(graph);
            }

            while (lastNode != firstNode) {
                cycle.add(lastNode);
                lastNode = parent[lastNode];
            }

            if (firstNode != -1) {
                cycle.add(firstNode);
            }

            if (cycleFound) {
                break;
            }
        }

        return cycle;
    }
}
