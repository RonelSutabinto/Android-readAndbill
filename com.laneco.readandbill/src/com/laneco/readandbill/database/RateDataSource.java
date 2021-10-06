package com.laneco.readandbill.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.androidapp.mytools.objectmanager.ArrayManager;
import com.generic.readandbill.database.UserProfileDataSource;
import java.util.List;

public class RateDataSource extends com.generic.readandbill.database.RateDataSource{
	public static final String FEED_TARIFF_ALLOWANCE = "feedtariffallowance";
    public static final String ICERA = "icera";
    public static final String OVERUNDERRECOVERY = "overunderrecovery";
    public static final String REAL_PROPERTY_TAX = "realpropertytax";
    public static final String SYSTEMLOSS_TRANSMISSION = "systemlosstransmission";
    public static final String UCMERED = "ucmered";
    public static final String UCSTRANDEDCONTRACTCOST = "ucstrandedcontractcost";
    public static final String VAT_DCDEMAND = "vatdcdemand";
    public static final String VAT_DCDISTRIBUTION = "vatdcdistribution";
    public static final String VAT_GENSYS = "vatgensys";
    public static final String VAT_HOSTCOMM = "vathostcomm";
    public static final String VAT_ICERA = "vaticera";
    public static final String VAT_LIFELINESUBSIDY = "vatlifelinesubsidy";
    public static final String VAT_MCRETAIL = "vatmcretail";
    public static final String VAT_MCSYSTEM = "vatmcsystem";
    public static final String VAT_PARR = "vatparr";
    public static final String VAT_PREVYEARADJPOWERCOST = "vatprevyearadjpowercost";
    public static final String VAT_REINVESTMENTFUNDSUSTCAPEX = "vatreinvestmentfundsustcapex";
    public static final String VAT_SCRETAIL = "vatscretail";
    public static final String VAT_SCSUPPLY = "vatscsupply";
    public static final String VAT_SENIORCITIZEN = "vatseniorcitizen";
    public static final String VAT_SYSTEMLOSS = "vatsystemloss";
    public static final String VAT_SYSTEMLOSSTRANSMISSION = "vatsystemlosstransmission";
    public static final String VAT_TCDEMAND = "vattcdemand";
    public static final String VAT_TCSYSTEM = "vattcsystem";
    private ReadandBillDatabaseHelper dbHelper;
    private String[] lAllColumns;

    public static List<String> ratesFields() {
        List<String> rateFields = com.generic.readandbill.database.RateDataSource.ratesFields();
        rateFields.set(rateFields.size() - 1, ((String) rateFields.get(rateFields.size() - 1)) + ", ");
        rateFields.add("systemlosstransmission real not null, ");
        rateFields.add("feedtariffallowance real not null, ");
        rateFields.add("ucstrandedcontractcost real not null, ");
        rateFields.add("ucmered real not null, ");
        rateFields.add("icera real not null, ");
        rateFields.add("overunderrecovery real not null, ");
        rateFields.add("realpropertytax real not null, ");
        rateFields.add("vatgensys real not null, ");
        rateFields.add("vathostcomm real not null, ");
        rateFields.add("vatsystemloss real not null, ");
        rateFields.add("vaticera real not null, ");
        rateFields.add("vatparr real not null, ");
        rateFields.add("vattcsystem real not null, ");
        rateFields.add("vattcdemand real not null, ");
        rateFields.add("vatsystemlosstransmission real not null, ");
        rateFields.add("vatdcdemand real not null, ");
        rateFields.add("vatdcdistribution real not null, ");
        rateFields.add("vatscretail real not null, ");
        rateFields.add("vatscsupply real not null, ");
        rateFields.add("vatmcretail real not null, ");
        rateFields.add("vatmcsystem real not null, ");
        rateFields.add("vatlifelinesubsidy real not null, ");
        rateFields.add("vatseniorcitizen real not null, ");
        rateFields.add("vatreinvestmentfundsustcapex real not null, ");
        rateFields.add("vatprevyearadjpowercost real not null");
        return rateFields;
    }

    public RateDataSource(Context context) {
        super(new ReadandBillDatabaseHelper(context), context);
        this.lAllColumns = new String[]{SYSTEMLOSS_TRANSMISSION, FEED_TARIFF_ALLOWANCE, UCSTRANDEDCONTRACTCOST, UCMERED, OVERUNDERRECOVERY, REAL_PROPERTY_TAX, VAT_GENSYS, VAT_HOSTCOMM, VAT_SYSTEMLOSS, VAT_ICERA, VAT_PARR, VAT_TCSYSTEM, VAT_TCDEMAND, VAT_SYSTEMLOSSTRANSMISSION, VAT_DCDEMAND, VAT_DCDISTRIBUTION, VAT_SCRETAIL, VAT_SCSUPPLY, VAT_MCRETAIL, VAT_MCSYSTEM, VAT_LIFELINESUBSIDY, VAT_SENIORCITIZEN, VAT_REINVESTMENTFUNDSUSTCAPEX, VAT_PREVYEARADJPOWERCOST};
        this.dbHelper = new ReadandBillDatabaseHelper(context);
        this.allColumns = ArrayManager.concat(this.allColumns, this.lAllColumns);
    }

    protected ContentValues rateContentValues(Rates rate) {
        ContentValues values = super.rateContentValues(rate);
        values.put(SYSTEMLOSS_TRANSMISSION, Double.valueOf(rate.getSystemLossTransmission()));
        values.put(FEED_TARIFF_ALLOWANCE, Double.valueOf(rate.getFeedTariffAllowance()));
        values.put(UCSTRANDEDCONTRACTCOST, Double.valueOf(rate.getUcStrandedContractCost()));
        values.put(UCMERED, Double.valueOf(rate.getUcmeRed()));
        values.put(ICERA, Double.valueOf(rate.getIcera()));
        values.put(OVERUNDERRECOVERY, Double.valueOf(rate.getOverUnderRecovery()));
        values.put(REAL_PROPERTY_TAX, Double.valueOf(rate.getRealPropertyTax()));
        values.put(VAT_GENSYS, Double.valueOf(rate.getVatGensys()));
        values.put(VAT_HOSTCOMM, Double.valueOf(rate.getVatHostComm()));
        values.put(VAT_SYSTEMLOSS, Double.valueOf(rate.getVatSystemLoss()));
        values.put(VAT_ICERA, Double.valueOf(rate.getVatIcera()));
        values.put(VAT_PARR, Double.valueOf(rate.getVatPARR()));
        values.put(VAT_TCSYSTEM, Double.valueOf(rate.getVatTcSystem()));
        values.put(VAT_TCDEMAND, Double.valueOf(rate.getVatTcDemand()));
        values.put(VAT_SYSTEMLOSSTRANSMISSION, Double.valueOf(rate.getVatSystemLossTransmission()));
        values.put(VAT_DCDEMAND, Double.valueOf(rate.getVatDcDemand()));
        values.put(VAT_DCDISTRIBUTION, Double.valueOf(rate.getVatDcDistribution()));
        values.put(VAT_SCRETAIL, Double.valueOf(rate.getVatScRetail()));
        values.put(VAT_SCSUPPLY, Double.valueOf(rate.getVatScSupply()));
        values.put(VAT_MCRETAIL, Double.valueOf(rate.getVatMcRetail()));
        values.put(VAT_MCSYSTEM, Double.valueOf(rate.getVatMcSystem()));
        values.put(VAT_LIFELINESUBSIDY, Double.valueOf(rate.getVatLifelineSubsidy()));
        values.put(VAT_SENIORCITIZEN, Double.valueOf(rate.getVatSeniorCitizen()));
        values.put(VAT_REINVESTMENTFUNDSUSTCAPEX, Double.valueOf(rate.getVatReinvestmentFundSustCapex()));
        values.put(VAT_PREVYEARADJPOWERCOST, Double.valueOf(rate.getVatPrevYearAdjPowerCost()));
        return values;
    }

    public Rates createRates(Rates rate) {
        this.db = this.dbHelper.getWritableDatabase();
        long insertId = this.db.insert(com.generic.readandbill.database.RateDataSource.TABLE_RATES, null, rateContentValues(rate));
        this.db.close();
        return getRate(insertId);
    }

    public Rates getRate(long id) {
        this.db = this.dbHelper.getReadableDatabase();
        Cursor cursor = this.db.query(com.generic.readandbill.database.RateDataSource.TABLE_RATES, this.allColumns, id + "=" + UserProfileDataSource.ID, null, null, null, null);
        cursor.moveToFirst();
        Rates myRate = cursorToRate(cursor);
        cursor.close();
        this.db.close();
        return myRate;
    }

    public Rates getConsumerRate(String RateCode) {
        this.db = this.dbHelper.getReadableDatabase();
        Cursor cursor = this.db.query(com.generic.readandbill.database.RateDataSource.TABLE_RATES, this.allColumns, "ratecode='" + RateCode + "'", null, null, null, null);
        cursor.moveToFirst();
        this.db.close();
        return cursorToRate(cursor);
    }

    protected Rates cursorToRate(Cursor cursor) {
        Rates rate = new Rates();
        if (cursor.getCount() == 0) {
            return rate;
        }
        rate = (Rates) super.cursorToRate(cursor, rate);
        rate.setSystemLossTransmission(cursor.getDouble(cursor.getColumnIndex(SYSTEMLOSS_TRANSMISSION)));
        rate.setFeedTariffAllowance(cursor.getDouble(cursor.getColumnIndex(FEED_TARIFF_ALLOWANCE)));
        rate.setUcStrandedContractCost(cursor.getDouble(cursor.getColumnIndex(UCSTRANDEDCONTRACTCOST)));
        rate.setUcmeRed(cursor.getDouble(cursor.getColumnIndex(UCMERED)));
        rate.setRealPropertyTax(cursor.getDouble(cursor.getColumnIndex(REAL_PROPERTY_TAX)));
        rate.setVatGensys(cursor.getDouble(cursor.getColumnIndex(VAT_GENSYS)));
        rate.setVatHostComm(cursor.getDouble(cursor.getColumnIndex(VAT_HOSTCOMM)));
        rate.setVatSystemLoss(cursor.getDouble(cursor.getColumnIndex(VAT_SYSTEMLOSS)));
        rate.setVatPARR(cursor.getDouble(cursor.getColumnIndex(VAT_PARR)));
        rate.setVatTcSystem(cursor.getDouble(cursor.getColumnIndex(VAT_TCSYSTEM)));
        rate.setVatSystemLossTransmission(cursor.getDouble(cursor.getColumnIndex(VAT_SYSTEMLOSSTRANSMISSION)));
        rate.setVatDcDemand(cursor.getDouble(cursor.getColumnIndex(VAT_DCDEMAND)));
        rate.setVatDcDistribution(cursor.getDouble(cursor.getColumnIndex(VAT_DCDISTRIBUTION)));
        rate.setVatScRetail(cursor.getDouble(cursor.getColumnIndex(VAT_SCRETAIL)));
        rate.setVatScSupply(cursor.getDouble(cursor.getColumnIndex(VAT_SCSUPPLY)));
        rate.setVatMcRetail(cursor.getDouble(cursor.getColumnIndex(VAT_MCRETAIL)));
        rate.setVatMcSystem(cursor.getDouble(cursor.getColumnIndex(VAT_MCSYSTEM)));
        rate.setVatLifelineSubsidy(cursor.getDouble(cursor.getColumnIndex(VAT_LIFELINESUBSIDY)));
        rate.setVatSeniorCitizen(cursor.getDouble(cursor.getColumnIndex(VAT_SENIORCITIZEN)));
        rate.setVatReinvestmentFundSustCapex(cursor.getDouble(cursor.getColumnIndex(VAT_REINVESTMENTFUNDSUSTCAPEX)));
        rate.setVatPrevYearAdjPowerCost(cursor.getDouble(cursor.getColumnIndex(VAT_PREVYEARADJPOWERCOST)));
        return rate;
    }

    public void truncate() {
        this.db = this.dbHelper.getWritableDatabase();
        this.dbHelper.refreshTable(this.db, com.generic.readandbill.database.RateDataSource.TABLE_RATES, ratesFields());
        this.db.close();
    }
}
