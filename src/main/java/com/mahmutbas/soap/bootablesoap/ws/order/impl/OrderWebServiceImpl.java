package com.mahmutbas.soap.bootablesoap.ws.order.impl;

import com.mahmutbas.soap.bootablesoap.ws.order.OrderWebService;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@SOAPBinding
@WebService(endpointInterface = "com.mahmutbas.soap.bootablesoap.ws.order.OrderWebService", serviceName = "OrderService")
public class OrderWebServiceImpl implements OrderWebService
{
    @Override
    public String createOrder(String customerNumber, String orderNumber)
    {
        return "success";
    }
}
