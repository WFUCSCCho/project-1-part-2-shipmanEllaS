/**********************************************************************************************
 * @file : Parser.java
 * @description : Parser class to process "villagers.csv" via "input.txt" with methods insert(),
 *                search(), and remove(). Results are output in "result.txt".
 * @author : Ella Shipman
 * @date : September 25, 2025
 * @acknowledgement : Jessica Li's "Animal Crossing New Horizons Catalog", "villagers.csv" file.
 * https://www.kaggle.com/datasets/jessicali9530/animal-crossing-new-horizons-nookplaza-dataset.
 *********************************************************************************************/

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {

    //Create a BST tree of Integer type
    private BST<Villager> mybst = new BST<>();
    private ArrayList<Villager> removedVillagers = new ArrayList<>();

    //Parametrized Constructor
    public Parser(String filename) throws FileNotFoundException {
        //Establish FileWriter and call process()
        FileWriter writer = null;
        //FileOutputStream resultFile = null;
        try {       //assume result.txt will be manually reset
            writer = new FileWriter("src/result.txt", true);
        }
        catch (IOException e) { System.out.println("FileNotFound!"); }
        fill_BST(new File("src/villagers.csv"), writer);
        process(new File(filename), writer);
    }

    //Fully fill BST from "villagers.csv"
    private void fill_BST(File input, FileWriter writer) throws FileNotFoundException {
        Scanner fileReader = new Scanner(input);
        String currLine = fileReader.nextLine();        //skipping over the column names in villagers.csv
        while(fileReader.hasNextLine()) {
            currLine = fileReader.nextLine();
            String[] villInfo = null;
            if (!currLine.isEmpty()) {
                villInfo = currLine.split(",");
            }
            String[] shortInfo = new String[4];
            shortInfo[0] = villInfo[0];     //name
            shortInfo[1] = villInfo[3];     //personality
            shortInfo[2] = villInfo[4];     //hobby
            shortInfo[3] = villInfo[7];     //favorite song

            Villager v = null;
            try {
                v = new Villager(shortInfo[0], shortInfo[1], shortInfo[2], shortInfo[3]);
            } catch (ArrayIndexOutOfBoundsException e) {}

            if (shortInfo[1] != null) {
                mybst.insert(mybst.getRoot(), new Node<>(v));
                //writeToFile("insert" + " " + shortInfo[0], writer);
            } else {
                //writeToFile("insert failed", writer);
            }
            //operate_BST(shortInfo, writer);
        }
        fileReader.close();
    }

    /*  --------------------------------------------------------------------------------------
     *   process - Removes redundant spaces for each input command
     *   File inpyt : the input filepath
     *   FileWriter writer : the output fileWriter
     */
    public void process(File input, FileWriter writer) throws FileNotFoundException {
        Scanner fileReader = new Scanner(input);
        String currLine;
        while(fileReader.hasNextLine()) {
            currLine = fileReader.nextLine();
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
        }
        try {writer.flush(); writer.close(); fileReader.close(); }
        catch (IOException e)  { System.out.println("Cannot close files"); }
    }

    /*  --------------------------------------------------------------------------------------
     *   operate_BST - Determine the incoming command and operate on the BST
     *   String command : the input command
     *   FileWriter writer : the output fileWriter
     */
    public void operate_BST(String[] command, FileWriter writer) {
        Villager v = null;

        switch (command[0]) {
            case "insert":       //add value to BST
                if (command[1] != null) {
                    //check for removed previously, add back to list if found
                    for (int i = 0; i < removedVillagers.size(); i++) {
                        if (removedVillagers.get(i).getName().equals(command[1])) {     //find name in list
                            mybst.insert(mybst.getRoot(), new Node<>(removedVillagers.get(i)));
                            removedVillagers.remove(i);
                            writeToFile("insert" + " " + command[1], writer);
                        }
                    }
                } else {
                    writeToFile("insert failed", writer);
                }
                break;
            case "search":      //searches BST for a given value
                if (command[1] != null) {
                    v = new Villager(); v.setName(command[1]);
                    Node<Villager> test = mybst.search(new Node<>(v));
                    if ( test != null) {
                        writeToFile("found" + " " + test.getValue().getName(), writer);
                    } else {
                        writeToFile("search failed", writer);
                    }
                } else {
                    writeToFile("search failed", writer);
                }
                break;
            case "remove":      //remove a given value from a BST
                if (command[1] != null) {
                    v = new Villager(); v.setName(command[1]);
                    Node<Villager> test = mybst.remove(new Node<>(v));
                    if (test != null) {
                        writeToFile("removed" + " " + test.getValue().getName(), writer);
                        removedVillagers.add(test.getValue());
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
