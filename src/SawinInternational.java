import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @authors Braden Ericson and Nicole Lopez
 *
 * This class holds all methods for the Sawin International bank.
 *
 */

public class SawinInternational{

private ArrayList<Long> tellerIdlenessArray = new ArrayList<Long>();
private Comparator<Customer> comparator = new CustomerComparator();
private PriorityQueue<Customer> customerList = new PriorityQueue<Customer>(5,comparator);
private ArrayList<Long> waitingTimesArray = new ArrayList<Long>();
private Teller[] tellerArray;
private ArrayList<LinkedList<Customer>> tellerLines;
private LinkedList<Customer> driveIn= new LinkedList<Customer>();
private int numberOfCustomersHelped;
private long globalTime;
private static final DecimalFormat df = new DecimalFormat("##.##");

/**
 * Constructor for the class. Initializes all instance variables and takes in three parameters.
 * @param numberOfTellers an int of the total number of tellers working at the bank.
 * @param customerDataFile a file containing all the information for all customers coming to the bank.
 * @param DriveInOpen a boolean that should be true if the drive in is open in the bank, and false if all people who attempt to use the drive thru must come inside.
 */
public SawinInternational(int numberOfTellers, File customerDataFile, boolean DriveInOpen)
{
	if(numberOfTellers <= 0 ){throw new IllegalArgumentException("Number of Tellers must be greater than zero");}


	this.numberOfCustomersHelped=0;
	this.tellerArray= new Teller[numberOfTellers];
	this.tellerLines= new ArrayList<LinkedList<Customer>>(numberOfTellers);
	String[] customerData;
	Customer customer;
	boolean bool;
	this.globalTime=0;
	try
		{
		Scanner fileScanner = new Scanner(customerDataFile);
		while(fileScanner.hasNext())
			{
	        String line = fileScanner.nextLine();
	        customerData=line.split("\t");


	        //check three data fields to be accurate
	        try
	        {
	    	    if(!DriveInOpen)
	    	    	{
					customer=new Customer(Integer.parseInt(customerData[0]),Integer.parseInt(customerData[1]),true);
					}
	    	    else
	    	    	{
					if(customerData[2].trim().equals("false"))
						{
						bool=false;
						}
					else
						{
						bool=true;
						}
					customer=new Customer(Integer.parseInt(customerData[0]),Integer.parseInt(customerData[1]),bool);
					}
	    	    customerList.add(customer);
			}
			catch(NumberFormatException nfe)
				{
				System.err.println("Customer data was unable to be processed. Customer turned away");
				}

	        //System.out.println(customer.getEventTime());
	        }//all customers added to LinkedList of all customers
		fileScanner.close();
	     }//end try
	     catch (FileNotFoundException e)
	     	{
	        System.err.println("Data file not found.");
	        }
	     catch (Exception e)
	     	{
	        System.err.println("A mysterious error occurred.");
	        e.printStackTrace(System.err);
        	}
	for(int i=0; i<numberOfTellers; i++)
		{
		this.tellerArray[i]=new Teller();
		this.tellerLines.add(new LinkedList<Customer>());
		}//tellers on duty
}//end constructor

/**
 * This method runs the entire bank given everything that was instantiated by the constructor for this class
 */
public void openBank()
{
	while(customerList.peek()!=null)
		{
		//System.out.println("Size is "+customerList.size()+" and customer event time is "+customerList.peek().getEventTime());
		int shortest=0;
		if(customerList.peek().getWasHelped()==false)//customer arriving and put in drive in line or shortest teller line
			{
				this.globalTime=customerList.peek().getEventTime();
				if(customerList.peek().getIsWalkIn()==false)
					{
					driveIn.add(customerList.poll());
					}
				else
					{
						for(int i=0; i<tellerLines.size(); i++)
							{
							if(tellerLines.get(i).size()<tellerLines.get(shortest).size())
								{
								shortest=i;
								}
							}
						customerList.peek().setTellerNumber(shortest);
						tellerLines.get(shortest).add(customerList.poll());
					}
			}
		else//customer is leaving, thus find teller helping them, set teller freeeee
			{
				this.globalTime=customerList.peek().getEventTime();
				tellerArray[customerList.peek().getTellerNumber()].setIsBusy(false);
				//System.out.println("Teller "+customerList.peek().getTellerNumber()+" is freeeeeeee!!!!!!!!");
				tellerArray[customerList.peek().getTellerNumber()].setStartIdleTime(this.globalTime);
				if(customerList.peek().getIsWalkIn())
					{
						tellerLines.get(customerList.poll().getTellerNumber()).remove(0);
					}
				else
					{
					customerList.poll();
					driveIn.remove(0);
					}
			}

		for(int j=0; j<tellerArray.length; j++)
			{
			if(tellerArray[j].getIsBusy()==false)
				{
				//System.out.println("At global time "+this.globalTime+" teller "+j+" wasn't busy.");
				if(driveIn.size()!=0 && driveIn.get(0).getWasHelped()==false)
					{
						//System.out.println("At global time "+this.globalTime+" teller "+j+" helped at drive-in. Would you like fries with that?");
						if(this.globalTime-tellerArray[j].getStartIdleTime()!=0)
							{
							this.tellerIdlenessArray.add(this.globalTime-tellerArray[j].getStartIdleTime());
							tellerArray[j].setTotalIdleTime(this.globalTime-tellerArray[j].getStartIdleTime());
							}//push of time into idleness array and add time to totalidletime for teller
						driveIn.get(0).setTellerNumber(j);
						tellerArray[j].setIsBusy(true);
						tellerArray[j].incrementNumberOfCustomers();
						this.numberOfCustomersHelped++;
						driveIn.get(0).setEventTime(this.globalTime+driveIn.get(0).getTransactionTime());
						driveIn.get(0).setWasHelped(true);
						if((this.globalTime-driveIn.get(0).getArrivalTime())!=0)
							{
							this.waitingTimesArray.add((this.globalTime-driveIn.get(0).getArrivalTime()));
							}
						//System.out.println("At global time "+this.globalTime+" customer was drive-in at empty teller. Event time set to leave time which is: "+(this.globalTime+driveIn.get(0).getTransactionTime()));
						customerList.add(driveIn.get(0));
						//calculate leave time, set event time to leave time, and add back to priority queue
					}
				else//help own line
					{
					if(tellerLines.get(j).size()!=0)
						{
						//System.out.println("At global time "+this.globalTime+" Teller "+j+" helping a walk-in with her walker. Poor old lady.");
						if(this.globalTime-tellerArray[j].getStartIdleTime()!=0)
							{
							this.tellerIdlenessArray.add(this.globalTime-tellerArray[j].getStartIdleTime());
							tellerArray[j].setTotalIdleTime(this.globalTime-tellerArray[j].getStartIdleTime());
						}//push of time into idleness array and add time to totalidletime for teller
						this.tellerLines.get(j).get(0).setTellerNumber(j);
						tellerArray[j].setIsBusy(true);
						tellerArray[j].incrementNumberOfCustomers();
						this.numberOfCustomersHelped++;
						this.tellerLines.get(j).get(0).setEventTime(this.globalTime+this.tellerLines.get(j).get(0).getTransactionTime());
						this.tellerLines.get(j).get(0).setWasHelped(true);
						if((this.globalTime-this.tellerLines.get(j).get(0).getArrivalTime())!=0)
							{
							this.waitingTimesArray.add((this.globalTime-this.tellerLines.get(j).get(0).getArrivalTime()));
							}
						//System.out.println("At global time "+this.globalTime+" Was walk-in at empty teller. Event time set to leave time which is: "+(this.globalTime+tellerLines.get(j).get(0).getTransactionTime()));
						customerList.add(this.tellerLines.get(j).get(0));
						}
					else//no one in own line or drive in, start idling like Buster
						{
						//still idling
						//System.out.println("At global time "+this.globalTime+" Teller "+j+" idling.");
						}
					}
				}
			}
		}//no more customers when finished
	//push off all tellers left idling
	for(int k=0;k<tellerArray.length;k++)
		{
		if(this.globalTime-tellerArray[k].getStartIdleTime()!=0)
			{
			this.tellerIdlenessArray.add(this.globalTime-tellerArray[k].getStartIdleTime());
			tellerArray[k].setTotalIdleTime(this.globalTime-tellerArray[k].getStartIdleTime());
			}
		}
}//bank is done, over, finished, GONE (except not)

/**
 * This method returns the global time.
 * @return the current global time.
 */
public long getGlobalTime()
	{
	return this.globalTime;
	}

/**
 * This method returns the total number of customers helped.
 * @return the current number of total customers helped.
 */
public int getNumberOfCustomersHelped()
	{
	return this.numberOfCustomersHelped;
	}

/**
 * This method returns the number of customers the given teller helped.
 * @param tellerNumber an int of the identification number of the teller
 * @return the current total number of customers helped by the given teller.
 */
public int getTellerNumberOfCustomersHelped(int tellerNumber)
	{
	if (tellerNumber<0 || tellerNumber>=this.tellerArray.length)
		{
		throw new IllegalArgumentException("Teller does not exist. What bank are you at?");
		}
	return this.tellerArray[tellerNumber].getNumberOfCustomers();
	}

/**
 * This method returns the average time a customer had to wait given the wait times of all customers.
 * @return the average wait time for a customer.
 */
public double getAverageCustomerWaitingTime()
	{
	double sum=0;
	for(int z=0; z<this.waitingTimesArray.size(); z++)
		{
		sum=sum+waitingTimesArray.get(z);
		}
	int rounded=(int)(sum*100/this.getNumberOfCustomersHelped());
	return (double)rounded/100;
//	return sum/(this.getNumberOfCustomersHelped());
	}

/**
 * This method returns the percentage of total time that the teller was idle for a specified teller.
 * @param tellerNumber an int of the identification number of the teller
 * @return the percentage of total time that the teller was idle.
 */
public double getPercentIdlenessForTeller(int tellerNumber)
	{
	if (tellerNumber<0 || tellerNumber>=this.tellerArray.length)
		{
		throw new IllegalArgumentException("Teller does not exist. What bank are you at?");
		}
	double toRound= 100*((double)this.tellerArray[tellerNumber].getTotalIdleTime()/(double)this.globalTime);

    return Double.valueOf(df.format(toRound));

	}

/**
 * This method returns the standard deviation of the sample of all teller idle times.
 * @return the standard deviation of teller idle times.
 */
public double getStandardDeviationOfIdleTellers()
	{
	//standard deviation=((summation of observation-mean)^2/total observations-1)^1/2
	double sum=0;
	double mean=0;
	if(tellerIdlenessArray.size()<=1)
		{
		return 0.0;
		}
	for(int b=0; b<tellerIdlenessArray.size(); b++)
		{
		mean=mean+tellerIdlenessArray.get(b);
		}
	mean=mean/tellerIdlenessArray.size();
	for(int b=0; b<tellerIdlenessArray.size(); b++)
		{
		sum=sum+((tellerIdlenessArray.get(b)-mean)*(tellerIdlenessArray.get(b)-mean));
		}
	sum=sum/(tellerIdlenessArray.size()-1);
	sum=Math.sqrt(sum);
	int rounded=(int)(sum*100);

	return (double)rounded/100;
	}

/**
 * This method returns the standard deviation of the sample of all customer wait times.
 * @return the standard deviation of customer wait times.
 */
public double getStandarDeviationOfWaitTime()
	{
	if(waitingTimesArray.size()<=1)
		{
		return 0.0;
		}
	double sum=0;
	double mean=this.getAverageCustomerWaitingTime();
	for(int b=0; b<waitingTimesArray.size(); b++)
		{
		sum=sum+((waitingTimesArray.get(b)-mean)*(waitingTimesArray.get(b)-mean));
		}
	sum=sum/(waitingTimesArray.size()-1);
	sum=Math.sqrt(sum);
	int rounded=(int)(sum*100);
	return (double)rounded/100;
	}

}//end class