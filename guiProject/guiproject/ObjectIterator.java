package guiproject;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ObjectIterator<T> implements Iterator<T> {
	private int position; //Always points to the next value
	private T [] values;
	private int count;
	
	
	public ObjectIterator(T[] theValues, int aCount) {
		position = 0;
		values = theValues;
		count = aCount;
	}
	public boolean hasNext() { 
		return position < count;
	}
	public T next() { 
		if (position >= count)
			throw new NoSuchElementException("Past " + count + " elements");
		position++;
		return values[position - 1];
    }
	
	public void remove() {
		throw new 
		UnsupportedOperationException("No remove for ArraySet");
	}

}
