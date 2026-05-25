package com.sbisec.helios.ap.api.athena.protocol;

public interface BaseRequest {
    
    public <T> T getHeader();
    
    public <T> T getParameter();
    
}