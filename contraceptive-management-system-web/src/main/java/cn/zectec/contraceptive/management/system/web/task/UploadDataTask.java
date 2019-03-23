package cn.zectec.contraceptive.management.system.web.task;

import cn.zectec.contraceptive.management.system.service.impl.UploadDataService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author duanpeizhou on 2019-03-20 20:27.
 */
@Service
public class UploadDataTask {

    @Resource
    private UploadDataService uploadDataService;


//    @Scheduled(fixedRate=1000*60*2)
    public void upload() {
        uploadDataService.sendGetMedicineRecord();
    }

//    @Scheduled(fixedRate=1000*60*3)
    public void uploadMachineInfo() {
//        uploadDataService.sendMachineInfo();
    }

}
