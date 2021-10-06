package com.androidapp.mytools.objectmanager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;

public class ProgressDialogMaker {
	 public static Handler progressHandler;

	    /* renamed from: com.androidapp.mytools.objectmanager.ProgressDialogMaker.1 */
	    static class C00071 implements Runnable {
	        final /* synthetic */ ProgressDialog val$myProgressDialog;

	        C00071(ProgressDialog progressDialog) {
	            this.val$myProgressDialog = progressDialog;
	        }

	        public void run() {
	            this.val$myProgressDialog.incrementProgressBy(1);
	        }
	    }

	    public static ProgressDialog myProgressBar(Activity activity, String title, String message, int max) {
	        progressHandler = new Handler();
	        ProgressDialog barProgressBar = new ProgressDialog(activity);
	        barProgressBar.setTitle(title);
	        barProgressBar.setMessage(message);
	        barProgressBar.setProgressStyle(1);
	        barProgressBar.setProgress(0);
	        barProgressBar.setMax(max);
	        barProgressBar.setCancelable(false);
	        activity.setRequestedOrientation(14);
	        return barProgressBar;
	    }

	    public static Runnable increaseProgress(ProgressDialog myProgressDialog) {
	        return new C00071(myProgressDialog);
	    }
}
