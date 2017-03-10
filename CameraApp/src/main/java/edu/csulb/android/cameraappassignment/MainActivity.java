package edu.csulb.android.cameraappassignment;

import android.app.*;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    List<ImageInfo> images;
    public static ArrayAdapter<ImageInfo> adapter;
    DatabaseHandler db = new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        listView=(ListView)findViewById(R.id.listView);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
               //         .setAction("Action", null).show();

                Intent intent = new Intent(MainActivity.this,AddPhoto.class);
                startActivity(intent);

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id==R.id.action_uninstall){
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:edu.csulb.android.cameraappassignment"));
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        images = db.getAllData();
        adapter = new ArrayAdapter<ImageInfo>(this,android.R.layout.simple_list_item_1,images);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

          //      TextView tv = (TextView)view.
                String textSelected = listView.getItemAtPosition(position).toString();
                FragmentManager fm = getFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("captionText",textSelected);
                android.app.DialogFragment dialogFragment = new DialogFragment();
                dialogFragment.setArguments(bundle);
                dialogFragment.show(fm, "Sample Fragment");

//                        Toast.makeText(MainActivity.this, listView.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

            }
        });


        //   images = db.getAllData();
      //  adapter.notifyDataSetChanged();
        Log.d("On resume","Called");
        super.onResume();
    }

 /*   @Override
    protected void onPause() {
        adapter.notifyDataSetChanged();
        super.onPause();
    }

    @Override
    protected void onStart() {
        adapter.notifyDataSetChanged();
        super.onStart();
    }
*/

}
