package Chapter4;

import java.util.Optional;

public class Kleisli {

    // >> 0
    public static Optional<Double> safeRoot(double d) {
        if (d >= 0) {
            return Optional.of(Math.sqrt(d));
        }

        return Optional.empty();
    }

    // >> 2
    public static Optional<Double> safeReciprocal(double d) {
        if (d != 0) {
            return Optional.of(1 / d);
        }

        return Optional.empty();
    }

    // >> 3
    public static Optional<Double> safeRootReciprocal(double d) {
        return safeReciprocal(d).flatMap(Kleisli::safeRoot);
    }

    public static void main(String[] args) {
        System.out.println(String.format("Root of 81 = %s", safeRoot(81)));
        System.out.println(String.format("Reciprocal of 81 = %s", safeReciprocal(81)));
        System.out.println(String.format("Root of reciprocal of 81 = %s", safeRootReciprocal(81)));
    }
}
