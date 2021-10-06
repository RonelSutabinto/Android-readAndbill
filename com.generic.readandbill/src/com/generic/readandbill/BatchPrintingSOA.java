package com.generic.readandbill;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.generic.readandbill.database.ConsumerDataSource;
public class BatchPrintingSOA extends Activity implements OnClickListener{
	 protected Button btnContinous;
	    protected Button btnPause;
	    protected Context context;
	    protected ConsumerDataSource dsConsumer;
	    protected EditText etAccountFrom;
	    protected EditText etAccountTo;

	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_batch_printing);
	        this.dsConsumer = new ConsumerDataSource(this);
	        this.etAccountFrom = (EditText) findViewById(R.id.etbpAccountFrom);
	        this.etAccountTo = (EditText) findViewById(R.id.etbpAccountTo);
	        this.btnContinous = (Button) findViewById(R.id.btnbpContinous);
	        this.btnPause = (Button) findViewById(R.id.btnbpPause);
	        this.btnContinous.setOnClickListener(this);
	        this.btnPause.setOnClickListener(this);
	        this.context = this;
	        uiInit();
	    }

	    public void onClick(View v) {
	        if (v.getId() == R.id.btnbpContinous || v.getId() != R.id.btnbpPause) {
	        }
	    }

	    protected void uiInit() {
	    }
}
