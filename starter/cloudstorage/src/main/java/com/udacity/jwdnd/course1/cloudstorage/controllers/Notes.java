package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class Notes {

    NotesService notesService;
    UserService userService;

    public Notes(NotesService notesService, UserService userService) {
        this.notesService = notesService;
        this.userService = userService;
    }

    @PostMapping("/notes")
    public String addNewNote(@ModelAttribute("newNote") Note note, RedirectAttributes redirectAttributes, Authentication authentication, Model model){
        if(note != null){
            String loggedInUser = authentication.getName();
            User user = userService.getUser(loggedInUser);
            if(user != null && user.getUserid() != null)
            {
                // check if noteId already exist in the DB then update the existing note rather then creating a new one
                if(note.getNoteId() != null){
                    Integer updatedNoteCount = notesService.updateNote(new Note(note.getNoteId(), note.getNoteTitle(), note.getNoteDescription(), user.getUserid()));
                    if(updatedNoteCount != null) {
                        redirectAttributes.addFlashAttribute("assetSuccessMessage", "Note has been edited successfully!");
                        return "redirect:/";
                    }
                    else {
                        redirectAttributes.addFlashAttribute("assetErrorMessage", "Error occurred while trying to edit the note.");
                        return "redirect:/";
                    }
                }else {
                    Integer createdNoteId = notesService.createNewNote(new Note(null, note.getNoteTitle(), note.getNoteDescription(), user.getUserid()));
                    if (createdNoteId != null) {
                        // Note was added to the DB successfully, redirect to home page with success message
                        redirectAttributes.addFlashAttribute("assetSuccessMessage", "Note has been created successfully!");
                        return "redirect:/";
                    } else {
                        // Error occurred while trying to add the note to the DB, redirect to home page with failure message
                        redirectAttributes.addFlashAttribute("assetErrorMessage", "Error occurred while trying to create the note.");
                        return "redirect:/";
                    }
                }
            }
        }
        return "redirect:/";
    }

    @GetMapping("/notes/delete")
    public String deleteNote(@RequestParam(name = "noteId", required = true) String noteId, RedirectAttributes redirectAttributes){
        if(noteId != null){
            int noteToDelete = Integer.parseInt(noteId);
            Integer deletedNote = notesService.deleteNote(noteToDelete);
            if(deletedNote >= 1){
                redirectAttributes.addFlashAttribute("assetSuccessMessage", "Note has been deleted successfully!");
                return "redirect:/";
            }else{
                redirectAttributes.addFlashAttribute("assetErrorMessage", "Error occurred while trying to delete the note.");
                return "redirect:/";
            }
        }
        redirectAttributes.addFlashAttribute("assetErrorMessage", "Error occurred while trying to delete the note.");
        return "redirect:/";
    }
}
