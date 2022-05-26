package com.demo.file.demofileservices.util;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.function.Predicate;

public enum ValidatorEnum {
    ;
    public static final Predicate<List<String>> VALIDATE = excelColumns -> excelColumns.stream().anyMatch(c -> StringUtils.isEmpty(c.trim()));
}
