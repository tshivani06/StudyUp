package edu.studyup.service;

import java.util.List;

import edu.studyup.entity.Event;
import edu.studyup.entity.Student;
import edu.studyup.util.StudyUpException;

/**
 * {@code EventService} holds all CRUD services for class {@link Event}
 * 
 * @author Shivani
 *
 */
public interface EventService {
	
	/**
	 * 
	 * 
	 * Service using Redis DataStore
	 * 
	 * 
	 */


	/**
	 * @param key The {@code key} of the specific {@link Event} to delete.
	 * @return 1 if the event is deleted, else 0.
	 */
	public long deleteEvent(int eventID);
	
	
	/**
	 * @param event The {@code event} object to be created.
	 * @return {@code key}, returns the key of the event in the store.
	 */
	public long createEvent(Event event);
	
	/**
	 * @param event The {@code event} object to be updated.
	 * @param key The {@code key} of the event mapped in the store.
	 * @return {@code Event}, returns the event.
	 */
	public Event updateEvent(Event event,  long key);
	
	/**
	 * Fetches all the events from the store.
	 * 
	 * @return The list of all {@code events}.
	 */
	public List<Event> getAllEvents();
	
	public String deleteAll();
	
	public Event getEvent(long key);

}
