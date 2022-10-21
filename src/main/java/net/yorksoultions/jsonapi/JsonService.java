package net.yorksoultions.jsonapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Enumeration;
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
        for (int i = 2; i < tokenList.length; i += 2) {
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

    public HashMap headers(HttpServletRequest request) {
        HashMap map = new HashMap();
        Enumeration<String> headerNameList = request.getHeaderNames();
        while (headerNameList.hasMoreElements()) {
            String headerName = headerNameList.nextElement();
            String headerData = request.getHeader(headerName);
            map.put(headerName, headerData);
        }

        return map;
    }

    public HashMap cookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(
                "jsontestdotcom",
                "ms:" + Instant.now().toEpochMilli()
        );
        response.addCookie(cookie);

        HashMap map = new HashMap();
        map.put(
                "cookie_status",
                "Cookie set with name jsontestdotcom"
        );

        return map;
    }

    public HashMap md5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            byte[] digest = md.digest();
            String hashedValue = DatatypeConverter.printHexBinary(digest).toLowerCase();

            HashMap map = new HashMap();
            map.put(
                    "original",
                    text
            );
            map.put(
                    "md5",
                    hashedValue
            );

            return map;

        } catch (NoSuchAlgorithmException exception) {
            System.out.println("bad algorithm");
            return new HashMap();
        }
    }

    public HashMap validate(String jsonString) {
        System.out.println(jsonString);

        try {
            boolean isArray = jsonString.charAt(0) == '[';

            int length;
            var startTime = Instant.now().getNano();
            if (isArray) {
                 JSONArray array = new JSONArray(jsonString);
                 length = array.length();

            } else {
                JSONObject obj = new JSONObject(jsonString);
                length = obj.length();
            }
            var endTime = Instant.now().getNano();


            HashMap map = new HashMap();
            map.put(
                    "object_or_array",
                    isArray ? "array" : "object"
            );
            map.put(
                    "validate",
                    true
            );
            map.put(
                    "parse_time_nanoseconds",
                    endTime - startTime
            );
            map.put(
                    "size",
                    length
            );
            map.put(
                    "empty",
                    length > 0 ? false : true
            );

            return map;
        } catch (JSONException exception) {
            HashMap map = new HashMap();
            map.put(
                    "validate",
                    false
            );
            map.put(
                    "error",
                    exception.getMessage()
            );
            map.put(
                    "object_or_array",
                    jsonString.charAt(0) == '[' ? "array" : "object"
            );
            map.put(
                    "error_info",
                    "This error came from the " + exception.getClass()
            );

            return map;
        }
    }

}
