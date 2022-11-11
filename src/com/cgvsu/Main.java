package com.cgvsu;


import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;



public class Main {

    public static void main(String[] args) throws IOException {

        String fileNameStr = "123.obj";
        Path fileName = Path.of(fileNameStr);

        String fileContent = Files.readString(fileName);

        System.out.println("Loading model ...");
        Model model = ObjReader.read(fileContent);
        model.recalculateNormals(fileContent);

        System.out.println("Vertices: " + model.vertices.size());
        System.out.println("Texture vertices: " + model.textureVertices.size());
        System.out.println("Normals: " + model.normals.size());
        System.out.println("Polygons: " + model.polygons.size());
    }
}
