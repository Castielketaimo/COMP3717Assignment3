package ca.bcit.ass3.li_leung.models;

/**
 * Event Model for the Event_Master table.
 *
 * @author Castiel Li & Justin Leung
 */
public class Event
{
    /** Event ID. */
    private int _eventId;

    /** Event name. */
    private String _Name;

    /** Event date. */
    private String _Date;

    /** Event time. */
    private String _Time;


    /**
     * Constructor for event instance.
     *
     * @param name - event name
     * @param date - event date
     * @param time - event time
     */
    public Event(String name, String date, String time)
    {
        _Name = name;
        _Date = date;
        _Time = time;
    }

    /**
     * Constructor for event instance, with event ID.
     *
     * @param name - event name
     * @param date - event date
     * @param time - event time
     */
    public Event(String name, String date, String time, int id)
    {
        _Name = name;
        _Date = date;
        _Time = time;
        _eventId = id;
    }

    /**
     * Returns event id.
     *
     * @return _eventId - events id.
     */
    public int getId()
    {
        return _eventId;
    }

    /**
     * Returns event name.
     *
     * @return _Name - events name.
     */
    public String getName()
    {
        return _Name;
    }

    /**
     * Returns event date.
     *
     * @return _Date - events date.
     */
    public String getDate()
    {
        return _Date;
    }

    /**
     * Returns event time.
     *
     * @return _Time - events time.
     */
    public String getTime()
    {
        return _Time;
    }

    /**
     * Sets event id.
     */
    public void setId(int eventId)
    {
        _eventId = eventId;
    }

    /**
     * Returns event name.
     */
    public void setName(String Name)
    {
        _Name = Name;
    }

    /**
     * Returns event date.
     */
    public void setDate(String Date)
    {
        _Date = Date;
    }

    /**
     * Set event time.
     */
    public void setTime(String Time)
    {
        _Time = Time;
    }

    /**
     * Returns event name.
     *
     * @return _Name - events name.
     */
    public String toString()
    {
        return _Name;
    }
}
