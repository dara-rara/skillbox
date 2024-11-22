package com.example.mod4.adapter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewListResponse {
    private Long id;
    private String text;
    private String username;
    private String category;
    private Long comments;
}
