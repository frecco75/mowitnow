package com.bbc.automower.behavior;


import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;
import lombok.val;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterConverters.EnumConverter;
import org.junit.runner.RunWith;

@RunWith(JUnitReportingRunner.class)
public class AutoMowerStories extends JUnitStory {

    @Override
    public Configuration configuration() {
        val configuration = new MostUsefulConfiguration()
                .useStoryLoader(new LoadFromClasspath())
                .useStoryReporterBuilder(
                        new StoryReporterBuilder()
                                .withDefaultFormats()
                                .withFormats(Format.CONSOLE, Format.TXT))
                .usePendingStepStrategy(new FailingUponPendingStep());

        configuration
                .parameterConverters()
                .addConverters(new EnumConverter());

        return configuration;
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new AutoMowerSteps());
    }

}
