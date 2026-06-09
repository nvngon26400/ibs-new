package com.sbisec.helios.ap.common.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** NRI_QueryAccountBalance outdata */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListIfaExchangeTradeRatesOutData {

	  @lombok.Getter(onMethod = @__(@JsonProperty("ifaExchangeTradeRates")))
	  @lombok.Setter(onMethod = @__(@JsonProperty("ifaExchangeTradeRates")))
	  private List<IfaExchangeTradeRates> ifaExchangeTradeRates;

  public ListIfaExchangeTradeRatesOutData() {}
}
