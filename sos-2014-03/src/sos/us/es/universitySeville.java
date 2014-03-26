package sos.us.es;

public class universitySeville {
	Integer year;
	Integer enrolled;
	Integer budget;
	Integer employability;
	Integer studentMigrants;
	
	public universitySeville(Integer year, Integer enrolled,Integer budget, Integer employability, Integer studentMigrants) {
		super();
		this.year = year;
		this.enrolled = enrolled;
		this.budget = budget;
		this.employability = employability;
		this.studentMigrants = studentMigrants;

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
	public Integer getBudget() {
		return budget;
	}
	public void setBudget(Integer budget) {
		this.budget = budget;
	}
	public Integer getEmployability() {
		return employability;
	}
	public void setEmployability(Integer employability) {
		this.employability = employability;
	}
	public Integer getStudentMigrants() {
		return studentMigrants;
	}
	public void setStudentMigrants(Integer studentMigrants) {
		this.studentMigrants = studentMigrants;
	}
}
