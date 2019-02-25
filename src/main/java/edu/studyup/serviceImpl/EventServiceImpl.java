package edu.studyup.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import edu.studyup.entity.Event;
import edu.studyup.entity.Student;
import edu.studyup.service.EventService;
import edu.studyup.util.DataStorage;
import edu.studyup.util.StudyUpException;
import redis.clients.jedis.Jedis;

public class EventServiceImpl implements EventService {
	
	//ToDO change server.
	Jedis jedis = new Jedis("localhost");
	Gson gson = new Gson();

	@Override
	public Event updateEventName(int eventID, String name) throws StudyUpException {
		Event event = DataStorage.eventData.get(eventID);
		if (event == null) {
			throw new StudyUpException("No event found.");
		}

		if (name.length() >= 20) {
			throw new StudyUpException("Length too long. Maximun is 20");
		}
		event.setName(name);
		DataStorage.eventData.put(eventID, event);
		event = DataStorage.eventData.get(event.getEventID());
		return event;
	}

	@Override
	public List<Event> getActiveEvents() {
		Map<Integer, Event> eventData = DataStorage.eventData;
		List<Event> activeEvents = new ArrayList<>();
		for (Integer key : eventData.keySet()) {
			Event ithEvent = eventData.get(key);
			activeEvents.add(ithEvent);
		}
		return activeEvents;
	}

	@Override
	public List<Event> getPastEvents() {
		Map<Integer, Event> eventData = DataStorage.eventData;
		List<Event> pastEvents = new ArrayList<>();

		for (Integer key : eventData.keySet()) {
			Event ithEvent = eventData.get(key);
			// Checks if an event date is before today, if yes, then add to the past event
			// list.
			if (ithEvent.getDate().before(new Date())) {
				pastEvents.add(ithEvent);
			}
		}
		return pastEvents;
	}

	@Override
	public Event addStudentToEvent(Student student, int eventID) throws StudyUpException {
		Event event = DataStorage.eventData.get(eventID);
		if (event == null) {
			throw new StudyUpException("No event found.");
		}
		List<Student> presentStudents = event.getStudents();
		if (presentStudents == null) {
			presentStudents = new ArrayList<>();
		}
		presentStudents.add(student);
		event.setStudents(presentStudents);
		return DataStorage.eventData.put(eventID, event);
	}

	@Override
	public Event deleteEvent(int eventID) {
		return DataStorage.eventData.remove(eventID);
	}

	@Override
	public long createEvent(Event event) {
		long id = jedis.incr("key");
		String eventString  = gson.toJson(event);
		jedis.set(String.valueOf(id), eventString);
		return id;
	}

	@Override
	public Event updateEvent(Event event) {
		long key = event.getEventID();
		String eventString  = gson.toJson(event);
		jedis.set(String.valueOf(key), eventString);
		String value = jedis.get("foo");
		return event;
	}

	@Override
	public List<Event> getAllEvents() {
		// TODO Auto-generated method stub
		return null;
	}

}
