package com.laneco.readandbill;
import android.os.Bundle;
import android.widget.TextView;
import com.laneco.readandbill.database.Consumer;
import com.laneco.readandbill.database.ConsumerDataSource;
public class MyFieldFindingEntry extends com.generic.readandbill.MyFieldFindingEntry{
	 private Consumer consumer;
	    private ConsumerDataSource dsConsumer;
	    private TextView mCMkilowatthour;
	    private TextView mTransLoss;

	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        this.dsConsumer = new ConsumerDataSource(this);
	    }

	    protected void initControls() {
	        super.initControls();
	        this.consumer = this.dsConsumer.getConsumer(Long.valueOf(this.extras.getLong("id")));
	        this.mTransLoss.setText(String.valueOf(this.consumer.getTransLoss()));
	        this.mCMkilowatthour.setText(String.valueOf(this.consumer.getChangeMeterKilowatthour()));
	    }
}
