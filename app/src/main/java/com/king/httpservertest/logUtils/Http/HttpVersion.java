package com.king.httpservertest.logUtils.Http;

public enum  HttpVersion {

    VERSION_1_0(1.0),
    VERSION_1_1(1.1),
    VERSION_1_2(1.2);

    private double value = 1.1;

    HttpVersion(double v) {
        value = v;
    }

    public double getValue()
    {
        return value;
    }

}
