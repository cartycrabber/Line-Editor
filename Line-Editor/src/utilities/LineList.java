package utilities;

import editor.Line;

public class LineList {
	
	//The first node in the list, which is also a dummy node
	private Node head;
	
	//Keep track of list size to make sure we're in bounds
	private int size;
	
	//Default constructor that just initializes the dummy node
	public LineList() {
		head = new Node(null);
		head.previous = head;
		head.next = head;
		size = 0;
	}
	
	/**
	 * Returns the Line at the specified index
	 * @param index index to return
	 */
	public Line get(int index) {
		//Check and make sure the index is something we can actually look for
		if((index < 0) || (index >= size)) {
			throw new IndexOutOfBoundsException("Index not in list");
		}
		
		Node node = getNode(index);
		
		//if node is null then the index is not in the list, throw an error
		if(node == null) {
			throw new IndexOutOfBoundsException("Index not in list");
		}
		else { //return the node's data
			return node.data;
		}
	}
	
	/**
	 * Set the line at the specified (preexisting) index
	 * @param index index to set
	 * @param line line to store
	 * @return returns the old value at the index
	 * @throws IndexOutOfBoundsException if index is < 0 or >= size of the list
	 */
	public Line set(int index, Line line) {
		//Check and make sure the index is something we can actually look for
		if((index < 0) || (index >= size)) {
			throw new IndexOutOfBoundsException("Index not in list");
		}
		
		Node n = getNode(index);
		
		if(n == null) {
			//There is no node at index i
			throw new IndexOutOfBoundsException("Index not in list");
		}
		else {
			Line old = n.data;
			n.data = line;
			return old;
		}
	}
	
	/**
	 * Insert a line at the specified index in the list
	 * If there is a node at the specified index, it will be pushed back
	 * @param index the position in the list, 0 indexed
	 */
	public void insertAt(int index, Line data) {
		//Check and make sure the index is something we can actually look for
		if((index < 0) || (index > size)) {
			throw new IndexOutOfBoundsException("Index not in list");
		}
		
		//Try to get the node currently at the index. If there is no node there, then this will be null
		Node nodeAtIndex = getNode(index);
		
		//Create the node with the line we are adding
		Node nodeToAdd = new Node(data);
		
		//Set the next and previous of the node we are inserting
		nodeToAdd.previous = nodeAtIndex.previous;
		nodeToAdd.next = nodeAtIndex;
		
		//Set the nodes around the node we are inserting to point at the node we are inserting
		nodeToAdd.previous.next = nodeToAdd;
		nodeToAdd.next.previous = nodeToAdd;
		
		size++;
	}
	
	/**
	 * Remove the node at the specified index
	 * @param index index to remove
	 */
	public void removeAt(int index) {
		//Check and make sure the index is something we can actually look for
		if((index < 0) || (index >= size)) {
			throw new IndexOutOfBoundsException("Index not in list");
		}
		
		Node nodeToRemove = getNode(index);
		//Set the links around the node we are removing to skip over the node
		nodeToRemove.previous.next = nodeToRemove.next;
		nodeToRemove.next.previous = nodeToRemove.previous;
		
		//remove the links of the node we are removing
		nodeToRemove.previous = null;
		nodeToRemove.next = null;
		
		size--;
	}
	
	/**
	 * Returns how many Lines are in the list
	 * @return number of Lines in the list
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Returns a string representation of the list
	 */
	public String toString() {
		//Start with an empty string and add each element to that string
		String str = "";
		
		Node current = head.next;
		//Traverse the list, adding each element to the string
		for(int i = 0; i < size; i++) {
			str += current.data;
			//Separate each with a comma
			str += ", ";
			current = current.next;
		}
		//Remove the last comma because it looks better
		if(str.length() >= 2) {//At least one element was added to our string
			str = str.substring(0, str.length() - 2);
		}
		
		return str;
	}
	
	/**
	 * Get the node at the specified index in the list
	 * @param index the position in the list, 0 indexed
	 * @return Node at index
	 */
	private Node getNode(int index) {
		//Check and make sure the index is >= 0
		if((index < 0) || (index > size))
			return null;
		
		//if the list is empty and we are getting index 0, return the dummy node
		if((index == 0) && (size == 0)) {
			return head;
		}
		
		//Set up a count and current node to help with traversal of the list
		int count = 0;
		Node current = head.next;
		
		//Traverse the list until the end or the specified index is reached
		while(current != null && count < index) {
			current = current.next;
			count++;
		}
		
		//Return whatever is at current. If the node was not found, this will be null
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
