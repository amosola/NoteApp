package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.noteapp.databinding.ActivityAddNotesBinding;
import com.example.noteapp.room.Note;

public class AddNotes extends AppCompatActivity {
    ActivityAddNotesBinding binding;
    private int update_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //------------> set minimum and maximum number indicator <--------------
        binding.npPriority.setMinValue(1);
        binding.npPriority.setMaxValue(5);
        //----------> Use close icon to close activity <--------------
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
        setTitle("Add Note");

        //-------------> get data from intent <------------
        Intent intent = getIntent();
        update_id = intent.getIntExtra("id", 0);
        String update_title = intent.getStringExtra("title");
        String update_description = intent.getStringExtra("description");
        int update_priority = intent.getIntExtra("priority",5);
        if (update_id > 0){
            binding.etTitle.setText(update_title);
            binding.etDescription.setText(update_description);
            binding.npPriority.setValue(update_priority);
        }
    }

    //-------------> save our notes <---------------
    private void saveNote(){
        String title = binding.etTitle.getText().toString().trim();
        String description = binding.etDescription.getText().toString().trim();
        int priority = binding.npPriority.getValue();
        if (title.isEmpty()){
            binding.etTitle.setError("Give a title");
        }
        if (description.isEmpty()){
            binding.etDescription.setError("Add description");
        }
        if (!title.isEmpty() || !description.isEmpty()){
            if (update_id > 0){
                Intent intent = new Intent();
                intent.putExtra("job", "update");
                intent.putExtra("title", title);
                intent.putExtra("description", description);
                intent.putExtra("priority", priority);
                setResult(RESULT_OK, intent);
                finish();
            }else {
                Intent intent = new Intent();
                intent.putExtra("job", "add");
                intent.putExtra("title", title);
                intent.putExtra("description", description);
                intent.putExtra("priority", priority);
                setResult(RESULT_OK, intent);
                finish();

            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_note, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSaveNote: //binding.getRoot(R.id.menuSaveNote):
                saveNote();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void getUpdate(Note note){

    }
}