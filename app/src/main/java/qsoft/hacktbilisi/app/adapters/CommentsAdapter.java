package qsoft.hacktbilisi.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import qsoft.hacktbilisi.app.R;
import qsoft.hacktbilisi.app.pojo.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by andrii on 20.12.14.
 */
public class CommentsAdapter extends ArrayAdapter<Comment> {

    private Context context;
    private ArrayList<Comment> comments;
    private int layoutResourceId;

    public CommentsAdapter(Context context, int layoutResourceId, ArrayList<Comment> comments) {
        super(context, layoutResourceId, comments);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.comments = comments;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        Comment comment = this.comments.get(position);

        final ImageView ivIcon = (ImageView) convertView.findViewById(R.id.iv_comment_item_icon);
//        ivIcon.setOnClickListener(imageClickListener(position));

        final TextView tvAuthor = (TextView) convertView.findViewById(R.id.tv_comment_item_author);
        TextView tvText = (TextView) convertView.findViewById(R.id.tv_comment_item_text);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tv_comment_item_time);

        //todo get user name and photo
//        comment.getParseObject("authorID")
//                .fetchIfNeededInBackground(new GetCallback<ParseObject>() {
//                    public void done(ParseObject user, ParseException e) {
//                        String title = user.getString("firstName") + " " + user.getString("lastName");
//                        tvAuthor.setText(title);
//                        User user1 = (User) user;
//                        Picasso.with(context)
//                                .load(user1.getPhoto())
//                                .resizeDimen(R.dimen.comment_user_icon_size, R.dimen.comment_user_icon_size)
//                                .into(ivIcon);
//                    }
//                });

        tvText.setText(comment.getText());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy HH:mm");
        tvTime.setText(sdf.format(comment.getTime()));

        return convertView;
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
