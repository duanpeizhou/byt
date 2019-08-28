package cn.zectec.contraceptive.management.system.sdk.message;

import org.apache.commons.codec.binary.Hex;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class TimeCheckResponseMessage extends ResponseMessage{
	private Date time = new Date();

	public TimeCheckResponseMessage(){
		this.type = 0x04;
		this.ownType = 0x01;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public byte[] bytes() {
		int i = 0;
		byte[] result = new byte[11];

		result[i++] = this.type;
		result[i++] = this.ownType;

		int t = terminalNo;
		for(int j = 0; j < 3; j ++)
		{
			int l = t % 100;
			result[i+2-j] = (byte) (((l/10)<<4) + l%10);
			t /= 100;
		}
		i += 3;

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(time);
		int year = calendar.get(Calendar.YEAR);
		year = year%100;
		result[i++] = (byte) (((year/10)<<4) + year%10);
		int month = calendar.get(Calendar.MONTH)+1;
		result[i++] = (byte) (((month/10)<<4) + month%10);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		result[i++] = (byte) (((day/10)<<4) + day%10);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		result[i++] = (byte) (((hour/10)<<4) + hour%10);
		int min = calendar.get(Calendar.MINUTE);
		result[i++] = (byte) (((min/10)<<4) + min%10);
		int second = calendar.get(Calendar.SECOND);
		result[i++] = (byte) (((second/10)<<4) + second%10);

		return result;
	}

	public static void main(String[] args) {
		TimeCheckResponseMessage msg = new TimeCheckResponseMessage();
		msg.setTerminalNo(150031);
		String s = Hex.encodeHexString(msg.bytes());
		System.out.println(s);
	}
}
