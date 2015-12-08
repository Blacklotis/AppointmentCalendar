package com.appointmentcalendar;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import com.calendar.Event;
import java.util.ArrayList;
import static android.widget.Toast.*;

public class EventAddFragment extends Fragment implements View.OnClickListener {

    private EventAddFragmentListener activityCallback;
    private ArrayList<Event> adapter;
    private Event event;
    private static final String TAG = "EVENT_EDIT_FRAG_TAG";
    EditText eventTitle;
    EditText eventOwner;
    EditText eventLocation;
    EditText eventStartTime;
    EditText eventEndTime;
    EditText eventDuration;
    EditText eventDay;
    EditText eventMonth;
    EditText eventYear;

    public interface EventAddFragmentListener {
        void eventAdd_addEvent(Event event);
    }

    public void eventAdd_addEvent(Event event)
    {
        activityCallback.eventAdd_addEvent(event);
    }


    public static EventAddFragment newInstance (ArrayList<Event> eventList){
        EventAddFragment mf = new EventAddFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(TAG, eventList);
        mf.setArguments(bundle);
        return mf;
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {
        boolean correct = true;

        int dayCheck = -1;
        int monthCheck = -1;
        int yearCheck = -1;

        if(eventDay.getText().length() == 0)
        {
            eventDay.setBackgroundColor(android.graphics.Color.RED);
            correct = false;
        }
        else if ( dayCheck <= 0 || dayCheck > 30 )
        {
            eventDay.setBackgroundColor(android.graphics.Color.RED);
            correct = false;
        }
        else
        {
            dayCheck = Integer.parseInt(eventDay.getText().toString());
        }

        if(eventMonth.getText().length() == 0)
        {
            eventMonth.setBackgroundColor(android.graphics.Color.RED);
            correct = false;
        }
        else if( monthCheck < 1 || monthCheck > 12 )
        {
            eventMonth.setBackgroundColor(android.graphics.Color.RED);
            correct = false;
        }
        else
        {
            monthCheck = Integer.parseInt(eventMonth.getText().toString());
        }

        if(eventYear.getText().length() == 0)
        {
            eventYear.setBackgroundColor(android.graphics.Color.RED);
            correct = false;
        }
        else if( yearCheck <= 2015 || yearCheck > 3000 )
        {
            eventYear.setBackgroundColor(android.graphics.Color.RED);
            correct = false;
        }
        else
        {
            yearCheck = Integer.parseInt(eventYear.getText().toString());
        }

        if(eventTitle.getText().length() == 0)
        {
            eventTitle.setBackgroundColor(android.graphics.Color.RED);
            correct = false;
        }
        if (eventOwner.getText().length() == 0) {
            eventOwner.setBackgroundColor(android.graphics.Color.RED);
            correct = false;
        }

        if(correct)
        {
            event.setTitle(eventTitle.getText().toString());
            event.setOwner(eventOwner.getText().toString());
            event.setLocation(eventLocation.getText().toString());
            event.setStartTime(eventStartTime.getText().toString());
            event.setEndTime(eventEndTime.getText().toString());
            event.setDuration(eventDuration.getText().toString());
            event.setDay(Integer.parseInt(eventDay.getText().toString()));
            event.setMonth(Integer.parseInt(eventMonth.getText().toString()));
            event.setYear(Integer.parseInt(eventYear.getText().toString()));
            hideKeyboard(getActivity());
            eventAdd_addEvent(event);
        }
        else
        {
            makeText(getActivity(), "FIX INVALID ENTRIES", LENGTH_SHORT).show();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.event_add_fragment,container, false);
        eventTitle = (EditText)view.findViewById(R.id.event_title);
        eventOwner = (EditText)view.findViewById(R.id.event_owner);
        eventLocation = (EditText)view.findViewById(R.id.event_location);
        eventStartTime = (EditText)view.findViewById(R.id.event_start_time);
        eventEndTime = (EditText)view.findViewById(R.id.event_end_time);
        eventDuration = (EditText)view.findViewById(R.id.event_duration);
        eventDay = (EditText)view.findViewById(R.id.event_day);
        eventMonth = (EditText)view.findViewById(R.id.event_month);
        eventYear = (EditText)view.findViewById(R.id.event_year);

        Button submitButton = (Button)view.findViewById(R.id.event_button);
        submitButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        Bundle b = this.getArguments();
        if(b != null)
        {
            ArrayList<String> temp = new ArrayList<>();
            adapter = b.getParcelableArrayList(TAG);
            event = adapter.get(0);
        }
        else
        {
            event = new Event();
            makeText(getActivity(), "FAILED TO ATTACH FRAGMENT", LENGTH_SHORT).show();
        }

        try
        {
            activityCallback = (EventAddFragmentListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement ToolbarListener");
        }
    }
}