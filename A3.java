import java.util.Scanner;

class Matrix {
    int row, col;
    double[][] data;

    Matrix(int r, int c) {
        row = r;
        col = c;
        data = new double[r][c];
    }

    void populate_matrix(Scanner sc) {
        System.out.println("Enter elements for " + row + " x " + col + " matrix:");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                data[i][j] = sc.nextDouble();
            }
        }
    }

    void display_matrix() {
        System.out.println("Matrix (" + row + "x" + col + "):");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(data[i][j] + "\t");
            }
            System.out.println();
        }
    }

    // Addition
    static Matrix add(Matrix m1, Matrix m2) {
        if (m1.row != m2.row || m1.col != m2.col) {
            System.out.println("Addition not possible!");
            return null;
        }
        Matrix result = new Matrix(m1.row, m1.col);
        for (int i = 0; i < m1.row; i++) {
            for (int j = 0; j < m1.col; j++) {
                result.data[i][j] = m1.data[i][j] + m2.data[i][j];
            }
        }
        return result;
    }

    // Subtraction
    static Matrix subtract(Matrix m1, Matrix m2) {
        if (m1.row != m2.row || m1.col != m2.col) {
            System.out.println("Subtraction not possible!");
            return null;
        }
        Matrix result = new Matrix(m1.row, m1.col);
        for (int i = 0; i < m1.row; i++) {
            for (int j = 0; j < m1.col; j++) {
                result.data[i][j] = m1.data[i][j] - m2.data[i][j];
            }
        }
        return result;
    }

    // Multiplication
    static Matrix multiply(Matrix m1, Matrix m2) {
        if (m1.col != m2.row) {
            System.out.println("Multiplication not possible!");
            return null;
        }
        Matrix result = new Matrix(m1.row, m2.col);
        for (int i = 0; i < m1.row; i++) {
            for (int j = 0; j < m2.col; j++) {
                result.data[i][j] = 0;
                for (int k = 0; k < m1.col; k++) {
                    result.data[i][j] += m1.data[i][k] * m2.data[k][j];
                }
            }
        }
        return result;
    }

    // Transpose
    Matrix transpose() {
        Matrix t = new Matrix(col, row);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                t.data[j][i] = data[i][j];
            }
        }
        return t;
    }

    // Determinant (recursive)
    double determinant() {
        if (row != col) {
            System.out.println("Determinant only for square matrices!");
            return Double.NaN;
        }
        return determinantHelper(data, row);
    }

    private double determinantHelper(double[][] mat, int n) {
        if (n == 1) return mat[0][0];
        if (n == 2) return (mat[0][0] * mat[1][1]) - (mat[0][1] * mat[1][0]);

        double det = 0;
        for (int p = 0; p < n; p++) {
            double[][] subMat = new double[n - 1][n - 1];
            for (int i = 1; i < n; i++) {
                int colIndex = 0;
                for (int j = 0; j < n; j++) {
                    if (j == p) continue;
                    subMat[i - 1][colIndex++] = mat[i][j];
                }
            }
            det += mat[0][p] * Math.pow(-1, p) * determinantHelper(subMat, n - 1);
        }
        return det;
    }

    // Inverse (Gauss-Jordan)
    Matrix inverse() {
        if (row != col) {
            System.out.println("Inverse only for square matrices!");
            return null;
        }
        int n = row;
        double[][] augmented = new double[n][2 * n];

        // Create augmented matrix [A|I]
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmented[i][j] = data[i][j];
            }
            augmented[i][i + n] = 1;
        }

        // Apply Gauss-Jordan elimination
        for (int i = 0; i < n; i++) {
            double diag = augmented[i][i];
            if (diag == 0) {
                System.out.println("Matrix is singular, inverse does not exist!");
                return null;
            }
            for (int j = 0; j < 2 * n; j++) {
                augmented[i][j] /= diag;
            }
            for (int k = 0; k < n; k++) {
                if (k == i) continue;
                double factor = augmented[k][i];
                for (int j = 0; j < 2 * n; j++) {
                    augmented[k][j] -= factor * augmented[i][j];
                }
            }
        }

        // Extract inverse matrix
        Matrix inv = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inv.data[i][j] = augmented[i][j + n];
            }
        }
        return inv;
    }
}

public class A3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Matrix m1 = null, m2 = null;

        while (true) {
            System.out.println("\n===== MATRIX MENU =====");
            System.out.println("1. Create Matrix 1");
            System.out.println("2. Create Matrix 2");
            System.out.println("3. Display Matrices");
            System.out.println("4. Add Matrices");
            System.out.println("5. Subtract Matrices");
            System.out.println("6. Multiply Matrices");
            System.out.println("7. Transpose Matrix");
            System.out.println("8. Determinant of Matrix");
            System.out.println("9. Inverse of Matrix");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1 -> {
                    System.out.print("Enter rows and cols for Matrix 1: ");
                    m1 = new Matrix(sc.nextInt(), sc.nextInt());
                    m1.populate_matrix(sc);
                }
                case 2 -> {
                    System.out.print("Enter rows and cols for Matrix 2: ");
                    m2 = new Matrix(sc.nextInt(), sc.nextInt());
                    m2.populate_matrix(sc);
                }
                case 3 -> {
                    if (m1 != null) m1.display_matrix();
                    if (m2 != null) m2.display_matrix();
                }
                case 4 -> {
                    if (m1 != null && m2 != null) {
                        Matrix sum = Matrix.add(m1, m2);
                        if (sum != null) sum.display_matrix();
                    } else System.out.println("Both matrices required!");
                }
                case 5 -> {
                    if (m1 != null && m2 != null) {
                        Matrix diff = Matrix.subtract(m1, m2);
                        if (diff != null) diff.display_matrix();
                    } else System.out.println("Both matrices required!");
                }
                case 6 -> {
                    if (m1 != null && m2 != null) {
                        Matrix prod = Matrix.multiply(m1, m2);
                        if (prod != null) prod.display_matrix();
                    } else System.out.println("Both matrices required!");
                }
                case 7 -> {
                    System.out.print("Transpose which matrix (1/2)? ");
                    int tChoice = sc.nextInt();
                    if (tChoice == 1 && m1 != null) m1.transpose().display_matrix();
                    else if (tChoice == 2 && m2 != null) m2.transpose().display_matrix();
                    else System.out.println("Matrix not defined!");
                }
                case 8 -> {
                    System.out.print("Determinant of which matrix (1/2)? ");
                    int dChoice = sc.nextInt();
                    if (dChoice == 1 && m1 != null && m1.row == m1.col) {
                        System.out.println("Det = " + m1.determinant());
                    } else if (dChoice == 2 && m2 != null && m2.row == m2.col) {
                        System.out.println("Det = " + m2.determinant());
                    } else {
                        System.out.println("Matrix not defined or not square!");
                    }
                }
                case 9 -> {
                    System.out.print("Inverse of which matrix (1/2)? ");
                    int iChoice = sc.nextInt();
                    if (iChoice == 1 && m1 != null) {
                        Matrix inv = m1.inverse();
                        if (inv != null) inv.display_matrix();
                    } else if (iChoice == 2 && m2 != null) {
                        Matrix inv = m2.inverse();
                        if (inv != null) inv.display_matrix();
                    } else System.out.println("Matrix not defined!");
                }
                case 0 -> {
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
