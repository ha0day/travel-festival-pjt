package com.ssafy.attraction.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.attraction.model.AttractionInfoDto;
import com.ssafy.attraction.model.AttractionSearchDto;

@Mapper
public interface AttractionMapper {

	List<AttractionInfoDto> attractionList(AttractionSearchDto attractionSearchDto) throws SQLException;
	
}