package com.medilabo.note;

import com.medilabo.note.domain.Note;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestVariables {

    public Note note;
    public List<Note> noteList;

    public void initializeVariables() {
        note = new Note(1, "patient", "note");
        noteList = List.of(note);
    }

}
