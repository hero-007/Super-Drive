package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.DriveFile;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
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
    FileService fileService;

    public Home(NotesService notesService, FileService fileService) {
        this.notesService = notesService;
        this.fileService = fileService;
    }

    @GetMapping
    public String getHomePage(Model model){
        model.addAttribute("newNote", new Note());
        List<Note> notesList = notesService.getAllNotes();
        model.addAttribute("notesList", notesList);
        List<DriveFile> fileList = fileService.getAllFiles();
        model.addAttribute("fileList", fileList);
        return "home";
    }
}
