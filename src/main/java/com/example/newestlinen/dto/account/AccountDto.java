package com.example.newestlinen.dto.account;

import com.example.newestlinen.dto.group.GroupDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class AccountDto {
    @Schema(name = "id")
    private Long id;
    @Schema(name = "kind")
    private int kind;
    @Schema(name = "username")
    private String username;
    @Schema(name = "email")
    private String email;
    @Schema(name = "fullName")
    private String fullName;
    @Schema(name = "group")
    private GroupDto group;
    @Schema(name = "lastLogin")
    private Date lastLogin;
    @Schema(name = "avatar")
    private String avatar;
    @Schema(name = "phone")
    private String phone;
    @Schema(name = "lang")
    private String lang;
    private Boolean isSuperAdmin;
}
