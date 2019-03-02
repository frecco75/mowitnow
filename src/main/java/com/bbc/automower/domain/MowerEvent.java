package com.bbc.automower.domain;

import com.bbc.automower.enumeration.Instruction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MowerEvent {
    Instruction instruction;
    Mower before;
    Mower after;

    public static MowerEvent of(final Mower before, final Mower after) {
        return before.getInstructions()
                .headOption()
                .map(instruction -> new MowerEvent(instruction, before, after))
                .getOrElseThrow(() -> new RuntimeException("Should not happen : missing instruction for mower " + before.getUuid()));
    }
}
