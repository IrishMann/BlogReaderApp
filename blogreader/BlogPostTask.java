package android.basui.blogreader;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BlogPostTask extends AsyncTask<Activity, Void, JSONObject>{
    private Activity activity;
    @Override
    protected JSONObject doInBackground(Activity... activities) {
        activity = activities[0];
        JSONObject jsonObject = null;

        try {
            URL blogFeedUrl = new URL("http://blog.teamtreehouse.com/api/get_recent_summary/?count=20");

            HttpURLConnection connection = (HttpURLConnection)blogFeedUrl.openConnection();
            connection.connect();
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                Log.i("BlogPostTask", "Successful Connection" + responseCode);
                jsonObject = BlogPostParser.get().parse(connection.getInputStream());
            }
        }
        catch (MalformedURLException error) {
            Log.e("BlogPostTask", "MalformedURL " + error);
        }
        catch (IOException error) {
            Log.e("BlogPostTask", "IOException " + error);
        }

        return jsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        BlogPostParser.get().readFeed(jsonObject);
        ListView listView = (ListView)activity.findViewById(R.id.ListView);

        BlogPostAdapter adapter = new BlogPostAdapter(activity, BlogPostParser.get(). posts);
        listView.setAdapter(adapter);
    }
}
