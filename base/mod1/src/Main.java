import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
        var sum = 0;
        for (var i = 0; i <= 1000; i++) {
            if (i % 3 == 0 || i % 5 == 0) sum += i;
        }
        out.println("Сумма чисел от 0 до 1000, кратных 3 или 5: " + sum);
    }
}