package DataStructures.Week2;

import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<? super T>> {
    
    // For all of the left sub-tree n nodes, n.left < n
    // For all of the right sub-tree n nodes, n.right > n
    // what happens if the value is equal? we reject or add a field in the node (quantity or something similar)

    public class Node{
        Node left;
        Node right;
        T value;
        public Node(T value){
            this.left = null;
            this.right = null;
            this.value = value;
        }
    }

    private Node root;

    public BinarySearchTree(T value){
        this.root = new Node(value);
    }

    public boolean validateBST(Node node, T max, T min){

        // while doing an inorder traversal and checking if the ouput is sorted works, 
        // it would require another O(n) operation and O(n) space
        boolean inBoundary = node.value.compareTo(max) < 0 && node.value.compareTo(min) > 0;
        if(!inBoundary) return false;

        if(node.left == null && node.right == null){
            return true;
        }

        return (node.left == null || validateBST(node.left, node.value, min)) && 
               (node.right == null || validateBST(node.right, max, node.value));
    }

    public boolean contains(T value){
        // iterate using in-order
        return containsInOrderTraversal(value, root);
    }

    private boolean containsInOrderTraversal(T value, Node curr){
        if (curr == null) return false;
        if(curr.value.compareTo(value) == 0) return true;
        return containsInOrderTraversal(value, curr.left) || containsInOrderTraversal(value, curr.right);
    }

    public void delete(T value){
        // deleting a node should keep the BST constraints true:
        // 1 - all child left nodes must be lower than all its parents
        // 2 - all child right nodes must be bigger than all its parents 

    }

    public int getHeight(){
        //root node has height 0 because no edges
        return getHeight(this.root, 0);
    }

    private int getHeight(Node curr, int height){
        //since this node is null and argument was given with height+1, we need to subtract 1
        // its faster than checking if left is null and right is null before calling
        // because its either leafs (2^maxHeight) do a single subtraction operation
        // or 2^0 + 2^1 + ... + 2^(maxHeight-1) do 2 compare operations
        // if maxHeight is 4, then 2^4 (assuming a full tree) is 16. Rest of nodes is 1+2+4+8 = 15
        if(curr == null) return height - 1;
        return Math.max(getHeight(curr.left,height+1), getHeight(curr.right, height+1));
    }

    public T getMax(){
        return getMax(root);
        //return oldGetMax(root, null);
    }

    private T getMax(Node curr){
        //could do an inorder traversal and get the last element, but lets do it this way:
        // always go right until we can't anymore.
        if(curr.right == null) return curr.value;
        return getMax(curr.right);
    }

    private T oldGetMax(Node curr, T max){
        if(curr == null) return max;
        T newMax = getMaxBetween(curr.value, max);
        return getMaxBetween(oldGetMax(curr.left,newMax), oldGetMax(curr.right, newMax));
    }

    private T getMaxBetween(T v1, T v2){
        if (v1 == null && v2 == null) return null;
        else if(v1 == null) return v2;
        else if(v2 == null) return v1;
        else return v1.compareTo(v2) > 0 ? v1 : v2;
    }

    // return true if value is inserted, false it not
    public boolean add(T value){
        Node newNode = new Node(value);
        Node curr = this.root;
        Node prev = null;
        boolean insertLeft = true;
        while(curr != null){
            prev = curr;
            int comparisionResult = value.compareTo(curr.value);
            if(comparisionResult < 0){ // value is lower than curr
                curr = curr.left;
                insertLeft = true;
            } else if(comparisionResult > 0){ // value is bigger than curr
                curr = curr.right;
                insertLeft = false;
            } else { // new value is equal to value at curr
                // we will choose to reject
                return false;
            }
        }

        if(insertLeft){
            prev.left = newNode;
        } else {
            prev.right = newNode;
        }
        return true;
    }

    private boolean testValidBFS(T[] values, T max, T min){
        
        Node root = new Node(values[0]);
        Node left1 = new Node(values[1]);
        Node right1 = new Node(values[2]);
        Node left3 = new Node(values[3]);

        root.left = left1;
        root.right = right1;
        right1.left = left3;
        return this.validateBST(root,max,min);
    }

    public static void main(String[] args) {
        System.out.println("--------Tree 1----------");
        BinarySearchTree<Integer> tree = new BinarySearchTree<>(1);
        tree.add(4);
        tree.add(3);
        tree.add(2);
        tree.add(8);
        tree.add(11);
        tree.add(7);
        System.out.println("InOrder: " + tree.toStringInOrderDFSTraversal(tree.root));
        System.out.println("ValidateBST:(true) " + tree.validateBST(tree.root, Integer.MAX_VALUE, Integer.MIN_VALUE));
        System.out.println("PreOrder: " + tree.toStringPreOrderDFSTraversal(tree.root));
        System.out.println("PostOrder: " + tree.toStringPostOrderDFSTraversal(tree.root));
        System.out.println("BFS: " + tree.toStringbfsTraversal(tree.root));
        System.out.println("Contains 3?(true): " + tree.contains(3));
        System.out.println("Contains 11?(true): " + tree.contains(11));
        System.out.println("Contains 9?(false): " + tree.contains(9));
        System.out.println("Contains 1?(true): " + tree.contains(1));
        System.out.println("GetHeight:(3) " + tree.getHeight());
        System.out.println("GetMax:(11) " + tree.getMax());

        System.out.println("-------------Tree 2-------------");
        BinarySearchTree<Integer> tree2 = new BinarySearchTree<>(13);
        tree2.add(7);
        tree2.add(3);
        tree2.add(8);
        tree2.add(15);
        tree2.add(14);
        tree2.add(19);
        tree2.add(18);
        tree2.add(17);
        System.out.println("InOrder: " + tree2.toStringInOrderDFSTraversal(tree2.root));
        System.out.println("ValidateBST:(true) " + tree2.validateBST(tree2.root, Integer.MAX_VALUE, Integer.MIN_VALUE));
        System.out.println("PreOrder: " + tree2.toStringPreOrderDFSTraversal(tree2.root));
        System.out.println("PostOrder: " + tree2.toStringPostOrderDFSTraversal(tree2.root));
        System.out.println("BFS: " + tree2.toStringbfsTraversal(tree2.root));
        System.out.println("Contains 15?(true): " + tree2.contains(15));
        System.out.println("Contains 11?(false): " + tree2.contains(11));
        System.out.println("Contains 9?(false): " + tree2.contains(9));
        System.out.println("Contains 1?(false): " + tree2.contains(1));
        System.out.println("GetHeight:(4) " + tree2.getHeight());
        System.out.println("GetMax:(19) " + tree2.getMax());

        Integer[] arr = new Integer[]{5,1,7,3};
        System.out.println("----Malconstructed tree----");
        System.out.println("testValidateBFS:(false) " + tree.testValidBFS(arr, Integer.MAX_VALUE, Integer.MIN_VALUE));
    }

    public String toStringbfsTraversal(Node node){

        StringBuilder sb = new StringBuilder();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(this.root);
        while(!queue.isEmpty()){
            Node n = queue.poll();
            while(n != null){
                sb.append(n.value + ", ");
                if(n.left != null) queue.offer(n.left);
                if(n.right != null) queue.offer(n.right);
                n = queue.poll();
            }
        }

        return sb.substring(0, sb.length()-2).toString();
    }


    private String toStringInOrderDFSTraversal(Node node){
        if (node == null) return "";
        return this.toStringInOrderDFSTraversal(node.left) + 
            (node == null ? "" : node.value + ", ") + 
            this.toStringInOrderDFSTraversal(node.right);
    }

    private String toStringPreOrderDFSTraversal(Node node){
    if (node == null) return "";
    return (node == null ? "" : node.value + ", ") + 
        this.toStringPreOrderDFSTraversal(node.left) + 
        this.toStringPreOrderDFSTraversal(node.right);
    }

    private String toStringPostOrderDFSTraversal(Node node){
        if (node == null) return "";
        return this.toStringPostOrderDFSTraversal(node.left) + 
            this.toStringPostOrderDFSTraversal(node.right) +
            (node == null ? "" : node.value + ", ");
    }
}
