package com.example.Ecommerce.Project.category.dtos.response;

import com.example.Ecommerce.Project.category.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetCategoriesDTO {
     private List<CategoryDTO> categories;
     private int pageNumber;
     private int pageSize;
     private Long totalElements;
     private int totalPages;
     private boolean lastPage;




     public static GetCategoriesDTO toDTO(Page<Category> categoryPage) {

         List<CategoryDTO> categoryDTOs = new ArrayList<>();

         for (Category category : categoryPage.getContent()) {
             categoryDTOs.add(
                     new CategoryDTO(category.getCategoryName())
             );
         }
         // set Pagination info
         GetCategoriesDTO response = new GetCategoriesDTO();
         response.setCategories(categoryDTOs);
         response.setPageNumber(categoryPage.getNumber());
         response.setPageSize(categoryPage.getSize());
         response.setTotalElements(categoryPage.getTotalElements());
         response.setTotalPages(categoryPage.getTotalPages());
         response.setLastPage(categoryPage.isLast());
         return response;
     }

}
