package com.ssafy.plan.model;

import java.util.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "TagToPlanDto :  태그와 여행계획 매핑 정보", description = "태그와 여행계획 매핑 정보를 나타낸다.")
public class TagToPlanDto {

    @ApiModelProperty(value = "매핑 테이블 아이디")
    private int id;

    @ApiModelProperty(value = "여행계획 아이디")
    private int planId;

    @ApiModelProperty(value = "태그 아이디")
    private int tagId;

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public int getTagId() {
        return tagId;
    }

    public TagToPlanDto() {
        super();
    }

    public TagToPlanDto(int id, int planId, int tagId) {
        this.id = id;
        this.planId = planId;
        this.tagId = tagId;
    }
}
