package com.generic.readandbill;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import com.androidapp.mytools.AppPreferenceActivity;
import com.androidapp.mytools.bluetooth.BluetoothSharedPrefs;
import com.androidapp.mytools.bluetooth.MyPrinter;
import com.androidapp.mytools.bluetooth.PrinterControls;
import com.androidapp.mytools.objectmanager.StringManager;
import com.generic.readandbill.database.ConsumerDataSource;
import com.generic.readandbill.database.FieldFindingDataSource;
import com.generic.readandbill.database.RateDataSource;
import com.generic.readandbill.database.Reading;
import com.generic.readandbill.database.ReadingDataSource;
import com.generic.readandbill.database.UserProfileDataSource; 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class SplashScreen extends Activity{
	 protected static final int CONSUMER = 10;
	    protected static final int NOTHING = 0;
	    protected static final int PREFCODE = 30;
	    protected static final String TAG = "Splash Screen";
	    protected static final int USERPROFILE = 20;
	    public static MyPrinter btPrinter;
	    protected String btrAddress;
	    private Activity context;
	    protected ConsumerDataSource dsConsumer;
	    protected FieldFindingDataSource dsFF;
	    protected RateDataSource dsRates;
	    protected ReadingDataSource dsReading;
	    protected UserProfileDataSource dsUserProfile;
	    protected String filePath;
	    protected int mode;
	    protected ImageView splashScreenImage;
        
	    // renamed from: com.generic.readandbill.SplashScreen.1 
	    class C00191 extends Thread {
	        final  String val$path;
	        final  List val$result;

	        // renamed from: com.generic.readandbill.SplashScreen.1.1 
	        class C00181 implements Runnable {
	            C00181() {
	            }

	            public void run() {
	                SplashScreen.this.doneDialog("Result").show();
	            }
	        }

	        C00191(String str, List list) {
	            this.val$path = str;
	            this.val$result = list;
	        }

	        public void run() {
	            try {
	                FileWriter fw = new FileWriter(new File(this.val$path));
	                for (int i = SplashScreen.NOTHING; i < this.val$result.size() - 1; i++) {
	                    fw.append((CharSequence) this.val$result.get(i));
	                    if (i % 4 == 0) {
	                        Thread.sleep(500);
	                    }
	                }
	                fw.flush();
	                fw.close();
	                SplashScreen.this.context.runOnUiThread(new C00181());
	            } catch (IOException e) {
	                e.printStackTrace();
	            } catch (InterruptedException e2) {
	                e2.printStackTrace();
	            }
	        }
	    }
       
	    public SplashScreen() {
	        this.btrAddress = null;
	        this.mode = NOTHING;
	        this.filePath = Environment.getExternalStorageDirectory() + "/ReadAndBill/InFiles/Upload.txt";
	    }

	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        this.context = this;
	        setContentView(R.layout.activity_splash_screen);
	        createDirIfNotExists(Environment.getExternalStorageDirectory() + "/ReadAndBill/OutFiles/");
	        createDirIfNotExists(Environment.getExternalStorageDirectory() + "/ReadAndBill/InFiles/");
	        btPrinter = new MyPrinter(this);
	        if (BluetoothSharedPrefs.getMacAddress(this) != null) {
	            btPrinter.setMacAddress(BluetoothSharedPrefs.getMacAddress(this));
	            btPrinter.setDeviceName(BluetoothSharedPrefs.getDeviceName(this));
	            btPrinter.setAlwaysOn(BluetoothSharedPrefs.getBluetoothAlwaysOn(this));
	            PrinterControls.btPrinter = btPrinter;
	        }
	        this.dsConsumer = new ConsumerDataSource(this);
	        this.dsRates = new RateDataSource(this);
	        this.dsReading = new ReadingDataSource(this);
	        this.dsUserProfile = new UserProfileDataSource(this);
	    }

	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if (requestCode == PREFCODE && resultCode == -1) {
	            btPrinter.setMacAddress(BluetoothSharedPrefs.getMacAddress(this));
	            btPrinter.setDeviceName(BluetoothSharedPrefs.getDeviceName(this));
	            btPrinter.setAlwaysOn(BluetoothSharedPrefs.getBluetoothAlwaysOn(this));
	            PrinterControls.btPrinter = btPrinter;
	        }
	        btPrinter.turnOffBT();
	    }

	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.menu_splash, menu);
	        return true;
	    }

	    public boolean onOptionsItemSelected(MenuItem item) {
	        if (item.getItemId() == R.id.iConsumerList) {
	            startActivity(new Intent(this, MyConsumerList.class));
	            return true;
	        } else if (item.getItemId() == R.id.iProcessTextFile) {
	            return true;
	        } else {
	            if (item.getItemId() == R.id.iGenerateResult) {
	                generateResultFile(initializeResultFields(), Environment.getExternalStorageDirectory() + "/ReadAndBill/OutFiles/result.txt");
	                return true;
	            } else if (item.getItemId() == R.id.iSummaryList) {
	                startActivity(new Intent(this, SummaryList.class));
	                return true;
	            } else if (item.getItemId() != R.id.iBluetoothSetting) {
	                return false;
	            } else {
	                btPrinter.turnOnBT();
	                while (!btPrinter.isTurnedOn()) {
	                    try {
	                        Thread.sleep(500);
	                    } catch (InterruptedException e) {
	                        e.printStackTrace();
	                    }
	                }
	                startActivityForResult(new Intent(this, AppPreferenceActivity.class), PREFCODE);
	                return true;
	            }
	        }
	    }

	    public void readDataList(List<String> dataList) {
	        File myFile = new File(Environment.getExternalStorageDirectory() + "/ReadAndBill/InFiles/Upload.txt");
	        for (int i = NOTHING; i < dataList.size(); i++) {
	            if (i == 0) {
	                this.mode = USERPROFILE;
	            } else if (((String) dataList.get(i)).equals("END TRANS")) {
	                this.mode = CONSUMER;
	            } else {
	                createData((String) dataList.get(i));
	            }
	        }
	        doneDialog("Processing Text File").show();
	        if (myFile.exists()) {
	            myFile.delete();
	        }
	    }

	    private void createData(String dataValues) {
	        switch (this.mode) {
	        }
	    }

	    private boolean createDirIfNotExists(String path) {
	        File file = new File(path);
	        if (file.exists() || file.mkdirs()) {
	            return true;
	        }
	        Log.e("Read And Bill Log :: ", "Problem creating folder or SD Card not present");
	        return false;
	    }

	    public List<String> retrieveStringFromFile(String path) {
	        List<String> textData = new ArrayList();
	        try {
	            BufferedReader br = new BufferedReader(new FileReader(path));
	            while (true) {
	                String line = br.readLine();
	                if (line == null) {
	                    break;
	                }
	                textData.add(line);
	            }
	            br.close();
	        } catch (IOException e) {
	            AlertDialog ad = new Builder(this).create();
	            ad.setTitle("File not found!");
	            ad.show();
	        }
	        return textData;
	    }

	    protected AlertDialog doneDialog(String dataKind) {
	        return new Builder(this).setTitle("Done processing").setMessage(dataKind + " please tap OK to resume").setPositiveButton("OK", null).setCancelable(false).create();
	    }

	    protected AlertDialog nothingDialog() {
	        return new Builder(this).setTitle("Nothing to process").setMessage("Tap OK to continue").setPositiveButton("OK", null).setCancelable(false).create();
	    }

	    protected List<String> initializeResultFields() {
	        List<String> result = new ArrayList();
	        List<Reading> readings = this.dsReading.getAllReadings();
	        Time myReadingDate = new Time();
	        String ffCode = "0";
	        if (readings.size() > 0) {
	            for (Reading reading : readings) {
	                Integer isPrinted;
	                String ffDescription;
	                myReadingDate.set(reading.getTransactionDate());
	                if (reading.getIsPrinted().booleanValue()) {
	                    isPrinted = Integer.valueOf(1);
	                } else {
	                    isPrinted = Integer.valueOf(NOTHING);
	                }
	                ffCode = "0";
	                if (reading.getFieldFinding() != -1) {
	                    ffCode = this.dsFF.getCode(reading.getFieldFinding());
	                    ffDescription = this.dsFF.getDescription(ffCode);
	                } else {
	                    ffDescription = reading.getRemarks();
	                }
	                result.add(StringManager.leftJustify(String.valueOf(reading.getIdConsumer()), 8) + StringManager.leftJustify("", 16) + StringManager.rightJustify(String.valueOf(reading.getReading()), 8) + StringManager.rightJustify(String.valueOf(reading.getDemand()), 4) + StringManager.leftJustify(isPrinted.toString(), 1) + StringManager.leftJustify(reading.getFeedBackCode(), 15) + StringManager.leftJustify(ffCode, 2) + StringManager.leftJustify(ffDescription, 15) + StringManager.leftJustify(myReadingDate.format("%m%d%H%M%S"), CONSUMER) + " " + StringManager.leftJustify(String.valueOf(reading.getKilowatthour()), 8) + '\r' + "" + '\n');
	            }
	        }
	        return result;
	    }

	    protected boolean generateResultFile(List<String> result, String path) {
	        if (result.size() != 0) {
	            new C00191(path, result).start();
	            return true;
	        }
	        nothingDialog().show();
	        return false;
	    }
 
	    protected void initializeDatabase() {
	        this.dsConsumer.truncate();
	        this.dsRates.truncate();
	        this.dsReading.truncate();
	        this.dsUserProfile.truncate();
	    }
}

