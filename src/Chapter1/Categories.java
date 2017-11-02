package Chapter1;

import java.util.Objects;
import java.util.function.Function;

public class Categories {

    public static <T> T id(T obj) {
        return obj;
    }

    public static <A, B, C> Function<A, C> compose(Function<B, C> g, Function<A, B> f) {
        return obj -> g.apply(f.apply(obj));
    }

    public static void main(String[] args) {
        System.out.println("1 == id 1 : " + (1 == id(1)));

        Integer number = 100;
        System.out.println("id . toString == toString : " +
                Objects.equals(compose(Categories::id, Object::toString).apply(number), number.toString()));
        System.out.println("toString . id == toString : " +
                Objects.equals(compose(Object::toString, Categories::id).apply(number), number.toString()));
    }
}
