package com.medilabo.note.unit;

import com.medilabo.note.TestVariables;
import com.medilabo.note.domain.Note;
import com.medilabo.note.repository.NoteRepository;
import com.medilabo.note.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = NoteService.class)
public class NoteServiceTest extends TestVariables {

    @Autowired
    NoteService noteService;

    @MockBean
    NoteRepository noteRepository;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        when(noteRepository.save(any(Note.class))).thenReturn(note);
        when(noteRepository.findById(any(String.class))).thenReturn(Optional.of(note));
        when(noteRepository.findByPatientId(any(Integer.class))).thenReturn(noteList);
    }

    @Nested
    public class addNoteTests {

        @Test
        public void addNoteTest() throws Exception {
            assertEquals(note, noteService.addNote(note));
        }

    }

    @Nested
    public class findByIdTests {

        @Test
        public void findByIdTest() throws Exception {
            assertEquals(note, noteService.findById(note.getId()));
        }

    }

    @Nested
    public class findByPatientIdTests {

        @Test
        public void findByPatientIdTest() throws Exception {
            assertEquals(noteList, noteService.findByPatientId(note.getPatientId()));
        }

    }

}
