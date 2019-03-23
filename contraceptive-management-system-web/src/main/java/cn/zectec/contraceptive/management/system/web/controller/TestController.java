package cn.zectec.contraceptive.management.system.web.controller;

import cn.zectec.contraceptive.management.system.service.impl.UploadDataService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author duanpeizhou on 2019-03-22 22:15.
 */
@Controller
@RequestMapping("/test")
public class TestController {
    @Resource
    private UploadDataService uploadDataService;

    @RequestMapping("/record")
    public void uploadRecord() {
        uploadDataService.sendGetMedicineRecord();
    }
    @RequestMapping("/info")
    public void uploadInfo() {
        uploadDataService.sendMachineInfo();
    }
}
