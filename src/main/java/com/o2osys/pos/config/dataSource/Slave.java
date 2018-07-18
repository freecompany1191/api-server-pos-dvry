package com.o2osys.pos.config.dataSource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author stunstun(minhyuck.jung@nhnent.com)
 *
 */

@Target(ElementType.TYPE)
//@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Slave {

}
