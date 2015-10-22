package nkosi.roger.splash;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {

    EditText fullNameString, lastNameString, userAgeString, userNumberString, userParentnumberString;

    TextView userNameString;
    ProgressDialog pDialog;
    Context context = null;
    Button save;
    String line = null;
    int code;
    JSONObject jsonObject;
    String result = null;
    Dialog loginDialog;
    InputStream inputStream;
    String username_edt, name_edt, Surname_edt, Age_edt, ParCell_edt, kidCellNum;

    //String username, name, lastName, password, age, gender, kidCellNumber, parentCellNumber;


    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //php login scrip

    //testing on Emulator:
    private static final String LOGIN_URL = "http://10.0.2.2/roger/updateCredintials.php";

    //testing from a real server:
    //private static final String LOGIN_URL = "http://www.empirestate.co.za/webservice/register.php";

    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        context = this;
        //RadioGroup radioGroup = (RadioGroup) findViewById(R.id.genderSelect);
        // genderValue = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();

        userNameString = (TextView) findViewById(R.id.txtuserName_edit);
        fullNameString = (EditText) findViewById(R.id.txtName_edit);
        lastNameString = (EditText) findViewById(R.id.txtSurname_edit);
        userAgeString = (EditText) findViewById(R.id.txtAge_edit);
        userNumberString = (EditText) findViewById(R.id.txtCellNumber_edit);
        userParentnumberString = (EditText) findViewById(R.id.txtParentCellNumber_edit);

        SharedPreferences sp = getSharedPreferences("Storedata", Context.MODE_PRIVATE);
        String username = sp.getString("username", "");
        sp.getString("username", null);

        userNameString.setText("Username: " + username);
        save = (Button) findViewById(R.id.pr_button);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new updateUserData().execute("");
            }
        });
    }

    class updateUserData extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Profile.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
        }

        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            name_edt = fullNameString.getText().toString();
            Surname_edt = lastNameString.getText().toString();
            Age_edt = userAgeString.getText().toString();
            ParCell_edt = userParentnumberString.getText().toString();
            kidCellNum = userNumberString.getText().toString();

            try {

                //getting the stored username
                SharedPreferences sp = getSharedPreferences("Storedata", Context.MODE_PRIVATE);
                String username = sp.getString("username", "");
                sp.getString("username", null);

                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                //String username, name, lastName, password, age, gender, kidCellNumber, parentCellNumer;
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("fName", name_edt));
                params.add(new BasicNameValuePair("last_name",Surname_edt));
                params.add(new BasicNameValuePair("age", Age_edt));
                //params.add(new BasicNameValuePair("gender", gender));
                params.add(new BasicNameValuePair("cell_num", kidCellNum));
                params.add(new BasicNameValuePair("parent_num", ParCell_edt));

                Log.d("request!", "starting");
                //Posting user data to script
                JSONObject postInputData = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);
                System.out.println("test: " + postInputData);

                // full json response
                Log.d("Login attempt", postInputData.toString());

                // json success element
                success = postInputData.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Update Successful!", postInputData.toString());
                    finish();
                    return postInputData.getString(TAG_MESSAGE);
                } else {
                    Log.d("Failure!", postInputData.getString(TAG_MESSAGE));
                    return postInputData.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(Profile.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }
}