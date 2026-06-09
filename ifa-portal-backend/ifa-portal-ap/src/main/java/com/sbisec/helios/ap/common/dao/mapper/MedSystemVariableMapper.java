package com.sbisec.helios.ap.common.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sbisec.helios.ap.common.model.MedSystemVariable;

/**
 * 仲介業システム値Mapper
 *
 * @author SCSK
 *
 */
@Mapper
public interface MedSystemVariableMapper {

    /**
     * 仲介業システム値取得処理
     *
     * @return 仲介業システム値リスト
     * @throws Exception 例外
     */
    public List<MedSystemVariable> getMedSystemVariableList() throws Exception;

}
