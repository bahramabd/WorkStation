import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;




public class CengTreeParser
{
    public static ArrayList<CengBook> parseBooksFromFile(String filename)
    {
        ArrayList<CengBook> bookList = new ArrayList<CengBook>();

        // You need to parse the input file in order to use GUI tables.
        // TODO: Parse the input file, and convert them into CengBooks

        try {
            Scanner sc = new Scanner(new File(filename));
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                String[] args=line.split("\\|");

                bookList.add(new CengBook(Integer.parseInt(args[0]),args[1], args[2], args[3]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bookList;
    }

    public static void startParsingCommandLine() throws IOException
    {
        // TODO: Start listening and parsing command line -System.in-.
        // There are 4 commands:
        // 1) quit : End the app, gracefully. Print nothing, call nothing, just break off your command line loop.
        // 2) add : Parse and create the book, and call CengBookRunner.addBook(newlyCreatedBook).
        // 3) search : Parse the bookID, and call CengBookRunner.searchBook(bookID).
        // 4) print : Print the whole tree, call CengBookRunner.printTree().

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] args = line.split("[|]");
            String cmd = args[0];

            if (cmd.equalsIgnoreCase("quit")) {break; }
            else if (cmd.equalsIgnoreCase("add")) {
                CengBook cengVideo = new CengBook(Integer.parseInt(args[1]),args[2],args[3],args[4]);
                CengBookRunner.addBook(cengVideo);
            } else if (cmd.equalsIgnoreCase("search")) {
                CengBookRunner.searchBook(Integer.parseInt(args[1]));
            } else if (cmd.equalsIgnoreCase("print")) {
                CengBookRunner.printTree();
            }
        }

        // Commands (quit, add, search, print) are case-insensitive.
    }
}
