package BPlusTree;

public class BPlusTree {

    private BPlusTreeNode root;
    public static int M = 30;

    public BPlusTree(){
        this.root = new BPlusTreeLeafNode();
    }

    public void insert(int value){

        BPlusTreeLeafNode leaf = this.findLeafNode(value);
        BPlusTreeNode n = leaf.insert(value);
        if(n != null) {
            this.root = n;
            this.root.parent = null;
        }
    }

    public void delete(int value){
        BPlusTreeLeafNode leaf = this.findLeafNode(value);
        BPlusTreeNode n = leaf.delete(value);
        if(n != null) {
            this.root = n;
            this.root.parent = null;
        }
    }

    public void update(int oldvalue, int newvalue){
        this.delete(oldvalue);
        this.insert(newvalue);
    }

    public void print(){
        System.out.println(this.root.keys);
        printNode(this.root);
        System.out.println();
    }

    private void printNode(BPlusTreeNode node){
        if(node.isLeafNode)
            return;
        for(int i = 0; i < node.keyNum + 1; ++i)
            System.out.print(node.pointers.get(i).keys);
        System.out.println();

        for(int i = 0; i < node.keyNum + 1; ++i)
            printNode(node.pointers.get(i));
    }

    private BPlusTreeLeafNode findLeafNode(int value) {

        BPlusTreeNode node = this.root;
        while (!node.isLeafNode) {
            node = node.search(value);
        }
        return (BPlusTreeLeafNode)node;
    }

    public BPlusTreeLeafNode getMostLeftLeafNode(){
        BPlusTreeNode node = this.root;
        while(!node.isLeafNode){
            node = node.pointers.get(0);
        }
        return (BPlusTreeLeafNode) node;
    }

    public BPlusTreeLeafNode getMostRightLeafNode(){
        BPlusTreeNode node = this.root;
        while(!node.isLeafNode){
            node = node.pointers.get(node.keyNum);
        }
        return (BPlusTreeLeafNode) node;
    }
}
