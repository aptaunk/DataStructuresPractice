import java.lang.Math;

public class AdiDynamicArray<E>
{
    private Object[] elmentArray;
    private int size;
    private double expansionFactor;
    
    public AdiDynamicArray() {
        elmentArray = new Object[1];
        size = 0;
        expansionFactor = 2;
    }
    
    public AdiDynamicArray(int initCap) {
        elmentArray = new Object[initCap];
        size = 0;
        expansionFactor = 2;
    }
    
    public AdiDynamicArray(int initCap, double factor) {
        elmentArray = new Object[initCap];
        size = 0;
        expansionFactor = factor;
    }
    
    public int getSize() {
        return size;
    }
    
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return (E)elmentArray[index];
    }
    
    public void set(int index, E val) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        elmentArray[index] = val;
    }
    
    private void expand() {
        Object[] newArray = new Object[Math.round(Math.round(elmentArray.length*expansionFactor))];
        for (int i=0; i<elmentArray.length; i++) {
            newArray[i] = elmentArray[i];
        }
        elmentArray = newArray; 
    }
    
    public void insert(E e) {
        elmentArray[size] = e;
        if (size == elmentArray.length-1) {
            expand();
        }
        size++;
    }
    
    public void remove() {
        elmentArray[size-1] = null;
        size--;
    }
}
