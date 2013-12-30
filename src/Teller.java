/**
 * @authors Braden Ericson and Nicole Lopez
 *
 * This class holds all helpful teller information for the Sawin International bank.
 *
 */
public class Teller{

private int numberOfCustomers;
private long startIdleTime; //when the teller started the most recent bout of idling
private long totalIdleTime;
private boolean isBusy; //true if teller currently helping someone

/**
 * Constructor for the class. Initializes all instance variables to either 0 or false.
 */
public Teller()
	{
	this.numberOfCustomers=0;
	this.startIdleTime=0;
	this.totalIdleTime=0;
	this.isBusy=false;
	}

/**
 * This method sets time that the teller starts idling if he or she is no longer helping someone.
 * @param newTime a long of the new time for the teller.
 */
public void setStartIdleTime(long start)
	{
	this.startIdleTime=start;
	}

/**
 * This method returns the time at which the teller most recently found idle time on their hands.
 * @return the time the teller started to idle.
 */
public long getStartIdleTime()
	{
	return this.startIdleTime;
	}

/**
 * This method increments the total amount of idle time by the time indicated.
 * @param newTime a long of the amount of idle time to be added to the teller's total idle time.
 */
public void setTotalIdleTime(long time)
	{
	this.totalIdleTime=this.totalIdleTime+time;
	}
/**
 * This method returns the total amount of time the teller has had free.
 * @return the total idle time.
 */
public long getTotalIdleTime()
	{
	return this.totalIdleTime;
	}
/**
 * This method returns the total number of customers the teller has helped.
 * @return the total number of customers helped.
 */
public int getNumberOfCustomers()
	{
	return this.numberOfCustomers;
	}
/**
 * This method increments the number of customers helped by one.
 */
public void incrementNumberOfCustomers()
	{
	this.numberOfCustomers++;
	}
/**
 * This method sets the isBusy boolean ot the given status.
 * @param a boolean status that is true if the teller is busy helping someone and false if he is free from the drudgery of work.
 */
public void setIsBusy(boolean status)
	{
	this.isBusy=status;
	}
/**
 * This method true if the teller is busy and can't be bothered, or false if he or she is lazing around.
 * @return the isBusy boolean.
 */
public boolean getIsBusy()
	{
	return this.isBusy;
	}

}//end class