package com.medilabo.note.controller;

import com.medilabo.note.domain.Note;
import com.medilabo.note.service.NoteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class NoteController {

    @Autowired
    NoteService noteService;

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    @GetMapping("getbyid")
    public ResponseEntity findById(String id) throws Exception {
        logger.info("received request to find note with id " + id);
        return ResponseEntity.ok().body(noteService.findById(id));
    }

    @GetMapping("getbypatid")
    public ResponseEntity findByPatId(Integer patId) throws Exception {
        logger.info("received request to find all notes with patId " + patId);
        return ResponseEntity.ok().body(noteService.findByPatId(patId));
    }

    @PostMapping("add")
    public ResponseEntity addNote(@Valid @RequestBody Note note, BindingResult bindingResult) {
        // if note is valid, add it to database
        if (!bindingResult.hasErrors()) {
            logger.info("received request to add note with patId " + note.getPatId());
            return ResponseEntity.created(null).body(noteService.addNote(note));
        }

        // if note is not valid, log errors
        logger.error("received incorrect request to add patient : " + note.toJson() + ", errors are : " + bindingResult.getFieldErrors());
        String errorMessage = new String();
        for(FieldError error : bindingResult.getFieldErrors()) {
            errorMessage = errorMessage + "error in field : " + error.getField() + ", rejected value : " + error.getRejectedValue() + "\n";
        }
        return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
    }

}
