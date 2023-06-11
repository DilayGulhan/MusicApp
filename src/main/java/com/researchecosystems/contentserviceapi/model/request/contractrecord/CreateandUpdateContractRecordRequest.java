package com.researchecosystems.contentserviceapi.model.request.contractrecord;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateandUpdateContractRecordRequest {
    private String name ;
    private boolean isActive ;


}
