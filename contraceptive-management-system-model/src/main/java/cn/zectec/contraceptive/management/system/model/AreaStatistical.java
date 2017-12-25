package cn.zectec.contraceptive.management.system.model;


@SuppressWarnings("serial")
public class AreaStatistical extends Statistical{

	public AreaStatistical() {
		super();
	}

	public AreaStatistical(long total, long manTotal, long womanTotal,
			long countyOfCity, long countyOutCity, long provinceOutCity,
			long otherProvinces,Area area) {
		super(total, manTotal, womanTotal, countyOfCity, countyOutCity,provinceOutCity, otherProvinces);
		this.setArea(area);
	}
	
}
