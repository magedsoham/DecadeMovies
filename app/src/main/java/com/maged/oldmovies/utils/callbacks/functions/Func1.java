package com.maged.oldmovies.utils.callbacks.functions;

import java.io.Serializable;

public interface Func1<T> extends Serializable {
    void apply(T input);
}
