package com.example.kotobaexamplesexport;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void exportSentences(View view){
        if(!isExternalStorageWritable()) {
            Toast.makeText(this, "External storage is not writable", Toast.LENGTH_LONG).show();
            return;
        }

        //File file = createDocumentFile("examples.txt");
        File root = android.os.Environment.getExternalStorageDirectory();

        // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder
        File dir = new File (root.getAbsolutePath() + "/download");
        dir.mkdirs();
        File file = new File(dir, "myData.txt");

        if(file != null){
            try {
                FileOutputStream f = new FileOutputStream(file);
                PrintWriter writer = new PrintWriter(f);
                DataFileSentence dfs = new DataFileSentence(this);
                for(int idx = 0; idx < dfs.InfoLength(); idx++){
                    DataFileSentence.SentenceInfo info = dfs.InfoEntry(idx);
                    String line = info.TextEn() + "\t" + info.TextJp();
                        writer.println(line);
                }

                writer.flush();
                writer.close();
            }
            catch(FileNotFoundException e){
            }
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

//    public File createDocumentFile(String fileName) {
//        // Get the directory for the user's public pictures directory.
//        File file = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_DOWNLOADS), fileName);
//        if (!file.mkdirs()) {
//            Toast.makeText(this, "Unable to create the file.", Toast.LENGTH_SHORT).show();
//        }
//        return file;
//    }
}
