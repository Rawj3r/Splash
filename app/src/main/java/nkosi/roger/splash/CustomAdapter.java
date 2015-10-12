package nkosi.roger.splash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Academy_Intern on 15/10/06.
 */
public class CustomAdapter extends ArrayAdapter<getStatement> {
    int stateId;
    ArrayList<getStatement> records;
    Context context;
    public CustomAdapter(Context context, int vg, int id, ArrayList<getStatement>records){
        super(context, vg, id);
        this.context = context;

        stateId = vg;

        this.records = records;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(stateId, parent, false);
        TextView gadget_name = (TextView) itemView.findViewById(R.id.item_lstview);
        TextView item_price = (TextView) itemView.findViewById(R.id.price_lstview);
        TextView trans_date = (TextView) itemView.findViewById(R.id.date_lstview);

        gadget_name.setText(records.get(position).get_gadget_name());
        item_price.setText(records.get(position).get_gadget_price());
        trans_date.setText(records.get(position).trans_date());

        return itemView;
    }
}

class getStatement{
    String gName, date;
    int gPrice;
    public void setpName(String gName){
        this.gName=gName;
    }
    public void setgPrice(int gPrice){
        this.gPrice=gPrice;
    }
    public void setDate(String date){
        this.date=date;
    }

    public String get_gadget_name(){
        return gName;
    }
    public int get_gadget_price(){
        return gPrice;
    }
    public String trans_date(){
        return date;
    }
}
