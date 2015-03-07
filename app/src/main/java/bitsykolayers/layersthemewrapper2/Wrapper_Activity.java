package bitsykolayers.layersthemewrapper2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class Wrapper_Activity extends Activity {
    static final String TAG = "copyingFile";

    Button button;

    public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_wrapper);

            button = (Button) findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    runtask1();
                }
            });
    }



    public void runtask1() {


        // setContentView(R.layout.activity_main);


        // THEME NAME CHANGE THIS TO YOUR THEME NAME
        String themename = getString(R.string.themename);


        // RENAME END STRING TO YOUR LAYERS FOLDER NAME
        String wrapperTemplatePath = getApplicationInfo().dataDir + "/" + themename;

        // DIR COPY NAME
        String copywrapperTemplatePath = getApplicationInfo().dataDir + "/" + themename + "/" + themename;

        // TEMP PATH & NAME
        String temppath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + themename;

        // FINAL PATH & NAME
        String finalname = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + themename + ".zip";

        // DIR COPY NAME
        String newname = "/" + themename;



        int id = this.getResources().getIdentifier(themename, "raw", this.getPackageName());

        // COPIES THEME FILE TO SDCARD FOLDER
        InputStream in = getResources().openRawResource(id);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(finalname);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "fileoutputstream is null", e);
        }
        byte[] buff = new byte[1024];
        int read = 0;

        try {
            try {
                while ((read = in.read(buff)) > 0) {
                    if (out != null) {
                        out.write(buff, 0, read);
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, "out write is null", e);
            }
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                Log.e(TAG, "in is null", e);
            }

            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "out is null", e);
            }
        }

        finish();

        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.lovejoy777.rroandlayersmanager");
        startActivity(LaunchIntent);

    }
}