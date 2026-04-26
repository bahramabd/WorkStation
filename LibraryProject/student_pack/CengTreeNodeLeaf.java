import java.util.ArrayList;

public class CengTreeNodeLeaf extends CengTreeNode
{
    private ArrayList<CengBook> books;
    // TODO: Any extra attributes



    public CengTreeNodeLeaf(CengTreeNode parent)
    {
        super(parent);
        this.type = CengNodeType.Leaf;
        books = new ArrayList<CengBook>();
        // TODO: Extra initializations
    }

    // GUI Methods - Do not modify
    public int bookCount()
    {
        return books.size();
    }
    public Integer bookKeyAtIndex(Integer index)
    {
        if(index >= this.bookCount()) {
            return -1;
        } else {
            CengBook book = this.books.get(index);

            return book.getBookID();
        }
    }

    // Extra Functions

    public  CengTreeNode findPath(Integer key){return null;}

    public  Integer keyCount(){ return 0;};
    public ArrayList<CengBook> getBooks() {
        return this.books;
    }
    public CengBook getBook(Integer position){
        return this.books.get(position);
    }
    public void newBookIndex(CengBook book){
        int i=0;
        for(i=0;i < this.books.size() &&  this.books.get(i).getBookID()< book.getBookID();i++) ;
        this.books.add(i, book);
    }


}




