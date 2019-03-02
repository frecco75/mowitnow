package com.bbc.automower.domain;

import com.bbc.automower.enumeration.Orientation;
import lombok.val;
import org.junit.jupiter.api.Test;

import static com.bbc.automower.enumeration.Instruction.*;
import static com.bbc.automower.enumeration.Orientation.*;
import static io.vavr.API.List;
import static io.vavr.API.Tuple;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class MowerTest {

    @Test
    void should_add_instructions() {
        // Given
        val mower = Mower.of(1, 2, NORTH);
        val instructions = List(MOVE_FORWARD);

        // When
        val newMower = mower.instructions(instructions);

        // Then
        assertSameUuidAndDifferentInstances(mower, newMower);
        assertThat(newMower.getInstructions()).isEqualTo(instructions);
        assertThat(newMower.getPosition()).isEqualTo(mower.getPosition());
        assertThat(newMower.getOrientation()).isEqualTo(mower.getOrientation());
    }

    @Test
    void should_turn_left() {
        testTurnLeft(NORTH, WEST);
        testTurnLeft(WEST, SOUTH);
        testTurnLeft(SOUTH, EAST);
        testTurnLeft(EAST, NORTH);
    }

    @Test
    void should_turn_right() {
        testTurnRight(WEST, NORTH);
        testTurnRight(SOUTH, WEST);
        testTurnRight(EAST, SOUTH);
        testTurnRight(NORTH, EAST);
    }

    @Test
    void should_move_forward() {
        testMoveForwardWhenNorth();
        testMoveForwardWhenSouth();
        testMoveForwardWhenEast();
        testMoveForwardWhenWest();
    }

    @Test
    void should_be_equal_if_same_id() {
        // Given
        val mower = Mower.of(1, 2, NORTH);

        // When
        val newMower = mower.moveForward();

        // Then
        assertThat(newMower).isNotSameAs(mower);
        assertThat(newMower.getUuid()).isEqualTo(mower.getUuid());
        assertThat(
                Tuple(newMower.getPosition(), newMower.getOrientation())
        ).isNotEqualTo(
                Tuple(mower.getPosition(), mower.getOrientation())
        );
    }

    @Test
    void should_execute_next_instruction() {
        // Given
        val mower = Mower.of(1, 2, NORTH)
                .instructions(List(TURN_LEFT, TURN_RIGHT));

        // When
        val maybeMower1 = mower.executeInstruction();

        // Then
        assertThat(maybeMower1).hasValueSatisfying(
                mower1 -> {
                    assertSameUuidAndDifferentInstances(mower, mower1);
                    assertThat(mower1.getOrientation()).isSameAs(WEST);
                    assertThat(mower1.getPosition()).isEqualTo(Position.of(1, 2));
                    assertThat(mower1.getInstructions().size()).isEqualTo(mower.getInstructions().size() - 1);
                }
        );
    }

    @Test
    void should_return_none_when_trying_to_execute_next_instruction_without_instruction() {
        // Given
        val mower = Mower.of(1, 2, NORTH);

        // When
        val maybeMower1 = mower.executeInstruction();

        // Then
        assertThat(maybeMower1).isEmpty();
    }

    @Test
    void should_remove_next_instruction_when_no_action() {
        // Given
        val mower = Mower.of(1, 2, NORTH)
                .instructions(List(TURN_LEFT, TURN_RIGHT));

        // When
        val mower1 = mower.removeInstruction();

        // Then
        assertThat(mower1.getInstructions())
                .hasSize(1)
                .containsExactly(TURN_RIGHT);
    }

    @Test
    void should_get_location() {
        // Given
        val mower = Mower.of(1, 2, NORTH);

        // When
        val location = mower.getLocation();

        // Then
        assertThat(location).isEqualTo("(1, 2, N)");
    }

    // Private methods
    //-------------------------------------------------------------------------

    private void testMoveForwardWhenNorth() {
        // Given
        val mower = Mower.of(5, 5, NORTH);

        // Then
        testMoveForward(mower, 5, 6);
    }

    private void testMoveForwardWhenSouth() {
        // Given
        val mower = Mower.of(5, 5, SOUTH);

        // Then
        testMoveForward(mower, 5, 4);
    }

    private void testMoveForwardWhenEast() {
        // Given
        val mower = Mower.of(5, 5, EAST);

        // Then
        testMoveForward(mower, 6, 5);
    }

    private void testMoveForwardWhenWest() {
        // Given
        val mower = Mower.of(5, 5, WEST);

        // Then
        testMoveForward(mower, 4, 5);
    }

    private void testTurnLeft(final Orientation initial, final Orientation expected) {
        // Given
        val mower = Mower.of(5, 5, initial);

        // When
        val newMower = mower.turnLeft();

        // Then
        assertSameUuidAndDifferentInstances(mower, newMower);
        assertThat(expected).isEqualTo(newMower.getOrientation());
    }

    private void testTurnRight(final Orientation initial, final Orientation expected) {
        // Given
        val mower = Mower.of(5, 5, initial);

        // When
        val newMower = mower.turnRight();

        // Then
        assertSameUuidAndDifferentInstances(mower, newMower);
        assertThat(expected).isSameAs(newMower.getOrientation());
    }

    private void testMoveForward(final Mower mower, int expectedX, int expectedY) {
        // Given
        val newMower = mower.moveForward();

        // Then
        assertSameUuidAndDifferentInstances(mower, newMower);
        assertThat(expectedX).isEqualTo(newMower.getPosition().getX());
        assertThat(expectedY).isEqualTo(newMower.getPosition().getY());
    }

    private void assertSameUuidAndDifferentInstances(final Mower mower1, final Mower mower2) {
        assertThat(mower1).isNotSameAs(mower2);
        assertThat(mower1).isEqualTo(mower2); //Same business object (UUID)
    }

}
