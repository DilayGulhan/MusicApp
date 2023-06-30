package com.dilay.contentserviceapi.controller;

import com.dilay.contentserviceapi.model.request.contractrecord.UpdateContractRecordRequest;
import com.dilay.contentserviceapi.model.response.ContractRecordResponse;
import com.dilay.contentserviceapi.service.AuthenticationService;
import com.dilay.contentserviceapi.service.ContractRecordService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/record")
public class ContractRecordController {
private final AuthenticationService authenticationService ;
private final ContractRecordService contractRecordService ;


    @PutMapping("/update")
    public ContractRecordResponse updateRecord( @Valid @RequestBody UpdateContractRecordRequest contractRecord ){
        return contractRecordService.updateContractRecord(authenticationService.getAuthenticatedUserId(), contractRecord);

    }
    @PostMapping("/{subscriptionName}")
    public ContractRecordResponse addRecord( @PathVariable String subscriptionName){
        return contractRecordService.addContractRecord(authenticationService.getAuthenticatedUserId(), subscriptionName  );
    }


}
