package com.ssafy.attraction.model.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.attraction.model.AttractionInfoDto;
import com.ssafy.attraction.model.AttractionSearchDto;
import com.ssafy.attraction.model.mapper.AttractionMapper;

import com.ssafy.util.PageNavigation;
import com.ssafy.util.SizeConstant;

@Service
public class AttractionServiceImpl implements AttractionService {

	private AttractionMapper attractionMapper;

	public AttractionServiceImpl(AttractionMapper attractionMapper) {
		super();
		this.attractionMapper = attractionMapper;
	}

	@Override
	public List<AttractionInfoDto> attractionList(AttractionSearchDto attractionSearchDto) throws Exception {
		return attractionMapper.attractionList(attractionSearchDto);
	}
}
