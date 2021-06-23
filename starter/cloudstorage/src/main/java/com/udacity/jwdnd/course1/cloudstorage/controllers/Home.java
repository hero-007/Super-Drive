package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class Home {
    NotesService notesService;

    public Home(NotesService notesService) {
        this.notesService = notesService;
    }

    @GetMapping
    public String getHomePage(Model model){
        model.addAttribute("newNote", new Note());
        List<Note> notesList = notesService.getAllNotes();
        model.addAttribute("notesList", notesList);
        return "home";
    }
}
