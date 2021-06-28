package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credentials getCredentials(int credentialId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credentials credentials);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credentials> getAllCredentials(int userId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Integer deleteCredentials(int credentialId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password} WHERE credentialid = #{credentialId}")
    Integer updateCredentials(Credentials credentials);
}
