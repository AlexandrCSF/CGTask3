package com.cgvsu.math;

import java.util.ArrayList;

// Это заготовка для собственной библиотеки для работы с линейной алгеброй
public class Vector3f {

    private Vector3f startPoint;

    public void setStartPoint(Vector3f startPoint) {
        this.startPoint = startPoint;
    }

    public Vector3f getStartPoint() {
        return startPoint;
    }

    public Vector3f(Vector3f startPoint, float x, float y, float z) {
        this.startPoint = startPoint;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean equals(Vector3f other) {
        // todo: желательно, чтобы это была глобальная константа
        final float eps = 1e-7f;
        return Math.abs(x - other.x) < eps && Math.abs(y - other.y) < eps && Math.abs(z - other.z) < eps;
    }

    float x, y, z;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public static Vector3f multiplication(float num, Vector3f vector){
        return new Vector3f(vector.getX() * num, vector.getY() * num, vector.getZ() * num);
    }

    public static Vector3f sum(Vector3f ... vectors){
        float x = vectors[0].getX();
        float y = vectors[0].getY();
        float z = vectors[0].getZ();
        for (int i = 1; i < vectors.length; i++) {
            x += vectors[i].getX();
            y += vectors[i].getY();
            z += vectors[i].getZ();
        }
        return new Vector3f(x,y,z);
    }

    public static Vector3f sum(ArrayList<Vector3f> vectors){
        float x = vectors.get(0).getX();
        float y = vectors.get(0).getY();
        float z = vectors.get(0).getZ();
        for (int i = 1; i < vectors.size(); i++) {
            x += vectors.get(i).getX();
            y += vectors.get(i).getY();
            z += vectors.get(i).getZ();
        }
        return new Vector3f(x,y,z);
    }


    public Vector3f divide(float num){
        return new Vector3f(x / num, y / num, z / num);
    }

    public static Vector3f divide(int num, Vector3f vector){
        return new Vector3f(vector.getX() / num, vector.getY() / num, vector.getZ() / num);
    }

    public static Vector3f calculateCrossProduct(Vector3f vector1,Vector3f vector2){
        float x = vector1.getY()* vector2.getZ() - vector1.getZ()* vector2.getY();
        float y = vector1.getZ() * vector2.getX() - vector1.getX() * vector1.getZ();
        float z = vector1.getX() * vector2.getY() - vector1.getY() * vector2.getX();
        return new Vector3f(x,y,z);
    }

    public static Vector3f createVector3fFromTwoPoints(float x1,float y1, float z1,float x2,float y2, float z2){
        return new Vector3f(x2 - x1,y2-y1,z2-z1);
    }

    public static boolean equals(Vector3f vector1, Vector3f vector2){
        return vector1.getX() == vector2.getX() && vector1.getY() == vector2.getY() && vector1.getZ() == vector2.getZ();
    }
}
