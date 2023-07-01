package com.dilay.youtubemusic.controller;

import com.dilay.youtubemusic.model.request.contractrecord.UpdateContractRecordRequest;
import com.dilay.youtubemusic.model.response.ContractRecordResponse;
import com.dilay.youtubemusic.service.AuthenticationService;
import com.dilay.youtubemusic.service.ContractRecordService;
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
