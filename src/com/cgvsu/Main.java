package com.cgvsu;

import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {

        String fileNameStr = "123.obj";
        Path fileName = Path.of(fileNameStr);

        String fileContent = Files.readString(fileName);

        System.out.println("Loading model ...");
        Model model = ObjReader.read(fileContent);

        try (PrintWriter out = new PrintWriter(fileNameStr)) {
            out.println(rewriteFile(fileContent, model));
        }

        System.out.println("Vertices: " + model.vertices.size());
        System.out.println("Texture vertices: " + model.textureVertices.size());
        System.out.println("Normals: " + model.normals.size());
        System.out.println("Polygons: " + model.polygons.size());
    }

    public static StringBuilder rewriteFile(String fileContent, Model model) throws IOException {


        Scanner scanner = new Scanner(fileContent);
        StringBuilder result = new StringBuilder();
        String line;

        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if(line.startsWith("vn")){
                continue;
            }
            result.append(line).append("\n");
        }

        for (int i = 0; i < model.normals.size(); i++) {
            Vector3f currNormal = model.normals.get(i);
           result.append("\nvn ").append(currNormal.getX()).append(" ").append(currNormal.getY()).append(" ").append(currNormal.getZ());
        }
        return result;
    }
}
