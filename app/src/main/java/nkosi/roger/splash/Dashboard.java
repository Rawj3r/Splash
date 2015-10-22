package nkosi.roger.splash;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    TextView play_txt;
    TextView leaderboard_txt;
    TextView jrBank_txt;
    TextView bRequest_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);

        play_txt = (TextView) findViewById(R.id.play_txt);
        Typeface custom_p = Typeface.createFromAsset(getAssets(), "fonts/CHUBBY.TTF");
        play_txt.setTypeface(custom_p);
        play_txt.setTextSize(25);

        leaderboard_txt = (TextView) findViewById(R.id.leaderboard_txt);
        Typeface custom_l = Typeface.createFromAsset(getAssets(), "fonts/CHUBBY.TTF");
        leaderboard_txt.setTypeface(custom_l);
        leaderboard_txt.setTextSize(25);

        jrBank_txt = (TextView) findViewById(R.id.jrBank_txt);
        Typeface custom_j = Typeface.createFromAsset(getAssets(), "fonts/CHUBBY.TTF");
        jrBank_txt.setTypeface(custom_j);
        jrBank_txt.setTextSize(25);

        bRequest_txt = (TextView) findViewById(R.id.bRequest_txt);
        Typeface custom_b = Typeface.createFromAsset(getAssets(), "fonts/CHUBBY.TTF");
        bRequest_txt.setTypeface(custom_b);
        bRequest_txt.setTextSize(25);

    }

        /*(if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            setContentView(R.layout.dashboard);

            play_txt = (TextView)findViewById(R.id.play_txt);
            Typeface custom_p= Typeface.createFromAsset(getAssets(),"fonts/CHUBBY.TTF");
            play_txt.setTypeface(custom_p);
            play_txt.setTextSize(25);

            leaderboard_txt =(TextView)findViewById(R.id.leaderboard_txt);
            Typeface custom_l = Typeface.createFromAsset(getAssets(),"fonts/CHUBBY.TTF");
            leaderboard_txt.setTypeface(custom_l);
            leaderboard_txt.setTextSize(25);

            jrBank_txt = (TextView)findViewById(R.id.jrBank_txt);
            Typeface custom_j = Typeface.createFromAsset(getAssets(),"fonts/CHUBBY.TTF");
            jrBank_txt.setTypeface(custom_j);
            jrBank_txt.setTextSize(25);

            bRequest_txt = (TextView)findViewById(R.id.jrBank_txt);
            Typeface custom_b =  Typeface.createFromAsset(getAssets(),"fonts/CHUBBY.TTF");
            bRequest_txt.setTypeface(custom_b);
            bRequest_txt.setTextSize(25);


              REbankLayout = (RelativeLayout)findViewById(R.id.reJRBank);

        REbankLayout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        REbankLayout.setBackgroundColor(Color.RED);

                        break;
                    case MotionEvent.ACTION_UP:

                        //set color back to default
                        REbankLayout.setBackgroundColor(Color.WHITE);
                        break;
                }
                return true;
            }
        });
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashbord, menu);
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
    }*/
    //set play activity
    public void rePlay (View v){
        startActivity(new Intent(Dashboard.this, Gadget.class));
    }

    //set leaderboard activity
    public void reLeaderboard (View v){
        startActivity(new Intent(Dashboard.this, LeaderBoard.class));
    }
    //set JR Bank activity
    public void reBank (View v){
        startActivity(new Intent(Dashboard.this, JuniorBank.class));
    }
    //set Request activity
    public void reRequest (View v){
        startActivity(new Intent(Dashboard.this, Request.class));
    }

    public void imgProfile(View v){
        startActivity(new Intent(Dashboard.this, Profile.class));
    }
}
