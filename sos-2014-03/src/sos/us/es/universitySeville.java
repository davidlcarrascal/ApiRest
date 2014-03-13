package sos.us.es;

public class universitySeville {
	Integer year;
	Integer enrolled;
	Integer budget;
	
	public universitySeville(Integer year, Integer enrolled, Integer budget) {
		super();
		this.year = year;
		this.enrolled = enrolled;
		this.budget = budget;
	}
	public universitySeville(){
		super();
	}
	public Integer getBudget() {
		return budget;
	}

	public void setBudget(Integer budget) {
		this.budget = budget;
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
