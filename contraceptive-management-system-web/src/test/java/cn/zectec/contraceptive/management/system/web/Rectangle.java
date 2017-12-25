package cn.zectec.contraceptive.management.system.web;

public class Rectangle {
	private double width;
	private double length;
	
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public double getArea(){
		return this.width*this.length;
	}
	public String toString(){
		return "矩形的长为："+this.length+";矩形的宽为："+this.width+";矩形的面积为："+getArea();
	}
	
}
