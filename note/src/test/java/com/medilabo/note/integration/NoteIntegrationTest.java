package com.medilabo.note.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.note.TestVariables;
import com.medilabo.note.domain.Note;
import com.medilabo.note.repository.NoteRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
public class NoteIntegrationTest extends TestVariables {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    NoteRepository noteRepository;

    Integer databaseSizeBefore;

    @BeforeAll
    public void setUpGlobal() {
        initializeVariables();
        noteRepository.save(note);
        noteId = note.getId();
    }

    @AfterAll
    public void cleanUpDatabase() {
        noteRepository.deleteById(noteId);
    }

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        databaseSizeBefore = noteRepository.findAll().size();
    }

    private Integer databaseSizeChange() {
        return noteRepository.findAll().size() - databaseSizeBefore;
    }

    private Boolean resultEqualsNote(MvcResult result, Note note) throws UnsupportedEncodingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Note resultNote = objectMapper.readValue(result.getResponse().getContentAsString(), Note.class);

        return resultNote.equals(note);
    }

    @Test
    public void contextLoads() {}

    @Test
    public void addNoteTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(note.toJson().toString()))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        note.setId(noteId);
        assertTrue(resultEqualsNote(result, note));
        assertEquals(1, databaseSizeChange());
    }

  /*  @Test
    public void findByIdTest() throws Exception {
        assertEquals(note, noteController.findById(noteId));
    }

    @Test
    public void findByPatIdTest() throws Exception {
        Object result = noteController.findByPatId(noteId);

        assertEquals(List.of(note), result);
    }
*/
}
