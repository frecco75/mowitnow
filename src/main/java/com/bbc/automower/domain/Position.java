package com.bbc.automower.domain;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;

import static lombok.AccessLevel.PRIVATE;

@Value
@AllArgsConstructor(staticName = "of")
public class Position {

    @With(PRIVATE)
    int x;

    @With(PRIVATE)
    int y;

    public Position incrementX() {
        return withX(x + 1);
    }

    public Position decrementX() {
        return withX(x - 1);
    }

    public Position incrementY() {
        return withY(y + 1);
    }

    public Position decrementY() {
        return withY(y - 1);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

}
