package nkosi.roger.splash;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    TextView textView;
    Animation animation;
    Typeface customFont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        get();
        threads();
        font();

    }

    public void threads(){
        // create a new thread
        Thread count = new Thread() {
            public void run() {
                try {
                    // try to sleep for 5 seconds
                    sleep(2000);
                    // catch interrupted exceptions
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // then finally we are going to run this statement, no
                    // matter what happens this statement should run.
                } finally {

                    // define an Intent which we are going to use for starting
                    // another activity using the <intent-filter> of the android
                    // manifest file
                    Intent startIntent = new Intent("android.intent.action.LOGIN");

                    // I am going to use the startActivity() method from the
                    // Activity class
                    startActivity(startIntent);
                }
            }
        };

        // create a new thread
        Thread countToAnimate = new Thread() {
            public void run() {
                try {
                     get();

                    // catch RuntimeExceptions
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    // then finally we are going to run this statement, no
                    // matter what happens this statement should run.

                    //Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
                } finally {
                    textView.setText("Dodge obstacles and get to the bank in the least time, with coins in your backpack. (Education and Entertainment).");
                    animation = AnimationUtils.loadAnimation(Splash.this, R.anim.abc_slide_in_bottom);
                    textView.startAnimation(animation);

                   // textView.startAnimation(AnimationUtils.loadAnimation(Splash.this, android.R.animator.fade_in));
                }
            }
        };
        countToAnimate.start();
        // execute the count thread
        count.start();
    }

    public void get(){
        textView = (TextView)findViewById(R.id.intro);
    }

    public void font(){
        customFont = Typeface.createFromAsset(getAssets(), "fonts/CHUBBY.TTF");
        textView.setTypeface(customFont);
    }

    // destroy the splash screen, the screen will be destroyed as soon as the
    // next activity has been called
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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
}
