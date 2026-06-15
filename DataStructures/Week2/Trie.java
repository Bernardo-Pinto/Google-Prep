package DataStructures.Week2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Trie {
    public class Node{
        public Object[] nodes;
        public boolean isEndOfWord;
        public int alphabetSize;

        public Node(int alphabetSize){
            this.alphabetSize = alphabetSize;
            this.nodes = new Object[alphabetSize];
        }
    }

    Node root;

    public Trie(int alphabetSize){
        this.root = new Node(alphabetSize);
    }

    public void insert(String word){
        Node curr = this.root;
        for(char c : word.toCharArray()){
            int charIndex = getNodeIndexFromASCIIChar(c);
            Node charNode = (Node)curr.nodes[charIndex];
            if(charNode == null){
                curr.nodes[charIndex] = new Node(curr.alphabetSize);
                charNode = (Node)curr.nodes[charIndex];
            }
            curr = charNode;
        }
        curr.isEndOfWord = true;

    }
    public boolean search(String word){
        Node curr = this.root;
        for(char c : word.toCharArray()){
            int charIndex = getNodeIndexFromASCIIChar(c);
            Node charNode = (Node)curr.nodes[charIndex];
            if(charNode == null) return false;
            curr = charNode;
        }
        return curr.isEndOfWord;
    }

    public boolean wildCardSearch(String word){
        //return wSearch(word, root);
        return recurWcSrc(word, 0, root);
    }

    private boolean recurWcSrc(String word,int index, Node node){
        if(node == null) return false;
        if(index == word.length()){
            if(node.isEndOfWord) return true;
            else return false;
        }
        char currentChar = word.charAt(index);
        boolean found = false;
        if(currentChar == '.'){
            for(Object o : node.nodes){
                if(o != null){
                    found = found || recurWcSrc(word, index+1, (Node)o);
                }
                if (found) break;
            }
        } else {
            int charIndex = getNodeIndexFromASCIIChar(word.charAt(index));
            Node nextNode = (Node)node.nodes[charIndex];
            found = recurWcSrc(word, index+1, nextNode);
        }
        return found;
    }


    public boolean startsWith(String prefix){
        if(prefix.equals("")) return true;
        if(this.root == null) return false;

        Node curr = this.root;
        for(char c : prefix.toCharArray()){
            int charIndex = getNodeIndexFromASCIIChar(c);
            Node charNode = (Node)curr.nodes[charIndex];
            if(charNode == null) return false;
            curr = charNode;
        }

        return true;
    }

    // If we start from the last node, I should delete if:
    // 1- Node is not endOfWord node 
    // 2- the node has no children
    public boolean recursiveDelete(String word){
        if(!this.search(word)) return false;
        else {
            rDelete(root, 0, word);
            return true;
        }
    }

    public boolean rDelete(Node curr, int index, String word){
        if(index == word.length()){
            // safe to do, because this is the end for the word we are deleting
            curr.isEndOfWord = false;
            return numberOfChildren(curr) == 0;
        }
        int charIndex = getNodeIndexFromASCIIChar(word.charAt(index));
        Node nextNode = (Node)curr.nodes[charIndex];
        boolean shouldDelete = rDelete(nextNode, index+1, word);
        if(shouldDelete){
            curr.nodes[charIndex] = null;
        }
        return numberOfChildren(curr) == 0 && !curr.isEndOfWord;
    }

    public void delete(String word){
        // if the whole word is a prefix, we only set isEndOfWord to false
        // if a subset of the word is a prefix, we delete from the largest prefix
        // largest prefix = node that is endOfWord or has more than 1 child

        Node curr = this.root;

        // go to node of last letter
        for(char c : word.toCharArray()){
            Node charNode = (Node)curr.nodes[getNodeIndexFromASCIIChar(c)];
            if(charNode == null) break;
            curr = charNode;
        }
        boolean isPrefix = false;
        //at the last node, if it has a child, then we are a prefix
        if(curr != null && numberOfChildren(curr)>0) isPrefix = true; 
        curr.isEndOfWord = false; // safe to do 

        if(!isPrefix){
            // if not a prefix, find out the last node where its safe to delete from
            curr = this.root;
            Node lastSafeNodeToDeleteFrom = null;
            int lastSafeNodeToDeleteIndex = -1;
            for(int i = 0; i < word.length();i++){
                int nodeIndex = getNodeIndexFromASCIIChar(word.charAt(i));
                Node node = (Node)curr.nodes[nodeIndex];
                if(node == null) break;
                if(node.isEndOfWord || numberOfChildren(node) >= 2){
                    lastSafeNodeToDeleteFrom = node;
                    lastSafeNodeToDeleteIndex = i+1;
                }
                curr = node;
            }

            if(lastSafeNodeToDeleteIndex != -1){
                int index = getNodeIndexFromASCIIChar(word.charAt(lastSafeNodeToDeleteIndex));
                lastSafeNodeToDeleteFrom.nodes[index] = null;
            }
        }
    }

    int numberOfChildren(Node node){
        int n = 0;
        for(Object o : node.nodes){
            if(o != null) n++;
        }
        return n;
    }


    public String[] getWordsWithPrefix(String prefix){
        List<String> list = new ArrayList<>();
        Node curr = this.root;
        for(char c : prefix.toCharArray()){
            Node next = (Node) curr.nodes[getNodeIndexFromASCIIChar(c)];
            if(next == null) return new String[0];
            curr = next;
        }
        collect(curr, prefix, list);
        return list.toArray(String[]::new);
    }

    private void collect(Node node, String word, List<String> list){
        if(node.isEndOfWord) list.add(word);
        for(int i = 0; i < node.alphabetSize; i++){
            Node child = (Node) node.nodes[i];
            if(child != null) collect(child, word + (char)(i + 'a'), list);
        }
    }

    public int countWordsWithPrefix(String prefix){
        Node curr = this.root;
        for(char c : prefix.toCharArray()){
            Node next = (Node) curr.nodes[getNodeIndexFromASCIIChar(c)];
            if(next == null) return 0;
            curr = next;
        }
        return countWords(curr);
    }

    private int countWords(Node node){
        int count = node.isEndOfWord ? 1 : 0;
        for(int i = 0; i < node.alphabetSize; i++){
            Node child = (Node) node.nodes[i];
            if(child != null) count += countWords(child);
        }
        return count;
    }

    // wildcard search with .

    private int getNodeIndexFromASCIIChar(char c){
        return c-97;
    }

    private static void check(String label, boolean condition){
        System.out.println((condition ? "PASS" : "FAIL") + " " + label);
    }

    public static void main(String[] args){
        // ── insert + search ──────────────────────────────────────────────────
       Trie t = new Trie(26);
       t.insert("apple");
       t.insert("app");
       t.insert("application");
       t.insert("bat");
       t.insert("ball");

       // exact match: inserted words found
       check("search 'apple'        (true)",  t.search("apple"));
       check("search 'app'          (true)",  t.search("app"));
       check("search 'bat'          (true)",  t.search("bat"));

       // exact match: non-inserted words not found
       check("search 'appl'         (false)", !t.search("appl"));
       check("search 'ap'           (false)", !t.search("ap"));
       check("search 'apply'        (false)", !t.search("apply"));
       check("search ''             (false)", !t.search(""));
//
//        // ── startsWith ──────────────────────────────────────────────────────
       check("startsWith 'app'      (true)",  t.startsWith("app"));
       check("startsWith 'appl'     (true)",  t.startsWith("appl"));
       check("startsWith 'ba'       (true)",  t.startsWith("ba"));
       check("startsWith 'apple'    (true)",  t.startsWith("apple"));  // full word is also a valid prefix
       check("startsWith 'xyz'      (false)", !t.startsWith("xyz"));
       check("startsWith 'baz'      (false)", !t.startsWith("baz"));
       check("startsWith ''         (true)",  t.startsWith(""));       // empty prefix matches everything

       // ── delete ──────────────────────────────────────────────────────────
       t.delete("app");
       check("delete 'app': search 'app'    (false)", !t.search("app"));
       check("delete 'app': search 'apple'  (true)",   t.search("apple"));    // shared prefix must survive
       check("delete 'app': startsWith 'app'(true)",   t.startsWith("app"));  // nodes still exist for 'apple'

       t.delete("apple");
       check("delete 'apple': search 'apple'       (false)", !t.search("apple"));
       check("delete 'apple': startsWith 'apple'   (false)", !t.startsWith("apple")); // no words left
       check("delete 'apple': search 'application' (true)",   t.search("application"));

       t.delete("xyz");  // delete non-existent: must not crash or corrupt
       check("delete non-existent: search 'bat' (true)", t.search("bat"));

       // ── recursiveDelete ──────────────────────────────────────────────────
       Trie r = new Trie(26);
       r.insert("apple");
       r.insert("app");
       r.insert("application");
       r.insert("bat");
       r.insert("ball");
       r.insert("applet"); // "apple" is a prefix of "applet"

       // delete a word that IS a prefix of another → path must survive
       r.recursiveDelete("app");
       check("rDelete prefix 'app': search 'app'         (false)", !r.search("app"));
       check("rDelete prefix 'app': search 'apple'       (true)",   r.search("apple"));
       check("rDelete prefix 'app': search 'application' (true)",   r.search("application"));
       check("rDelete prefix 'app': startsWith 'app'     (true)",   r.startsWith("app"));

       // delete a word whose suffix diverges from others → only unique nodes removed
       r.recursiveDelete("apple");
       check("rDelete 'apple': search 'apple'      (false)", !r.search("apple"));
        check("rDelete 'apple': startsWith 'apple'  (true)",   r.startsWith("apple")); // 'e' still exists via 'applet'
       check("rDelete 'apple': search 'applet'     (true)",   r.search("applet"));    // 'e' still exists via 'applet'
       check("rDelete 'apple': search 'application'(true)",   r.search("application"));

       // delete a word that has another word as its prefix → only nodes after the branch removed
       r.recursiveDelete("applet");
       check("rDelete 'applet': search 'applet'    (false)", !r.search("applet"));
//        check("rDelete 'applet': startsWith 'appl'  (true)",   r.startsWith("appl")); // 'l' still exists via 'application'
       check("rDelete 'applet': search 'application'(true)",  r.search("application"));

       // delete a standalone word (no shared prefix with others)
       r.recursiveDelete("bat");
       check("rDelete standalone 'bat': search 'bat'  (false)", !r.search("bat"));
       check("rDelete standalone 'bat': startsWith 'bat'(false)", !r.startsWith("bat")); // all 3 nodes gone
       check("rDelete standalone 'bat': search 'ball' (true)",    r.search("ball"));     // 'ba' still exists

       // delete non-existent word → no crash, no corruption
       r.recursiveDelete("xyz");
       check("rDelete non-existent: search 'ball' (true)", r.search("ball"));

//        // ── getWordsWithPrefix ───────────────────────────────────────────────
       Trie t2 = new Trie(26);
       t2.insert("apple"); t2.insert("app"); t2.insert("application"); t2.insert("bat"); t2.insert("ball");

       // prefix with multiple matches
       String[] appWords = t2.getWordsWithPrefix("app");
       check("getWordsWithPrefix 'app': count 3",           appWords != null && appWords.length == 3);
       check("getWordsWithPrefix 'app': has 'app'",         java.util.Arrays.asList(appWords).contains("app"));
       check("getWordsWithPrefix 'app': has 'apple'",       java.util.Arrays.asList(appWords).contains("apple"));
       check("getWordsWithPrefix 'app': has 'application'", java.util.Arrays.asList(appWords).contains("application"));

       // prefix with 2 matches
       String[] baWords = t2.getWordsWithPrefix("ba");
       check("getWordsWithPrefix 'ba': count 2",            baWords != null && baWords.length == 2);
       check("getWordsWithPrefix 'ba': has 'bat'",          java.util.Arrays.asList(baWords).contains("bat"));
       check("getWordsWithPrefix 'ba': has 'ball'",         java.util.Arrays.asList(baWords).contains("ball"));

       // prefix that is a full word itself
       String[] appExact = t2.getWordsWithPrefix("apple");
       check("getWordsWithPrefix 'apple': count 1",         appExact != null && appExact.length == 1);
       check("getWordsWithPrefix 'apple': has 'apple'",     java.util.Arrays.asList(appExact).contains("apple"));

       // prefix with no matches
       String[] noWords = t2.getWordsWithPrefix("xyz");
       check("getWordsWithPrefix 'xyz': empty",             noWords != null && noWords.length == 0);

       // empty prefix returns all words
       String[] allWords = t2.getWordsWithPrefix("");
       check("getWordsWithPrefix '': count 5",              allWords != null && allWords.length == 5);

       // ── countWordsWithPrefix ─────────────────────────────────────────────
       check("countWordsWithPrefix 'app'  = 3", t2.countWordsWithPrefix("app")  == 3);
       check("countWordsWithPrefix 'appl' = 2", t2.countWordsWithPrefix("appl") == 2);
       check("countWordsWithPrefix 'ba'   = 2", t2.countWordsWithPrefix("ba")   == 2);
       check("countWordsWithPrefix 'bat'  = 1", t2.countWordsWithPrefix("bat")  == 1);
       check("countWordsWithPrefix 'xyz'  = 0", t2.countWordsWithPrefix("xyz")  == 0);
       check("countWordsWithPrefix ''     = 5", t2.countWordsWithPrefix("")     == 5);  // all words

       // ── wildCardSearch ───────────────────────────────────────────────────
       Trie w = new Trie(26);
       w.insert("apple"); w.insert("app"); w.insert("application"); w.insert("bat"); w.insert("ball");

       // single '.' in the middle
       check("wildcard 'app.e'     (true)",   w.wildCardSearch("app.e")); // matches 'apple'
       check("wildcard 'b.ll'      (true)",   w.wildCardSearch("b.ll"));  // matches 'ball'
       check("wildcard 'b.t'       (true)",   w.wildCardSearch("b.t"));   // matches 'bat'
       check("wildcard 'ba.'       (true)",   w.wildCardSearch("ba."));   // matches 'bat' ('ball' is 4 chars)

       // multiple '.' wildcards
       check("wildcard 'a..le'     (true)",   w.wildCardSearch("a..le")); // matches 'apple'
       check("wildcard '...'       (true)",   w.wildCardSearch("..."));   // matches 'app' and 'bat'
       check("wildcard '....'      (true)",   w.wildCardSearch("...."));  // matches 'ball'
       check("wildcard '.....'     (true)",   w.wildCardSearch(".....")); // matches 'apple'

       // wildcard that matches exact word
       check("wildcard '..p'       (true)",   w.wildCardSearch("..p"));   // matches 'app'

       // no match cases
       check("wildcard 'a..x'      (false)", !w.wildCardSearch("a..x"));  // no word ends in 'x' at length 4
       check("wildcard 'xyz'       (false)", !w.wildCardSearch("xyz"));   // no such word
       check("wildcard '......'    (false)", !w.wildCardSearch("......")); // no 6-char words inserted
    }
}
