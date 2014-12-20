package qsoft.hacktbilisi.app.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import qsoft.hacktbilisi.app.R;
import qsoft.hacktbilisi.app.fragments.DayFragment;
import qsoft.hacktbilisi.app.pojo.TimeTable;

/**
 * Created by andrii on 20.12.14.
 */
public class DaysPagerAdapter extends FragmentStatePagerAdapter {

    private TimeTable timeTable;

    private int currPage = 0;

    private Context context;

    public DaysPagerAdapter(FragmentManager fm, Context context, TimeTable timeTable) {
        super(fm);
        this.context = context;
        this.timeTable = timeTable;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                return DayFragment.init(position);
            case 1:
                return DayFragment.init(position);
            case 2:
                return DayFragment.init(position);
            case 3:
                return DayFragment.init(position);
            case 4:
                return DayFragment.init(position);
            case 5:
                return DayFragment.init(position);
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return timeTable.getDaysNum();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getResources().getString(R.string.monday);
            case 1:
                return context.getResources().getString(R.string.tuesday);
            case 2:
                return context.getResources().getString(R.string.wednesday);
            case 3:
                return context.getResources().getString(R.string.thursday);
            case 4:
                return context.getResources().getString(R.string.friday);
            case 5:
                return context.getResources().getString(R.string.saturday);
        }
        return null;
    }

    public void setData(TimeTable t) {
        this.timeTable = t;
        this.notifyDataSetChanged();
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
