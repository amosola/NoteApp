package com.example.noteapp.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String priority;

    //-----------------> constructor <----------------

    public Note(String title, String description, String priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    //----------------> setters <-----------------

    public void setId(int id) {
        this.id = id;
    }


    //-----------------> getters <-----------------

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }
}
