package com.mahmutbas.soap.bootablesoap.ws.customer.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "http://customer.ws.bootablesoap.soap.mahmutbas.com/getCustomerInfo")
@Data
public class GetCustomerInfoResponse
{
    private String customerNo;
    private String msisdn;
    private String customerName;
    private String customerAddress;
}
