package com.example.dombeg.cashflowbanker;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ACT_Parent extends Activity {
    //==============================================================================================
    //==============================================================================================
    Handler handle;
    //protected int act_view_number;
    //protected int[] ViewID;
    static final int[] MiceDrawID =
            {
                    R.drawable.mice0,
                    R.drawable.mice1,
                    R.drawable.mice3,
                    R.drawable.mice2,
                    R.drawable.mice5,
                    R.drawable.mice4
            };
    //==============================================================================================
    //==============================================================================================
    void init_variables()
    {
        handle = new Handler();
    }
    //==============================================================================================
    //==============================================================================================
    protected void writeToGameFile(String data)
    {
        File path, gamefile;
        path = getApplicationContext().getFilesDir();
        gamefile = new File(path, "game00.txt");
        writeToFile(gamefile.getPath(),data);
    }
    //==============================================================================================
    //==============================================================================================
    void writeToFile(String path, String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(path, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("login activity", "File write failed: " + e.toString());
        }
    }
    //==============================================================================================
    //==============================================================================================
    String readFromFile(String path, int line, boolean concat)
    {
        return readFromFile(path,line,line,concat)[0];
    }
    //==============================================================================================
    //==============================================================================================
    String[] readFromFile(String path, int start_line, int end_line, boolean concat)
    {
        String []ret = new String[end_line - start_line + 1];
        String line;
        try {
            InputStream inputStream = openFileInput(path);
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder collect = new StringBuilder();
                for(int i=0; i < end_line || concat; i++)
                {
                    line = bufferedReader.readLine();
                    if(line == null) break;
                    if(i >= start_line-1)
                    {
                        line += '\n';
                        if(concat)collect.append(line);
                            else ret[(i-start_line+1)] = line;
                    }
                }
                inputStream.close();
                if(concat) ret[0] = collect.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }
    //==============================================================================================
    //==============================================================================================
}
