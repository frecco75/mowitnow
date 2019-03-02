package com.bbc.automower.domain;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Set;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import static io.vavr.API.*;
import static java.util.function.Predicate.not;
import static lombok.AccessLevel.PRIVATE;


@Value
@AllArgsConstructor(access = PRIVATE)
@Slf4j
public class Lawn {

    int width;
    int height;

    @With(PRIVATE)
    Set<Mower> mowers;

    @With(PRIVATE)
    List<MowerEvent> history;

    public static Lawn of(final int width, final int height) {
        return new Lawn(width, height);
    }

    private Lawn(final int width, final int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Arguments must be >= 0");
        }

        this.width = width;
        this.height = height;
        mowers = LinkedSet();
        history = List();
    }

    public Lawn initialize(final Set<Mower> mowers) {
        return withMowers(this.mowers.addAll(mowers));
    }

    public Lawn execute() {
        return mowers.foldLeft(
                this,
                (l, m) -> l.executeInstructions(m)
                        .map2(Mower::printPosition)
                        ._1
        );
    }

    private Tuple2<Lawn, Mower> executeInstructions(final Mower mower) {
        return mower.executeInstruction()
                .map(m -> move(mower, m))
                .peek(lm -> log.debug("Mower {} at position {}", lm._2.getUuid(), lm._2.getLocation()))
                .map(lm -> lm._1.executeInstructions(lm._2))
                .getOrElse(() -> Tuple(this, mower));
    }

    private Tuple2<Lawn, Mower> move(final Mower source, final Mower target) {
        val mower = inside(target.getPosition()) && available(source, target.getPosition()) ?
                target :
                source.removeInstruction();

        return Tuple(
                withHistory(history.append(MowerEvent.of(source, mower)))
                        .withMowers(mowers
                                .remove(source)
                                .add(mower)),
                mower);
    }

    private boolean inside(final Position position) {
        return position.getX() >= 0 && position.getX() <= width
                && position.getY() >= 0 && position.getY() <= height;
    }

    private boolean available(final Mower mower, final Position position) {
        return !mowers
                .filter(not(mower::equals))
                .map(Mower::getPosition)
                .contains(position);
    }

}
