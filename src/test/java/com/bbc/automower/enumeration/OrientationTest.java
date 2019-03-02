package com.bbc.automower.enumeration;

import org.junit.jupiter.api.Test;

import static com.bbc.automower.enumeration.Orientation.*;
import static io.vavr.API.None;
import static io.vavr.API.Some;
import static org.assertj.core.api.Assertions.assertThat;

class OrientationTest {
    
    @Test
    void should_move_right() {
        assertThat(NORTH.right()).isSameAs(EAST);
        assertThat(EAST.right()).isSameAs(SOUTH);
        assertThat(SOUTH.right()).isSameAs(WEST);
        assertThat(WEST.right()).isSameAs(NORTH);
    }
    
    @Test
    void should_move_left() {
        assertThat(NORTH.left()).isSameAs(WEST);
        assertThat(WEST.left()).isSameAs(SOUTH);
        assertThat(SOUTH.left()).isSameAs(EAST);
        assertThat(EAST.left()).isSameAs(NORTH);
    }

    @Test
    void should_get_label() {
        assertThat(NORTH.getLabel()).isEqualTo("N");
        assertThat(WEST.getLabel()).isEqualTo("W");
        assertThat(SOUTH.getLabel()).isEqualTo("S");
        assertThat(EAST.getLabel()).isEqualTo("E");
    }

    @Test
    void should_get_by_label() {
        assertThat(getByLabel("N")).isEqualTo(Some(NORTH));
        assertThat(getByLabel("W")).isEqualTo(Some(WEST));
        assertThat(getByLabel("S")).isEqualTo(Some(SOUTH));
        assertThat(getByLabel("E")).isEqualTo(Some(EAST));
        assertThat(getByLabel("UNKNOWN")).isEqualTo(None());
    }
    
}
