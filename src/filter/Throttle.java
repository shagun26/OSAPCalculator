package filter;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class Throttle
 */

@WebFilter(dispatcherTypes = {DispatcherType.REQUEST }, urlPatterns = {"/Start", "/Startup", "/Startup/*"},
	    servletNames = {"Start"})
public class Throttle implements Filter {
	
	private boolean first = true;
    /**
     * Default constructor. 
     */
    public Throttle() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		if (first)
		{
			first = false;
			chain.doFilter(request, response);
		}
		else if (!first)
		{
		long current = System.currentTimeMillis();
		HttpServletRequest req = (HttpServletRequest)request;
		long previous = req.getSession().getLastAccessedTime();
		if (current - previous < 1000)
		{
			request.getRequestDispatcher("/Throttle.jspx").forward(request, response);
		}
		else 
		{
		// pass the request along the filter chain
		chain.doFilter(request, response);
		}
		}
	/**	else
		{
			System.out.println("TRIVIAL SKIP");
			chain.doFilter(request, response);
		}**/
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
