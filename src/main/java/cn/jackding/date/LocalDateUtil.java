package cn.jackding.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jack
 * @Date 2019/12/5 10:32
 * @Version 1.0.0
 */
public class LocalDateUtil {

    /**
     * 判断是否周末
     *
     * @param date
     * @return
     */
    public static boolean isWeekend(LocalDate date) {
        int weekNum = date.getDayOfWeek().getValue();
        return 6 == weekNum || 7 == weekNum;
    }

    /**
     * 获取一年所有的天数yyyyMMdd
     *
     * @param date
     * @return
     */
    public static List<String> getAllYearDay(LocalDate date) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= date.lengthOfYear(); i++) {
            list.add(LocalDate.ofYearDay(date.getYear(), i).format(DateTimeFormatter.BASIC_ISO_DATE));
        }
        return list;
    }
}
