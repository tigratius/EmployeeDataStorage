package com.tigratius.employeedatastorage.builder;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
public class CommonBuilder {

    private static final String patternDate = "yyyy-MM-dd";
    private static final String patternDateTime = "yyyy-MM-dd HH:mm:ss";

    public static BigDecimal number(String number) {
        return new BigDecimal(number);
    }

    public static Long id(String id) {
        return new Long(id);
    }

    public static <T> List<T> list(T... objects) {
        return Lists.newArrayList(objects);
    }

    public static Date date(String date) {
        try {
            return new SimpleDateFormat(patternDate).parse(date);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    public static Date datetime(String date) {
        try {
            return new SimpleDateFormat(patternDateTime).parse(date);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return null;
    }
}
