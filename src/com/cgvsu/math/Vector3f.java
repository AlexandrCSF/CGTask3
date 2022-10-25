package com.cgvsu.math;

// Это заготовка для собственной библиотеки для работы с линейной алгеброй
public class Vector3f {
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

    public static Vector3f multiplicate(float num, Vector3f vector){
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

    public static Vector3f divide(float num, Vector3f vector){
        return new Vector3f(vector.getX() / num, vector.getY() / num, vector.getZ() / num);
    }
}
