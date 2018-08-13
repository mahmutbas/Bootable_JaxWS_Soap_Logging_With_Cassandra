package com.mahmutbas.soap.bootablesoap.ws.util;

import com.google.common.base.Stopwatch;
import com.mahmutbas.soap.bootablesoap.repository.WSCallLogRepository;
import com.mahmutbas.soap.bootablesoap.repository.model.WSCallLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class LoggingHandler extends SpringBeanAutowiringSupport implements SOAPHandler<SOAPMessageContext>
{
    private static final Logger logger = LoggerFactory.getLogger(LoggingHandler.class);

    @Autowired
    private WSCallLogRepository wsCallLogRepository;

    @Override
    public Set<QName> getHeaders()
    {
        return null;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context)
    {


        Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (outbound == null)
        {
            throw new IllegalStateException("Cannot obtain required property: " + MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        }


        return !outbound ? handleInbound(context) : handleOutbound(context);
    }

    @Override
    public boolean handleFault(SOAPMessageContext context)
    {
        return false;
    }

    @Override
    public void close(MessageContext context)
    {

    }

    protected boolean handleInbound(SOAPMessageContext context)
    {
        try
        {
            Stopwatch stopwatch = Stopwatch.createUnstarted();
            stopwatch.start();
            logger.debug("Operation Name: " + ((QName) context.get("javax.xml.ws.wsdl.operation")).getLocalPart());
            logger.debug("Service Name: " + ((QName) context.get("javax.xml.ws.wsdl.service")).getLocalPart());
            logger.debug("Requester Url: " + (context.get("com.sun.xml.ws.transport.http.servlet.requestURL").toString()));
            logger.debug("Input: " + (WsHandlerUtil.convertSOAPMessageToXML(context.getMessage())));
            context.put("INPUT_LOG", WsHandlerUtil.convertSOAPMessageToXML(context.getMessage()));
            context.put("STOP_WATCH", stopwatch);
        }
        catch (Exception exception)
        {
            logger.error(exception.getStackTrace().toString());
        }
        return true;
    }

    protected boolean handleOutbound(SOAPMessageContext context)
    {
        try
        {
            WSCallLog wsCallLog = new WSCallLog();
            wsCallLog.setId(UUID.randomUUID());
            wsCallLog.setServicename(((QName) context.get("javax.xml.ws.wsdl.service")).getLocalPart());
            wsCallLog.setOperation(((QName) context.get("javax.xml.ws.wsdl.operation")).getLocalPart());
            wsCallLog.setRequest(context.get("com.sun.xml.ws.transport.http.servlet.requestURL").toString());
            Stopwatch stopwatch = (Stopwatch) context.get("STOP_WATCH");
            wsCallLog.setDuration(Double.valueOf(stopwatch.elapsed(TimeUnit.MILLISECONDS)));
            wsCallLog.setRequest(context.get("INPUT_LOG").toString());
            wsCallLog.setResponse(WsHandlerUtil.convertSOAPMessageToXML(context.getMessage()));
            saveLogs(wsCallLog);
        }
        catch (Exception exception)
        {
            logger.error(exception.getStackTrace().toString());
        }
        return true;
    }

    public WSCallLog saveLogs(WSCallLog wsCallLog)
    {
        wsCallLog = this.wsCallLogRepository.save(wsCallLog);
        return wsCallLog;
    }
}
