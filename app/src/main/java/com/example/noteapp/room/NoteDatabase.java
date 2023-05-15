package com.example.noteapp.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance;
    public abstract NoteDAO noteDAO();

    public static synchronized NoteDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    //---------> call on create method the first time app is created <---------------
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateDbAsyncTask(instance).execute();
        }
    };

    //--------------> asyncTask for roomCallback <---------------
    private static class populateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private NoteDAO noteDAO;
        private  populateDbAsyncTask(NoteDatabase db){
            noteDAO = db.noteDAO();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDAO.insert(new Note("title 1","description 1", 1));
            noteDAO.insert(new Note("title 2","description 2",2));
            noteDAO.insert(new Note("title 3","description 3",3));
            return null;
        }
    }
}
