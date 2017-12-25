package cn.zectec.contraceptive.management.system.utils;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.zectec.contraceptive.management.system.utils.export.ExcelExport;
import cn.zectec.contraceptive.management.system.utils.export.Filed;
import cn.zectec.contraceptive.management.system.utils.export.Formatter;

public class Test {
	public static void main(String[] args) {
		ExcelExport<Student> excel = new ExcelExport<Student>();
		List<Filed<Student>> fileds = new ArrayList<>();
		Filed<Student> filed = new Filed<Student>();
		filed.setName("ID");
		filed.setFiledName("id");
		fileds.add(filed);
		Filed filed2 = new Filed<Student>();
		filed2.setName("name");
		filed2.setFiledName("name");
		fileds.add(filed2);
		Filed filed3 = new Filed<Student>();
		filed3.setName("birthday");
		filed3.setFiledName("birthday");
		filed3.setWidth(150);
		fileds.add(filed3);
		Filed filed4 = new Filed<Student>();
		filed4.setName("sex");
		filed4.setFiledName("sex");
		Filed filed5 = new Filed<Student>();
		filed5.setName("S");
		filed5.setFiledName("s.a");
		fileds.add(filed5);
		filed4.setFormaterr(new Formatter<Student>() {

			@Override
			public String formatter(Student t, int index, Filed<Student> filed) {
				if(t.sex){
					return "男";
				}else{
					return "女";
				}
			}
			
		});
		fileds.add(filed4);
		
		List<Student> contents = new ArrayList<Student>();
		contents.add(new Student(1,"1",11,true,new Date()));
		contents.add(new Student(2,"2",22,false,new Date()));
		excel.setFileds(fileds);
		excel.setContent(contents);
		try {
			FileOutputStream fo = new FileOutputStream("机器编号.xls");
			fo.write(excel.export());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static class S{
		public String a;

		public String getA() {
			return a;
		}

		public void setA(String a) {
			this.a = a;
		}


	}
	
	public static class Student {
	    private long id;
	    private String name;
	    private int age;
	    private boolean sex;
	    private Date birthday;
	    private S s;
	    

	    public Student() {
	        super();
	    }

	    public Student(long id, String name, int age, boolean sex, Date birthday) {
	        super();
	        this.id = id;
	        this.name = name;
	        this.age = age;
	        this.sex = sex;
	        this.birthday = birthday;
	        s = new S();
	        s.setA("aaaa");
	    }

	    public S getS() {
			return s;
		}

		public void setS(S s) {
			this.s = s;
		}

		public Date getBirthday() {
			return birthday;
		}

		public void setBirthday(Date birthday) {
			this.birthday = birthday;
		}

		public long getId() {
	        return id;
	    }

	    public void setId(long id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public int getAge() {
	        return age;
	    }

	    public void setAge(int age) {
	        this.age = age;
	    }

	    public boolean getSex() {
	        return sex;
	    }

	    public void setSex(boolean sex) {
	        this.sex = sex;
	    }
	}

}

