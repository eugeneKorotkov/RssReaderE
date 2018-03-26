package korotkov.ru.rssreadere;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final String urlString = "http://www.vesti.ru/vesti.rss";

    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (isNetworkAvailable()){
            loadFeedFromWeb();
            mRecyclerView.setAdapter(mAdapter);
        }



    }

    //for checking internet connection

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void loadFeedFromWeb() {


        Parser parser = new Parser();
        parser.execute(urlString);
        parser.onFinish(new Parser.OnTaskCompleted() {
            //what to do when the parsing is done
            @Override
            public void onTaskCompleted(ArrayList<News> list) {
                //list is an Arra y List with all article's information
                //set the adapter to recycler view
                mAdapter = new MainAdapter(list, MainActivity.this);
            }

            //what to do in case of error
            @Override
            public void onError() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Unable to load data.",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

}
