package com.generic.readandbill;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.generic.readandbill.database.Consumer;
import com.generic.readandbill.database.ConsumerDataSource;
import com.generic.readandbill.database.Reading;
import com.generic.readandbill.database.ReadingDataSource;
import java.util.Comparator;
import java.util.List;

public class MyConsumerList extends ListActivity {
    protected static final int DELETE_ID = 3;
    protected static final int FIELDFINDING_ID = 2;
    protected static final int REQUEST_CODE = 10;
    private static final int SORT_ACCOUNT_NUMBER = 10;
    private static final int SORT_METER_SERIAL = 40;
    private static final int SORT_NAME = 30;
    private static final int SORT_SEQUENCE = 20;
    protected ConsumerArrayAdapter adapter;
    protected ConsumerDataSource dsc;
    protected ReadingDataSource dsr;
    private EditText search;
    private int sortMode;
    protected TextView totalConsumer;
    protected List<Consumer> values;

    /* renamed from: com.generic.readandbill.MyConsumerList.1 */
    class C00091 implements TextWatcher {
        C00091() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (count < before) {
                MyConsumerList.this.adapter.resetData();
            }
            MyConsumerList.this.adapter.getFilter().filter(s.toString());
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    /* renamed from: com.generic.readandbill.MyConsumerList.2 */
    class C00102 implements Comparator<Consumer> {
        C00102() {
        }

        public int compare(Consumer lhs, Consumer rhs) {
            return lhs.getAccountNumber().compareTo(rhs.getAccountNumber());
        }
    }

    /* renamed from: com.generic.readandbill.MyConsumerList.3 */
    class C00113 implements Comparator<Consumer> {
        C00113() {
        }

        public int compare(Consumer lhs, Consumer rhs) {
            return lhs.getName().compareTo(rhs.getName());
        }
    }

    /* renamed from: com.generic.readandbill.MyConsumerList.4 */
    class C00124 implements Comparator<Consumer> {
        C00124() {
        }

        public int compare(Consumer lhs, Consumer rhs) {
            return lhs.getMeterSerial().compareTo(rhs.getMeterSerial());
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_list);
        getListView().setDividerHeight(FIELDFINDING_ID);
        this.dsc = new ConsumerDataSource(this);
        this.values = this.dsc.getAllConsumer();
        this.totalConsumer = (TextView) findViewById(R.id.cltvTotalConsumer);
        this.search = (EditText) findViewById(R.id.etclSearch);
        this.totalConsumer.setText(this.dsc.getNumberOfConsumer().toString());
        this.search.addTextChangedListener(new C00091());
        initialization();
    }

    protected void onResume() {
        super.onResume();
        if (this.adapter != null) {
            this.adapter.notifyDataSetInvalidated();
        }
    }

    public void initialization() {
        this.dsr = new ReadingDataSource(this);
        this.adapter = new ConsumerArrayAdapter(this, this.values);
        setListAdapter(this.adapter);
        this.sortMode = SORT_ACCOUNT_NUMBER;
        applySort();
        registerForContextMenu(getListView());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.iclSortByAccountNumber) {
            if (!item.isChecked()) {
                item.setChecked(true);
                this.sortMode = SORT_ACCOUNT_NUMBER;
                applySort();
                return true;
            }
       /* } else if (item.getItemId() == R.id.iclSortBySequence) {
            if (!item.isChecked()) {
                item.setChecked(true);
                this.sortMode = SORT_SEQUENCE;
                applySort();
                return true;
            }*/
        } else if (item.getItemId() == R.id.iclSortByName) {
            if (!item.isChecked()) {
                item.setChecked(true);
                this.sortMode = SORT_NAME;
                applySort();
                return true;
            }
        } else if (item.getItemId() == R.id.iclSortByMeterSerial && !item.isChecked()) {
            item.setChecked(true);
            this.sortMode = SORT_METER_SERIAL;
            applySort();
            return true;
        }
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_consumer_list, menu);
        return true;
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = myTransactionActivityCaller(this.dsr.getReading(this.adapter.getItem(position).getId(), SORT_SEQUENCE));
        intent.putExtra("id", this.adapter.getItemId(position));
        intent.putExtra("pos", position);
        startActivityForResult(intent, SORT_ACCOUNT_NUMBER);
    }

    protected Intent myTransactionActivityCaller(Reading reading) {
        if (reading.getId() == -1) {
            return new Intent(this, MyReadingEntry.class);
        }
        if (reading.getFieldFinding() != 0) {
            return new Intent(this, MyFieldFindingEntry.class);
        }
        return new Intent(this, MyReadingEntry.class);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1 && requestCode == SORT_ACCOUNT_NUMBER) {
            applySort();
            getListView().setSelection(data.getExtras().getInt("pos"));
        }
    }
 
   protected void applySort() {
        switch (this.sortMode) {
            case 10: //SORT_ACCOUNT_NUMBER: //10:
                this.adapter.sort(new C00102());
                this.adapter.setFilterMode(SORT_ACCOUNT_NUMBER);
                break;
            case 30: //SORT_NAME: //30:
                this.adapter.sort(new C00113());
                this.adapter.setFilterMode(SORT_NAME);
                break;
            case 40: //SORT_METER_SERIAL: //40:
                this.adapter.sort(new C00124());
                this.adapter.setFilterMode(SORT_METER_SERIAL);
                break;
        }
        setListAdapter(this.adapter);
        this.adapter.notifyDataSetChanged();
    }
    
    
    
}
