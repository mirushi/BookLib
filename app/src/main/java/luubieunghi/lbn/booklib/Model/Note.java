package luubieunghi.lbn.booklib.Model;

public class Note {
    String note;
    int color; //1 red 2 yellow 3 blue 4 green
    String location;

    public void setNote(String note) {
        this.note = note;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getLocation()
    {
        return this.location;
    }

    public String getNote() {
        return note;
    }
}
