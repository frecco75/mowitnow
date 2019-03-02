package com.bbc.automower.util;

import com.bbc.automower.error.Error;
import io.vavr.API;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Try;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.bbc.automower.error.Error.FileNotFound;
import static io.vavr.API.List;
import static org.apache.commons.io.IOUtils.LINE_SEPARATOR;

@Slf4j
@AllArgsConstructor
public class Parser<T> {

    private final Validator<T> validator;

    public Validation<Seq<Error>, T> parse(@NonNull final String filePath) {
        log.debug("Parsing file {}......", filePath);
        return readFile(filePath)
                .mapError(API::Seq)
                .flatMap(validator::validate);
    }

    private Validation<Error, List<String>> readFile(final String filePath) {
        return Try.withResources(() -> getInputStream(filePath))
                .of(inputStream -> List(
                        IOUtils.toString(inputStream)
                                .split(LINE_SEPARATOR)))
                .toValidation(() -> FileNotFound.of(filePath));
    }

    private InputStream getInputStream(final String filePath) throws FileNotFoundException {
        val file = new File(filePath);
        return file.exists() ?
                new FileInputStream(file) :
                Option.of(Parser.class
                        .getClassLoader()
                        .getResourceAsStream(filePath))
                        .getOrElseThrow(() -> new FileNotFoundException("File " + filePath + " not found"));
    }

}
