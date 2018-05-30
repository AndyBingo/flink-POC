package com.ebay.dss.model;

import java.lang.reflect.Field;

/**
 * @author tianhu
 */
public class TransFields {

    public static final String ITEM = "itemId";
    public static final String SELLER = "sellerId";
    public static final String BUYER = "buyerId";

//    public static final String BUYER_ZIP = "buyerZipcode_";
//    public static final String ITEM_ZIP = "itemZipcode_";

    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";

    public static final String SELLER_CNTRY = "sellercntry";
    public static final String BUYER_CNTRY = "buyerCntry";

    public static final String TRANS_SITE = "transactionSiteID";
    public static final String LSTG_SITE = "listingSiteID";

    public static final String GMV = "gmv";
    public static final String QTY = "transQuantity";

    public static final String TRANS_DT = "createdDT";//create_dt

    public static final String EPID = "productId";
    public static final String CATY_1 = "categoryOne";
    public static final String CATY_2 = "categoryTwo";

    public static final String TRANS_TYPE = "transactionType";
    public static final String LSTG_TYPE = "lstgTypeCd";

    public static final String VERTICAL = "vertical";
    public static final String CNTRY = "cntry";

    public static final String LSTG_GMV = "gmvLstgAmt";
    public static final String CURRENCY_ID = "currencyID";
    public static final String TRANSACTION_ID = "m_transactionId";

    public static final String TITLE = "item_title";
    //    public static final String VERTICAL = "item_vertical";
    public static final String SELLER_NAME = "seller_name";
    public static final String MODULE = "module_name";
    public static final String START = "start";
    public static final String END = "end";
    public static final String SUBSIDY = "subsidy";
    public static final String SUBSIDIZED = "subsidized";
    public static final String PRICE = "price";
    public static final String STOCK = "stock";

    public static String[] fields() {
        Field[] fields = TransFields.class.getDeclaredFields();
        String[] names = new String[fields.length];
        int i = 0;
        for (Field f : fields) {
            try {
                names[i] = (String) fields[i].get(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            i++;
        }
        return names;

    }
}
