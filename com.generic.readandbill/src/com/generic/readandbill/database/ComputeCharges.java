package com.generic.readandbill.database;
import android.content.Context;
import com.androidapp.mytools.objectmanager.DoubleManager;
import java.text.DecimalFormat;
public class ComputeCharges {
	  private Consumer consumer;
	    private RateDataSource dsRates;
	    private Rates rate;
	    protected Reading reading;

	    public ComputeCharges(Context context, Consumer consumer) {
	        this.dsRates = new RateDataSource(context);
	        setConsumer(consumer);
	    }

	    public void setConsumer(Consumer consumer) {
	        this.consumer = consumer;
	        this.rate = dsRates.getRate(consumer.getRateCode());
	    }

	    public void setReading(Reading reading) {
	        this.reading = reading;
	    }

	    public Double diff() {
	        double result = 0.0d;
	        DecimalFormat myFormatter = new DecimalFormat("########0.00");
	        if (reading.getFeedBackCode().equals("A")) {
	            return Double.valueOf(reading.getReading());
	        }
	        if (reading.getFeedBackCode().equals("R")) {
	            if (consumer.getInitialReading() < 1000000.0d && consumer.getInitialReading() >= 100000.0d) {
	                result = 1000000.0d - consumer.getInitialReading();
	            } else if (consumer.getInitialReading() < 100000.0d && consumer.getInitialReading() >= 10000.0d) {
	                result = 100000.0d - consumer.getInitialReading();
	            } else if (consumer.getInitialReading() < 10000.0d && consumer.getInitialReading() >= 1000.0d) {
	                result = 10000.0d - consumer.getInitialReading();
	            } else if (consumer.getInitialReading() < 1000.0d) {
	                result = 1000.0d - consumer.getInitialReading();
	            }
	            result += reading.getReading();
	        } else {
	            result = reading.getReading() - consumer.getInitialReading();
	        }
	        return Double.valueOf(Double.parseDouble(myFormatter.format(result)));
	    }

	    public double getKilowatthour() {
	        return diff().doubleValue() * consumer.getMultiplier();
	    }

	    public Double genSys() {
	        return DoubleManager.rRound(Double.valueOf(getKilowatthour() * rate.getGenSys()));
	    }

	    public Double hostComm() {
	        return DoubleManager.rRound(Double.valueOf(getKilowatthour() * rate.getHostComm()));
	    }

	    public Double tcDemand() {
	        return DoubleManager.rRound(Double.valueOf(reading.getDemand() * rate.getTcDemand()));
	    }

	    public Double tcSystem() {
	        return DoubleManager.rRound(Double.valueOf(getKilowatthour() * rate.getTcSystem()));
	    }

	    public Double systemLoss() {
	        return DoubleManager.rRound(Double.valueOf(getKilowatthour() * rate.getSystemLoss()));
	    }

	    public Double dcDemand() {
	        return DoubleManager.rRound(Double.valueOf(reading.getDemand() * rate.getDcDemand()));
	    }

	    public Double dcDistribution() {
	        return DoubleManager.rRound(Double.valueOf(getKilowatthour() * rate.getDcDistribution()));
	    }

	    public Double scRetailCust() {
	        return DoubleManager.rRound(Double.valueOf(rate.getScRetailCust()));
	    }

	    public Double scSupplySys() {
	        return DoubleManager.rRound(Double.valueOf(getKilowatthour() * rate.getScSupplySys()));
	    }

	    public Double mcRetailCust() {
	        return DoubleManager.rRound(Double.valueOf(rate.getMcRetailCust()));
	    }

	    public Double mcSystem() {
	        return DoubleManager.rRound(Double.valueOf(getKilowatthour() * rate.getMcSys()));
	    }

	    public Double reinvestmentFundSustCapex() {
	        return DoubleManager.rRound(Double.valueOf(getKilowatthour() * rate.getReinvestmentFundSustCapex()));
	    }

	    public Double ucme() {
	        return DoubleManager.rRound(Double.valueOf(getKilowatthour() * rate.getUcme()));
	    }

	    public Double ucsd(){
	    	return DoubleManager.rRound(Double.valueOf(getKilowatthour() * rate.getUcsd()));
	    }
	    public Double ucec() {
	        return DoubleManager.rRound(Double.valueOf(getKilowatthour() * rate.getUcec()));
	    }

	    public Double powerActRateRedA() {
	        return DoubleManager.rRound(Double.valueOf(getKilowatthour() * rate.getParr()));	    	
	    }

	    public Double prevYearAdjPowerCost() {
	        return DoubleManager.rRound(Double.valueOf(getKilowatthour() * rate.getPrevYearAdjPowerCost()));
	    }

	    public Double totalPower() {
	        return Double.valueOf(((((((((genSys().doubleValue() + hostComm().doubleValue()) + tcDemand().doubleValue()) + tcSystem().doubleValue()) + systemLoss().doubleValue()) + dcDemand().doubleValue()) + dcDistribution().doubleValue()) + scSupplySys().doubleValue()) + mcRetailCust().doubleValue()) + mcSystem().doubleValue());
	    }

	    public Double scForDiscount() {
	        return Double.valueOf((((((((((genSys().doubleValue() + hostComm().doubleValue()) + tcDemand().doubleValue()) + tcSystem().doubleValue()) + systemLoss().doubleValue()) + dcDemand().doubleValue()) + dcDistribution().doubleValue()) + scRetailCust().doubleValue()) + scSupplySys().doubleValue()) + mcRetailCust().doubleValue()) + mcSystem().doubleValue());
	    }

	    public Double lifelineDiscSubs() {
	        Double result = Double.valueOf(0.0d);
	        if (!rate.getRateCode().equals("R") || getKilowatthour() > 20.0d) {
	            result = Double.valueOf(getKilowatthour() * rate.getLifeLineSubsidy());
	        } else {
	            if (getKilowatthour() <= 20.0d && getKilowatthour() > 19.0d) {
	                result = Double.valueOf(-0.05d);
	            } else if (getKilowatthour() <= 19.0d && getKilowatthour() > 18.0d) {
	                result = Double.valueOf(-0.1d);
	            } else if (getKilowatthour() <= 18.0d && getKilowatthour() > 17.0d) {
	                result = Double.valueOf(-0.2d);
	            } else if (getKilowatthour() <= 17.0d && getKilowatthour() > 16.0d) {
	                result = Double.valueOf(-0.3d);
	            } else if (getKilowatthour() <= 16.0d && getKilowatthour() > 15.0d) {
	                result = Double.valueOf(-0.4d);
	            } else if (getKilowatthour() <= 15.0d) {
	                result = Double.valueOf(-0.5d);
	            }
	            result = Double.valueOf(result.doubleValue() * totalPower().doubleValue());
	        }
	        return DoubleManager.rRound(result);
	    }

	    public Double seniorCitizenDiscRate() {
	        Double result = Double.valueOf(0.0d);
	        if (getKilowatthour() <= 20.0d && getKilowatthour() > 19.0d) {
	            return Double.valueOf(-0.95d);
	        }
	        if (getKilowatthour() <= 19.0d && getKilowatthour() > 18.0d) {
	            return Double.valueOf(-0.9d);
	        }
	        if (getKilowatthour() <= 18.0d && getKilowatthour() > 17.0d) {
	            return Double.valueOf(-0.8d);
	        }
	        if (getKilowatthour() <= 17.0d && getKilowatthour() > 16.0d) {
	            return Double.valueOf(-0.7d);
	        }
	        if (getKilowatthour() <= 16.0d && getKilowatthour() > 15.0d) {
	            return Double.valueOf(-0.6d);
	        }
	        if (getKilowatthour() <= 15.0d) {
	            return Double.valueOf(-0.5d);
	        }
	        return result;
	    }

	    public Double currentBill() {
	        return DoubleManager.rRound(Double.valueOf((((((((((((((((genSys().doubleValue() + hostComm().doubleValue()) + tcDemand().doubleValue()) + tcSystem().doubleValue()) + systemLoss().doubleValue()) + dcDemand().doubleValue()) + dcDistribution().doubleValue()) + scRetailCust().doubleValue()) + scSupplySys().doubleValue()) + mcRetailCust().doubleValue()) + mcSystem().doubleValue()) + ucme().doubleValue()) + ucec().doubleValue()) + powerActRateRedA().doubleValue()) + lifelineDiscSubs().doubleValue()) + prevYearAdjPowerCost().doubleValue()) + reinvestmentFundSustCapex().doubleValue()));
	    }
}
