package com.o2osys.pos.common.location.packet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.o2osys.pos.packet.ResCommon;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResLocation extends ResCommon {

    /** 응답주소 */
    @JsonProperty("LOCATION")
    private Location location;

}
