package luubieunghi.lbn.booklib.UI.BookPageManagement;

public class PageManagementRecyclerViewItem {
    public String text;
    public int image;

    public PageManagementRecyclerViewItem(String text, int image) {
        this.text = text;
        this.image = image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getImage() {
        return this.image;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

}
