//
// EVENTLIST.JAVA
// Skeleton code for your EventList collection type.
//
import java.util.*;

class EventList {

	Random randseq;
	int maxHeight;
	int formerHeight;
	EventNode head;
	EventNode tail;
	Event headEvent;
	Event tailEvent;
	ArrayList <Event> headElems;
	ArrayList <Event> tailElems;

	////////////////////////////////////////////////////////////////////
	// Here's a suitable geometric random number generator for choosing
	// pillar heights.  We use Java's ability to generate random booleans
	// to simulate coin flips.
	////////////////////////////////////////////////////////////////////

	int randomHeight()
	{
		int v = 1;
		while (randseq.nextBoolean()) { v++; }
		return v;
	}


	//
	// Constructor
	//
	public EventList(){
		headElems= new ArrayList<Event>(1);
		tailElems=new ArrayList<Event>(1);
		headEvent=new Event (-10000,null);
		tailEvent=new Event (10000,null);
		headElems.add(headEvent);
		tailElems.add(tailEvent);
		maxHeight=1;
		formerHeight=1;
		head= new EventNode(maxHeight,headElems);
		tail= new EventNode (maxHeight,tailElems);
		randseq = new Random(58243); // You may seed the PRNG however you like.
			head.next.add(tail);
	
	}
public void doubleSize(int r){
	while (maxHeight<=r){
		formerHeight=maxHeight;
		maxHeight=maxHeight*2;
			ArrayList <Event> temp=new ArrayList<Event>();
			Event e= new Event(-10000, null);
			temp.add(e);
		 EventNode newHead= new EventNode(maxHeight,temp);
		 for ( int i=0; i<formerHeight; i++){
			 newHead.next.add(head.next.get(i));
		 }
		 for ( int j=formerHeight; j<maxHeight; j++){
			 newHead.next.add(tail);
		 }
		 head=newHead;
	}
}

	// Add an Event to the list.
	//
	public void insert(Event e)
	{
		//check at beginning to see if a new node will require an increase in size.
		int r= randomHeight();
		if(r>=maxHeight){
			doubleSize(r);
		}
		EventNode[] lefts= new EventNode[maxHeight];
		EventNode[] rights= new EventNode[maxHeight];
		int l= maxHeight-1;
		EventNode x= head;
		EventNode y;
		while (l>=0){
			y=x.next.get(l);
			if(y.element.get(0).year==(e.year)){
				y.element.add(e);
				return;
			}
			else if (y.element.get(0).year<(e.year)){
				x=y;
			}
			else if (y.element.get(0).year>(e.year)){
				lefts[l]=x;
				rights[l]=y;
				l--;
			}	
		}
		ArrayList<Event> element= new ArrayList<Event>();
		element.add(e);
		EventNode newInsert= new EventNode(r, element);
		for(int i=0; i<r; i++){
			lefts[i].next.set(i, newInsert);
		}
		for ( int j=0; j<r; j++){
			newInsert.next.add(rights[j]);
		}
	}


	public void remove(int year)
	{
		int l= head.height-1;
		EventNode x= head;
		EventNode y;
		while (l>=0){
			y=x.next.get(l);
			if(y.element.get(0).year==(year)){
				x.next.set(l, y.next.get(l));
				l--;
			}
			else if (y.element.get(0).year<(year)){
				x=y;
			}
			else if (y.element.get(0).year>(year)){
				l--;
			}	
		}
	}


	//
	// Find all events with greatest year <= input year
	//


	public Event [] findMostRecent(int year)
	{
		ArrayList <Event> recents= new ArrayList <Event>();
		int l= head.height-1;
		EventNode x= head;
		EventNode y;
		while (l>=0){
			y=x.next.get(l);
			if(y.element.get(0).year==(year)){
				for ( int n=0; n<y.element.size(); n++){
					recents.add(y.element.get(n));
				}
				return (Event[]) recents.toArray(new Event[recents.size()]);
			}
			if (y.element.get(0).year < (year)){
				x=y;
			}
			if (y.element.get(0).year > (year)){
				l--;
			}
		}
		for (int i=0; i<x.element.size(); i++){
			if (x.element.get(i).description!=null){
				recents.add(x.element.get(i));
			}
		}
		//check to see if anything was found
		if (!recents.isEmpty()){
			return (Event[]) recents.toArray(new Event[recents.size()]);
		}
		else{
			return null;
		}
	}

	//helper method for findRange that returns the element just smaller than the range.
	//because findRange starts with next
	public EventNode findSingleRecent(int year){
		int l= head.height-1;
		EventNode x= head;
		EventNode y;
		EventNode prev = null;

		while (l>=0){
			y=x.next.get(l);
			if(y.element.get(0).year==(year)){
				return x;
			}
			if (y.element.get(0).year < (year)){
				x=y;
			}
			if (y.element.get(0).year > (year)){
				l--;
			}
		}
		prev=x;
		return prev;
	}


	//
	// Find all Events within the specific range of years (inclusive).
	//
	public Event [] findRange(int first, int last)
	{
		ArrayList<Event> range= new ArrayList<Event>();
		EventNode starter=findSingleRecent(first);
		int m= 0;
		EventNode f= starter;
		EventNode g;
		while (m>=0){
			g=f.next.get(m);
			if (g.element.get(0).year<(first)){
				f=g;
			}
			else if ((g.element.get(0).year>=first) && (g.element.get(0).year<=last)){

				for(int i=0; i<g.element.size(); i++){
					if (!range.contains(g.element.get(i))){
						range.add(g.element.get(i));
					}
				}
				f=g;
			}
			else if (g.element.get(0).year>(last)){
				m--;

			}
		}
		if(!range.isEmpty()){
			return (Event[]) range.toArray(new Event[range.size()]);
		}
		else{
			return null;
		}
	}
}

