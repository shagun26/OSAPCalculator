package listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Application Lifecycle Listener implementation class MaxPrincipal
 *
 */
@WebListener
public class MaxPrincipal implements HttpSessionAttributeListener {

	
	private static final String MAX_PRINCIPAL = "max_principal";
	private static double currentMax = 0;
    /**
     * Default constructor. 
     */
    public MaxPrincipal() {
        // TODO Auto-generated constructor stub
    	
    }

	/**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    	checkMax(arg0);

    }

	/**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    	checkMax(arg0);
    }
    
    public void checkMax(HttpSessionBindingEvent arg0)
    {
    	if (arg0.getName().equals("new_principal"))
    	{
    		try {
    		double val = Double.parseDouble((String)arg0.getValue());
    		if (val > currentMax)
    		{
    			currentMax = val;
    		}
    		}
    		catch (Exception e)
    		{}
    	}
    }
    
    public static double getMax()
    {
    	return currentMax;
    }
	
}
