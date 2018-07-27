package test.cn.example.com.androidskill;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent;

import org.greenrobot.greendao.rx.RxDao;
import org.greenrobot.greendao.rx.RxQuery;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import test.cn.example.com.androidskill.greendao.NotesAdapter;
import test.cn.example.com.androidskill.model.greendao.DaoSession;
import test.cn.example.com.androidskill.model.greendao.Note;
import test.cn.example.com.androidskill.model.greendao.NoteDao;
import test.cn.example.com.androidskill.model.greendao.NoteType;

/**
 *  GreenDao的使用演示
 * Created by xgxg on 2018/7/26.
 */

public class GreenDaoActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText;
    private View addNoteButton;

    private RxDao<Note, Long> noteDao;
    private RxQuery<Note> notesQuery;
    private NotesAdapter notesAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greendao);
//        init();
        setUpViews();
        // get the Rx variant of the note DAO
        DaoSession daoSession = MyApplication.getInstance().getDaoSession();
        noteDao = daoSession.getNoteDao().rx();

        // query all notes, sorted a-z by their text
        notesQuery = daoSession.getNoteDao().queryBuilder().orderAsc(NoteDao.Properties.Text).rx();
        updateNotes();
    }

    private void init() {
//       findViewById(R.id.addBtn).setOnClickListener(this);
//       findViewById(R.id.addAllBtn).setOnClickListener(this);
//       findViewById(R.id.deleteBtn).setOnClickListener(this);
//       findViewById(R.id.deleteAllBtn).setOnClickListener(this);
//       findViewById(R.id.updateBtn).setOnClickListener(this);
//       findViewById(R.id.selectBtn).setOnClickListener(this);
//       findViewById(R.id.selectAllBtn).setOnClickListener(this);
    }

    protected void setUpViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewNotes);
        //noinspection ConstantConditions
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notesAdapter = new NotesAdapter(noteClickListener);
        recyclerView.setAdapter(notesAdapter);

        addNoteButton = findViewById(R.id.buttonAdd);

        editText = (EditText) findViewById(R.id.editTextNote);
        //noinspection ConstantConditions
        RxTextView.editorActions(editText).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer actionId) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            addNote();
                        }
                    }
                });
        RxTextView.afterTextChangeEvents(editText).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TextViewAfterTextChangeEvent>() {
                    @Override
                    public void call(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
                        boolean enable = textViewAfterTextChangeEvent.editable().length() > 0;
                        addNoteButton.setEnabled(enable);
                    }
                });
    }

    public void onAddButtonClick(View view) {
        addNote();
    }

    private void addNote() {
        String noteText = editText.getText().toString();
        editText.setText("");

        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());

        for (int i = 0; i < 10; i++) {
            final Note note = new Note(null, noteText+"---"+i, comment, new Date(), NoteType.TEXT);;
            noteDao.insert(note)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Note>() {
                        @Override
                        public void call(Note note) {
                            Log.d("DaoExample", "Inserted new note, ID: " + note.getId()+"---"+Thread.currentThread().getName());
                            updateNotes();
                        }
                    });

        }
//        Note note = new Note(null, noteText, comment, new Date(), NoteType.TEXT);
//        noteDao.insert(note)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Note>() {
//                    @Override
//                    public void call(Note note) {
//                        Log.d("DaoExample", "Inserted new note, ID: " + note.getId());
//                        updateNotes();
//                    }
//                });
    }

    private void updateNotes() {
        notesQuery.list()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Note>>() {
                    @Override
                    public void call(List<Note> notes) {
                        notesAdapter.setNotes(notes);
                    }
                });
    }

    NotesAdapter.NoteClickListener noteClickListener = new NotesAdapter.NoteClickListener() {
        @Override
        public void onNoteClick(int position) {
            Note note = notesAdapter.getNote(position);
            final Long noteId = note.getId();

            noteDao.deleteByKey(noteId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            Log.d("DaoExample", "Deleted note, ID: " + noteId);
                            updateNotes();
                        }
                    });
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addBtn:

                break;
//            case R.id.addAllBtn:
//
//                break;
//            case R.id.deleteBtn:
//
//                break;
//            case R.id.deleteAllBtn:
//
//                break;
//            case R.id.updateBtn:
//
//                break;
//            case R.id.selectBtn:
//
//                break;
//            case R.id.selectAllBtn:
//
//                break;
            default:
                break;
        }
    }
}
