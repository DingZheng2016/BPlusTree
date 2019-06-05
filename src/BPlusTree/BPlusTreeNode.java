package BPlusTree;

import java.util.ArrayList;

public abstract class BPlusTreeNode {

    public boolean isLeafNode;
    public ArrayList<Integer> keys;
    public int keyNum;
    public ArrayList<BPlusTreeNode> pointers;
    public BPlusTreeNode parent;
    public BPlusTreeNode leftSibling;
    public BPlusTreeNode rightSibling;


    public BPlusTreeNode(boolean isLeafNode){
        this.isLeafNode = isLeafNode;
        this.keyNum = 0;
        this.keys = new ArrayList<>();
        this.pointers = new ArrayList<BPlusTreeNode>();
        this.parent = null;
        this.leftSibling = null;
        this.rightSibling = null;
    }

    abstract public BPlusTreeNode split();

    abstract public BPlusTreeNode search(int value);
}
