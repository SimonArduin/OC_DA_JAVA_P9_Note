package com.medilabo.note.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    // utility method that returns how much the database size has changed since the beginning of a test
    private Integer databaseSizeChange() {
        return noteRepository.findAll().size() - databaseSizeBefore;
    }

    // utility method that extracts a Note from a MvcResult and compares it to another Note
    private Boolean resultEqualsNote(MvcResult result, Note note) throws UnsupportedEncodingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Note resultNote = objectMapper.readValue(result.getResponse().getContentAsString(), Note.class);

        return resultNote.equals(note);
    }

    // utility method that extracts a List<Note> from a MvcResult and compares it to another List<Note>
    private Boolean resultEqualsNoteList(MvcResult result, List<Note> noteList) throws UnsupportedEncodingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Note> resultNoteList = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<Note>>() {}
        );
        return resultNoteList.equals(noteList);
    }

    @Test
    public void contextLoads() {}

    @Test
    public void addNoteTest() throws Exception {
        note.setId(null);
        MvcResult result = mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(note.toJson()))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        assertTrue(resultEqualsNote(result, note));
        assertEquals(1, databaseSizeChange());

        // delete the created note
        // TODO : make this class transactional
        ObjectMapper objectMapper = new ObjectMapper();
        Note resultNote = objectMapper.readValue(result.getResponse().getContentAsString(), Note.class);
        noteRepository.delete(resultNote);
    }

    @Test
    public void findByIdTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/getbyid")
                        .param("id",noteId))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        assertTrue(resultEqualsNote(result, note));
        assertEquals(0, databaseSizeChange());
    }

    @Test
    public void findByPatientIdTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/getbypatientid")
                        .param("patientId",note.getPatientId().toString()))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        assertTrue(resultEqualsNoteList(result, noteList));
        assertEquals(0, databaseSizeChange());
    }

}
