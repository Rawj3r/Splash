package nkosi.roger.splash;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Statement extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    //localhost
    //testing on Emulator:
    //private static final String GET_STATE = "http://10.0.2.2/roger/state.php?category=gadget&username=test";
    //private static final String GET_STATE = "http://10.5.0.139/roger/state.php";


    //JSON IDS:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_NAME = "Description";
    private static final String TAG_GET = "get";
    private static final String TAG_P_DATE = "Trans_Date";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_PRICE = "Amount_";

    //An array of all of user's bank statement
    private JSONArray statementData = null;
    //manages all of our comments in a list.
    private ArrayList<HashMap<String, String>> statementList;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);
    }

    public String get(){
        String catToString = "gadget";
        //getting the username on stored sharedPreferences
        SharedPreferences sp = getSharedPreferences("Storedata", Context.MODE_PRIVATE);
        String username = sp.getString("username", "");
        sp.getString("username", null);

        final String GET_STATES = "http://10.5.0.139/roger/state.php?category=gadget&username="+username;
        // Building Parameters
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("username", username));
        pairs.add(new BasicNameValuePair("category", catToString));
        return GET_STATES;
    }


    @Override
    protected void onResume() {

        // TODO Auto-generated method stub
        super.onResume();
        //loading the bank statement via AsyncTask
        new LoadComments().execute();

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

        statementList = new ArrayList<HashMap<String, String>>();
        // Bro, it's time to power up the J parser
        JSONParser jParser = new JSONParser();
        // Feed the beast our comments url, and it spits us
        //back a JSON object.  Boo-yeah Jerome.
        JSONObject json = jParser.getJSONFromUrl(get());
        //when parsing JSON stuff, we should probably
        //try to catch any exceptions:
        try {

            statementData = json.getJSONArray(TAG_GET);

            // looping through all posts according to the json object returned
            for (int i = 0; i < statementData.length(); i++){
                JSONObject c = statementData.getJSONObject(i);


                //gets the content of each tag
                String gName = c.getString(TAG_NAME);
                String gPrice = c.getString(TAG_PRICE);
                String pDate = c.getString(TAG_P_DATE);

                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();

                map.put(TAG_NAME, gName);
                map.put(TAG_PRICE, gPrice);
                map.put(TAG_P_DATE, pDate);



                // adding HashList to ArrayList
                statementList.add(map);

                //Our JSON data is up to date same with our array list
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    /**
     * Inserts the parsed data into our listview
     */
    private void updateList() {
        // For a ListActivity we need to set the List Adapter, and in order to do
        //that, we need to create a ListAdapter.  This SimpleAdapter,
        //will utilize our updated Hashmapped ArrayList,
        //use our activity_statement_custom_row xml template for each item in our list,
        //and place the appropriate info from the list to the
        //correct GUI id.  Order is important here.
        ListAdapter adapter = new SimpleAdapter(this, statementList, R.layout.activity_statement_custom_row, new String[] { TAG_NAME, TAG_PRICE,
                TAG_P_DATE }, new int[] { R.id.item_lstview, R.id.price_lstview,
                R.id.date_lstview });

        setListAdapter(adapter);
        // Optional: when the user clicks a list item we
        //could do something.  However, we will choose
        //to do nothing...

        final ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }


    public class LoadComments extends AsyncTask<Void, Void, Boolean>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Statement.this);
            pDialog.setMessage("Loading Your Statement Data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected Boolean doInBackground(Void... arg0) {

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
}