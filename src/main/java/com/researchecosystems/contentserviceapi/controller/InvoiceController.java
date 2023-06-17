package com.researchecosystems.contentserviceapi.controller;

import com.researchecosystems.contentserviceapi.entity.Invoice;
import com.researchecosystems.contentserviceapi.model.request.Payment.CreatePaymentRequest;
import com.researchecosystems.contentserviceapi.model.response.InvoiceResponse;
import com.researchecosystems.contentserviceapi.model.response.SubscriptionResponse;
import com.researchecosystems.contentserviceapi.service.AuthenticationService;
import com.researchecosystems.contentserviceapi.service.InvoiceService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/invoice")
public class InvoiceController {

    private final AuthenticationService authenticationService;
    private final InvoiceService invoiceService;

    @GetMapping
    @ApiPageable
    public Page<InvoiceResponse> listInvoice(@ApiIgnore Pageable pageable ) {
        return invoiceService.listInvoice(pageable  , authenticationService.getAuthenticatedUserId());

    }

    @PostMapping("/{contractRecordId}")
    public InvoiceResponse payInvoice(@Valid @RequestBody CreatePaymentRequest
                                                  paymentRequest,
                                      @PathVariable String contractRecordId){

return invoiceService.payInvoice(paymentRequest, authenticationService.getAuthenticatedUserId(), contractRecordId);
        
    }


//    @GetMapping("/seeInvoice/{contractRecordId}")
//    public InvoiceResponse getInvoiceBySelf(@PathVariable String contractRecordId){
//        return invoiceService.getInvoice(contractRecordId , authenticationService.getAuthenticatedUserId());
//    }

    @GetMapping("/{invoiceId}")
    public InvoiceResponse getInvoicebyAdmin(@PathVariable String invoiceId){
    return invoiceService.getInvoiceByAdmin(invoiceId , authenticationService.getAuthenticatedUserId());
    }
}
