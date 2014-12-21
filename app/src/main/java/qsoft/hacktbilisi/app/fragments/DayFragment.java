package qsoft.hacktbilisi.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.parse.*;
import qsoft.hacktbilisi.app.R;
import qsoft.hacktbilisi.app.adapters.DayLessonAdapter;

import java.util.List;

/**
 * Created by andrii on 20.12.14.
 */
public class DayFragment extends Fragment {

    private Context context;

    private int dayPosition;
    private RecyclerView rvLessons;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static DayFragment init(int dayPosition) {
        DayFragment dayFragment = new DayFragment();
        Bundle args = new Bundle();
        args.putInt("dayPosition", dayPosition);
        dayFragment.setArguments(args);
        return dayFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dayPosition = getArguments() != null ? getArguments().getInt("dayPosition") : 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        context = getActivity();

        initViews(view);
        setupViews();

        return view;
    }

    private void initViews(View v) {
        rvLessons = (RecyclerView) v.findViewById(R.id.rv_day_lessons);
    }

    private void setupViews() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rvLessons.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(context);
        rvLessons.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Lesson");
        query.whereEqualTo("group_id", ParseUser.getCurrentUser().get("group"));
        query.whereEqualTo("day", dayPosition);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    mAdapter = new DayLessonAdapter(context, scoreList);
                    rvLessons.setAdapter(mAdapter);
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }
}
