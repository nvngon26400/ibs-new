
package com.sbisec.helios.ap.wholePortal.psite.dao;

import org.springframework.stereotype.Component;

import com.sbibits.earth.model.DataList;
import com.sbisec.helios.ap.wholePortal.psite.model.GetDealerNameAndRegistNoModel;
import com.sbisec.helios.ap.wholePortal.psite.model.GetTbMedDisclaimerModel;
import com.sbisec.helios.ap.wholePortal.psite.model.GetTempFileDirForDocumentCategoryIdModel;

@Component
public interface PSiteServiceDao {
    
    public DataList<GetTempFileDirForDocumentCategoryIdModel> getTempFileDirForDocumentCategoryId(String funcId,
            String catId) throws Exception;
    
    public DataList<GetDealerNameAndRegistNoModel> getDealerNameAndRegistNo(String brokerCode) throws Exception;
    
    public DataList<GetTbMedDisclaimerModel> getTbMedDisclaimer(String disclaimerId) throws Exception;
    
}
