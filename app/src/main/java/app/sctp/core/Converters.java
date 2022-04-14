package app.sctp.core;

import androidx.room.TypeConverter;

import java.sql.Date;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

import app.sctp.utils.LocaleUtils;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }


    @TypeConverter
    public static List<Long> toList(String commaSeparatedList) {
        if (LocaleUtils.hasText(commaSeparatedList)) {
            String[] items = commaSeparatedList.split(",");
            if (items.length > 0) {
                List<Long> list = new LinkedList<>();
                for (String number : items) {
                    list.add(Long.parseLong(number));
                }
                return list;
            }
        }
        return Collections.emptyList();
    }

    @TypeConverter
    public static String toCommaSeparatedList(List<Long> longList) {
        if (longList == null || longList.isEmpty()) {
            return null;
        }
        StringBuilder builder = new StringBuilder(longList.get(0).toString());
        for (int i = 1; i < longList.size(); i++) {
            builder.append(",").append(longList.get(i).toString());
        }
        return builder.toString();
    }
}
