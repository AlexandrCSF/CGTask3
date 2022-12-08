package com.cgvsu.model;

import com.cgvsu.math.Vector3f;

import java.util.ArrayList;

import static com.cgvsu.math.Vector3f.*;

public class ModelUtils {

    public static void recalculateNormals(Model model) {
        model.normals.clear();
        for (int i = 0; i < model.vertices.size(); i++) {
            model.normals.add(calculateNormalForVertexInModel(model, i));
        }
    }

    protected static Vector3f calculateNormalForPolygon(final Polygon polygon, final Model model){

        ArrayList<Integer> vertexIndices = polygon.getVertexIndices();
        int verticesCount = vertexIndices.size();

        Vector3f vector1 = fromTwoPoints(model.vertices.get(vertexIndices.get(0)), model.vertices.get(vertexIndices.get(1)));
        Vector3f vector2 = fromTwoPoints(model.vertices.get(vertexIndices.get(0)), model.vertices.get(vertexIndices.get(verticesCount - 1)));

        return calculateCrossProduct(vector1, vector2);
    }

    protected static Vector3f calculateNormalForVertexInModel(final Model model, final int vertexIndex) {
        ArrayList<Vector3f> saved = new ArrayList<>();
        for (Polygon polygon : model.polygons) {
            if (polygon.getVertexIndices().contains(vertexIndex)) {
                saved.add(calculateNormalForPolygon(polygon, model));
            }
        }
        return sum(saved).divide(saved.size());
    }
}
