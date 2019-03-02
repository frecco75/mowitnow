package com.bbc.automower.domain;

import lombok.val;
import org.junit.jupiter.api.Test;

import static com.bbc.automower.enumeration.Instruction.MOVE_FORWARD;
import static com.bbc.automower.enumeration.Instruction.TURN_LEFT;
import static com.bbc.automower.enumeration.Orientation.NORTH;
import static com.bbc.automower.enumeration.Orientation.SOUTH;
import static io.vavr.API.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class LawnTest {

    @Test
    void should_throw_illegalargumentexception_when_x_is_negative() {
        // Given
        val throwable = catchThrowable(() -> Lawn.of(-1, 1));

        // Then
        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Arguments must be >= 0");
    }

    @Test
    void should_throw_illegalargumentexception_when_y_is_negative() {
        // Given
        val throwable = catchThrowable(() -> Lawn.of(1, -1));

        // Then
        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Arguments must be >= 0");
    }

    @org.junit.jupiter.api.Test
    public void should_initialize_lawn() {
        // Given
        val lawn = Lawn.of(5, 5);
        val mowers = Set(Mower
                .of(1, 2, NORTH)
                .instructions(List(TURN_LEFT, MOVE_FORWARD, TURN_LEFT, MOVE_FORWARD, TURN_LEFT, MOVE_FORWARD, TURN_LEFT, MOVE_FORWARD, MOVE_FORWARD)));

        // When
        val newLawn = lawn.initialize(mowers);

        // Then
        assertThat(newLawn.getMowers()).isEqualTo(mowers);
        assertThat(newLawn.getWidth()).isEqualTo(lawn.getWidth());
        assertThat(newLawn.getHeight()).isEqualTo(lawn.getHeight());
    }

    @Test
    void should_execute_instructions() {
        // Given
        val lawn = Lawn.of(5, 5)
                .initialize(
                        LinkedSet(Mower
                                .of(1, 2, NORTH)
                                .instructions(List(TURN_LEFT, MOVE_FORWARD, TURN_LEFT, MOVE_FORWARD, TURN_LEFT, MOVE_FORWARD, TURN_LEFT, MOVE_FORWARD, MOVE_FORWARD))));

        // When
        val newLawn = lawn.execute();

        // Then
        assertThat(newLawn.getWidth()).isEqualTo(lawn.getWidth());
        assertThat(newLawn.getHeight()).isEqualTo(lawn.getHeight());

        assertThat(newLawn.getMowers()).hasSize(1);
        assertThat(newLawn.getMowers().head().getOrientation()).isSameAs(NORTH);
        assertThat(newLawn.getMowers().head().getPosition()).isEqualTo(Position.of(1, 3));
    }

    @Test
    void should_do_nothing_when_mower_without_instruction() {
        // Given
        val lawn = Lawn.of(5, 5)
                .initialize(
                        Set(Mower.of(1, 2, NORTH)));

        // When
        val newLawn = lawn.execute();

        // Then
        assertSameLawn(newLawn, lawn);
    }

    @Test
    void should_do_nothing_when_mower_try_to_go_outside() {
        // Given
        val lawn = Lawn.of(5, 5)
                .initialize(
                        Set(
                                Mower.of(0, 0, SOUTH)
                                        .instructions(List(MOVE_FORWARD))));

        // When
        val newLawn = lawn.execute();

        // Then
        assertSameLawn(newLawn, lawn);
    }

    private void assertSameLawn(final Lawn l, final Lawn o) {
        assertThat(l.getHeight()).isEqualTo(o.getHeight());
        assertThat(l.getWidth()).isEqualTo(o.getWidth());
        assertThat(l.getMowers()).hasSize(o.getMowers().size());

        l.getMowers().forEach(mower ->
                {
                    // When
                    val maybeMower = o.getMowers()
                            .find(mower2 -> mower.getUuid().equals(mower2.getUuid()));

                    // Then
                    assertThat(maybeMower).isNotEmpty();
                    maybeMower
                            .forEach(mower2 -> {
                                        assertThat(mower.getPosition()).isEqualTo(mower2.getPosition());
                                        assertThat(mower.getLocation()).isEqualTo(mower2.getLocation());
                                    }
                            );
                }
        );
    }

}
