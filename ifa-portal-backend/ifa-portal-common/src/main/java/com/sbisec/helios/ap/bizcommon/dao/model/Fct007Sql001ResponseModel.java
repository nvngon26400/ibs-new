package com.sbisec.helios.ap.bizcommon.dao.model;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 証券営業日付

 * @author 鄒
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Fct007Sql001ResponseModel extends ModelBase {

    private static final long serialVersionUID = 1L;

    private int rowNum;

    private String businessDay;

}
