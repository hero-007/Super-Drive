package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class FileUploadExceptionAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(
            MaxUploadSizeExceededException exc,
            RedirectAttributes rd) {

        rd.addFlashAttribute("assetErrorMessage", "File size is too big.");
        return "redirect:/";
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public String handleDuplicateFileException(
            DuplicateKeyException dex,
            RedirectAttributes rd
    ){
        rd.addFlashAttribute("assetErrorMessage", "Duplicate File Names are not allowed.");
        return "redirect:/";
    }
}