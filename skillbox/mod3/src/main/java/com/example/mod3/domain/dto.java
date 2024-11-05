package com.example.mod3.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@FieldNameConstants
@NoArgsConstructor
public class dto {
    private Long index;
}
