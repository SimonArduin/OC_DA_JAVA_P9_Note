package com.medilabo.note.service;

import com.medilabo.note.domain.Note;
import com.medilabo.note.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public Note addNote(Note note) {
        return noteRepository.save(note);
    }

    public Note findById(String id) throws Exception {
        return noteRepository.findById(id).orElseThrow(() -> new Exception("Note with id " + id + " not found"));
    }

    public List<Note> findByPatientId(Integer patientId) {
        return noteRepository.findByPatientId(patientId);
    }

}
