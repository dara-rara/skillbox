package com.example.mod4.adapter.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewSimpleResponse {
    private Long id;
    private String text;
    private String username;
    private String category;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CommentResponse> comments = new ArrayList<>();
}
