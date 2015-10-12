class Deposit extends AsyncTask<String, String, String>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(JuniorBank.this);
                pDialog.setMessage("Creating User...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
            }

            protected String doInBackground(String... args) {
                // TODO Auto-generated method stub
                // Check for success tag
                int success;

                deposit = txtDeposit.getText().toString();

                try {

                    SharedPreferences sp = getSharedPreferences("Storedata", Context.MODE_PRIVATE);
                    String username = sp.getString("username", "");
                    sp.getString("username", null);
                    // Building Parameters
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    //String username, name, lastName, password, age, gender, kidCellNumber, parentCellNumer;
                    params.add(new BasicNameValuePair("deposit", deposit));
                    params.add(new BasicNameValuePair("username", username));

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
        }