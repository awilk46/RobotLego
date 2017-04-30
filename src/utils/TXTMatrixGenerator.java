/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.*;
import java.io.IOException;
import java.util.Random;
import matrix.Matrix;

/**
 *
 * @author Kalvador
 */
public class TXTMatrixGenerator {

    public static void generate(String path, int width, int height) throws IOException  {
        File file = new File(path+".txt");

        // creates the file
        file.createNewFile();

        // creates a FileWriter Object
        FileWriter writer = new FileWriter(file);

        Random r = new Random();

        // Writes the content to the file
        for (int i = 0; i < height; i++) {
            StringBuilder s = new StringBuilder();
            for (int j = 0; j < width; j++) {
                int x = r.nextInt(100);
                if(x < 2){
                    s.append("2");
                    if(j<(height-1)){
                        s.append(",");
                    }
                }
                else if (x < 40){
                    s.append("1");
                    if(j<(height-1)){
                        s.append(",");
                    }
                }
                else if(x < 100){
                    s.append("0");
                    if(j<(height-1)){
                        s.append(",");
                    }
                }
            }
            writer.write(s.toString()+"\r\n");
        }
        writer.flush();
        writer.close();
    }
    public static void main(String[] args) throws IOException {
        generate("matrix", 100,100);
    }
}