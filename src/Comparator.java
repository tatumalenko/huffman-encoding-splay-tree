/**
 * A simple comparator interface prototype the method needing to be
 * overridden.
 * <p>
 *
 * Compares two objects of the same generic type to determine its
 * relative ordering.
 *
 * @param <E> the generic types used to compare
 */
public interface Comparator<E> {
    public int compare(E o1, E o2);
}