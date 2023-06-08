package com.itlang.models.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckQuestion {
    private Long id;
    private Long userAnswer;
}
