import java.util.ArrayList;

public class CengTreeNodeInternal extends CengTreeNode
{
    private ArrayList<Integer> keys;
    private ArrayList<CengTreeNode> children;

    public CengTreeNodeInternal(CengTreeNode parent)
    {
        super(parent);
        this.type = CengNodeType.Internal;
        this.keys = new ArrayList<Integer>();
        this.children = new ArrayList<CengTreeNode>();
        // TODO: Extra initializations, if necessary.
    }

    // GUI Methods - Do not modify
    public ArrayList<CengTreeNode> getAllChildren()
    {
        return this.children;
    }
    public Integer keyCount()
    {
        return this.keys.size();
    }
    public Integer keyAtIndex(Integer index)
    {
        if(index >= this.keyCount() || index < 0)
        {
            return -1;
        }
        else
        {
            return this.keys.get(index);
        }
    }

    // Extra Functions
    public ArrayList<CengTreeNode> getChildren() {
        return this.children;
    }
    public void setChildren(int position, CengTreeNode child) {
        this.children.set(position, child);
    }
    public void setChildren(ArrayList<CengTreeNode> children) {
        this.children=children;
    }
    public ArrayList<Integer> getKeys() {
        return this.keys;
    }
    public void setKeys(ArrayList<Integer> keys) {
        this.keys = keys;
    }
    public void setKeys(int position, Integer key) {
        this.keys.set(position,key);
    }



}



