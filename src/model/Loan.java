package model;

import javax.servlet.http.HttpServletRequest;

public class Loan
{
	
	
	public Loan()
	{
	}
	
	public double calculatePayment(String principal, String period, String interest, boolean grace, int gracePeriod, double fixed) throws Exception
	{
		StringBuilder eb = new StringBuilder("");
		double d_principal = 0;
		double d_interest = 0;
		int i_period = 0;
		try 
		{
			d_principal = Double.parseDouble(principal);
		}
		catch (Exception e)
		{
			eb.append("Please provide a principal.\n");
		}
		try 
		{
			d_interest = Double.parseDouble(interest) / 100;
		}
		catch (Exception e)
		{
			eb.append("Please provide an interest.\n");
		}
		try 
		{
			i_period = Integer.parseInt(period);
		}
		catch (Exception e)
		{
			eb.append("Please provide a period.\n");
		}
		if (d_interest < 0)
		{
			eb.append("Interest must be non-negative.\n");
		}
		if (i_period < 0)
		{
			eb.append("Period must be non-negative.\n");
		}
		if (d_principal < 0)
		{
			eb.append("Principal must be non-negative.\n");
		}
		if (!eb.toString().equals(""))
		{
			throw new Exception(eb.toString());
		}
		double payment;
		double total = d_interest + fixed;
		payment = (total / 12) * d_principal / (1 - Math.pow((1 + (total/12)), (-1 * i_period))  );
		if (grace)
		{
			double graceInterest = computeGraceInterest(principal, gracePeriod, interest, fixed);
			payment += (graceInterest/gracePeriod);
		}
		return roundTwoDecimal(payment);
	}
	
	public double computeGraceInterest(String principal, int gracePeriod, String interest, double fixed) throws Exception
	{
		double d_interest;
		double d_principal;
		try 
		{
			d_interest = Double.parseDouble(interest) / 100;
			d_principal = Double.parseDouble(principal);
		}
		catch (Exception e)
		{
			throw new Exception();
		}
		
		double total = d_interest + fixed;
		return roundTwoDecimal((d_principal * ((total) / 12) * gracePeriod));	
	}
	
	private static double roundTwoDecimal(double value)
	{
		value = Math.round(value*100);
		value /= 100;
		return value;
	}
	
	
}