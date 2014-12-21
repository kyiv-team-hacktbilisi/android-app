package qsoft.hacktbilisi.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.parse.ParseObject;
import com.parse.ParseUser;
import qsoft.hacktbilisi.app.R;
import qsoft.hacktbilisi.app.actvities.CommentsActivity;
import qsoft.hacktbilisi.app.actvities.LessonPreviewActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by andrii on 20.12.14.
 */
public class DayLessonAdapter extends RecyclerView.Adapter<DayLessonAdapter.ViewHolder> {

    private Context context;
    private List<ParseObject> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView ivStartTime;
        private final TextView ivEndTime;
        // each data item is just a string in this case
        public TextView tvLessonName;
        public TextView tvTeacherName;
        public TextView tvPlaceTime;
        public ImageView ivComments;


        public ViewHolder(View v, TextView v1, TextView v2, TextView v3, ImageView ivComments, TextView ivStartTime, TextView ivEndTime) {
            super(v);
            this.tvLessonName = v1;
            this.tvTeacherName = v2;
            this.tvPlaceTime = v3;
            this.ivComments = ivComments;
            this.ivStartTime = ivStartTime;
            this.ivEndTime = ivEndTime;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DayLessonAdapter(Context context, List<ParseObject> myDataset) {
        this.context = context;
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
        TextView tvPlaceT = (TextView) v.findViewById(R.id.tv_place);
        TextView ivStartTime = (TextView) v.findViewById(R.id.iv_start_time);
        TextView ivEndTime = (TextView) v.findViewById(R.id.iv_end_time);
        ImageView ivComments = (ImageView) v.findViewById(R.id.iv_comment);
        View.OnClickListener clickListener = clickListener();
        v.setOnClickListener(clickListener);
        ViewHolder vh = new ViewHolder(v, tvLesson, tvTeacher, tvPlaceT, ivComments, ivStartTime, ivEndTime);
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
        View.OnClickListener commentClickListener = commetnClickListener(mDataset.get(position).getObjectId());
        holder.ivComments.setOnClickListener(commentClickListener);

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            Date data = simpleDateFormat.parse(mDataset.get(position).getString("start_time"));
            holder.ivStartTime.setText(mDataset.get(position).getString("start_time"));
            int d = ParseUser.getCurrentUser().getInt("lessonDuration");
            holder.ivEndTime.setText(simpleDateFormat.format(new Date(data.getTime() +
                    (60000 * (d == 0 ? 90 : d)))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tvLessonName.setText(mDataset.get(position).getString("name"));
        holder.tvTeacherName.setText(mDataset.get(position).getString("teacher"));
        holder.tvPlaceTime.setText(mDataset.get(position).getString("audience"));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    private View.OnClickListener clickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LessonPreviewActivity.class);
                context.startActivity(intent);
            }
        };
    }

    private View.OnClickListener commetnClickListener(final String lessonID) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentsActivity.class);
                intent.putExtra("lessonID", lessonID);
                context.startActivity(intent);
            }
        };
    }

}
