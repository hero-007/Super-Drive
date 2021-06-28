package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.boot.context.properties.bind.handler.IgnoreTopLevelConverterNotFoundBindHandler;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class Credentials {

    CredentialService credentialService;
    UserService userService;

    public Credentials(CredentialService credentialService, UserService userService){
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping("/credential")
    public String addNewCredentials(@ModelAttribute("newCredential") com.udacity.jwdnd.course1.cloudstorage.models.Credentials newCredentials, RedirectAttributes redirectAttributes, Authentication authentication, Model model){
        if(newCredentials != null){
            String loggedInUser = authentication.getName();
            User user = userService.getUser(loggedInUser);
            if(user != null && user.getUserid() != null){
                // check if the credential id already exist in the DB then update otherwise create a new credetial
                if(newCredentials.getCredentialId() != null){
                    // update the credentials
                    Integer updateCredentialCount = credentialService.updateCredentials(new com.udacity.jwdnd.course1.cloudstorage.models.Credentials(newCredentials.getCredentialId(), newCredentials.getUrl(), newCredentials.getUsername(), newCredentials.getKey(), newCredentials.getPassword(), user.getUserid()));
                    redirectAttributes.addFlashAttribute("assetSuccessMessage", "Your credentials have been updated successfully!");
                }else{
                    // create new credentials in the DB
                    Integer createdCredentialId = credentialService.createNewCredentials(new com.udacity.jwdnd.course1.cloudstorage.models.Credentials(null, newCredentials.getUrl(), newCredentials.getUsername(), null, newCredentials.getPassword(), user.getUserid()));
                    if(createdCredentialId != null){
                        // credential was successfully added to the DB
                        redirectAttributes.addFlashAttribute("assetSuccessMessage", "Your credentials have been saved successfully!");
                        return "redirect:/";
                    }else{
                        // Error occurred while trying to add credential to the DB
                        redirectAttributes.addFlashAttribute("assetErrorMessage", "Error occurred while trying to save the credentials.");
                        return "redirect:/";
                    }
                }
            }
        }
        return "redirect:/";
    }

    @GetMapping("credential/delete")
    public String deleteCredential(@RequestParam(name = "credentialId") String credentialId, RedirectAttributes redirectAttributes){
        if(credentialId != null){
            int credentialToDelete = Integer.parseInt(credentialId);
            Integer deletedCredential = credentialService.deleteCredentials(credentialToDelete);
            if(deletedCredential != null){
                redirectAttributes.addFlashAttribute("assetSuccessMessage", "Your credentials have been deleted successfully!");
                return "redirect:/";
            }else{
                redirectAttributes.addFlashAttribute("assetErrorMessage", "Error occurred while trying to delete the credentials.");
                return "redirect:/";
            }
        }
        redirectAttributes.addFlashAttribute("assetErrorMessage", "Error occurred while trying to delete the credentials.");
        return "redirect:/";
    }
}
