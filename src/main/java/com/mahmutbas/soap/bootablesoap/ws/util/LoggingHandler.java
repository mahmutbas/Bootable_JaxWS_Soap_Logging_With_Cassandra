package com.mahmutbas.soap.bootablesoap.ws.util;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Set;


public class LoggingHandler extends SpringBeanAutowiringSupport implements SOAPHandler<SOAPMessageContext>
{
    private static final Logger logger = LoggerFactory.getLogger(LoggingHandler.class);

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
            context.put("STOP_WATCH", stopwatch);
        }
        catch (Exception exception)
        {
            logger.debug(exception.getStackTrace().toString());
        }
        return true;
    }

    protected boolean handleOutbound(SOAPMessageContext context)
    {
        try
        {
            logger.debug("Output: " + (WsHandlerUtil.convertSOAPMessageToXML(context.getMessage())));
            logger.debug("Response Time: " + (context.get("STOP_WATCH")));
        }
        catch (Exception exception)
        {
            logger.debug(exception.getStackTrace().toString());
        }
        return true;
    }
}
