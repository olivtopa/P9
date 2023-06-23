package com.mediscreen.analysis.service;

import com.mediscreen.analysis.model.Assessment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

// DiabetesAnalyzerTest
class DiabetesAnalyzer2Test {

    private static final String MALE = "M";
    private static final String FEMALE = "F";
    private final DiabetesAnalyzer analyzer = new DiabetesAnalyzer();


    static Stream<Arguments> input() {
        return Stream.of(
                of(20, 0, MALE, Assessment.NONE),
                of(20, 0, FEMALE, Assessment.NONE),
                of(20, 2, MALE, Assessment.NONE),
                of(20, 2, FEMALE, Assessment.NONE),

                of(31, 2, MALE, Assessment.BORDERLINE),
                of(31, 2, FEMALE, Assessment.BORDERLINE),
                of(34, 5, MALE, Assessment.BORDERLINE),
                of(44, 5, FEMALE, Assessment.BORDERLINE),

                of(20, 3, MALE, Assessment.IN_DANGER),
                of(20, 4, FEMALE, Assessment.IN_DANGER),
                of(34, 6, MALE, Assessment.IN_DANGER),
                of(44, 6, FEMALE, Assessment.IN_DANGER),

                of(29, 5, MALE, Assessment.EARLY_ONSET),
                of(29, 7, FEMALE, Assessment.EARLY_ONSET),
                of(34, 8, MALE, Assessment.EARLY_ONSET),
                of(44, 8, FEMALE, Assessment.EARLY_ONSET),
                of(44, 9, MALE, Assessment.EARLY_ONSET),
                of(44, 9, FEMALE, Assessment.EARLY_ONSET)
        );
    }

    @ParameterizedTest
    @MethodSource("input")
    void getAssessmentValue(int age, int numTriggerTerms, String sex, Assessment expectedAssessment) {

        Assessment assessment = analyzer.getAssessmentValue(age, numTriggerTerms, sex);

        Assertions.assertThat(assessment).isEqualTo(expectedAssessment);
    }


}