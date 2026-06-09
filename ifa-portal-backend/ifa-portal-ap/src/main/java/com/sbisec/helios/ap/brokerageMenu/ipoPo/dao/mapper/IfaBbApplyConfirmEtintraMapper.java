package com.sbisec.helios.ap.brokerageMenu.ipoPo.dao.mapper;

import org.apache.ibatis.annotations.Param;

import com.sbisec.helios.ap.brokerageMenu.ipoPo.dao.model.IfaBbApplyConfirmSql015RequestModel;
import com.sbisec.helios.ap.common.annotation.dao.EtintraMapper;

@EtintraMapper
public interface IfaBbApplyConfirmEtintraMapper {
    
    /**
     * SQLID：Sql015
     * SQL名：BB申込情報登録
     * SQLタイプ：insert
     *
     * @param req IfaBbApplyConfirmSql001RequestModel
     * @return int effect rows
     * @exception Exception SQLエラー
     */
    public int insertIfaBbApplyConfirmSql015(
            @Param("req")  IfaBbApplyConfirmSql015RequestModel req
        ) throws Exception;
}
