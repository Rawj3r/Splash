package nkosi.roger.splash;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Gadget extends AppCompatActivity {
    TextView txtBalance, txtNumOfJetpacks, txtNumOfClocks;

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

    /**
     *Created by: Avhasei
     *Date:17 September 2015
     *Task: pop up the dialog screen for the borrow when the borrow button is pressed
     * @param view responsible for handling an event
     */
    public void JetPack(View view){
        final TextView txtCalculationJetpack;
        Button btnPurchaseJetGadget,btnCancelJetGadgetPurchase;
        final Dialog dialogCust = new Dialog(this);
        dialogCust.setContentView(R.layout.activity_gadget_dialogjetpack);

        dialogCust.setTitle("Gadget Purchase");
        txtNumOfJetpacks = (TextView)dialogCust.findViewById(R.id.txtNumOfJetpacks);
        txtCalculationJetpack = (TextView)dialogCust.findViewById(R.id.txtCalculationJetPack);
        btnPurchaseJetGadget = (Button)dialogCust.findViewById(R.id.btnPurchaseJetGadget);
        btnCancelJetGadgetPurchase=(Button)dialogCust.findViewById(R.id.btnCancelJetGadgetPurchase);
        dialogCust.setCanceledOnTouchOutside(false);

        txtCalculationJetpack.setText("B29 - B2 = B27");
        btnPurchaseJetGadget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


    /**
     *Created by: Avhasei
     *Date:17 September 2015
     *Task: pop up the dialog screen for the borrow when the borrow button is pressed
     * @param view responsible for handling an event
     */
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

        txtCalculationClock.setText("B29 - B2 = B27");
        btnPurchaseClockGadget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
