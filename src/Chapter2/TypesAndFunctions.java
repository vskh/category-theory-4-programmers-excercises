package Chapter2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TypesAndFunctions {
    // >> 1
    public static <I, O> Function<I, O> memoize(Function<I, O> func) {
        Map<I, O> results = new HashMap<>();

        return (I arg) -> results.computeIfAbsent(arg, func);
    }

    // >> 1
    public static <O> Supplier<O> memoize(Supplier<O> func) {
        Function<Void, O> funcMem = memoize((Void v) -> func.get());

        return () -> funcMem.apply(null);
    }

    public static Double rand() {
        return Math.random();
    }

    public static Double randWithSeed(int seed) {
        Random random = new Random(seed);

        return random.nextDouble();
    }

    public static int fact(int n) {
        int result = 1;
        for (int i = 2; i < n; ++i) {
            result *= i;
        }

        return result;
    }

    public static void timeAndPrint(String msg, Supplier<?> callable) {
        long start = System.nanoTime();
        Object result = callable.get();
        System.out.println(String.format("[%d ns] %s ", System.nanoTime() - start, msg) + result);
    }

    public static void testPass(Consumer<Integer> runBlock, int numTests, int numPasses) {
        for (int j = 1; j <= numPasses; ++j) {
            System.out.println("Pass " + j);
            for (int i = 0; i < numTests; ++i) {
                runBlock.accept(i);
            }
        }
    }

    public static void testPass(Consumer<Integer> runBlock) {
        testPass(runBlock, 5, 2);
    }

    public static void main(String[] args) {
        // Pure functions:
        //  1. Always return the same value for the same argument.
        //  2. Do not have side-effects.

        // >> 2
        // Does not work because rand() is not pure:
        //  it returns different results for single (or lack of) argument
        Supplier<Double> randMem = memoize(TypesAndFunctions::rand);
        testPass(i -> {
            timeAndPrint("rand() = ", TypesAndFunctions::rand);
            timeAndPrint("randMemoized() = ", randMem);
        });

        // >> 3
        // Works but reseeding generator on each call defeats the purpose (values are not random anymore)
        Function<Integer, Double> randWithSeedMem = memoize(TypesAndFunctions::randWithSeed);
        testPass(i -> {
            timeAndPrint("randWithSeed() = ", () -> randWithSeed(i));
            timeAndPrint("randWithSeedMemoized() = ", () -> randWithSeedMem.apply(i));
        });

        // >> 4.1
        // Works with factorial as it is pure
        Function<Integer, Integer> factMem = memoize(TypesAndFunctions::fact);
        testPass(i -> {
            timeAndPrint("fact() = ", () -> fact(i));
            timeAndPrint("factMem() = ", () -> factMem.apply(i));
        }, 10, 2);

        // >> 4.2
//         std::getchar() is same as rand()

        // >> 4.3 - not pure, has side effects, although could be memoized
//        bool f() {
//            std::cout << "Hello!" << std::endl;
//            return true;
//        }

        // >> 4.4 - not pure, has side effects that affect value, can't be memoized
//        int f(int x) {
//            static int y = 0;
//            y += x;
//            return y;
//        }

    }

    // >> 5
    public static boolean id(boolean b) { return b; }
    public static boolean nid(boolean b) { return !b; }
}
