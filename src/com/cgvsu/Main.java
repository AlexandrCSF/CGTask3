package com.cgvsu;


import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;


public class Main {

    public static void main(String[] args) throws IOException {

        String fileNameStr = "../ObjModels/Faceform/WrapHead.obj";
        Path fileName = Path.of(fileNameStr);

        String fileContent = Files.readString(fileName);

        System.out.println("Loading model ...");

        Model model = ObjReader.read(fileContent);
        model.recalculateNormals();

        System.out.println("Vertices: " + model.vertices.size());
        System.out.println("Texture vertices: " + model.textureVertices.size());
        System.out.println("Normals: " + model.normals.size());
        System.out.println("Polygons: " + model.polygons.size());
    }

//    public static HashMap<Integer,Integer> getIndex(String fileContent, char character){
//        HashMap<Integer,Integer> result = new HashMap<>();
//        for (int i = 0; i < fileContent.toCharArray().length; i++) {
//            if(character == fileContent.charAt(i)){
//                result.put(i,0);
//                for (int j = i; j < fileContent.toCharArray().length; j++) {
//                    if(fileContent.toCharArray()[j] == '\n'){
//                        result.put(i,j);
//                        break;
//                    }
//                }
//            }
//        }
//        return result;
//    }
//
//    public static String adjust(String fileContent){
//        int size = getIndex(fileContent,'#').size();
//        String adjusted = fileContent;
//        for (int i = 0; i < size; i++) {
//
//            int currToken = getIndex(fileContent,'#').get((Integer) getIndex(fileContent,'#').keySet().toArray()[i]);
//
//            adjusted = adjusted.replace(fileContent.substring((Integer) getIndex(fileContent,'#').keySet().toArray()[i],currToken),"");
//        }
//        return adjusted.replaceAll("(?m)^[ \t]*\r?\n", "");
//    }
}
