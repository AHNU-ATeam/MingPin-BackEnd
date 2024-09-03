package com.mingpin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Campus {
    private Integer id;-
    private String logo; //指地址
    private String name;
    private String address;
    private Integer principalId;
    private LocalDate renewalStart,renewalEnd;
    private String info;
    private Integer population;
    private LocalDate createdAt,updatedAt;

}
