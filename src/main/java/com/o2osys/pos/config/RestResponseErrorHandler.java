package com.o2osys.pos.config;

import java.io.IOException;

import javax.naming.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class RestResponseErrorHandler implements ResponseErrorHandler {

 // 로그
   private final Logger log = LoggerFactory.getLogger(RestResponseErrorHandler.class);
    
    @Override
    public void handleError(ClientHttpResponse clienthttpresponse) throws IOException {
        
        if (clienthttpresponse.getStatusCode() == HttpStatus.FORBIDDEN) {
            log.debug(HttpStatus.FORBIDDEN + " response. Throwing authentication exception");
            try {
                throw new AuthenticationException();
            } catch (AuthenticationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }

    @Override
    public boolean hasError(ClientHttpResponse clienthttpresponse) throws IOException {
        if (clienthttpresponse.getStatusCode() != HttpStatus.OK) {
            log.debug("Status code: " + clienthttpresponse.getStatusCode());
            log.debug("Response" + clienthttpresponse.getStatusText());
            log.debug(""+clienthttpresponse.getBody());

            if (clienthttpresponse.getStatusCode() == HttpStatus.FORBIDDEN) {
                log.debug("Call returned a error 403 forbidden resposne ");
                return true;
            }
        }
        return false;
    }

}
