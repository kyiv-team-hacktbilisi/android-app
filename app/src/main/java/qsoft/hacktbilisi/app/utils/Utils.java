package qsoft.hacktbilisi.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import qsoft.hacktbilisi.app.R;

/**
 * Created by andrii on 20.12.14.
 */
public class Utils {

    public static void saveLoginState(String sessionToken, Context con) {
        Logger.d("Saving login state");
        SharedPreferences prefs = con.getSharedPreferences("loginStatePrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("sessionToken", sessionToken);
        editor.commit();
        Logger.d("Session token saved " + sessionToken);
    }

    public static String restoreLoginState(Context con) {
        Logger.d("Restoring login state");
        SharedPreferences prefs = con.getSharedPreferences("loginStatePrefs", Context.MODE_PRIVATE);
        String sessionToken = prefs.getString("sessionToken", null);
        Logger.d("Session token restored " + sessionToken);
        return sessionToken;
    }


    public static int[] colorChoice(Context context) {

        int[] mColorChoices = null;
        String[] color_array = context.getResources().
                getStringArray(R.array.default_color_choice_values);

        if (color_array != null && color_array.length > 0) {
            mColorChoices = new int[color_array.length];
            for (int i = 0; i < color_array.length; i++) {
                mColorChoices[i] = Color.parseColor(color_array[i]);
            }
        }
        return mColorChoices;
    }

    public static int getColorPosition(Context context, String color) {
        int pos = 0;
        String[] color_array = context.getResources().
                getStringArray(R.array.default_color_choice_values);
        for (int i = 0; i < color_array.length; i++) {
            if (color_array[i].equalsIgnoreCase(color)) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    public static String colorToString(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }
}
