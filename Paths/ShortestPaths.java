import java.util.ArrayList;

//
// SHORTESTPATHS.JAVA
// Compute shortest paths in a graph.
//
// Your constructor should compute the actual shortest paths and
// maintain all the information needed to reconstruct them.  The
// returnPath() function should use this information to return the
// appropriate path of edge ID's from the start to the given end.
//
// Note that the start and end ID's should be mapped to vertices using
// the graph's get() function.
//
// You can ignore the input and startTime arguments to the constructor
// unless you are doing the extra credit.
//
class ShortestPaths {
	int startId;
	Multigraph G;
	Edge[] edges;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ShortestPaths(Multigraph G, int startId, 
			Input input, int startTime) 
	{
		this.G=G;
		this.startId= startId;
		int max= Integer.MAX_VALUE;
		PriorityQueue<Vertex> Q= new PriorityQueue<Vertex>();
		edges= new Edge[G.nVertices()];
		Handle[] handles= new Handle[G.nVertices()];

		//initialize the queue with max values
		for ( int i=0; i< G.nVertices(); i++){
			Vertex u= G.get(i);
			u.parent=null;
			handles[i]=Q.insert(max, u);
		}
		Handle first= handles[startId];
		Q.decreaseKey(first, startTime);
		//run while queue is not empty
		while ( !Q.isEmpty()){
			int newkey=Q.min();
			Vertex v= Q.extractMin();
			if ( v.id()==max){
				return;
			}
			Vertex.EdgeIterator I= v.adj();
			//run while there are still edges for v.
			while (I.hasNext()){

				Edge newEdge= I.next();
				int newWeight= newEdge.weight();
				Vertex w=newEdge.to();
				Handle wH= handles[w.id()];
				Handle vH= handles[v.id()];
				int out=input.flights[newEdge.id()].startTime;
				int in=vH.key;
				int layover;
				if (startTime!=0){
					 layover=45+ (out-in-45+ 2880)%1440;	
				}
				else{
					layover=0;
				}
				if (Q.decreaseKey(wH, newkey+newWeight+ layover)){
					edges[w.id()]=newEdge;
				}
			}
		}
	}





	//
	// returnPath()
	// Return an array containing a list of edge ID's forming
	// a shortest path from the start vertex to the specified
	// end vertex.
	//
	public int [] returnPath(int endId) 
	{
		if (endId==(startId)){
			return new int[0];
		}
		ArrayList<Integer> paths= new ArrayList<Integer>();
		int temp=endId;
		Edge eg= edges[temp];

		for (int i=0; i<G.nVertices(); i++){
			while(temp!=startId){
				//add elements in reverse order, most recent to oldest
				paths.add(eg.id());
				Vertex parent=eg.from();
				eg=edges[parent.id()];
				temp=parent.id();
			}
		}
		//reverse the order of the elements for returning
		int[] returnable= new int [paths.size()];
		for ( int j=0; j<paths.size(); j++){
			returnable[paths.size()-j-1]=paths.get(j);
		}
		return returnable;
	}
}


