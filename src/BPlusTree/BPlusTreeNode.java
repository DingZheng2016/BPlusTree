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

    public int search(int value){


        if(this.keyNum < 100){
            int i;
            for(i = 0; i < this.keyNum; ++i) {
                if (this.keys.get(i) > value)
                    break;
            }
            return i;
        }

        int left = 0, right = this.keyNum, mid;
        while(true){
            mid = (left + right) / 2;
            if(this.keys.get(mid) > value){
                if(mid == 0 || this.keys.get(mid - 1) <= value)
                    return mid;
                right = mid;
            }else{
                if(mid == this.keyNum - 1)
                    return this.keyNum;
                left = mid + 1;
            }
        }
    }
}
