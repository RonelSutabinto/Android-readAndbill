package com.androidapp.mytools.bluetooth;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.UUID;

public class MyPrinter implements Runnable {
    protected static final String TAG = "Bluetooth Printing Class";
    protected boolean alwaysOn;
    protected UUID applicationUUID;
    protected volatile BluetoothAdapter bta;
    protected volatile BluetoothDevice btd;
    protected volatile BluetoothSocket bts;
    protected Activity context;
    protected String defaultMacAddress;
    protected String deviceName;
    protected BitSet dots;
    protected Bitmap logo;
    protected int logoHeight;
    protected int logoWidth;
    protected volatile List<List<String>> printQue;
    protected Thread printThread;

    /* renamed from: com.androidapp.mytools.bluetooth.MyPrinter.1 */
    class C00031 extends Thread {
        C00031() {
        }

        public void run() {
            while (MyPrinter.this.bta.getState() != 12) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            MyPrinter.this.btd = MyPrinter.this.bta.getRemoteDevice(MyPrinter.this.defaultMacAddress);
        }
    }

    /* renamed from: com.androidapp.mytools.bluetooth.MyPrinter.2 */
    class C00042 extends Thread {
        C00042() {
        }

        public void run() {
            MyPrinter.this.bta = BluetoothAdapter.getDefaultAdapter();
            while (MyPrinter.this.bta.getState() != 10) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            MyPrinter.this.bta.enable();
        }
    }

    /* renamed from: com.androidapp.mytools.bluetooth.MyPrinter.3 */
    class C00053 implements Runnable {
        C00053() {
        }

        public void run() {
            if (!MyPrinter.this.alwaysOn) {
                while (MyPrinter.this.bta.getState() == 11) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (MyPrinter.this.bta.isEnabled()) {
                    MyPrinter.this.bta.disable();
                }
                MyPrinter.this.bta = null;
                if (MyPrinter.this.bts != null) {
                    try {
                        MyPrinter.this.bts.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    /* renamed from: com.androidapp.mytools.bluetooth.MyPrinter.4 */
    class C00064 implements Runnable {
        C00064() {
        }

        public void run() {
            Toast.makeText(MyPrinter.this.context, "Please turnOn bluetooth printer..", 1).show();
        }
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setMacAddress(String macAddress) {
        this.defaultMacAddress = macAddress;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public MyPrinter(Activity context) {
        this.alwaysOn = false;
        this.applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        this.context = context;
        if (this.bta == null) {
            this.bta = BluetoothAdapter.getDefaultAdapter();
            if (this.bta == null) {
                Builder adb = new Builder(context);
                adb.setTitle("No bluetooth hardware found in this unit");
                adb.setMessage("Could not proceed to printing");
                adb.setCancelable(false);
                adb.setPositiveButton("OK", null);
                adb.create().show();
            }
        }
        this.printQue = new ArrayList();
    }

    private void setPrintThread() {
        if (this.printThread == null) {
            this.printThread = new Thread(this);
        } else if (this.printThread.getState() == State.TERMINATED) {
            this.printThread = new Thread(this);
        } else if (this.printThread.getState() == State.RUNNABLE) {
            this.printThread.run();
        }
        if (this.printThread.getState() == State.NEW) {
            this.printThread.start();
        }
    }

    public void setAlwaysOn(boolean alwaysOn) {
        this.alwaysOn = alwaysOn;
        if (alwaysOn) {
            initBluetooth();
            connectSocket();
        }
    }

    public BluetoothDevice getBtd() {
        return this.btd;
    }

    public void setBluetoothDevice() {
        if (this.btd == null) {
            new C00031().start();
        }
    }

    protected void closeSocket(BluetoothSocket bs) {
        if (!this.alwaysOn) {
            if (bs != null) {
                try {
                    bs.close();
                } catch (IOException e) {
                    Log.d(TAG, "Could not close socket!");
                    return;
                }
            }
            Log.d(TAG, "Socket closed");
        }
    }

   protected Boolean connectSocket() {
        try {
            while (btd == null)
                Thread.sleep(500);
            this.bta.cancelDiscovery();
            this.bts = btd.createRfcommSocketToServiceRecord(this.applicationUUID);
            this.bts.connect();
            return true;
        } catch (IOException e) {
            Log.e(TAG + ": Socket Connection", e.getMessage());
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

   /* 
    protected Boolean connectSocket() { //Origin
        while (this.btd == null) {
            try {
                Thread.sleep(500);
            } catch (IOException e) {
                Log.e("Bluetooth Printing Class: Socket Connection", e.getMessage());
                return Boolean.valueOf(false);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
                return Boolean.valueOf(false);
            }
            
        }
        this.bta.cancelDiscovery();
        this.bts = this.btd.createRfcommSocketToServiceRecord(this.applicationUUID);
        this.bts.connect();
        return Boolean.valueOf(true);
    }
*/
    public void appendToQue(List<String> forPrinting) {
        this.printQue.add(forPrinting);
    }

    public boolean print(List<String> forPrinting) {
        setPrintThread();
        appendToQue(forPrinting);
        return false;
    }

    public BluetoothAdapter getBta() {
        return this.bta;
    }

    public void turnOnBT() {
        if (this.bta == null) {
            this.bta = BluetoothAdapter.getDefaultAdapter();
        }
        if (this.bta.getState() == 13) {
            new C00042().start();
        } else if (this.bta.getState() == 10) {
            this.bta.enable();
        }
    }

    public void turnOffBT() {
        new Thread(new C00053()).start();
    }

    public void run() {
        while (true) {
            if (this.printQue.isEmpty()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                if (this.bta == null) {
                    this.bta = BluetoothAdapter.getDefaultAdapter();
                }
                if (!this.alwaysOn && (this.bta.getState() == 10 || this.bta.getState() == 13)) {
                    initBluetooth();
                }
                while (!this.bta.isEnabled()) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
                while (this.btd == null) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e22) {
                        e22.printStackTrace();
                    }
                }
                if (!this.alwaysOn) {
                    connectSocket();
                }
                Integer iterator = Integer.valueOf(1);
                try {
                    OutputStream os = this.bts.getOutputStream();
                    while (!this.printQue.isEmpty()) {
                       // for (String string : (List) this.printQue.get(0)) { //Origin
                    	for (String string : printQue.get(0)) {	
                            os.write(string.getBytes());
                            iterator = Integer.valueOf(iterator.intValue() + 1);
                            if (iterator.intValue() >= 5) {
                                Thread.sleep(500);
                                iterator = Integer.valueOf(0);
                            }
                        }
                        this.printQue.remove(0);
                    }
                    if (this.printQue.size() == 0) {
                        closeSocket(this.bts);
                        if (!this.alwaysOn) {
                            turnOffBT();
                        }
                    }
                } catch (IOException e3) {
                    Log.e("Bluetooth Printing Class: Printing", e3.getMessage());
                    this.printQue.clear();
                    this.context.runOnUiThread(new C00064());
                    if (this.printQue.size() == 0) {
                        closeSocket(this.bts);
                        if (!this.alwaysOn) {
                            turnOffBT();
                        }
                    }
                } catch (InterruptedException e222) {
                    Log.e("Bluetooth Printing Class: Interruption", e222.getMessage());
                    if (this.printQue.size() == 0) {
                        closeSocket(this.bts);
                        if (!this.alwaysOn) {
                            turnOffBT();
                        }
                    }
                } catch (Throwable th) {
                    if (this.printQue.size() == 0) {
                        closeSocket(this.bts);
                        if (!this.alwaysOn) {
                            turnOffBT();
                        }
                    }
                }
            }
        }
    }

    protected void initBluetooth() {
        turnOnBT();
        setBluetoothDevice();
    }

    public boolean isTurnedOn() {
        if (this.bta.getState() == 12) {
            return true;
        }
        return false;
    }
}
