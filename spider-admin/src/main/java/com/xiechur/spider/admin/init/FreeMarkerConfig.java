package com.xiechur.spider.admin.init;

import cn.org.rapid_framework.freemarker.directive.BlockDirective;
import cn.org.rapid_framework.freemarker.directive.ExtendsDirective;
import cn.org.rapid_framework.freemarker.directive.OverrideDirective;
import cn.org.rapid_framework.freemarker.directive.SuperDirective;
import com.xiechur.spider.admin.templateMethod.CentralTemplateMethod;
import com.xiechur.spider.admin.templateMethod.FormatSizeTemplateMethod;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class FreeMarkerConfig {

	@Autowired
	protected freemarker.template.Configuration configuration;
	@Autowired
	protected org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver resolver;
	@Autowired
	protected org.springframework.web.servlet.view.InternalResourceViewResolver springResolver;
	@Autowired
	@Qualifier("centralTemplateMethod")
	private CentralTemplateMethod templateMethod;
	@Autowired
	@Qualifier("formatSizeTemplateMethod")
	private FormatSizeTemplateMethod formatSizeTemplateMethod;

	@Value("${domain.admin}")
	private String domainAdmin;
	@Value("${domain.web}")
	private String domainWeb;
	
	@PostConstruct
	public void setSharedVariable() {
		configuration.setSharedVariable("extends", new ExtendsDirective());
		configuration.setSharedVariable("override", new OverrideDirective());
		configuration.setSharedVariable("block", new BlockDirective());
		configuration.setSharedVariable("super", new SuperDirective());
		configuration.setSharedVariable("central", templateMethod);
		configuration.setSharedVariable("format_size", formatSizeTemplateMethod);
		try {
			configuration.setSharedVariable("domain_admin", domainAdmin);
			configuration.setSharedVariable("domain_web", domainWeb);
			configuration.setSetting("template_update_delay", "0");
			configuration.setSetting("default_encoding", "UTF-8");
			configuration.setSetting("classic_compatible", "true");
			configuration.setSetting("number_format", "0");
			configuration.setClassForTemplateLoading(this.getClass(), "/templates");
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		springResolver.setSuffix(".html");
		resolver.setSuffix(".html");
	}
}
