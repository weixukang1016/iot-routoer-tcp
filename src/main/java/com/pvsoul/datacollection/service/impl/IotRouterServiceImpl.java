package com.pvsoul.datacollection.service.impl;

//import com.pvsoul.yuandongiotrouter.entity.Test;
//import com.pvsoul.yuandongiotrouter.mapper.TestMapper;
import com.pvsoul.datacollection.service.IotRouterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IotRouterServiceImpl implements IotRouterService {

    //@Autowired
    //private TestMapper testMapper;

    @Override
    public void saveData(String data) {
        log.info(data);
    }
}
