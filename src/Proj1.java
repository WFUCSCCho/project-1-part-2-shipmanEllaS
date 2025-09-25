/**********************************************************************************************
 * @file : Proj1.java
 * @description : Main file to execute the sorting of Animal Crossing: New Horizons villagers,
 *                based on my personal opinion of their favorite song and other arbitrary
 *                factors. Input file is a command line argument and output is logged
 *                in "result.txt.".
 * @author : Ella Shipman
 * @date : September 25, 2025
 * @acknowledgement : Jessica Li's "Animal Crossing New Horizons Catalog", "villagers.csv" file.
 * https://www.kaggle.com/datasets/jessicali9530/animal-crossing-new-horizons-nookplaza-dataset.
 *********************************************************************************************/

import java.io.FileNotFoundException;

public class Proj1 {
    public static void main(String[] args) throws FileNotFoundException{
        if(args.length != 1){
            System.err.println("Argument count is invalid: " + args.length);
            System.exit(0);
        }
        new Parser(args[0]);
    }
}