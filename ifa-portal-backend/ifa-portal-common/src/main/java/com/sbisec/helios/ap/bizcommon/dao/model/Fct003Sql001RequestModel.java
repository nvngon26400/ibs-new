package com.sbisec.helios.ap.bizcommon.dao.model;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Fct003Sql001RequestModel extends ModelBase {
    
    private static final long serialVersionUID = 2668631100084987591L;

    private String butenCode;
    
    private String accountNumber;
    
}
