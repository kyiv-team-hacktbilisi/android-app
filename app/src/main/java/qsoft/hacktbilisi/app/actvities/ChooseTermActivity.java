package qsoft.hacktbilisi.app.actvities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import qsoft.hacktbilisi.app.R;
import qsoft.hacktbilisi.app.adapters.TermAdapter;

import java.util.ArrayList;

/**
 * Created by andrii on 20.12.14.
 */
public class ChooseTermActivity extends Activity implements AdapterView.OnItemClickListener {

    private Context context;

    private ListView lvTerms;
    private ArrayList<String> terms;

    private TermAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_term);

        context = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
        setupViews();
    }

    private void initViews() {
        lvTerms = (ListView) findViewById(R.id.lv_terms);
    }

    private void setupViews() {
        terms = new ArrayList<>();
        terms.add("1 term");
        terms.add("2 term");
        terms.add("3 term");
        terms.add("4 term");
        terms.add("5 term");
        terms.add("6 term");

        adapter = new TermAdapter(context, terms);
        lvTerms.setAdapter(adapter);
        lvTerms.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(context, ScheduleDayActivity.class);
        startActivity(intent);
    }
}
