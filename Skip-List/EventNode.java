import java.util.ArrayList;


public class EventNode {
	ArrayList<Event> element;
	 ArrayList<EventNode> next;
	 int height;
	
	public EventNode(int height, ArrayList<Event> element) {
		this.next= new ArrayList<EventNode>(height);
		this.element= element;
		this.height=height;
	}
	

}

