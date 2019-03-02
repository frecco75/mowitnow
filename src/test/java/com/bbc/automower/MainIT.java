package com.bbc.automower;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.bbc.automower.Constants.BAD_LAWN_FILE_PATH;
import static com.bbc.automower.Constants.GOOD_FILE_PATH;
import static com.bbc.automower.Main.main;
import static com.bbc.automower.TestAppender.clear;
import static com.bbc.automower.TestAppender.messages;
import static org.assertj.core.api.Assertions.assertThat;

class MainIT {

    @BeforeEach
    void setUp() {
        clear();
    }

    @Test
    void should_execute_automower_program() {
        // Given
        val args = new String[]{};

        // When
        main(args);

        // Then
        assertThatMowersHavePrintedTheirPositions();
    }

    @Test
    void should_execute_automower_program_with_file() {
        // Given
        val args = new String[]{GOOD_FILE_PATH};

        // When
        main(args);

        // Then
        assertThatMowersHavePrintedTheirPositions();
    }

    @Test
    void should_print_errors() {
        // Given
        val args = new String[]{BAD_LAWN_FILE_PATH};

        // When
        main(args);

        // Then
        assertThat(messages)
                .isNotNull()
                .hasSize(1)
                .containsExactly("Line 1: the line contains 3 elements instead of 2");
    }

    private void assertThatMowersHavePrintedTheirPositions() {
        assertThat(messages)
                .isNotNull()
                .hasSize(2)
                .containsExactly("1 3 N", "5 1 E");
    }

}
