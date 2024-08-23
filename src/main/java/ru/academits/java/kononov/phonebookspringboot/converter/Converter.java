package ru.academits.java.kononov.phonebookspringboot.converter;

import java.util.List;
import java.util.stream.Collectors;

public interface Converter<S, D> {
    D convert(S source);

    default List<D> convert(List<S> source) {
        return source.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
