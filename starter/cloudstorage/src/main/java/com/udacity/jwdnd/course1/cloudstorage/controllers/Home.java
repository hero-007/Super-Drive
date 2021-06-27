package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.models.DisplayCredentials;
import com.udacity.jwdnd.course1.cloudstorage.models.DriveFile;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
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
    CredentialService credentialService;

    public Home(NotesService notesService, FileService fileService, CredentialService credentialService) {
        this.notesService = notesService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String getHomePage(Model model){
        model.addAttribute("newNote", new Note());
        List<Note> notesList = notesService.getAllNotes();
        model.addAttribute("notesList", notesList);
        List<DriveFile> fileList = fileService.getAllFiles();
        model.addAttribute("fileList", fileList);
        model.addAttribute("newCredential", new Credentials());
        List<DisplayCredentials> credentialsList = credentialService.getAllCredentials();
        model.addAttribute("credentialList", credentialsList);
        return "home";
    }
}
