package com.o2osys.pos.config;

import java.sql.SQLException;
import java.util.Locale;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.o2osys.pos.common.constants.Define;


@ControllerAdvice
public class ControllerErrorHandler {
    //log추가
    @Autowired
    private MessageSource mMessageSource;

    @ExceptionHandler(SQLException.class)
    public void sqlException(HttpServletRequest request, ServletResponse servletResponse, SQLException e)
            throws Exception {
        write((HttpServletResponse) servletResponse, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "디비 에러");
    }

    @ExceptionHandler(Exception.class)
    public void exception(HttpServletRequest request, ServletResponse servletResponse, Exception e) throws Exception {
        write((HttpServletResponse) servletResponse, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void methodArgumentNotValidException(MethodArgumentNotValidException ex, ServletResponse servletResponse,
            HttpServletRequest request) throws Exception {
        BindingResult bindingResult = ex.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();

        write((HttpServletResponse) servletResponse, HttpServletResponse.SC_BAD_REQUEST,
                getMessage(fieldError, request.getParameter(Define.Param.LANG_CODE)));
    }

    /**
     * 에러메시지를가져온다.
     *
     * @param error
     * @param lang
     * @return
     */
    private String getMessage(FieldError error, String lang) {
        if (error == null) {
            return "";
        }

        return mMessageSource.getMessage(error.getDefaultMessage(), null, new Locale(lang));
    }

    /**
     * 에러메시지를 보여준다.
     *
     * @param response
     * @param code
     * @param message
     * @throws Exception
     */
    private void write(HttpServletResponse response, int code, String message) throws Exception {
        if (response == null) {
            return;
        }

        response.resetBuffer();
        response.setHeader("Content-Type", "text/html");
        response.setStatus(code);
        if (StringUtils.isNotEmpty(message)) {
            response.getOutputStream().write(message.getBytes("utf-8"));
        }
        response.flushBuffer();
    }
}
