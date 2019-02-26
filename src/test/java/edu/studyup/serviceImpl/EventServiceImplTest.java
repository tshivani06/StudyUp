package edu.studyup.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.studyup.entity.Event;
import edu.studyup.entity.Location;
import edu.studyup.entity.Student;
import edu.studyup.util.DataStorage;
import edu.studyup.util.StudyUpException;
import redis.clients.jedis.Jedis;

class EventServiceImplTest {

	EventServiceImpl eventServiceImpl;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		eventServiceImpl = new EventServiceImpl();
		// Create Student
		Student student = new Student();
		student.setFirstName("John");
		student.setLastName("Doe");
		student.setEmail("JohnDoe@email.com");
		student.setId(1);

		// Create Event1
		Event event = new Event();
		event.setDate(new Date());
		event.setName("Event 1");
		Location location = new Location(-122, 37);
		event.setLocation(location);
		List<Student> eventStudents = new ArrayList<>();
		eventStudents.add(student);
		event.setStudents(eventStudents);

		eventServiceImpl.createEvent(event);
	}

	@AfterEach
	void tearDown() throws Exception {
		eventServiceImpl.deleteAll();
	}

	@Test
	void testGetEvent_GoodCase() throws StudyUpException {
		assertEquals("Event 1",eventServiceImpl.getEventFromKey("1").getName());
	}
	
	@Test
	void testGetAllEvent_GoodCase() throws StudyUpException {
		List<Event> events = eventServiceImpl.getAllEvents();
		assertEquals("Event 1",events.get(0).getName());
	}


}
