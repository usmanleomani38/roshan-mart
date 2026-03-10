package com.example.Ecommerce.Project.category.categoryservice;

import com.example.Ecommerce.Project.category.categoryrepo.CategoryRepo;
import com.example.Ecommerce.Project.category.dtos.request.UpdateCategoryDTO;
import com.example.Ecommerce.Project.category.dtos.response.GetCategoriesDTO;
import com.example.Ecommerce.Project.category.model.Category;
import com.example.Ecommerce.Project.exeptionhandler.customexceptions.CategoryAlreadyExists;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepo categoryRepo;

    public Category createCategory(Category category) throws CategoryAlreadyExists {
        if(categoryRepo.existsByCategoryName(category.getCategoryName()))
            throw new CategoryAlreadyExists("Category "+ category.getCategoryName() + "Already Exists");
        return categoryRepo.save(category);
    }

    public GetCategoriesDTO getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage =  categoryRepo.findAll(pageDetails);
        if(categoryPage.getContent().isEmpty())
            throw new EntityNotFoundException("No Categories Found!");
        return GetCategoriesDTO.toDTO(categoryPage);
    }


    public String updateCategory(UpdateCategoryDTO dto, Long id) {

        Category existingCategory = categoryRepo.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("No Category found with this ID "));
        if (dto.getCategoryName() != null)
            existingCategory.setCategoryName(dto.getCategoryName());
         categoryRepo.save(existingCategory);
         return "Category Updated Successfully";
    }

    public void deleteCategory(Long id) {
       if(categoryRepo.existsById(id))
           categoryRepo.deleteById(id);
       else
           throw new EntityNotFoundException("Category not found");
    }

    public String addCategoriesInBulk(List<Category> categories ) {

    List<String> categoryNames = new ArrayList<>();
     for (Category category: categories) {
                categoryNames.add(category.getCategoryName());
     }
     List<Category> existingCategories = categoryRepo.findByCategoryNameIn(categoryNames);
     if(!existingCategories.isEmpty())
          throw new CategoryAlreadyExists("One or more Categories already exists");
      categoryRepo.saveAll(categories);
      return "Categories Added Successfully";
    }
}

