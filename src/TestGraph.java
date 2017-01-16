import java.io.*;
import java.util.*;

/**
	 * Driver program that reads in a graph and prompts user for shortests paths in the graph.
	 * (Intentionally without comments.  Read through the code to understand what it does.)
	 */
	public class TestGraph {

		public static void main(String[] args) {
			if(args.length != 2) {
				System.err.println("USAGE: java Paths <vertex_file> <edge_file>");
				System.exit(1);
			}

			MyGraph g = readGraph(args[0],args[1]);

			@SuppressWarnings("resource")
			Scanner console = new Scanner(System.in);
			Collection<Vertex> v = g.vertices();
	        Collection<Edge> e = g.edges();
			System.out.println("Vertices are "+v);
			System.out.println("Edges are "+e);
			
			//Test neighbors
			System.out.println("Vertex Test: type 'N' to do cost test ");
			String next = console.nextLine();
			
			while(!next.equals("N")) {
				Vertex ver = new Vertex(next);
				Collection<Vertex> neigh = g.adjacentVertices(ver);
				System.out.println("Neighboring vertices to " + ver + " are: " + neigh);
				System.out.println("Another Vertex? ");
				next = console.nextLine();
			}
			
			//Test for neighboring vertices cost, and data structure behavior
			System.out.println("Test vertex 1? (type 'exit' to skip) ");
			String v1 = console.nextLine();
			System.out.println("Test vertex 2? (type 'exit' to skip) ");
			String v2 = console.nextLine();
			
			//If next is typed
			while(!v1.equals("N") && !v2.equals("N")) {
				Vertex V1 = new Vertex(v1);
				Vertex V2 = new Vertex(v2);
				int cost = g.edgeCost(V1, V2);
				System.out.println("Edge cost between " + V1 + " to " + V2 + " is: " + cost);
				System.out.println("Test vertex 1? (type 'exit' to skip) ");
				v1 = console.nextLine();
				System.out.println("Test vertex 2? (type 'exit' to skip) ");
				v2 = console.nextLine();
			}
		}

		public static MyGraph readGraph(String f1, String f2) {
			Scanner s = null;
			try {
				s = new Scanner(new File(f1));
			} catch(FileNotFoundException e1) {
				System.err.println("FILE NOT FOUND: "+f1);
				System.exit(2);
			}

			Collection<Vertex> v = new ArrayList<Vertex>();
			while(s.hasNext())
				v.add(new Vertex(s.next()));

			try {
				s = new Scanner(new File(f2));
			} catch(FileNotFoundException e1) {
				System.err.println("FILE NOT FOUND: "+f2);
				System.exit(2);
			}

			Collection<Edge> e = new ArrayList<Edge>();
			while(s.hasNext()) {
				try {
					Vertex a = new Vertex(s.next());
					Vertex b = new Vertex(s.next());
					int w = s.nextInt();
					e.add(new Edge(a,b,w));
				} catch (NoSuchElementException e2) {
					System.err.println("EDGE FILE FORMAT INCORRECT");
					System.exit(3);
				}
			}

			return new MyGraph(v,e);
		}
	}
