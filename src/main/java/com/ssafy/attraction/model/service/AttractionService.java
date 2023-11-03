package com.ssafy.attraction.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.attraction.model.AttractionInfoDto;
import com.ssafy.attraction.model.AttractionSearchDto;
import com.ssafy.util.PageNavigation;

public interface AttractionService {
	
	List<AttractionInfoDto> attractionList(AttractionSearchDto attractionSearchDto) throws Exception;
	
}
