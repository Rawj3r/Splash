package nkosi.roger.splash;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity implements View.OnClickListener {


    EditText username_edt, name_edt, Surname_edt, password_edt, Age_edt, ParCell_edt, gender_edt, kidCellNum;
    Button mRegister;
    //String username, name, lastName, password, age, gender, kidCellNumber, parentCellNumber;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //php login script


    //localhost :
    //testing on your device
    //put your local ip instead,  on windows, run CMD > ipconfig
    //or in mac's terminal type ifconfig and look for the ip under en0 or en1
    // private static final String LOGIN_URL = "http://xxx.xxx.x.x:1234/webservice/register.php";

    //testing on Emulator:
    private static final String LOGIN_URL = "http://10.5.0.139/roger/register.php";

    //testing from a real server:
    //private static final String LOGIN_URL = "http://www.empirestate.co.za/webservice/register.php";

    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setFont();


        mRegister = (Button)findViewById(R.id.registerButton);
        mRegister.setOnClickListener(this);

    }

    public void setFont(){
        username_edt = (EditText)findViewById(R.id.username_reg);
        Typeface custom_reg_UserN = Typeface.createFromAsset(getAssets(), "fonts/CHUBBY.TTF");
        username_edt.setTypeface(custom_reg_UserN);
        username_edt.setTextSize(25);

        name_edt = (EditText)findViewById(R.id.name_reg);
        Typeface custom_reg_Name = Typeface.createFromAsset(getAssets(), "fonts/CHUBBY.TTF");
        name_edt.setTypeface(custom_reg_Name);
        name_edt.setTextSize(25);


        Surname_edt = (EditText)findViewById(R.id.Surname_reg);
        Typeface custom_surname = Typeface.createFromAsset(getAssets(), "fonts/CHUBBY.TTF");
        Surname_edt.setTypeface(custom_surname);
        Surname_edt.setTextSize(25);


        password_edt = (EditText)findViewById(R.id.Password_reg);
        Typeface custom_password = Typeface.createFromAsset(getAssets(), "fonts/CHUBBY.TTF");
        password_edt.setTypeface(custom_password);
        password_edt.setTextSize(25);


        Age_edt = (EditText)findViewById(R.id.Age_reg);
        Typeface custom_age = Typeface.createFromAsset(getAssets(), "fonts/CHUBBY.TTF");
        Age_edt.setTypeface(custom_age);
        Age_edt.setTextSize(25);


        ParCell_edt = (EditText)findViewById(R.id.parentCell);
        Typeface custom_parCell_reg = Typeface.createFromAsset(getAssets(), "fonts/CHUBBY.TTF");
        ParCell_edt.setTypeface(custom_parCell_reg);
        ParCell_edt.setTextSize(25);

        gender_edt = (EditText)findViewById(R.id.gender_reg);
        Typeface custom_gender_reg = Typeface.createFromAsset(getAssets(), "fonts/CHUBBY.TTF");
        gender_edt.setTypeface(custom_gender_reg);
        gender_edt.setTextSize(25);

        kidCellNum  =(EditText)findViewById(R.id.childsCell);
        Typeface custom_kidMobile_reg = Typeface.createFromAsset(getAssets(), "fonts/CHUBBY.TTF");
        kidCellNum.setTypeface(custom_kidMobile_reg);
        kidCellNum.setTextSize(25);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        new CreateUser().execute();
    }


    class CreateUser extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Register.this);
            pDialog.setMessage("Creating User...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            String username = username_edt.getText().toString();
            String name = name_edt.getText().toString();
            String lastName = Surname_edt.getText().toString();
            String password = password_edt.getText().toString();
            String age = Age_edt.getText().toString();
            String gender = gender_edt.getText().toString();
            String kidCellNumber = kidCellNum.getText().toString();
            String parentCellNumber = ParCell_edt.getText().toString();

            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                //String username, name, lastName, password, age, gender, kidCellNumber, parentCellNumer;
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("fName", name));
                params.add(new BasicNameValuePair("lastName", lastName));
                params.add(new BasicNameValuePair("password", password));
                params.add(new BasicNameValuePair("age", age));
                params.add(new BasicNameValuePair("gender", gender));
                params.add(new BasicNameValuePair("cellNum", kidCellNumber));
                params.add(new BasicNameValuePair("parentNum", parentCellNumber));

                Log.d("request!", "starting");
                //Posting user data to script
                JSONObject postInputData = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);
                //System.out.println("test: " +postInputData); //testing purposes

                // full json response
                Log.d("Registering", postInputData.toString());

                // json success element
                success = postInputData.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("User Created!", postInputData.toString());
                    finish();
                    return postInputData.getString(TAG_MESSAGE);
                }else{
                    Log.d("Login Failure!", postInputData.getString(TAG_MESSAGE));
                    return postInputData.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(Register.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
