package esse.chat.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util{
  
    public Timestamp convertStringToTimestamp(String str) {
    try {
      DateFormat formatter;
      formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date date = (Date)formatter.parse(str);
      java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());
      return timeStampDate;
    } catch (ParseException e) {
      System.out.println("Exception :" + e);
      return null;
    }
  }


}
