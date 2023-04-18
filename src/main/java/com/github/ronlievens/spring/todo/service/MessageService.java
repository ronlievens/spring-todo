package com.github.ronlievens.spring.todo.service;

import com.github.ronlievens.spring.todo.config.TodoProperties;
import com.github.ronlievens.spring.todo.model.User;
import com.github.ronlievens.spring.todo.model.UserToken;
import com.github.ronlievens.spring.todo.model.enumeration.LanguageType;
import com.github.ronlievens.spring.todo.model.enumeration.UserTokenType;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.lang.String.format;

@RequiredArgsConstructor
@Slf4j
@Service
public class MessageService {

    private static final String DISPLAY_ADDRESS = "mail.account.display-address";
    private static final String VERIFICATION_SUBJECT = "mail.account.verification.subject";
    private static final String PASSWORD_RESET_SUBJECT = "mail.account.password.reset.subject";
    private static final String EMAIL_CHANGE_SUBJECT = "mail.account.email.change.subject";

    private static final String ENCODING = "UTF-8";

    private final TodoProperties properties;
    private final MailService mailService;
    private final MessageSource mailSource;
    private final Configuration freemarkerConfig;

    public void createMessage(final UserToken request, final User user) {
        val subject = getSubject(request.getType(), user.getLanguageCode());
        val context = new HashMap<String, Object>();
        context.put("subject", subject);
        context.put("supportEmail", properties.getSupportEmail());
        context.put("url", properties.getUrl());
        context.put("token", request.getValue());
        mailService.send(user.getEmail(), user.getFullName(),
            properties.getSupportEmail(), getDisplayAddress(user.getLanguageCode()),
            subject, generateContent(getTemplateFile(request.getType(), user.getLanguageCode()), context));
    }

    // --[ LOGIC ]-----------------------------------------------------------------------------------------------------
    private String generateContent(final String templateFile,
                                   final Map<String, Object> context) {
        try (val output = new StringWriter()) {
            val template = freemarkerConfig.getTemplate(templateFile);
            template.process(context, output);
            output.flush();
            return Base64.getEncoder().encodeToString(output.toString().getBytes(ENCODING));
        } catch (final IOException ioe) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, format("Could not open template file: %s", templateFile));
        } catch (final TemplateException te) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, format("Unable to process template file: %s", templateFile));
        } catch (final NullPointerException npe) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, format("Nullpointer: %s", npe.getMessage()));
        }
    }

    private String getTemplateFile(final UserTokenType type, final LanguageType lt) {
        switch (type) {
            case VERIFICATION:
                return format("account-verification-%s.ftl", lt.getLanguage());
            case PASSWORD_RESET:
                return format("account-password-reset-%s.ftl", lt.getLanguage());
            case CHANGE_EMAIL:
                return format("account-change-email-%s.ftl", lt.getLanguage());
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, format("Request type %s is not supported.", type));
    }

    private String getDisplayAddress(final LanguageType lt) {
        return getMessage(DISPLAY_ADDRESS, lt.getLocale());
    }

    private String getSubject(final UserTokenType type, final LanguageType lt) {
        switch (type) {
            case VERIFICATION:
                return getMessage(VERIFICATION_SUBJECT, lt.getLocale());
            case PASSWORD_RESET:
                return getMessage(PASSWORD_RESET_SUBJECT, lt.getLocale());
            case CHANGE_EMAIL:
                return getMessage(EMAIL_CHANGE_SUBJECT, lt.getLocale());
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, format("Request type %s is not supported.", type));
    }

    private String getMessage(final String label, final Locale locale) {
        return mailSource.getMessage(label, new Object[]{}, locale);
    }
}
