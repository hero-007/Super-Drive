package com.udacity.jwdnd.course1.cloudstorage.models;

public class Note {
    private Integer noteId;
    private String noteTitle;
    private String noteDescription;
    private Integer userid;

    public Note() {

    }

    public Note(Integer noteId, String noteTitle, String noteDescription, Integer userid) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.userid = userid;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public void clearAllAttr(){
        noteId = null;
        noteTitle = "";
        noteDescription = "";
        userid = null;
    }
}
