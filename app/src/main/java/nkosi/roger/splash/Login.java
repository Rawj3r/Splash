package nkosi.roger.splash;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class Login extends AppCompatActivity implements  View.OnClickListener {

    Typeface custFont;
    Button login, signUp;
    EditText username, password;
    private int cellNum = 0;
    private String userNameStr,passwordStr;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //php login script location:

    //localhost :
    //testing on your device
    //put your local ip instead,  on windows, run CMD > ipconfig
    //or in mac's terminal type ifconfig and look for the ip under en0 or en1
    // private static final String LOGIN_URL = "http://xxx.xxx.x.x:1234/webservice/login.php";

    //testing on Emulator:
    private static final String LOGIN_URL = "http://10.0.2.2/roger/login.php";

    //testing from a real server:

    //JSON element ids from response of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        relay();
        animate();

        //register listeners
        login.setOnClickListener(this);
        signUp.setOnClickListener(this);
    }

    private void relay() {
        //assign the xml to the java class and edit the xml with fonts and sizes
        login = (Button)findViewById(R.id.btnSignIn);
        custFont = Typeface.createFromAsset(getAssets(), "fonts/CHUBBY.TTF");
        login.setTypeface(custFont);
        login.setTextSize(25);

        signUp = (Button)findViewById(R.id.btnsignUp);
        signUp.setTypeface(custFont);
        signUp.setTextSize(25);

        username = (EditText)findViewById(R.id.txtLoginName);
        password = (EditText)findViewById(R.id.txtLoginPassword);
        username.setTypeface(custFont);
        password.setTypeface(custFont);
        username.setTextSize(25);
        password.setTextSize(25);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btnSignIn:
                new AttemptLogin().execute();
                saveData();
                break;
            case R.id.btnsignUp:
                Intent signUpActivity = new Intent(this, Register.class);
                startActivity(signUpActivity);
                break;

            default:
                break;
        }
    }

    public void saveData(){
        try{
            File saveUsername = new File("/sdcard/users.txt");
            saveUsername.createNewFile();

            FileOutputStream fileOutputStream = new FileOutputStream(saveUsername);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);

            outputStreamWriter.append(username.getText());
            outputStreamWriter.close();
            fileOutputStream.close();

            Toast.makeText(getBaseContext(), "Success", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            //e.printStackTrace();
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    class AttemptLogin extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            String user = username.getText().toString();
            String pass = password.getText().toString();
            try {

                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", user));
                params.add(new BasicNameValuePair("password", pass));
                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

                // check your log for json response
                Log.d("Login attempt", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Successful!", json.toString());
                    SharedPreferences sp = getSharedPreferences("Storedata", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("username", user);
                    editor.commit();
                    Intent i = new Intent(Login.this, Dashboard.class);
                    i.putExtra("username", user);
                    startActivity(i);
                    finish();
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (RuntimeException e){
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
                Toast.makeText(Login.this, file_url, Toast.LENGTH_LONG).show();
            }

        }


        }

    public void btnForgotPass(View view){
        //declare variables
        final Dialog dialogCustom = new Dialog(this);
        final TextView txtCellNo;
        Button btnSend,btnCancel;

        //link java class variables with the xml file
        dialogCustom.setContentView(R.layout.activity_forgotpassword_dialog);
        dialogCustom.setTitle("Retrieve password");
        txtCellNo = (TextView)dialogCustom.findViewById(R.id.txtForgotPasswordCell);
        btnCancel = (Button)dialogCustom.findViewById(R.id.btnCancelForgotPassword);
        btnSend = (Button)dialogCustom.findViewById(R.id.btnForgotPassword);

        //used to assign the cell number variable which was entered by the user
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    cellNum = Integer.parseInt(txtCellNo.getText().toString());
                    Toast.makeText(Login.this,"Success",Toast.LENGTH_LONG).show();
                    dialogCustom.dismiss();
                }catch (IllegalArgumentException ex){
                    Toast.makeText(Login.this,"Enter a cell number",Toast.LENGTH_LONG).show();
                }
            }
        });

        //used to close the dialog box
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this,"Canceled",Toast.LENGTH_LONG).show();
                dialogCustom.dismiss();
            }
        });
        dialogCustom.show();

    }
    public void animate(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //recieve the text on the text fields
                userNameStr = username.getText().toString();
                passwordStr = password.getText().toString();

                final Animation animAlpha = AnimationUtils.loadAnimation(Login.this, R.anim.alpha_animation);
                username.startAnimation(animAlpha);

                //check if both the user name and password is not empty
                if(userNameStr.equals("")){
                    username.setHint("Enter User name");
                    username.startAnimation(animAlpha);
                    username.setTextColor(Color.WHITE);
                    username.setBackgroundColor(Color.RED);
                    Toast.makeText(Login.this,"Enter a user name",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(passwordStr.equals("")){
                    password.setHint("Enter Password");
                    password.startAnimation(animAlpha);
                    password.setTextColor(Color.WHITE);
                    password.setBackgroundColor(Color.RED);
                    Toast.makeText(Login.this,"Enter your password",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
    }