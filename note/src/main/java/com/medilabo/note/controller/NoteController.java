package com.medilabo.note.controller;

import com.medilabo.note.domain.Note;
import com.medilabo.note.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NoteController {

    @Autowired
    NoteService noteService;

    @GetMapping("getbyid")
    public ResponseEntity findById(String id) throws Exception {
        return ResponseEntity.ok().body(noteService.findById(id));
    }

    @GetMapping("getbypatid")
    public ResponseEntity findByPatId(Integer patId) throws Exception {
        return ResponseEntity.ok().body(noteService.findByPatId(patId));
    }

    @PostMapping("add")
    public ResponseEntity addNote(@Valid @RequestBody Note note, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return ResponseEntity.created(null).body(noteService.addNote(note));
        }
        String errorMessage = new String();
        for(FieldError error : bindingResult.getFieldErrors()) {
            errorMessage = errorMessage + "error in field : " + error.getField() + ", rejected value : " + error.getRejectedValue() + "\n";
        }
        return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
    }

}
