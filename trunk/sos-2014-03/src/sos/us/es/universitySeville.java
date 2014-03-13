package sos.us.es;

public class universitySeville {
	Integer year;
	Integer enrolled;
	
	
	public universitySeville(Integer year, Integer enrolled) {
		super();
		this.year = year;
		this.enrolled = enrolled;

	}
	public universitySeville(){
		super();
	}


	public Integer getEnrolled() {
		return enrolled;
	}

	public void setEnrolled(Integer enrolled) {
		this.enrolled = enrolled;
	}

	public Integer getYear() {
		return year;
	}

	public void setYears(Integer year) {
		this.year = year;
	}
	
}
