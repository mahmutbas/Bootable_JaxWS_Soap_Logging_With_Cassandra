package com.mahmutbas.soap.bootablesoap.ws.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.jaxws.JaxWsSoapFaultException;
import org.xml.sax.InputSource;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.soap.SOAPFaultException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class WsHandlerUtil
{
    private static final Logger logger = LoggerFactory.getLogger(WsHandlerUtil.class);

    public static String convertSOAPMessageToXML(SOAPMessage message)
    {
        OutputStream outputStream = null;
        try
        {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            message.writeTo(byteArrayOutputStream);
            outputStream = getXMLMessageOutputStream(byteArrayOutputStream.toByteArray());
            return new String(((ByteArrayOutputStream) outputStream).toByteArray());
        }
        catch (Exception exception)
        {
            logger.error(exception.getStackTrace().toString());
            throw generateSOAPFault(message, exception.getMessage());
        }
        finally
        {
            safeClose(outputStream);
        }
    }

    private static OutputStream getXMLMessageOutputStream(byte[] out) throws TransformerException
    {
        OutputStream outputStream;
        Transformer serializer = SAXTransformerFactory.newInstance().newTransformer();
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        Source xmlSource = new SAXSource(new InputSource(new ByteArrayInputStream(out)));
        StreamResult res = new StreamResult(new ByteArrayOutputStream());
        serializer.transform(xmlSource, res);
        outputStream = res.getOutputStream();
        return outputStream;
    }


    private static void safeClose(OutputStream out)
    {
        if (out != null)
        {
            try
            {
                out.close();
            }
            catch (IOException exception)
            {
                logger.error(exception.getStackTrace().toString());
            }
        }
    }

    public static JaxWsSoapFaultException generateSOAPFault(SOAPMessage soapMessage, String reason)
    {
        try
        {
            SOAPBody body = soapMessage.getSOAPPart().getEnvelope().getBody();
            SOAPFault fault = body.addFault();
            fault.setFaultString(reason);
            return new JaxWsSoapFaultException(new SOAPFaultException(fault));
        }
        catch (SOAPException exception)
        {
            logger.error(exception.getStackTrace().toString());
            throw new RuntimeException(exception);
        }
    }
}
