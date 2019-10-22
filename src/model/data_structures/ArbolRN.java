package model.data_structures;
import java.util.NoSuchElementException;
import model.data_structures.Queue;
public class ArbolRN <K extends Comparable<K>, V>{
	private static final boolean RED   = true;
	private static final boolean BLACK = false;

	private NodoArbol root;     
	private class NodoArbol  {
		private K key;           
		private V value;         
		private NodoArbol left;
		private NodoArbol right;  
		private boolean color;    
		private int size;         

		public NodoArbol(K key, V val, boolean color, int size) {
			this.key = key;
			this.value = val;
			this.color = color;
			this.size = size;
		}
	}


	public ArbolRN() {
	}
	private boolean isRed(NodoArbol nodo) {
		if (nodo == null)
			return false;
		return nodo.color == RED;
	}
	private int size(NodoArbol nodo) {
		if (nodo == null)
			return 0;
		return nodo.size;
	} 
	public int size() {
		return size(root);
	}

	public boolean isEmpty() {
		return root == null;
	}

	public V get(K key) {
		if (key == null) 
			throw new IllegalArgumentException("la llave ingresada por parametro es null");
		return get(root, key);
	}

	private V get(NodoArbol nodo, K key) {
		while (nodo != null) {
			int cmp = key.compareTo(nodo.key);
			if      (cmp < 0) 
				nodo = nodo.left;
			else if (cmp > 0)
				nodo = nodo.right;
			else 
				return nodo.value;
		}
		return null;
	}


	public boolean contains(K key) {
		return get(key) != null;
	}

	public void put(K key, V val) {
		if (key == null) 
			throw new IllegalArgumentException("La llave a colocar es null");
		if (val == null) {
			delete(key);
			return;
		}

		root = put(root, key, val);
		root.color = BLACK;
	}

	private NodoArbol put(NodoArbol nodo, K key, V val) { 
		if (nodo == null) 
			return new NodoArbol(key, val, RED, 1);

		int cmp = key.compareTo(nodo.key);
		if      (cmp < 0) 
			nodo.left  = put(nodo.left,  key, val); 
		else if (cmp > 0)
			nodo.right = put(nodo.right, key, val); 
		else             
			nodo.value   = val;

		if (isRed(nodo.right) && !isRed(nodo.left))     
			nodo = rotateLeft(nodo);
		if (isRed(nodo.left)  &&  isRed(nodo.left.left))
			nodo = rotateRight(nodo);
		if (isRed(nodo.left)  &&  isRed(nodo.right))    
			flipColors(nodo);
		nodo.size = size(nodo.left) + size(nodo.right) + 1;

		return nodo;
	}


	public void deleteMin() {
		if (isEmpty())
			throw new NoSuchElementException("El árbol está vacio");

		if (!isRed(root.left) && !isRed(root.right))
			root.color = RED;

		root = deleteMin(root);
		if (!isEmpty()) 
			root.color = BLACK;
	}

	private NodoArbol deleteMin(NodoArbol nodo) { 
		if (nodo.left == null)
			return null;

		if (!isRed(nodo.left) && !isRed(nodo.left.left))
			nodo = moveRedLeft(nodo);

		nodo.left = deleteMin(nodo.left);
		return balance(nodo);
	}


	public void deleteMax() {
		if (isEmpty()) throw new NoSuchElementException("El árbol está vacio");

		if (!isRed(root.left) && !isRed(root.right))
			root.color = RED;

		root = deleteMax(root);
		if (!isEmpty()) root.color = BLACK;

	}

	private NodoArbol deleteMax(NodoArbol nodo) { 
		if (isRed(nodo.left))
			nodo = rotateRight(nodo);

		if (nodo.right == null)
			return null;

		if (!isRed(nodo.right) && !isRed(nodo.right.left))
			nodo = moveRedRight(nodo);

		nodo.right = deleteMax(nodo.right);

		return balance(nodo);
	}


	public void delete(K key) { 
		if (key == null) throw new IllegalArgumentException("La llave a eliminar es null");
		if (!contains(key)) return;


		if (!isRed(root.left) && !isRed(root.right))
			root.color = RED;

		root = delete(root, key);
		if (!isEmpty()) root.color = BLACK;
	}

	private NodoArbol delete(NodoArbol nodo, K key) { 

		if (key.compareTo(nodo.key) < 0)  {
			if (!isRed(nodo.left) && !isRed(nodo.left.left))
				nodo = moveRedLeft(nodo);
			nodo.left = delete(nodo.left, key);
		}
		else {
			if (isRed(nodo.left))
				nodo = rotateRight(nodo);
			if (key.compareTo(nodo.key) == 0 && (nodo.right == null))
				return null;
			if (!isRed(nodo.right) && !isRed(nodo.right.left))
				nodo = moveRedRight(nodo);
			if (key.compareTo(nodo.key) == 0) {
				NodoArbol minDerecho = min(nodo.right);
				nodo.key = minDerecho.key;
				nodo.value = minDerecho.value;
				nodo.right = deleteMin(nodo.right);
			}
			else 
				nodo.right = delete(nodo.right, key);
		}
		return balance(nodo);
	}

	private NodoArbol rotateRight(NodoArbol nodo) {
		NodoArbol hijoIzquierdo = nodo.left;
		nodo.left = hijoIzquierdo.right;
		hijoIzquierdo.right = nodo;
		hijoIzquierdo.color = hijoIzquierdo.right.color;
		hijoIzquierdo.right.color = RED;
		hijoIzquierdo.size = nodo.size;
		nodo.size = size(nodo.left) + size(nodo.right) + 1;
		return hijoIzquierdo;
	}

	private NodoArbol rotateLeft(NodoArbol pNodo) {
		NodoArbol nodoDerecho = pNodo.right;
		pNodo.right = nodoDerecho.left;
		nodoDerecho.left = pNodo;
		nodoDerecho.color = nodoDerecho.left.color;
		nodoDerecho.left.color = RED;
		nodoDerecho.size = pNodo.size;
		pNodo.size = size(pNodo.left) + size(pNodo.right) + 1;
		return nodoDerecho;
	}


	private void flipColors(NodoArbol nodo) {

		nodo.color = !nodo.color;
		nodo.left.color = !nodo.left.color;
		nodo.right.color = !nodo.right.color;
	}

	private NodoArbol moveRedLeft(NodoArbol nodo) {

		flipColors(nodo);
		if (isRed(nodo.right.left)) { 
			nodo.right = rotateRight(nodo.right);
			nodo = rotateLeft(nodo);
			flipColors(nodo);
		}
		return nodo;
	}

	private NodoArbol moveRedRight(NodoArbol nodo) {

		flipColors(nodo);
		if (isRed(nodo.left.left)) { 
			nodo = rotateRight(nodo);
			flipColors(nodo);
		}
		return nodo;
	}


	private NodoArbol balance(NodoArbol nodo) {


		if (isRed(nodo.right))                    
			nodo = rotateLeft(nodo);
		if (isRed(nodo.left) && isRed(nodo.left.left))
			nodo = rotateRight(nodo);
		if (isRed(nodo.left) && isRed(nodo.right))
			flipColors(nodo);

		nodo.size = size(nodo.left) + size(nodo.right) + 1;
		return nodo;
	}



	public int height() {
		return height(root);
	}
	private int height(NodoArbol nodo) {
		if (nodo == null) 
			return -1;
		return 1 + Math.max(height(nodo.left), height(nodo.right));
	}


	public K min() {
		if (isEmpty()) 
			throw new NoSuchElementException("El árbol está vacio");
		return min(root).key;
	} 

	private NodoArbol min(NodoArbol nodo) { 

		if (nodo.left == null)
			return nodo; 
		else     
			return min(nodo.left); 
	} 


	public K max() {
		if (isEmpty())
			throw new NoSuchElementException("El árbol está vacio");
		return max(root).key;
	} 

	private NodoArbol max(NodoArbol nodo) { 
		if (nodo.right == null) 
			return nodo; 
		else 
			return max(nodo.right); 
	} 



	public K floor(K pKey) {
		if (pKey == null)
			throw new IllegalArgumentException("La llave ingresada por parametro es null");
		if (isEmpty())
			throw new NoSuchElementException("el árbol está vacio");
		NodoArbol x = floor(root, pKey);
		if (x == null) 
			return null;
		else 
			return x.key;
	}    
	private NodoArbol floor(NodoArbol nodo, K pKey) {
		if (nodo == null) 
			return null;
		int cmp = pKey.compareTo(nodo.key);
		if (cmp == 0)
			return nodo;
		if (cmp < 0)
			return floor(nodo.left, pKey);
		NodoArbol floorRecursivo = floor(nodo.right, pKey);
		if (floorRecursivo != null)
			return floorRecursivo; 
		else 
			return nodo;
	}

	public K ceiling(K pKey) {
		if (pKey == null) 
			throw new IllegalArgumentException("la llave ingresada por parametro es null");
		if (isEmpty()) 
			throw new NoSuchElementException("El arbol está vacio");
		NodoArbol x = ceiling(root, pKey);
		if (x == null) 
			return null;
		else   
			return x.key;  
	}

	private NodoArbol ceiling(NodoArbol nodo, K pKey) {  
		if (nodo == null) return null;
		int cmp = pKey.compareTo(nodo.key);
		if (cmp == 0)
			return nodo;
		if (cmp > 0)  
			return ceiling(nodo.right, pKey);
		NodoArbol ceilingRecursivo = ceiling(nodo.left, pKey);
		if (ceilingRecursivo != null) 
			return ceilingRecursivo; 
		else 
			return nodo;
	}


	public K select(int n) {
		if (n < 0 || n >= size()) {
			throw new IllegalArgumentException("El número ingresado por parametro es invalido: " + n);
		}
		NodoArbol nodo = select(root, n);
		return nodo.key;
	}


	private NodoArbol select(NodoArbol nodo, int n) {
		int tamanio = size(nodo.left); 
		if      (tamanio > n) return select(nodo.left,  n); 
		else if (tamanio < n) return select(nodo.right, n-tamanio-1); 
		else            return nodo; 
	} 


	public int rank(K pKey) {
		if (pKey == null) 
			throw new IllegalArgumentException("La llave ingresada por parametro es null");
		return rank(pKey, root);
	} 


	private int rank(K pKey, NodoArbol nodo) {
		if (nodo == null) 
			return 0; 
		int cmp = pKey.compareTo(nodo.key); 
		if      (cmp < 0) 
			return rank(pKey, nodo.left); 
		else if (cmp > 0) 
			return 1 + size(nodo.left) + rank(pKey, nodo.right); 
		else          
			return size(nodo.left); 
	} 


	public Iterable<K> keys() {
		if (isEmpty())
			return null;
		return keys(min(), max());

	}

	public Iterable<K> keys(K lo, K hi) {
		if (lo == null) 
			throw new IllegalArgumentException("La primera llave es null");
		if (hi == null) 
			throw new IllegalArgumentException("La segunda llave es null");

		Queue<K> queue = new Queue<K>(null);
		keys(root, queue, lo, hi);
		return  queue;
	} 

	private void keys(NodoArbol nodo, Queue<K> queue, K lo, K hi) { 
		if (nodo == null) return; 
		int cmplo = lo.compareTo(nodo.key); 
		int cmphi = hi.compareTo(nodo.key); 
		if (cmplo < 0) 
			keys(nodo.left, queue, lo, hi); 
		if (cmplo <= 0 && cmphi >= 0) 
			queue.enQueue(nodo.key); 
		if (cmphi > 0) 
			keys(nodo.right, queue, lo, hi); 
	} 
	public Iterable<V> valuesInRange(K lo, K hi) {
		if (lo == null) 
			throw new IllegalArgumentException("La primera llave es null");
		if (hi == null) 
			throw new IllegalArgumentException("La segunda llave es null");

		Queue<V> queue = new Queue<V>(null);
		valuesInRange(root, queue, lo, hi);
		return  queue;
	} 
	private void valuesInRange(NodoArbol nodo, Queue<V> queue, K lo, K hi) { 
		if (nodo == null) return; 
		int cmplo = lo.compareTo(nodo.key); 
		int cmphi = hi.compareTo(nodo.key); 
		if (cmplo < 0) 
			valuesInRange(nodo.left, queue, lo, hi); 
		if (cmplo <= 0 && cmphi >= 0) 
			queue.enQueue(nodo.value); 
		if (cmphi > 0) 
			valuesInRange(nodo.right, queue, lo, hi); 
	} 

	public int size(K lo, K hi) {
		if (lo == null)
			throw new IllegalArgumentException("La primera llave es null");
		if (hi == null) 
			throw new IllegalArgumentException("La segunda llave es null");

		if (lo.compareTo(hi) > 0) 
			return 0;
		if (contains(hi))
			return rank(hi) - rank(lo) + 1;
		else        
			return rank(hi) - rank(lo);
	}
}