package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.DriveFile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public Integer uploadFileIntoDB(DriveFile file){
        if(file != null){
            return fileMapper.uploadFile(file);
        }
        return null;
    }

    public Integer deleteFile(Integer fileId){
        try {
            return fileMapper.deleteFile(fileId);
        }catch (Exception e){
            return null;
        }
    }

    public List<DriveFile> getAllFiles(int userId){
        return fileMapper.getAllFiles(userId);
    }

    public DriveFile getFile(Integer fileId){
        if(fileId != null){
            return fileMapper.getFile(fileId);
        }
        return null;
    }
}
