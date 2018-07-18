package com.o2osys.pos.common.location.packet.kakao.request;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqLocation {

    @NotNull
    @JsonProperty("address")
    private String address;

}