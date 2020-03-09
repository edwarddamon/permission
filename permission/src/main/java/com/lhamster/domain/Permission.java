package com.lhamster.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter@Getter@ToString
public class Permission {
    private Long perId;

    private String perName;

    private String perResource;
}