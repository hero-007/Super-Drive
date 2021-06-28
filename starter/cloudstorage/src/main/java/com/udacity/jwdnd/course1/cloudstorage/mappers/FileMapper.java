package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.DriveFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int uploadFile(DriveFile file);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<DriveFile> getAllFiles(int userId);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    Integer deleteFile(int fileId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    DriveFile getFile(int fileId);
}
