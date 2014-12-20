package qsoft.hacktbilisi.app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import qsoft.hacktbilisi.app.R;

/**
 * Created by andrii on 20.12.14.
 */
public class DayLessonAdapter extends RecyclerView.Adapter<DayLessonAdapter.ViewHolder> {

    private String[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvLessonName;
        public TextView tvTeacherName;
        public TextView tvPlaceTime;

        public ViewHolder(View v, TextView v1, TextView v2, TextView v3) {
            super(v);
            tvLessonName = v1;
            tvTeacherName = v2;
            tvPlaceTime = v3;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DayLessonAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DayLessonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lesson, parent, false);
        // set the view's size, margins, paddings and layout parameters

        TextView tvLesson = (TextView) v.findViewById(R.id.tv_lesson_name);
        TextView tvTeacher = (TextView) v.findViewById(R.id.tv_teacher_name);
        TextView tvPlaceT = (TextView) v.findViewById(R.id.tv_place_time);

        ViewHolder vh = new ViewHolder(v, tvLesson, tvTeacher, tvPlaceT);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.tvLessonName.setText(mDataset[position]);
//        holder.tvTeacherName.setText(mDataset[position]);
//        holder.tvPlaceTime.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}