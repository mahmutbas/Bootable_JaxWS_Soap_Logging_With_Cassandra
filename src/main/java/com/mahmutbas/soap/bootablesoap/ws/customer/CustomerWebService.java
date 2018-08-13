package com.mahmutbas.soap.bootablesoap.ws.customer;

import com.mahmutbas.soap.bootablesoap.ws.customer.model.GetCustomerInfoResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface CustomerWebService
{
    @WebMethod(operationName = "getCustomerInfo")
    @WebResult(name = "getCustomerInfoResponse")
    GetCustomerInfoResponse getCustomerInfo(@WebParam(name = "customerNumber") String customerNumber);
}
