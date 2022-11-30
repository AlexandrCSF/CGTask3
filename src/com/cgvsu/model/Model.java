package com.cgvsu.model;
import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;

import java.util.*;

import static com.cgvsu.math.Vector3f.*;

public class Model {

    public ArrayList<Vector3f> vertices = new ArrayList<>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<>();
    public ArrayList<Vector3f> normals = new ArrayList<>();
    public ArrayList<Polygon> polygons = new ArrayList<>();


    public void recalculateNormals(){
        normals.clear();
        for (Vector3f vertex : vertices) {
            normals.add(calculateNormalForVertexInModel(vertex));
            }
        }

    protected Vector3f calculateNormalForVertexInModel(Vector3f vertex){
        ArrayList<Vector3f> saved = new ArrayList<>();
        for (Polygon polygon : polygons) {
            for (int j = 0; j < polygon.getVertexIndices().size(); j++) {
                if (vertices.get(polygon.getVertexIndices().get(j)).equals(vertex)){
                    saved.add(calculateNormalForVertexInPolygon(vertex,polygon));
                }
            }
        }
        return sum(saved).divide(saved.size());
    }

    protected Vector3f calculateNormalForVertexInPolygon(Vector3f vertex,Polygon polygon){
        int index = -1;
        ArrayList<Integer> vertexIndices = polygon.getVertexIndices();
        int n = vertexIndices.size();
        for (int i = 0; i < vertexIndices.size(); i++) {
            if(vertices.get(vertexIndices.get(i)).equals(vertex)){
                index = i;
                break;
            }
        }
        if(index == -1){
            return null;
        }

        Vector3f vector1;
        Vector3f vector2;
        if(index == 0) {
             vector1 = fromTwoPoints(vertices.get(vertexIndices.get(0)), vertices.get(n - 1));
             vector2 = fromTwoPoints(vertices.get(vertexIndices.get(0)), vertices.get(1));
        } else if(index == (n - 1)){
             vector1 = fromTwoPoints(vertices.get(vertexIndices.get(n - 1)), vertices.get(n - 2));
             vector2 = fromTwoPoints(vertices.get(vertexIndices.get(n - 1)), vertices.get(0));
        } else {
             vector1 = fromTwoPoints(vertices.get(vertexIndices.get(index)), vertices.get(index - 1));
             vector2 = fromTwoPoints(vertices.get(vertexIndices.get(index)), vertices.get(index + 1));
        }
        return calculateCrossProduct(vector1,vector2);
    }
}
// Тесты
// Разбить на методы
//
