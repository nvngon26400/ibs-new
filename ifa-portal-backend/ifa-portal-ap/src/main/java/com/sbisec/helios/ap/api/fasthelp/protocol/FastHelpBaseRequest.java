package com.sbisec.helios.ap.api.fasthelp.protocol;

public interface FastHelpBaseRequest {

    public <T> T getHeader();

    public <T> T getParameter();

}