package com.mediscreen.analysis.service;

import com.mediscreen.analysis.model.Assessment;
import com.mediscreen.analysis.model.RiskLevels;
import org.springframework.stereotype.Component;

/**
 * Configuration of the 4 risk levels
 * */

@Component
public class DiabetesAnalyzer {

    private static final String MALE_SEX = "M";
    private static final String FEMALE_SEX = "F";

    public Assessment getAssessmentValue(int age, int numTriggerTerms, String sex) {

        boolean mal = sex.equals(MALE_SEX);
        boolean female = sex.equals(FEMALE_SEX);

        if ((age > 30) && (numTriggerTerms >= RiskLevels.EARLY_ONSET_OVER_30.triggerTermsRequired))
            return RiskLevels.EARLY_ONSET_OVER_30.assessment;
        else if (((mal && (age < 30) && (numTriggerTerms >= RiskLevels.EARLY_ONSET_MALE_UNDER_30.triggerTermsRequired)))) {
            return RiskLevels.EARLY_ONSET_MALE_UNDER_30.assessment;
        } else if ((female && (age < 30) && (numTriggerTerms >= RiskLevels.EARLY_ONSET_FEMALE_UNDER_30.triggerTermsRequired))) {
            return RiskLevels.EARLY_ONSET_FEMALE_UNDER_30.assessment;
        } else if ((age > 30) && (numTriggerTerms >= RiskLevels.IN_DANGER_OVER_30.triggerTermsRequired)) {
            return RiskLevels.IN_DANGER_OVER_30.assessment;
        } else if (mal && age < 30 && numTriggerTerms >= RiskLevels.IN_DANGER_MALE_UNDER_30.triggerTermsRequired) {
            return RiskLevels.IN_DANGER_MALE_UNDER_30.assessment;
        } else if (female && age < 30 && numTriggerTerms >= RiskLevels.IN_DANGER_FEMALE_UNDER_30.triggerTermsRequired) {
            return RiskLevels.IN_DANGER_FEMALE_UNDER_30.assessment;
        } else if (age > 30 && numTriggerTerms >= RiskLevels.BORDERLINE.triggerTermsRequired) {
            return RiskLevels.BORDERLINE.assessment;
        }
        return Assessment.NONE;
    }
}
