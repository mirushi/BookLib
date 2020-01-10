package luubieunghi.lbn.booklib.UI.Main.EventBus;

public class BookListUpdatedEventBus {
    //Để cho cả 3 fragment đều được cập nhật.
    int count = 2;
    synchronized public boolean checkForUpdate(){
        --count;
        if (count == 0)
            return false;
        return true;
    }
}
