package com.laneco.readandbill;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.generic.readandbill.R;
import com.generic.readandbill.database.FieldFindingDataSource;
import com.generic.readandbill.database.FieldFindings;
import com.laneco.readandbill.database.ComputeCharges;
import com.laneco.readandbill.database.Consumer;
import com.laneco.readandbill.database.ConsumerDataSource;
import com.laneco.readandbill.database.Reading;
import com.laneco.readandbill.database.ReadingDataSource;
import com.laneco.readandbill.database.UserProfileDataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyReadingEntry extends com.generic.readandbill.MyReadingEntry
		implements OnFocusChangeListener {
	private ComputeCharges compute;
	private ConsumerDataSource dsConsumer;
	private ReadingDataSource dsReading;
	protected FieldFindingDataSource dsfieldfinding;
	protected EditText ffCode;
	protected List<FieldFindings> fieldFindings;
	private ComputeCharges lcompute;
	private Consumer lconsumer;
	private Reading lreading;
	private TextView mDemandMult;
	private EditText mDemandReading;
	protected Spinner mFieldFinding;
	private TextView mKilowattUsed;
	private List<Consumer> route;
	protected int routePosition;
	private ConsumerArrayAdapter consmrArrAdapter;

	/* renamed from: com.laneco.readandbill.MyReadingEntry.1 */
	class C00351 implements OnFocusChangeListener {
		C00351() {
		}

		public void onFocusChange(View v, boolean hasFocus) {
			if (!hasFocus
					&& !MyReadingEntry.this.mDemandReading.getText().toString()
							.trim().equals("")) {
				MyReadingEntry.this.assignToReading();
			} else if (hasFocus) {
				MyReadingEntry.this.mDemandReading.selectAll();
			}
		}
	}

	protected void onCreate(Bundle savedInstanceState) {
		this.dsConsumer = new ConsumerDataSource(this);
		this.dsfieldfinding = new FieldFindingDataSource();
		this.dsReading = new ReadingDataSource(this);
		this.route = this.dsConsumer.getAllLanecoConsumer();
		super.onCreate(savedInstanceState);
	}

	protected void onResume() {
		super.onResume();
		for (int i = 0; i <= this.route.size() - 1; i++) {
			if (this.consumer.getId() == ((Consumer) this.route.get(i)).getId()) {
				this.routePosition = i;
				return;
			}
		}
	}

	protected void initControls() {
		TextView mTransLoss = (TextView) findViewById(R.id.tvreTransLoss);
		TextView mCMkilowatthour = (TextView) findViewById(R.id.tvreChangeMeter);
		this.mDemandReading = (EditText) findViewById(R.id.etreDemandReading);
		this.mKilowattUsed = (TextView) findViewById(R.id.tvREKilowatthUsed);
		this.mDemandMult = (TextView) findViewById(R.id.tvreDemandMult);
		this.mFieldFinding = (Spinner) findViewById(R.id.spnFieldFindings);
		if (!(this.mFeedBackCode == null || this.mFeedBackCode.isFocused())) {
			this.mFeedBackCode.requestFocus();
		}
		this.consumer = this.lconsumer;
		this.fieldFindings = this.dsfieldfinding.getAllFieldFindings();
		ArrayAdapter<String> adapter = new ArrayAdapter(this, 17367049,
				setList());
		adapter.setDropDownViewResource(17367049);
		this.mFieldFinding.setAdapter(adapter);
		this.mFieldFinding.setSelection(0);
		if (this.lconsumer == null) {
			this.lconsumer = this.dsConsumer.getConsumer(Long
					.valueOf(this.extras.getLong("id")));
		}
		this.consumer = this.lconsumer;
		this.lreading = this.dsReading.getReading(this.consumer.getId(), 20);
		if (this.lreading.getId() == -1) {
			this.lreading.setidConsumer(this.consumer.getId());
			this.lreading.setDemand(this.lconsumer.getDemand());
			this.lreading.setRoute(SplashScreen.up.getRoute());
		} else if (!(this.lreading.getFieldFinding() == 0 || this.lreading
				.getFieldFinding() == -1)) {
			String fieldFinding = this.dsfieldfinding
					.getDescription(this.dsfieldfinding.getCode(this.lreading
							.getFieldFinding()));
			for (int i = 0; i < this.fieldFindings.size(); i++) {
				if (((String) this.mFieldFinding.getItemAtPosition(i))
						.equalsIgnoreCase(fieldFinding)) {
					this.mFieldFinding.setSelection(i);
					break;
				}
			}
		}
		this.reading = this.lreading;
		this.lreading.setRoute(new UserProfileDataSource(this).getUserProfile()
				.getRoute());
		this.lcompute = new ComputeCharges(this, this.lconsumer);
		this.compute = this.lcompute;
		this.lcompute.setReading(this.lreading);
		this.mDemandReading.setOnFocusChangeListener(new C00351());
		mTransLoss.setText(String.valueOf(this.lconsumer.getTransLoss()));
		mCMkilowatthour.setText(String.valueOf(this.lconsumer
				.getChangeMeterKilowatthour()));
		this.mDemandMult.setText(String.valueOf(this.lconsumer
				.getDemandMultiplier(true)));
		this.mDemandReading.setText(String.valueOf(this.lcompute
				.getKilowattUsed()));
		super.initControls();
		if (this.lreading.getFeedBackCode().equals("A")) {
			this.mReading.setText(localizedFormat(
					Double.valueOf(this.lreading.getKilowatthour()),
					Locale.getDefault()));
		} else {
			this.mReading.setText(localizedFormat(
					Double.valueOf(this.lreading.getReading()),
					Locale.getDefault()));
		}
	}

	protected void assignUI() {
		this.reading = this.lreading;
		super.assignUI();
		this.compute.setReading(this.lreading);
		this.lcompute.setReading(this.lreading);
		if (this.lreading.getFeedBackCode().equals("A")) {
			this.mReading.setText(localizedFormat(
					Double.valueOf(this.lreading.getKilowatthour()),
					Locale.getDefault()));
		} else {
			this.mReading.setText(localizedFormat(
					Double.valueOf(this.lreading.getReading()),
					Locale.getDefault()));
		}
		this.mDemandReading
				.setText(localizedFormat(
						Double.valueOf(this.lreading.getDemand()),
						Locale.getDefault()));
		this.mKilowatthour.setText(localizedFormat(
				Double.valueOf(this.lcompute.getKilowatthour()),
				Locale.getDefault()));
		this.mKilowattUsed.setText(localizedFormat(
				Double.valueOf(this.lcompute.getKilowattUsed()),
				Locale.getDefault()));
		this.mTotalBill.setText(localizedFormat(this.lcompute.currentBill(),
				Locale.getDefault()));
	}

	AlertDialog setFluctuate(boolean isHigher) {
		Builder adb = new Builder(this);
		String partMsg = "30%";
		adb.setTitle("Reading Fluctuates");
		if (isHigher) {
			adb.setMessage("Higher " + partMsg + " more from average");
		} else {
			adb.setMessage("Lesser " + partMsg + " less from average");
		}
		adb.setCancelable(false);
		adb.setPositiveButton("OK", null);
		return adb.create();
	}

	public void onClick(View v) {
		if (v.getId() == R.id.brConfirmAndPrint || v.getId() == R.id.brConfirm) {
			if (this.mReading.getText().toString().trim().equals("")
					&& this.mRemarks.getText().toString().trim().equals("")
					&& (this.lreading.getFieldFinding() == -1 || this.lreading
							.getFieldFinding() == 0)) {
				this.routePosition++;
				this.lconsumer = (Consumer) this.route.get(this.routePosition);
				this.consumer = this.lconsumer;
				initControls();
			} else {
				super.onClick(v);
			}
		}
		if (v.getId() == R.id.brCancel) {				
						
			finish();
			
		}
	}

	protected void finishActivity(View v) {
		if (v.getId() == R.id.brConfirmAndPrint) {
			AlertDialog ad = new Builder(this).setTitle("Could not print SOA")
					.setMessage("Can't print a negative SOA")
					.setPositiveButton("OK", null).create();

			

				if (this.lcompute.currentBill().doubleValue() > 0.0d) {
					
					try {
					printSOA(new StatementGenerator(this, this.lconsumer,
							this.lreading).generateSOA());
					} catch (Exception e) {
						Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
								.show();
					}
					
					if (this.routePosition == this.route.size() - 1) {
						setResult(-1);
						finish();
						return;
					}
					this.routePosition++;
					this.lconsumer = (Consumer) this.route
							.get(this.routePosition);
					this.consumer = this.lconsumer;
					initControls();
					return;
				}
				ad.show();
			
		} else if (this.routePosition == this.route.size() - 1) {
			setResult(-1);
			finish();
		} else {
			this.routePosition++;
			this.lconsumer = (Consumer) this.route.get(this.routePosition);
			this.consumer = this.lconsumer;
			initControls();
		}
	}

	protected void updateReading() {
		this.dsReading.updateReadingCancelled(this.lreading.getIdConsumer());
		this.lreading.setCancelled(0);
		this.dsReading.createReading(this.lreading);
	}

	protected void assignToReading() {
		super.assignToReading();
		Time readingDate = new Time();
		readingDate.setToNow();
		this.lreading = (Reading) this.reading;
		this.lreading.setFieldFinding((long) this.mFieldFinding
				.getSelectedItemPosition());
		this.lreading.setIsAM(Boolean.valueOf(readingDate.format("%p").equals(
				"AM")));
		if (this.mDemandReading.getText().toString().equals("")) {
			this.reading.setDemand(0.0d);
		} else {
			this.lreading.setDemand(Double.parseDouble(this.mDemandReading
					.getText().toString()));
		}
		this.lcompute.setReading(this.lreading);
		this.mKilowattUsed.setText(localizedFormat(
				Double.valueOf(this.lcompute.getKilowattUsed()),
				Locale.getDefault()));
		this.mKilowatthour.setText(localizedFormat(
				Double.valueOf(this.lcompute.getKilowatthour()),
				Locale.getDefault()));
		this.mTotalBill.setText(localizedFormat(this.lcompute.currentBill(),
				Locale.getDefault()));
		Time myTime = new Time();
		myTime.set(this.reading.getTransactionDate());
		this.mKilowattUsed
				.setText(localizedFormat(
						Double.valueOf(this.lreading.getDemand()),
						Locale.getDefault()));
		if (this.activityMode == 10) {
			this.lreading.setSoaPrefix(myTime.format("%m%d%y"));
			this.lreading.setSoaNumber(this.dsReading
					.getMaxSOANumber(this.lreading.getSoaPrefix()));
		}
		this.lreading = (Reading) this.reading;
		this.lreading.setKilowatthour(this.lcompute.getKilowatthour());
		this.lreading.setTotalbill(this.lcompute.currentBill().doubleValue());
		AlertDialog ad;
		if (this.lcompute.getKilowatthour() > this.lconsumer.getAveKwh()
				+ (this.lconsumer.getAveKwh() * 0.3d)) {
			ad = setFluctuate(true);
		} else if (this.lcompute.getKilowatthour() < this.lconsumer.getAveKwh()
				- (this.lconsumer.getAveKwh() * 0.3d)) {
			ad = setFluctuate(false);
		}
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

	public void onFocusChange(View v, boolean hasFocus) {
		if (v.getId() == R.id.etFFEFieldFindingsCode) {
			if (!hasFocus
					&& !this.ffCode.getText().toString().trim().equals("")) {
				for (int i = 0; i < this.mFieldFinding.getCount(); i++) {
					if (((String) this.mFieldFinding.getItemAtPosition(i))
							.equals(this.dsfieldfinding
									.getDescription(this.ffCode.getText()
											.toString()))) {
						this.mFieldFinding.setSelection(i);
					}
				}
			}
		} else if (v.getId() == R.id.spnFieldFindings && !hasFocus) {
			this.ffCode.setText(this.dsfieldfinding.getCode(this.mFieldFinding
					.getItemAtPosition(
							this.mFieldFinding.getSelectedItemPosition())
					.toString()));
		}
	}
}
