package DataStructures.Week2;

public class BRTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
    
    private boolean fixNeeded = false;

    public BRTree(T value){
        super(value);
    }

    @Override
    public boolean add(T value){
        if(this.contains(value)) return false;
        this.root = add(root, value);
        this.root.color = Color.BLACK;
        return true;
    }

    // let N = inserted node, child of P
    // let P = parent of N, child of G
    // let U = other child of G, can be null 
    // let G = parent of P and U
    private Node add(Node node, T value){
        //stop case insert
        if(node == null) return new Node(value);
        // search where to insert
        int cmp = value.compareTo(node.value);
        if(cmp < 0) { // value is smaller than node's value
            node.left = add(node.left, value);

            //checking on node G
            Node G = node;
            Node P = node.left;
            Node U = node.right;
            // we know P is to the left of G because of cmp < 0
            if(P.color == Color.RED){
                if(isRed(P.left) || isRed(P.right)){
                    if(isRed(U)){
                        //Red uncle
                        G.color = Color.RED;
                        U.color = Color.BLACK;
                        P.color = Color.BLACK;
                    } else {
                        if(isRed(P.left)){
                            //LL
                            P = rotateRight(G);
                            //G is P.right
                            P.color = P.right.color;
                            P.right.color = P.left.color;
                            node = P;
                        } else {
                            //LR
                            Node N = P.right;
                            N = rotateLeft(P);
                            //P is now N.left
                            G.left = N;
                            P = rotateRight(G);
                            //G is now P.right, N is P.left
                            P.color = P.right.color;
                            P.right.color = P.left.color;
                            node = P;
                        }
                    }
                }
            }
        } else if(cmp > 0){
            node.right = add(node.right, value);
             //checking on node G
            Node G = node;
            Node P = node.right;
            Node U = node.left;
            // we know P is to the right of G because of cmp > 0
            if(P.color == Color.RED){
                if(isRed(P.left) || isRed(P.right)){
                    if(isRed(U)){
                        //Red uncle
                        G.color = Color.RED;
                        U.color = Color.BLACK;
                        P.color = Color.BLACK;
                    } else {
                        if(isRed(P.left)){
                            //RL
                            Node N = P.right;
                            N = rotateRight(P);
                            //P is now N.right
                            G.right = N;
                            P = rotateLeft(G);
                            //G is now P.left, , N is P.right
                            P.color = P.left.color;
                            P.left.color = P.right.color;
                            node = P;
                        } else {
                            //RR
                            P = rotateLeft(G);
                            //G is P.left, N is P.right
                            P.color = P.left.color;
                            P.left.color = P.right.color;
                            node = P;

                        }
                    }
                }
            }
        }
        return node;
    }

    public boolean delete(T value){
        if (!this.contains(value)) return false;
        this.root = delete(root, value);
        if(this.root != null) this.root.color = Color.BLACK;
        return true;
    }

    private Node delete(Node node, T value){
        // find the element, swap values with nextMinValue, delete nextMinValue's node
        if(node == null) return node;
        
        int cmp = value.compareTo(node.value);
        if(cmp < 0){
            node.left = delete(node.left, value);
            if(this.fixNeeded) node = fixLeft(node);
        } else if(cmp > 0){
            node.right = delete(node.right, value);
            if(this.fixNeeded) node = fixRight(node);
        } else {

            if(node.right != null && node.left != null){
                //find node with NVM
                Node MND = findNextMinNode(node.right);
                //swap values
                node.value = MND.value;
                // delete MND
                node.right = delete(node.right, MND.value);
                if(this.fixNeeded) node = fixRight(node);
            } else {
                // 0 or 1 children
                if(node.left != null){
                    // node is BLACK and child is RED
                    node.value = node.left.value;
                    node.color = Color.BLACK;
                    node.left = null;
                } else if(node.right != null){
                    // node is BLACK and child is RED
                    node.value = node.right.value;
                    node.color = Color.BLACK;
                    node.right = null;
                } else {
                    //0 children
                    if(isRed(node)) return null;
                    // else
                    this.fixNeeded = true;
                    return null; // we return null because we delete it
                }
            }
        }
        return node;
    }

    private Node fixLeft(Node node){
        Node P = node;
        Node S = node.right; // sibling is on the right when DB is on the left

        if(isRed(S)){
            // Case 1: S is RED → rotate P left, swap P/S colors, recurse on old P
            P = rotateLeft(P);
            Color temp = P.left.color;
            P.left.color = P.color;
            P.color = temp;
            P.left = fixLeft(P.left);
        } else if(!isRed(S.left) && !isRed(S.right)){
            // Case 2: S BLACK, both children BLACK → push blackness up
            S.color = Color.RED;
            if(isRed(P)){
                P.color = Color.BLACK;
                fixNeeded = false;
            }
            // if P is BLACK: fixNeeded stays true, propagates up
        } else {
            if(!isRed(S.right)){
                // Case 3: S BLACK, near child (S.left) RED, far child (S.right) BLACK
                // Rotate S right and swap colors → converts to Case 4
                Color oldSColor = S.color;
                S = rotateRight(S);
                S.color = oldSColor;        // new S (old S.left) → BLACK
                S.right.color = Color.RED;  // old S → RED
                P.right = S;
            }
            // Case 4: S BLACK, far child (S.right) RED
            Color oldPColor = P.color;
            P = rotateLeft(P);
            P.color = oldPColor;          // new root gets P's original color
            P.left.color = Color.BLACK;   // old P → BLACK
            P.right.color = Color.BLACK;  // far child → BLACK
            fixNeeded = false;
        }
        return P;
    }

    private Node fixRight(Node node){
        Node P = node;
        Node S = node.left; // sibling is on the left when DB is on the right

        if(isRed(S)){
            // Case 1: S is RED → rotate P right, swap P/S colors, recurse on old P
            P = rotateRight(P);
            Color temp = P.right.color;
            P.right.color = P.color;
            P.color = temp;
            P.right = fixRight(P.right);
        } else if(!isRed(S.left) && !isRed(S.right)){
            // Case 2: S BLACK, both children BLACK → push blackness up
            S.color = Color.RED;
            if(isRed(P)){
                P.color = Color.BLACK;
                fixNeeded = false;
            }
            // if P is BLACK: fixNeeded stays true, propagates up
        } else {
            if(!isRed(S.left)){
                // Case 3: S BLACK, near child (S.right) RED, far child (S.left) BLACK
                // Rotate S left and swap colors → converts to Case 4
                Color oldSColor = S.color;
                S = rotateLeft(S);
                S.color = oldSColor;        // new S (old S.right) → BLACK
                S.left.color = Color.RED;   // old S → RED
                P.left = S;
            }
            // Case 4: S BLACK, far child (S.left) RED
            Color oldPColor = P.color;
            P = rotateRight(P);
            P.color = oldPColor;          // new root gets P's original color
            P.right.color = Color.BLACK;  // old P → BLACK
            P.left.color = Color.BLACK;   // far child → BLACK
            fixNeeded = false;
        }
        return P;
    }

    private boolean isRed(Node node){
        return node == null ? false : node.color == Color.RED;
    }

    private Node rotateRight(Node node){
        Node P = node.left;
        Node pRight = P.right;
        P.right = node;
        node.left = pRight;
        return P;
    }

    private Node rotateLeft(Node node){
        Node P = node.right;
        Node pLeft = P.left;
        P.left = node;
        node.right = pLeft;
        return P;
    }

    // ── helpers for testing ──────────────────────────────────────────────────

    private boolean validateRB(Node node, int blackCount, int[] expected){
        if(node == null){
            if(expected[0] == -1) expected[0] = blackCount;
            return blackCount == expected[0];
        }
        if(isRed(node) && (isRed(node.left) || isRed(node.right))) return false;
        int nextCount = blackCount + (node.color == Color.BLACK ? 1 : 0);
        return validateRB(node.left, nextCount, expected) &&
               validateRB(node.right, nextCount, expected);
    }

    private boolean isValidRB(){
        if(root == null) return true;
        if(isRed(root)) return false;
        return validateRB(root, 0, new int[]{-1});
    }

    private void check(String label, boolean condition){
        System.out.println((condition ? "PASS" : "FAIL") + " " + label);
    }

    public static void main(String[] args){
        // ── delete RED leaf ──────────────────────────────────────────────────
        BRTree<Integer> t1 = new BRTree<>(10);
        t1.add(5); t1.add(15);
        // 5 and 15 are RED leaves; root 10 is BLACK
        t1.delete(5);
        t1.check("Delete RED leaf: 5 gone",        !t1.contains(5));
        t1.check("Delete RED leaf: 10 still there", t1.contains(10));
        t1.check("Delete RED leaf: valid RB",        t1.isValidRB());

        // ── delete BLACK node with one RED child ─────────────────────────────
        // Insert 10,5,15,3 → after rebalancing, 3 is a RED leaf under 5(BLACK)
        BRTree<Integer> t2 = new BRTree<>(10);
        t2.add(5); t2.add(15); t2.add(3);
        t2.delete(5);  // 5 is BLACK with RED child 3 → replace value, drop child
        t2.check("Delete BLACK+1RED child: 5 gone",  !t2.contains(5));
        t2.check("Delete BLACK+1RED child: 3 there",  t2.contains(3));
        t2.check("Delete BLACK+1RED child: valid RB",  t2.isValidRB());

        // ── delete causing Case 2 (sibling BLACK, both nephews BLACK) ────────
        // Build: 10(B) 5(B) 15(B) → perfect 3-node BLACK tree
        // Deleting 5 (BLACK leaf): sibling 15 is BLACK with no children → Case 2
        BRTree<Integer> t3 = new BRTree<>(10);
        t3.add(5); t3.add(15);
        // Force both children black for this test by adding/deleting to trigger recolor
        t3.add(3); t3.add(7); // 3 and 7 are RED; this recolors 5 to BLACK, 3/7 RED
        t3.delete(3); t3.delete(7); // remove the RED leaves so 5 is BLACK leaf
        t3.delete(5);
        t3.check("Case 2: 5 gone",    !t3.contains(5));
        t3.check("Case 2: valid RB",   t3.isValidRB());

        // ── delete causing Case 4 (far nephew RED) ───────────────────────────
        BRTree<Integer> t4 = new BRTree<>(10);
        t4.add(5); t4.add(15); t4.add(20);
        // After inserts: 10(B) 5(B) 15(B) 20(R)
        t4.delete(5);
        t4.check("Case 4: 5 gone",    !t4.contains(5));
        t4.check("Case 4: valid RB",   t4.isValidRB());

        // ── delete with two children ─────────────────────────────────────────
        BRTree<Integer> t5 = new BRTree<>(10);
        t5.add(5); t5.add(15); t5.add(3); t5.add(7); t5.add(12); t5.add(20);
        t5.delete(10);
        t5.check("Two-children delete: 10 gone",  !t5.contains(10));
        t5.check("Two-children delete: 5 there",   t5.contains(5));
        t5.check("Two-children delete: 15 there",  t5.contains(15));
        t5.check("Two-children delete: valid RB",   t5.isValidRB());

        // ── delete all nodes one by one ──────────────────────────────────────
        BRTree<Integer> t6 = new BRTree<>(10);
        int[] vals = {5, 15, 3, 7, 12, 20, 1, 4, 6, 8};
        for(int v : vals) t6.add(v);
        boolean allOk = true;
        for(int v : vals){
            t6.delete(v);
            if(!t6.isValidRB()){ allOk = false; break; }
        }
        t6.delete(10);
        t6.check("Delete all: valid RB after each step", allOk);
    }
}
