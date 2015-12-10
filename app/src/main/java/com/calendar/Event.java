package com.calendar;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable{
	private long eventID;
	private long calendarID;
	private int day;
	private int month;
	private int year;
	private String owner;
	private String title;
	private String location;
	private String date;
	private String startTime;
	private String endTime;
	private String duration;

	public Event()
	{
		this.eventID = -1;
		this.calendarID = -1;
		this.day = -1;
		this.month = -1;
		this.year = -1;
		this.owner = "";
		this.title = "";
		this.location = "";
		this.date = "";
		this.startTime = "";
		this.endTime = "";
		this.duration = "";
	}
	public Event(long newEventID)
	{
		this.eventID = newEventID;
		calendarID = -1;
		this.day = -1;
		this.month = -1;
		this.year = -1;
		this.owner = "";
		this.title = "";
		this.location = "";
		this.date = "";
		this.startTime = "";
		this.endTime = "";
		this.duration = "";
	}
	public Event(int newDay, int newMonth, int newYear)
	{
		eventID = -1;
		calendarID = -1;
		this.day = newDay;
		this.month = newMonth;
		this.year = newYear;
		this.owner = "";
		this.title = "";
		this.location = "";
		this.date = "";
		this.startTime = "";
		this.endTime = "";
		this.duration = "";
	}
	/*Constructor that takes a cursor filled with event information*/
	public Event(Cursor c)
	{
		this.eventID = c.getInt(c.getColumnIndexOrThrow("_id"));
		this.calendarID = c.getLong(c.getColumnIndexOrThrow("_calendarid"));
		this.owner = c.getString(c.getColumnIndexOrThrow("owner"));
		this.title = c.getString(c.getColumnIndexOrThrow("title"));
		this.location = c.getString(c.getColumnIndex("location"));
		this.date = c.getString(c.getColumnIndexOrThrow("date"));
		this.startTime = c.getString(c.getColumnIndexOrThrow("start_time"));
		this.endTime = c.getString(c.getColumnIndexOrThrow("end_time"));
		this.duration = c.getString(c.getColumnIndexOrThrow("duration"));
		this.day = c.getInt(c.getColumnIndexOrThrow("day"));
		this.month = c.getInt(c.getColumnIndexOrThrow("month"));
		this.year = c.getInt(c.getColumnIndexOrThrow("year"));
	}

    public Event(Parcel in) {

        this.eventID = in.readLong();
        this.calendarID = in.readLong();
        this.day = in.readInt();
        this.month = in.readInt();
        this.year = in.readInt();
        this.owner = in.readString();
        this.title = in.readString();
        this.location = in.readString();
        this.date = in.readString();
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.duration = in.readString();
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(eventID);
        dest.writeLong(calendarID);
        dest.writeInt(day);
        dest.writeInt(month);
        dest.writeInt(year);
        dest.writeString(owner);
        dest.writeString(title);
        dest.writeString(location);
        dest.writeString(date);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(duration);
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventID=" + eventID +
                ", calendarID=" + calendarID +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", owner='" + owner + '\'' +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }

    public String[] toStringArray() {
        return new String[] {owner, title, location, date, startTime, endTime, duration};

    }

    public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
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

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * @return the eventID
	 */
	public long getEventID() {
		return eventID;
	}
	
	/**
	 * @paran eventid the eventID to set
	 */
	public void setEventID(long eventid) {
		this.eventID = eventid;
	}

}
