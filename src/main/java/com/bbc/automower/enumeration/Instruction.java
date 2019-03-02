package com.bbc.automower.enumeration;

import com.bbc.automower.domain.Movable;
import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static io.vavr.API.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Instruction {
    MOVE_FORWARD('F') {
        @Override
        public <T extends Movable<T>> T apply(final Movable<T> movable) {
            return movable.moveForward();
        }
    },
    TURN_RIGHT('R') {
        @Override
        public <T extends Movable<T>> T apply(final Movable<T> movable) {
            return movable.turnRight();
        }
    },
    TURN_LEFT('L') {
        @Override
        public <T extends Movable<T>> T apply(final Movable<T> movable) {
            return movable.turnLeft();
        }
    };

    private final char label;

    public static Option<Instruction> getByLabel(final char label) {
        return List(values())
                .find(move -> move.label == label);
    }

    public abstract <T extends Movable<T>> T apply(Movable<T> movable);
}
