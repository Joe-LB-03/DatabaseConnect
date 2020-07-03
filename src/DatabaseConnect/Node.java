package DatabaseConnect;
import java.util.ArrayList;
import java.util.List;

//So according to the java documentation, I can use this Comparable statement to make it so I can sort and compare nodes more easily. So I used it!
public class Node implements Comparable<Node> 
{
 
    private String name;
    private List<Edge> adjacenciesList;
    //I also found this List library that apparently makes it easier to create adjacency lists. Will try it out. (UPDATE: It works great!)
    private boolean visited;
    private Node predecessor;
    private double distance = Double.MAX_VALUE;
    //This sets the distance to a temporary value of INFINITY so that the edges set to this distance don't interfere with the rest of the queue. (Again, this one had to be found out the hard way.)
 
    //The rest of this is just functions for returning and setting data that the algorithm needs to read/write to.
    public Node(String name) 
    {
        this.name = name;
        this.adjacenciesList = new ArrayList<>();
    }
 
    public void addNeighbour(Edge edge) 
    {
        this.adjacenciesList.add(edge);
    }
 
    public String getName() 
    {
        return name;
    }
 
    public void setName(String name) 
    {
	    this.name = name;
    }
 
    public List<Edge> getAdjacenciesList() 
    {
	    return adjacenciesList;
    }
 
    public void setAdjacenciesList(List<Edge> adjacenciesList) 
    {
    	this.adjacenciesList = adjacenciesList;
    }
 
    public boolean isVisited() 
    {
	    return visited;
    }
 
    public void setVisited(boolean visited) 
    {
        this.visited = visited;
    }
 
    public Node getPredecessor() 
    {
	    return predecessor;
    }
 
    public void setPredecessor(Node predecessor) 
    {
	    this.predecessor = predecessor;
    }
 
    public double getDistance() 
    {
	    return distance;
    }
 
    public void setDistance(double distance) 
    {
	    this.distance = distance;
    }

    //Ok I lied there's a bit more. Here is the overrides for the toString and compareTo functions that I had to add to fix some errors. 
    //Not sure why these errors happend without these, but this fixes them so I don't really mind. They were probably something to do with preexisting functions in java messing things up.
    @Override
    public String toString() 
    {
	    return this.name;
    }
 
    @Override
    public int compareTo(Node othernode) 
    {
    	return Double.compare(this.distance, othernode.getDistance());
    }
}