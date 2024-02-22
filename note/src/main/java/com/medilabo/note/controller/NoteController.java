package com.medilabo.note.controller;

import com.medilabo.note.domain.Note;
import com.medilabo.note.service.NoteService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class NoteController {

    @Autowired
    NoteService noteService;

    public Note findById(String id) throws Exception {
        return noteService.findById(id);
    }

    public List<Note> findByPatId(Integer patId) throws Exception {
        return noteService.findByPatId(patId);
    }
}
