package cn.zectec.contraceptive.management.system.sender;
/*
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;
import cn.zectec.contraceptive.management.system.repository.IGetMedicineRecordRepository;
import cn.zectec.contraceptive.management.system.sender.util.Encoder;
*/

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/application-context.xml" })


public class SendString {

	@Autowired
	private IGetMedicineRecordRepository record;
	List<GetMedicineRecord> records;
	@Autowired
	Encoder encoder;
	
	@Test
	public void getClientString() throws UnsupportedEncodingException{
		records = record.findNotSendRecords();
		String result = encoder.encode2String(records);
		
		System.out.println(result);
		byte[] b = result.getBytes("utf-8");
		for(int i=0;i<b.length;i++){
			System.out.printf("%2x ",b[i]);
		}
	}
	

}*/
