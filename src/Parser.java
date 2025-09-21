import java.io.*;
import java.util.Scanner;

public class Parser {

    //Create a BST tree of Integer type
    private BST<Villager> mybst = new BST<>();

    //Parametrized Constructor
    public Parser(String filename) throws FileNotFoundException {
        //Establish FileWriter and call process()
        FileWriter writer = null;
        //FileOutputStream resultFile = null;
        try {       //assume result.txt will be manually reset
            writer = new FileWriter("src/result.txt", true);
        }
        catch (IOException e) { System.out.println("FileNotFound!"); }
        process(new File(filename), writer);
    }

    /*  --------------------------------------------------------------------------------------
     *   process - Removes redundant spaces for each input command
     *   File inpyt : the input filepath
     *   FileWriter writer : the output fileWriter
     */
    public void process(File input, FileWriter writer) throws FileNotFoundException {
        Scanner fileReader = new Scanner(input);
        String currLine = fileReader.nextLine();        //skipping over the column names in villagers.csv
        while(fileReader.hasNextLine()) {
            currLine = fileReader.nextLine();
            /*
            if (!currLine.isEmpty()) {                              //replacing unnecessary spaces
                while (currLine.contains("  ")) {
                    currLine = currLine.replace("  ", " ");
                }
                if (currLine.charAt(0) == ' ') {
                    currLine = currLine.substring(1);
                }
                if (currLine.charAt(currLine.length() - 1) == ' ') {
                    currLine = currLine.substring(0, currLine.length() - 1);
                }
                operate_BST(currLine.split(" "), writer);      //call operate_BST method
            }

             */
            String[] villInfo = null;
            if (!currLine.isEmpty()) {
                villInfo = currLine.split(",");
            }
            String[] shortInfo = new String[5];
            shortInfo[0] = "insert";
            shortInfo[1] = villInfo[0];     //name
            shortInfo[2] = villInfo[3];     //personality
            shortInfo[3] = villInfo[4];     //hobby
            shortInfo[4] = villInfo[7];     //favorite song
            operate_BST(shortInfo, writer);
        }
        String[] lastCommand = {"print"};
        operate_BST(lastCommand, writer);
        try {writer.flush(); writer.close(); }
        catch (IOException e)  { System.out.println("Cannot close fileWriter"); }
    }




    /*  --------------------------------------------------------------------------------------
     *   operate_BST - Determine the incoming command and operate on the BST
     *   String command : the input command
     *   FileWriter writer : the output fileWriter
     */
    public void operate_BST(String[] command, FileWriter writer) {
        Villager v = null;
        try {
            v = new Villager(command[1], command[2], command[3], command[4]);
        } catch (ArrayIndexOutOfBoundsException e) {}
        switch (command[0]) {
            case "insert":       //add value to BST
                if (command[1] != null) {
                    mybst.insert(mybst.getRoot(), new Node<>(v));
                    writeToFile("insert" + " " + command[1], writer);
                } else {
                    writeToFile("Invalid Command - insert", writer);
                }
                break;
            case "search":      //searches BST for a given value
                if (command[1] != null) {
                    if (mybst.search(new Node<>(v)) != null) {
                        writeToFile("found" + " " + command[1], writer);
                    } else {
                        writeToFile("search failed", writer);
                    }
                } else {
                    writeToFile("search failed", writer);
                }
                break;
            case "remove":      //remove a given value from a BST
                if (command[1] != null) {
                    Node<Villager> test = mybst.remove(new Node<>(v));
                    if (test != null) {
                        writeToFile("removed" + " " + test.getValue().getName(), writer);
                    } else {
                        writeToFile("remove failed", writer);
                    }
                } else {
                    writeToFile("Invalid Command", writer);
                }
                break;
            case "print":
                mybst.ascendingOrderToFile(mybst.getRoot(), writer);
                writeToFile("", writer);
                break;
            default:            // default case for Invalid Command
                writeToFile("Invalid Command", writer);
                break;
        }
    }

    /*  --------------------------------------------------------------------------------------
     *   writeToFile - Writes a given string to an output file
     *   File inpyt : the input filepath
     *   FileWriter writer : the output fileWriter
     */
    public void writeToFile(String content, FileWriter writer) {
        try {
            writer.write(content + "\n");
        } catch (IOException e) {
            System.out.println("File not found!");
        }
    }
}
