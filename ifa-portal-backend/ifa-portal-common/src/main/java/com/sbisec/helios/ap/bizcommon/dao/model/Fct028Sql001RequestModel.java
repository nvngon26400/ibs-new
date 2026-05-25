package com.sbisec.helios.ap.bizcommon.dao.model;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 鄒
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Fct028Sql001RequestModel extends ModelBase {

    private static final long serialVersionUID = 4207847352527622582L;

    private String butenCode;

    private String accountNumber;

    private String brandCode;

    private String depositType;

}
