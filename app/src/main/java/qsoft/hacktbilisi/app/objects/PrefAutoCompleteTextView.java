package qsoft.hacktbilisi.app.objects;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

/**
 * Created by andrii on 20.12.14.
 */
public class PrefAutoCompleteTextView extends AutoCompleteTextView {

    public PrefAutoCompleteTextView(Context context) {
        super(context);
    }

    public PrefAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PrefAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // this is how to disable AutoCompleteTextView filter
    @Override
    protected void performFiltering(final CharSequence text, final int keyCode) {
        String filterText = "";
        super.performFiltering(filterText, keyCode);
    }
}
