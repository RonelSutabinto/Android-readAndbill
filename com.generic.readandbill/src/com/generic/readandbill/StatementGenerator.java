package com.generic.readandbill;
import android.content.Context;
import com.generic.readandbill.database.Consumer;
import com.generic.readandbill.database.ConsumerDataSource;
import com.generic.readandbill.database.RateDataSource;
import com.generic.readandbill.database.Rates;
import com.generic.readandbill.database.Reading;
import com.generic.readandbill.database.ReadingDataSource;
import com.generic.readandbill.database.UserProfile;
import com.generic.readandbill.database.UserProfileDataSource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
public class StatementGenerator {
	  protected static final String END_STRING = "\n";
	    protected DecimalFormat amountFormat;
	    protected final Context context;
	    protected ConsumerDataSource dsConsumer;
	    protected RateDataSource dsRate;
	    protected ReadingDataSource dsReading;
	    protected UserProfileDataSource dsUserProfile;
	    protected DecimalFormat kilowattUsed;
	    protected Consumer myConsumer;
	    protected DecimalFormat percentage;
	    protected Rates rate;
	    protected DecimalFormat rateFormat;
	    protected Reading reading;
	    protected UserProfile userProfile;

	    public StatementGenerator(Context context, Consumer myConsumer) {
	        this.context = context;
	        this.dsConsumer = new ConsumerDataSource(context);
	        this.dsReading = new ReadingDataSource(context);
	        this.dsUserProfile = new UserProfileDataSource(context);
	        this.dsRate = new RateDataSource(context);
	        this.kilowattUsed = new DecimalFormat("######0.0");
	        this.amountFormat = new DecimalFormat("###,###,##0.00");
	        this.rateFormat = new DecimalFormat("##0.0000");
	        this.myConsumer = myConsumer;
	        if (myConsumer != null) {
	            this.reading = this.dsReading.getReading(myConsumer.getId(), 20);
	            this.rate = this.dsRate.getRate(myConsumer.getRateCode());
	        }
	    }

	    public List<String> generateSOA() {
	        List<String> result = new ArrayList();
	        if (this.myConsumer != null) {
	            for (String string : soaHeader()) {
	                result.add(string);
	            }
	            for (String string2 : soaBody()) {
	                result.add(string2);
	            }
	            for (String string22 : soaFooter()) {
	                result.add(string22);
	            }
	        }
	        return result;
	    }

	    protected List<String> soaFooter() {
	        return null;
	    }

	    protected List<String> soaBody() {
	        return null;
	    }

	    protected List<String> soaHeader() {
	        return null;
	    }
}
