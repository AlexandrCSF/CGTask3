package com.cgvsu.model;

import com.cgvsu.math.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static com.cgvsu.math.Vector3f.calculateCrossProduct;
import static com.cgvsu.math.Vector3f.createVector3fFromTwoPoints;
import static com.cgvsu.objreader.ObjReader.getLine;
import static com.cgvsu.objreader.ObjReader.parseVertex;

public class Polygon {

    private ArrayList<Integer> vertexIndices;
    private ArrayList<Integer> textureVertexIndices;
    private ArrayList<Integer> normalIndices;


    public Polygon() {
        vertexIndices = new ArrayList<Integer>();
        textureVertexIndices = new ArrayList<Integer>();
        normalIndices = new ArrayList<Integer>();
    }

    public void setVertexIndices(ArrayList<Integer> vertexIndices) {
        assert vertexIndices.size() >= 3;
        this.vertexIndices = vertexIndices;
    }

    public void setTextureVertexIndices(ArrayList<Integer> textureVertexIndices) {
        assert textureVertexIndices.size() >= 3;
        this.textureVertexIndices = textureVertexIndices;
    }

    public void setNormalIndices(ArrayList<Integer> normalIndices) {
        assert normalIndices.size() >= 3;
        this.normalIndices = normalIndices;
    }

    public ArrayList<Integer> getVertexIndices() {
        return vertexIndices;
    }

    public ArrayList<Integer> getTextureVertexIndices() {
        return textureVertexIndices;
    }

    public ArrayList<Integer> getNormalIndices() {
        return normalIndices;
    }

    public static Vector3f getVertexCordsFromIndex(String fileContent,int vertexIndex){

        String currLine = getLine(fileContent,vertexIndex);

        while (!currLine.startsWith("v")) {
            currLine = getLine(fileContent, vertexIndex++);
        }

            ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList(currLine.split("\\s+")));
            wordsInLine.remove(0);

            return parseVertex(wordsInLine, vertexIndex);
    }
    public static ArrayList<Vector3f> getVerticesCordsFromIndex(String fileContent, ArrayList<Integer> vertexIndices){
        ArrayList<Vector3f> result = new ArrayList<>();
        for (Integer vertexIndex : vertexIndices) {
            String currVertexStr = getLine(fileContent, vertexIndex);
            ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList(currVertexStr.split("\\s+")));
            if (wordsInLine.isEmpty()) {
                continue;
            }
            wordsInLine.remove(0);

            Vector3f currVertex = parseVertex(wordsInLine, vertexIndex);
            result.add(currVertex);
        }
        return result;
        }

        public static Vector3f calculateNormalForVertexInPolygon(String fileContent, Vector3f vertex,Polygon polygon){

            ArrayList<Integer> onePolygonVertexIndices = polygon.getVertexIndices();
            
            float[] x = new float[onePolygonVertexIndices.size()];
            float[] y = new float[onePolygonVertexIndices.size()];
            float[] z = new float[onePolygonVertexIndices.size()];
            
            for (int j = 0; j < onePolygonVertexIndices.size(); j++) {

                String currLine = getLine(fileContent, onePolygonVertexIndices.get(j));

                ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList(currLine.split("\\s+")));

                wordsInLine.remove(0);

                if (wordsInLine.isEmpty()) {
                    continue;
                }

                Vector3f currVertex = parseVertex(wordsInLine, onePolygonVertexIndices.get(j));

                x[j] = currVertex.getX();
                y[j] = currVertex.getY();
                z[j] = currVertex.getZ();
            }
            int n = onePolygonVertexIndices.size();
            for (int i = 0; i < onePolygonVertexIndices.size(); i++) {
                if(getVertexCordsFromIndex(fileContent,onePolygonVertexIndices.get(i)).equals(vertex)){
                    if(i == 0){
                        return calculateCrossProduct(createVector3fFromTwoPoints(x[0], y[0], z[0], x[1], y[1], z[1]),
                                createVector3fFromTwoPoints(x[0], y[0], z[0], x[n - 1], y[n - 1], z[n - 1]));
                    } if(i == n - 1){
                       return calculateCrossProduct(createVector3fFromTwoPoints(x[n - 1], y[n - 1], z[n - 1], x[0], y[0], z[0]),
                                createVector3fFromTwoPoints(x[n - 1], y[n - 1], z[n - 1], x[n - 2], y[n - 2], z[n - 2]));
                    } else return calculateCrossProduct(createVector3fFromTwoPoints(x[i], y[i], z[i], x[i + 1], y[i + 1], z[i + 1]),
                                createVector3fFromTwoPoints(x[i], y[i], z[i], x[i - 1], y[i - 1], z[i - 1]));
                }
            }
            return null;
        }
    }