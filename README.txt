project folder:
ali-zargari-cs1c-project05/


Brief description of submitted files:

src/lazyTrees/Item.java
    -  One object of Item class represents one item in the inventory, with two class members.

src/lazyTrees/LazySearchTree.java
    -  LazySearchTree is based on FHsearch_tree.java, except it enables lazy deletion.
      This class is meant to help simulates an inventory, enabling the user to keep
      track of items added and removed (type of the item depends on design).

src/lazyTrees/PrintObject.java
    -  An object of this class is used to print objects being traversed through.

src/lazyTrees/SuperMarket.java
    -  Simulates the market scenario of buying and adding items to a store's inventory.
       Implements BST with lazy deletion to keep track of total inventory ("deleted" + non deleted)
       and current inventory (non deleted only).

src/lazyTrees/Traverser.java
    - Interface to make any class a 'traverser'. Only includes on 'visit' function.


resources/inventory_short.txt
    - Test inputs where tree root has both left and right children:

resources/inventory_log.txt
    - A text file filled with sample test inputs for testing basic features and functionality

resources/inventory_invalid_Removal.txt
    - Test input where we request and item that doesn't exist.

resources/gibberish.txt
    - Test input where the program is tested against all sorts of invalid inputs and formats.


resources/P5_part1_proof1.mp4
    - proof that program runs with inventory_log.txt as input

resources/P5_part1_proof2.mp4
    - proof that program runs with inventory_short.txt as input

resources/P5_part1_proof3.mp4
    - proof that program runs with inventory_invalid_removal.txt as input

resources/P5_part1_proof4.mp4
    - proof that program runs with gibberish.txt as input


resources/RUN.txt
    - A copy and paste of various different outputs by this program.

README.txt
    -Description of every file.