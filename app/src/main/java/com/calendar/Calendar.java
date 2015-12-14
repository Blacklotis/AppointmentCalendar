package com.calendar;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;

public class Calendar {

    private final int MAX_DAILY_EVENTS = 100;
	private ArrayList<Event> events;
	private long calendarID;
	private String owner;
	
	public Calendar()
	{
		calendarID = -1;
		events = new ArrayList<>();
	}

	/*Constructor that takes a cursor filled with event information*/
	public Calendar(Cursor c)
	{
		this.calendarID = c.getLong(c.getColumnIndexOrThrow("_calendarID"));
		this.owner = c.getString(c.getColumnIndexOrThrow("owner"));
	}
	public boolean addEvent(Event newEvent)
	{
		events.add(newEvent);
		return false;
	}
	public Event getEvent(long eventID)
	{
        for(Event e : events)
        {
            if(e.getEventID() == eventID)
                return e;
        }
		return new Event(-1);
	}
	public boolean deleteEvent(long eventID)
	{
		for(Event e : events)
		{
			if(e.getEventID() == eventID)
			{
				events.remove(e);
				return true;
			}
		}
		return false;
	}
	public ArrayList<Event> getEvents(int day, int month, int year)
	{
		ArrayList<Event> eventList = new ArrayList<>();
        for(Event e : events)
        {
            if(e.getDay() == day && e.getMonth() == month && e.getYear() == year)
                eventList.add(e);
        }
		return eventList;
	}
	/**
	 * @return the calendarID
	 */
	public long getCalendarID() {
		return calendarID;
	}

	/**
	 * @param calendarID the calendarID to set
	 */
	public void setCalendarID(long calendarID) {
		this.calendarID = calendarID;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Calendar [calendarID=" + calendarID + ", owner=" + owner + "]";
	}
	
	public void clearEvents() {
		events.clear();
	}
}
