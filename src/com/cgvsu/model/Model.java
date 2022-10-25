package com.cgvsu.model;
import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;

import java.util.*;


public class Model {

    public ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<Vector2f>();
    public ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
    public static ArrayList<Polygon> polygons = new ArrayList<Polygon>();


    private static final String OBJ_FACE_TOKEN = "f";


    private static String getLine(int index,String fileContent) {
        int lineInd = 0;
        String line = null;
        Scanner scanner = new Scanner(fileContent);
        while (scanner.hasNextLine() && lineInd < index) {
            line = scanner.nextLine();
            lineInd++;
        }

        return line;
    }

    public static void calculateNormals(String fileContent, Model model) {

        float x1;
        float y1;
        float z1;

        float x2;
        float y2;
        float z2;

        float x3;
        float y3;
        float z3;

        for (Polygon polygon : polygons) {

            String[] split = getLine((polygon.getVertexIndices().get(0) + 1), fileContent).split("\\s+");

            x1 = Float.parseFloat(split[1]);
            y1 = Float.parseFloat(split[2]);
            z1 = Float.parseFloat(split[3]);

            split = getLine((polygon.getVertexIndices().get(1) + 1), fileContent).split("\\s+");

            x2 = Float.parseFloat(split[1]);
            y2 = Float.parseFloat(split[2]);
            z2 = Float.parseFloat(split[3]);

            split = getLine((polygon.getVertexIndices().get(2) + 1), fileContent).split("\\s+");

            x3 = Float.parseFloat(split[1]);
            y3 = Float.parseFloat(split[2]);
            z3 = Float.parseFloat(split[3]);

            Vector3f vector12 = createVector3fFromTwoPoints(x1, y1, z1, x2, y2, z2);
            Vector3f vector13 = createVector3fFromTwoPoints(x1, y1, z1, x3, y3, z3);
            Vector3f vector23 = createVector3fFromTwoPoints(x2, y2, z2, x3, y3, z3);

            Vector3f normal1 = calculateCrossProduct(vector12, vector13);
            Vector3f normal2 = calculateCrossProduct(Vector3f.multiplicate(-1, vector12), vector23);
            Vector3f normal3 = calculateCrossProduct(Vector3f.multiplicate(-1, vector13), Vector3f.multiplicate(-1, vector23));

            Vector3f arithmeticMean = Vector3f.divide(3, Vector3f.sum(normal1, normal2, normal3));

            model.normals.add(arithmeticMean);
        }
    }


    public static Vector3f createVector3fFromTwoPoints(float x1,float y1, float z1,float x2,float y2, float z2){
        return new Vector3f(x2 - x1,y2-y1,z2-z1);
    }
    public static Vector3f calculateCrossProduct(Vector3f vector1,Vector3f vector2){
        float x = vector1.getY()* vector2.getZ() - vector1.getZ()* vector2.getY();
        float y = vector1.getZ() * vector2.getX() - vector1.getX() * vector1.getZ();
        float z = vector1.getX() * vector2.getY() - vector1.getY() * vector2.getX();
        return new Vector3f(x,y,z);
    }
}
