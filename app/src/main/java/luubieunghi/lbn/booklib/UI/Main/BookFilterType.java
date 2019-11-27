package luubieunghi.lbn.booklib.UI.Main;

public enum BookFilterType
{
    ALL (0),
    EBOOKONLY (1),
    AUDIOBOOKONLY (2);
    private final int value;
    private BookFilterType(int value)
    {
        this.value = value;
    }
    public int getValue()
    {
        return value;
    }
    public static BookFilterType getFilterType(int _value)
    {
        if (ALL.getValue() == _value)
            return ALL;
        if (EBOOKONLY.getValue() == _value)
            return EBOOKONLY;
        if (AUDIOBOOKONLY.getValue() == _value)
            return AUDIOBOOKONLY;
        return null;
    }
}
