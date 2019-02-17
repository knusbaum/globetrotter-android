package com.knusbaum.globetrotter.util;

public class Either <V,E> {
    private V value;
    private E error;

    public static <V> Either<V,?> Success(V value) {
        Either<V,?> ret = new Either<>();
        ret.value = value;
        return ret;
    }

    public static <E> Either<?,E> Failure(E error) {
        Either<?, E> ret = new Either<>();
        ret.error = error;
        return ret;
    }

    public boolean isSuccess() {
        return value != null;
    }

    public V getValue() {
        return value;
    }

    public E getError() {
        return error;
    }
}
