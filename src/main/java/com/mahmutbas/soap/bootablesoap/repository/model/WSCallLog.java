package com.mahmutbas.soap.bootablesoap.repository.model;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.UUID;

@Data
@Table("wsCallLog")
public class WSCallLog implements Serializable
{
    @PrimaryKey
    private UUID id;
    private String servicename;
    private String operation;
    private String request;
    private String response;
    private String requestUrl;
    private Double duration;

}
