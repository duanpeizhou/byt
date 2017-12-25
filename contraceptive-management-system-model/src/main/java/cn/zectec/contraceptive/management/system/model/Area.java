package cn.zectec.contraceptive.management.system.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 地理区域
 * @author wxy
 *
 */
@SuppressWarnings("serial")
public class Area extends Base{
	public static enum Level{
		/**省*/
		Province,
		/**市*/
		City,
		/**区县级*/
		County,
		/**乡级  街道*/
		TownshipStreet,
		/**村级  社区*/
		Community
	}
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 排序
	 */
	private int order;
	/**
	 * 父区域
	 */
	private Area parentArea;
	/**
	 * 子区域
	 */
	private List<Area> childAreas = new ArrayList<Area>();
	/**
	 * 级别
	 */
	private Level level;
	/**
	 * 编号  身份证有关 
	 */
	private Long no;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Area getParentArea() {
		return parentArea;
	}
	public void setParentArea(Area parentArea) {
		this.parentArea = parentArea;
	}

	public List<Area> getChildAreas() {
		return childAreas;
	}
	public void setChildAreas(List<Area> childAreas) {
		this.childAreas = childAreas;
	}
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}

}
