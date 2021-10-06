package com.generic.readandbill;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
//import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import android.widget.TextView;
import com.generic.readandbill.database.Consumer;
import com.generic.readandbill.database.ConsumerDataSource;
import com.generic.readandbill.database.Reading;
import com.generic.readandbill.database.ReadingDataSource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ConsumerArrayAdapter extends ArrayAdapter<Consumer> implements Filterable {
    public static final int ACCOUNT_NUMBER = 10;
    public static final int METER_SERIAL = 40;
    public static final int NAME = 30;
    public static final int SEQUENCE = 20;
    private Filter consumerFilter;
    private List<Consumer> consumers;
    protected final Activity context;
    protected ConsumerDataSource dsConsumer;
    protected ReadingDataSource dsReading;
    private int filterMode;
    protected DecimalFormat kilowattFormatter;
    private List<Consumer> origList;

    private class ConsumerFilter extends Filter {
        private ConsumerFilter() {
        }

        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults myResults = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                myResults.values = ConsumerArrayAdapter.this.origList;
                myResults.count = ConsumerArrayAdapter.this.origList.size();
            } else {
                List<Consumer> filteredList = new ArrayList();
                boolean addIt = false;
                for (Consumer c : ConsumerArrayAdapter.this.consumers) {
                    switch (ConsumerArrayAdapter.this.filterMode) {
                        case ConsumerArrayAdapter.ACCOUNT_NUMBER /*10*/:
                            addIt = c.getAccountNumber().startsWith(constraint.toString());
                            break;
                        case ConsumerArrayAdapter.NAME /*30*/:
                            addIt = c.getName().toUpperCase(Locale.US).indexOf(constraint.toString().trim().toUpperCase(Locale.US), 0) > -1;
                            break;
                        case ConsumerArrayAdapter.METER_SERIAL /*40*/:
                            addIt = c.getMeterSerial().startsWith(constraint.toString());
                            break;
                    }
                    if (addIt) {
                        filteredList.add(c);
                    }
                }
                myResults.values = filteredList;
                myResults.count = filteredList.size();
            }
            return myResults;
        }

        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0) {
                ConsumerArrayAdapter.this.notifyDataSetInvalidated();
                return;
            }
            ConsumerArrayAdapter.this.consumers = (List) results.values;
            ConsumerArrayAdapter.this.notifyDataSetChanged();
        }
    }

    static class ViewHolder {
        protected TextView acctNumber;
        protected TextView address;
        protected TextView kilowatthour;
        protected TextView meterSerial;
        protected TextView name;

        ViewHolder() {
        }
    }

    public ConsumerArrayAdapter(Activity context, List<Consumer> list) {
        super(context, R.layout.row_consumer_list, list);
        this.kilowattFormatter = new DecimalFormat("##,###,###,##0.0");
        this.context = context;
        this.consumers = list;
        this.origList = list;
        this.dsReading = new ReadingDataSource(context);
        this.dsConsumer = new ConsumerDataSource(context);
    }

    public int getCount() {
        return this.consumers.size();
    }

    public Consumer getItem(int position) {
        return (Consumer) this.consumers.get(position);
    }

    public long getItemId(int position) {
        return ((Consumer) this.consumers.get(position)).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = this.context.getLayoutInflater().inflate(R.layout.row_consumer_list, null);
            holder = new ViewHolder();
            TextView tvName = (TextView) view.findViewById(R.id.tvName);
            TextView tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            TextView tvMetSer = (TextView) view.findViewById(R.id.tvMeterSerial);
            TextView tvKWH = (TextView) view.findViewById(R.id.tvKilowatthour);
            holder.acctNumber = (TextView) view.findViewById(R.id.tvAccountNumber);
            holder.name = tvName;
            holder.address = tvAddress;
            holder.meterSerial = tvMetSer;
            holder.kilowatthour = tvKWH;
            view.setTag(holder);
        } else {
            view = convertView;
        }
        Consumer c = (Consumer) this.consumers.get(position);
        Reading r = this.dsReading.getReading(c.getId(), SEQUENCE);
        holder = (ViewHolder) view.getTag();
        holder.acctNumber.setText(c.getAccountNumber());
        holder.name.setText(c.getName());
        holder.address.setText(c.getAddress());
        holder.meterSerial.setText(c.getMeterSerial());
        if (r == null) {
            holder.kilowatthour.setText("0");
        } else if (r.getId() == -1) {
            holder.kilowatthour.setText("0");
        } else if (r.getFieldFinding() == 0 || r.getFieldFinding() == -1) {
            holder.kilowatthour.setText(this.kilowattFormatter.format(r.getKilowatthour()));
        } else {
            holder.kilowatthour.setText("FF");
        }
        return view;
    }
    
   

    public int getFilterMode() {
        return this.filterMode;
    }

    public void setFilterMode(int filterMode) {
        this.filterMode = filterMode;
    }

    public void resetData() {
        this.consumers = this.origList;
    }

    public Filter getFilter() {
        if (this.consumerFilter == null) {
            this.consumerFilter = new ConsumerFilter();
        }
        return this.consumerFilter;
    }
}
