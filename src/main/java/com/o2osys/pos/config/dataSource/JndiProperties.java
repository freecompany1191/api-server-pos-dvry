package com.o2osys.pos.config.dataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
   @FileName  : JndiProdProperties.java
   @Description :
   @author      : KMS
   @since       : 2018. 4. 26.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2018. 4. 26.    KMS            최초생성

 */
@Component
@Data
public class JndiProperties {

    @Value("${spring.datasource.master.jndi-name}")
    private String jndiMaster;
    @Value("${spring.datasource.slave.jndi-name}")
    private String jndiSlave;

}
