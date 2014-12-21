package qsoft.hacktbilisi.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.parse.*;
import qsoft.hacktbilisi.app.R;
import qsoft.hacktbilisi.app.pojo.Comment;
import qsoft.hacktbilisi.app.pojo.User;

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

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("objectId", comment.getAuthorID());
        query.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {
                ivIcon.setText(user.getString("username").substring(0, 1).toUpperCase());
                tvAuthor.setText(user.getString("username"));
                Log.d("score", "Retrieved the object.");
            }
        });

//        comment.getParseObject("authorID")
//                .fetchIfNeededInBackground(new GetCallback<ParseObject>() {
//                    public void done(ParseObject user, ParseException e) {
//                        if (user == null) {
//                            Log.d("score", "The getFirst request failed.");
//                        } else {
//                            ivIcon.setText(user.getString("username").substring(0, 1).toUpperCase());
//                            tvAuthor.setText(user.getString("username").substring(0, user.getString("username").indexOf("@")));
//                            Log.d("score", "Retrieved the object.");
//                        }
//                    }
//                });

        tvText.setText(comment.getText());
//        ParseQuery<ParseUser> query = ParseQuery.getQuery("User");
//        query.whereEqualTo("objectId", comment.getAuthorID());
//        query.getFirstInBackground(new GetCallback<ParseUser>() {
//            public void done(ParseUser object, ParseException e) {
//
//            }
//        });
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
