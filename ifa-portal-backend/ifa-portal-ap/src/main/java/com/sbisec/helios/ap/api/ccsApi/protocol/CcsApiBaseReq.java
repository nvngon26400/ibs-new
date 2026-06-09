package com.sbisec.helios.ap.api.ccsApi.protocol;

public interface CcsApiBaseReq {

    public <T> T getHeader();

    public <T> T getParameter();

}