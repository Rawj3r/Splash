package nkosi.roger.splash;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Gadget extends AppCompatActivity {
    TextView txtBalance, txtNumOfJetpacks, txtNumOfClocks;

    ProgressDialog pDialog;

    private static final String GADGET_URL = "http://10.5.0.139/roger/gadget.php";
    //private static final String GADGET_URL = "http://10.0.2.2/roger/gadget.php";
    JSONParser jsonParser = new JSONParser();

    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_GET = "data";
    private static final String TAG_BALANCE = "Balance";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gadget);

        txtBalance = (TextView)findViewById(R.id.txtBalance);
        txtNumOfJetpacks = (TextView)findViewById(R.id.txtNumOfJetpacks);
        txtNumOfClocks = (TextView)findViewById(R.id.txtNumOfClocks);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gadget, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }


    public void JetPack(View view){
        Button btnPurchaseJetGadget, btnCancelJetGadgetPurchase;
        final Dialog dialogCust = new Dialog(this);
        dialogCust.setContentView(R.layout.activity_gadget_dialogjetpack);

        dialogCust.setTitle("Gadget Purchase");
        txtNumOfJetpacks = (TextView)dialogCust.findViewById(R.id.txtNumOfJetpacks);
        btnPurchaseJetGadget = (Button)dialogCust.findViewById(R.id.btnPurchaseJetGadget);
        btnCancelJetGadgetPurchase=(Button)dialogCust.findViewById(R.id.btnCancelJetGadgetPurchase);
        dialogCust.setCanceledOnTouchOutside(false);

        class PJetPack extends AsyncTask<String, String, String>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(Gadget.this);
                pDialog.setMessage("Purchasing Jet Pack");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
            }

            protected String doInBackground(String... args){
                int success = 1;
                String numJetPacks = "1";
                String amountJet = "7.10";
                String gadgetId = "4";

                try{
                    SharedPreferences sp = getSharedPreferences("Storedata", Context.MODE_PRIVATE);
                    String username = sp.getString("username", "");
                    sp.getString("username", null);

                    // Building Parameters
                    List<NameValuePair> params = new ArrayList<NameValuePair>();

                    params.add(new BasicNameValuePair("unit_", numJetPacks));
                    params.add(new BasicNameValuePair("username", username));
                    params.add(new BasicNameValuePair("amount_", amountJet));
                    params.add(new BasicNameValuePair("g_id", gadgetId));

                    android.util.Log.d("Purchasing JetPack!", "starting");
                    //Posting user data to script
                    JSONObject postInputData = jsonParser.makeHttpRequest(GADGET_URL, "GET", params);

                    // full json response
                    android.util.Log.d("Purchasing JetPack!", postInputData.toString());

                    // json success element
                    success = postInputData.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        android.util.Log.d("Jet Purchase Success", postInputData.toString());
                        finish();
                        return postInputData.getString(TAG_MESSAGE);
                    } else {
                        android.util.Log.d("Failure!", postInputData.getString(TAG_MESSAGE));
                        return postInputData.getString(TAG_MESSAGE);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                return null;
            }
        }

        btnPurchaseJetGadget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PJetPack().execute();
                Toast.makeText(Gadget.this, "Success", Toast.LENGTH_SHORT).show();
                dialogCust.dismiss();
            }
        });
        btnCancelJetGadgetPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Gadget.this,"Cancelled",Toast.LENGTH_SHORT).show();
                dialogCust.dismiss();
            }
        });
        dialogCust.show();
    }


    public void Clock(View view){
        final TextView txtCalculationClock;
        Button btnPurchaseClockGadget,btnCancelClockGadgetPurchase;
        final Dialog dialogCust = new Dialog(this);
        dialogCust.setContentView(R.layout.activity_gadget_dialogclock);

        dialogCust.setTitle("Gadget Purchase");
        txtCalculationClock = (TextView)dialogCust.findViewById(R.id.txtCalculationClock);
        btnPurchaseClockGadget = (Button)dialogCust.findViewById(R.id.btnPurchaseClockGadget);
        btnCancelClockGadgetPurchase=(Button)dialogCust.findViewById(R.id.btnCancelClockGadgetPurchase);
        dialogCust.setCanceledOnTouchOutside(false);


        class PClock extends AsyncTask<String, String, String>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(Gadget.this);
                pDialog.setMessage("Purchasing Clock");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
            }

            @Override
            protected String doInBackground(String... params) {
                int success = 1;
                String numClocks = "1";
                String amountClock = "5.25";
                String gadgetId = "2";

                try {

                    SharedPreferences sp = getSharedPreferences("Storedata", Context.MODE_PRIVATE);
                    String username = sp.getString("username", "");
                    sp.getString("username", null);


                    // Building Parameters
                    List<NameValuePair> parameter = new ArrayList<NameValuePair>();

                    parameter.add(new BasicNameValuePair("unit_", numClocks));
                    parameter.add(new BasicNameValuePair("username", username));
                    parameter.add(new BasicNameValuePair("amount_", amountClock));
                    parameter.add(new BasicNameValuePair("g_id", gadgetId));

                    android.util.Log.d("Purchasing JetPack!", "starting");
                    //Posting user data to script
                    JSONObject postInputData = jsonParser.makeHttpRequest(GADGET_URL, "GET", parameter);

                    // full json response
                    android.util.Log.d("Purchasing JetPack!", postInputData.toString());

                    // json success element
                    success = postInputData.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        android.util.Log.d("Jet Purchase Success", postInputData.toString());
                        finish();
                        return postInputData.getString(TAG_MESSAGE);
                    } else {
                        android.util.Log.d("Failure!", postInputData.getString(TAG_MESSAGE));
                        return postInputData.getString(TAG_MESSAGE);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                return null;
            }
        }
        btnPurchaseClockGadget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PClock().execute();
                Toast.makeText(Gadget.this, "Success", Toast.LENGTH_SHORT).show();
                dialogCust.dismiss();
            }
        });
        btnCancelClockGadgetPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Gadget.this,"Cancelled",Toast.LENGTH_SHORT).show();
                dialogCust.dismiss();
            }
        });
        dialogCust.show();
    }
}
