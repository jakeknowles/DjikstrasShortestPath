import java.util.*;

/**
 * A representation of a graph. Assumes that we do not have negative cost edges
 * in the graph.
 * 
 * @author Jake Knowles
 * @author Wayvern McCormick
 * @author Teacher
 */

public class MyGraph implements Graph {
	
	/** Set of edges. **/
	private final Set<Edge> setOfEdges;
	
	/** Map for connection between vertexs. **/
	private final Map<Vertex, Set<Vertex>> vertexVertex;
	
	/** Map for connection between vertexs to edges. **/
	private final Map<Vertex, Set<Edge>> vertexEdge;

	/**
	 * Creates a MyGraph object with the given collection of vertices and the
	 * given collection of edges.
	 * 
	 * @param v
	 *            a collection of the vertices in this graph
	 * @param e
	 *            a collection of the edges in this graph
	 */
	public MyGraph(Collection<Vertex> v, Collection<Edge> e) {

		setOfEdges = new HashSet<>();
		vertexVertex = new HashMap<>();
		vertexEdge = new HashMap<>();
		
		//Check all edges and vertices in collection
		for (Edge edge : e) {
			
			//Edge cannot be negative, for test files / any illegal edges
			if (0 > edge.getWeight()) {
				throw new IllegalArgumentException(edge.toString() + " INVALID EDGE!");
			
			} else {
				//Assign root vertex and end vertex
				Vertex rootVertex = edge.getSource();
				Vertex endVertex = edge.getDestination();
				
				if (v.contains(rootVertex) && v.contains(endVertex)) {
					
					if (!vertexEdge.containsKey(rootVertex)) {
						vertexEdge.put(rootVertex, new HashSet<Edge>());
					}
					if (!vertexVertex.containsKey(rootVertex)) {
						vertexVertex.put(rootVertex, new HashSet<Vertex>());
					}
					
					vertexEdge.get(rootVertex).add(new Edge(edge));
					vertexVertex.get(rootVertex).add(new Vertex(endVertex));
					
					if (setOfEdges.contains(edge)) {
						// For test files that contain the same edges, throw exception
						throw new IllegalArgumentException("DUPLICATE EDGES!");
					}
					
					setOfEdges.add(new Edge(edge));
				
				} else {
					// For test files that contain vertices that aren't valid in the passed in txt
					throw new IllegalArgumentException("Invalid Vertices!");
				}
			}
		}
	}


	/**
	 * Return the collection of vertices of this graph
	 * 
	 * @return the vertices as a collection (which is anything iterable)
	 */
	@Override
	public Collection<Vertex> vertices() {
		return vertexVertex.keySet(); //Returns the key values in the map.
	}

	/**
	 * Return the collection of edges of this graph
	 * 
	 * @return the edges as a collection (which is anything iterable)
	 */
	@Override
	public Collection<Edge> edges() {
		return setOfEdges; //Returns collection (set) of edges
	}

	/**
	 * Return a collection of vertices adjacent to a given vertex v. i.e., the
	 * set of all vertices w where edges v -> w exist in the graph. Return an
	 * empty collection if there are no adjacent vertices.
	 * 
	 * @param v
	 *            one of the vertices in the graph
	 * @return an iterable collection of vertices adjacent to v in the graph
	 * @throws IllegalArgumentException
	 *             if v does not exist.
	 */
	@Override
	public Collection<Vertex> adjacentVertices(Vertex v) {
		if (!vertices().contains(v)) {
			throw new IllegalArgumentException("Vertex doesnt exist!");
		}
		return vertexVertex.get(v); //May need to check if vertices in collection are valid
	}

	/**
	 * Test whether vertex b is adjacent to vertex a (i.e. a -> b) in a directed
	 * graph. Assumes that we do not have negative cost edges in the graph.
	 * 
	 * @param a
	 *            one vertex
	 * @param b
	 *            another vertex
	 * @return cost of edge if there is a directed edge from a to b in the
	 *         graph, return -1 otherwise.
	 * @throws IllegalArgumentException
	 *             if a or b do not exist.
	 */
	public int edgeCost(Vertex a, Vertex b) {
		
		//Checks to see if passed in vertex's exist
		if (!vertices().contains(a) || (!vertices().contains(b))) {
			throw new IllegalArgumentException("VERTEX DOESN'T EXIST!");
		}
		
		//Set containing vertex a edges
		Set<Edge> vertexASetOfEdges = vertexEdge.get(a);
		int cost = -1; //initialize to negative, 0 will cause errors on certain situations
		
		//Iterate through all edges in set
		for (Edge e : vertexASetOfEdges) {
			
			// if edges destination is the same vertex as b, then assign cost new value of weight
			if (e.getDestination().equals(b)) {
				cost = e.getWeight();
				break; // exit after find weight
			}
		}
		return cost;
	}

	/**
	 * Returns the shortest path from a to b in the graph, or null if there is
	 * no such path. Assumes all edge weights are nonnegative. Uses Dijkstra's
	 * algorithm.
	 * 
	 * @param a
	 *            the starting vertex
	 * @param b
	 *            the destination vertex
	 * @return a Path where the vertices indicate the path from a to b in order
	 *         and contains a (first) and b (last) and the cost is the cost of
	 *         the path. Returns null if b is not reachable from a.
	 * @throws IllegalArgumentException
	 *             if a or b does not exist.
	 */
	public Path shortestPath(Vertex a, Vertex b) {

		//Checks to see if passed in vertex's exist
		if (!vertices().contains(a) || (!vertices().contains(b))) {
			throw new IllegalArgumentException("VERTEX DOESN'T EXIST!");
		}
		
		//Priority Queue implementation
		PriorityQueue<Vertex> myPQ = new PriorityQueue<>();
		
		//Just like in class with djikstras algorithm, need vertices, isKnown, map of the neighbors
		Map<Vertex, Set<Edge>> neighbors = vertexEdge; 
		List<Vertex> vertices = new ArrayList<>();
		Set<Vertex> isKnown = new HashSet<>();
		
		if (a.equals(b)) {
			List<Vertex> result = new ArrayList<>();
			result.add(new Vertex(a));
			return new Path(result, 0);
		}
		
		//add all vertices to list
		for (Vertex v : vertices()) {
			vertices.add(new Vertex(v));
		}
		
		//Add start vertex to Priority Queue
		Vertex root = new Vertex(a.getLabel(), 0 , null);
		myPQ.add(root);
		vertices.set(vertices.indexOf(a), root);
		Vertex last = null;
		
		//Visit each vertex, only once! important
		for (int i = 0; i < vertices().size(); i++) {
			Vertex current = myPQ.remove();
			
			if (!isKnown.contains(current)) { //Mark as "known" and get neighboring edges
				isKnown.add(current);
				Set<Edge> neighbor = neighbors.get(current);
				
				for (Edge e : neighbor) { //look at all neighboring edges, assign costs
					Vertex destination = e.getDestination();
					int newCost = e.getWeight() + current.getCost();
					int endCost = vertices.get(vertices.indexOf(destination)).getCost();
					
					//if smaller cost is found, change cost
					if (newCost < endCost) {
						Vertex swapVertex = new Vertex(destination.getLabel(), newCost, current);
						if (swapVertex.equals(b)) {
							last = swapVertex;
						}
						vertices.set(vertices.indexOf(destination), swapVertex);
						myPQ.add(swapVertex);
					}
				}	
			}
		}
		
		if (last == null) {
			return null;
		}
		
		List<Vertex> result = new LinkedList<>(); //store result
		int cost = vertices.get(vertices.indexOf(last)).getCost();
		result.add(last);
		
		//Find shortest path
		while(last.getPath() != null) {
			result.add(new Vertex(last.getPath()));
			last = last.getPath();
		}
		
		Collections.reverse(result); //Cool Collections function I researched http://www.tutorialspoint.com/java/util/collections_reverse.htm
		return new Path(result, cost);
	}
}