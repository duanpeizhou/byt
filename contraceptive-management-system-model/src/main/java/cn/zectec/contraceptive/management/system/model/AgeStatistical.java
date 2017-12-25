package cn.zectec.contraceptive.management.system.model;

@SuppressWarnings("serial")
public class AgeStatistical extends Statistical {
	public static enum AgeGroup {
		Below25,Between25And40,Between41And50,Between51And60,MoreThan60,
	}
	private AgeGroup ageGroup;
	
	
	public AgeStatistical() {
		super();
	}
	public AgeStatistical(long total, long manTotal, long womanTotal,
			long countyOfCity, long countyOutCity, long provinceOutCity,
			long otherProvinces,AgeGroup ageGroup) {
		super(total, manTotal, womanTotal, countyOfCity, countyOutCity,
				provinceOutCity, otherProvinces);
		this.ageGroup = ageGroup;
	}
	public AgeGroup getAgeGroup() {
		return ageGroup;
	}
	public void setAgeGroup(AgeGroup ageGroup) {
		this.ageGroup = ageGroup;
	}

}
