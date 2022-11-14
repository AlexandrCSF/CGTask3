package com.cgvsu.model;
import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;

import java.util.*;

import static com.cgvsu.model.Polygon.*;

public class Model {

    public ArrayList<Vector3f> vertices = new ArrayList<>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<>();
    public ArrayList<Vector3f> normals = new ArrayList<>();
    public ArrayList<Polygon> polygons = new ArrayList<>();


    public void recalculateNormals(String fileContent){
        normals.clear();
        ArrayList<Vector3f> saved = new ArrayList<>();
        for (Vector3f vertex : vertices) {
            for (Polygon polygon : polygons) {
                for (int k = 0; k < polygon.getVertexIndices().size(); k++) {
                    if (vertex.equals(getVertexCordsFromIndex(fileContent, (polygon.getVertexIndices()).get(k)))) {
                        saved.add(calculateNormalForVertexInPolygon(fileContent, vertex, polygon));
                        break;
                    }
                }
            }
            normals.add(Vector3f.sum(saved).divide(saved.size()));
            saved.clear();
        }
    }
}

