package sheridan.likej.mytodo.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import sheridan.likej.mytodo.EditorActivity;
import sheridan.likej.mytodo.R;
import sheridan.likej.mytodo.database.TodoEntity;

import static sheridan.likej.mytodo.utilities.Constants.TODO_ID_KEY;

public class TodosAdapter extends RecyclerView.Adapter<TodosAdapter.ViewHolder> {

    private final List<TodoEntity> mTodos;
    private final Context mContext;

    public TodosAdapter(List<TodoEntity> todos, Context context) {
        mTodos = todos;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.mytodo_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TodoEntity todo = mTodos.get(position);
        holder.mTextView.setText(todo.getTitle()); //////////////////////////////////////////////////////////////

        holder.mFab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditorActivity.class);
                intent.putExtra(TODO_ID_KEY, todo.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTodos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mytodo_title)
        TextView mTextView;

        @BindView(R.id.fab)
        FloatingActionButton mFab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
