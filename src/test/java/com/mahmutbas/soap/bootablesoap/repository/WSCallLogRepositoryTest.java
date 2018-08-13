package com.mahmutbas.soap.bootablesoap.repository;

import com.mahmutbas.soap.bootablesoap.repository.model.WSCallLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WSCallLogRepositoryTest
{
    @Autowired
    private WSCallLogRepository wsCallLogRepository;

    @Test
    public void repositoryCrudOperations()
    {
        WSCallLog wsCallLog = sampleLog();
        this.wsCallLogRepository.save(wsCallLog);
        WSCallLog savedLog = this.wsCallLogRepository.findById(wsCallLog.getId()).get();
        assertThat(savedLog.getServicename(), equalTo("TestLog"));
        this.wsCallLogRepository.delete(savedLog);
    }

    private WSCallLog sampleLog()
    {
        WSCallLog newLog = new WSCallLog();
        newLog.setId(UUID.randomUUID());
        newLog.setServicename("TestLog");
        newLog.setDuration(0.0);
        return newLog;
    }
}