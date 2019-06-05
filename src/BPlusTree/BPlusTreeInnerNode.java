package BPlusTree;

public class BPlusTreeInnerNode extends BPlusTreeNode {



    public BPlusTreeInnerNode(){
        super(false);
    }

    public BPlusTreeNode insert(int value, BPlusTreeNode node){

        int i = this.search(value);

        this.keys.add(i, value);
        this.pointers.add(i + 1, node);
        ++this.keyNum;

        if(this.keyNum == BPlusTree.M)
            return this.split();
        return null;
    }

    public BPlusTreeNode split(){
        assert this.keyNum == BPlusTree.M;

        BPlusTreeInnerNode newNode = new BPlusTreeInnerNode();
        int m = (int)Math.ceil(BPlusTree.M / 2.0);
        int key = this.keys.get(m);

        for(int i = m + 1; i < this.keyNum; ++i){
            newNode.keys.add(this.keys.get(i));
            newNode.pointers.add(this.pointers.get(i));
            this.pointers.get(i).parent = newNode;
        }
        newNode.pointers.add(this.pointers.get(this.keyNum));
        this.pointers.get(this.keyNum).parent = newNode;
        newNode.keyNum = this.keyNum - m - 1;

        for(int i = m; i < this.keyNum; ++i){
            this.keys.remove(m);
            this.pointers.remove(m + 1);
        }
        this.keyNum = m;

        newNode.parent = this.parent;

        if(this.parent == null){
            BPlusTreeInnerNode newRoot = new BPlusTreeInnerNode();
            newRoot.keys.add(key);
            newRoot.keyNum = 1;
            newRoot.pointers.add(this);
            newRoot.pointers.add(newNode);
            this.parent = newRoot;
            newNode.parent = newRoot;
            return newRoot;
        }

        return ((BPlusTreeInnerNode)this.parent).insert(key, newNode);
    }

    public BPlusTreeNode delete(int index, int pindex){

        this.keys.remove(index);
        this.pointers.remove(pindex);
        --this.keyNum;

        if(this.parent == null && this.keyNum == 0)
            return this.pointers.get(0);

        if(this.keyNum < (int)Math.ceil((BPlusTree.M - 1) / 2.0) - 1)
            return this.combine();
        return null;
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
            BPlusTreeInnerNode left = (BPlusTreeInnerNode) this.parent.pointers.get(i - 1);

            this.keys.add(0, this.parent.keys.get(i-1));
            this.pointers.add(0, left.pointers.get(left.keyNum));
            left.pointers.get(left.keyNum).parent = this;
            ++this.keyNum;

            this.parent.keys.set(i - 1, left.keys.get(left.keyNum - 1));

            left.keys.remove(left.keyNum - 1);
            left.pointers.remove(left.keyNum);
            --left.keyNum;
            return null;

        }else if(i + 1 < this.parent.keyNum + 1 && this.parent.pointers.get(i + 1).keyNum > (int)Math.ceil((BPlusTree.M - 1) / 2.0) - 1){
            BPlusTreeInnerNode right = (BPlusTreeInnerNode) this.parent.pointers.get(i + 1);

            this.keys.add(this.keyNum, this.parent.keys.get(i));
            this.pointers.add(this.keyNum + 1, right.pointers.get(0));
            right.pointers.get(0).parent = this;
            ++this.keyNum;

            this.parent.keys.set(i, right.keys.get(0));

            right.keys.remove(0);
            right.pointers.remove(0);
            --right.keyNum;

            return null;
        }

        if(i - 1 >= 0){
            BPlusTreeInnerNode left = (BPlusTreeInnerNode) this.parent.pointers.get(i - 1);

            this.keys.add(0, this.parent.keys.get(i - 1));

            for(int j = 0; j < left.keyNum; ++j){
                this.keys.add(j, left.keys.get(j));
                this.pointers.add(j, left.pointers.get(j));
                left.pointers.get(j).parent = this;
            }

            this.pointers.add(left.keyNum, left.pointers.get(left.keyNum));
            left.pointers.get(left.keyNum).parent = this;

            this.keyNum += 1 + left.keyNum;

            //delete left

            return ((BPlusTreeInnerNode)this.parent).delete(i - 1, i - 1);

        }

        BPlusTreeInnerNode right = (BPlusTreeInnerNode) this.parent.pointers.get(i + 1);

        this.keys.add(this.parent.keys.get(i));

        for(int j = 0; j < right.keyNum; ++j){
            this.keys.add(right.keys.get(j));
            this.pointers.add(right.pointers.get(j));
            right.pointers.get(j).parent = this;
        }

        this.pointers.add(right.pointers.get(right.keyNum));
        right.pointers.get(right.keyNum).parent = this;

        this.keyNum += 1 + right.keyNum;

        return ((BPlusTreeInnerNode)this.parent).delete(i, i + 1);
    }
}
