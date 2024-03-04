package com.medilabo.note;

import com.medilabo.note.domain.Note;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestVariables {

    public Note note;

    // stores the id of the note created for integration tests
    public String noteId;
    public List<Note> noteList;
    public String stringBlank;

    public void initializeVariables() {
        note = new Note(0, "patient", "note");
        note.setId("noteId");
        noteList = List.of(note);
        stringBlank = " ";
    }

}
