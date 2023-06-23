package com.mediscreen.analysis.service;

import com.mediscreen.analysis.model.Assessment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

class DiabetesAnalyzerTest {

    private static final String MALE = "M";
    private static final String FEMALE = "F";
    private final DiabetesAnalyzer analyzer = new DiabetesAnalyzer();

    @Test
    void getAssessmentValueNoTriggerTerm() {

        Assessment assessment = analyzer.getAssessmentValue(20, 0, "M");

        assertThat(assessment).isEqualTo(Assessment.NONE);
    }

    @Test
    void found8TermsUnder30() {

        Assessment assessment = analyzer.getAssessmentValue(31,8,"M");

        assertThat(assessment).isEqualTo(Assessment.EARLY_ONSET);
    }

    @Test
    void found5MalUnder30() {

       Assessment assessment = analyzer.getAssessmentValue(29,5,"M");

       assertThat(assessment).isEqualTo(Assessment.EARLY_ONSET);
    }

    @Test
    void found5FemaleUnder30() {

        Assessment assessment = analyzer.getAssessmentValue(29,5,"F");

        assertThat(assessment).isNotEqualTo(Assessment.EARLY_ONSET);

    }

    @Test
    void found7FemaleUnder30() {

        Assessment assessment = analyzer.getAssessmentValue(29,7,"F");

        assertThat(assessment).isEqualTo(Assessment.EARLY_ONSET);

    }

    @Test
    void found6Over30() {

        Assessment assessment = analyzer.getAssessmentValue(31,6,"F");

        assertThat(assessment).isEqualTo(Assessment.IN_DANGER);
    }

    @Test
    void found3FemaleUnder3O() {

        Assessment assessment = analyzer.getAssessmentValue(15,3,"F");

        assertThat(assessment).isNotEqualTo(Assessment.IN_DANGER);
    }

    @Test
    void found3MalUnder3O() {

        Assessment assessment = analyzer.getAssessmentValue(15,3,"M");

        assertThat(assessment).isEqualTo(Assessment.IN_DANGER);
    }
    @Test
    void found4FemaleUnder3O() {

        Assessment assessment = analyzer.getAssessmentValue(15,4,"F");

        assertThat(assessment).isEqualTo(Assessment.IN_DANGER);
    }

    @Test
    void found2Over3O() {

        Assessment assessment = analyzer.getAssessmentValue(50,2,"F");

        assertThat(assessment).isEqualTo(Assessment.BORDERLINE);
    }

    @Test
    void found0FemaleUnder3O() {

        Assessment assessment = analyzer.getAssessmentValue(15,0,"F");

        assertThat(assessment).isNotEqualTo(Assessment.BORDERLINE);
    }

}