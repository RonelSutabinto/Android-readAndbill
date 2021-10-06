package com.laneco.readandbill;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.generic.readandbill.R;
import com.androidapp.mytools.objectmanager.ProgressDialogMaker;
import com.androidapp.mytools.objectmanager.StringManager;
import com.generic.readandbill.database.FieldFindingDataSource;
import com.lamerman.FileDialog;
import com.laneco.readandbill.database.Consumer;
import com.laneco.readandbill.database.ConsumerDataSource;
import com.laneco.readandbill.database.RateDataSource;
import com.laneco.readandbill.database.Rates;
import com.laneco.readandbill.database.Reading;
import com.laneco.readandbill.database.ReadingDataSource;
import com.laneco.readandbill.database.UserProfile;

import com.laneco.readandbill.database.UserProfileDataSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
public class SplashScreen  extends com.generic.readandbill.SplashScreen{
	 private static final int REQUEST_LOAD = 10;
	    private static final int REQUEST_SAVE = 20;
	    public static UserProfile up = null;
	    public static final String version = "2.2.0.0";
	    protected ProgressDialog barProgressDialog;
	    private ConsumerDataSource dsConsumer;
	    private RateDataSource dsRates;
	    private ReadingDataSource dsReading;
	    private UserProfileDataSource dsUserProfile;

	    // renamed from: com.laneco.readandbill.SplashScreen.1 
	    class C00381 implements Runnable {
	        final  List val$rawData;

	        // renamed from: com.laneco.readandbill.SplashScreen.1.1
	        class C00371 implements Runnable {
	            C00371() {
	            }

	            public void run() {
	                new Builder(SplashScreen.this).setTitle("Done").setMessage("Processing Text File Complete!").setPositiveButton("Done", null).create().show();
	                SplashScreen.this.setRequestedOrientation(1);
	            }
	        }

	        C00381(List list) {
	            this.val$rawData = list;
	        }

	        public void run() {
	            for (int i = 0; i <= this.val$rawData.size() - 1; i++) {
	                if (i == 0) {
	                    SplashScreen.this.dsUserProfile.createUserProfile(SplashScreen.this.listToUserProfile((String) this.val$rawData.get(i)));
	                } else {
	                    SplashScreen.this.dsConsumer.createConsumer(SplashScreen.this.listToConsumer((String) this.val$rawData.get(i)));
	                }
	                ProgressDialogMaker.progressHandler.post(ProgressDialogMaker.increaseProgress(SplashScreen.this.barProgressDialog));
	            }
	            SplashScreen.this.barProgressDialog.dismiss();
	            SplashScreen.this.runOnUiThread(new C00371());
	        }
	    }

	    // renamed from: com.laneco.readandbill.SplashScreen.2
	    class C00402 extends Thread {
	        final  String val$path;
	        final  List val$result;

	        // renamed from: com.laneco.readandbill.SplashScreen.2.1 
	        class C00391 implements Runnable {
	            C00391() {
	            }

	            public void run() {
	                SplashScreen.this.doneDialog("Result").show();
	                SplashScreen.this.setRequestedOrientation(1);
	            }
	        }

	        C00402(String str, List list) {
	            this.val$path = str;
	            this.val$result = list;
	        } 

	        public void run() {
	            try {
	                FileWriter fw = new FileWriter(new File(this.val$path));
	                for (int i = 0; i <= this.val$result.size() - 1; i++) {
	                    fw.append((CharSequence) this.val$result.get(i));
	                    if (i % 4 == 0) {
	                        Thread.sleep(500);
	                    }
	                }
	                ProgressDialogMaker.progressHandler.post(ProgressDialogMaker.increaseProgress(SplashScreen.this.barProgressDialog));
	                fw.flush();
	                fw.close();
	                SplashScreen.this.barProgressDialog.dismiss();
	                SplashScreen.this.runOnUiThread(new C00391());
	            } catch (IOException e) {
	                e.printStackTrace();
	            } catch (InterruptedException e2) {
	                e2.printStackTrace();
	            }
	        }
	    }

	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        this.dsConsumer = new ConsumerDataSource(this);
	        this.dsReading = new ReadingDataSource(this);
	        this.dsRates = new RateDataSource(this);
	        this.dsUserProfile = new UserProfileDataSource(this);
	        up = new UserProfile();  
	        
	        //try
	        //{
	        	up = this.dsUserProfile.getUserProfile();
	        //}catch (Exception e) {
				// TODO: handle exception
	        	//Toast.makeText(this,  e.getMessage(), Toast.LENGTH_LONG).show();
			//}	 	        	
	       
	        setTitle("LANECO Read And Bill 1.0.3.8");
	        this.splashScreenImage = (ImageView) findViewById(R.id.imageView1);
	        this.splashScreenImage.setImageResource(R.drawable.lanecologo);
	    }

	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if (resultCode != -1) {
	            return;
	        }
	        if (requestCode == REQUEST_LOAD) {
	            retrieveData(data.getStringExtra(FileDialog.RESULT_PATH));
	        } else if (requestCode == REQUEST_SAVE) {
	            UserProfile up = this.dsUserProfile.getUserProfile();
	            Time gadgetTime = new Time();
	            gadgetTime.setToNow();
	            generateResultFile(initializeResultFields(), data.getStringExtra(FileDialog.RESULT_PATH) + "/" + up.getName().replace(",", "") + " " + up.getRoute() + " " + gadgetTime.format("%D-%R").replace("/", "").replace(":", "") + ".prn");
	        } else {
	            super.onActivityResult(requestCode, resultCode, data);
	        }
	    }

	    public boolean onOptionsItemSelected(MenuItem item) {
	        if (item.getItemId() == R.id.iConsumerList) {
	            if (this.dsConsumer.existing()) {
	                startActivity(new Intent(this, MyConsumerList.class));
	                return true;
	            }
	            new Builder(this).setTitle("Database not found!").setMessage("Please process the text file before tapping consumer list").setPositiveButton("Done", null).create().show();
	            return true;
	        } else if (item.getItemId() == R.id.iSummaryList) {
	            startActivity(new Intent(this, SummaryList.class));
	            return true;
	        } else if (item.getItemId() == R.id.iProcessTextFile) {
	        	Intent intent = new Intent(getBaseContext(), FileDialog.class);
	            intent.putExtra(FileDialog.START_PATH, Environment.getExternalStorageDirectory() + "/ReadAndBill");
	            intent.putExtra(FileDialog.SELECTION_MODE, 1);
	            intent.putExtra(FileDialog.CAN_SELECT_DIR, true);
	            intent.putExtra(FileDialog.FORMAT_FILTER, new String[]{"txt"});
	            startActivityForResult(intent, REQUEST_LOAD);
	            return true;
	        } else if (item.getItemId() != R.id.iGenerateResult) {
	            return super.onOptionsItemSelected(item);
	        } else {
	        	Intent intent = new Intent(getBaseContext(), FileDialog.class);
	            intent.putExtra(FileDialog.START_PATH, Environment.getExternalStorageDirectory() + "/ReadAndBill");
	            intent.putExtra(FileDialog.SELECTION_MODE, 1);
	            intent.putExtra(FileDialog.CAN_SELECT_DIR, true);
	            intent.putExtra(FileDialog.FORMAT_FILTER, new String[]{"prn"});
	            startActivityForResult(intent, REQUEST_SAVE);
	            return true;
	        }
	    }

	    protected boolean retrieveData(String path) {
	        if (new File(path).exists()) {
	            initializeDatabase();
	            processRawData(retrieveStringFromFile(path));
	        } else {
	            AlertDialog ad = new Builder(this).create();
	            ad.setTitle("File not found!");
	            ad.show();
	        }
	        return false;
	    }

	    public boolean onCreateOptionsMenu(Menu menu) {
	        boolean result = super.onCreateOptionsMenu(menu);
	        MenuItem newConsumer = menu.findItem(R.id.iNewConList);
	        MenuItem zoneReport = menu.findItem(R.id.iZoneReport);
	        newConsumer.setVisible(false);
	        zoneReport.setChecked(false); 
	        return true;
	    }
       
	    //original code==================== 
	    //=================================
	    private void processRawData(List<String> rawData) {
	        this.barProgressDialog = ProgressDialogMaker.myProgressBar(this, "Processing " + this.dsUserProfile.getUserProfile().getRoute(), "Processing text file please wait..", rawData.size());
	        new Thread(new C00381(rawData)).start();
	    }
	    

	    private Consumer listToConsumer(String rawData) {
	    	 String[] data = StringManager.listTrimmer(rawData.split("~"));
	    	 Consumer consumer = new Consumer();
		        consumer.setAccountNumber(data[0]);
		        consumer.setConnCode(data[1]);
		        consumer.setInitialReading(Double.parseDouble(data[2].trim().replace(",", "")));
		        consumer.setMeterSerial(data[3]);
		        consumer.setRateCode(data[4]);
		        consumer.setName(data[5]);
		        consumer.setAddress(data[6]);
		        consumer.setContracted(data[7].equals("Y"));
		        consumer.setMultiplier(Double.parseDouble(data[8]));
		        consumer.setCoreLoss(Double.parseDouble(data[43]));
		        consumer.setTransformerLostTestResult(Double.parseDouble(data[44]));
		        consumer.setDemandMultiplier(Double.parseDouble(data[49]));
		        consumer.setDemandMin(Double.parseDouble(data[50]));
		        consumer.setDemandMax(Double.parseDouble(data[51]));
		        consumer.setArMats(Double.parseDouble(data[56].replace(",", "")));
		        consumer.setScap(Double.parseDouble(data[57].replace(",", "")));
		        consumer.setRefund(Double.parseDouble(data[58].replace(",", "")));
		        consumer.setHelp(Double.parseDouble(data[59].replace(",", "")));
		        consumer.setPilfer(Double.parseDouble(data[60].replace(",", "")));
		        consumer.setSCSwitch(data[61].equals("T"));
		        consumer.setNumberOfArrears(Integer.parseInt(data[62]));
		        consumer.setArrears(Double.parseDouble(data[63].replace(",", "")));
		        consumer.setAveKwh(Double.parseDouble(data[80].replace(",", "")));
		        consumer.setDateEnergized(data[81]);
		        consumer.setMeterBrand(data[82]);
		        consumer.setTransformerNumber(data[83]);
		        consumer.setKw(Double.parseDouble(data[84].trim()));
		        consumer.setTransformerRental(Double.parseDouble(data[86].trim().replace(",", "")));
		        consumer.setDemandCharge(Double.parseDouble(data[85].trim().replace(",", "")));
		        consumer.setDisco(Double.parseDouble(data[87].trim().replace(",", "")));
		        consumer.setIncentives(Double.parseDouble(data[88].trim().replace(",", "")));
		        consumer.setMaterial(Double.parseDouble(data[89].trim().replace(",", "")));
		        consumer.setEquiptment(Double.parseDouble(data[90].trim().replace(",", "")));
		        consumer.setOthersSurcharge(Double.parseDouble(data[91].trim().replace(",", "")));
		        Rates rate = this.dsRates.getConsumerRate(consumer.getRateCode());
		        if (rate.getId() == -1) {
		            rate.setScSwitch(Boolean.valueOf(consumer.isScSwitch()));
		            rate.setRateCode(consumer.getRateCode());
		            rate.setGenSys(Double.parseDouble(data[9]));
		            rate.setHostComm(Double.parseDouble(data[REQUEST_LOAD]));
		            rate.setIcera(Double.parseDouble(data[11]));
		            rate.setTcDemand(Double.parseDouble(data[12]));
		            rate.setTcSystem(Double.parseDouble(data[13]));
		            rate.setSystemLoss(Double.parseDouble(data[14]));
		            rate.setDcDemand(Double.parseDouble(data[15]));
		            rate.setDcDistribution(Double.parseDouble(data[16]));
		            rate.setScSupplySys(Double.parseDouble(data[17]));
		            rate.setScRetailCust(Double.parseDouble(data[18]));
		            rate.setMcSys(Double.parseDouble(data[19]));
		            rate.setMcRetailCust(Double.parseDouble(data[REQUEST_SAVE]));	            
		            rate.setUcsd(Double.parseDouble(data[23]));
		            rate.setUcme(Double.parseDouble(data[24]));
		            rate.setUcStrandedContractCost(Double.parseDouble(data[25]));
		            rate.setUcec(Double.parseDouble(data[26]));
		            rate.setFeedTariffAllowance(Double.parseDouble(data[27]));
		            rate.setParr(Double.parseDouble(data[29]));
		            rate.setLifeLineSubsidy(Double.parseDouble(data[30]));
		            rate.setSeniorCitizenDiscount(Double.parseDouble(data[32]));
		            rate.setSeniorCitizenSubsidy(Double.parseDouble(data[33]));
		            rate.setPrevYearAdjPowerCost(Double.parseDouble(data[36]));
		            rate.setReinvestmentFundSustCapex(Double.parseDouble(data[37]));
		            rate.setVatGensys(Double.parseDouble(data[64]));
		            rate.setVatPARR(Double.parseDouble(data[65]));
		            rate.setVatIcera(Double.parseDouble(data[66]));
		            rate.setVatTcSystem(Double.parseDouble(data[67]));
		            rate.setVatTcDemand(Double.parseDouble(data[68])); 
		            rate.setVatDcDistribution(Double.parseDouble(data[69]));
		            rate.setVatDcDemand(Double.parseDouble(data[70]));
		            rate.setVatScSupply(Double.parseDouble(data[71]));
		            rate.setVatMcSystem(Double.parseDouble(data[72]));
		            rate.setVatLifelineSubsidy(Double.parseDouble(data[73]));
		            rate.setVatReinvestmentFundSustCapex(Double.parseDouble(data[74]));
		            rate.setVatSeniorCitizen(Double.parseDouble(data[75]));
		            rate.setVatScRetail(Double.parseDouble(data[76]));
		            rate.setVatMcRetail(Double.parseDouble(data[77]));
		            rate.setVatSystemLoss(Double.parseDouble(data[78])); 
		            rate.setVatSystemLossTransmission(Double.parseDouble(data[79]));
		            rate.setUcmeRed(Double.parseDouble(data[92]));//================
		            rate.setRealPropertyTax(Double.parseDouble(data[96]));
		            this.dsRates.createRates(rate);
		        }
		        return consumer;
	    }

	    protected UserProfile listToUserProfile(String dataValues) {
	        String[] data = StringManager.listTrimmer(dataValues.split("~"));
	        UserProfile up = new UserProfile();
	        up.setRoute(data[0]);	        
	        up.setReadingDate(data[1]);
	        up.setInitialReadingDate(data[2]);
	        up.setName(data[3]);
	        return up;
	    }

	    protected void initializeDatabase() {
	        this.dsConsumer.truncate();
	        this.dsRates.truncate();
	        if (!this.dsReading.tableExist()) {
	            this.dsReading.truncate();
	        }
	        this.dsUserProfile.truncate();        
	    }

	    protected List<String> initializeResultFields() {
	        DecimalFormat amtFormater = new DecimalFormat("#####0.0");
	        List<String> result = new ArrayList();
	        if (this.dsFF == null) {
	            this.dsFF = new FieldFindingDataSource(); 
	        }
	        if (this.dsReading.getResultReading().size() > 0) {
	            for (Reading reading : this.dsReading.getResultReading()) {
	                result.add(StringManager.leftJustify(this.dsConsumer.getConsumer(Long.valueOf(reading.getIdConsumer())).getOrigAccountNumber(), 13) + " " + StringManager.leftJustify(amtFormater.format(reading.getReading()), REQUEST_LOAD) + " " + StringManager.leftJustify(amtFormater.format(reading.getDemand()), REQUEST_LOAD) + " " + StringManager.leftJustify(this.dsFF.getDescription(this.dsFF.getCode(reading.getFieldFinding())) + "-" + reading.getRemarks(), 51) + '\r' + "" + '\n');
	            }
	            result.add("TM Start\r\n");
	            for (com.generic.readandbill.database.Reading reading2 : this.dsReading.getAllReadings()) {
	                Time readTime = new Time();
	                readTime.set(reading2.getTransactionDate());
	                result.add(readTime.format("%D %r") + " " + this.dsConsumer.getConsumer(Long.valueOf(reading2.getIdConsumer())).getAccountNumber() + " " + StringManager.rightJustify(amtFormater.format(reading2.getKilowatthour()), REQUEST_LOAD) + " " + StringManager.rightJustify(amtFormater.format(reading2.getDemand()), REQUEST_LOAD) + '\r' + "" + '\n');
	            }
	        }
	        return result;
	    }

	    protected boolean generateResultFile(List<String> result, String path) {
	        if (result.size() != 0) {
	            this.barProgressDialog = ProgressDialogMaker.myProgressBar(this, "Generating " + this.dsUserProfile.getUserProfile().getRoute(), "Generating text file please wait..", result.size());
	            this.barProgressDialog.show();
	            new C00402(path, result).start();
	            return true;
	        }
	        nothingDialog().show();
	        return false;
	    }
}
