package qsoft.hacktbilisi.app.actvities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.melnykov.fab.FloatingActionButton;
import qsoft.hacktbilisi.app.R;
import qsoft.hacktbilisi.app.pojo.Lesson;

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

    private Lesson lesson;
    private ListView cList;
    private TextView tvComm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_preview);

        context = this;
        loadLesson();
    }

    private void loadLesson() {
        // todo load lesson
        
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
        setupViews();
    }

    private void initViews() {
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
        if (lesson != null) {
            tvLessonName.setText(lesson.getName());
            tvTeacher.setText(lesson.getTeacher());
            tvAudience.setText(lesson.getAudience());
            tvType.setText(lesson.getType());
            //todo format date
//            tvTime.setText(lesson.getStartTime());
        }

        

        bShowComments.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_show_comments:
                Intent intent = new Intent(context, CommentsActivity.class);
                intent.putExtra("lessonID", lesson.getObjectId());
                startActivity(intent);
                break;
        }
    }
}

