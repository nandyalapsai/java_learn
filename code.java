public class code {
    public static void main(String[] args) {
        long start, end;

        // With primitives
        start = System.nanoTime();
        long sum1 = 0;
        for (long i = 0; i < 1_000; i++) {
            sum1 += i;
        }
        end = System.nanoTime();
        System.out.println("Primitive time: " + (end - start));

        // With wrappers (autoboxing!)
        start = System.nanoTime();
        Long sum2 = 0L;
        for (long i = 0; i < 1_000_000; i++) {
            sum2 += i;  // Autoboxing/unboxing each iteration
        }
        end = System.nanoTime();
        System.out.println("Wrapper time: " + (end - start));
        System.out.println();
    }
}