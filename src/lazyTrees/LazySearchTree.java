package lazyTrees;

import java.util.NoSuchElementException;

/**
 * LazySearchTree is based on FHsearch_tree.java, except it enables lazy deletion.
 * This class is meant to help simulates an inventory, enabling the user to keep
 * track of items added and removed (type of the item depends on design).
 *
 *
 * @author Ali Zargari
 */
public class LazySearchTree<E extends Comparable< ? super E > >
        implements Cloneable {


    /**
     * mSize: number of nodes
     */
    protected int mSize;

    /**
     * mSizeHard: tracks the number of hard nodes, both deleted and undeleted.
     */
    protected int mSizeHard;

    /**
     * mDeleted: A counter to keep track of
     */
    protected int mDeleted;

    /**The first/highest node in the tree. the root.*/
    protected LazySTNode mRoot;

    /**
     * Returns true if the mSize is 0, aka list is empty.
     *
     * @return true if the mSize is 0, aka list is empty.
     */
    public boolean empty() {
        return (mSize == 0);
    }

    /**
     * Returns the size of the tree.
     *
     * @return how many nodes are in the tree
     */
    public int size() {

        return mSize;
    }

    /**
     * Clears the tree by setting size to 0, and the
     * root to null.
     *
     *
     */
    public void clear() {
        mSize = 0;
        mRoot = null;
    }

    /**
     * Returns the number of 'levels' in the tree, aka its height.
     * Uses helper method findHeight which takes 2 parameters.
     *
     * @return number of 'levels' in the tree.
     */
    public int showHeight() {

        return findHeight(mRoot, -1);
    }

    /**
     * Returns the number of both deleted and undeleted nodes.
     *
     * @return number of both deleted and undeleted nodes.
     *
     */
    public int sizeHard() {

        return mSizeHard;

    }

    /**
     * Returns the minimum value node that has not been deleted.
     *
     * @return minimum value node that has not been deleted.
     *
     */
    public E findMin() {
        if (mRoot == null)
            throw new NoSuchElementException();


        return findMin(mRoot).data;
    }

    /**
     * Returns the maximum value node that has not been deleted.
     *
     * @return the maximum value node that has not been deleted.
     *
     */
    public E findMax() {
        if (mRoot == null)
            throw new NoSuchElementException();
        return findMax(mRoot).data;
    }

    /**
     * Find the item in the tree that equates to parameter x, and return it.
     * Makes use of helper method find(mRoot, x) to return the correct data.
     *
     * @param x The object we are looking for in the tree.
     *
     * @return the found object, or throw NoSuchElementException.
     */
    public E find( E x ) {
        LazySTNode resultNode;
        resultNode = find(mRoot, x);

        if (resultNode == null)
            throw new NoSuchElementException();

        return resultNode.data;
    }

    /**
     * returns true if there exists an item in the tree
     * that equates to parameter x. Uses the find function to
     * return the right value.
     *
     * @param x The object we are looking for in the tree.
     *
     * @return true if tree contains x
     */
    public boolean contains(E x) {
        return find(mRoot, x) != null;
    }

    /**
     * Attempts to insert parameter x in the tree. If successfull, return true.
     * If unsuccessful, return false.
     *
     * Makes use of helper method that does most of the work.
     *
     * @param x The object we want to insert into the tree
     *
     * @return true if successfully inserted. False if not.
     */
    public boolean insert( E x ) {
        int oldSize = mSize;
        mRoot = insert(mRoot, x);
        return (mSize != oldSize);
    }

    /**
     * Attempts to remove the first node that is equivalent parameter x in the tree.
     * If successful, return true.
     * If unsuccessful, return false.
     *
     * Makes use of helper method that does most of the work.
     *
     * @param x The object we want to insert into the tree
     *
     * @return true if deletion is successful. False if not.
     */
    public boolean remove( E x ) {
        int oldSize = mSize;
        remove(mRoot, x);
        return (mSize != oldSize);
    }

    /**
     * Will traverse all nodes, which means both deleted and undeleted
     * nodes in the tree will be traversed.
     *
     * invokes the helper method traverseHard(func, mRoot) to return correct data.
     *
     * @param func The functor to be used while traversing the current instance of this tree
     *
     */
    public < F extends Traverser<? super E > >
    void traverseHard(F func) {
        traverseHard(func, mRoot);
    }

    /**
     * Will traverse all nodes, which means both deleted and undeleted
     * nodes in the tree will be traversed.
     *
     * invokes the helper method traverseSoft(func, mRoot) to return correct data.
     *
     * @param func The functor to be used while traversing the current instance of this tree
     */
    public < F extends Traverser<? super E > >
    void traverseSoft(F func) {
        traverseSoft(func, mRoot);
    }

    /**
     * Helper method to soft traverse the tree. Will not traverse deleted nodes.
     *
     * @param func The functor to be used while traversing the current instance of this tree
     * @param node helper prameter so we can recursively traverse the tree
     */
    protected <F extends Traverser<? super E>>
    void traverseSoft(F func, LazySTNode node) {
        if(node == null)
            return;

        traverseSoft(func, node.lftChild);

        if(!node.deleted)
            func.visit(node.data);

        traverseSoft(func, node.rtChild);
    }

    /**
     * Helper method to hard traverse the tree. Will traverse all nodes, deleted or non deleted.
     *
     * @param func The functor to be used while traversing the current instance of this tree
     * @param node helper prameter so we can recursively traverse the tree
     */
    protected <F extends Traverser<? super E>>
    void traverseHard(F func, LazySTNode node) {
        if (node == null)
            return;

        traverseHard(func, node.lftChild);
        func.visit(node.data);
        traverseHard(func, node.rtChild);
    }


    /**
     * Returns a copy of this object.
     *
     * @return a copy of this object.
     */
    public Object clone() throws CloneNotSupportedException {

        LazySearchTree<E> newObject = (LazySearchTree<E>) super.clone();

        newObject.clear();
        newObject.mRoot = cloneSubtree(mRoot);
        newObject.mSize = mSize;

        return newObject;
    }

    /**
     * Helper method to find minimum value node in the tree.
     * If not found, return null.
     * @param root the first node where we start the search. Helps find the
     *            value recursively
     *
     * @return Minimum value node found in tree.
     */
    protected LazySTNode findMin( LazySTNode root ) {
        if (root == null)
            return null;

        if (root.lftChild == null)
            return root;

        if (!root.deleted) return root;

        return findMin (root.lftChild);
    }

    /**
     * Helper method to find maximum value node in the tree.
     * If not found, return null.
     * @param root the first node where we start the search. Helps find the
     *            value recursively
     *
     * @return Maximum value node found in tree.
     *
     */
    protected LazySTNode findMax( LazySTNode root ) {

        if (root == null)
            return null;

        if (root.rtChild == null)
            return root;

        if (!root.deleted) return root;

        return findMax(root.rtChild);
    }

    /**
     * Helper method to insert a node into the tree.
     *
     * @param root the first node where we start the search. Helps find the
     *            value recursively
     * @param x The item to be inserted into the tree.
     *
     * @return the root of the tree.
     */
    protected LazySTNode insert( LazySTNode root, E x ) {
        int compareResult;  // avoid multiple calls to compareTo()

        if (root == null) {
            mSize++;
            mSizeHard++;
            return new LazySTNode(x, null, null);
        }

        compareResult = x.compareTo(root.data);

        if ( compareResult < 0 )
            root.lftChild = insert(root.lftChild, x);
        else if ( compareResult > 0 )
            root.rtChild = insert(root.rtChild, x);

        if (compareResult == 0 && root.deleted == true)
        {
            root.deleted = false;
            mDeleted--;
            mSize++;
            mSizeHard = mSize + mDeleted;
        }

        return root;
    }

    /**
     * Helper method to remove a node from the tree.
     *
     * @param root the first node where we start the search. Helps find the
     *            value recursively
     * @param x item to be removed from the tree
     *
     */
    protected void remove( LazySTNode root, E x  ) {
        int compareResult;  // avoid multiple calls to compareTo()

        if (root == null)
            throw new NoSuchElementException();

        compareResult = x.compareTo(root.data);

        if ( compareResult < 0 )
            remove(root.lftChild, x);
        else if ( compareResult > 0 )
            remove(root.rtChild, x);

        // found the node
        if (compareResult == 0 && !root.deleted)
        {
            root.deleted = true;
            mSize--;
            mDeleted++;
            mSizeHard = mSize + mDeleted;
        }
    }

    /**
     * Helper method to find a specific node given the root of a tree.
     * If not found, or if the item is deleted, return null.
     *
     * @param root the first node where we start the search. Helps find the
     *            value recursively
     * @param x item to be removed from the tree
     *
     * @return the root of the tree.
     *
     */
    protected LazySTNode find( LazySTNode root, E x ) {
        int compareResult;  // avoid multiple calls to compareTo()

        if (root == null)
            return null;

        compareResult = x.compareTo(root.data);

        if (compareResult < 0)
            return find(root.lftChild, x);
        if (compareResult > 0)
            return find(root.rtChild, x);
        if(compareResult == 0 && root.deleted)
            return null;

        return root;   // found
    }

    /**
     * Clones the subtree and returns it as a new root node.
     *
     * @param root contains the subtree that we want to clone.
     *
     * @return Subtree of passed parameter, as a new root node.
     *
     */
    protected LazySTNode cloneSubtree(LazySTNode root) {
        LazySTNode newNode;
        if (root == null)
            return null;

        newNode = new LazySTNode
                (
                        root.data,
                        cloneSubtree(root.lftChild),
                        cloneSubtree(root.rtChild)
                );
        return newNode;
    }

    /**
     * findHeight helper method to count how many levels our tree has.
     *
     * @param node contains the subtree that we want to clone.
     *
     * @return the number of levels in tree.
     *
     */
    protected int findHeight( LazySTNode node, int height ) {
        int leftHeight, rightHeight;
        if (node == null)
            return height;
        height++;
        leftHeight = findHeight(node.lftChild, height);
        rightHeight = findHeight(node.rtChild, height);
        return (leftHeight > rightHeight)? leftHeight : rightHeight;
    }


    /**
     * An object of this class can be used as a node in LazySearchTree.
     * Made for lazy deletion.
     *
     * @author Foothill College, Ali Zargari
     */
    private class LazySTNode {
        /** use public access so the tree or other classes can access members*/
        public LazySTNode lftChild, rtChild;
         /**data in the node*/
        public E data;
         /**true if deleted.*/
        public boolean deleted;

        public LazySTNode(E d, LazySTNode lft, LazySTNode rt ) {
            lftChild = lft;
            rtChild = rt;
            deleted = false;
            data = d;
        }

        //Constructor
        public LazySTNode() {
            this(null, null, null);
        }

    }
}

