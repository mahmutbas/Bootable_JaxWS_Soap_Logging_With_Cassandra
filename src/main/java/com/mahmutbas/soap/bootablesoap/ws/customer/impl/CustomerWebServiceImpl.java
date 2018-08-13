package com.mahmutbas.soap.bootablesoap.ws.customer.impl;

import com.mahmutbas.soap.bootablesoap.ws.customer.CustomerWebService;
import com.mahmutbas.soap.bootablesoap.ws.customer.model.GetCustomerInfoResponse;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@SOAPBinding
@WebService(endpointInterface = "com.mahmutbas.soap.bootablesoap.ws.customer.CustomerWebService", serviceName = "CustomerService")
public class CustomerWebServiceImpl implements CustomerWebService
{
    @Override
    public GetCustomerInfoResponse getCustomerInfo(String customerNumber)
    {
        return new GetCustomerInfoResponse();
    }
}
