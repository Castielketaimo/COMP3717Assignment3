package ca.bcit.ass3.li_leung.models;

/**
 * Detail Model for the Event_Detail table.
 *
 * @author Castiel Li & Justin Leung
 */
public class Detail
{
    /** Detail ID. */
    private int _detailId;

    /** Detail name. */
    private String _ItemName;

    /** Detail unit. */
    private String _ItemUnit;

    /** Detail quantity. */
    private String _ItemQuantity;

    /**
     * Constructor for detail instance.
     *
     * @param itemName - detail name
     * @param itemQuantity - detail quantity
     * @param itemUnit - detail unit
     */
    public Detail(String itemName, String itemUnit,  String itemQuantity)
    {
        _ItemName = itemName;
        _ItemUnit = itemUnit;
        _ItemQuantity = itemQuantity;
    }

    /**
     * Constructor for detail instance.
     *
     * @param itemName - detail name
     * @param itemQuantity - detail quantity
     * @param itemUnit - detail unit
     */
    public Detail(String itemName, String itemUnit,  String itemQuantity, int detailId)
    {
        _ItemName = itemName;
        _ItemUnit = itemUnit;
        _ItemQuantity = itemQuantity;
        _detailId = detailId;
    }

    /**
     * Returns detail id.
     *
     * @return _detailId - events id.
     */
    public int getId()
    {
        return _detailId;
    }

    /**
     * Returns detail name.
     *
     * @return _Name - detail name.
     */
    public String getName()
    {
        return _ItemName;
    }

    /**
     * Returns detail unit.
     *
     * @return _Date - detail unit.
     */
    public String getUnit()
    {
        return _ItemUnit;
    }

    /**
     * Returns detail quantity.
     *
     * @return _Time - detail quantity.
     */
    public String getQuantity()
    {
        return _ItemQuantity;
    }

    /**
     * Sets detail id.
     */
    public void setId(int detailId)
    {
        _detailId = detailId;
    }

    /**
     * Returns detail name.
     */
    public void setName(String Name)
    {
        _ItemName = Name;
    }

    /**
     * Returns detail unit.
     */
    public void setUnit(String unit)
    {
        _ItemUnit = unit;
    }

    /**
     * Set detail quantity.
     */
    public void setQuantity(String quantity)
    {
        _ItemQuantity = quantity;
    }

    /**
     * Returns detail name.
     *
     * @return _Name - detail name.
     */
    public String toString()
    {
        return _ItemName;
    }
}
