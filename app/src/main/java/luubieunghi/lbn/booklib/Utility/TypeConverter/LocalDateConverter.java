package luubieunghi.lbn.booklib.Utility.TypeConverter;

import android.os.Build;

import java.time.LocalDate;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

//Ta sẽ lưu LocalDate dưới dạng Epoch Day.
public class LocalDateConverter {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static LocalDate fromTimeStamp(String value){
        return value == null ? null : (LocalDate.parse(value));
    }
    @TypeConverter
    public static String localDateToTimeStamp(LocalDate date){
        return date == null ? null : (date.toString());
    }
}
