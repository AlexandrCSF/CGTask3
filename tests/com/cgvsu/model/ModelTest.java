package com.cgvsu.model;

import com.cgvsu.math.Vector3f;
import com.cgvsu.objreader.ObjReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {
    @Test
    void calculateNormalForVertexInPolygon() throws IOException {

        String fileNameStr = "test.obj";
        Path fileName = Path.of(fileNameStr);
        String fileContent = Files.readString(fileName);

        Model model = ObjReader.read(fileContent);
        Polygon polygon = model.polygons.get(0);
        Vector3f vertex1 = model.vertices.get(0);
        Vector3f vertex2 = model.vertices.get(polygon.getVertexIndices().size() - 1);

        Vector3f result1 = model.calculateNormalForVertexInPolygon(vertex1,polygon);
        Vector3f expectedResult1 = new Vector3f(-23.05f,555.55f,-67.2f);

        Vector3f result2 = model.calculateNormalForVertexInPolygon(vertex2,polygon);
        Vector3f expectedResult2 = new Vector3f(-144.8f, 329.52f,14.49f);
        Assertions.assertTrue(expectedResult1.equals(result1) && expectedResult2.equals(result2));
    }

    void calculateNormalForVertexInModel(){

    }
}