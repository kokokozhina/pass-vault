package com.kokokozhina.diploma.controller;

import com.kokokozhina.diploma.dto.StringMessage;
import com.kokokozhina.diploma.model.Secret;
import com.kokokozhina.diploma.service.SecretService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@Api(value = "Secret Management", description = "Manage your secrets")
@RequestMapping("/")
public class SecretController {

    private static final String CREATE_UPDATE_SECRET_ENDPOINT = "/createUpdateSecret";
    private static final String GET_SECRETS_NAMES_LIST_ENDPOINT = "/getSecretsNamesList";
    private static final String GET_SECRET_BY_NAME_ENDPOINT = "/getSecretByName";
    private static final String DELETE_SECRET_ENDPOINT = "/deleteSecret";

    @Autowired
    private SecretService secretService;

    @ResponseBody
    @RequestMapping(value = CREATE_UPDATE_SECRET_ENDPOINT, method = RequestMethod.POST, consumes = {"application/json"})
    @ApiOperation(value = "Create ot update a secret", response = StringMessage.class)
    public ResponseEntity<Object> createSecret(@RequestBody Secret secret) {
        secretService.createOrUpdateSecret(secret);
        return new ResponseEntity<>(new StringMessage("Secret was successfully created"), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = GET_SECRETS_NAMES_LIST_ENDPOINT, method = RequestMethod.GET)
    @ApiOperation(value = "Get secrets names", response = Set.class)
    public ResponseEntity<Object> getSecretsList() {
        return new ResponseEntity<>(secretService.getSecretNamesList(), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = GET_SECRET_BY_NAME_ENDPOINT, method = RequestMethod.GET)
    @ApiOperation(value = "Get secret value by name", response = Object.class)
    public ResponseEntity<Object> getSecretByName(@RequestParam String secretName) {
        try {
            Secret secret = secretService.getSecretValue(secretName);
            return new ResponseEntity<>(secret, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new StringMessage("Secret with this name was not found"), HttpStatus.CONFLICT);
        }
    }

    @ResponseBody
    @RequestMapping(value = DELETE_SECRET_ENDPOINT, method = RequestMethod.POST)
    @ApiOperation(value = "Delete secret by name", response = StringMessage.class)
    public ResponseEntity<Object> deleteSecretByName(@RequestParam String secretName) {
        secretService.deleteSecret(secretName);
        return new ResponseEntity<>(new StringMessage("Secret was successfully deleted"), HttpStatus.OK);
    }

}
