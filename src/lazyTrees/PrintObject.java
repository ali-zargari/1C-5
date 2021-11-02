package lazyTrees;
/**
 * An object of this class is used to print objects being traversed through.
 *
 * @author Ali Zargari
 *
 * @param <E> param type depends on the design.
 *
 *
 */
public class PrintObject<E> implements Traverser<E> {
    public void visit(E x) {
        System.out.print( x + " ");
    }
}