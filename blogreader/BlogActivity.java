package android.basui.blogreader;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.net.URI;


public class BlogActivity extends Activity {

    protected ProgressBar progressBar;
    protected ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);



        progressBar = (ProgressBar)findViewById(R.id.ProgressBar);
        listView = (ListView)findViewById(R.id.ListView);

        listView.setEmptyView(progressBar);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.i("BlogActivity", "Title: " + BlogPostParser.get().posts.get(position));

                Intent intent = new Intent(getApplicationContext(), BlogWebActivity.class);
                Uri blogUri = Uri.parse(BlogPostParser.get().posts.get(position).url);
                intent.setData(blogUri);

                startActivity(intent);
            }
        });



        new BlogPostTask().execute(this);
    }



}
