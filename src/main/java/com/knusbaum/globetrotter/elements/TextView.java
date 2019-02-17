package com.knusbaum.globetrotter.elements;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;

import com.knusbaum.globetrotter.Globetrotter;
import com.knusbaum.globetrotter.Json.ErrorResponse;
import com.knusbaum.globetrotter.Json.StringResponse;
import com.knusbaum.globetrotter.util.Either;

public class TextView extends android.support.v7.widget.AppCompatTextView {
    public TextView(Context context) {
        this(context, (AttributeSet)null);
    }

    public TextView(Context context, AttributeSet attrs) {
        this(context, attrs, 16842884);
    }

    public TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //attrs.getAttributeValue()
        String text = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "text");
        new TextViewFillTask().execute(text);

    }

    private class TextViewFillTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... args)
        {
            String reply = "";
            try {
                Either<StringResponse, ErrorResponse> resp =  Globetrotter.instance()
                        .lookupString(args[0]);
                if(resp.isSuccess()) {
                    reply = resp.getValue().getString();
                }
                else {
                    //reply = resp.getError().getError();
                    reply = "";
                }
            }
            catch (Exception e) {
                //throw new RuntimeException(e);
                reply = args[0];
            }
            return reply;
        }

        @Override
        protected void onProgressUpdate(Integer... progress)
        {}

        @Override
        protected void onPostExecute(String result) {
            setText(result);
        }
    }
}
