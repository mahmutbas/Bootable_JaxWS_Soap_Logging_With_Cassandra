package com.mahmutbas.soap.bootablesoap.ws.order;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface OrderWebService
{
    String createOrder(@WebParam(name = "customerNumber") String customerNumber, @WebParam(name = "orderNumber") String orderNumber);
}
