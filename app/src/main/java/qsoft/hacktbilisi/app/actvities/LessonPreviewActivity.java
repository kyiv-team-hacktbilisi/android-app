package qsoft.hacktbilisi.app.actvities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import qsoft.hacktbilisi.app.R;

/**
 * Created by andrii on 20.12.14.
 */
public class LessonPreviewActivity extends Activity implements View.OnClickListener {

    private Context context;

    private Button bShowComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_preview);

        context = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
        setupViews();
    }

    private void initViews() {
        bShowComments = (Button) findViewById(R.id.b_show_comments);
    }

    private void setupViews() {
        bShowComments.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_show_comments:
                Intent intent = new Intent(context, CommentsActivity.class);
                startActivity(intent);
                break;
        }
    }
}

