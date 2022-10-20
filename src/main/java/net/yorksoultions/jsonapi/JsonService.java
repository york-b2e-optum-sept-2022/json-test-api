package net.yorksoultions.jsonapi;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

@Service
public class JsonService {

    public String code(HttpServletRequest request) {
        return "alert(\"Your IP address is: " + request.getRemoteAddr() + "\");";
    }

    public HashMap ip(HttpServletRequest request) {
        HashMap map = new HashMap();
        map.put(
                "ip",
                request.getRemoteAddr()
        );

        return map;
    }

    public HashMap echo(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String[] tokenList = uri.split("/");

        HashMap map = new HashMap();
        for (int i = 2; i < tokenList.length; i+=2) {
            String key = tokenList[i];

            String value = "";
            int valueIndex = i + 1;
            if (valueIndex < tokenList.length) {
                value = tokenList[valueIndex];
            }

            map.put(
                    key,
                    value
            );
        }

        return map;
    }

    public HashMap time() {
        Date date = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
        timeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        String dateString = dateFormat.format(date);
        String timeString = timeFormat.format(date);

        HashMap map = new HashMap();
        map.put(
                "date",
                dateString
        );
        map.put(
                "time",
                timeString
        );
        map.put(
                "milliseconds_since_epoch",
                Instant.now().toEpochMilli()
        );

        return map;
    }

}
