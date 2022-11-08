package com.cgvsu.objreader;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


import static com.cgvsu.math.Vector3f.calculateCrossProduct;
import static com.cgvsu.math.Vector3f.createVector3fFromTwoPoints;


public class ObjReader {

	private static final String OBJ_VERTEX_TOKEN = "v";
	private static final String OBJ_TEXTURE_TOKEN = "vt";
	private static final String OBJ_NORMAL_TOKEN = "vn";
	private static final String OBJ_FACE_TOKEN = "f";

	public static String getLine(String str, int lineNum) {
		int lineInd = 0;

		Scanner scanner = new Scanner(str);
		String line = scanner.nextLine();
		while (scanner.hasNextLine() && lineInd != lineNum) {
			line = scanner.nextLine();
			lineInd++;
		}
		return line;
	}

	public static Model read(String fileContent) {
		Model result = new Model();

		int lineInd = 0;
		Scanner scanner = new Scanner(fileContent);
		while (scanner.hasNextLine()) {
			final String line = scanner.nextLine();
			ArrayList<String> wordsInLine = new ArrayList<String>(Arrays.asList(line.split("\\s+")));
			if (wordsInLine.isEmpty()) {
				continue;
			}

			final String token = wordsInLine.get(0);
			wordsInLine.remove(0);

			++lineInd;
			switch (token) {
				// Для структур типа вершин методы написаны так, чтобы ничего не знать о внешней среде.
				// Они принимают только то, что им нужно для работы, а возвращают только то, что могут создать.
				// Исключение - индекс строки. Он прокидывается, чтобы выводить сообщение об ошибке.
				// Могло быть иначе. Например, метод parseVertex мог вместо возвращения вершины принимать вектор вершин
				// модели или сам класс модели, работать с ним.
				// Но такой подход может привести к большему количеству ошибок в коде. Например, в нем что-то может
				// тайно сделаться с классом модели.
				// А еще это портит читаемость
				// И не стоит забывать про тесты. Чем проще вам задать данные для теста, проверить, что метод рабочий,
				// тем лучше.
				case OBJ_VERTEX_TOKEN -> result.vertices.add(parseVertex(wordsInLine, lineInd));
				case OBJ_TEXTURE_TOKEN -> result.textureVertices.add(parseTextureVertex(wordsInLine, lineInd));
				//case OBJ_NORMAL_TOKEN -> result.normals.add(parseNormal(wordsInLine, lineInd));
				case OBJ_FACE_TOKEN -> result.polygons.add(parseFace(wordsInLine, lineInd,fileContent));
				default -> {
				}
			}
		}

		for (int i = 0; i < result.polygons.size(); i++) {
			Polygon currPolygon = result.polygons.get(i);
			for (int j = 0; j < currPolygon.getNormals().size(); j++) {
				result.normals.add(currPolygon.getNormals().get(j));
			}
		}

		return result;
	}

	// Всем методам кроме основного я поставил модификатор доступа protected, чтобы обращаться к ним в тестах
	protected static Vector3f parseVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
		try {
			return new Vector3f(
					Float.parseFloat(wordsInLineWithoutToken.get(0)),
					Float.parseFloat(wordsInLineWithoutToken.get(1)),
					Float.parseFloat(wordsInLineWithoutToken.get(2)));

		} catch (NumberFormatException e) {
			throw new ObjReaderException("Failed to parse float value.", lineInd);

		} catch (IndexOutOfBoundsException e) {
			throw new ObjReaderException("Too few vertex arguments.", lineInd);
		}
	}

	protected static Vector2f parseTextureVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
		try {
			return new Vector2f(
					Float.parseFloat(wordsInLineWithoutToken.get(0)),
					Float.parseFloat(wordsInLineWithoutToken.get(1)));

		} catch (NumberFormatException e) {
			throw new ObjReaderException("Failed to parse float value.", lineInd);

		} catch (IndexOutOfBoundsException e) {
			throw new ObjReaderException("Too few texture vertex arguments.", lineInd);
		}
	}

	protected static Vector3f parseNormal(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
		try {
			return new Vector3f(
					Float.parseFloat(wordsInLineWithoutToken.get(0)),
					Float.parseFloat(wordsInLineWithoutToken.get(1)),
					Float.parseFloat(wordsInLineWithoutToken.get(2)));

		} catch (NumberFormatException e) {
			throw new ObjReaderException("Failed to parse float value.", lineInd);

		} catch (IndexOutOfBoundsException e) {
			throw new ObjReaderException("Too few normal arguments.", lineInd);
		}
	}

	protected static Polygon parseFace(final ArrayList<String> wordsInLineWithoutToken, int lineInd, String fileContent) {
		ArrayList<Integer> onePolygonVertexIndices = new ArrayList<Integer>();
		ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<Integer>();
		ArrayList<Integer> onePolygonNormalIndices = new ArrayList<Integer>();



		for (String s : wordsInLineWithoutToken) {
			parseFaceWord(s, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, lineInd);
		}
		ArrayList<Vector3f> onePolygonNormals = recalculateNormals(onePolygonVertexIndices,fileContent);

		Polygon result = new Polygon();
		result.setVertexIndices(onePolygonVertexIndices);
		result.setTextureVertexIndices(onePolygonTextureVertexIndices);
		result.setNormals(onePolygonNormals);
		return result;
	}

	protected static ArrayList<Vector3f> recalculateNormals(
			ArrayList<Integer> onePolygonVertexIndices,
			String fileContent){

		ArrayList<Vector3f> onePolygonNormals = new ArrayList<>();
		float[] x = new float[onePolygonVertexIndices.size()];
		float[] y = new float[onePolygonVertexIndices.size()];
		float[] z = new float[onePolygonVertexIndices.size()];


		for (int i = 0; i < onePolygonVertexIndices.size(); i++) {
			String currLine = getLine(fileContent,onePolygonVertexIndices.get(i));

			ArrayList<String> wordsInLine = new ArrayList<String>(Arrays.asList(currLine.split("\\s+")));
			wordsInLine.remove(0);

			Vector3f currVertex = parseVertex(wordsInLine, onePolygonVertexIndices.get(i));

			x[i] = currVertex.getX();
			y[i] = currVertex.getY();
			z[i] = currVertex.getZ();
		}

		int n = x.length;

		Vector3f currVector = calculateCrossProduct(createVector3fFromTwoPoints(x[0], y[0], z[0], x[1], y[1], z[1]),
				createVector3fFromTwoPoints(x[0], y[0], z[0], x[n - 1], y[n - 1], z[n - 1]));
		onePolygonNormals.add(currVector);

		currVector = calculateCrossProduct(createVector3fFromTwoPoints(x[n - 1], y[n - 1], z[n - 1], x[0], y[0], z[0]),
				createVector3fFromTwoPoints(x[n - 1], y[n - 1], z[n - 1], x[n - 2], y[n - 2], z[n - 2]));
		onePolygonNormals.add(currVector);

		for (int i = 1; i < onePolygonVertexIndices.size() - 1; i++) {
			currVector = calculateCrossProduct(createVector3fFromTwoPoints(x[i], y[i], z[i], x[i + 1], y[i + 1], z[i + 1]),
					createVector3fFromTwoPoints(x[i], y[i], z[i], x[i - 1], y[i - 1], z[i - 1]));

			onePolygonNormals.add(currVector);
		}
		return onePolygonNormals;
	}

	// Обратите внимание, что для чтения полигонов я выделил еще один вспомогательный метод.
	// Это бывает очень полезно и с точки зрения структурирования алгоритма в голове, и с точки зрения тестирования.
	// В радикальных случаях не бойтесь выносить в отдельные методы и тестировать код из одной-двух строчек.
	protected static void parseFaceWord(
			String wordInLine,
			ArrayList<Integer> onePolygonVertexIndices,
			ArrayList<Integer> onePolygonTextureVertexIndices,
			ArrayList<Integer> onePolygonNormalIndices,
			int lineInd) {
		try {
			String[] wordIndices = wordInLine.split("/");
			switch (wordIndices.length) {
				case 1 -> {
					onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
				}
				case 2 -> {
					onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
					onePolygonTextureVertexIndices.add(Integer.parseInt(wordIndices[1]) - 1);
				}
				case 3 -> {
					onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
					onePolygonNormalIndices.add(Integer.parseInt(wordIndices[2]) - 1);
					if (!wordIndices[1].equals("")) {
						onePolygonTextureVertexIndices.add(Integer.parseInt(wordIndices[1]) - 1);
					}
				}
				default -> {
					throw new ObjReaderException("Invalid element size.", lineInd);
				}
			}

		} catch (NumberFormatException e) {
			throw new ObjReaderException("Failed to parse int value.", lineInd);

		} catch (IndexOutOfBoundsException e) {
			throw new ObjReaderException("Too few arguments.", lineInd);
		}
	}

	public static ArrayList<Vector3f> calculateNormals(Polygon polygon,String fileContent) {
		ArrayList<Vector3f> result = new ArrayList<>();
		ArrayList<Integer> onePolygonVertexIndices = polygon.getVertexIndices();
		float[] x = new float[onePolygonVertexIndices.size()];
		float[] y = new float[onePolygonVertexIndices.size()];
		float[] z = new float[onePolygonVertexIndices.size()];

		try {
			for (int i = 0; i < onePolygonVertexIndices.size(); i++) {
				String[] currLineSplit = getLine(fileContent, onePolygonVertexIndices.get(i)).split("\s++");
				x[i] = Float.parseFloat(currLineSplit[1]);
				y[i] = Float.parseFloat(currLineSplit[2]);
				z[i] = Float.parseFloat(currLineSplit[3]);
			}
		} catch (Exception ignore) {
		}
		int n = x.length;

		Vector3f currVector = calculateCrossProduct(createVector3fFromTwoPoints(x[0], y[0], z[0], x[1], y[1], z[1]),
				createVector3fFromTwoPoints(x[0], y[0], z[0], x[n - 1], y[n - 1], z[n - 1]));
		result.add(currVector);

		currVector = calculateCrossProduct(createVector3fFromTwoPoints(x[n - 1], y[n - 1], z[n - 1], x[0], y[0], z[0]),
				createVector3fFromTwoPoints(x[n - 1], y[n - 1], z[n - 1], x[n - 2], y[n - 2], z[n - 2]));
		result.add(currVector);

		for (int i = 1; i < onePolygonVertexIndices.size() - 1; i++) {
			currVector = calculateCrossProduct(createVector3fFromTwoPoints(x[i], y[i], z[i], x[i + 1], y[i + 1], z[i + 1]),
					createVector3fFromTwoPoints(x[i], y[i], z[i], x[i - 1], y[i - 1], z[i - 1]));

			result.add(currVector);
		}
		return result;
	}
}
