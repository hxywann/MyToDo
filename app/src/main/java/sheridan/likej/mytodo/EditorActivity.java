package sheridan.likej.mytodo;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sheridan.likej.mytodo.database.TodoEntity;
import sheridan.likej.mytodo.viewmodel.EditorViewModel;

import static sheridan.likej.mytodo.utilities.Constants.CONFIRM_FRAGMENT;
import static sheridan.likej.mytodo.utilities.Constants.DATE_PICKER_FRAGMENT;
import static sheridan.likej.mytodo.utilities.Constants.EDITING_KEY;
import static sheridan.likej.mytodo.utilities.Constants.TODO_ID_KEY;


public class EditorActivity extends AppCompatActivity implements ConfirmFragment.ConfirmListener,
        DatePickerFragment.DateSetListener{

    @BindView(R.id.title_text)
    TextView mTitleView;

    @BindView(R.id.desc_text)
    TextView mDescView;

    @BindView(R.id.date_text)
    TextView mDateView;

    private EditorViewModel mViewModel;
    private boolean mNewTodo, mEditing;
    private boolean mConfirmed;


    private Date mDate = new Date(); // this returns today's date

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        if(savedInstanceState!=null){
            mEditing=savedInstanceState.getBoolean(EDITING_KEY);
        }
        initViewModel();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(EditorViewModel.class);
        mViewModel.mLiveData.observe(this, new Observer<TodoEntity>() {
            @Override
            public void onChanged(TodoEntity todoEntity) {
                if (todoEntity != null && !mEditing) {  //if we are editing the text then we won't change it when the screen orientation changed
                    mTitleView.setText(todoEntity.getTitle());
                    mDescView.setText(todoEntity.getDescription());
                    mDateView.setText(DateFormat.getLongDateFormat(EditorActivity.this).format(todoEntity.getDueDate()));
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle(R.string.new_todo);
            mNewTodo = true;
        }else{
            setTitle(R.string.edit_todo);
            int todoId = extras.getInt(TODO_ID_KEY);
            mViewModel.loadData(todoId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!mNewTodo){  //if the note is already existed then the delete button will display
            MenuInflater inflater  = getMenuInflater();
            inflater.inflate(R.menu.menu_editor,menu);   //the menu editor's menu (only garbage can ) will displayed
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {  // the check sign pressed
        if(item.getItemId() == android.R.id.home){
            saveAndReturn();
            return true;
        }
        else if(item.getItemId()==R.id.action_delete){   // if the delete button clicked on the edit page up corner then call the confirm fragment

            ConfirmFragment confirmFragment
                    = ConfirmFragment.newInstance(0, getString(R.string.confirm_message));
            confirmFragment.show(getSupportFragmentManager(), CONFIRM_FRAGMENT);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfirmed(int dialogID) {
        mConfirmed = true;
        mViewModel.deleteTodo();
        finish(); //renturn main activity
    }

    @Override
    public void onBackPressed() {  // the device back button pressed
        saveAndReturn();
    }

    private void saveAndReturn() {
        mViewModel.saveTodo(mTitleView.getText().toString(),mDescView.getText().toString(), mDate);
        finish();
    }

    @OnClick(R.id.edit_date)
    void editDateClickHandler(){
        DialogFragment fragment = DatePickerFragment.getInstance(mDate);
        fragment.show(getSupportFragmentManager(), DATE_PICKER_FRAGMENT);
    }

    @Override
    public void onDateSet(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        calendar.set(year, month, day, hour, minute);
        mDate = calendar.getTime();
        mDateView.setText(DateFormat.getLongDateFormat(this).format(mDate));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }
}
