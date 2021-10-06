package com.laneco.readandbill;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
//import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.generic.readandbill.R;
import com.generic.readandbill.database.FieldFindingDataSource;
import com.laneco.readandbill.database.Consumer;
import com.laneco.readandbill.database.ConsumerDataSource;
import com.laneco.readandbill.database.Reading;
import com.laneco.readandbill.database.ReadingDataSource;
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
    protected Activity context;
    protected ConsumerDataSource dsConsumer;
    protected FieldFindingDataSource dsFieldFinding;
    protected ReadingDataSource dsReading;
    private boolean filterAllUnread;
    private int filterMode;
    private List<Consumer> origList;
    private TextView totalConsumer;

    private class ConsumerFilter extends Filter {
        private ConsumerFilter() {
        }

        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults myResults = new FilterResults();
            List<Consumer> filteredList;
            if (constraint != null && constraint.length() != 0) {
                filteredList = new ArrayList();
                boolean addIt = false;
                for (Consumer c : ConsumerArrayAdapter.this.consumers) {
                    switch (ConsumerArrayAdapter.this.filterMode) {
                        case ConsumerArrayAdapter.ACCOUNT_NUMBER /*10*/:
                            addIt = c.getAccountNumber().startsWith(constraint.toString(), 6);
                            break;
                        case ConsumerArrayAdapter.NAME /*30*/:
                            addIt = c.getName().toUpperCase(Locale.US).contains(constraint.toString().toUpperCase(Locale.US));
                            break;
                        case ConsumerArrayAdapter.METER_SERIAL /*40*/:
                            addIt = c.getMeterSerial().startsWith(constraint.toString());
                            break;
                    }
                    if (addIt) {
                        if (!ConsumerArrayAdapter.this.filterAllUnread) {
                            filteredList.add(c);
                        } else if (ConsumerArrayAdapter.this.dsReading.getReading(c.getId(), (int) ConsumerArrayAdapter.SEQUENCE).getId() == -1) {
                            filteredList.add(c);
                        }
                    }
                    
                }
                myResults.values = filteredList;
                myResults.count = filteredList.size();
            } else if (ConsumerArrayAdapter.this.filterAllUnread) {
                filteredList = new ArrayList();
                for (Consumer c2 : ConsumerArrayAdapter.this.consumers) {
                    if (ConsumerArrayAdapter.this.dsReading.getReading(c2.getId(), (int) ConsumerArrayAdapter.SEQUENCE).getId() == -1) {
                        filteredList.add(c2);
                    }
                }
                myResults.values = filteredList;
                myResults.count = filteredList.size();
            } else {
                myResults.values = ConsumerArrayAdapter.this.origList;
                myResults.count = ConsumerArrayAdapter.this.origList.size();
            }
            return myResults;
        }

        protected void publishResults(CharSequence constraint, FilterResults results) {
            ConsumerArrayAdapter.this.consumers = (List) results.values;
            if (ConsumerArrayAdapter.this.totalConsumer != null) {
                ConsumerArrayAdapter.this.totalConsumer.setText(String.valueOf(ConsumerArrayAdapter.this.consumers.size()));
            }
            ConsumerArrayAdapter.this.notifyDataSetChanged();
        }
    }

    static class ViewHolder {
        TextView acctNumber;
        TextView address;
        TextView fieldFinding;
        TextView kilowatthour;
        LinearLayout llRowLayout;
        TextView meterSerial;
        TextView name;
        TextView remarks;

        ViewHolder() {
        }
    }

    public ConsumerArrayAdapter(Activity context, List<Consumer> list) {
        super(context, R.layout.row_consumer_list, list);
        this.filterAllUnread = false;
        this.context = context;
        this.consumers = list;
        this.origList = list;
        this.dsReading = new ReadingDataSource(context);
        this.dsConsumer = new ConsumerDataSource(context);
        this.dsFieldFinding = new FieldFindingDataSource();
        this.totalConsumer = (TextView) context.findViewById(R.id.cltvTotalConsumer);
        if (this.totalConsumer != null) {
            this.totalConsumer.setText(String.valueOf(list.size()));
        }
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
            TextView tvFieldFinding = (TextView) view.findViewById(R.id.tvclFieldFinding);
            TextView tvRemarks = (TextView) view.findViewById(R.id.tvclRemarks);
            LinearLayout llRowLayout = (LinearLayout) view.findViewById(R.id.clLinerLayout);
            holder.acctNumber = (TextView) view.findViewById(R.id.tvAccountNumber);
            holder.name = tvName;
            holder.address = tvAddress;
            holder.meterSerial = tvMetSer;
            holder.kilowatthour = tvKWH;
            holder.fieldFinding = tvFieldFinding;
            holder.remarks = tvRemarks;
            holder.llRowLayout = llRowLayout;
            view.setTag(holder);
        } else {
            view = convertView;
        }
        Consumer c = (Consumer) this.consumers.get(position);
        Reading r = this.dsReading.getReading(c.getId(), (int) SEQUENCE);
        holder = (ViewHolder) view.getTag();
        holder.acctNumber.setText(c.getAccountNumber());
        holder.name.setText(c.getName());
        holder.address.setText(c.getAddress());
        holder.meterSerial.setText(c.getMeterSerial());
        holder.remarks.setText(r.getRemarks());
        if (r == null) {
            holder.kilowatthour.setText("");
        } else if (r.getId() != -1) {
            holder.kilowatthour.setText(String.valueOf(r.getKilowatthour()));
            if (r.getFieldFinding() == 0 || r.getFieldFinding() == -1) {
                holder.fieldFinding.setText("");
            } else {
                holder.fieldFinding.setText(this.dsFieldFinding.getDescription(this.dsFieldFinding.getCode(r.getFieldFinding())));
            }
        } else {
            holder.kilowatthour.setText("");
        }
        if (r.getId() != -1) {
            holder.llRowLayout.setBackgroundColor(-16711681);
        } else {
            holder.llRowLayout.setBackgroundColor(-1);
        }
        return view;
    }

    public void setFilterMode(int filterMode) {
        this.filterMode = filterMode;
    }

    public void resetData() {
        this.consumers = this.origList;
    }

    public void setFilterAllUnread(boolean filterAllUnread) {
        this.filterAllUnread = filterAllUnread;
    }

    public Filter getFilter() {
        if (this.consumerFilter == null) {
            this.consumerFilter = new ConsumerFilter();
        }
        return this.consumerFilter;
    }
}
