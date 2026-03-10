package com.example.Ecommerce.Project.appcontants.commonutils;

import com.example.Ecommerce.Project.exeptionhandler.customexceptions.ResourceNotFoundException;
import com.example.Ecommerce.Project.user.model.User;
import com.example.Ecommerce.Project.user.repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class Methods {

    private Methods() {}
    private static UserRepo userRepo;

    @Autowired
    public void setUserRepo(UserRepo userRepo) {
        Methods.userRepo = userRepo;
    }

    public static String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static Long getCurrentUserId() {

       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       User user = userRepo.findByEmail(auth.getName())
               .orElseThrow(()-> new ResourceNotFoundException("User not found!"));
       return user.getUserId();

    }

    public static User getCurrentUser() {

//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String userEmail = (principal instanceof UserDetails)
//                ? ((UserDetails) principal).getUsername()
//                : principal.toString();

         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         return userRepo.findByEmail(auth.getName())
               .orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

}
