package com.sbisec.helios.ap.api.safe.protocol;

public interface SafeBaseRequest {

    public <T> T getHeader();

    public <T> T getParameter();

}