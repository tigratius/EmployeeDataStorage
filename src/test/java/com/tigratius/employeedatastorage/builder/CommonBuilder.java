package com.tigratius.employeedatastorage.builder;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
public class CommonBuilder {

    private static final String pattern = "yyyy-MM-dd";

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
            return new SimpleDateFormat(pattern).parse(date);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return null;
    }
}
