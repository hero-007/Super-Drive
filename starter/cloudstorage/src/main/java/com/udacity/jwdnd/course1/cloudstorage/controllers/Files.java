package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.DriveFile;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class Files {
    private FileService fileService;
    private UserService userService;

    public Files(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/file-upload")
    public String uploadFile(@RequestParam("fileUpload")MultipartFile fileUpload, Authentication authentication, Model model)throws IOException {
        String username = authentication.getName();
        Integer userid = userService.getUser(username).getUserid();
        if(fileUpload != null && userid != null){
            Integer fileId = fileService.uploadFileIntoDB(new DriveFile(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(), String.valueOf(fileUpload.getSize()), userid, fileUpload.getBytes()));
            if(fileId != null){
                return "redirect:/";
            }else{
                return "redirect:/";
            }
        }
        return "redirect:/";
    }

    @GetMapping("/files/delete")
    public String deleteFile(@RequestParam(name = "fileId", required = true) String fileId){
        if(fileId != null){
            int fileToDelete = Integer.parseInt(fileId);
            Integer deletedFile = fileService.deleteFile(fileToDelete);
            if(deletedFile > 1){
                return "redirect:/";
            }else{
                return "redirect:/";
            }
        }
        return "redirect:/";
    }

    @GetMapping("/files/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam(name = "fileId", required = true) String fileId){
        if(fileId != null){
            int fileToDownload = Integer.parseInt(fileId);
            DriveFile file = fileService.getFile(fileToDownload);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file.getFileData());
        }
        return null;
    }
}
