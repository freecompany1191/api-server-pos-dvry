package com.o2osys.pos.config.dataSource;

import java.util.HashMap;
import java.util.Map;

/**
   @FileName  : MultiDataSourcePlatformConfiguratorImpl.java
   @Description :
   Implements the {@link MultiDataSourcePlatformConfigurator}.
   Going to be injected at {@link de.laboranowitsch.jndimulti.repo.ItemRepoImpl}
   and {@link de.laboranowitsch.jndimulti.repo.PeopleRepoImpl}.

   @author      : KMS
   @since       : 2018. 4. 26.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2018. 4. 26.    KMS            최초생성

 */
public class MultiDataSourcePlatformConfiguratorImpl implements MultiDataSourcePlatformConfigurator {

    private Map<String, DataSourceDetails> dataSourceDetailsMap = new HashMap<>();

    @Override
    public void addDetails(String key, DataSourceDetails dataSourceDetails) {
        dataSourceDetailsMap.put(key, dataSourceDetails);
    }

    @Override
    public DataSourceDetails getDetails(String key) {
        return dataSourceDetailsMap.get(key);
    }
}
