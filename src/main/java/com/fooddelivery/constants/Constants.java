package com.fooddelivery.constants;

public class Constants {
	public static final String HEALTH_CHECK_MESSAGE = "Welcome to Food Delivery Service, I am up and running -:)";

	// (Placed, Confirmed, Ready, Picked-up, Delivered, Canceled)
	public static final Integer ORDER_STATUS_PLACED = 0; //Order placed
	public static final Integer ORDER_STATUS_CONFIRMED = 1; //Order confirmed
	public static final Integer ORDER_STATUS_READY = 2; //Order ready
	public static final Integer ORDER_STATUS_PICKED_UP = 3; //Order on the way
	public static final Integer ORDER_STATUS_DELIVERED = 4; //Order delivered
	public static final Integer ORDER_STATUS_CANCELLED = 5; //Order cancelled

	public static final String TRANSACTION_TYPE_CREDIT = "Credit";
	public static final String TRANSACTION_TYPE_DEBIT = "Debit";


}
