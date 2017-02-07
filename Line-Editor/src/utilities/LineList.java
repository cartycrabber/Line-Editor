package utilities;

import editor.Line;

public class LineList {
	
	//The first node in the list, which is also a dummy node
	private Node head;
	
	//Size of the list, good to make sure operations are in-bounds
	private int size;
	
	//Default constructor that just initializes the dummy node
	public LineList() {
		head = new Node(null);
	}
	
	/**
	 * Insert a line at the specified index in the list
	 * If there is a node at the specified index, it will be pushed back
	 * @param index the position in the list, 0 indexed
	 */
	public void insertAt(int index, Line data) {
		//Try to get the node currently at the index. If there is no node there, then 
		try {
			Node nodeAtIndex = getNode(index);
		} catch (IndexOutOfBoundsException e) {
			
		}
	}
	
	/**
	 * Get the node at the specified index in the list
	 * @param index the position in the list, 0 indexed
	 * @return Node at index
	 */
	private Node getNode(int index) {
		//Check and make sure the index is >= 0
		if(index < 0)
			throw new IndexOutOfBoundsException("Index must be greater than or equal to 0");
		
		//Set up a count and current node to help with traversal of the list
		int count = 0;
		Node current = head.next;
		
		//Traverse the list until the end or the specified index is reached
		while(current != null && count < index) {
			current = current.next;
			count++;
		}
		
		//If current is null, then index is outside of the list. Throw an exception to be consistent with our <0 check
		if(current == null)
			throw new IndexOutOfBoundsException("Index greater than length of list");
		
		//At this point current is the node at the index, just return it
		return current;
	}
	
	//Nodes to store lines in the list
	private class Node {
		//The actual line to store
		Line data;
		
		//References to nodes infront and behind in order to create doubly linked list behavior
		Node next, previous;
		
		//Create a node holding a line with no forward or backward links
		public Node(Line data) {
			this.data = data;
		}
	}
}
