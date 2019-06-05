package BPlusTree;

public class BPlusTreeLeafNode extends BPlusTreeNode {

    public BPlusTreeLeafNode(){
        super(true);
    }

    public BPlusTreeNode search(int value){
        System.out.println("search in leaf node");
        return null;
    }

    public BPlusTreeNode insert(int value){

        int i = 0;

        for(i = 0; i < this.keyNum; ++i){
            if(value < this.keys.get(i))
                break;
        }

        this.keys.add(i, value);
        ++this.keyNum;

        if(this.keyNum == BPlusTree.M)
            return this.split();

        return null;
    }

    public BPlusTreeNode delete(int value){
        int i;

        for(i = 0; i < this.keyNum; ++i){
            if(value == this.keys.get(i))
                break;
        }

        assert i != this.keyNum;

        this.keys.remove(i);
        --this.keyNum;

        if(this.keyNum < (int)Math.ceil((BPlusTree.M - 1) / 2.0) - 1)
            return this.combine();

        return null;
    }


    public BPlusTreeNode split(){
        assert this.keyNum == BPlusTree.M;

        BPlusTreeLeafNode newNode = new BPlusTreeLeafNode();
        int m = (int)Math.ceil(BPlusTree.M / 2.0);
        for(int i = m; i < this.keyNum; ++i){
            newNode.keys.add(this.keys.get(i));
        }

        newNode.keyNum = this.keyNum - m;

        for(int i = m; i < this.keyNum; ++i){
            this.keys.remove(m);
        }

        this.keyNum = m;

        newNode.parent = this.parent;
        newNode.leftSibling = this;
        newNode.rightSibling = this.rightSibling;
        if(this.rightSibling != null)
            this.rightSibling.leftSibling = newNode;
        this.rightSibling = newNode;

        if(this.parent == null){
            BPlusTreeInnerNode newRoot = new BPlusTreeInnerNode();
            newRoot.keys.add(newNode.keys.get(0));
            newRoot.keyNum = 1;
            newRoot.pointers.add(this);
            newRoot.pointers.add(newNode);
            this.parent = newRoot;
            newNode.parent = newRoot;
            return newRoot;
        }

        return ((BPlusTreeInnerNode)this.parent).insert(newNode.keys.get(0), newNode);
    }

    public BPlusTreeNode combine(){
        if(this.parent == null)
            return null;

        int i;

        for(i = 0; i < this.parent.keyNum + 1; ++i) {
            if (this.parent.pointers.get(i) == this)
                break;
        }

        assert i != this.parent.keyNum + 1;

        if(i - 1 >= 0 && this.parent.pointers.get(i - 1).keyNum > (int)Math.ceil((BPlusTree.M - 1) / 2.0) - 1){
            BPlusTreeLeafNode left = (BPlusTreeLeafNode) this.parent.pointers.get(i - 1);

            this.keys.add(0, left.keys.get(left.keyNum - 1));
            ++this.keyNum;

            this.parent.keys.set(i - 1, left.keys.get(left.keyNum - 1));

            left.keys.remove(left.keyNum - 1);
            --left.keyNum;
            return null;

        }else if(i + 1 < this.parent.keyNum + 1 && this.parent.pointers.get(i + 1).keyNum > (int)Math.ceil((BPlusTree.M - 1) / 2.0) - 1){
            BPlusTreeLeafNode right = (BPlusTreeLeafNode) this.parent.pointers.get(i + 1);

            this.keys.add(this.keyNum, right.keys.get(0));
            ++this.keyNum;

            this.parent.keys.set(i, right.keys.get(1));

            right.keys.remove(0);
            --right.keyNum;

            return null;
        }

        if(i - 1 >= 0){
            BPlusTreeLeafNode left = (BPlusTreeLeafNode) this.parent.pointers.get(i - 1);
            for(int j = 0; j < this.keyNum; ++j){
                left.keys.add(this.keys.get(j));
            }

            left.keyNum += this.keyNum;
            left.rightSibling = this.rightSibling;
            if(this.rightSibling != null)
                this.rightSibling.leftSibling = left;

            return ((BPlusTreeInnerNode)this.parent).delete(i - 1, i);

        }

        BPlusTreeLeafNode right = (BPlusTreeLeafNode) this.parent.pointers.get(i + 1);
        for(int j = 0; j < right.keyNum; ++j){
            this.keys.add(right.keys.get(j));
        }

        this.keyNum += right.keyNum;
        this.rightSibling = right.rightSibling;
        if(right.rightSibling != null)
            right.rightSibling.leftSibling = this;

        return ((BPlusTreeInnerNode)this.parent).delete(i, i + 1);
    }
}
