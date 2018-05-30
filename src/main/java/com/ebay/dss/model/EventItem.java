/**
 *
 */
package com.ebay.dss.model;

import java.io.Serializable;
import java.util.Map;

/**
 * @author tianhu
 */
public class EventItem implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1450430224554036389L;
	private final String categoryTwo;
    private String transactionSiteID;
    private String m_transactionId;
    private String sellercntry;
    private String categoryOne;
    private String listingSiteID;
    private double gmv;
    private double plan_gmv;
    private double subsidy;
    private int transQuantity;
    private int stock;
    private long createdDT;
    private long start;
    private long end;
    private long ts;
    private String buyerCntry;
    private String currencyID;
    private String gmvLstgAmt;

    private String sellerId;
    private String buyerId;
    private String itemId;
    private String lstgTypeCd;
    private String slr_rollup;
    private String slr_type;
    private String vertical;
    private String meta;
    private String item_title;
    private String seller_name;
    private String module_name;
    private String price;
    private boolean subsidized;


    @Override
	public String toString() {
		return "EventItem [categoryTwo=" + categoryTwo + ", transactionSiteID=" + transactionSiteID
				+ ", m_transactionId=" + m_transactionId + ", sellercntry=" + sellercntry + ", categoryOne="
				+ categoryOne + ", listingSiteID=" + listingSiteID + ", gmv=" + gmv + ", plan_gmv=" + plan_gmv
				+ ", subsidy=" + subsidy + ", transQuantity=" + transQuantity + ", stock=" + stock + ", createdDT="
				+ createdDT + ", start=" + start + ", end=" + end + ", ts=" + ts + ", buyerCntry=" + buyerCntry
				+ ", currencyID=" + currencyID + ", gmvLstgAmt=" + gmvLstgAmt + ", sellerId=" + sellerId + ", buyerId="
				+ buyerId + ", itemId=" + itemId + ", lstgTypeCd=" + lstgTypeCd + ", slr_rollup=" + slr_rollup
				+ ", slr_type=" + slr_type + ", vertical=" + vertical + ", meta=" + meta + ", item_title=" + item_title
				+ ", seller_name=" + seller_name + ", module_name=" + module_name + ", price=" + price + ", subsidized="
				+ subsidized + "]";
	}

	public EventItem(Map<String, Object> map) {
        this.itemId = (String) map.get(TransFields.ITEM);
        this.sellerId = (String) map.get(TransFields.SELLER);
        this.buyerId = (String) map.get(TransFields.BUYER);
        this.m_transactionId = (String) map.get(TransFields.TRANSACTION_ID);
        this.transactionSiteID = (String) map.get(TransFields.TRANS_SITE);
        this.listingSiteID = (String) map.get(TransFields.LSTG_SITE);
        this.gmv = (Double) map.get(TransFields.GMV);
        this.sellercntry = (String) map.get(TransFields.SELLER_CNTRY);
        this.categoryOne = (String) map.get(TransFields.CATY_1);
        this.categoryTwo = (String) map.get(TransFields.CATY_2);
        this.transQuantity = (Integer) map.get(TransFields.QTY);
        this.createdDT = (Long) map.get(TransFields.TRANS_DT);
        this.lstgTypeCd = (String) map.get(TransFields.LSTG_TYPE);
        this.buyerCntry = (String) map.get(TransFields.BUYER_CNTRY);
        this.gmvLstgAmt = (String) map.get(TransFields.LSTG_GMV);
        this.currencyID = (String) map.get(TransFields.CURRENCY_ID);

    }

    public String getCategoryTwo() {
        return categoryTwo;
    }

    public String getTransactionSiteID() {
        return transactionSiteID;
    }

    public void setTransactionSiteID(String transactionSiteID) {
        this.transactionSiteID = transactionSiteID;
    }

    public String getM_transactionId() {
        return m_transactionId;
    }

    public void setM_transactionId(String m_transactionId) {
        this.m_transactionId = m_transactionId;
    }

    public String getSellercntry() {
        return sellercntry;
    }

    public void setSellercntry(String sellercntry) {
        this.sellercntry = sellercntry;
    }

    public String getCategoryOne() {
        return categoryOne;
    }

    public void setCategoryOne(String categoryOne) {
        this.categoryOne = categoryOne;
    }

    public String getListingSiteID() {
        return listingSiteID;
    }

    public void setListingSiteID(String listingSiteID) {
        this.listingSiteID = listingSiteID;
    }

    public double getGmv() {
        return gmv;
    }

    public void setGmv(double gmv) {
        this.gmv = gmv;
    }

    public double getPlan_gmv() {
        return plan_gmv;
    }

    public void setPlan_gmv(double plan_gmv) {
        this.plan_gmv = plan_gmv;
    }

    public double getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(double subsidy) {
        this.subsidy = subsidy;
    }

    public int getTransQuantity() {
        return transQuantity;
    }

    public void setTransQuantity(int transQuantity) {
        this.transQuantity = transQuantity;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public long getCreatedDT() {
        return createdDT;
    }

    public void setCreatedDT(long createdDT) {
        this.createdDT = createdDT;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String getBuyerCntry() {
        return buyerCntry;
    }

    public void setBuyerCntry(String buyerCntry) {
        this.buyerCntry = buyerCntry;
    }

    public String getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(String currencyID) {
        this.currencyID = currencyID;
    }

    public String getGmvLstgAmt() {
        return gmvLstgAmt;
    }

    public void setGmvLstgAmt(String gmvLstgAmt) {
        this.gmvLstgAmt = gmvLstgAmt;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getLstgTypeCd() {
        return lstgTypeCd;
    }

    public void setLstgTypeCd(String lstgTypeCd) {
        this.lstgTypeCd = lstgTypeCd;
    }

    public String getSlr_rollup() {
        return slr_rollup;
    }

    public void setSlr_rollup(String slr_rollup) {
        this.slr_rollup = slr_rollup;
    }

    public String getSlr_type() {
        return slr_type;
    }

    public void setSlr_type(String slr_type) {
        this.slr_type = slr_type;
    }

    public String getVertical() {
        return vertical;
    }

    public void setVertical(String vertical) {
        this.vertical = vertical;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getModule_name() {
        return module_name;
    }

    public void setModule_name(String module_name) {
        this.module_name = module_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isSubsidized() {
        return subsidized;
    }

    public void setSubsidized(boolean subsidized) {
        this.subsidized = subsidized;
    }
}
