package com.xiechur.spider.admin.templateMethod;

import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.List;

@Component(value = "formatSizeTemplateMethod")
public class FormatSizeTemplateMethod implements TemplateMethodModelEx {

	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List args) throws TemplateModelException {
		if (args.size() < 1) {
			throw new TemplateModelException("参数不正确");
		}
		Double resourceSizeForB = ((SimpleNumber) args.get(0)).getAsNumber().doubleValue();
		if (resourceSizeForB == null || resourceSizeForB == 0) {
			return "0K";
		}
		DecimalFormat df = new DecimalFormat("##.00");
		if (resourceSizeForB < (1024 * 1024)) {
			double resourceSizeForK = (resourceSizeForB / 1024);
			resourceSizeForK = Double.parseDouble(df.format(resourceSizeForK));
			return String.valueOf(resourceSizeForK) + "K";
		}
		if (resourceSizeForB > (1024 * 1024 * 1024)) {
			double resourceSizeForG = (((resourceSizeForB / 1024) / 1024) / 1024);
			resourceSizeForG = Double.parseDouble(df.format(resourceSizeForG));
			return String.valueOf(resourceSizeForG) + "G";
		}
		double resourceSizeForM = ((resourceSizeForB / 1024) / 1024);
		resourceSizeForM = Double.parseDouble(df.format(resourceSizeForM));
		return String.valueOf(resourceSizeForM) + "M";
	}
}
