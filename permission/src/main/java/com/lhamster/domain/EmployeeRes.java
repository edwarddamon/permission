package com.lhamster.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter@Getter
public class EmployeeRes {
    private Long total;
    private List<?> rows;
}
