package ctrl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Loan;

/**
 * Servlet implementation class Start
 */
@WebServlet({ "/Start", "/Startup", "/Startup/*" })
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String MONTHLY_PAYMENT = "payment";
	private static final String GRACE_INTEREST = "graceInterest";
	private static final String LOAN_HANDLER = "loan";
	private static final String ERROR = "error";
	private static final String OLD_PRINCIPAL = "old_principal";
	private static final String OLD_INTEREST = "old_interest";
	private static final String OLD_PERIOD = "old_period";
	private static final String NEW_PRINCIPAL = "new_principal";
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Start() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init() throws ServletException 
    {
		this.getServletContext().setAttribute(LOAN_HANDLER, new Loan());
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		System.out.println("Hello, Got a GET request!");
		
		Loan loan = (Loan)(this.getServletContext().getAttribute(LOAN_HANDLER));
		
		//Forward to UI to get values.
		if (request.getParameter("calculate") == null)
		{
		request.getRequestDispatcher("/UI.jspx").forward(request, response);
		}
		

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		
		Loan loan = (Loan)(this.getServletContext().getAttribute(LOAN_HANDLER));
		this.getServletContext().setAttribute(ERROR, null);
		// Forward to UI if values unsubmitted
		if (request.getParameter("calculate") == null)
		{
		request.getRequestDispatcher("/UI.jspx").forward(request, response);
		}
		String interest = request.getParameter("interest");
		String principal = request.getParameter("principal");
		String period = request.getParameter("period");
		
		//MAKE UI.JSPX REFER TO THESE
	//////SET THE BUTTON TO CHECKED.
		request.getSession().setAttribute(OLD_INTEREST, interest);
		request.getSession().setAttribute(OLD_PERIOD, period);
		request.getSession().setAttribute(OLD_PRINCIPAL, principal);
		request.getSession().setAttribute(NEW_PRINCIPAL, principal);
		request.getSession().setAttribute(NEW_PRINCIPAL, "0.0");
		
		int gracePeriod = Integer.parseInt(this.getServletContext().getInitParameter("gracePeriod"));
		double fixed = Double.parseDouble(this.getServletContext().getInitParameter("fixedInterest")) / 100;
		
		boolean grace = (request.getParameter("graceperiod") != null);
		double payment = 0;
		double graceInterest = 0;
		try
		{
			payment = loan.calculatePayment(principal, period, interest, grace, gracePeriod, fixed);
			graceInterest = loan.computeGraceInterest(principal, gracePeriod, interest, fixed);
		} 
		catch (Exception e) 
		{
			request.setAttribute(ERROR, e.getMessage());
		}
		
		request.setAttribute(MONTHLY_PAYMENT, payment);
		if (grace)
		{
			request.setAttribute(GRACE_INTEREST, graceInterest);
		}
		else
		{
			request.setAttribute(GRACE_INTEREST, 0);
		}
		
		
		
		if (request.getAttribute(ERROR) == null)
		{
			request.getRequestDispatcher("/showResult.jspx").forward(request, response);
		}
		else
		{
			request.getRequestDispatcher("/UI.jspx").forward(request,  response);
		}

	}
	
	
	



}
