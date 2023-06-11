package com.researchecosystems.contentserviceapi.controller;

import com.researchecosystems.contentserviceapi.model.request.contractrecord.UpdateContractRecordRequest;
import com.researchecosystems.contentserviceapi.model.response.ContractRecordResponse;
import com.researchecosystems.contentserviceapi.service.AuthenticationService;
import com.researchecosystems.contentserviceapi.service.ContractRecordService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/record")
public class ContractRecordController {
private final AuthenticationService authenticationService ;
private final ContractRecordService contractRecordService ;

@PostMapping("/{subscriptionName}")
public ContractRecordResponse addRecord( @PathVariable String subscriptionName){
        return contractRecordService.addContractRecord(authenticationService.getAuthenticatedUserId(), subscriptionName  );

    }
    @PutMapping
    public ContractRecordResponse updateRecord( @Valid @RequestBody UpdateContractRecordRequest contractRecord ){
        return contractRecordService.updateContractRecord(authenticationService.getAuthenticatedUserId(), contractRecord);

    }

}
