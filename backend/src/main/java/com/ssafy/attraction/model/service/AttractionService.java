package com.ssafy.attraction.model.service;

import java.util.List;

import com.ssafy.attraction.model.AttractionInfoDto;
import com.ssafy.attraction.model.AttractionSearchDto;

public interface AttractionService {
	
	List<AttractionInfoDto> attractionList(AttractionSearchDto attractionSearchDto) throws Exception;
	
}
