package com.mediscreen.analysis.model;

/**
 * For DiabetesAnalyzer configuration
 *
 * Provide the riskLevels:
 * ○ aucun risque (None) - Le dossier du patient ne contient aucune note du
 * médecin contenant les déclencheurs (terminologie),
 * ○ risque limité (Borderline) - Le dossier du patient contient deux déclencheurs et
 * le patient est âgé de plus de 30 ans,
 * ○ danger (In Danger) - Dépend de l'âge et du sexe du patient. Si le patient est un
 * homme et a moins de 30 ans, alors trois termes déclencheurs doivent être
 * présents. Si le patient est une femme et a moins de 30 ans, il faudra quatre
 * termes déclencheurs. Si le patient a plus de 30 ans, alors il en faudra six.
 * ○ apparition précoce (Early onset) - Encore une fois, cela dépend de l'âge et du
 * sexe. Si le patient est un homme et a moins de 30 ans, alors cinq termes
 * déclencheurs sont nécessaires. Si le patient est une femme et a moins de 30
 * ans, il faudra sept termes déclencheurs. Si le patient a plus de 30 ans, alors il en
 * faudra huit ou plus.
 */
public enum RiskLevels {
    BORDERLINE(2, Assessment.BORDERLINE),
    IN_DANGER_MALE_UNDER_30(3, Assessment.IN_DANGER),
    IN_DANGER_FEMALE_UNDER_30(4, Assessment.IN_DANGER),
    IN_DANGER_OVER_30(6, Assessment.IN_DANGER),
    EARLY_ONSET_MALE_UNDER_30(5, Assessment.EARLY_ONSET),
    EARLY_ONSET_FEMALE_UNDER_30(7, Assessment.EARLY_ONSET),
    EARLY_ONSET_OVER_30(8, Assessment.EARLY_ONSET);

    public final int triggerTermsRequired;
    public final Assessment assessment;

    RiskLevels(int triggerTermsRequired, Assessment assessment) {
        this.triggerTermsRequired = triggerTermsRequired;
        this.assessment = assessment;
    }
}
