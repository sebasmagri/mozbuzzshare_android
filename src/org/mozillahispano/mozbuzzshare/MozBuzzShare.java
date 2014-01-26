package org.mozillahispano.mozbuzzshare;

import java.util.ArrayList;
import java.util.regex.Matcher;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;


public class MozBuzzShare extends Activity
{
    private static final String LOGGING_TAG = "MozBuzzShare";
    private static final String CREATE_MENTION_URL = "https://www.mozilla-hispano.org/mozbuzz/mention/create";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Intent intent;
        Bundle extras;
        String extraText;

        Log.v(LOGGING_TAG, "Creating MozBuzz Share activity");
        super.onCreate(savedInstanceState);

        intent = getIntent();
        extras = intent.getExtras();

        if (extras == null) {
            Log.v(LOGGING_TAG, "Unable to get extras from intent");
            throw new AssertionError();
        }

        extraText = extras.getString("android.intent.extra.TEXT");
        if (extraText != null) {
            Log.v(LOGGING_TAG, "Processing: " + extraText);

            // Find all urls in text
            int textIndex = 0;
            ArrayList<String> urls = new ArrayList<String>();
            Matcher urlsMatcher = Patterns.WEB_URL.matcher(extraText);
            while (urlsMatcher.find(textIndex)) {
                textIndex = urlsMatcher.end();
                String url = urlsMatcher.group();
                Log.v(LOGGING_TAG, "Found URL: " + url);
                urls.add(url);
            }

            // If there are several URLs, pick one with a dialog
            // TODO

            // Open in browser?
            Intent browserIntent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(CREATE_MENTION_URL + "?url=" + urls.get(0))
            );
            startActivity(browserIntent);
        }

        this.finish();
    }
}
