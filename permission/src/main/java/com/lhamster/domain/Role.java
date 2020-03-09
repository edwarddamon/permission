package com.lhamster.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter@Getter@ToString
public class Role {
    private Long roleId;

    private String roleNum;

    private String roleName;

    private List<Permission> permissions =new ArrayList<>();
}