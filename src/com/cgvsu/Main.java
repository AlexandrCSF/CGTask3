package com.cgvsu;


import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {

        String fileNameStr = "123.obj";
        Path fileName = Path.of(fileNameStr);

        String fileContent = Files.readString(fileName);

        String adjusted = fileContent.replace(fileContent.substring(getIndex(fileContent,'#'),getIndex(fileContent, '\n')),"");
         adjusted = adjusted.replaceAll("(?m)^[ \t]*\r?\n", "");


        System.out.println("Loading model ...");
        Model model = ObjReader.read(adjusted);
        model.recalculateNormals(adjusted);

        System.out.println("Vertices: " + model.vertices.size());
        System.out.println("Texture vertices: " + model.textureVertices.size());
        System.out.println("Normals: " + model.normals.size());
        System.out.println("Polygons: " + model.polygons.size());
    }

    public static int getIndex(String fileContent, char character){
        for (int i = 0; i < fileContent.toCharArray().length; i++) {
            if(character == fileContent.charAt(i)){
                return i;
            }
        }
        return -1;
    }
}
