package com.medilabo.note.controller;

import com.medilabo.note.domain.Note;
import com.medilabo.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class NoteController {

    @Autowired
    NoteService noteService;

    public Note findById(String id) throws Exception {
        return noteService.findById(id);
    }
}
