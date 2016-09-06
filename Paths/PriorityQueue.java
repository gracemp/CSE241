import java.util.ArrayList;

//
// PRIORITYQUEUE.JAVA
// A priority queue class supporting sundry operations needed for
// Dijkstra's algorithm.
//

class PriorityQueue<T> {
	ArrayList<Handle<T>> elems;

	// constructor
	//
	public PriorityQueue()
	{
		elems= new ArrayList<Handle<T>>();
	}

	// Return true iff the queue is empty.
	//
	public boolean isEmpty()
	{
		return elems.isEmpty();
	}

	// Insert a pair (key, value) into the queue, and return
	// a Handle to this pair so that we can find it later in
	// constant time.
	//
	Handle<T> insert(int key, T value)
	{

		Handle<T> newH= new Handle<T>(key, value);
		elems.add(newH);
		newH.position=elems.indexOf(newH);
		//repair the heap to fit heap properties
		bubbleup (newH);
		return newH;
	}
//helper method to repair the heap after insert()
	//checks if new element is smaller than the parent, if so, bubbles it up
	public void bubbleup(Handle <T> i){
		if (elems.size()>1){
			Handle <T>parent=elems.get(i.position/2);
			if (parent.key>i.key){
				swap(i,parent);
				bubbleup(i);
			}
		}
	}
	//helper method to repair the heap after extractMin()
	// after the swap in extractMin(), this method compares the new root with the children to restore the heap property
	public void bubbledown(Handle <T> i){
		if (elems.size()>1){
			Handle <T> left= new Handle <T>(1000000000, null);
			Handle<T> right= new Handle<T>(1000000000, null);
			Handle <T>  smallest = i;
			if (i.position*2<=elems.size()-1){
				left=elems.get(i.position*2);
			}
			if ((i.position*2+1)<=elems.size()-1){
				right= elems.get( i.position*2 +1);
			}
			if (right.key<smallest.key && right.value!=null){
				smallest=right;
			}
			if ( left.key<smallest.key && right.value !=null){
				smallest=left;
			}
			if (smallest.key != i.key){
				//swap with smallest child
				swap(i, smallest);
				bubbledown(i);
			}
		}
	}


	public void swap(Handle<T> i, Handle<T> j){
		int pos1= elems.indexOf(i);
		int pos2=elems.indexOf(j);
		elems.set(pos1, j);
		j.position=pos1;
		elems.set(pos2, i);
		i.position=pos2;
	}
	//Return the smallest key in the queue.
	//
	public int min()
	{
		return elems.get(0).key;

	}

	// Extract the (key, value) pair associated with the smallest
	// key in the queue and return its "value" object.
	//
	public T extractMin()
	{
		Handle <T> H = elems.get(0);
		if (elems.size()>1){
			swap(elems.get(0), elems.get( elems.size()-1));
			elems.remove(elems.size()-1);
			bubbledown(elems.get(0));
		}
		else{
			elems.remove(elems.get(0));
		}
		H.position=-1;
		return H.value;
	}
	// Look at the (key, value) pair referenced by Handle h.
	// If that pair is no longer in the queue, or its key
	// is <= newkey, do nothing and return false.  Otherwise,
	// replace "key" by "newkey", fixup the queue, and return
	// true.
	//
	public boolean decreaseKey(Handle<T> h, int newkey)
	{
		boolean here=false;
		for ( int i=0; i<elems.size(); i++){
			if (elems.get(i).key==h.key){
				here=true;
			}
		}
		//if element isnt there, or the new key is greater than its old one
		if (!here || h.key<=newkey){
			return false;
		}
		else{
			h.key=newkey;
			bubbleup(h);
			return true;
		}
	}



	// Get the key of the (key, value) pair associated with a 
	// given Handle. (This result is undefined if the handle no longer
	// refers to a pair in the queue.)
	//
	public int handleGetKey(Handle<T> h)
	{
		return h.key;
	}

	// Get the value object of the (key, value) pair associated with a 
	// given Handle. (This result is undefined if the handle no longer
	// refers to a pair in the queue.)
	//
	public T handleGetValue(Handle<T> h)
	{
		return h.value;
	}

	// Print every element of the queue in the order in which it appears
	// in the implementation (i.e. the array representing the heap).
	public String toString()
	{
		return elems.toString();
	}
}
