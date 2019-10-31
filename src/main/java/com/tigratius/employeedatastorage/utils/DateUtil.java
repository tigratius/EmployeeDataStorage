package com.tigratius.employeedatastorage.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class DateUtil {

    public static Date getDateNow()
    {
        return Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
    }
}
