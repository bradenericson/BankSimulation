/**
 * @authors Braden Ericson and Nicole Lopez
 *
 * This class holds all helpful customer information for the Sawin International bank.
 *
 */

public class Customer{

private long arrivalTime;
private long transactionTime;
private boolean isWalkIn; //true if the customer walked in the bank (as opposed to drive-thru)
private long eventTime;
private boolean wasHelped; //true if being helped or done
private int tellerNumber; //the id of the teller for which the customer is in line

/**
 * Constructor for the class. Initializes all instance variables and takes in three parameters.
 * @param arrival a long of the time at which the customer arrived at the bank
 * @param transactionTime a long of the duration of the customer's necessary transaction
 * @param walkedIn a boolean that should be true if the customer was a walk-in (and false for a drive-thru customer)
 */
public Customer(long arrival, long transactionTime, boolean walkedIn)
	{
		if(arrival < 0){throw new IllegalArgumentException("Arrival time must be greater than or equal to 0. You cannot get to the bank before it opens.");}
		if(transactionTime < 0 ){throw new IllegalArgumentException("Transaction time must be greater than 0, otherwise you are wasting our time");}

		this.arrivalTime=arrival;
		this.transactionTime=transactionTime;
		this.isWalkIn=walkedIn;
		this.eventTime=arrivalTime;
		this.wasHelped=false;
		this.tellerNumber=-1;
	}
/**
 * This method returns the time at which the customer arrived
 * @return the arrival time
 */
public long getArrivalTime()
	{
	return this.arrivalTime;
	}
/**
 * This method returns the time it will take the customer to complete their transaction
 * @return the transaction time
 */
public long getTransactionTime()
	{
	return this.transactionTime;
	}
/**
 * This method returns true if the customer was a walk-in, or false if the customer utilized the drive-thru
 * @return whether or not the customer walked in
 */
public boolean getIsWalkIn()
	{
	return this.isWalkIn;
	}
/**
 * This method sets the major event time for the customer. When the customer first enters the bank, their event time is their arrival time. Once they have been helped, their event time should be set to their estimated time of departure.
 * @param newTime a long of the new major event time for the customer.
 */
public void setEventTime(long newTime)
	{
	this.eventTime=newTime;
	}
/**
 * This method returns the event time of the customer, which should either be their arrival time or pending leave time.
 * @return the event time of the customer
 */
public long getEventTime()
	{
	return this.eventTime;
	}
/**
 * This method sets the was helped boolean to true if the customer has been assissted by a teller.
 * @param the customers status of being helped (true if helped, false if still waiting)
 */
public void setWasHelped(boolean status)
	{
	this.wasHelped=status; //true means customer is being helped or done
	}
/**
 * This method returns true if the customer was helped by a teller and false if the customer is still waiting
 * @return true if the customer was helped
 */
public boolean getWasHelped()
	{
	return this.wasHelped;
	}
/**
 * This method returns the identification number of the teller whose line the customer chose to stand in if the customer was a walk-in
 * @return the identification number of the customer's chosen teller
 */
public int getTellerNumber()
	{
	return this.tellerNumber;
	}
/**
 * This method sets the identification number of the teller whose line the customer chose to stand in if the customer was a walk-in
 * @param the number of the teller chosen by the customer
 */
public void setTellerNumber(int id)
	{
	this.tellerNumber=id;
	}

}//end class