package com.example.noteapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.noteapp.adapter.NoteAdapter;
import com.example.noteapp.databinding.ActivityMainBinding;
import com.example.noteapp.room.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NoteViewModel noteViewModel;
    ActivityMainBinding binding;
    private NoteAdapter adapter;
    Context context;
    public static final int NOTE_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNotes.class);
                startActivityForResult(intent, NOTE_REQUEST_CODE);
            }
        });
        //------------> Recyclerview setup <--------------
        binding.recyclerView.hasFixedSize();
        adapter = new NoteAdapter();//context,listFromDb
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //----------> this is where we will update our recycler view <------------
                Toast.makeText(MainActivity.this, "onChanged called", Toast.LENGTH_SHORT).show();
                adapter.setNote(notes);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NOTE_REQUEST_CODE && resultCode == RESULT_OK){
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");
            int priority = data.getIntExtra("priority",5);

            Note note = new Note(title,description,priority);
            noteViewModel.insert(note);
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this, "Note Not Saved", Toast.LENGTH_SHORT).show();
    }
}