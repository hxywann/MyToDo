package sheridan.likej.mytodo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sheridan.likej.mytodo.database.TodoEntity;
import sheridan.likej.mytodo.ui.TodosAdapter;
import sheridan.likej.mytodo.viewmodel.MainViewModel;

import static sheridan.likej.mytodo.utilities.Constants.ABOUT_FRAGMENT;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @OnClick(R.id.fab)  //two fabs one for add new one for the edit sign
    void fabClickHandler(){
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }

    private List<TodoEntity> todosData = new ArrayList<>();
    private TodosAdapter mAdapter;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        initRecyclerView();
        initViewModel();

//        todosData.addAll(mViewModel.mTodos);
//        for(TodoEntity todo: todosData)
//            Log.i("Kevin's Todos", todo.toString());
    }

    private void initViewModel() {
        final Observer<List<TodoEntity>> todosObserver =
                new Observer<List<TodoEntity>>(){

                    @Override
                    public void onChanged(@Nullable List<TodoEntity> todoEntities){
                        todosData.clear();
                        todosData.addAll(todoEntities);

                        if(mAdapter==null){
                            mAdapter = new TodosAdapter(todosData, MainActivity.this);
                            mRecyclerView.setAdapter(mAdapter);
                        }else{
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                };
        mViewModel= ViewModelProviders.of(this).get(MainViewModel.class);
        mViewModel.mTodos.observe(this, todosObserver);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true); //every row same height
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(
                mRecyclerView.getContext(),layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_add_sample_data) {
            addSampleData();
            return true;
        }else if (id == R.id.action_delete_all){
            deleteAllData();
            return true;
        }else if (id == R.id.about){
            AboutFragment aboutFragment = AboutFragment.newInstance();
            aboutFragment.show(getSupportFragmentManager(), ABOUT_FRAGMENT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllData() {
        mViewModel.deleteAllData();
    }

    private void addSampleData() {
        mViewModel.addSampleData();
    }
}
