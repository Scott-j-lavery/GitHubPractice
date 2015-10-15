package com.example.scott.freestyle;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import databases.Database;
import fragments.ListViewFragment;
import interfaces.PointsCollectInterface;
import utility.FileManager;

public class MainActivity extends AppCompatActivity implements PointsCollectInterface {

    public static final String SHARED_PREF_KEY = "com.example.scott.freestyle.SHARED_PREF_KEY";
    public static final String FILE_NAME = "freestyle.txt";
    public static final String FILESAVED = "FileSaved";
    public static final String TAG = "SJL";

    private Database db = new Database(this);

    ImageView arnoldPicture;
    EditText editText;
    TextView textView;
    FileManager fileManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ListViewFragment frag = ListViewFragment.newInstance();

        arnoldPicture = (ImageView) findViewById(R.id.bodybuilder_picture);
        arnoldPicture.setImageResource(R.drawable.arnold);

        Button animButton = (Button) findViewById(R.id.button_animationStart);
        animButton.setOnClickListener(buttonListener);

        Button displayButton = (Button) findViewById(R.id.button_displayPref);
        displayButton.setOnClickListener(displayPrefListener);

        editText = (EditText) findViewById(R.id.saveInternal);
        textView = (TextView) findViewById(R.id.prefdisplay_edittext);

        fileManager = new FileManager();


        SharedPreferences prefs = this.getSharedPreferences("sharedprefkey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.poopstring), "test value");
        editor.commit();

        textView.setText(prefs.getString(getString(R.string.poopstring), ""));

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean fileSaved = preferences.getBoolean(FILESAVED, false);

        if (fileSaved) {
            loadSavedfile();
        }

        showPrompt();

      File path =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File file = new File(path, "DemoPicture.jpg");
        path.mkdirs();



    }

    private void loadSavedfile() {
        try {
           FileInputStream fis = openFileInput(FILE_NAME);
          BufferedReader reader = new BufferedReader(new InputStreamReader(new DataInputStream(fis)));
            String line;
            while ((line = reader.readLine()) != null) {
                editText.append(line);
                editText.append("\n");
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPrompt() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setTitle("Create your passpoint sequence.");
        builder.setMessage("Click four different areas to create your password");


        AlertDialog dlg = builder.create();

        dlg.show();
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
        }

        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /*Animation arnoldFlex = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animeffect);
            arnoldPicture.startAnimation(arnoldFlex);*/
            String editTextValue = editText.getText().toString();
            /*SharedPreferences prefs = getApplicationContext().getSharedPreferences("sharedprefkey", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(SHARED_PREF_KEY, editTextValue);
            editor.commit();

            fileManager.saveFile(getApplicationContext(), "bootyFile"); */
            try {
               FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                fos.write(editTextValue.getBytes());
                fos.close();

                SharedPreferences prefs = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(FILESAVED, true);
                editor.commit();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<Point> list = db.getPoints();
            int i = 0;
            int x = 0;
            int y = 0;
            for (Point point: list) {
                Point p = list.get(i);
                x = p.x;
                y = p.y;

                i++;
            }
            Log.i(TAG, String.format("X: %s, Y: %s", x, y ));
        }
    };

    private View.OnClickListener displayPrefListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences prefs = getApplicationContext().getSharedPreferences("sharedprefkey", Context.MODE_PRIVATE);
            String prefsValue = prefs.getString(SHARED_PREF_KEY, "");
            textView.setText(prefsValue);
        }
    };

    @Override
    public void pointsCollected(List<Point> points) {
        db.storePoints(points);
    }
}
