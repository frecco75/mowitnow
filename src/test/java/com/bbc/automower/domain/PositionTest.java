package com.bbc.automower.domain;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class PositionTest {

    @Test
    void should_increment_x() {
        // Given
        val position = Position.of(2, 5);

        // When
        val newPosition = position.incrementX();

        // Then
        assertThatPositionIsEqualToExpected(position, newPosition, 3, 5);
    }

    @Test
    void should_decrement_x() {
        // Given
        val position = Position.of(2, 5);

        // When
        val newPosition = position.decrementX();

        // Then
        assertThatPositionIsEqualToExpected(position, newPosition, 1, 5);
    }

    @Test
    void should_increment_y() {
        // Given
        val position = Position.of(2, 5);

        // When
        val newPosition = position.incrementY();

        // Then
        assertThatPositionIsEqualToExpected(position, newPosition, 2, 6);
    }

    @Test
    void should_decrement_y() {
        // Given
        val position = Position.of(2, 5);

        // When
        val newPosition = position.decrementY();

        //Asserts
        assertThatPositionIsEqualToExpected(position, newPosition, 2, 4);
    }


    // Private methods
    //-------------------------------------------------------------------------

    private void assertThatPositionIsEqualToExpected(final Position before, final Position after, int expectedX, int expectedY) {
        assertThat(after).isNotSameAs(before); // Not same references -> position is immutable
        assertThat(after.getX()).isEqualTo(expectedX);
        assertThat(after.getY()).isEqualTo(expectedY);
    }
}
