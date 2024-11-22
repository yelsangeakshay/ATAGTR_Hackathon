package API_POJO;

public class Pojo_Post_API {
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	
	public String getid() {
		return id;
	}
	
	public String getcreatedAt() {
		return createdAt;
	}
	
	private String name;
	private String job;
	private String id;
	private String createdAt;

}
