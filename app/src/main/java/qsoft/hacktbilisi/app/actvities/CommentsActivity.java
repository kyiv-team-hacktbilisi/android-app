package qsoft.hacktbilisi.app.actvities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import qsoft.hacktbilisi.app.R;
import qsoft.hacktbilisi.app.adapters.CommentsAdapter;
import qsoft.hacktbilisi.app.pojo.Comment;
import qsoft.hacktbilisi.app.pojo.User;
import qsoft.hacktbilisi.app.utils.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by andrii on 20.12.14.
 */
public class CommentsActivity extends Activity implements View.OnClickListener {

    private Context context;

    private EditText etComment;
    private ImageView ivSendComment;
    private ListView lvComments;

    private CommentsAdapter adapter;

    private ArrayList<Comment> comments;
    private String lessonID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        context = this;

        Intent intent = getIntent();
        lessonID = intent.getStringExtra("lessonID");

    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
        setupViews();
    }

    private void initViews() {
        etComment = (EditText) findViewById(R.id.et_new_comment);
        ivSendComment = (ImageView) findViewById(R.id.iv_send_comment);
        lvComments = (ListView) findViewById(R.id.lv_comments);
    }

    private void setupViews() {
        loadComments();
        setTitle("Comments");
        ivSendComment.setOnClickListener(this);
    }

    private void loadComments() {
        ParseQuery<Comment> query = ParseQuery.getQuery("Comment");
        query.whereEqualTo("lessonID", lessonID);
        query.findInBackground(new FindCallback<Comment>() {
            public void done(List<Comment> eventList, ParseException e) {
                if (e == null) {
                    comments = new ArrayList<>(eventList);
                    adapter = new CommentsAdapter(context, comments);
                    lvComments.setAdapter(adapter);
                    lvComments.smoothScrollToPosition(adapter.getCount() - 1);
                } else {
                    Logger.d("Error: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_send_comment:
                String c = etComment.getText().toString();
                if (c != null && c.length() > 0) {
                    Comment comment = new Comment();
                    comment.setLessonID(lessonID);
                    comment.setAuthorID(User.getCurrentUser().getObjectId());
                    comment.setText(c);
                    comment.setTime(new Date());
                    comments.add(comment);
                    adapter.notifyDataSetChanged();
                    etComment.setText("");
                    scrollListViewToBottom();
                    comment.saveEventually();
                }
                break;
        }
    }

    private void scrollListViewToBottom() {
        lvComments.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                lvComments.setSelection(adapter.getCount() - 1);
            }
        });
    }
}
