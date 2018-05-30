package com.ebay.dss.model;

/**
 * @author tianhu
 */
public class TransactionFields {
    public static final String GMV = "gmv";//spot rate
    public static final String QUANTITY = "transQuantity";
    public static final String ITEM = "itemId";
    public static final String USER = "buyerId";
    public static final String TIMESTAMP = "createdDT";
    public static final String SUM = "sum";

    public static final String BUYER_ZIP = "buyerZipcode";
    public static final String ITEM_ZIP = "itemZipcode";

    public static final String SITE = "transactionSiteID";

    public static final String SLR_CNTRY = "sellercntry";
    public static final String BYR_CNTRY = "buyerCntry";

    //LSTG_GMV * CURRENCY_ID will get plan rate GMV
    public static final String LSTG_GMV = "gmvLstgAmt";
    public static final String CURRENCY_ID = "currencyID";
}
