package com.mingpin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private int student_id;
    private String name;
    private String parent_name;
    private String parent_phone;
    private LocalDateTime birth_date;
    private String gender;
    private String address;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
