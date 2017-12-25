package cn.zectec.contraceptive.management.system.web;

public class Square extends Rectangle{

	
	public Square(double bianchang){
		this.setLength(bianchang);
		this.setWidth(bianchang);
	}
	
	public double getArea() {
		
		return super.getArea();
	}
	
	public String toString() {
		return "正方形的边长为："+this.getLength()+"，面积为："+getArea();
	}
	public static void main(String[] args) {
		Square s = new Square(6.5);
		String ss = s.toString();
		System.out.println(ss);
	}

}
