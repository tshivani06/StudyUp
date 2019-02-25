package edu.studyup.servlets;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.studyup.entity.Location;
import edu.studyup.map.Lookup;

public class SearchServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain");
		String place = request.getParameter("place");
		System.out.println(request.getParameterMap());
		if (place != null && !place.isEmpty()) {
			Location loc = Lookup.lookupPlace(place);
			if (loc != null) {
				double lat = loc.lat;
				double lon = loc.lon;
				double[] bounds = loc.bounds;
				response.getWriter().write(lat + "\t" + lon + "\t" + Arrays.toString(bounds));
			}
		}
	}
}
