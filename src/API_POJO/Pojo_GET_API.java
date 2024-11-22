package API_POJO;

import java.util.List;

public class Pojo_GET_API {

	private int page;
	private int per_page;
	private int total;
	private int total_pages;
	private List<Pojo_GET_API_Data_Array> data;
	private Pojo_GET_API_Support support;

	public int getPage() {
		return page;
	}

	public int getPer_page() {
		return per_page;
	}

	public int getTotal() {
		return total;
	}

	public int getTotal_pages() {
		return total_pages;
	}

	public List<Pojo_GET_API_Data_Array> getData() {
		return data;
	}
	
	public Pojo_GET_API_Support getSupport() {
		return support;
	}

}
