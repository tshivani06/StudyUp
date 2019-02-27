package edu.studyup.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;

import edu.studyup.entity.Event;
import edu.studyup.entity.Student;
import edu.studyup.service.EventService;
import edu.studyup.util.DataStorage;
import edu.studyup.util.StudyUpException;
import redis.clients.jedis.Jedis;

public class EventServiceImpl implements EventService {
	
	//ToDO change server.
	Jedis jedis = new Jedis("127.0.0.1", 8888);
	Gson gson = new Gson();

	@Override
	public long deleteEvent(int key) {
		return jedis.del(String.valueOf(key));
	}

	@Override
	public long createEvent(Event event) {
		long id = jedis.incr("key");
		event.setEventID(id);
		String eventString  = gson.toJson(event);
		jedis.set(String.valueOf(id), eventString);
		return id;
	}

	@Override
	public Event updateEvent(Event event, long key) {
		String eventString  = gson.toJson(event);
		jedis.set(String.valueOf(key), eventString);
		return event;
	}

	@Override
	public List<Event> getAllEvents() {
		List<Event> eventList = new ArrayList<>();
		//ToDo: No the optimized way.
		Set<String> keys = jedis.keys("*");
		for (String key: keys) {
			if(!key.equalsIgnoreCase("key")) {
				Event event = getEventFromKey(key);
				eventList.add(event);
			}
		}
		return eventList;
	}

	@Override
	public String deleteAll() {
		return jedis.flushAll();
	}

	@Override
	public Event getEvent(long key) {
		Event event = getEventFromKey(String.valueOf(key));
		return event;
	}
	
	public Event getEventFromKey(String key) {
		String eventString = jedis.get(key);
		Event event = gson.fromJson(eventString, Event.class);
		return event;
	}

}
