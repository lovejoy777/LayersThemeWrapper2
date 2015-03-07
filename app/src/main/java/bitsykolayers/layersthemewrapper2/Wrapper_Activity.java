package bitsykolayers.layersthemewrapper2;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lovejoy777 & bgill55 on 3/7/15.
 */

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

        // IF LAYERS MANAGER IS INSTALLED LAUNCH IT
        boolean installed  =   appInstalledOrNot("com.lovejoy777.rroandlayersmanager");
        if(installed) {

            // THEME NAME CHANGE THIS TO YOUR THEME NAME
            String themename = getString(R.string.themename);

            // FINAL PATH & NAME
            String finalname = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + themename + ".zip";

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

            Intent LaunchIntent = getPackageManager()
                    .getLaunchIntentForPackage("com.lovejoy777.rroandlayersmanager");
            startActivity(LaunchIntent);

        } else {

            // IF LAYERS MANAGER IS NOT INSTALLED LAUNCH THE PLAYSTORE TO DOWNLOAD
            Toast.makeText(Wrapper_Activity.this, "Layers Manager is not installed\n" +
                    "on your device", Toast.LENGTH_LONG).show();

            final String appPackageName = "com.lovejoy777.rroandlayersmanager"; // getPackageName() from Context or Activity object
            try {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            }

            catch (android.content.ActivityNotFoundException anfe) {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed ;
    }
}