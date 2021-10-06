package com.laneco.readandbill;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import com.generic.readandbill.R;
import com.generic.readandbill.database.tmpVariable;
import com.laneco.readandbill.database.Consumer;
import com.laneco.readandbill.database.ConsumerDataSource;
import com.laneco.readandbill.database.ReadingDataSource;
import java.util.Comparator;
import java.util.List;
public class SummaryList extends com.generic.readandbill.SummaryList{
	private static final int READ = 20;
    private static final int UNREAD = 10;
    private String activityTitle;
    private ConsumerArrayAdapter adapter;
    private ConsumerDataSource dsc;
    private ReadingDataSource dsr;
    private String sortingType;
    private List<Consumer> values;

    /* renamed from: com.laneco.readandbill.SummaryList.1 */
    class C00411 implements TextWatcher {
        C00411() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (SummaryList.this.adapter != null) {
                if (count < before) {
                    SummaryList.this.adapter.resetData();
                }
                SummaryList.this.adapter.getFilter().filter(s.toString());
            }
        }

        public void afterTextChanged(Editable s) {
        }
    }

    /* renamed from: com.laneco.readandbill.SummaryList.2 */
    class C00422 implements Comparator<Consumer> {
        C00422() {
        }

        public int compare(Consumer lhs, Consumer rhs) {
            return lhs.getAccountNumber().compareTo(rhs.getAccountNumber());
           //return lhs.getName().compareTo(rhs.getName());
        }
    }

    /* renamed from: com.laneco.readandbill.SummaryList.3 */
    class C00433 implements Comparator<Consumer> {
        C00433() {
        }

        public int compare(Consumer lhs, Consumer rhs) {
            return lhs.getName().compareTo(rhs.getName());
        }
    }

    /* renamed from: com.laneco.readandbill.SummaryList.4 */
    class C00444 implements Comparator<Consumer> {
        C00444() {
        }

        public int compare(Consumer lhs, Consumer rhs) {
            return lhs.getMeterSerial().compareTo(rhs.getMeterSerial());
        }
    }

    public SummaryList() {
        this.activityTitle = "Read Consumer Summary";
        this.sortingType = "(Account Number)";
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.dsc = new ConsumerDataSource(this);
        this.dsc = new ConsumerDataSource(this);
        this.dsr = new ReadingDataSource(this);
        setContentView(R.layout.activity_summary);
        setTitle(this.activityTitle + " " + this.sortingType);
        this.search = (EditText) findViewById(R.id.etslSearch);
        this.totalConsumer = (TextView) findViewById(R.id.tvslTotalRecord);
        this.totalRead = (TextView) findViewById(R.id.tvslTotalRead);
        this.totalUnread = (TextView) findViewById(R.id.tvslTotalUnread);
    }

    protected TextWatcher searchTextWatcher() {
        return new C00411();
    }

    protected void onResume() {
        super.onResume();
        changeListMode(READ);
    }

    private void changeListMode(int mode) {
        String str;
        switch (mode) {
            case UNREAD /*10*/:
                str = "Unread Consumer Summary";
                this.activityTitle = str;
                this.activityTitle = str;
                this.values = this.dsc.getUnreadSummary();
                this.adapter = new ConsumerArrayAdapter(this, this.values);
                this.totalConsumer.setText(this.dsc.getNumberOfConsumer().toString());
                this.totalUnread.setText(Integer.toString(this.values.size()));
                this.totalRead.setText(Integer.toString(this.dsc.getNumberOfConsumer().intValue() - this.values.size()));
                break;
            case READ /*20*/:
                str = "Read Consumer Summary";
                this.activityTitle = str;
                this.activityTitle = str;
                this.values = this.dsc.getReadSummary();
                this.adapter = new ConsumerArrayAdapter(this, this.values);
                this.totalConsumer.setText(this.dsc.getNumberOfConsumer().toString());
                this.totalRead.setText(Integer.toString(this.values.size()));
                this.totalUnread.setText(Integer.toString(this.dsc.getNumberOfConsumer().intValue() - this.values.size()));
                break;
        }
        switch (this.sortMode) {
            case UNREAD /*10*/:
                this.sortingType = "(Account Number";
                break;
            case ConsumerArrayAdapter.NAME /*30*/:
                this.sortingType = "(Name)";
                break;
            case ConsumerArrayAdapter.METER_SERIAL /*40*/:
                this.sortingType = "(Meter Serial)";
                break;
            default:
                this.sortingType = "";
                break;
        }
        setTitle(this.activityTitle + " " + this.sortingType);
        applySort();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem unReadOnly = menu.findItem(R.id.iclSeeOnlyUnread);
        MenuItem printSummary = menu.findItem(R.id.islPrintSummary);
        unReadOnly.setVisible(false);
        printSummary.setVisible(true);
        menu.add(0, 1, 5, "Switch to unread");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("Switch to unread")) {
            item.setTitle("Switch to read");
            changeListMode(UNREAD);
            return true;
        } else if (item.getTitle().equals("Switch to read")) {
            item.setTitle("Switch to unread");
            changeListMode(READ);
            return true;
        }else if (item.getItemId() == R.id.iclSortByAccountNumber) {
            if (item.isChecked()) {
                return true;
            }
            item.setChecked(true);
            this.sortMode = SORT_ACCOUNT_NUMBER;
            applySort();
            return true;
        } else if (item.getItemId() == R.id.iclSortByName) {
            if (item.isChecked()) {
                return true;
            }
            item.setChecked(true);
            this.sortMode = SORT_NAME;
            applySort();
            return true;
        } else if (item.getItemId() != R.id.iclSortByMeterSerial) {
            //return super.onOptionsItemSelected(item);
        	if (item.isChecked()) {
                return true;
            }
            item.setChecked(true);
            this.sortMode = SORT_METER_SERIAL;
            applySort();
            return true;     
        //=============================   
        } else if (item.getItemId() != R.id.islPrintSummary) {
            return super.onOptionsItemSelected(item);
        } else {
            SplashScreen.btPrinter.print(new SummaryDataGenerator(this, this.values, this.dsc.getNumberOfConsumer()).getSummary());
            return true;
        }
    }

    protected void applySort() {
        if (this.adapter != null) {
            switch (this.sortMode) {
                case UNREAD /*10*/:
                    this.adapter.sort(new C00422());
                    this.adapter.setFilterMode(UNREAD);
                    break;
                case ConsumerArrayAdapter.NAME /*30*/:
                    this.adapter.sort(new C00433());
                    this.adapter.setFilterMode(30);
                    break;
                case ConsumerArrayAdapter.METER_SERIAL /*40*/:
                    break;
            }
            this.adapter.sort(new C00444());
            this.adapter.setFilterMode(40);
            setListAdapter(this.adapter);
            this.adapter.notifyDataSetChanged();
            
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
        
    }
}
