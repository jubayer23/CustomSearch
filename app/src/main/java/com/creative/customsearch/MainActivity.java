package com.creative.customsearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //final DelayAutoCompleteTextView bookTitle = (DelayAutoCompleteTextView) findViewById(R.id.et_book_title);
       // bookTitle.setThreshold(3);
       // bookTitle.setAdapter(new BookAutoCompleteAdapter(this)); // 'this' is Activity instance
       // bookTitle.setLoadingIndicator(
      //          (android.widget.ProgressBar) findViewById(R.id.pb_loading_indicator));
       // bookTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
       //     @Override
       //     public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
       //         Book book = (Book) adapterView.getItemAtPosition(position);
       //         bookTitle.setText(book.getTitle());
       //     }
       // });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
