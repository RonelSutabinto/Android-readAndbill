package com.generic.readandbill;
import android.app.ListActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import com.generic.readandbill.database.Consumer;
import com.generic.readandbill.database.ConsumerDataSource;
import com.generic.readandbill.database.ReadingDataSource;
import com.generic.readandbill.database.tmpVariable;


import java.util.Comparator;
import java.util.List;
public class SummaryList extends ListActivity{
	  protected static final int SORT_ACCOUNT_NUMBER = 10;
	    protected static final int SORT_METER_SERIAL = 40;
	    protected static final int SORT_NAME = 30;
	    protected static final int SORT_NOTHING = 5;
	    protected static final int SORT_SEQUENCE = 20;
	    protected static final String TAG = "SummaryList";
	    protected ConsumerArrayAdapter adapter;
	    protected ConsumerDataSource dsc;
	    protected ReadingDataSource dsr;
	    protected EditText search;
	    protected int sortMode;
	    protected TextView totalConsumer;
	    protected TextView totalRead;
	    protected TextView totalUnread;
	    protected List<Consumer> values;

	    /* renamed from: com.generic.readandbill.SummaryList.1 */
	    class C00201 implements TextWatcher {
	        C00201() {
	        }

	        public void onTextChanged(CharSequence s, int start, int before, int count) {
	            if (SummaryList.this.adapter != null) {
	                if (count < before) {
	                    SummaryList.this.adapter.resetData();
	                }
	                SummaryList.this.adapter.getFilter().filter(s.toString());
	            }
	        }

	        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	        }

	        public void afterTextChanged(Editable s) {
	        }
	    }

	    /* renamed from: com.generic.readandbill.SummaryList.2 */
	    class C00212 implements Comparator<Consumer> {
	        C00212() {
	        }

	        public int compare(Consumer lhs, Consumer rhs) {
	            return lhs.getAccountNumber().compareTo(rhs.getAccountNumber());
	        }
	    }

	    /* renamed from: com.generic.readandbill.SummaryList.3 */
	    class C00223 implements Comparator<Consumer> {
	        C00223() {
	        }

	        public int compare(Consumer lhs, Consumer rhs) {
	            return lhs.getName().compareTo(rhs.getName());
	        }
	    }

	    /* renamed from: com.generic.readandbill.SummaryList.4 */
	    class C00234 implements Comparator<Consumer> {
	        C00234() {
	        }

	        public int compare(Consumer lhs, Consumer rhs) {
	            return lhs.getMeterSerial().compareTo(rhs.getMeterSerial());
	        }
	    }

	    public SummaryList() {
	        this.sortMode = SORT_NOTHING;
	    }

	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_summary);
	        getListView().setDividerHeight(2);
	        this.search = (EditText) findViewById(R.id.etslSearch);
	        this.totalRead = (TextView) findViewById(R.id.tvslTotalRead);
	        this.totalUnread = (TextView) findViewById(R.id.tvslTotalUnread);
	        this.totalConsumer = (TextView) findViewById(R.id.tvslTotalRecord);
	        this.dsc = new ConsumerDataSource(this);
	        this.dsr = new ReadingDataSource(this);
	        this.search.addTextChangedListener(searchTextWatcher());
	    }

	    protected TextWatcher searchTextWatcher() {
	        return new C00201();
	    }

	    protected void onResume() {
	        super.onResume();
	        Integer totalConsumer = this.dsc.getNumberOfConsumer();
	        Integer totalRead = this.dsr.getTotalRead();
	        Integer totalUnread = Integer.valueOf(totalConsumer.intValue() - totalRead.intValue());
	        this.values = this.dsc.getAllUnReadConsumer();
	        this.totalRead.setText(totalRead.toString());
	        this.totalUnread.setText(totalUnread.toString());
	        this.totalConsumer.setText(totalConsumer.toString());
	        if (this.adapter == null) {
	            this.adapter = new ConsumerArrayAdapter(this, this.values);
	            setListAdapter(this.adapter);
	        } else {
	            this.adapter.notifyDataSetChanged();
	        }
	        if (this.sortMode == SORT_NOTHING) {
	            this.sortMode = SORT_ACCOUNT_NUMBER;
	            if (this.adapter != null) {
	              //  applySort();
	            }
	        }
	    }

	    public boolean onOptionsItemSelected(MenuItem item) {
	        if (item.getItemId() == R.id.iclSortByAccountNumber) {
	            if (item.isChecked()) {
	                return true;
	            }
	            item.setChecked(true);
	            this.sortMode = SORT_ACCOUNT_NUMBER;
	            //applySort();
	            return true;
	        } else if (item.getItemId() == R.id.iclSortByName) {
	            if (item.isChecked()) {
	                return true;
	            }
	            item.setChecked(true);
	            this.sortMode = SORT_NAME;
	            //applySort();
	            return true;
	        } else if (item.getItemId() != R.id.iclSortByMeterSerial) {
	            return super.onOptionsItemSelected(item);
	        } else {
	            if (item.isChecked()) {
	                return true;
	            }
	            item.setChecked(true);
	            this.sortMode = SORT_METER_SERIAL;
	            //applySort();
	            return true;
	        }
	    }

	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.menu_consumer_list, menu);
	        return true;
	    }

	  /*  protected void applySort() {
	        switch (this.sortMode) {
	            case SORT_ACCOUNT_NUMBER: //10/:
	                this.adapter.sort(new C00212());
	                this.adapter.setFilterMode(SORT_ACCOUNT_NUMBER);
	                break;
	            case SORT_NAME: //30/:
	                this.adapter.sort(new C00223());
	                this.adapter.setFilterMode(SORT_NAME);
	                break;
	            case SORT_METER_SERIAL: //40/:
	                this.adapter.sort(new C00234());
	                this.adapter.setFilterMode(SORT_METER_SERIAL);
	                break;
	        }
	        setListAdapter(this.adapter);
	        this.adapter.notifyDataSetChanged();
	    }*/

	    protected void onDestroy() {
	        super.onDestroy();
	    }
}
