package com.bbc.automower;

import com.bbc.automower.domain.Lawn;
import com.bbc.automower.error.Error;
import com.bbc.automower.util.LawnValidator;
import com.bbc.automower.util.Parser;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import static io.vavr.API.List;
import static java.util.Objects.nonNull;

@Slf4j
public class Main {

    private final static String DEFAULT_FILE_PATH = "META-INF/config/automower.txt";
    private final static Parser<Lawn> LAWN_PARSER = new Parser<>(new LawnValidator());

    public static void main(final String[] args) {
        val filePath = getFilePath(List(args));
        handleError(execute(filePath));
    }

    static Validation<Seq<Error>, Lawn> execute(final String filePath) {
        return LAWN_PARSER
                .parse(filePath)
                .map(Lawn::execute);
    }

    static void handleError(final Validation<Seq<Error>, ?> validation) {
        validation
                .swap()
                .forEach(errors -> errors
                        .map(Error::text)
                        .forEach(log::error));
    }

    static String getFilePath(final List<String> args) {
        return nonNull(args) && args.size() == 1 ?
                args.head() :
                DEFAULT_FILE_PATH;
    }

}
