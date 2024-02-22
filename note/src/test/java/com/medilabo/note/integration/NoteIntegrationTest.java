package com.medilabo.note.integration;

import com.medilabo.note.TestVariables;
import com.medilabo.note.controller.NoteController;
import com.medilabo.note.domain.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BindingResult;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class NoteIntegrationTest extends TestVariables {

    @Autowired
    NoteController noteController;

    @MockBean
    BindingResult bindingResult;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        note = new Note(1, "TestNone", "Le patient déclare qu'il 'se sent très bien'. Poids égal ou inférieur au poids recommandé");
        note.setId("65d5fbfaac8f88b4829621aa");
        when(bindingResult.hasErrors()).thenReturn(false);
    }

    @Test
    public void addNoteTest() throws Exception {
        assertEquals(note, noteController.addNote(note, bindingResult));
    }

    @Test
    public void findByIdTest() throws Exception {
        assertEquals(note, noteController.findById(note.getId()));
    }

    @Test
    public void findByPatIdTest() throws Exception {
        List<Note> result = noteController.findByPatId(note.getPatId());

        assertEquals(List.of(note), result);
    }

}
