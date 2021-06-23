package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {
    NoteMapper noteMapper;

    public NotesService(NoteMapper noteMapper){
        this.noteMapper = noteMapper;
    }

    public Note getNote(Integer noteId){
        if(noteId != null){
            Note note = noteMapper.getNote(noteId);
            return note;
        }
        return null;
    }

    public Integer createNewNote(Note note){
        if(note != null){
            return noteMapper.insert(new Note(null, note.getNoteTitle(), note.getNoteDescription(), note.getUserid()));
        }
        return null;
    }

    public List<Note> getAllNotes(){
        return noteMapper.getAllNotes();
    }

    public Integer deleteNote(int noteId){
        if(noteId > 0){
            return noteMapper.deleteNote(noteId);
        }
        return null;
    }

    public Integer updateNote(Note note){
        if(note != null && note.getNoteId() != null){
            return noteMapper.updateNote(note);
        }
        return null;
    }
}
