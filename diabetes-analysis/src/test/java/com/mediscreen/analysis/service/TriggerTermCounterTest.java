package com.mediscreen.analysis.service;

import com.mediscreen.analysis.model.DrNote;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TriggerTermCounterTest {

    private final TriggerTermCounter counter = new TriggerTermCounter();

    @Test
    void getNumberOfTriggerTermsEmptyList() {

        int numberOfTriggerTerms = counter.getNumberOfTriggerTerms(new ArrayList<>());

        assertThat(numberOfTriggerTerms).isZero();
    }

    @Test
    void getNumberOfTriggerTermsNoMatchInDrNote() {
        // DONE
        DrNote drNote = new DrNote();
        drNote.setNote("La note ne contient aucun terme déclencheur");
        List<DrNote> notes = List.of(drNote);

        int numberOfTriggerTerms = counter.getNumberOfTriggerTerms(notes);

        assertThat(numberOfTriggerTerms).isZero();
    }

    @Test
    void getNumberOfTriggerTerms() {

        DrNote drNote = new DrNote();
        drNote.setNote("La note contient FumeUr et Réaction.");
        DrNote drNote2 = new DrNote();
        drNote2.setNote("Anormal");
        List<DrNote> notes = List.of(drNote, drNote2);

        int numberOfTriggerTerms = counter.getNumberOfTriggerTerms(notes);

        assertThat(numberOfTriggerTerms).isEqualTo(3);
    }

}