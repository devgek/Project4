package enums;

public enum ProjectType {
	VVO("VVO", 1),
	PIPE("PIPE", 2),
	KAHRER_SOFTWARE("KAHRER_SOFTWARE", 3);
	
	private String code;
	private int priority;
	
	ProjectType(String code, int priority) {
		this.code = code;
		this.priority = priority;
	}

	public String getCode() {
		return code;
	}

	public int getPriority() {
		return priority;
	}
	
}
