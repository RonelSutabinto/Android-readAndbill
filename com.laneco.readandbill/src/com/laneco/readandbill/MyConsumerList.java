package com.laneco.readandbill;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;
import com.generic.readandbill.R;
import com.laneco.readandbill.database.Consumer;
import com.laneco.readandbill.database.ConsumerDataSource;
import com.laneco.readandbill.database.Reading;
import com.laneco.readandbill.database.ReadingDataSource;
import java.util.Comparator;
import java.util.List;

public class MyConsumerList extends ListActivity {
    protected static final int REQUEST_CODE = 10;
    private static final int SORT_ACCOUNT_NUMBER = 10;
    private static final int SORT_METER_SERIAL = 40;
    private static final int SORT_NAME = 30;
    private static final int SORT_SEQUENCE = 20;
    private Activity activity;
    private String activityTitle;
    protected ConsumerArrayAdapter adapter;
    private Context context;
    protected ConsumerDataSource dsc;
    protected ReadingDataSource dsr;
    private EditText search;
    private int sortMode;
    protected List<Consumer> values;
    
   

    /* renamed from: com.laneco.readandbill.MyConsumerList.1 */
    class C00311 implements TextWatcher {
        C00311() {
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

    /* renamed from: com.laneco.readandbill.MyConsumerList.2 */
    /*class C00322 implements Comparator<Consumer> {
        C00322() {
        }

        public int compare(Consumer lhs, Consumer rhs) {
            return lhs.getAccountNumber().compareTo(rhs.getAccountNumber());
        }
    }*/

    /* renamed from: com.laneco.readandbill.MyConsumerList.3 */
    /*class C00333 implements Comparator<Consumer> {
        C00333() {
        }

        public int compare(Consumer lhs, Consumer rhs) {
            return lhs.getName().compareTo(rhs.getName());
        }
    }*/

    /* renamed from: com.laneco.readandbill.MyConsumerList.4 */
    /*class C00344 implements Comparator<Consumer> {
        C00344() {
        }

        public int compare(Consumer lhs, Consumer rhs) {
            return lhs.getMeterSerial().compareTo(rhs.getMeterSerial());
        }
    }*/

    public MyConsumerList() {
        this.activityTitle = "Consumer List";
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_list);
        getListView().setDividerHeight(2);
        this.dsc = new ConsumerDataSource(this);
        this.values = this.dsc.getAllLanecoConsumer();
        setTitle(this.activityTitle + " (Account Number");
        this.search = (EditText) findViewById(R.id.etclSearch);
        this.context = this;
        this.activity = this;
        this.search.addTextChangedListener(new C00311());
        initialization();
    }

    protected void onResume() {
        super.onResume();
        if (this.adapter != null) {
            this.adapter.notifyDataSetInvalidated();
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Consumer Context Menu");
        menu.add(0, v.getId(), 0, "Reading Entry");
        menu.add(0, v.getId(), 0, "Reprint");
        menu.add(0, v.getId(), 0, "Delete Reading");
    }

    public void initialization() {
        this.dsr = new ReadingDataSource(this);
        this.adapter = new ConsumerArrayAdapter(this, this.values);
        setListAdapter(this.adapter);
        this.sortMode = SORT_ACCOUNT_NUMBER;
        applySort();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        String sortingType;
        boolean z = true;
        if (item.getItemId() == R.id.iclSortByAccountNumber) {
            if (!item.isChecked()) {
                item.setChecked(true);
                this.sortMode = SORT_ACCOUNT_NUMBER;
                setTitle("Consumer List (AccountNumber)");
                applySort();
                return true;
            }
        } else if (item.getItemId() == R.id.iclSortByName) {
            if (!item.isChecked()) {
                item.setChecked(true);
                this.sortMode = SORT_NAME;
                setTitle("Consumer List (Name)");
                applySort();
                return true;
            }
        } else if (item.getItemId() == R.id.iclSortByMeterSerial) {
            if (!item.isChecked()) {
                item.setChecked(true);
                this.sortMode = SORT_METER_SERIAL;
                setTitle("Consumer List (Meter Serial)");
                applySort();
                return true;
            }
        } else if (item.getItemId() == R.id.iclSeeOnlyUnread) {
            if (item.isChecked()) {
                z = false;
            }
            item.setChecked(z);
            if (item.isChecked()) {
                this.activityTitle = "Unread Consumer List";
            } else {
                this.activityTitle = "Consumer List";
            }
            this.adapter.setFilterAllUnread(item.isChecked());
            this.adapter.getFilter().filter(this.search.getText());
        }
        switch (this.sortMode) {
            case SORT_ACCOUNT_NUMBER /*10*/:
                sortingType = "(Account Number)";
                break;
            case SORT_NAME /*30*/:
                sortingType = "(Name)";
                break;
            case SORT_METER_SERIAL /*40*/:
                sortingType = "(Meter Serial)";
                break;
            default:
                sortingType = "";
                break;
        }
        setTitle(this.activityTitle + " " + sortingType);
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_consumer_list, menu);
        return true;
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Reading reading = this.dsr.getReading(this.adapter.getItem(position).getId(), (int) SORT_SEQUENCE);
        if (reading.getId() != -1) {
            registerForContextMenu(getListView());
            l.showContextMenuForChild(v);
            return;
        }
        Intent intent = myTransactionActivityCaller(reading);
        intent.putExtra("id", this.adapter.getItemId(position));
        intent.putExtra("pos", position);
        startActivityForResult(intent, SORT_ACCOUNT_NUMBER);
    }

    public boolean onContextItemSelected(MenuItem item) {
        long position = (long) ((AdapterContextMenuInfo) item.getMenuInfo()).position;
        Consumer consumer = this.adapter.getItem((int) position);
        Reading reading = this.dsr.getReading(consumer.getId(), (int) SORT_SEQUENCE);
        if (item.getTitle().equals("Delete Reading")) {
            this.dsr.updateReadingCancelled(position+1);
            this.adapter.notifyDataSetChanged();
        } else if (item.getTitle().equals("Reading Entry")) {
            Intent intent = myTransactionActivityCaller(reading);
            intent.putExtra("id", this.adapter.getItemId((int) position));
            intent.putExtra("pos", position);
            startActivityForResult(intent, SORT_ACCOUNT_NUMBER);
        } else if (item.getTitle().equals("Reprint")) {
            SplashScreen.btPrinter.print(new StatementGenerator(this.context, consumer, reading).generateSOA());
        }
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1 && requestCode == SORT_ACCOUNT_NUMBER) {
            applySort();
        }
    }

    /*Origin
    private void applySort() {
        switch (this.sortMode) {
            case 10: //SORT_ACCOUNT_NUMBER 
                this.adapter.sort(new C00322());
                this.adapter.setFilterMode(SORT_ACCOUNT_NUMBER);
            case 30: //SORT_NAME 
                this.adapter.sort(new C00333());
                this.adapter.setFilterMode(SORT_NAME);
            case 40: //SORT_METER_SERIAL 
                this.adapter.sort(new C00344());
                this.adapter.setFilterMode(SORT_METER_SERIAL);
            default:
        }
    } 
    
    
    */
    
    protected void applySort() {
		switch (sortMode) {
		case SORT_ACCOUNT_NUMBER:			
			
			adapter.sort(new Comparator<Consumer>() {
				
				@Override
				public int compare(Consumer lhs, Consumer rhs) {
					return lhs.getAccountNumber().compareTo(
						   rhs.getAccountNumber());
				}

			});
			adapter.setFilterMode(ConsumerArrayAdapter.ACCOUNT_NUMBER);
			break;
		case SORT_SEQUENCE:
			break;
		case SORT_NAME:
			adapter.sort(new Comparator<Consumer>() {

				@Override
				public int compare(Consumer lhs, Consumer rhs) {
					return lhs.getName().compareTo(rhs.getName());
				}

			});
			adapter.setFilterMode(ConsumerArrayAdapter.NAME);
			break;
		case SORT_METER_SERIAL:
			adapter.sort(new Comparator<Consumer>() {

				
				@Override
				public int compare(Consumer lhs, Consumer rhs) {
					return lhs.getMeterSerial().compareTo(rhs.getMeterSerial());
				}

			});
			adapter.setFilterMode(ConsumerArrayAdapter.METER_SERIAL);
			break;
		}
		setListAdapter(adapter);
		adapter.notifyDataSetChanged();		
		
	}
    

    protected Intent myTransactionActivityCaller(Reading reading) {
        if (reading.getId() != -1) {
            return new Intent(this, MyReadingEntry.class);
        }
        return new Intent(this, MyReadingEntry.class);
    }

    public void onContextMenuClosed(Menu menu) {
        super.onContextMenuClosed(menu);
        unregisterForContextMenu(getListView());
    }
}
