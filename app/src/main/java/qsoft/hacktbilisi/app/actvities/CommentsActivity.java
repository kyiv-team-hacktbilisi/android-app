package qsoft.hacktbilisi.app.actvities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import it.gmariotti.android.example.colorpicker.Utils;
import it.gmariotti.android.example.colorpicker.calendarstock.ColorPickerDialog;
import it.gmariotti.android.example.colorpicker.calendarstock.ColorPickerSwatch;
import qsoft.hacktbilisi.app.R;
import qsoft.hacktbilisi.app.adapters.CommentsAdapter;
import qsoft.hacktbilisi.app.pojo.Comment;

import java.util.ArrayList;
import java.util.Date;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        context = this;
        loadComments();
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
        ivSendComment.setOnClickListener(this);
        adapter = new CommentsAdapter(context, R.layout.item_comment_other, comments);
        lvComments.setAdapter(adapter);
    }

    private void loadComments() {
        comments = new ArrayList<>();
        Comment comment = new Comment();
        comment.setLessonID("12345678");
        comment.setAuthorID("12345678");
        comment.setText("12345678");
        comment.setTime(new Date());
        comments.add(comment);
        comments.add(comment);
        comments.add(comment);
        comments.add(comment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_send_comment:
                String c = etComment.getText().toString();
                if (c != null && c.length() > 0) {
                    Comment comment = new Comment();
                    comment.setLessonID("12345678");
                    comment.setAuthorID("12345678");
                    comment.setText(c);
                    comment.setTime(new Date());
                    comments.add(comment);
                    adapter.notifyDataSetChanged();
                    etComment.setText("");
                    scrollListViewToBottom();
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
