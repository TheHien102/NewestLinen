package com.example.newestlinen.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class ABasicAdminDto {
    @Schema(name = "id")
    private Long id;

    @Schema(name = "status")
    private Integer status;

    @Schema(name = "modifiedDate")
    private Date modifiedDate;

    @Schema(name = "createdDate")
    private Date createdDate;

    @Schema(name = "modifiedBy")
    private String modifiedBy;

    @Schema(name = "createdBy")
    private String createdBy;
}
