package cn.zectec.contraceptive.management.system.model;


public class ContraceptiveStatistical extends Statistical{
	private Contraceptive contraceptive;

	
	
	public ContraceptiveStatistical() {
		super();
	}

	public ContraceptiveStatistical(long total, long manTotal, long womanTotal,
			long countyOfCity, long countyOutCity, long provinceOutCity,
			long otherProvinces,Contraceptive contraceptive) {
		super(total, manTotal, womanTotal,
				countyOfCity, countyOutCity, provinceOutCity, otherProvinces);
		this.contraceptive = contraceptive;
	}

	public Contraceptive getContraceptive() {
		return contraceptive;
	}

	public void setContraceptive(Contraceptive contraceptive) {
		this.contraceptive = contraceptive;
	}
	
	
}
