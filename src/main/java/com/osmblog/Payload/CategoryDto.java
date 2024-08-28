package com.osmblog.Payload;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long categoryId;

        @NotEmpty(message = "Category Title should not be empty")
        private String categoryTitle;

        @NotEmpty(message = "Category Description should not be empty")
        private String categoryDescription;


}
