package com.sbisec.helios.ap.bizcommon.dao.model;

import java.util.List;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Fct025Sql002RequestModel extends ModelBase {
    
    private static final long serialVersionUID = -6556936299195223245L;
    private List<String> nriCodeList;
}
