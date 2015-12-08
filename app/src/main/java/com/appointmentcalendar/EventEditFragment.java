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

public class EventEditFragment extends Fragment implements View.OnClickListener {

    private EventEditFragmentListener activityCallback;
    private ArrayList<Event> adapter;
    private Event event;
    private static final String TAG = "EVENT_EDIT_FRAG_TAG";
    EditText eventTitle;
    EditText eventOwner;
    EditText eventLocation;
    EditText eventStartTime;
    EditText eventEndTime;
    EditText eventDuration;

    public interface EventEditFragmentListener {
        void eventEdit_addEvent(Event event);
        void eventEdit_editEvent(ArrayList<Event> event);
    }

    public void eventEdit_addEvent(Event event)
    {
        activityCallback.eventEdit_addEvent(event);
    }
    public void eventEdit_editEvent(ArrayList<Event> event)
    {
        activityCallback.eventEdit_editEvent(event);
    }

    public static EventEditFragment newInstance (ArrayList<Event> eventList){
        EventEditFragment mf = new EventEditFragment();
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
            ArrayList<Event> stuff = new ArrayList<>();
            stuff.add(event);
            hideKeyboard(getActivity());
            eventEdit_editEvent(stuff);
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
        View view = inflater.inflate(R.layout.event_edit_fragment,container, false);
        eventTitle = (EditText)view.findViewById(R.id.event_title);
        eventOwner = (EditText)view.findViewById(R.id.event_owner);
        eventLocation = (EditText)view.findViewById(R.id.event_location);
        eventStartTime = (EditText)view.findViewById(R.id.event_start_time);
        eventEndTime = (EditText)view.findViewById(R.id.event_end_time);
        eventDuration = (EditText)view.findViewById(R.id.event_duration);

        eventTitle.setText(event.getTitle());
        eventOwner.setText(event.getOwner());
        eventLocation.setText(event.getLocation());
        eventStartTime.setText(event.getStartTime());
        eventEndTime.setText(event.getEndTime());
        eventDuration.setText(event.getDuration());

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
            activityCallback = (EventEditFragmentListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement ToolbarListener");
        }
    }
}