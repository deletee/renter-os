package com.delete.renter.utils;

import com.googlecode.aviator.AviatorEvaluator;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class NormCalc {
    public static float calcNum(String normStr)  {
        float sum = 0.0f;
        String[] arr = StringUtils.split(normStr.trim(),";");
        for (String expr:arr) {
            if (!expr.contains("*")) return 0.0f;
            sum += (Long) AviatorEvaluator.execute(expr);
        }
        return sum;
    }

    public static float setScale(double v){
        return new BigDecimal(v).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }
}
