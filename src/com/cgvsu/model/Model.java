package com.cgvsu.model;
import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;

import java.util.*;

import static com.cgvsu.model.Polygon.calculateNormalForVertexInPolygon;
import static com.cgvsu.model.Polygon.getVerticesCordsFromIndex;

public class Model {

    public ArrayList<Vector3f> vertices = new ArrayList<>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<>();
    public ArrayList<Vector3f> normals = new ArrayList<>();
    public ArrayList<Polygon> polygons = new ArrayList<>();


    public void recalculateNormals(String fileContent){
        ArrayList<Vector3f> saved = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < polygons.size(); j++) {
                for (int k = 0; k < polygons.get(j).getVertexIndices().size(); k++) {
                    if(vertices.get(i).equals(getVerticesCordsFromIndex(fileContent,polygons.get(j).getVertexIndices()).get(k))){
                        saved.add(calculateNormalForVertexInPolygon(fileContent,vertices.get(i),polygons.get(j)));
                    }
                }
            }
          normals.add(Vector3f.sum(saved).divide(saved.size()));
        }
    }
}

