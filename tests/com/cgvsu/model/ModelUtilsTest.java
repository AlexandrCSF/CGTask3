package com.cgvsu.model;

import com.cgvsu.math.Vector3f;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;



class ModelUtilsTest {

    @Test
    void calculateNormalForVertexInPolygon(){

        Model model = new Model();

        model.vertices.add(new Vector3f(0, 0, 0));
        model.vertices.add(new Vector3f(38, 2, 3.5f));
        model.vertices.add(new Vector3f(24, 10.3f, 5.6f));
        model.vertices.add(new Vector3f(6.3f, 2.1f, 15.2f));
        ArrayList<Integer> vertexIndices = new ArrayList<>(Arrays.asList(0, 1, 2, 3));

        Polygon polygon = new Polygon();
        polygon.setVertexIndices(vertexIndices);

        model.polygons.add(polygon);

        polygon = model.polygons.get(0);

        Vector3f result = ModelUtils.calculateNormalForPolygon(polygon, model);
        Vector3f expectedResult = new Vector3f(23.05f, -555.55f, 67.2f);

        Assertions.assertTrue(expectedResult.equals(result));
    }


    @Test
    void calculateNormalForVertexInModel() {

        Model model = new Model();

        model.vertices.add(new Vector3f(0, 0, 0));
        model.vertices.add(new Vector3f(1, 0, 0));
        model.vertices.add(new Vector3f(1, 1, 0));
        model.vertices.add(new Vector3f(0, 1, 0));
        model.vertices.add(new Vector3f(0, 0, 1));
        model.vertices.add(new Vector3f(1, 0, 1));

        ArrayList<Integer> vertexIndices1 = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
        ArrayList<Integer> vertexIndices2 = new ArrayList<>(Arrays.asList(0, 1, 4, 5));
        ArrayList<Integer> vertexIndices3 = new ArrayList<>(Arrays.asList(2, 3, 4, 5));
        ArrayList<Integer> vertexIndices4 = new ArrayList<>(Arrays.asList(1, 2, 5));
        ArrayList<Integer> vertexIndices5 = new ArrayList<>(Arrays.asList(0, 3, 4));

        Polygon polygon1 = new Polygon();
        polygon1.setVertexIndices(vertexIndices1);
        Polygon polygon2 = new Polygon();
        polygon2.setVertexIndices(vertexIndices2);
        Polygon polygon3 = new Polygon();
        polygon3.setVertexIndices(vertexIndices3);
        Polygon polygon4 = new Polygon();
        polygon4.setVertexIndices(vertexIndices4);
        Polygon polygon5 = new Polygon();
        polygon5.setVertexIndices(vertexIndices5);

        model.polygons.add(polygon1);
        model.polygons.add(polygon2);
        model.polygons.add(polygon3);
        model.polygons.add(polygon4);
        model.polygons.add(polygon5);

        ModelUtils.recalculateNormals(model);

        Vector3f expectedResult1 = new Vector3f(1 / 3f, -1 / 3f, 1 / 3f);
        Vector3f expectedResult2 = new Vector3f(1 / 3f, -1 / 3f, 1 / 3f);
        Vector3f expectedResult3 = new Vector3f(1 / 3f, 1 / 3f, 2 / 3f);
        Vector3f expectedResult4 = new Vector3f(1 / 3f, 1 / 3f, 2 / 3f);
        Vector3f expectedResult5 = new Vector3f(1 / 3f, 0, 1 / 3f);
        Vector3f expectedResult6 = new Vector3f(1 / 3f, 0, 1 / 3f);

        ArrayList<Vector3f> expectedResult = new ArrayList<>(Arrays.asList(expectedResult1, expectedResult2, expectedResult3, expectedResult4, expectedResult5, expectedResult6));
        for (int i = 0; i < model.normals.size(); i++) {
            Assertions.assertTrue(expectedResult.get(i).equals(model.normals.get(i)));
        }
    }
}
