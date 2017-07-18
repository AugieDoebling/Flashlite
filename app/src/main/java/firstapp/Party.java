package firstapp;

import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import ooqle.firstapp.R;

/**
 * Created by augiedoebling on 12/27/15.
 */
public class Party extends ActionBarActivity
{

    private MediaPlayer audio;
    private boolean partyTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);

        audio = MediaPlayer.create(Party.this, R.raw.testmusic);
        audio.setLooping(true);
        audio.setVolume(100, 100);

        updateColors();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //i don think this will show
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

    private void updateColors()
    {
        View background = (View) findViewById(R.id.background);

        ColorCycle cycle = new ColorCycle(getResources());

        long start = System.currentTimeMillis();
//        while(partyTime)
//        {
//            if(System.currentTimeMillis() - start > 1000)
//            {
//                System.out.println("CHANGE BACKGROUND COLOR");
//                background.setBackgroundColor(cycle.next());
//                start = System.currentTimeMillis();
//            }
//        }
    }

    public void onStop()
    {
        super.onStop();
        partyTime = false;
        audio.release();
    }

}
