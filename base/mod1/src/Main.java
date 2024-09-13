import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
        exerciseOne();
        exerciseTwo();
    }

    private static void exerciseTwo() {
        int[][] arr = { {20, 34, 2}, {9, 12, 18}, {3, 4, 5} };
        var min = arr[0][0];

        for (var i = 0; i < arr.length; i++) {
            for (var j = 0; j < arr[i].length; j++) {
                min = Integer.min(min, arr[i][j]);
            }
        }
        System.out.println("2. Минимальный элемент массива: " + min);
    }

    private static void exerciseOne() {
        var sum = 0;
        for (var i = 0; i <= 1000; i++) {
            if (i % 3 == 0 || i % 5 == 0) sum += i;
        }
        out.println("1. Сумма чисел от 0 до 1000, кратных 3 или 5: " + sum);
    }

}