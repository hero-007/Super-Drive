package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.models.DisplayCredentials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    CredentialMapper credentialMapper;
    EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public Credentials getCredentials(Integer credentialId){
        if(credentialId != null){
            Credentials credentials = credentialMapper.getCredentials(credentialId);
            if(credentials != null) {
                String key = credentials.getKey();
                String decryptedPassword = encryptionService.decryptValue(credentials.getPassword(), key);
                return new Credentials(credentials.getCredentialId(), credentials.getUrl(), credentials.getUsername(), credentials.getKey(), decryptedPassword, credentials.getUserId());
            }
        }
        return null;
    }

    public Integer createNewCredentials(Credentials credentials){
        if(credentials != null){
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            String key = Base64.getEncoder().encodeToString(salt);
            String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(), key);
            return credentialMapper.insert(new Credentials(null, credentials.getUrl(), credentials.getUsername(), key, encryptedPassword, credentials.getUserId()));
        }
        return null;
    }

    public List<DisplayCredentials> getAllCredentials() {
        List<Credentials> credentialsList = credentialMapper.getAllCredentials();
        List<DisplayCredentials> decryptedCredentialsList = new ArrayList<DisplayCredentials>();
        for(Credentials credential: credentialsList){
            String encryptedPassword  = credential.getPassword();
            String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
            decryptedCredentialsList.add(new DisplayCredentials(encryptedPassword, new Credentials(credential.getCredentialId(), credential.getUrl(), credential.getUsername(), credential.getKey(), decryptedPassword, credential.getUserId())));
        }
        return decryptedCredentialsList;
    }

    public Integer deleteCredentials(int credentialId){
        if(credentialId > 0){
            return  credentialMapper.deleteCredentials(credentialId);
        }
        return null;
    }

    public Integer updateCredentials(Credentials credentials){
        if(credentials != null && credentials.getCredentialId() != null) {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            String key = Base64.getEncoder().encodeToString(salt);
            String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(), key);
            return credentialMapper.updateCredentials(new Credentials(credentials.getCredentialId(), credentials.getUrl(), credentials.getUsername(), key, encryptedPassword, credentials.getUserId()));
        }
        return null;
    }
}
