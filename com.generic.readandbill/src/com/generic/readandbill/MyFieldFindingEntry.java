package com.generic.readandbill;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.generic.readandbill.database.Consumer;
import com.generic.readandbill.database.ConsumerDataSource;
import com.generic.readandbill.database.FieldFindingDataSource;
import com.generic.readandbill.database.FieldFindings;
import com.generic.readandbill.database.Reading;
import com.generic.readandbill.database.ReadingDataSource;
//import com.laneco.readandbill.ConsumerArrayAdapter;
import java.util.ArrayList;
import java.util.List;

public class MyFieldFindingEntry extends Activity implements OnClickListener,OnFocusChangeListener{
	 protected final int INSERT_ACTIVITY;
	    protected final int UPDATE_ACTIVITY;
	    protected int activityMode;
	    protected Consumer consumer;
	    protected ConsumerDataSource dsConsumer;
	    protected ReadingDataSource dsReading;
	    protected FieldFindingDataSource dsfieldfinding;
	    protected Bundle extras;
	    protected EditText ffCode;
	    protected List<FieldFindings> fieldFindings;
	    private int listPosition;
	    private TextView mAccountNumber;
	    private TextView mAddress;
	    protected Button mCancel;
	    protected Button mConfirm;
	    protected Spinner mFieldFinding;
	    private TextView mInitialReading;
	    protected TextView mMultiplier;
	    private TextView mName;
	    private TextView mRateCode;
	    protected TextView mRemarks;
	    protected Reading reading;
	    protected boolean updateList;

	    /* renamed from: com.generic.readandbill.MyFieldFindingEntry.1 */
	    class C00131 implements OnItemSelectedListener {
	        C00131() {
	        }

	        public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
	            MyFieldFindingEntry.this.mConfirm.setEnabled(MyFieldFindingEntry.this.mFieldFinding.getSelectedItemPosition() != 0);
	        }

	        public void onNothingSelected(AdapterView<?> adapterView) {
	            MyFieldFindingEntry.this.mConfirm.setEnabled(false);
	        }
	    }

	    public MyFieldFindingEntry() {
	        this.updateList = false;
	        this.INSERT_ACTIVITY = 10;
	        this.UPDATE_ACTIVITY = 20;
	    }

	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_fieldfinding_entry);
	        if (savedInstanceState != null) {
	            this.extras = savedInstanceState;
	        } else {
	            this.extras = getIntent().getExtras();
	        }
	        this.dsfieldfinding = new FieldFindingDataSource();
	        this.dsReading = new ReadingDataSource(this);
	        this.dsConsumer = new ConsumerDataSource(this);
	        this.fieldFindings = this.dsfieldfinding.getAllFieldFindings();
	        this.listPosition = this.extras.getInt("pos");
	        initControls();
	    }

	    protected void initControls() {
	        this.consumer = this.dsConsumer.getConsumer(Long.valueOf(this.extras.getLong("id")));
	        this.reading = this.dsReading.getReading(this.consumer.getId(), 20);
	        this.mAccountNumber = (TextView) findViewById(R.id.tvCIAccountNumber);
	        this.mRateCode = (TextView) findViewById(R.id.tvCIRateCode);
	        this.mMultiplier = (TextView) findViewById(R.id.tvCIMultiplier);
	        this.mName = (TextView) findViewById(R.id.tvCIName);
	        this.mAddress = (TextView) findViewById(R.id.tvCIAddress);
	        this.mInitialReading = (TextView) findViewById(R.id.tvCIInitialReading);
	        this.mRemarks = (TextView) findViewById(R.id.etFFERemarks);
	        this.mAccountNumber.setText(this.consumer.getAccountNumber());
	        this.mRateCode.setText(this.consumer.getRateCode());
	        this.mName.setText(this.consumer.getName());
	        this.mAddress.setText(this.consumer.getAddress());
	        this.mInitialReading.setText(String.valueOf(this.consumer.getInitialReading()));
	        ArrayAdapter<String> adapter = new ArrayAdapter(this, 17367049, setList());
	        adapter.setDropDownViewResource(17367049);
	        this.mFieldFinding = (Spinner) findViewById(R.id.spnFieldFindings);
	        this.mFieldFinding.setAdapter(adapter);
	        this.mFieldFinding.setSelection(0);
	        this.mFieldFinding.setOnItemSelectedListener(new C00131());
	        this.mConfirm = (Button) findViewById(R.id.bfConfirm);
	        this.mCancel = (Button) findViewById(R.id.bfCancel);
	        this.mRemarks.setText("");
	        this.mConfirm.setEnabled(false);
	        this.mConfirm.setOnClickListener(this);
	        this.mCancel.setOnClickListener(this);
	        if (this.reading == null || this.reading.getId() == -1) {
	            this.reading.setidConsumer(this.consumer.getId());
	            this.activityMode = 10;
	            return;
	        }
	        this.activityMode = 20;
	        String fieldFinding = this.dsfieldfinding.getDescription(this.dsfieldfinding.getCode(this.reading.getFieldFinding()));
	        for (int i = 0; i < this.fieldFindings.size(); i++) {
	            if (((String) this.mFieldFinding.getItemAtPosition(i)).equalsIgnoreCase(fieldFinding)) {
	                this.mFieldFinding.setSelection(i);
	                break;
	            }
	        }
	        this.mRemarks.setText(this.reading.getRemarks());
	    }

	    public void finish() {
	        if (this.updateList) {
	            Intent data = new Intent();
	            data.putExtra("id", this.consumer.getId());
	            data.putExtra("pos", this.listPosition);
	            setResult(-1, data);
	        } else {
	            setResult(0);
	        }
	        super.finish();
	    }

	    protected void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        if (!(this.reading == null || this.mFieldFinding.getSelectedItemPosition() == 0)) {
	            switch (this.activityMode) {
	                case ConsumerArrayAdapter.ACCOUNT_NUMBER /*10*/:
	                    this.dsReading.createReading(this.reading);
	                    break;
	                case ConsumerArrayAdapter.SEQUENCE /*20*/:
	                    this.dsReading.updateReading(this.reading);
	                    break;
	            }
	        }
	        outState.putLong("id", this.consumer.getId());
	    }

	    protected List<String> setList() {
	        List<FieldFindings> ffs = this.dsfieldfinding.getFfs();
	        List<String> spinnerArray = new ArrayList();
	        for (int i = 0; i < ffs.size(); i++) {
	            spinnerArray.add(((FieldFindings) ffs.get(i)).getfDescription());
	        }
	        this.ffCode = (EditText) findViewById(R.id.etFFEFieldFindingsCode);
	        this.ffCode.setOnFocusChangeListener(this);
	        return spinnerArray;
	    }

	    public void onClick(View v) {
	        if (v.getId() == R.id.bfConfirm) {
	            this.updateList = false;
	            passData();
	            switch (this.activityMode) {
	                case ConsumerArrayAdapter.ACCOUNT_NUMBER /*10*/:
	                    this.dsReading.createReading(this.reading);
	                    this.updateList = true;
	                    break;
	                case ConsumerArrayAdapter.SEQUENCE /*20*/:
	                    this.dsReading.updateReading(this.reading);
	                    this.updateList = true;
	                    break;
	            }
	            finish();
	        } else if (v.getId() == R.id.bfCancel) {
	            finish();
	        }
	    }

	    protected void passData() {
	        Time readingDate = new Time();
	        readingDate.setToNow();
	        this.reading.setidConsumer(this.consumer.getId());
	        this.reading.setRemarks(this.mRemarks.getText().toString());
	        this.reading.setFieldFinding((long) this.mFieldFinding.getSelectedItemPosition());
	        this.reading.setIsAM(Boolean.valueOf(readingDate.format("%p").equals("AM")));
	        this.reading.setReadingDate(readingDate.format("%m%d"));
	    }

	    public void onFocusChange(View v, boolean hasFocus) {
	        if (v.getId() == R.id.etFFEFieldFindingsCode) {
	            if (!hasFocus && !this.ffCode.getText().toString().trim().equals("")) {
	                for (int i = 0; i < this.mFieldFinding.getCount(); i++) {
	                    if (((String) this.mFieldFinding.getItemAtPosition(i)).equals(this.dsfieldfinding.getDescription(this.ffCode.getText().toString()))) {
	                        this.mFieldFinding.setSelection(i);
	                    }
	                }
	            }
	        } else if (v.getId() == R.id.spnFieldFindings && !hasFocus) {
	            this.ffCode.setText(this.dsfieldfinding.getCode(this.mFieldFinding.getItemAtPosition(this.mFieldFinding.getSelectedItemPosition()).toString()));
	        }
	    }
}
