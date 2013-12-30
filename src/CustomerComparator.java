import java.util.Comparator;
/**
 * @authors Braden Ericson and Nicole Lopez
 *
 * This class holds the necessary information for our Customer Comparator utilized in Sawin International.
 *
 */
public class CustomerComparator implements Comparator<Customer>{

/**
 * This method compares the two customer objects and sorts them according to first time, then leaving or coming, and then drive thru priority
 * @return an int of the compare status
 */
public int compare(Customer c1, Customer c2)
	{
	if(c1.getEventTime()==c2.getEventTime())
		{
			if(c1.getWasHelped()==true)
				{
				return -1;
				}
			else//c1 arriving
				{
					if(c2.getWasHelped()==true)
						{
						return 1;
						}
					else//c1 and c2 both arriving
						{
							if(c1.getIsWalkIn()==true)
								{
								return 1;
								}
							else//c1 is driving and should have priority
								{
								return -1;
								}
						}
				}

		}
	else if(c1.getEventTime()<c2.getEventTime())
		{
		return -1;
		}
	return 1;
	}

}//end class