package cn.zectec.contraceptive.management.system.service.impl;

import cn.zectec.contraceptive.management.system.manager.IMachineryEquipmentManager;
import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.service.IGetMedicineRecordService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 畅速 2019/03/20 下午3:18
 * 名称：上海葆青电子有限公司
 *
 * angentCode：BJBQ
 * 接口域名（APP_HOST）：http://beijing.yaoju.org.cn/fafangji/
 * appId：maxd6675830502c46ab
 * token：2efd2b16907d40cea12de9ff335d5b31
 *
 *
 * @author duanpeizhou on 2019-03-20 20:49.
 */
@Service
public class UploadDataService {
    private static Logger logger=Logger.getLogger(UploadDataService.class);

    private static final String SEND_MEDICINE_RECORD_URL = "http://beijing.yaoju.org.cn/fafangji/cgi-bin/machleadrecord";
//    private static final String SEND_MEDICINE_RECORD_URL = "http://127.0.0.1:6060/data/record";

//    private static final String SEND_MACHINE_URL = "http://127.0.0.1:6060/data/record";
    private static final String SEND_MACHINE_URL = "http://beijing.yaoju.org.cn/fafangji/cgi-bin/machineinfo";

    private static final String APP_ID = "maxd6675830502c46ab";

    private static final String TOKEN = "2efd2b16907d40cea12de9ff335d5b31";

    private static final String SIGN_BASE = APP_ID + TOKEN;

    @Resource
    private IGetMedicineRecordService getMedicineRecordService;
    @Resource
    private IMachineryEquipmentManager machineryEquipmentManager;
    private volatile boolean isSendingRecord = false;
    private volatile boolean isSendingMachInfo = false;

    private HttpClient httpClient = new HttpClient();


    public void sendGetMedicineRecord() {
        try {

            logger.info("开始上传药具领用记录");
            if (isSendingRecord) {
                logger.info("正在上传药具领用记录");
                return;
            }
            List<GetMedicineRecord> notSentRecords = getMedicineRecordService.find10NotSentRecords();
            if (notSentRecords.isEmpty()) {
                return;
            }
            isSendingRecord = true;
            List<MedicineRecordDto> dto = new ArrayList<>();
            for (GetMedicineRecord notSentRecord : notSentRecords) {
                dto.add(new MedicineRecordDto(notSentRecord));
            }
            JSONObject result = post(JSON.toJSONString(dto), SEND_MEDICINE_RECORD_URL);
            logger.info("上传数据响应 = " + result);
            if (result.containsKey("code") && result.getIntValue("code") == 0) {
                for (GetMedicineRecord notSentRecord : notSentRecords) {
                    getMedicineRecordService.updateSendStatus(true, notSentRecord.getId());
                }
                logger.info("上传药具领用记录成功 数量 = " + notSentRecords.size());
            }
            isSendingRecord = false;
        } catch (Exception e) {
            logger.error("上传药具领用记录失败", e);
        } finally {
            isSendingRecord = false;
        }

    }

    public void sendMachineInfo() {
        try {
            logger.info("开始上传机器状态信息");
            if (isSendingMachInfo) {
                logger.info("正在上传机器状态信息");
                return;
            }
            List<MachineryEquipment> all = machineryEquipmentManager.findAll();
            if (all.isEmpty()) {
                return;
            }
            isSendingMachInfo = true;
            List<MachineInfoDto> dtoList = new ArrayList<>();
            int size = 1;
            for (MachineryEquipment me : all) {
                dtoList.add(new MachineInfoDto(me));
                if (size % 50 == 0) {
                    JSONObject result = post(JSON.toJSONString(dtoList), SEND_MACHINE_URL);
                    if (result.containsKey("code") && result.getIntValue("code") == 0) {
                        logger.info("上传机器状态成功");
                    }
                    dtoList.clear();
                }
                size++;
            }
            JSONObject result = post(JSON.toJSONString(dtoList), SEND_MACHINE_URL);
            if (result.containsKey("code") && result.getIntValue("code") == 0) {
                logger.info("上传机器状态成功");
            }
            isSendingMachInfo = false;
        } catch (Exception e) {
            logger.error("上传状态信息有误", e);
        } finally {
            isSendingMachInfo = false;
        }
    }

    private JSONObject post(String data, String url) {
        try {
            PostMethod postMethod = new PostMethod(url + getParameters());
            RequestEntity requestEntity = new StringRequestEntity(data, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            httpClient.executeMethod(postMethod);
            String responseBody = postMethod.getResponseBodyAsString();
            return JSON.parseObject(responseBody, JSONObject.class);
        } catch (Exception e) {
            logger.error("上传数据失败：" + url, e);
        }
        return new JSONObject();
    }

    public String getParameters() {
        long millis = System.currentTimeMillis();
        StringBuilder builder = new StringBuilder();
        builder.append("?appId=").append(APP_ID);
        builder.append("&timestamp=").append(millis);
        String sign = DigestUtils.md5Hex(SIGN_BASE + millis);
        builder.append("&sign=").append(sign);
        return builder.toString();

    }

}
