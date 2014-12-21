package qsoft.hacktbilisi.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import qsoft.hacktbilisi.app.R;
import qsoft.hacktbilisi.app.pojo.Comment;
import qsoft.hacktbilisi.app.pojo.User;
import qsoft.hacktbilisi.app.utils.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by andrii on 20.12.14.
 */
public class CommentsAdapter extends ArrayAdapter<Comment> {

    private Context context;
    private ArrayList<Comment> comments;

    public CommentsAdapter(Context context, ArrayList<Comment> comments) {
        super(context, 0, comments);
        this.context = context;
        this.comments = comments;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            if (getItemViewType(position) == 0) {
                convertView = inflater.inflate(R.layout.item_comment_your, parent, false);
            } else if (getItemViewType(position) == 1) {
                convertView = inflater.inflate(R.layout.item_comment_other, parent, false);

            }
        }

        Comment comment = this.comments.get(position);

        final TextView ivIcon = (TextView) convertView.findViewById(R.id.tv_comment_item_icon);
//        ivIcon.setOnClickListener(imageClickListener(position));

        final TextView tvAuthor = (TextView) convertView.findViewById(R.id.tv_comment_item_author);
        TextView tvText = (TextView) convertView.findViewById(R.id.tv_comment_item_text);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tv_comment_item_time);

        //todo get user name and photo
        ParseQuery<User> query = ParseQuery.getQuery("_User");
        query.whereEqualTo("objectId", comment.getAuthorID());
        query.getFirstInBackground(new GetCallback<User>() {
            public void done(User result, ParseException e) {
                if (e == null) {
                    tvAuthor.setText(result.getName());
                    Logger.d("Retrieved the object.");
                } else {
                    Logger.e("The getFirst request failed.");
                }
            }
        });

        tvText.setText(comment.getText());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy HH:mm");
        tvTime.setText(sdf.format(comment.getTime()));

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        // Define a way to determine which layout to use, here it's just evens and odds.

        return getItem(position).getAuthorID().equals(User.getCurrentUser().getObjectId()) ?
                0 : 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2; // Count of different layouts
    }

//    private View.OnClickListener imageClickListener(final int position) {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (v.getId()) {
//                    case R.id.iv_comment_item_icon:
//                        Intent intent = new Intent(context, UserProfileActivity.class);
//                        intent.putExtra("userID", comments.get(position).getAuthorID());
//                        context.startActivity(intent);
//                        break;
//                }
//            }
//        };
//    }
}
