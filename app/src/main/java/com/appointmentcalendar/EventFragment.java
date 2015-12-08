package com.appointmentcalendar;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.calendar.Event;
import java.util.ArrayList;
import static android.widget.Toast.*;

public class EventFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private EventFragmentListener activityCallback;
    private ArrayList<Event> adapter;
    private static final String TAG = "EVENT_FRAG_TAG";
    private String[] row_text;

    public interface EventFragmentListener {
        void deleteEvents(ArrayList<Event> event);
    }

    public void deleteEvents(ArrayList<Event> event)
    {
        activityCallback.deleteEvents(event);
    }

    public static EventFragment newInstance(ArrayList<Event> eventList){
        EventFragment mf = new EventFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(TAG, eventList);
        mf.setArguments(bundle);
        return mf;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ArrayList<Event> temp = new ArrayList<>();
        temp.add((adapter.get(position)));
        setSecondFragment(temp, true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        ArrayList<Event> temp = new ArrayList<>();

        switch(item.getItemId()) {
            case R.id.share:
                // add stuff here
                return true;
            case R.id.edit:
                temp.add((adapter.get(info.position)));
                setEditFragment(temp, false);
                return true;
            case R.id.delete:
                temp.add((adapter.get(info.position)));
                deleteEvents(temp);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    //This sets our second fragment with a new fragment.
    //When deleting an item, do not add the fragment with the deleted item to the back stack
    private void setEditFragment(ArrayList<Event> dailyList, boolean addToBackStack) {
        LinearLayout eventFragContainer = (LinearLayout) getActivity().findViewById(R.id.FragmentContainer2);
        eventFragContainer.setId(R.id.FragmentContainer2);
        FragmentManager fm = getActivity().getSupportFragmentManager();
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

    private void setSecondFragment(ArrayList<Event> dailyList, boolean addToBackStack) {
        LinearLayout eventFragContainer = (LinearLayout) getActivity().findViewById(R.id.FragmentContainer2);
        eventFragContainer.setId(R.id.FragmentContainer2);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft;
        ft = fm.beginTransaction();
        Fragment frag = EventDetailsFragment.newInstance(dailyList);
        if(addToBackStack)
        {
            ft.addToBackStack("EVENT_FRAG_TAG");
        }
        ft.replace(eventFragContainer.getId(), frag, "EVENT_DETAILS_FRAG_TAG").commit();
        fm.executePendingTransactions();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        registerForContextMenu(getListView());
        super.onActivityCreated(savedInstanceState);

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
            for (Event e : adapter) {
                temp.add(e.getTitle());
            }
            String[] tempRows = new String[temp.size()];
            row_text = temp.toArray(tempRows);
        }
        else
        {
            row_text = new String[]{"NULL"};
            makeText(getActivity(), "FAILED TO ATTACH FRAGMENT", LENGTH_SHORT).show();
        }
        setListAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, row_text));
        try
        {
            activityCallback = (EventFragmentListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement ToolbarListener");
        }
    }
}