package com.sbisec.helios.ap.api.athena.protocol.exchange.master;

import com.sbisec.helios.ap.api.athena.protocol.BaseRequest;

import lombok.Data;

@Data
public class ListExchangeTradeCurrenciesReq implements BaseRequest {
    
    public ListExchangeTradeCurrenciesReq() {
        
    }
    
    // headerとparameterインスタンス化
    private Header header = new Header();
    
    private Parameter parameter = new Parameter();
    
    @Data
    public class Header {
        
        public Header() {
            
        }
        
    }
    
    @Data
    public class Parameter {
        
        public Parameter() {
            
        }
        
    }
    
}
