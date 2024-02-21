package com.medilabo.note;

import com.medilabo.note.domain.Note;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestVariables {

    public Note note;

    public void initializeVariables() {
        note = new Note(1, "patient", "note");
    }

}
