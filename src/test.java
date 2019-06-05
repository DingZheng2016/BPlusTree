import BPlusTree.BPlusTree;
import BPlusTree.BPlusTreeLeafNode;

import java.util.ArrayList;
import java.util.Collections;
public class test {

    //enum Operator {Insert, Delete, Update};

    public static void main(String[] args){
        BPlusTree bpt = new BPlusTree();
        /*
        Scanner in = new Scanner(System.in);
        int op, value;


        while(true){
            op = in.nextInt();

            switch (op){
                case 1:
                    value = in.nextInt();
                    bpt.insert(value);
                    break;
                case 2:
                    value = in.nextInt();
                    bpt.delete(value);
                    break;

            }
            bpt.print();
        }
        */

        long start = System.nanoTime();

        int tot = 1000000;

        ArrayList<Integer> arr = new ArrayList<>();

        for(int i = 1; i <= tot; ++i){
            arr.add(i);
        }

        Collections.shuffle(arr);
//        System.out.println(arr);
//        arr.add(6);arr.add(2);arr.add(3);arr.add(5);arr.add(4);arr.add(1);


        for(int i = 0; i < arr.size(); ++i) {
            bpt.insert(arr.get(i));
        }

        BPlusTreeLeafNode node = bpt.getMostLeftLeafNode();
        int k = 1;

        while(node != null){
            for(int i = 0; i < node.keyNum; ++i)
                if(node.keys.get(i) != k++){
                    System.out.println("error " + k);
                }
            node = (BPlusTreeLeafNode) node.rightSibling;
        }

        if(k == tot + 1){
            System.out.println("leaf left success");
        }else{
            System.out.println("error");
        }

        node = bpt.getMostRightLeafNode();
        k = tot;

        while(node != null){
            for(int i = node.keyNum - 1; i >= 0; --i)
                if(node.keys.get(i) != k--){
                    System.out.println("error " + k);
                }
            node = (BPlusTreeLeafNode) node.leftSibling;
        }

        if(k == 0){
            System.out.println("leaf right success");
        }else{
            System.out.println("error");
        }


        Collections.shuffle(arr);



        for(int i = 0; i < arr.size(); ++i){
            bpt.update(arr.get(i), arr.get(i) + tot);
        }

        node = bpt.getMostLeftLeafNode();
        k = tot + 1;

        while(node != null){
            for(int i = 0; i < node.keyNum; ++i)
                if(node.keys.get(i) != k++){
                    System.out.println("error " + k);
                }
            node = (BPlusTreeLeafNode) node.rightSibling;
        }

        if(k == tot + tot + 1){
            System.out.println("leaf left success");
        }else{
            System.out.println("error");
        }

        node = bpt.getMostRightLeafNode();
        k = tot + tot;

        while(node != null){
            for(int i = node.keyNum - 1; i >= 0; --i)
                if(node.keys.get(i) != k--){
                    System.out.println("error " + k);
                }
            node = (BPlusTreeLeafNode) node.leftSibling;
        }

        if(k == tot){
            System.out.println("leaf right success");
        }else{
            System.out.println("error");
        }

        Collections.shuffle(arr);


        for(int i = 0; i < arr.size(); ++i){
            bpt.delete(arr.get(i) + tot);
        }

        long end = System.nanoTime();

        System.out.println((end-start)/ 1000000000.0);



    }
}
