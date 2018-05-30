package com.ebay.dss.model;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * @author tianhu
 */
public class DealsOutItem implements Serializable{
    

	/**
	 * 
	 */
	public static final long serialVersionUID = 9143828674378586342L;
	public String trans_site;
	public String transaction_id;
	public String seller_country;
	public double plan_gmv;
	public double subsidy;
	public int qty;
	public int stock;
	public long start;
	public long end;
	public long timestamp;
	public String buyer_id;
	public String item_id;
	public String vertical;
	public String meta;
	public String item_title;
	public String seller_name;
	public String module_name;
	public String price;
	public boolean subsidized;

    public DealsOutItem(EventItem eventItem) {
        this.item_id = eventItem.getItemId();
        this.item_title = eventItem.getItem_title();
        this.timestamp = eventItem.getCreatedDT();
        this.qty = eventItem.getTransQuantity();
        this.seller_country = eventItem.getSlr_rollup();
        this.plan_gmv = eventItem.getPlan_gmv();
        this.vertical = eventItem.getVertical();
        this.buyer_id = eventItem.getBuyerId();
        this.transaction_id = eventItem.getM_transactionId();
        this.seller_name = eventItem.getSeller_name();
        this.module_name = eventItem.getModule_name();
        this.start = eventItem.getStart();
        this.end = eventItem.getEnd();
        this.subsidy = eventItem.getSubsidy();
        this.subsidized = eventItem.isSubsidized();
        this.price = eventItem.getPrice();
        this.stock = eventItem.getStock();
        this.meta = eventItem.getMeta();
        this.trans_site = eventItem.getTransactionSiteID();
    }

    public DealsOutItem() {

    }

    public static void main(String[] args){
        DealsOutItem dealsOutItem = new DealsOutItem();
        Class d_class=dealsOutItem.getClass();
        Field[] fields=d_class.getDeclaredFields();
        for (Field field:fields){

                System.out.println(field.getName());

        }
    }
    @Override
	public String toString() {
		return "DealsOutItem [trans_site=" + trans_site + ", transaction_id=" + transaction_id + ", seller_country="
				+ seller_country + ", plan_gmv=" + plan_gmv + ", subsidy=" + subsidy + ", qty=" + qty + ", stock="
				+ stock + ", start=" + start + ", end=" + end + ", timestamp=" + timestamp + ", buyer_id=" + buyer_id
				+ ", item_id=" + item_id + ", vertical=" + vertical + ", meta=" + meta + ", item_title=" + item_title
				+ ", seller_name=" + seller_name + ", module_name=" + module_name + ", price=" + price + ", subsidized="
				+ subsidized + "]";
	}
}
