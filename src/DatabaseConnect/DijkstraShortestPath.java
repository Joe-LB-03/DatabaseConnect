package DatabaseConnect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
 
public class DijkstraShortestPath
{
    public void computeShortestPaths(final Node sourcenode) 
    {
        sourcenode.setDistance(0);
        final PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>();
        priorityQueue.add(sourcenode);
        sourcenode.setVisited(true);
        while (!priorityQueue.isEmpty())
        {
            // Find the minimum distance node using priority queue
            final Node actualnode = priorityQueue.poll();
            // I found you can iterate over a collection using a colon like this. I know we
            // didn't learn this but I found it in the documentation!
            for (final Edge edge : actualnode.getAdjacenciesList()) 
            {
                final Node n = edge.getTargetnode();
                if (!n.isVisited()) 
                {
                    // Increment the total distance if needed.
                    final double newDistance = actualnode.getDistance() + edge.getWeight();
                    // Now if the distance found is more efficent, that becomes the new "smallest"
                    // distance, and the node being worked on is removed from the Priority Queue,
                    // then added to the back.
                    if (newDistance < n.getDistance()) 
                    {
                        priorityQueue.remove(n);
                        n.setDistance(newDistance);
                        n.setPredecessor(actualnode);
                        priorityQueue.add(n);
                    }
                }
            }
            actualnode.setVisited(true);
        }
    }
    // Now, all the calculated shortest routes can be put into a list with this
    // function. Keeps things tidy!
    public List<Node> getShortestPathTo(final Node targetnode)
    {
        final List<Node> path = new ArrayList<>();
        for(Node node=targetnode; node!=null; node=node.getPredecessor())
        {   
	        path.add(node);
        }
        Collections.reverse(path);
        return path;
    }
}
