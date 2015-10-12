package nkosi.roger.splash;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JuniorBank extends ActionBarActivity {

    //declare variables for the xml data to be stored in
    private TextView deposit_txt,borrow_txt,request_txt,statement_txt,sentToBank_txt,username_txt,amount_txt,currency_txt;


    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_GET = "data";
    private static final String TAG_BALANCE = "Balance";

    ProgressDialog pDialog;


    //String username, name, lastName, password, age, gender, kidCellNumber, parentCellNumber;

    private static final String LOGIN_URL = "http://10.0.2.2/roger/bank.php";

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    String balance;

    //private variables to handle user data
    private double depAmount = 0.00;
    private String uNameBorrow = "",uNameRequest;
    private double amountBorrow, amountDeposit;
    private double amountRequest = 0.00,amountSend = 0.00;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_junior_bank);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        textEdit();
        SharedPreferences sp = getSharedPreferences("Storedata", Context.MODE_PRIVATE);
        String username = sp.getString("username", "");
        sp.getString("username", null);
        username_txt.setText(username);
        new getBalance().execute();
    }

    class getBalance extends AsyncTask<String, String, String>{
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(JuniorBank.this);
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... params){
            int success;
            try {

                String catToString = "gadget";

                //getting the username on stored sharedPreferences
                SharedPreferences sp = getSharedPreferences("Storedata", Context.MODE_PRIVATE);
                String username = sp.getString("username", "");
                sp.getString("username", null);
                // Building Parameters
                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                pairs.add(new BasicNameValuePair("username", username));
                pairs.add(new BasicNameValuePair("category", catToString));

                //getting the user details using the get method
                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "GET", pairs);

                // check log for json response
                Log.d("Single Product Details", json.toString());

                success = json.getInt(TAG_SUCCESS);
                if (success==1){
                    //successfully received logged in user balance
                    JSONArray balObject = json.getJSONArray(TAG_GET);
                    JSONObject bal = balObject.getJSONObject(0);

                    balance = bal.getString(TAG_BALANCE);
                }else {

                }
            }catch (JSONException e){
                e.printStackTrace();
            }
            return null;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String s) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    currency_txt.setText(balance);

                }
            });

            // dismiss the dialog once got all details
            pDialog.dismiss();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_junior_bank, menu);
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

    public void reDeposited(View v){
        //declare variables and create a new Dialog object
        final Dialog dialogCustom = new Dialog(this);
        Button btnCancelDeposit,btnDeposit;
        final TextView txtDeposit;

        //link the java variables with the xml file
        dialogCustom.setContentView(R.layout.activity_deposit_dialoge);
        dialogCustom.setTitle("Deposit");
        txtDeposit = (TextView) dialogCustom.findViewById(R.id.txtDeposit);
        btnDeposit = (Button) dialogCustom.findViewById(R.id.btnDeposit);
        btnCancelDeposit = (Button) dialogCustom.findViewById(R.id.btnCancelDeposit);
        dialogCustom.setCanceledOnTouchOutside(false);




        class Deposit extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(JuniorBank.this);
                pDialog.setMessage("Depositing...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
            }

            protected String doInBackground(String... args){

                int success;

                String deposit = txtDeposit.getText().toString();


                try{
                    SharedPreferences sp = getSharedPreferences("Storedata", Context.MODE_PRIVATE);
                    String username = sp.getString("username", "");
                    sp.getString("username", null);

                    // Building Parameters
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    //String username, name, lastName, password, age, gender, kidCellNumber, parentCellNumer;
                    params.add(new BasicNameValuePair("deposit", deposit));
                    params.add(new BasicNameValuePair("username", username));


                    android.util.Log.d("request!", "starting");
                    //Posting user data to script
                    JSONObject postInputData = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);
                    System.out.println("test: " + postInputData);

                    // full json response
                    android.util.Log.d("Depositing . . .", postInputData.toString());

                    // json success element
                    success = postInputData.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        android.util.Log.d("Update Successful!", postInputData.toString());
                        finish();
                        return postInputData.getString(TAG_MESSAGE);
                    } else {
                        android.util.Log.d("Failure!", postInputData.getString(TAG_MESSAGE));
                        return postInputData.getString(TAG_MESSAGE);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

        }

        //listener used to assign the amountDeposit variable with the users ented value
        btnDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Deposit().execute();
                dialogCustom.dismiss();

            }
        });

        //listener used to cancel a deposit
        btnCancelDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JuniorBank.this, "Cancelled", Toast.LENGTH_SHORT).show();
                dialogCustom.dismiss();
            }
        });

        dialogCustom.show();
    }

    /**
     *Created by: Avhasei
     *Date:17 September 2015
     *Task: pop up the dialog screen for the borrow when the borrow button is pressed
     * @param view responsible for handling an event
     */
    public void reBorrowed(View view){
        final TextView txtName,txtAmount;
        Button btnCancel,btnBorrow;
        final Dialog dialogCust = new Dialog(this);
        dialogCust.setContentView(R.layout.activity_borrow_dialog);

        dialogCust.setTitle("Borrow Barclions");
        txtName = (TextView) dialogCust.findViewById(R.id.txtDeposit);
        txtAmount = (TextView)dialogCust.findViewById(R.id.txtAmount);
        btnBorrow = (Button)dialogCust.findViewById(R.id.btnBorrow);
        btnCancel=(Button)dialogCust.findViewById(R.id.btnCancelBorrow);
        dialogCust.setCanceledOnTouchOutside(false);

        btnBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //try catch to make sure that both text views are not null
                try {
                    uNameBorrow = txtName.getText().toString();
                    amountBorrow = Double.parseDouble(txtAmount.getText().toString());
                    Toast.makeText(JuniorBank.this, "Success", Toast.LENGTH_SHORT).show();
                    dialogCust.dismiss();
                }catch (NullPointerException ex){
                    Toast.makeText(JuniorBank.this,"Enter values",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JuniorBank.this,"Cancelled",Toast.LENGTH_SHORT).show();
                dialogCust.dismiss();
            }
        });
        dialogCust.show();
    }

    /**
     *Created by: Avhasei
     *Date:17 September 2015
     *Task: pop up the dialog screen for the request when the request button is pressed
     * @param view responsible for handling an event
     */
    public void reRequest(View view){
        final Dialog dialogCustom = new Dialog(this);
        final TextView txtName,txtAmount;
        Button btnRequest,btnCancel;

        dialogCustom.setContentView(R.layout.activity_request_dialog);
        dialogCustom.setTitle("Request Barclions");
        txtAmount = (TextView)dialogCustom.findViewById(R.id.txtAmountRequest);
        txtName = (TextView)dialogCustom.findViewById(R.id.txtNameRequest);
        btnCancel=(Button)dialogCustom.findViewById(R.id.btnCancelRequest);
        btnRequest = (Button)dialogCustom.findViewById(R.id.btnRequest);
        dialogCustom.setCanceledOnTouchOutside(false);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    uNameRequest = txtName.getText().toString();
                    amountRequest = Double.parseDouble(txtAmount.getText().toString());
                    Toast.makeText(JuniorBank.this, "Success", Toast.LENGTH_SHORT).show();
                    dialogCustom.dismiss();
                } catch (NullPointerException ex) {
                    Toast.makeText(JuniorBank.this, "Enter values", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JuniorBank.this, "Cancelled", Toast.LENGTH_SHORT).show();
                dialogCustom.dismiss();
            }
        });

        dialogCustom.show();
    }

    /**
     *Created by: Avhasei
     *Date:17 September 2015
     *Task: pop up the dialog screen for the send to bank when the send to bank button is pressed
     * @param view responsible for handling an event
     */
    public void reSendToBank(View view){

        //declare variables
        final Dialog dialogCustom = new Dialog(this);
        final TextView txtAmount;
        Button btnCancel,btnSend;

        //assign the java class variables with the xml data
        dialogCustom.setContentView(R.layout.activity_send_dialog);
        dialogCustom.setTitle("Send to bank");
        txtAmount = (TextView)dialogCustom.findViewById(R.id.txtAmountSend);
        btnCancel = (Button)dialogCustom.findViewById(R.id.btnCancelSend);
        btnSend = (Button)dialogCustom.findViewById(R.id.btnCancelSend);
        dialogCustom.setCanceledOnTouchOutside(false);

        //send button on the dialog used to assing the amountSend variable with that of the entered amount from the xml file
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    amountSend = Double.parseDouble(txtAmount.getText().toString());
                    Toast.makeText(JuniorBank.this, "Success", Toast.LENGTH_SHORT).show();
                    dialogCustom.show();
                } catch (NullPointerException ex) {
                    Toast.makeText(JuniorBank.this, "Enter value", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //cancel button used to cancel the send to bank dialog
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JuniorBank.this, "Cancelled", Toast.LENGTH_SHORT).show();
                dialogCustom.dismiss();
            }
        });

        dialogCustom.dismiss();
    }

    /**
     *Created by: Avhasei
     *Date:17 September 2015
     *Task: start a new activity of the statement screen when the statement button is pressed
     * @param view responsible for handling an event
     */
    public void reStatement(View view){
        startActivity(new Intent(JuniorBank.this, Statement.class));
    }

    public void textEdit(){
        //assign the java varables to the xml (link the two) then set the text fonts and sizes
        //custom username text
        username_txt = (TextView)findViewById(R.id.username);
        Typeface customFonts_u = Typeface.createFromAsset(getAssets(),"fonts/CHUBBY.TTF");
        username_txt.setTypeface(customFonts_u);
        username_txt.setTextSize(20);


        //custom amount text
        amount_txt = (TextView)findViewById(R.id.amount_txt);
        Typeface customFonts_amt = Typeface.createFromAsset(getAssets(),"fonts/CHUBBY.TTF");
        amount_txt.setTypeface(customFonts_amt);
        amount_txt.setTextSize(20);

        //custom currency text
        currency_txt = (TextView)findViewById(R.id.txtJRBankAmount);
        Typeface customFonts_curr = Typeface.createFromAsset(getAssets(),"fonts/CHUBBY.TTF");
        currency_txt.setTypeface(customFonts_curr);
        currency_txt.setTextSize(20);

        //custom deposit text
        deposit_txt = (TextView)findViewById(R.id.txt_deposit);
        Typeface customFonts_d = Typeface.createFromAsset(getAssets(),"fonts/CHUBBY.TTF");
        deposit_txt .setTypeface(customFonts_d);
        deposit_txt.setTextSize(25);

        //custom borrow text
        borrow_txt = (TextView)findViewById(R.id.borrow_txt);
        Typeface customFonts_b = Typeface.createFromAsset(getAssets(),"fonts/CHUBBY.TTF");
        borrow_txt.setTypeface(customFonts_b);
        borrow_txt.setTextSize(25);



        request_txt = (TextView)findViewById(R.id.request_txt);
        Typeface customFonts_r = Typeface.createFromAsset(getAssets(),"fonts/CHUBBY.TTF");
        request_txt.setTypeface(customFonts_r);
        request_txt.setTextSize(25);

        statement_txt = (TextView)findViewById(R.id.statement_txt);
        Typeface customFonts_s = Typeface.createFromAsset(getAssets(),"fonts/CHUBBY.TTF");
        statement_txt.setTypeface(customFonts_s);
        statement_txt.setTextSize(25);


        sentToBank_txt = (TextView)findViewById(R.id.sendToBank_txt);
        Typeface customFonts_stb = Typeface.createFromAsset(getAssets(),"fonts/CHUBBY.TTF");
        sentToBank_txt.setTypeface(customFonts_stb);
        sentToBank_txt.setTextSize(25);
    }
}
