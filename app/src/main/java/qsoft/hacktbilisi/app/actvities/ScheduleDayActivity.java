package qsoft.hacktbilisi.app.actvities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import qsoft.hacktbilisi.app.R;
import qsoft.hacktbilisi.app.adapters.DaysPagerAdapter;
import qsoft.hacktbilisi.app.pojo.TimeTable;

/**
 * Created by andrii on 20.12.14.
 */
public class ScheduleDayActivity extends FragmentActivity {

    private Context context;

    public static ViewPager mViewPager;

    public static DaysPagerAdapter mDaysPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_day);
        getActionBar().setElevation(0);
        context = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
        setupViews();
    }

    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
//        PagerTitleStrip strip = (PagerTitleStrip)mViewPager.findViewById(R.id.pager_title_strip);
    }

    private void setupViews() {
        TimeTable timeTable = new TimeTable();
        timeTable.setDaysNum(5);
        mDaysPagerAdapter = new DaysPagerAdapter(getSupportFragmentManager(), context, timeTable);
        mViewPager.setAdapter(mDaysPagerAdapter);
        mDaysPagerAdapter.setContext(this);
        mDaysPagerAdapter.setCurrPage(mViewPager.getCurrentItem());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_day, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(context, EditLessonActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
