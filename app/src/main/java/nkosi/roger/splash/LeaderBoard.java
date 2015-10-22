package nkosi.roger.splash;


import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import java.util.List;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import android.widget.ListAdapter;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Academy_Intern on 15/09/10.
 */



public class LeaderBoard extends ListActivity {

    TextView rank_txt;
    TextView player_txt;
    TextView time_txt;
    ImageView player_profile;

    ListView leaderboard_listView;

    String[] rank_number_LeaderBoard;
    int[] img_player_LeaderBoard;
    String[] name_player_leaderboard;
    String[] time_player_leaderboard;

    //An array of all of user's bank statement
    private JSONArray leaderData = null;
    //manages all of our data in a list.
    private ArrayList<HashMap<String, String>> leaderList;

    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_GET = "leaderboard";
    private static final String USER_NAME = "name";
    private static final String PROGRESS = "progress";
    private static final String TAG_BALANCE = "Balance";
    private static final String USER_ID = "user_id";

    ProgressDialog pDialog;


    //String username, name, lastName, password, age, gender, kidCellNumber, parentCellNumber;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        rank_txt = (TextView)findViewById(R.id.ld_Ranks);
        Typeface custom_ldRank =Typeface.createFromAsset(getAssets(),"fonts/CHUBBY.TTF");
        rank_txt.setTypeface(custom_ldRank);
        rank_txt.setTextSize(20);



        player_txt = (TextView)findViewById(R.id.ld_player);
        Typeface custom_ldPlayer = Typeface.createFromAsset(getAssets(),"fonts/CHUBBY.TTF");
        player_txt.setTypeface(custom_ldPlayer);
        player_txt.setTextSize(20);

        time_txt = (TextView)findViewById(R.id.ld_time);
        Typeface custom_ldTime = Typeface.createFromAsset(getAssets(), "fonts/CHUBBY.TTF");
        time_txt.setTypeface(custom_ldTime);
        time_txt.setTextSize(20);


        Resources res = getResources();

        //data resources of data type string[]
        //retrieves the array items of the rank_number
        rank_number_LeaderBoard = res.getStringArray(R.array.rank_number);


        //data resources of data type string[]
        //retrieves the array items of the rank_names
        name_player_leaderboard = res.getStringArray(R.array.rank_names);

        img_player_LeaderBoard = new int[]{R.drawable.user, R.drawable.user, R.drawable.user, R.drawable.user, R.drawable.user};

        //data resources of data type string[]
        //retrieves the array items of the rank_time
        time_player_leaderboard = res.getStringArray(R.array.rank_time);



        //leaderboard_listView = (ListView)findViewById(R.id.leaderboard_lstview);

        //new instance of the object declared for the data resources
       // myCustomAdapterLeaderB myAdapter_lstView_leaderB = new myCustomAdapterLeaderB(this,rank_number_LeaderBoard,img_player_LeaderBoard,name_player_leaderboard,time_player_leaderboard);

        //leaderboard_listView.setAdapter(myAdapter_lstView_leaderB);

    }

    public String get(){

        //getting the username on stored sharedPreferences
        SharedPreferences sp = getSharedPreferences("Storedata", Context.MODE_PRIVATE);
        String username = sp.getString("username", "");
        sp.getString("username", null);

        final String GET_BOARD = "http://10.0.2.2/roger/leaderboard.php?username="+username;
        // Building Parameters
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("username", username));
        return GET_BOARD;
    }

    @Override
    protected void onResume() {

        // TODO Auto-generated method stub
        super.onResume();
        //loading the bank statement via AsyncTask
        new getLeaderBoard().execute();

    }

    /**
     * Retrieves json data of bank statement
     */
    public void updateJSONdata() {
        // Instantiate the arraylist to contain all the JSON data.
        // we are going to use a bunch of key-value pairs, referring
        // to the json element name, and the content.
        // Building Parameters
        // List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        //pairs.add(new BasicNameValuePair("username", username));
        //pairs.add(new BasicNameValuePair("category", catToString));

        leaderList = new ArrayList<HashMap<String, String>>();
        // Bro, it's time to power up the J parser
        JSONParser jParser = new JSONParser();
        // Feed the beast our leaderboard url, and it spits us
        //back a JSON object.
        JSONObject json = jParser.getJSONFromUrl(get());
        //when parsing JSON stuff, we should probably
        //try to catch any exceptions:
        try {

            String pos = null;

            leaderData = json.getJSONArray(TAG_GET);

            // looping through all posts according to the json object returned
            for (int i = 0; i < leaderData.length(); i++){
                JSONObject c = leaderData.getJSONObject(i);

                System.out.println(i);



                //gets the content of each tag
                String user_id = c.getString(USER_ID);
                String name = c.getString(USER_NAME);
                String progress = c.getString(PROGRESS);


                //System.out.println(user_id);

                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();

                String row = Integer.toString(i);
                //map.put("row", row);
                //map.put(USER_ID, user_id);
                map.put(USER_NAME, name);
                map.put(PROGRESS, progress);

                //map.put(TAG_P_DATE, pDate);

                // adding HashList to ArrayList
                leaderList.add(map);

              /*  if (leaderList.add(map)){
                    count++;w
                    System.out.println(count);
                } */


                //Our JSON data is up to date same with our array list
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    public void updateList(){
        // For a ListActivity we need to set the List Adapter, and in order to do
        //that, we need to create a ListAdapter.  This SimpleAdapter,
        //will utilize our updated Hashmapped ArrayList,
        //use our activity_statement_custom_row xml template for each item in our list,
        //and place the appropriate info from the list to the
        //correct GUI id.  Order is important here.
        ListAdapter listAdapter = new SimpleAdapter(this, leaderList, R.layout.activity_leaderboard_custom_row, new String[]{
                USER_NAME, PROGRESS}, new int[]{R.id.name_leaderB, R.id.times_leadearB});

        setListAdapter(listAdapter);


        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // This method is triggered if an item is click within our
                // list. For our example we won't be using this, but
                // it is useful to know in real life applications.

            }
        });
    }

    public class getLeaderBoard extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LeaderBoard.this);
            pDialog.setMessage("Loading ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected Boolean doInBackground(Void... arg0) {
            //we will develop this method in version 2
            updateJSONdata();
            return null;

        }
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            //we will develop this method in version 2
            updateList();
        }
    }

    class viewHolder{
        TextView arrRank_numLeaderB;
        TextView arrName_playerLeaderB;
        ImageView arrImages_leaderB;
        TextView arrTime_playerLeaderB;


        viewHolder(View v) {

            arrRank_numLeaderB = (TextView)v.findViewById(R.id.rank_nbr_leaderB);
            arrName_playerLeaderB = (TextView)v.findViewById(R.id.name_leaderB);
            arrImages_leaderB = (ImageView)v.findViewById(R.id.img_leaderB);
            arrTime_playerLeaderB = (TextView)v.findViewById(R.id.times_leadearB);

        }
    }

    class myCustomAdapterLeaderB extends ArrayAdapter{

        Context context;
        String[] rank_Nums_leaderB;
        int[] img_players_leaderB;
        String[] name_players_leaderB;
        String[] time_players_leaderB;

        public myCustomAdapterLeaderB(Context context,String[] rank_Nums_leaderB, int[] img_players_leaderB, String[] name_players_leaderB,String[] time_players_leaderB)
        {

            super(context, R.layout.activity_leaderboard_custom_row,R.id.rank_nbr_leaderB, rank_Nums_leaderB);


            this.context = context;
            this.rank_Nums_leaderB = rank_Nums_leaderB;
            this.img_players_leaderB = img_players_leaderB;
            this.name_players_leaderB = name_players_leaderB;
            this.time_players_leaderB = time_players_leaderB;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = convertView;
            viewHolder v_Holder = null;

            //if row is null meaning is being created for the first time
            if(row == null)
            {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.activity_leaderboard_custom_row,parent,false);
                v_Holder = new viewHolder(row);
                row.setTag(v_Holder);

            }
            else
            {
                v_Holder = (viewHolder) row.getTag();
            }


            v_Holder.arrRank_numLeaderB.setText(rank_Nums_leaderB[position]);
            v_Holder.arrName_playerLeaderB.setText(name_players_leaderB[position]);
            v_Holder.arrTime_playerLeaderB.setText(time_players_leaderB[position]);

            return row;

        }
    }
}
