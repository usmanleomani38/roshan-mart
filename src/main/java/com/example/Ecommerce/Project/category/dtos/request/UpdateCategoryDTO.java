package com.example.Ecommerce.Project.category.dtos.request;

import com.example.Ecommerce.Project.category.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryDTO {

    private String categoryName;

    public static UpdateCategoryDTO toDto(Category category) {
        UpdateCategoryDTO dto = new UpdateCategoryDTO();
        dto.setCategoryName(category.getCategoryName());
        return dto;
    }

}
