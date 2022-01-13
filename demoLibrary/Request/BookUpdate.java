package com.example.libraryManagement.demoLibrary.Request;

import com.example.libraryManagement.demoLibrary.model.Genre;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookUpdate {

    private String name;
    private Genre genre;
    private Integer cost;

}
