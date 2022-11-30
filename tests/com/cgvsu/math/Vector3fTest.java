package com.cgvsu.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Vector3fTest {

    @Test
    void testEquals() {
        Vector3f vector1 = new Vector3f(34,0,165);
        Vector3f vector2 = new Vector3f(34,0,165);
        Assertions.assertTrue(vector1.equals(vector2));
    }

    @Test
    void multiplication() {
        Vector3f vector1 = new Vector3f(5,0,4);
        int num = 2;
        Vector3f expectedResult = new Vector3f(10,0,8);
        Assertions.assertTrue(expectedResult.equals(vector1.multiplication(num)));
    }

    @Test
    void sum() {
        Vector3f vector1 = new Vector3f(34,67,165);
        Vector3f vector2 = new Vector3f(45,32,-2);
        Vector3f expectedResult = new Vector3f(79,99,163);
        Assertions.assertTrue(expectedResult.equals(Vector3f.sum(vector1,vector2)));
    }

    @Test
    void divide() {
        Vector3f vector1 = new Vector3f(100,200,300);
        int num = 100;
        Vector3f expectedResult = new Vector3f(1,2,3);
        Assertions.assertTrue(expectedResult.equals(vector1.divide(num)));
    }

    @Test
    void calculateCrossProduct() {
        Vector3f vector1 = new Vector3f(3,12,5);
        Vector3f vector2 = new Vector3f(8, 4,-2);
        Vector3f expectedResult = new Vector3f(-44,46,-84);
        Assertions.assertTrue(expectedResult.equals(Vector3f.calculateCrossProduct(vector1,vector2)));
    }

    @Test
    void fromTwoPoints() {
        Vector3f vector1 = new Vector3f(1,1,1);
        Vector3f vector2 = new Vector3f(2,2,2);
        Vector3f expectedResult = new Vector3f(1,1,1);
        Assertions.assertTrue(expectedResult.equals(Vector3f.fromTwoPoints(vector1,vector2)));
    }
}