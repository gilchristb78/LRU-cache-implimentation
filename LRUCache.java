import java.util.HashMap;

/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */
public class LRUCache<T, U> implements Cache<T, U> {

	/**
	 * @param p the data provider to consult for a cache miss
	 * @param cap the exact number of (key,value) pairs to store in the cache
	 */
	public LRUCache (DataProvider<T, U> p, int cap) {
		numMiss = 0;
		listSize = 0;
		head = new Node<>(null,null,null,null);
		tail = new Node<>(null,null,null,head);
		head.next = tail;
		capacity = cap;
		provider = p;
	}

	/**
	 * Returns the number of cache misses since the object's instantiation.
	 * @return the number of cache misses since the object's instantiation.
	 */
	public int getNumMisses () {
		return numMiss;
	}

	/**
	 * Node class used to creat doubly linked lists
	 * @param <T> Parameter type of the key
	 * @param <U> Parameter type of the value
	 */
	private static class Node<T,U>{
		private T key;
		private U val;
		private Node next;
		private Node prev;
		Node(T k,U v, Node n, Node p) {
			key = k;
			next = n;
			prev = p;
			val = v;
		}
	}

	private Node<T,U> head, tail;
	private int numMiss;
	private int listSize;
	private int capacity;
	private DataProvider<T,U> provider;
	private HashMap<T,Node<T,U>> cache = new HashMap<T,Node<T,U>>();;

	/**
	* Returns the value associated with the specified key.
	* @param key the key requested
	* @return the value associated with the key
	*/
	public U get(T key) {
		if(cache.get(key) != null){
			Node n = cache.get(key);
			n.prev.next = n.next;
			n.next.prev = n.prev;
			listSize--;                   //remove the node from the linked list (not the hashmap)
			add((T)n.key,(U)n.val);      // move the node to the end of the line (LRU)
			return (U)n.val;
		}
		numMiss++;
		U val = provider.get(key);
		cache.put(key,add(key,val));
		return val;
	}

	/**
	 * Add a key,val pair to the linked list just before the tail (newest slot)
	 * @param key the key value requested
	 * @param val the associated value
	 * @return Node added to the linked list
	 */
	private Node add(T key, U val){
		Node temp = new Node(key,val,tail,tail.prev);
		tail.prev.next = temp;
		tail.prev = temp;
		listSize ++;
		if(listSize > capacity){
			cache.remove(head.next.key);
			head.next = head.next.next;
			listSize--;
		}
		return temp;
	}
}
