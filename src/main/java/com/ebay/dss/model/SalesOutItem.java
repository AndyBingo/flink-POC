package com.ebay.dss.model;

import java.io.Serializable;

/**
 * @author tianhu
 */
public class SalesOutItem  {
    private long createdDT;
    private long ts;
    private double plan_gmv;
    private int transQuantity;
    private String itemId;
    private String transactionSiteID;
    private String sellercntry;
    private String categoryOne;
    private String listingSiteID;

    private String vertical;

    private String buyerCntry;
    private String sellerId;
    private String lstgTypeCd;
    private String slr_rollup;
    private String slr_type;





    public SalesOutItem(EventItem eventItem) {
        this.slr_rollup = eventItem.getSlr_rollup();
        this.ts = eventItem.getTs();
        this.vertical = eventItem.getVertical();
        this.slr_type = eventItem.getSlr_type();
        this.transQuantity = eventItem.getTransQuantity();
        this.plan_gmv = eventItem.getPlan_gmv();
        this.transactionSiteID = eventItem.getTransactionSiteID();
        this.createdDT = eventItem.getCreatedDT();
        this.itemId = eventItem.getItemId();
        this.lstgTypeCd = eventItem.getLstgTypeCd();


        this.categoryOne = eventItem.getCategoryOne();
        this.sellercntry = eventItem.getSellercntry();
        this.listingSiteID = eventItem.getListingSiteID();
        this.buyerCntry = eventItem.getBuyerCntry();
        this.sellerId = eventItem.getSellerId();
        this.itemId = eventItem.getItemId();


    }

    @Override
    public String toString() {
        return "SalesOutItem{" +
                "transactionSiteID='" + transactionSiteID + '\'' +
                ", sellercntry='" + sellercntry + '\'' +
                ", categoryOne='" + categoryOne + '\'' +
                ", listingSiteID='" + listingSiteID + '\'' +
                ", plan_gmv=" + plan_gmv +
                ", transQuantity=" + transQuantity +
                ", createdDT=" + createdDT +
                ", ts=" + ts +
                ", buyerCntry='" + buyerCntry + '\'' +
                ", sellerId='" + sellerId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", lstgTypeCd='" + lstgTypeCd + '\'' +
                ", slr_rollup='" + slr_rollup + '\'' +
                ", slr_type='" + slr_type + '\'' +
                ", vertical='" + vertical + '\'' +
                '}';
    }

    public String getTransactionSiteID() {
        return transactionSiteID;
    }

    public void setTransactionSiteID(String transactionSiteID) {
        this.transactionSiteID = transactionSiteID;
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

    public Double getPlan_gmv() {
        return plan_gmv;
    }

    public void setPlan_gmv(Double plan_gmv) {
        this.plan_gmv = plan_gmv;
    }

    public Integer getTransQuantity() {
        return transQuantity;
    }

    public void setTransQuantity(Integer transQuantity) {
        this.transQuantity = transQuantity;
    }

    public Long getCreatedDT() {
        return createdDT;
    }

    public void setCreatedDT(Long createdDT) {
        this.createdDT = createdDT;
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

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
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
}
