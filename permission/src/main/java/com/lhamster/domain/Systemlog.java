package com.lhamster.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
@Setter@Getter@ToString
public class Systemlog {
    private Long id;

    private Date optime;

    private String ip;

    private String funct;

    private String params;

    private String username;
}