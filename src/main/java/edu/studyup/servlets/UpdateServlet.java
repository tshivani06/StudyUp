package edu.studyup.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.studyup.entity.Event;
import edu.studyup.entity.Student;
import edu.studyup.serviceImpl.EventServiceImpl;

public class UpdateServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			updateEvent(request);
			response.getWriter().write("Success!");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write(e.getMessage());
		}
	}
	private void updateEvent(HttpServletRequest request) {
		EventServiceImpl impl = new EventServiceImpl();
		long eventId = Long.parseLong(request.getParameter("eventId"));
		boolean isNew = eventId == 0;
		Event event = new Event();
		if (!isNew) {
			event.setEventID(eventId);
			event = impl.getEvent(eventId);
			String[] attendees = request.getParameterValues("eventAttendees[]"); // For existing events, you can only add an attendee
			if (attendees.length > event.getStudents().size()) {
				Student student = createStudent(attendees[event.getStudents().size()]);
				event.getStudents().add(student);
			}
			impl.updateEvent(event, eventId);
		}
		else {
			event.setName(request.getParameter("eventName"));
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
				event.setDate(simpleDateFormat.parse(request.getParameter("eventDate")));
			} catch (ParseException e) {
				System.err.println(e + "\t" + request.getParameter("eventDate"));
			}
			List<Student> students = new ArrayList<Student>();
			for (String attendee : request.getParameterValues("eventAttendees[]")) {
				students.add(createStudent(attendee));
			}
			event.setStudents(students);
			impl.createEvent(event);
		}
	}
	
	private Student createStudent(String attendee) {
		Student student = new Student();
		if (attendee.contains(" ")) {
			student.setFirstName(attendee.substring(0, attendee.indexOf(" ")));
			student.setLastName(attendee.substring(attendee.indexOf(" ") + 1));
		}
		else {
			student.setFirstName(attendee);
		}
		return student;
	}
}
