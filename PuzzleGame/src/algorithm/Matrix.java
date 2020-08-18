/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import java.awt.Point;
import java.util.Random;

/**
 *
 * @author conqu
 */
public class Matrix {

    private final int randomTimes = 1000;

    // Right -> Left -> Down -> Up
    private final int[] xMove = {0, 0, 1, -1};
    private final int[] yMove = {1, -1, 0, 0};

    private Point empty;

    private int[][] numberMatrix;

    public Matrix() {
        empty = new Point();
    }

    public Matrix(int n) {
        this();
        setNumberMatrix(n);
    }

    public int[][] getNumberMatrix() {
        return numberMatrix;
    }

    public Point getEmptyPosition() {
        return empty;
    }

    public static void main(String[] args) {
        Matrix matrix = new Matrix(3);
        matrix.printMatrix();
        matrix.printMatrix();
    }

    public void setNumberMatrix(int size) {
        numberMatrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                numberMatrix[i][j] = i * size + j + 1;
            }
        }

        numberMatrix[size - 1][size - 1] = 0;
        empty.setLocation(size - 1, size - 1);

        randomize();
    }

    /**
     *
     * @param direction
     * @return Position of next move
     * <br>{@code null} when move outside
     */
    public Point move(int direction) {
        int x = empty.x + xMove[direction];
        int y = empty.y + yMove[direction];
        //Out of bounds
        if (x >= numberMatrix.length || y >= numberMatrix.length) {
            return null;
        }

        // Out of bounds
        if (x < 0 || y < 0) {
            return null;
        }

        numberMatrix[empty.x][empty.y] = numberMatrix[x][y];
        numberMatrix[x][y] = 0;
        empty.setLocation(x, y);

        return new Point(x, y);
    }

    public Integer getDirection(int x, int y) {

        for (int i = 0; i < 4; i++) {
            if (x - empty.x == xMove[i]
                    && y - empty.y == yMove[i])
                return i;
        }

        return null;
    }

    public boolean isCorrectOrder() {
        int num = 0;
        for (int i = 0; i < numberMatrix.length; i++) {
            for (int j = 0; j < numberMatrix.length; j++) {
                if (numberMatrix[i][j] != ++num) {
                    return num == numberMatrix.length * numberMatrix.length;
                }
            }
        }
        return true;
    }

    private void randomize() {
        Random rand = new Random();
        int direction;

        for (int i = 0; i < this.randomTimes; i++) {
            direction = rand.nextInt(4);
            move(direction);
        }
    }

    public void printMatrix() {
        for (int i = 0; i < numberMatrix.length; i++) {
            for (int j = 0; j < numberMatrix[i].length; j++) {
                System.out.print(numberMatrix[i][j] + " ");
            }
            System.out.println("");
        }
    }

}
