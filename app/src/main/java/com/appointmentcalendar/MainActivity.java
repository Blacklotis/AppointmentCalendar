package com.appointmentcalendar;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.calendar.Calendar;
import com.calendar.CalendarAdapter;
import com.calendar.Event;
import com.database.DatabaseAdapter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import static android.widget.Toast.*;
import com.tools.DateFormat;

public class MainActivity extends ActionBarActivity implements Serializable,
                                                    CalendarFragment.CalendarFragmentListener,
                                                    EventFragment.EventFragmentListener,
                                                    EventEditFragment.EventEditFragmentListener,
                                                    EventAddFragment.EventAddFragmentListener{

    private CalendarView calendarFragmentView;
    private DatabaseAdapter dbAdapter;
    private CalendarAdapter calAdapter;
    private LinearLayout calendarFragContainer;
    private LinearLayout eventFragContainer;
    private Calendar ourCal;
    private ArrayList<Event> dailyEvents;
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //sets the main layout of the activity
        setContentView(R.layout.activity_main);

        calendarFragmentView = (CalendarView)findViewById(R.id.calendar);

        calendarFragContainer = (LinearLayout)findViewById(R.id.FragmentContainer1);
        calendarFragContainer.setId(R.id.FragmentContainer1);

        eventFragContainer = (LinearLayout)findViewById(R.id.FragmentContainer2);
        eventFragContainer.setId(R.id.FragmentContainer2);

        calAdapter = new CalendarAdapter();
        dbAdapter = new DatabaseAdapter(getApplicationContext());
        dailyEvents = new ArrayList<>();
        ourCal = new Calendar();

        setTestData();
    }

    public void onListItemClick(ListView listView, View view, int position,long id)
    {
        makeText(getApplicationContext(), "CLICKED: " + position, LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_event:
                ArrayList<Event> newEvents = new ArrayList<>();
                Event newEvent = new Event();
                newEvents.add(newEvent);
                setAddFragment(newEvents, false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void eventAdd_addEvent(Event e)
    {
        calAdapter.getCalendar(0).addEvent(e);
        dailyEvents = ourCal.getEvents(e.getDay(), e.getMonth(), e.getYear());
        makeText(getApplicationContext(), "EVENT ADD SUCCESS", LENGTH_SHORT).show();
    }
    public void eventEdit_addEvent(Event e)
    {
        calAdapter.getCalendar(0).addEvent(e);
        dailyEvents = ourCal.getEvents(e.getDay(), e.getMonth(), e.getYear());
        setSecondFragment(dailyEvents, false);
    }
    public void eventEdit_editEvent(ArrayList<Event> events)
    {
        Event e = events.get(0);
        calAdapter.getCalendar(0).deleteEvent(e.getEventID());
        calAdapter.getCalendar(0).addEvent(e);
        dailyEvents = ourCal.getEvents(e.getDay(), e.getMonth(), e.getYear());
        setSecondFragment(dailyEvents, false);
    }



    /* Start Event Fragment Callbacks*/
    public void deleteEvents(ArrayList<Event> events)
    {
        for (Event e : events){
            calAdapter.getCalendar(0).deleteEvent(e.getEventID());
            dailyEvents = ourCal.getEvents(e.getDay(), e.getMonth(), e.getYear());
        }
        setSecondFragment(dailyEvents, false);
    }
    /* End Event Fragment Callbacks*/

    /* Start Calendar Fragment Callbacks*/
    public void onSelectedDayChange(CalendarView view, int year, int month, int day)
    {
        dailyEvents = ourCal.getEvents(day, month + 1, year);
        if (dailyEvents.size() == 0)
        {
            makeText(getApplicationContext(), "NO EVENTS FOUND", LENGTH_SHORT).show();
        }
        else
        {
            setSecondFragment(dailyEvents, true);
        }
    }
    /* End Calendar Fragment Callbacks*/

    //This sets our second fragment with a new fragment.
    //When deleting an item, do not add the fragment with the deleted item to the back stack
    private void setAddFragment(ArrayList<Event> dailyList, boolean addToBackStack) {
        LinearLayout eventFragContainer = (LinearLayout) findViewById(R.id.FragmentContainer2);
        eventFragContainer.setId(R.id.FragmentContainer2);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft;
        ft = fm.beginTransaction();
        Fragment frag = EventAddFragment.newInstance(dailyList);
        if (fm.findFragmentByTag("EVENT_FRAG_TAG") == null)
        {
            ft.add(eventFragContainer.getId(), frag, "EVENT_FRAG_TAG").commit();
        }
        else
        {
            if(addToBackStack)
            {
                ft.addToBackStack("EVENT_FRAG_TAG");
            }
            ft.replace(eventFragContainer.getId(), frag, "EVENT_FRAG_TAG").commit();
        }
        fm.executePendingTransactions();
    }

    //This sets our second fragment with a new fragment.
    //When deleting an item, do not add the fragment with the deleted item to the back stack
    private void setEditFragment(ArrayList<Event> dailyList, boolean addToBackStack) {
        LinearLayout eventFragContainer = (LinearLayout) findViewById(R.id.FragmentContainer2);
        eventFragContainer.setId(R.id.FragmentContainer2);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft;
        ft = fm.beginTransaction();
        Fragment frag = EventEditFragment.newInstance(dailyList);
        if (fm.findFragmentByTag("EVENT_FRAG_TAG") == null)
        {
            ft.add(eventFragContainer.getId(), frag, "EVENT_FRAG_TAG").commit();
        }
        else
        {
            if(addToBackStack)
            {
                ft.addToBackStack("EVENT_FRAG_TAG");
            }
            ft.replace(eventFragContainer.getId(), frag, "EVENT_FRAG_TAG").commit();
        }
        fm.executePendingTransactions();
    }


    //This sets our second fragment with a new fragment.
    //When deleting an item, do not add the fragment with the deleted item to the back stack
    private void setSecondFragment(ArrayList<Event> dailyList, boolean addToBackStack) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        Fragment frag = EventFragment.newInstance(dailyList);
        if (fm.findFragmentByTag("EVENT_FRAG_TAG") == null)
        {
            ft.add(eventFragContainer.getId(), frag, "EVENT_FRAG_TAG").commit();
        }
        else
        {
            if(addToBackStack)
            {
                ft.addToBackStack("EVENT_FRAG_TAG");
            }
            ft.replace(eventFragContainer.getId(), frag, "EVENT_FRAG_TAG").commit();
        }
        fm.executePendingTransactions();
    }

    public void syncData(int calendarID) {
        //Begin database -> calendar sync
        calAdapter.syncCalendars(calendarID, dbAdapter.getEventCursor());

        //Begin calendar -> database sync

        //ASSUMPTIONS: terminates whenever EventID becomes -1.
        Calendar chosen = calAdapter.getCalendar(calendarID);
        int i = 1;
        Event newEvent = chosen.getEvent(i);
        while(newEvent.getEventID() != -1)
        {
            dbAdapter.addEvent(newEvent);
            ++i;
            newEvent = chosen.getEvent(i);
        }
    }

    public void setTestData(){
        Random rnd = new Random();
        dbAdapter.open();
        ourCal.setCalendarID(0);
        ourCal.setOwner("Mark");
        Event today;

        for(int i = 1; i < 90; ++i)
        {
            today = new Event(i);
            today.setDay((i%30));
            today.setMonth(12);
            today.setYear(2015);
            today.setCalendarID(0);
            today.setOwner("Mark");
            today.setDuration("1 hour");
            today.setLocation("My House");
            today.setStartTime("1pm");
            today.setEndTime("2pm");
            today.setTitle("Event: " + rnd.nextInt(100));

            dbAdapter.addEvent(today);
            ourCal.addEvent(today);
        }


        calAdapter.setCalendar(ourCal);
        dbAdapter.addCalendar(ourCal);

        dbAdapter.refresh();
    }
}

