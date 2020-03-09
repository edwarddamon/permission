package com.lhamster.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
public class Employee {
    private Long eId;
    private String username;
    private String password;//密码
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT + 8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inputtime;
    private String tel;
    private String email;
    private Boolean state;
    private Boolean admin;
    private Department department;

    /*角色*/
    private List<Role> roles = new ArrayList<>();
}