package com.generic.readandbill;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.generic.readandbill.database.ComputeCharges;
import com.generic.readandbill.database.Consumer;
import com.generic.readandbill.database.ConsumerDataSource;
import com.generic.readandbill.database.Reading;
import com.generic.readandbill.database.ReadingDataSource;
import com.lamerman.SelectionMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
public class MyReadingEntry extends Activity implements OnClickListener{
	protected static final int INSERT_ACTIVITY = 10;
    protected static final String TAG = "ReadingEntry";
    protected static final int UPDATE_ACTIVITY = 20;
    protected int activityMode;
    protected DecimalFormat amountFormatter;
    protected ComputeCharges compute;
    protected Consumer consumer;
    protected Context context;
    protected ConsumerDataSource dsConsumer;
    protected ReadingDataSource dsReading;
    protected Bundle extras;
    protected DecimalFormat kilowattFormatter;
    private int listPosition;
    private TextView mAccountNumber;
    private TextView mAddress;
    private Button mCancel;
    private Button mConfirm;
    private Button mConfirmAndPrint;
    protected EditText mFeedBackCode;
    private TextView mInitialReading;
    protected TextView mKilowatthour;
    private TextView mMultiplier;
    private TextView mName;
    private TextView mRateCode;
    protected EditText mReading;
    protected TextView mRemarks;
    protected TextView mTotalBill;
    protected Reading reading;
    protected boolean updateList;

    /* renamed from: com.generic.readandbill.MyReadingEntry.1 */
    class C00141 implements OnFocusChangeListener {
        C00141() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus && !MyReadingEntry.this.mReading.getText().toString().trim().equals("")) {
                MyReadingEntry.this.assignToReading();
            } else if (hasFocus) {
                MyReadingEntry.this.mReading.selectAll();
            }
            MyReadingEntry.this.mConfirm.setEnabled(MyReadingEntry.this.mReading.getText().toString() != "");
        }
    }

    /* renamed from: com.generic.readandbill.MyReadingEntry.2 */
    class C00152 implements OnFocusChangeListener {
        C00152() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (!(hasFocus || MyReadingEntry.this.mFeedBackCode.getText().toString().trim().equals(""))) {
                if (hasFocus || MyReadingEntry.this.mReading.getText().toString().trim().equals("")) {
                    MyReadingEntry.this.reading.setFeedBackCode(MyReadingEntry.this.mFeedBackCode.getText().toString());
                } else {
                    MyReadingEntry.this.assignToReading();
                }
            }
            MyReadingEntry.this.mConfirm.setEnabled(MyReadingEntry.this.mReading.getText().toString() != "");
        }
    }

    /* renamed from: com.generic.readandbill.MyReadingEntry.3 */
    class C00163 implements TextWatcher {
        C00163() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
            MyReadingEntry.this.mConfirm.setEnabled(MyReadingEntry.this.mReading.getText().toString() != "");
        }
    }

    public MyReadingEntry() {
        this.updateList = false;
        this.kilowattFormatter = new DecimalFormat("##,###,###,##0.0");
        this.amountFormatter = new DecimalFormat("##,###,###,##0.00");
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_entry);
        if (savedInstanceState != null) {
            this.extras = savedInstanceState;
        } else {
            this.extras = getIntent().getExtras();
        }
        this.context = this;
        this.dsConsumer = new ConsumerDataSource(this);
        this.dsReading = new ReadingDataSource(this);
        this.consumer = this.dsConsumer.getConsumer(Long.valueOf(this.extras.getLong("id")));
        this.reading = this.dsReading.getReading(this.consumer.getId(), UPDATE_ACTIVITY);
        if (this.reading.getIdConsumer() == -1) {
            this.reading.setidConsumer(this.consumer.getId());
        }
        this.listPosition = this.extras.getInt("pos");
        initControls();
    }

    protected void initControls() {
        this.compute = new ComputeCharges(this, this.consumer);
        this.mAccountNumber = (TextView) findViewById(R.id.tvCIAccountNumber);
        this.mRateCode = (TextView) findViewById(R.id.tvCIRateCode);
        this.mMultiplier = (TextView) findViewById(R.id.tvCIMultiplier);
        this.mName = (TextView) findViewById(R.id.tvCIName);
        this.mAddress = (TextView) findViewById(R.id.tvCIAddress);
        this.mInitialReading = (TextView) findViewById(R.id.tvCIInitialReading);
        this.mFeedBackCode = (EditText) findViewById(R.id.etREFeedbackcode);
        this.mReading = (EditText) findViewById(R.id.etREReading);
        this.mKilowatthour = (TextView) findViewById(R.id.tvREKilowatthour);
        this.mTotalBill = (TextView) findViewById(R.id.tvRETotalBill);
        this.mRemarks = (TextView) findViewById(R.id.tvRERemarks);
        this.mConfirm = (Button) findViewById(R.id.brConfirm);
        this.mConfirmAndPrint = (Button) findViewById(R.id.brConfirmAndPrint);
        this.mCancel = (Button) findViewById(R.id.brCancel);
        this.mAccountNumber.setText(this.consumer.getAccountNumber());
        this.mRateCode.setText(this.consumer.getRateCode());
        this.mMultiplier.setText(String.valueOf(this.consumer.getMultiplier()));
        this.mName.setText(this.consumer.getName());
        this.mAddress.setText(this.consumer.getAddress());
        if (this.mInitialReading != null) {
            this.mInitialReading.setText(localizedFormat(Double.valueOf(this.consumer.getInitialReading()), Locale.getDefault()));
        }
        this.mConfirm.setEnabled(false);
        this.mReading.setText("");
        this.mKilowatthour.setText("");
        this.mTotalBill.setText("");
        this.mRemarks.setText("");
        this.mReading.setOnFocusChangeListener(new C00141());
        this.mFeedBackCode.setOnFocusChangeListener(new C00152());
        this.mReading.addTextChangedListener(new C00163());
        this.mConfirm.setOnClickListener(this);
        this.mConfirmAndPrint.setOnClickListener(this);
        this.mCancel.setOnClickListener(this);
        if (this.reading == null || this.reading.getId() == -1) {
            this.activityMode = INSERT_ACTIVITY;
            return;
        }
        this.activityMode = UPDATE_ACTIVITY;
        assignUI();
    }

    protected void assignUI() {
        this.mFeedBackCode.setText(this.reading.getFeedBackCode().toString());
        this.mFeedBackCode.setText(this.reading.getFeedBackCode());
        if (!(this.reading.getId() == -1 || this.reading.getReading() == 0.0d)) {
            this.mReading.setText(localizedFormat(Double.valueOf(this.reading.getReading()), Locale.getDefault()));
        }
        this.compute.setReading(this.reading);
        this.mKilowatthour.setText(localizedFormat(Double.valueOf(this.compute.getKilowatthour()), Locale.getDefault()));
        this.mTotalBill.setText(localizedFormat(this.compute.currentBill(), Locale.getDefault()));
        this.mRemarks.setText(this.reading.getRemarks());
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!(this.reading == null || this.mReading.getText().toString() == "")) {
            this.dsReading.updateReading(this.reading);
        }
        outState.putLong("id", this.consumer.getId());
    }

    public void finish() {
        if (this.updateList) {
            Intent data = new Intent();
            data.putExtra("id", this.consumer.getId());
            data.putExtra("pos", this.listPosition);
            setResult(1, data);
            //setResult(RESULT_OK, data);
        } else {
           // setResult(0);
           setResult(RESULT_CANCELED);          
        }     
      
        super.finish();        
    }

    public void exitReading(){
    	setResult(RESULT_CANCELED);  
    	
    	super.finish(); 
    }
    protected String localizedFormat(Double value, Locale loc) {
        DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(loc);
        df.applyPattern("###,###.00");
        return df.format(value);
    }

    protected void assignToReading() {
        Time readingDate = new Time();
        readingDate.setToNow();
        this.reading.setFeedBackCode(this.reading.getFeedBackCode());
        this.reading.setRemarks(this.mRemarks.getText().toString());
        this.reading.setTransactionDate(readingDate.toMillis(true));
        if (!this.mReading.getText().toString().trim().equals("")) {
            this.reading.setReading(Double.parseDouble(this.mReading.getText().toString().replaceAll(",", "")));
            this.compute.setReading(this.reading);
            this.reading.setFeedBackCode(this.mFeedBackCode.getText().toString());
            this.reading.setKilowatthour(this.compute.getKilowatthour());
            this.reading.setTotalbill(this.compute.currentBill().doubleValue());
            this.reading.setSeniorCitizenDiscount(this.compute.seniorCitizenDiscRate().doubleValue());
            this.mKilowatthour.setText(this.kilowattFormatter.format(this.compute.getKilowatthour()));
            this.mTotalBill.setText(this.amountFormatter.format(this.compute.currentBill()));
        }
        this.reading.setIsAM(Boolean.valueOf(readingDate.format("%p").equals("AM")));
        this.reading.setReadingDate(readingDate.format("%m%d"));
    }

    public void onClick(View v) {
        boolean z = false;
        if (v.getId() == R.id.brConfirm || v.getId() == R.id.brConfirmAndPrint) {
            this.updateList = false;
            assignToReading();
            if (this.activityMode == INSERT_ACTIVITY || this.activityMode == UPDATE_ACTIVITY) {
                z = true;
            }
            this.updateList = z;
            updateReading();
        }
        finishActivity(v);
    }

    protected void updateReading() {
        switch (this.activityMode) {
            case INSERT_ACTIVITY /*10*/:
                this.dsReading.createReading(this.reading);
            case UPDATE_ACTIVITY /*20*/:
                this.dsReading.updateReading(this.reading);
            default:
        }
    }

    protected void finishActivity(View v) {
        finish();
    }

    protected AlertDialog setAlertdialogBox(int mode) {
        Builder adb = new Builder(this);
        switch (mode) {
            case SelectionMode.MODE_CREATE /*0*/:
                adb.setTitle("bluetooth not yet configured");
                adb.setMessage("Could not proceed to printing");
                break;
            case SelectionMode.MODE_OPEN /*1*/:
                adb.setTitle("Bluetooth is still printing");
                adb.setMessage("Wait till i'm done");
                break;
        }
        adb.setCancelable(false);
        adb.setPositiveButton("OK", null);
        return adb.create();
    }

    protected void printSOA(List<String> soaDetail) {
        if (SplashScreen.btPrinter.print(soaDetail)) {
            finish();
        }
    }

}
