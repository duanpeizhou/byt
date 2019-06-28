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


    @Scheduled(cron = "0 0/5 22,23,0-5 * * *")
    public void upload() {
        uploadDataService.sendGetMedicineRecord();
    }

    @Scheduled(fixedRate=1010*60*3)
    public void uploadMachineInfo() {
        uploadDataService.sendMachineInfo();
    }

}
