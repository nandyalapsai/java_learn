abstract class Car {
    public abstract void pressBrake();
}

interface  Calculator {
    int add(int a, int b);
}

public class code {
    public static void main(String[] args) {
        new Car() {
            @Override
            public void pressBrake() {
                System.out.println("Brakes pressed!");
            }
        }.pressBrake();

        Calculator calc = (a, b) -> {
            return a + b;
        };

    }
}