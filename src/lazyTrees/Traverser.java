package lazyTrees;

/**
 * Interface to make any class a 'traverser'
 * Only needs the visit function.
 *
 * @author Ali Zargari
 *
 * @param <E> param type depends on the design.
 *
 *
 */
public interface Traverser<E> {
    public void visit(E x);
}