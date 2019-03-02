package com.bbc.automower.util;

import com.bbc.automower.domain.Lawn;
import com.bbc.automower.domain.Mower;
import com.bbc.automower.error.Error;
import com.bbc.automower.error.Error.FileNotFound;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.bbc.automower.Constants.GOOD_FILE_PATH;
import static com.bbc.automower.enumeration.Instruction.*;
import static com.bbc.automower.enumeration.Orientation.EAST;
import static com.bbc.automower.enumeration.Orientation.NORTH;
import static io.vavr.API.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParserTest {

    @Mock
    private Validator<Lawn> validator;

    @InjectMocks
    private Parser<Lawn> parser;

    @Test
    void sould_throw_illegalargumentexception_when_filename_is_null() {
        // When
        val throwable = catchThrowable(() -> parser.parse(null));

        // Then
        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("filePath is marked non-null but is null");
    }

    @Test
    void should_be_invalid_when_file_not_found() {
        // When
        val errorsOrAutomower = parser.parse("src/main/resources/META-INF/config/dzedze");

        // Then
        assertThat(errorsOrAutomower).containsInvalid(Seq(FileNotFound.of("src/main/resources/META-INF/config/dzedze")));
    }

    @Test
    void should_parse_file_in_file_system() {
        // Given
        val lawn = expectedLawn();
        when(validator.validate(any())).thenReturn(Valid(lawn));

        // When
        val errorsOrAutomower = parser.parse("src/main/resources/META-INF/config/" + GOOD_FILE_PATH);

        // Then
        assertThat(errorsOrAutomower).containsValidSame(lawn);
        verify(validator).validate(any());
    }

    @Test
    void should_parse_file_in_classpath() {
        // Given
        val lawn = expectedLawn();
        when(validator.validate(any())).thenReturn(Valid(lawn));

        // When
        val errorsOrAutomower = parser.parse(GOOD_FILE_PATH);

        // Then
        assertThat(errorsOrAutomower).containsValidSame(lawn);
        verify(validator).validate(any());
    }

    @Test
    void should_be_invalid_when_validator_returns_invalid() {
        // Given
        val error = Error.EmptyFile.of();
        when(validator.validate(any())).thenReturn(Invalid(Seq(error)));

        // When
        val errorsOrAutomower = parser.parse(GOOD_FILE_PATH);

        // Then
        assertThat(errorsOrAutomower).containsInvalid(Seq(error));
        verify(validator).validate(any());
    }

    private Lawn expectedLawn() {
        return Lawn.of(5, 5)
                .initialize(
                        LinkedSet(
                                Mower
                                        .of(1, 2, NORTH)
                                        .instructions(List(TURN_LEFT, MOVE_FORWARD, TURN_LEFT, MOVE_FORWARD, TURN_LEFT, MOVE_FORWARD, TURN_LEFT, MOVE_FORWARD, MOVE_FORWARD)),
                                Mower
                                        .of(3, 3, EAST)
                                        .instructions(List(MOVE_FORWARD, MOVE_FORWARD, TURN_RIGHT, MOVE_FORWARD, MOVE_FORWARD, TURN_RIGHT, MOVE_FORWARD, TURN_RIGHT, TURN_RIGHT, MOVE_FORWARD))
                        ));
    }

}
