package com.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLHelper extends SQLiteOpenHelper
{
	protected static final String DATABASE_NAME = "AC";
	private final static int DATABASE_VERSION = 1;

	public static final String TABLE_CALENDAR = "calendar";
	public static final String TABLE_EVENT = "event";

	/*Calendar table*/
	public static final String COLUMN_CALENDARID = "_calendarid";
	public static final String COLUMN_OWNER = "owner";
	public final static String[] CALENDAR_COLUMNS = {COLUMN_CALENDARID, COLUMN_OWNER};
	
	/*Event table*/
	public static final String COLUMN_EVENTID = "_id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_LOCATION = "location";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_STARTTIME = "start_time";
	public static final String COLUMN_ENDTIME = "end_time";
	public static final String COLUMN_DURATION = "duration";
	public static final String COLUMN_DAY = "day";
	public static final String COLUMN_MONTH = "month";
	public static final String COLUMN_YEAR = "year";
	public final static String[] EVENT_COLUMNS = {	COLUMN_EVENTID, COLUMN_CALENDARID, COLUMN_OWNER, 
													COLUMN_TITLE, COLUMN_LOCATION, COLUMN_DATE, 
													COLUMN_STARTTIME, COLUMN_ENDTIME,COLUMN_DURATION,
													COLUMN_DAY, COLUMN_MONTH, COLUMN_YEAR };

	// Database creation sql statements
	private final String CREATE_CALENDAR_TABLE = "create table " + TABLE_CALENDAR + "(" + CALENDAR_COLUMNS[0]
			+ " integer primary key autoincrement, " + CALENDAR_COLUMNS[1] + " text not null);";

	private final String CREATE_EVENT_TABLE = "create table " + TABLE_EVENT + "(" + COLUMN_EVENTID
			+ " integer primary key autoincrement, " + COLUMN_CALENDARID + " integer, "+
			COLUMN_OWNER + " text not null, " + COLUMN_TITLE + " text not null, " +
			COLUMN_LOCATION + " text, " + COLUMN_DATE + " text, " + COLUMN_STARTTIME + " text, " +
			COLUMN_ENDTIME + " text, " + COLUMN_DURATION + " text, " + COLUMN_DAY + " integer, " +
			COLUMN_MONTH + " integer, " + COLUMN_YEAR + " integer);";

	public SQLHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database)
	{
		database.execSQL(CREATE_CALENDAR_TABLE);
		database.execSQL(CREATE_EVENT_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(SQLHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion
				+ ", which will destroy all old data");

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALENDAR);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
		onCreate(db);
	}

	public String getCalendarTable()
	{
		return TABLE_CALENDAR;
	}
	public String getEventTable()
	{
		return TABLE_EVENT;
	}
}
