package com.sbisec.helios.ap.bizcommon.dao.model;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Fct018Sql001RequestModel extends ModelBase {
	
	private static final long serialVersionUID = 1L;

    private String syubetu;
	
	private String countryCode;
	

}
