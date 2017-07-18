package firstapp;

import android.content.Intent;
import android.hardware.Camera;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import java.io.File;

import ooqle.firstapp.R;


public class TestActivity extends ActionBarActivity {

    private boolean solidstate;
    private boolean strobeState;
    private boolean turningoff;

    private Switch mySwitch;

    private Camera cam;
    private Camera.Parameters parameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the status bar.
        hideStatusBar();

        //init layout?
        setContentView(R.layout.activity_test);

        //set switch, camera and strobe button
        mySwitch = (Switch) findViewById(R.id.switch1);
        cam = Camera.open();
        parameters = cam.getParameters();

        //set the switch to default state
        mySwitch.setChecked(false);
        //attach a listener to check for changes in state
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            //runs when switch is changed
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    updater();
                }
                else
                {
                    turningoff = true;
                    try {
                        Thread.sleep(100);
                    }
                    catch(InterruptedException e)
                    {
                        System.out.println(e);
                    }
                    solidstate = false;
                    manOff();
                    turningoff = false;
                }
        }
        });
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
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

    private void updater()
    {
        Runnable runUpdater = new Runnable() {
            @Override
            public void run()  //regular switch only works if its not party time
                {
                    while (mySwitch.isChecked()) {
                        if (strobeState) {
                            try {
                                manOn();
                                Thread.sleep(50);
                                manOff();
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                System.out.println(e);
                            }
                        }
                        if (!turningoff) {
                            solidstate = true;
                            manOn();
                        }
                    }
                }

        };
        new Thread(runUpdater).start();
    }

    public void strobeButton(View v)
    {
        ImageButton stbut = (ImageButton) findViewById(R.id.strobeButton);
        if(strobeState)
        {
            stbut.setImageResource(R.drawable.strobeoff);
            strobeState = false;
        }
        else
        {
            stbut.setImageResource(R.drawable.strobeon);
            strobeState = true;
        }
    }

    public void partyButton(View v)
    {
        //send to party layout
        Intent iWantToParty = new Intent(TestActivity.this, Party.class);
        TestActivity.this.startActivity(iWantToParty);
    }

    public void questionMark(View v)
    {
        Intent myIntent = new Intent(TestActivity.this, About.class);
        TestActivity.this.startActivity(myIntent);
    }

    private void manOn()
    {
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        cam.setParameters(parameters);
        cam.startPreview();
    }

    private void manOff()
    {
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        cam.setParameters(parameters);
        cam.stopPreview();
    }

//    private void hideStatusBar()
//    {
//        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
//        final Window window = getWindow();
//
//        if (isFullScreen == true)
//        {
//            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            // This flag will prevent the status bar disappearing animation from jerking the content view
//            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//        }
//        else
//        {
//            window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }
//    }

    private void hideStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
    }

    public void onPause()
    {
        super.onPause();

        System.out.println("onPause");
        mySwitch.setChecked(false);
    }

    public void onStop()
    {
        super.onStop();

        System.out.println("onStop");
        cam.release();
    }

    public void onRestart()
    {
        super.onRestart();

        hideStatusBar();

        System.out.println("onRestart");
        cam = Camera.open();
        parameters = cam.getParameters();
    }

}
