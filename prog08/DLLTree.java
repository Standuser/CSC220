package prog08;
import java.util.*;

import prog04.DLLEntry;
import prog07.BST;
import prog07.BST;

public class DLLTree <K extends Comparable<K>, V>
  extends AbstractMap<K, V> {

  private class Node <K extends Comparable<K>, V>
    implements Map.Entry<K, V> {
    K key;
    V value;
  
    Node left, right;
    int size = 1; // size of subtree with this node as root

    Node previous, next;
    
    Node (K key, V value) {
      this.key = key;
      this.value = value;
    }

    public K getKey () { return key; }
    public V getValue () { return value; }
    public V setValue (V newValue) {
      V oldValue = value;
      value = newValue;
      return oldValue;
    }
  }
  
  private int size;
  private Node theRoot, tail, head;

  public int size () { 
    if (theRoot == null)
      return 0;
    return theRoot.size;
  }

  /**
   * Find the node with the given key.
   * @param key The key to be found.
   * @return The node with that key.
   */
  private Node<K, V> find (K key, Node<K,V> root) {
    // EXERCISE
    // return null; // not correct
	/*	if (theRoot != null) {
			int cmp = key.compareTo(theRoot.key);
			if (cmp == 0) {
				return theRoot;
			} else if (cmp < 0) {
				return find(key, theRoot.left);
			} else
				return find(key, theRoot.right);
		}

		return null;*/
	  if(root == null) {
		  return null;
	  }
	  if(key.compareTo(root.key) ==0) {
		  return root;
	  }
	  if(key.compareTo(root.key) < 0) {
		  return find(key, root.left);
	  } else {
		  return find(key, root.right);
	  }
  }    

  public boolean containsKey (Object key) {
    return find((K) key, theRoot) != null;
  }
  
  public V get (Object key) {
    Node<K, V> node = find((K) key, theRoot);
    if (node != null)
      return node.value;
    return null;
  }
  
  public boolean isEmpty () { return theRoot == null; }

  /**
   * Add node between previous and next in the DLL.
   */
  private void addDLL (Node<K,V> node, Node<K,V> previous, Node<K,V> next) {
    // EXERCISE Step 8
	  node.next=next;
	  node.previous=previous;
	  
	  if(previous == null) {
		  head=node;
	  }else{
		  previous.next=node;
	  }
	  //it's opposite 
	  //like codes in put and add.
	  if(next==null) {
		  tail=node;
	  }else {
		   next.previous=node;
	  }

  }

  /**
   * Add key,value pair to tree rooted at root.
   * Return root of modified tree.
   */
  private Node<K,V> add (K key, V value, Node<K,V> root) {
    // EXERCISE
	 //need changes-done
		if (root == null) {
			return new Node<K, V>(key, value);
		}
	
		if (key.compareTo(root.key) < 0) {
			root.left = add(key, value,root.left);
		
		if (root.left.next == null) {
				addDLL(root.left, root.previous, root);
				//addDLL(arg1, arg2, arg3)//put the key here//theRoot.left.next = theRoot;	//next is its root(1->2)
		}
		}else if (key.compareTo(root.key) > 0) {
			root.right = add(key, value, root.right);
			if (root.right.previous == null) {
				//addDLL(Node<K,V>,theRoot.previous, theRoot.next);
				addDLL(root.right, root,root.next);
				//addDLL(arg1, arg2, arg3)//theRoot.right.previous= theRoot;//it's previous is its root//opposite to left(10<-11)
			}
		}
		root.size++;
		//step10 done?
		if (size(root.left) > (root.size * (2.0 / 3.0)) || size(root.right) > (root.size * (2.0 / 3.0))) {
			return rebalance(getMinimum(root), size(root)); 
		}		
			
		/*if (key.compareTo(theRoot.key) > 0)
			theRoot.right = add(key, value, theRoot.right);*/
		return root;
 
  }
  
  
  
  
  /**
   * Return the size of the tree rooted at root, using root.size if it
   * is not an empty tree.
   */
  private int size (Node<K,V> root) {
    if (root == null)
      return 0;
    return root.size;
  }

  /**
   * Return the root of a balanced tree made from the nodes in a DLL
   * with head head and size elements.
   */
  private Node<K,V> rebalance (Node<K,V> head, int size) {
    // EXERCISE
	  if(size==1) {
		  head.left=null;
		  head.right=null;
		  head.size=1;
		  return head;
	  }
	  
	  //left sub=rebalanceLeft(head,size/2);
	  //right sub=rebalanceLeft(left.next, size - size/2)
	  Node<K,V> root=rebalanceLeft(head, size - size/2);
	  root.right =rebalance(root.next, size/2);
	  //left.size=size(left)
	  root.size=size(root.left)+size(root.right)+1;
    return root;
  }

  /**
   * Return the left half of a balanced tree made from the nodes in a
   * DLL with head head and size elements: the root and left subtree
   * are correct, but the right subtree is empty.
   */
  private Node<K,V> rebalanceLeft (Node<K,V> head, int size) {
    // EXERCISE	  
	  if(size==1) {
		  head.left=null;
		  head.right=null;
		  head.size=1;
		  return head;
	  }
	  Node<K,V>left=rebalanceLeft(head, size/2);
	  Node<K,V>root=rebalanceLeft(left.next, size - (size/2));
	  
	 left.right=root.left;
	 // update size of left
	 left.size=size(left.left)+size(root.left)+1;
	 root.left=left;
	 // update size of root
	 root.size= left.size + size(root.right) + 1;
	 
    return root;
  }

  public V put (K key, V value) {
    // EXERCISE
		//need changes
	  /*if(containsKey(key)) {//change it
		  find(key, theRoot).setValue(value);
		  return value;
	  }
			
			//theRoot = add(key, value, theRoot); old code
			// if null, add in a new key and value to the root
		}*/
	Node<K,V> put = find(key,theRoot);
    if (put != null){
      find(key,theRoot).setValue(value);
    } else {
      theRoot = add(key, value, theRoot);
      size++;
    }
    if(theRoot.previous == null) {
      head = theRoot;
    }
    if(theRoot.next == null) {
      tail = theRoot;
    }

    return null;

  }      
  
  public V remove (Object keyAsObject) {
    // EXERCISE
    K key = (K) keyAsObject;
 
	/*Node<K, V> node = find(key, theRoot);
	if (node != null) {
	} else if (node == null){
		return null;
  }*/
    Node<K, V> node = find(key, theRoot);
    if (node == null)
    	return null;
    theRoot =remove(key,theRoot);
    size--;
    return node.value;

  }

  /**
   * Remove the node with key from the tree with root.  Return the
   * root of the resulting tree.
   */
  private Node<K,V> remove (K key, Node<K,V> root) {
    // EXERCISE
	  //call DLL remove
      if(key.compareTo(root.key)==0) {
    	  return removeRoot(root);
      }
      if(key.compareTo(root.key)>0) {
    	  root.right= remove(key,root.right);
      } 

if(key.compareTo(root.key)<0) {
    	  root.left= remove(key,root.left);
      }
      root.size--;
      if (size(root.left) > (root.size * (2.0 / 3.0)) || size(root.right) > (root.size * (2.0 / 3.0))) {
    	  return rebalance(getMinimum(root), size(root)); 
      }
    return root;
  }

  /**
   * Remove node from a DLL.
   */
  private void removeDLL (Node<K,V> node) {
    // EXERCISE

	  if(node.previous == null) {
		  head = node.next;
	  } else {
		  node.previous.next = node.next;
	  } 
	  if(node.next == null) {
		  tail = node.previous;
	  } else {
		  node.next.previous = node.previous;
	  }
  }

  /**
   * Remove root of tree rooted at root.
   * Return root of a BST of the remaining nodes.
   */
  private Node<K,V> removeRoot (Node<K,V> root) {
    // IMPLEMENT using getMinimum and removeMinimum
    // Node returned by getMinimum becomes the new root.
		if (root.left == null) {
			return root.right;
		} else if (root.right == null) {
			return root.left;
		} else {
			// Use getMininum to get the minimum (leftmost) node in the right subtree.
			// That will be the new root.
			// Use removeMinimum to remove the minimum node from the right subtree.
			// Copy the left and right subtree of root to the new root.
			// Return the new root.(done)
			// Node rRoot = root.right;
			// Node lRoot = root.left;
			Node newRoot = getMinimum(root.right);
			newRoot.left = removeMinimum(root.right);
			newRoot.right = root.right;
			// return newRoot;
			root = newRoot;
		}

		return root;

  }

	private Node getMinimum(Node root) {
		// do we need to use Node root?
		if (root.left == null) {
			return root;
		} else
			return getMinimum(root.left);
		// Use getMinimum to get the minimum
		// in the left subtree and return it.
		// return root.left;
		
	}
	// use this method to get the new key

	private Node removeMinimum(Node root) {
		if (root.left == null) {
			return root.right;
		}else
		root.left = removeMinimum(root.left);
		return root;

		// remove minimum of left subtree;
	}



  protected class Iter implements Iterator<Map.Entry<K, V>> {
    Node<K, V> next;
    
    Iter () {
      next = head;
    }
    
    public boolean hasNext () { return next != null; }
    
    public Map.Entry<K, V> next () {
      Map.Entry<K, V> ret = next;
      next = next.next;
      return ret;
    }
    
    public void remove () {
      throw new UnsupportedOperationException();
    }
  }
  
  protected class Setter extends AbstractSet<Map.Entry<K, V>> {
    public Iterator<Map.Entry<K, V>> iterator () {
      return new Iter();
    }
    
    public int size () { return DLLTree.this.size(); }
  }
  
  public Set<Map.Entry<K, V>> entrySet () { return new Setter(); }
  
  public String toString () {
    return toString(theRoot, 0);
  }
  
  private String toString (Node root, int indent) {
    if (root == null)
      return "";
    String ret = toString(root.right, indent + 2);
    for (int i = 0; i < indent; i++)
      ret = ret + "  ";
    ret = ret + root.key + ":" + root.value + ":" + root.size + "\n";
    ret = ret + toString(root.left, indent + 2);
    return ret;
  }

  public String listString () {
    if (head == null)
      return "";
    String s = head.key + ":" + head.value;
    for (Node<K,V> node = head.next; node != null; node = node.next)
      s = s + " " + node.key + ":" + node.value;
    return s;
  }

  public static void main (String[] args) {
    DLLTree<Character, Integer> tree = new DLLTree<Character, Integer>();
    String s = "abcdefghijklmnopqrstuvwxyz";
    
    for (int i = 0; i < s.length(); i++) {
      tree.put(s.charAt(i), i);
      System.out.print(tree);
      System.out.println("-------------");
    }

    System.out.println("list: " + tree.listString());
    System.out.println("-------------");

    for (int i = 0; i < s.length(); i++) {
      tree.remove(s.charAt(i));
      System.out.print(tree);
      System.out.println("-------------");
    }

    System.out.println("list: " + tree.listString());
  }
}