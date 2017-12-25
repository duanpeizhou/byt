package cn.zectec.contraceptive.management.system.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonFilter {
	@SuppressWarnings("rawtypes")
	Class pojo();

	String[] allow() default {};

	String[] ignore() default {};

}
