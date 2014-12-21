package qsoft.hacktbilisi.app.actvities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.melnykov.fab.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import qsoft.hacktbilisi.app.R;
import qsoft.hacktbilisi.app.adapters.CommentsAdapter;
import qsoft.hacktbilisi.app.pojo.Comment;
import qsoft.hacktbilisi.app.pojo.Lesson;
import qsoft.hacktbilisi.app.pojo.User;
import qsoft.hacktbilisi.app.utils.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by andrii on 20.12.14.
 */
public class LessonPreviewActivity extends Activity implements View.OnClickListener {

    private Context context;

    private TextView tvLessonName;
    private TextView tvAudience;
    private TextView tvTeacher;
    private TextView tvType;
    private TextView tvTime;
    private FloatingActionButton bShowComments;

    private String lessonID;
    private Lesson lesson;
    private ListView cList;
    private TextView tvComm;
    private String lessonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_preview);

        context = this;

        Intent intent = getIntent();
        lessonName = intent.getStringExtra("lessonName");
        lessonID = intent.getStringExtra("lessonID");

        if (lessonName != null) setTitle(lessonName);
    }

    private void loadLesson() {
        ParseQuery<Lesson> query = ParseQuery.getQuery("Lesson");
        query.whereEqualTo("objectId", lessonID);
        query.getFirstInBackground(new GetCallback<Lesson>() {
            public void done(Lesson result, ParseException e) {
                if (e == null) {
                    lesson = result;
                    tvTeacher.setText(lesson.getTeacher());
                    tvAudience.setText(lesson.getAudience());
                    tvType.setText(lesson.getType());

                    try {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                        Date data = simpleDateFormat.parse(lesson.getStartTime());
                        StringBuilder s = new StringBuilder();
                        s.append(lesson.getStartTime());
                        int d = User.getCurrentUser().getInt("lessonDuration");
                        String end = simpleDateFormat.format(new Date(data.getTime() +
                                (60000 * (d == 0 ? 90 : d))));
                        s.append(" - " + end);
                        tvTime.setText(s.toString());
                    } catch (java.text.ParseException ex) {
                        ex.printStackTrace();
                    }
                    getComments(lessonID);
                    Logger.d("Retrieved the object.");
                } else {
                    Logger.e("The getFirst request failed.");
                }
            }
        });
    }

    private void getComments(String lessonID) {
        ParseQuery<Comment> query = ParseQuery.getQuery("Comment");
        query.whereEqualTo("lessonID", lessonID);
        query.orderByDescending("updatedAt");
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> list, ParseException e) {
                if (!list.isEmpty()) {
                    tvComm.setVisibility(View.VISIBLE);
                    cList.setVisibility(View.VISIBLE);
                    ArrayList<Comment> comments = new ArrayList<>();
                    comments.add(list.get(0));
                    cList.setAdapter(new CommentsAdapter(LessonPreviewActivity.this, comments));
                    int n = list.size() - 1;
                    if (n != 0) {
                        ((TextView) findViewById(R.id.tv_comments_count)).setText("and " + n +
                                " other comment" + (n == 1 ? "" : "s"));
                    }
                } else {
                    tvComm.setVisibility(View.INVISIBLE);
                    cList.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
        setupViews();
    }

    private void initViews() {
        tvLessonName = (TextView) findViewById(R.id.tv_lesson_name);
        tvTeacher = (TextView) findViewById(R.id.tv_teacher);
        tvAudience = (TextView) findViewById(R.id.tv_audience);
        tvType = (TextView) findViewById(R.id.tv_lesson_type);
        tvTime = (TextView) findViewById(R.id.tv_time);
        bShowComments = (FloatingActionButton) findViewById(R.id.b_show_comments);
        bShowComments.show(true);
        cList = (ListView) findViewById(R.id.lv_comments);
        tvComm = (TextView) findViewById(R.id.tv_comments);
    }

    private void setupViews() {
        loadLesson();
        bShowComments.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_show_comments:
                Intent intent = new Intent(context, CommentsActivity.class);
                intent.putExtra("lessonID", lessonID);
                startActivity(intent);
                break;
        }
    }
}

