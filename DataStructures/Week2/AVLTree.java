package DataStructures.Week2;

public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {

    public AVLTree(T value){
        super(value);
    }

    @Override
    public boolean add(T value){
        if(this.contains(value)) return false;
        this.root = add(this.root, value);
        return true;
    }

    private Node add(Node currNode, T value){
        if(currNode == null) return new Node(value);

        int cmp = value.compareTo(currNode.value);
        if(cmp < 0) currNode.left = add(currNode.left, value);
        else if (cmp > 0) currNode.right = add(currNode.right, value);

        //AVLTree counts height as number of nodes in the largest path instead of edges
        currNode.height = calculateHeight(currNode);
        int balance = getBalance(currNode);

        // we use getHeight(left) - getHeight(right) in getBalance
        // this means that for left heavy the balance is positive 
        if(balance > 1){ //LL or LR

            if(getBalance(currNode.left) >= 0){ //LL
                //only need to rotate this node
                return this.rotateRight(currNode);
            } else { //LR
                //need to rotate currNode.left to the left
                currNode.left = rotateLeft(currNode.left);
                // then rotate currNode to the right
                return rotateRight(currNode);
            }
        } else if(balance < -1){//RR or RL
            if(getBalance(currNode.right) <= 0){ //RR
                return this.rotateLeft(currNode);
            } else { //RL
                //need to rotate currNode.right to the right
                currNode.right = rotateRight(currNode.right);
                // then rotate currNode to the right
                return rotateLeft(currNode);
            }
        }
        return currNode;
    }

    private Node rotateRight(Node node){
        // this node needs to become the right child of node.left
        // the right child of node.left needs to become the left child of node
        Node nodeLeft = node.left;
        Node nodeLeftRight = nodeLeft.right;
        nodeLeft.right = node;
        node.left = nodeLeftRight;

        //recalculate heights
        node.height = calculateHeight(node);
        nodeLeft.height = calculateHeight(nodeLeft);
        return nodeLeft;
    }

    private Node rotateLeft(Node node){
        // this node needs to become the left child of node.right
        // the left child of node.right needs to become the right child of node
        Node nodeRight = node.right;
        Node nodeRightLeft = nodeRight.left;
        nodeRight.left = node;
        node.right = nodeRightLeft;

        //recalculate heights
        node.height = calculateHeight(node);
        nodeRight.height = calculateHeight(nodeRight);
        return nodeRight;
    }

    private int calculateHeight(Node node){
        return 1 + Math.max(getNodeHeight(node.left), getNodeHeight(node.right));
    }

    private int getBalance(Node node){
        if (node == null) return 0;
        return getNodeHeight(node.left) - getNodeHeight(node.right);
    }

    private int getNodeHeight(Node node){
        if (node == null) return 0;
        return node.height;
    }

    // @Override
    // public boolean delete(T value){
    //     super.delete(value);
    //     return true;
    // }

    @Override
    public boolean delete(T value){
        if(!this.contains(value)) return false;
        root = delete(root, value);
        return true;
    }


    private Node delete(Node currNode, T value){

        int cmp = value.compareTo(currNode.value);
        if(cmp < 0) currNode.left = delete(currNode.left, value);
        else if (cmp > 0) currNode.right = delete(currNode.right, value);
        else { //found node with same value
            if(currNode.left == null){
                // save the right tree from deleted node into parent
                Node deletedRight = currNode.right;
                return deletedRight;
            } else if(currNode.right == null){
                Node deletedLeft = currNode.left;
                return deletedLeft;
            } else {
                //find next min value (smallest but bigger than value)
                Node nextMinNode = this.findNextMinNode(currNode.right);
                currNode.value = nextMinNode.value;
                currNode.right = delete(currNode.right, nextMinNode.value);
            }
        }

        //update height
        currNode.height = calculateHeight(currNode);
        int balance = getBalance(currNode);

        // we use getHeight(left) - getHeight(right) in getBalance
        // this means that for left heavy the balance is positive 
        if(balance > 1){ //LL or LR

            if(getBalance(currNode.left) >= 0){ //LL
                //only need to rotate this node
                return this.rotateRight(currNode);
            } else { //LR
                //need to rotate currNode.left to the left
                currNode.left = rotateLeft(currNode.left);
                // then rotate currNode to the right
                return rotateRight(currNode);
            }
        } else if(balance < -1){//RR or RL
            if(getBalance(currNode.right) <= 0){ //RR
                return this.rotateLeft(currNode);
            } else { //RL
                //need to rotate currNode.right to the right
                currNode.right = rotateRight(currNode.right);
                // then rotate currNode to the right
                return rotateLeft(currNode);
            }
        }
        return currNode;
    }

    private Node findNextMinNode(Node node){
        Node curr = node;        
        while(curr.left != null){
            curr = curr.left;
        }
        return curr;
    }

    public static void main(String[] args){
        // LL rotation: insert 3,2,1 → root becomes 2
        System.out.println("--- LL rotation ---");
        AVLTree<Integer> ll = new AVLTree<>(3);
        ll.add(2); ll.add(1);
        System.out.println("BFS (expect 2, 1, 3): " + ll.toStringbfsTraversal(ll.root));
        System.out.println("ValidateBST (expect true): " + ll.validateBST(ll.root, null, null));

        // RR rotation: insert 1,2,3 → root becomes 2
        System.out.println("--- RR rotation ---");
        AVLTree<Integer> rr = new AVLTree<>(1);
        rr.add(2); rr.add(3);
        System.out.println("BFS (expect 2, 1, 3): " + rr.toStringbfsTraversal(rr.root));
        System.out.println("ValidateBST (expect true): " + rr.validateBST(rr.root, null, null));

        // LR rotation: insert 3,1,2 → root becomes 2
        System.out.println("--- LR rotation ---");
        AVLTree<Integer> lr = new AVLTree<>(3);
        lr.add(1); lr.add(2);
        System.out.println("BFS (expect 2, 1, 3): " + lr.toStringbfsTraversal(lr.root));
        System.out.println("ValidateBST (expect true): " + lr.validateBST(lr.root, null, null));

        // RL rotation: insert 1,3,2 → root becomes 2
        System.out.println("--- RL rotation ---");
        AVLTree<Integer> rl = new AVLTree<>(1);
        rl.add(3); rl.add(2);
        System.out.println("BFS (expect 2, 1, 3): " + rl.toStringbfsTraversal(rl.root));
        System.out.println("ValidateBST (expect true): " + rl.validateBST(rl.root, null, null));

        // Larger tree — all rotations must keep it balanced and valid
        System.out.println("--- Larger tree ---");
        AVLTree<Integer> big = new AVLTree<>(10);
        for(int v : new int[]{20,30,40,50,25}) big.add(v);
        System.out.println("BFS (expect 30, 20, 40, 10, 25, 50): " + big.toStringbfsTraversal(big.root));
        System.out.println("ValidateBST (expect true): " + big.validateBST(big.root, null, null));
        System.out.println("InOrder sorted (expect true): " + big.toStringInOrderDFSTraversal(big.root));

        // Duplicate rejection
        System.out.println("--- Duplicates ---");
        AVLTree<Integer> dup = new AVLTree<>(5);
        dup.add(3); dup.add(7);
        System.out.println("add(5) duplicate (expect false): " + dup.add(5));
        System.out.println("add(3) duplicate (expect false): " + dup.add(3));
        System.out.println("add(9) new (expect true): " + dup.add(9));

        // Delete non-existent value
        System.out.println("--- Delete non-existent ---");
        AVLTree<Integer> d0 = new AVLTree<>(5);
        d0.add(3); d0.add(7);
        System.out.println("delete(99) (expect false): " + d0.delete(99));
        System.out.println("BFS unchanged (expect 5, 3, 7): " + d0.toStringbfsTraversal(d0.root));

        // Delete leaf node
        System.out.println("--- Delete leaf ---");
        AVLTree<Integer> d1 = new AVLTree<>(2);
        d1.add(1); d1.add(3);
        System.out.println("delete(1) (expect true): " + d1.delete(1));
        System.out.println("BFS (expect 2, 3): " + d1.toStringbfsTraversal(d1.root));
        System.out.println("ValidateBST (expect true): " + d1.validateBST(d1.root, null, null));

        // Delete node with one child
        System.out.println("--- Delete node with one child ---");
        AVLTree<Integer> d2 = new AVLTree<>(2);
        d2.add(1); d2.add(4); d2.add(3);
        // BFS before: 2, 1, 4, 3
        System.out.println("BFS before (expect 2, 1, 4, 3): " + d2.toStringbfsTraversal(d2.root));
        System.out.println("delete(4) (expect true): " + d2.delete(4));
        System.out.println("BFS after (expect 2, 1, 3): " + d2.toStringbfsTraversal(d2.root));
        System.out.println("ValidateBST (expect true): " + d2.validateBST(d2.root, null, null));

        // Delete node with two children (successor copy)
        System.out.println("--- Delete node with two children ---");
        AVLTree<Integer> d3 = new AVLTree<>(2);
        for(int v : new int[]{1,4,3,5}) d3.add(v);
        // BFS: 2, 1, 4, 3, 5
        System.out.println("BFS before (expect 2, 1, 4, 3, 5): " + d3.toStringbfsTraversal(d3.root));
        System.out.println("delete(4) (expect true): " + d3.delete(4));
        // Successor of 4 is 5; 4 replaced by 5, right child of new 5 removed
        System.out.println("BFS after (expect 2, 1, 5, 3): " + d3.toStringbfsTraversal(d3.root));
        System.out.println("ValidateBST (expect true): " + d3.validateBST(d3.root, null, null));

        // Delete triggers rebalance (RR after removing left-heavy sibling)
        System.out.println("--- Delete triggers rebalance ---");
        AVLTree<Integer> d4 = new AVLTree<>(1);
        for(int v : new int[]{2,3,4,5}) d4.add(v);
        // After adds: BFS 2, 1, 4, 3, 5
        System.out.println("BFS before (expect 2, 1, 4, 3, 5): " + d4.toStringbfsTraversal(d4.root));
        System.out.println("delete(1) (expect true): " + d4.delete(1));
        // Removing 1 makes root left-height=0, right-height=2 → rebalance → BFS: 4, 2, 5, 3
        System.out.println("BFS after (expect 4, 2, 5, 3): " + d4.toStringbfsTraversal(d4.root));
        System.out.println("ValidateBST (expect true): " + d4.validateBST(d4.root, null, null));

        // Delete root
        System.out.println("--- Delete root ---");
        AVLTree<Integer> d5 = new AVLTree<>(2);
        d5.add(1); d5.add(3);
        System.out.println("delete(2) root (expect true): " + d5.delete(2));
        // Successor of 2 is 3; root becomes 3, right=null, left=1
        System.out.println("BFS after (expect 3, 1): " + d5.toStringbfsTraversal(d5.root));
        System.out.println("ValidateBST (expect true): " + d5.validateBST(d5.root, null, null));
    }
}
