package com.bbc.automower.util;

import com.bbc.automower.error.Error;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;

@FunctionalInterface
public interface Validator<T> {

    Validation<Seq<Error>, T> validate(List<String> lines);

}