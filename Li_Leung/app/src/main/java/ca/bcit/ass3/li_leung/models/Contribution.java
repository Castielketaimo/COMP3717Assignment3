package ca.bcit.ass3.li_leung.models;

/**
 * Contribution Model for the Contribution table.
 *
 * @author Castiel Li & Justin Leung
 */
public class Contribution
{
    /** Contribution ID. */
    private int _contributionId;

    /** Contribution name. */
    private String _Name;

    /** Contribution quantity. */
    private String _Quantity;

    /** Contribution date. */
    private String _Date;

    /**
     * Constructor for Contribution instance.
     *
     * @param Name - contribution name
     * @param Quantity - contribution quantity
     * @param Date - contribution unit
     */
    public Contribution(String Name, String Quantity,  String Date)
    {
        _Name = Name;
        _Quantity = Quantity;
        _Date = Date;
    }

    /**
     * Constructor for Contribution instance.
     *
     * @param Name - contribution name
     * @param Quantity - contribution quantity
     * @param Date - contribution unit
     * @param contributionId - contribution id.
     */
    public Contribution(String Name, String Quantity,  String Date, int contributionId)
    {
        _Name = Name;
        _Quantity = Quantity;
        _Date = Date;
        _contributionId = contributionId;
    }

    /**
     * Returns contribution id.
     *
     * @return _contributionId - contribution id.
     */
    public int getId()
    {
        return _contributionId;
    }

    /**
     * Returns contribution name.
     *
     * @return _Name - contribution name.
     */
    public String getName()
    {
        return _Name;
    }

    /**
     * Returns contribution quantity.
     *
     * @return _Quantity - contribution quantity.
     */
    public String getQuantity()
    {
        return _Quantity;
    }

    /**
     * Returns contribution date.
     *
     * @return _Date - contribution date.
     */
    public String getDate()
    {
        return _Date;
    }

    /**
     * Sets contribution id.
     */
    public void setId(int contributionId)
    {
        _contributionId = contributionId;
    }

    /**
     * Returns contribution name.
     */
    public void setName(String Name)
    {
        _Name = Name;
    }

    /**
     * Returns contribution quantity.
     */
    public void setQuantity(String Quantity)
    {
        _Quantity = Quantity;
    }

    /**
     * Set contribution date.
     */
    public void setDate(String Date)
    {
        _Date = Date;
    }

    /**
     * Returns contribution name.
     *
     * @return _Name - detail name.
     */
    public String toString()
    {
        return _Name;
    }
}
