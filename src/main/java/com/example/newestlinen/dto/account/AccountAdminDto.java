package com.example.newestlinen.dto.account;

import com.example.newestlinen.dto.ABasicAdminDto;
import com.example.newestlinen.dto.group.GroupDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
@Data
public class AccountAdminDto extends ABasicAdminDto {

    @Schema(name = "kind")
    private Integer kind;
    @Schema(name = "username")
    private String username;
    @Schema(name = "email")
    private String email;
    @Schema(name = "fullName")
    private String fullName;
    @Schema(name = "group")
    private GroupDto group;

    @Schema(name = "lastLogin")
    @JsonFormat(timezone="Asia/Jakarta", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLogin;

    @Schema(name = "avatar")
    private String avatar;
    @Schema(name = "phone")
    private String phone;
    @Schema(name = "labelColor")
    private String labelColor;
    @Schema(name = "salary")
    private Double salary;
}
