package com.medilabo.note.unit;

import com.medilabo.note.TestVariables;
import com.medilabo.note.service.NoteService;
import com.medilabo.note.controller.NoteController;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = NoteController.class)
public class NoteControllerTest extends TestVariables {

    @Autowired
    NoteController noteController;

    @MockBean
    NoteService noteService;

    @BeforeEach
    public void setUpPerTest() throws Exception {
        initializeVariables();
        note.setId("noteId");
        when(noteService.findById(any(String.class))).thenReturn(note);
        when(noteService.findByPatId(any(Integer.class))).thenReturn(noteList);
    }

    @Nested
    public class findByIdTests {

        @Test
        public void findByIdTest() throws Exception {
            assertEquals(note, noteController.findById(note.getId().toString()));
        }

    }

    @Nested
    public class findByPatIdTests {

        @Test
        public void findByPatIdTest() throws Exception {
            assertEquals(noteList, noteController.findByPatId(note.getPatId()));
        }

    }

}
