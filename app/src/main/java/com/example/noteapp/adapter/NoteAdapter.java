package com.example.noteapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.AddNotes;
import com.example.noteapp.MainActivity;
import com.example.noteapp.NoteViewModel;
import com.example.noteapp.R;
import com.example.noteapp.room.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    Context context;
    List<Note> noteList = new ArrayList<>();
    private NoteViewModel noteViewModel;
    private AddNotes addNotes;

    public NoteAdapter(Context context, List<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
    }
    public NoteAdapter(){

    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.title.setText(String.valueOf(note.getTitle()));
        holder.priority.setText(String.valueOf(note.getPriority()));
        holder.description.setText(String.valueOf(note.getDescription()));
        //-----------------> onClick Listener goes here <-----------------

        holder.delete.setOnClickListener(view -> {
            MainActivity.noteViewModel.delete(note);
//            noteViewModel.delete(note);
        });

        holder.edit.setOnClickListener(view -> {
//            addNotes.getUpdate(note);
            Intent intent = new Intent(context, AddNotes.class);
            intent.putExtra("id", note.getId());
            intent.putExtra("title", note.getTitle());
            intent.putExtra("description", note.getDescription());
            intent.putExtra("priority", note.getPriority());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView priority;
        TextView title;
        TextView description;
        CardView noteCardView;
        ImageView edit, delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            priority = itemView.findViewById(R.id.tvPriority);
            title = itemView.findViewById(R.id.tvTitle);
            description = itemView.findViewById(R.id.tvDescription);
            noteCardView = itemView.findViewById(R.id.cvNotes);
            edit = itemView.findViewById(R.id.imgEdit);
            delete = itemView.findViewById(R.id.imgDelete);
        }


    }
    public void setNote(List<Note> noteList){
        this.noteList = noteList;
        notifyDataSetChanged();
    }
}
