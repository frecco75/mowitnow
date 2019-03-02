package com.bbc.automower.behavior;

import com.bbc.automower.domain.Lawn;
import com.bbc.automower.domain.Mower;
import com.bbc.automower.enumeration.Instruction;
import com.bbc.automower.enumeration.Orientation;
import io.vavr.collection.CharSeq;
import io.vavr.control.Option;
import lombok.val;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.LinkedList;
import java.util.regex.Pattern;

import static io.vavr.API.Try;
import static io.vavr.collection.HashSet.ofAll;
import static io.vavr.control.Option.none;
import static java.lang.Integer.parseInt;
import static java.util.function.Function.identity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class AutoMowerSteps {

    // Constants
    //----------------------------------------------------

    private final static Pattern REGEX_GET_INT_PATTERN = Pattern.compile("^(\\d+)\\S+$");


    // Context
    //----------------------------------------------------

    private Lawn lawn;
    private java.util.List<Mower> mowers = new LinkedList<>();


    // Behavior Driven Development
    //----------------------------------------------------

    @Given("a $width by $height lawn")
    public void lawn(final int width, final int height) {
        lawn = Lawn.of(width, height);
    }

    @Given("a mower at position $x $y $orientationLabel with instructions $instructions")
    public void mowerAtPosition(final int x, final int y, final String orientationLabel, final String instructions) {
        mowers.add(
                Mower
                        .of(x, y, Orientation.getByLabel(orientationLabel).get())
                        .instructions(CharSeq
                                .of(instructions)
                                .map(Instruction::getByLabel)
                                .flatMap(identity())
                                .toList()));
    }

    @When("the mowers execute their instructions")
    public void mowersExecuteInstructions() {
        lawn = lawn.initialize(ofAll(mowers))
                .execute();
    }

    @Then("the $numero mower should be at position $x $y $orientationLabel")
    public void mowerShouldBeAtPosition(String number, int x, int y, String orientationLabel) {
        extractIndex(number)
                .map(mowers::get)
                .forEach(mower -> {
                    val maybeMower = lawn
                            .getMowers()
                            .find(mower::equals);

                    if (maybeMower.isEmpty()) {
                        fail("Mower " + number + " not found in lawn at position (" + x + "," + y + ")");
                    }

                    maybeMower.forEach(mower2 -> {
                        assertThat(mower2.getPosition()).isNotNull();
                        assertThat(x).isEqualTo(mower2.getPosition().getX());
                        assertThat(y).isEqualTo(mower2.getPosition().getY());
                        assertThat(Orientation.getByLabel(orientationLabel))
                                .isNotEmpty()
                                .containsExactly(mower2.getOrientation());
                    });
                });
    }


    // Private methods
    //----------------------------------------------------

    private Option<Integer> extractIndex(final String numero) {
        val matcher = REGEX_GET_INT_PATTERN.matcher(numero);
        return matcher.matches() ?
                Try(() -> parseInt(matcher.group(1)) - 1)
                        .toOption() :
                none();
    }

}
