package com.example.newestlinen.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE+"account")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Account extends  Auditable<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer kind;

    @Column(nullable = false)
    @NotEmpty
    @Size(min = 6, max=12)
    private String username;

    @Column(nullable = false)
    @NotEmpty
    @Size(min = 1, max=200)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "full_name",nullable = false)
    @NotEmpty
    @Size(min = 1, max=100)
    private String fullName;

    @Column(nullable = false)
    @NotEmpty
    @Size(min = 1, max=11)
    private String phone;

    @Column(name = "avatar_path")
    private String avatarPath;

    @Column(name = "is_super_admin")
    private Boolean isSuperAdmin = false;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "last_login")
    private Date lastLogin;

    @Column(name="reset_pwd_code")
    private String resetPwdCode;
    @Column(name = "reset_pwd_time")
    private Date resetPwdTime;
    @Column(name = "attempt_forget_pwd")
    private Integer attemptCode;
    @Column(name = "attempt_login")
    private Integer attemptLogin;
    @Column(name="verify_code")
    private String verifyCode;
    @Column(name = "verify_time")
    private Date verifyTime;
}
