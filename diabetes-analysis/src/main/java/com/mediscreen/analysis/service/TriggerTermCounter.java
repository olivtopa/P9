package com.mediscreen.analysis.service;

import com.mediscreen.analysis.model.DrNote;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.List;
import java.util.Set;

@Component
public class TriggerTermCounter {
    private static final Set<String> TRIGGER_TERMS = Set.of(" Hémoglobine A1C", " Microalbumine", " Taille", "Poids", "Fumeur",
            "Anormal", "Cholestérol", "Vertige", "Rechute", "Réaction", "Anticorps");

    // For each notes of DrNote, count each term of the Set TRIGGER_TERMS
    public int getNumberOfTriggerTerms(List<DrNote> notes) {
        int numTriggerTerms = 0;
        for (DrNote note : notes) {
            for (String term : TRIGGER_TERMS) {
                if (convertNote(note.getNote()).contains(convertNote(term))) {
                    numTriggerTerms++;
                }
            }
        }
        return numTriggerTerms;
    }

    // remove accents and capitalize String
    String convertNote(String note) {
        return Normalizer.normalize(note, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toUpperCase();
    }
}
