package com.cgvsu.model;
import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;

import java.util.*;

import static com.cgvsu.math.Vector3f.*;

public class Model {

    public ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<Vector2f>();
    public ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
    public ArrayList<Polygon> polygons = new ArrayList<Polygon>();

    public static void calculateNormals(Model model){
        model.normals.clear();

        int n = model.vertices.size();
        
        float[] x = new float[n];
        float[] y = new float[n];
        float[] z = new float[n];

        for (int i = 0; i < n; i++) {
            x[i] = model.vertices.get(i).getX();
            y[i] = model.vertices.get(i).getY();
            z[i] = model.vertices.get(i).getZ();
        }

          model.normals.add(calculateCrossProduct(createVector3fFromTwoPoints(x[0], y[0], z[0], x[1], y[1], z[1]),
                  createVector3fFromTwoPoints(x[0], y[0], z[0], x[n - 1], y[n - 1], z[n - 1])));
          model.normals.add(calculateCrossProduct(createVector3fFromTwoPoints(x[n - 1], y[n - 1], z[n - 1], x[0], y[0], z[0]),
                  createVector3fFromTwoPoints(x[n - 1], y[n - 1], z[n - 1], x[n - 2], y[n - 2], z[n - 2])));

        for (int i = 1; i < n - 1; i++) {
           model.normals.add(calculateCrossProduct(createVector3fFromTwoPoints(x[i], y[i], z[i], x[i + 1], y[i + 1], z[i + 1]),
                   createVector3fFromTwoPoints(x[i], y[i], z[i], x[i - 1], y[i - 1], z[i - 1])));
        }
    }
}
