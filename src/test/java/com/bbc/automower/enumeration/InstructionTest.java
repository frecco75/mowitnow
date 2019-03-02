package com.bbc.automower.enumeration;

import com.bbc.automower.domain.Mower;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.bbc.automower.enumeration.Instruction.*;
import static io.vavr.API.None;
import static io.vavr.API.Some;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InstructionTest {

    @Mock
    private Mower mower;

    @Test
    void should_get_by_label() {
        assertThat(getByLabel('F')).isEqualTo(Some(MOVE_FORWARD));
        assertThat(getByLabel('R')).isEqualTo(Some(TURN_RIGHT));
        assertThat(getByLabel('L')).isEqualTo(Some(TURN_LEFT));
        assertThat(getByLabel('X')).isEqualTo(None());
    }

    @Test
    void should_execute_move_forward_when_forward() {
        // When
        MOVE_FORWARD.apply(mower);

        // Then
        verify(mower).moveForward();
    }

    @Test
    void should_execute_turn_right_when_right() {
        // When
        TURN_RIGHT.apply(mower);

        // Then
        verify(mower).turnRight();
    }

    @Test
    void should_execute_turn_left_when_left() {
        // When
        TURN_LEFT.apply(mower);

        // Then
        verify(mower).turnLeft();
    }

}