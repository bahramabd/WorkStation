import java.util.ArrayList;

public class CengTree
{
    public CengTreeNode root;
    // Any extra attributes...

    public CengTree(Integer order)
    {
        CengTreeNode.order = order;
        // TODO: Initialize the class
        root=new CengTreeNodeLeaf(null);

    }

    public void addBook(CengBook book) {
        // TODO: Insert Book to Tree

        CengTreeNode node = root;
        if (node == null) {
            root = new CengTreeNodeLeaf(null);
            ((CengTreeNodeLeaf) root).newBookIndex(book);
            return;
        }

        while (node.getType() == CengNodeType.Internal) {
            CengTreeNodeInternal internalNode = (CengTreeNodeInternal) node;
            int i;
            for (i = 0; i < internalNode.getKeys().size() && book.getBookID() >= internalNode.getKeys().get(i); i++) {}
            node = internalNode.getChildren().get(i);
        }
        CengTreeNodeLeaf l = (CengTreeNodeLeaf) node; l.newBookIndex(book);
        CengTreeNodeInternal pNode = null;
        if (l.bookCount()> 2*CengTreeNode.order) {
            pNode= split1(l);

            while (pNode.keyCount()>2*CengTreeNode.order) {
                pNode= split2(pNode);
            }

            if (pNode.getParent() == null) {
                root = pNode;
            } else {
                CengTreeNodeInternal grandparent = (CengTreeNodeInternal) pNode.getParent();
                if (grandparent != null && grandparent.keyCount() > 2 * CengTreeNode.order) {
                    pNode = split2(grandparent);
                    if (pNode.getParent() == null) {
                        root = pNode;
                    }
                }
            }
        }
    }


    private CengTreeNodeInternal split1(CengTreeNodeLeaf newNode){
        int mid = newNode.bookCount() / 2;
        Integer midID = newNode.bookKeyAtIndex(mid);
        CengTreeNodeInternal pNode = (CengTreeNodeInternal) newNode.getParent();

        CengTreeNodeLeaf left_node= new CengTreeNodeLeaf(pNode);
        left_node.getBooks().addAll(newNode.getBooks().subList(0, mid));

        CengTreeNodeLeaf right_node= new CengTreeNodeLeaf(pNode);
        right_node.getBooks().addAll(newNode.getBooks().subList(mid, newNode.bookCount()));
        if (pNode != null) {
            int a= pNode.getChildren().indexOf(newNode);
            pNode.getKeys().add(a, midID);
            pNode.setChildren(a, left_node);
            pNode.getChildren().add(a+ 1, right_node);

        } else {
            pNode = new CengTreeNodeInternal(null);
            left_node.setParent(pNode);
            right_node.setParent(pNode);
            pNode.getChildren().add(left_node);
            pNode.getChildren().add(right_node);
            pNode.getKeys().add(midID);

        }

        return pNode;
    }
    private CengTreeNodeInternal split2(CengTreeNodeInternal newNode){
        int a;
        int mid =  newNode.keyCount()/2;
        int midID = newNode.keyAtIndex(mid);
        CengTreeNodeInternal pNode = (CengTreeNodeInternal) newNode.getParent();
        CengTreeNode node;

        CengTreeNodeInternal left_node = new CengTreeNodeInternal(pNode);
        int i = 0;
        while (i < mid) {
            node = newNode.getChildren().get(i);
            node.setParent(left_node);
            left_node.getChildren().add(node);
            a= newNode.keyAtIndex(i);
            left_node.getKeys().add(a);
            i++;
        }
        CengTreeNodeInternal right_node = new CengTreeNodeInternal(pNode);
        int j= mid+1;
        while(j < newNode.keyCount()){
            node= newNode.getChildren().get(j);
            node.setParent(right_node);
            right_node.getChildren().add(j - mid-1, node);
            a= newNode.keyAtIndex(j);
            right_node.getKeys().add(j- mid-1, a);
            j++;
        }
        if (pNode!= null) {
            int b= pNode.getChildren().indexOf(newNode);
            pNode.getKeys().add(b, midID);
            pNode.setChildren(b, left_node);
            pNode.getChildren().add(b+ 1, right_node);

        } else {
            pNode= new CengTreeNodeInternal(null);
            left_node.setParent(pNode);
            right_node.setParent(pNode);
            pNode.getChildren().add(left_node);
            pNode.getChildren().add(right_node);
            pNode.getKeys().add(midID);

        }
        if (newNode.keyCount()<newNode.getChildren().size()) {
            node = newNode.getChildren().get(newNode.keyCount());
            node.setParent(right_node);
            right_node.getChildren().add(newNode.keyCount() - mid - 1, node);
        };

        if (mid<newNode.getChildren().size()) {
            node= newNode.getChildren().get(mid);
            node.setParent(left_node);
            left_node.getChildren().add(mid, node);
        }
        return pNode;
    }
    public ArrayList<CengTreeNode> searchBook(Integer key) {
        CengBook book = null;

        ArrayList<CengTreeNode> output = new ArrayList<>();
        CengTreeNode node = this.root;

        while (node != null) {
            output.add(node);
            if (node.type == CengNodeType.Internal) {
                CengTreeNodeInternal ni = (CengTreeNodeInternal) node;
                int i;
                for (i = 0; i < ni.keyCount(); i++) {
                    if (key < ni.keyAtIndex(i)) {
                        node = ni.getChildren().get(i);
                        break;
                    }
                }
                if (i == ni.keyCount()) {
                    node = ni.getChildren().get(i);
                }
            } else if (node.type == CengNodeType.Leaf) {
                CengTreeNodeLeaf nl = (CengTreeNodeLeaf) node;
                for (int i = 0; i < nl.bookCount(); i++) {
                    if (nl.bookKeyAtIndex(i).equals(key)) {
                        book = nl.getBook(i);
                        break;
                    }
                }
                break;
            }
        }

        if (book == null) {
            System.out.println("Could not find " + key + ".");
            return null;
        }

        String res = "";
        CengTreeNodeInternal node1;

        for (int i = 0; i < output.size(); i++) {
            CengTreeNode currentNode = output.get(i);

            if (currentNode.type == CengNodeType.Internal) {
                node1 = (CengTreeNodeInternal) currentNode;
                //for(int a=0; a<i; a++)indentString += "\t"; //noooo change it
                res = "\t".repeat(i);
                System.out.println(res + "<index>");
                for (int j = 0; j < node1.keyCount(); j++) {
                    System.out.println(res + node1.keyAtIndex(j));
                }
                System.out.println(res + "</index>");
            }
        }

        //for(int a=0; a< output.size(); a++)indentString += "\t"; // does not work :((
        res = "\t".repeat(output.size()-1);
        System.out.println(res + "<record>" + book.fullName() + "</record>");

        return output;
    }


    public void printTree()
    {
        // TODO: Print the whole tree to console
        traverse(root, 0);

    }
    private void traverse(CengTreeNode start_node, Integer node_level){
        String indent_string = "\t".repeat(node_level);

        if(start_node.getType() == CengNodeType.Leaf){

            CengTreeNodeLeaf tmp = ((CengTreeNodeLeaf) start_node);
            System.out.println(indent_string + "<data>");

            for(int i = 0; i < tmp.bookCount(); i++) {
                System.out.println(indent_string + "<record>" + tmp.getBook(i).fullName() + "</record>");
            }

            System.out.println(indent_string + "</data>");

        }else{
            CengTreeNodeInternal tmp = ((CengTreeNodeInternal) start_node);
            Integer key_count = tmp.keyCount();

            System.out.println(indent_string + "<index>");
            for(int i = 0; i < key_count; i++)
                System.out.println(indent_string + tmp.keyAtIndex(i));
            System.out.println(indent_string + "</index>");

            for(int i = 0; i <= key_count; i++) {
                traverse(tmp.getChildren().get(i), node_level+1);
            }

        }
    }



       }
