package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.models.DisplayCredentials;
import com.udacity.jwdnd.course1.cloudstorage.models.DriveFile;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class Home {
    NotesService notesService;
    FileService fileService;
    CredentialService credentialService;
    UserService userService;

    public Home(NotesService notesService, FileService fileService, CredentialService credentialService, UserService userService) {
        this.notesService = notesService;
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(Model model, Authentication authentication){
        String loggedInUser = authentication.getName();
        int userId = userService.getUser(loggedInUser).getUserid();
        model.addAttribute("newNote", new Note());
        List<Note> notesList = notesService.getAllNotes(userId);
        model.addAttribute("notesList", notesList);
        List<DriveFile> fileList = fileService.getAllFiles(userId);
        model.addAttribute("fileList", fileList);
        model.addAttribute("newCredential", new Credentials());
        List<DisplayCredentials> credentialsList = credentialService.getAllCredentials(userId);
        model.addAttribute("credentialList", credentialsList);
        return "home";
    }
}
