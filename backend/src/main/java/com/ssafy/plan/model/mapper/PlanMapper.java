package com.ssafy.plan.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.plan.model.PlanDto;
import com.ssafy.plan.model.PlanParamDto;

@Mapper
public interface PlanMapper {
	
	List<PlanDto> planList(PlanParamDto planParamDto) throws SQLException;
	PlanDto planDetail(int planId) throws SQLException;
	void writePlan(PlanDto planDto) throws SQLException;
	void modifyPlan(PlanDto planDto) throws SQLException;
	void deletePlan(int planId) throws SQLException;

}
