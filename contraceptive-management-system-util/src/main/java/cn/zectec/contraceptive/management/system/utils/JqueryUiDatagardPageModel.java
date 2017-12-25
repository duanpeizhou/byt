package cn.zectec.contraceptive.management.system.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JqueryUiDatagardPageModel<T> {
	private long total;
	private List<T> rows;
	private List<Map<String, Object>> footer = new ArrayList<Map<String, Object>>();

	public JqueryUiDatagardPageModel(long total, List<T> rows)
	{
		super();
		this.total = total;
		this.rows = rows;
	}

	public JqueryUiDatagardPageModel(long total, List<T> rows,
			List<Map<String, Object>> footer)
	{
		super();
		this.total = total;
		this.rows = rows;
		this.footer = footer;
	}

	public long getTotal()
	{
		return total;
	}

	public void setTotal(long total)
	{
		this.total = total;
	}

	public List<T> getRows()
	{
		return rows;
	}

	public void setRows(List<T> rows)
	{
		this.rows = rows;
	}

	public List<Map<String, Object>> getFooter()
	{
		return footer;
	}

	public void setFooter(List<Map<String, Object>> footer)
	{
		this.footer = footer;
	}

	@Override
	public String toString()
	{
		return "JqueryUiDatagardPageModel [total=" + total + ", rows=" + rows
				+ ", footer=" + footer + "]";
	}

}
