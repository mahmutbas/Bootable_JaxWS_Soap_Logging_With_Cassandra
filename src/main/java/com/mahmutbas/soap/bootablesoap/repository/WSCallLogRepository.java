package com.mahmutbas.soap.bootablesoap.repository;

import com.mahmutbas.soap.bootablesoap.repository.model.WSCallLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.UUID;

@Repository
public interface WSCallLogRepository extends CrudRepository<WSCallLog,UUID>
{

}
