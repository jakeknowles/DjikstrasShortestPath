/**
 * A representation of a Vertex.
 * in the graph.
 * @author Jake Knowles
 * @author Wayvern McCormick
 * @author Teacher
 */
public class Vertex implements Comparable<Vertex> { //decided to use comparable because its much easier to compare.
	
	// label attached to this vertex
	private String label;
	
	//Added for cost access
	private final int cost;
	
	//Vertex path added
	private final Vertex path;

	/**
	 * Construct a new vertex
	 * 
	 * @param label
	 *            the label attached to this vertex
	 */
	public Vertex(String label) {
		if (label == null)
			throw new IllegalArgumentException("null");
		this.label = label;
		this.cost = Integer.MAX_VALUE; //Dijkstras algorithm rules
		this.path = null;
	}
	
	/**
	 * Construct a new vertex.
	 * 
	 * @param v v is a vertex.
	 */
	public Vertex(Vertex v) {
		this(v.label, v.cost, v.path);
	}
	
	/**
	 * Construct a new vertex, another constructor for different parameters
	 * 
	 * @param label the label attached to this vertex
	 * @param cost the cost for each vertex
	 * @param path the path from a vertex to another vertex
	 */
	public Vertex(String label, int cost, Vertex path) {
		if (label == null) 
			throw new IllegalArgumentException("null");
		this.label = label;
		this.cost = cost;
		this.path = path;
	}
		
	/** Get's path for each vertex. (ADDED for help)
	 * 
	 * @return path path is the path from vertex to vertex.
	 */
	public Vertex getPath() {
		return path;
	}
	
	/** Get's cost for each vertex. (ADDED for help)
	 * 
	 * @return cost cost is the cost of each edge from vertex to vertex.
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Get a vertex label
	 * 
	 * @return the label attached to this vertex
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * A string representation of this object
	 * 
	 * @return the label attached to this vertex
	 */
	public String toString() {
		return label;
	}

	// auto-generated: hashes on label
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	// auto-generated: compares labels
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Vertex other = (Vertex) obj;
		if (label == null) {
			return other.label == null;
		} else {
			return label.equals(other.label);
		}
	}
	
	/** Compares vertexs my cost.
	 * 
	 * @param v v is a vertex
	 * @return -1 if cost > v, 0 if cost = v, and 1 if v > cost
	 */
	public int compareTo(Vertex v) {
		return this.cost - v.cost;
	}

}
