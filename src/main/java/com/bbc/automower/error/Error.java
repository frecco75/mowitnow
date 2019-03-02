package com.bbc.automower.error;

import com.bbc.automower.enumeration.Instruction;
import com.bbc.automower.enumeration.Orientation;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import lombok.Value;

import static io.vavr.API.Invalid;
import static io.vavr.API.Seq;
import static java.lang.String.format;

@FunctionalInterface
public interface Error {

    String text();

    default <T> Validation<Error, T> asInvalid() {
        return Invalid(this);
    }

    default <T> Validation<Seq<Error>, T> asInvalidSeq() {
        return Invalid(Seq(this));
    }

    @Value(staticConstructor = "of")
    class EmptyFile implements Error {
        @Override
        public String text() {
            return "Empty file";
        }
    }

    @Value(staticConstructor = "of")
    class FileNotFound implements Error {
        String filename;

        @Override
        public String text() {
            return format("File %s not found", filename);
        }
    }

    @Value(staticConstructor = "of")
    class InvalidInstruction implements Error {
        int line;
        char value;

        @Override
        public String text() {
            return format("Line %d: %c cannot be cast to " + Instruction.class.getName(), line, value);
        }
    }

    @Value(staticConstructor = "of")
    class InvalidOrientation implements Error {
        int line;
        String value;

        @Override
        public String text() {
            return format("Line %d: %s cannot be cast to " + Orientation.class.getName(), line, value);
        }
    }

    @Value(staticConstructor = "of")
    class InvalidLength implements Error {
        int line;
        int elements;
        int expectedElements;

        @Override
        public String text() {
            return format("Line %s: the line contains %d elements instead of %d", line, elements, expectedElements);
        }
    }

    @Value(staticConstructor = "of")
    class InvalidInt implements Error {
        int line;
        String value;

        @Override
        public String text() {
            return format("Line %s: %s is not a numeric", line, value);
        }
    }

    @Value(staticConstructor = "of")
    class InvalidSizeList implements Error {
        List<?> list;
        int expectedElements;

        @Override
        public String text() {
            return format("Bad number of elements for list %s : expected %d elements", list, expectedElements);
        }
    }

}
