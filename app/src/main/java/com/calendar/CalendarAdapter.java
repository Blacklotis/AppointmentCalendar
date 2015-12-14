package com.calendar;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appointmentcalendar.R;

import java.util.Arrays;

public class CalendarAdapter {

    private Calendar[] calendars;

    public CalendarAdapter()
    {
        calendars = new Calendar[100];
        Arrays.fill(calendars, null);
    }
    public Calendar getCalendar(int calendarID)
    {
        for(int i = 0; i < 100; ++i)
        {
            if(calendars[i].getCalendarID() == calendarID)
            {
                return calendars[i];
            }
        }
        return null;
    }

    public boolean setCalendar(Calendar newCalendar)
    {
        if(calendars[(int)newCalendar.getCalendarID()] == null)
        {
            calendars[(int)newCalendar.getCalendarID()] = newCalendar;
            return true;
        }
        return false;
    }

    public boolean syncCalendars(int calendarID)
    {
        return true;
    }
    public long syncCalendars(int calendarID, Cursor c)
    {
        long i = 1;
        c.moveToFirst();
        // ASSUMPTIONS: Cursor points to a valid database
        Calendar chosen = getCalendar(calendarID);

        chosen.clearEvents();
        while(!c.isAfterLast()) {
            if(chosen.getCalendarID() == c.getLong(c.getColumnIndexOrThrow("_calendarID")))
            {
                Event newEvent = new Event(c);
                chosen.addEvent(newEvent);
                ++i;
            }
            c.moveToNext();
        }

        return i;
    }
}
