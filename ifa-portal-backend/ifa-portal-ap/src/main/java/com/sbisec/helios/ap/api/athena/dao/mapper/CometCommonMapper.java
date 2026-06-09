package com.sbisec.helios.ap.api.athena.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbisec.helios.ap.api.athena.model.AthenaErrorMessageModel;

@Mapper
public interface CometCommonMapper {

	/**
	 * Athenaエラーメッセージマスタ（ATHENA_ERROR_MESSAGE_MST）エラーコードとエラーメッセージを取得する
	 * 
	 * @param apiErrorCode APIエラーコード
	 * @return AthenaErrorMessageModel モテル
	 * @throws Exception 異常
	 */
	public AthenaErrorMessageModel getAthenaErrorCodeAndMessage(@Param("apiErrorCode") String apiErrorCode)
			throws Exception;

}
