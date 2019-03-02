package com.bbc.automower.enumeration;

import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static io.vavr.API.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Orientation {
    NORTH("N") {
        @Override
        public Orientation right() {
            return EAST;
        }
        @Override
        public Orientation left() {
            return WEST;
        }
    },
    EAST("E") {
        @Override
        public Orientation right() {
            return SOUTH;
        }
        @Override
        public Orientation left() {
            return NORTH;
        }
    },
    SOUTH("S") {
        @Override
        public Orientation right() {
            return WEST;
        }
        @Override
        public Orientation left() {
            return EAST;
        }
    },
    WEST("W") {
        @Override
        public Orientation right() {
            return NORTH;
        }
        @Override
        public Orientation left() {
            return SOUTH;
        }
    };

    private final String label;

    public static Option<Orientation> getByLabel(final String label) {
        return List(values())
                .find(orientation -> orientation.label.equals(label));
    }

    public abstract Orientation right();
    public abstract Orientation left();

}
