package com.o2osys.pos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2osys.pos.service.impl.PosScheduledMapperServiceImpl;
import com.o2osys.pos.service.impl.SkyRestServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootConfiguration
//@WebAppConfiguration
//@WebMvcTest
public class PosApplicationTests {

    // @Autowired
    //private MockMvc mvd;


    private final Logger log = LoggerFactory.getLogger(PosApplicationTests.class);

    @Autowired
    SkyRestServiceImpl skyRestService;

    @Autowired
    PosScheduledMapperServiceImpl posScheduledMapperService;

    @Test
    public void contextLoads() throws Exception {

        //        skyRestService.RestSkySD0201Test();
        /*
        ArrayList<ConInfo> conList = posScheduledMapperService.bdGetConList();

        for(ConInfo conInfo : conList){

            log.info("### conList = "+CommonUtils.jsonStringFromObject(conInfo));

        }
         */

    }

}
