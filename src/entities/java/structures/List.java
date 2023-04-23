package structures;

import java.util.ArrayList;

public class List<T> {
    protected final java.util.List<T> elements = new ArrayList<>();

    public void add(T element) {
        this.elements.add(element);
    }

    public void removeLastElement() {
        this.elements.remove(this.elements.size() - 1);
    }

    public void remove(T element) {
        this.elements.remove(element);
    }

    public int getLength() {
        return this.elements.size();
    }

    public T get(int index) {
        return this.elements.get(index);
    }

    public T getLastElement() {
        int index = this.elements.size() - 1;
        if (index > 0)
            return this.elements.get(index);
        return null;
    }

    public void clear() {
        this.elements.clear();
    }
}
