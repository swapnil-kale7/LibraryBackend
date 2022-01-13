package com.example.libraryManagement.demoLibrary.Request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminCreateReq {
    private String name;
    private String email;
    private String password;

}
