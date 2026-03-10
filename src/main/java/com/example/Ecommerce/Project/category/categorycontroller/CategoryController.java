package com.example.Ecommerce.Project.category.categorycontroller;

import com.example.Ecommerce.Project.appcontants.appconstants.AppContants;
import com.example.Ecommerce.Project.category.categoryservice.CategoryService;
import com.example.Ecommerce.Project.category.dtos.request.UpdateCategoryDTO;
import com.example.Ecommerce.Project.category.dtos.response.GetCategoriesDTO;
import com.example.Ecommerce.Project.category.model.Category;
import com.example.Ecommerce.Project.exeptionhandler.ApiResponse;
import com.example.Ecommerce.Project.exeptionhandler.Status;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Builder
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/admin/category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category newCategory = categoryService.createCategory(category);
        ApiResponse<Category> response = ApiResponse.<Category>builder()
                .status(Status.SUCCESS)
                .message("Category created successfully")
                .data(newCategory)
                .build();
        URI location = URI.create("api/category/" + newCategory.getCategoryID());
        return ResponseEntity.created(location).body(newCategory);
    }
    @PostMapping("/admin/categories/bulk")
    public ResponseEntity<ApiResponse<String>> addCategoriesInBulk(@RequestBody List<Category> categories) {
        String  message = categoryService.addCategoriesInBulk(categories);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(Status.SUCCESS)
                .message(message)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/public/categories")
    public ResponseEntity<ApiResponse<GetCategoriesDTO>> getAllCategories(
             @RequestParam(name = "pageNumber",defaultValue = AppContants.PAGE_NUMBER, required = false) Integer pageNumber,
             @RequestParam(name = "pageSize",defaultValue = AppContants.PAGE_SIZE, required = false) Integer pageSize,
             @RequestParam(name = "sortBy", defaultValue = AppContants.SORT_CATEGORIES_BY, required = false) String sortBy,
             @RequestParam(name = "sortOrder", defaultValue = AppContants.SORT_DIR, required = false) String sortOrder) {
        ApiResponse<GetCategoriesDTO> response =
                ApiResponse.<GetCategoriesDTO>builder()
               .status(Status.SUCCESS)
               .message("Categories fetched successfully")
               .data( categoryService.getAllCategories(
                       pageNumber,pageSize,sortBy,sortOrder))
               .build();
       return ResponseEntity.ok(response);
    }

    @PutMapping("/admin/categories/{id}")
    public ResponseEntity<ApiResponse<String>> updateCategory(
            @RequestBody UpdateCategoryDTO dto,
            @PathVariable Long id) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(Status.SUCCESS)
                .message("Category Updated Successfully")
                .data(categoryService.updateCategory(dto,id))
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<ApiResponse<Void>>deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status(Status.SUCCESS)
                .message("Category Deleted Successfully")
                .build();
        return ResponseEntity.ok(response);
    }
}
