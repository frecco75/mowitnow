package com.bbc.automower.error;

import lombok.val;
import org.junit.jupiter.api.Test;

import static com.bbc.automower.error.Error.*;
import static io.vavr.API.List;
import static io.vavr.API.Seq;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class ErrorTest {

    @Test
    void should_format_errors() {
        assertThat(EmptyFile.of().text()).isEqualTo("Empty file");
        assertThat(FileNotFound.of("filename").text()).isEqualTo("File filename not found");
        assertThat(InvalidInstruction.of(1, 'X').text()).isEqualTo("Line 1: X cannot be cast to com.bbc.automower.enumeration.Instruction");
        assertThat(InvalidOrientation.of(1, "X").text()).isEqualTo("Line 1: X cannot be cast to com.bbc.automower.enumeration.Orientation");
        assertThat(InvalidLength.of(1, 2, 3).text()).isEqualTo("Line 1: the line contains 2 elements instead of 3");
        assertThat(InvalidInt.of(1, "X").text()).isEqualTo("Line 1: X is not a numeric");
        assertThat(InvalidSizeList.of(List("123"), 2).text()).isEqualTo("Bad number of elements for list List(123) : expected 2 elements");
    }

    @Test
    void should_be_invalid() {
        // Given
        Error e = () -> "an error";

        // When
        val errorOrSomething = e.asInvalid();

        // Then
        assertThat(errorOrSomething).containsInvalid(e);
    }

    @Test
    void should_be_invalid_seq() {
        // Given
        Error e = () -> "an error";

        // When
        val errorOrSomething = e.asInvalidSeq();

        // Then
        assertThat(errorOrSomething).containsInvalid(Seq(e));
    }

}