package com.mountbirch.commons.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;


/**
 * Created by Kaur Laanemäe on 22/11/2018.
 * Mountbirch OÜ
 * kaur@mountbirch.com
 */
@Service
public class DocumentGenerationService {

    private TemplateEngine templateEngine;

    @Autowired
    public DocumentGenerationService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String createHtmlFromTemplate(String templateName, Map params){
        Context context = new Context();
        if (params != null) {
            for (Object entryObject : params.entrySet()) {
                Map.Entry entry = (Map.Entry) entryObject;
                context.setVariable(entry.getKey().toString(), entry.getValue());
            }
        }

        return templateEngine.process(templateName, context);
    }
}
