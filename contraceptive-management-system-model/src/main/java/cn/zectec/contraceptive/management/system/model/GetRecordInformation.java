package cn.zectec.contraceptive.management.system.model;

public class GetRecordInformation {
	private GiveOutInfo GiveOutInfo;
	private IDCardInfo IDCardInfo;
	public GetRecordInformation(GiveOutInfo giveOutInfo,
			IDCardInfo iDCardInfo) {
		super();
		setGiveOutInfo(giveOutInfo);
		setIDCardInfo(iDCardInfo);
	}
	public IDCardInfo getIDCardInfo() {
		return IDCardInfo;
	}
	public void setIDCardInfo(IDCardInfo iDCardInfo) {
		IDCardInfo = iDCardInfo;
	}
	public GiveOutInfo getGiveOutInfo() {
		return GiveOutInfo;
	}
	public void setGiveOutInfo(GiveOutInfo giveOutInfo) {
		GiveOutInfo = giveOutInfo;
	}
	
}
