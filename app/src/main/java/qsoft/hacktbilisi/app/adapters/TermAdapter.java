package qsoft.hacktbilisi.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import qsoft.hacktbilisi.app.R;

import java.util.ArrayList;

/**
 * Created by andrii on 20.12.14.
 */
public class TermAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> terms;

    public TermAdapter(Context context, ArrayList<String> terms) {
        this.context = context;
        this.terms = terms;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String term = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_term, parent, false);
        }

        TextView tvTerm = (TextView) convertView.findViewById(R.id.tv_term);
        tvTerm.setText(term);
        return convertView;
    }

    @Override
    public int getCount() {
        return terms.size();
    }

    @Override
    public String getItem(int position) {
        return terms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
