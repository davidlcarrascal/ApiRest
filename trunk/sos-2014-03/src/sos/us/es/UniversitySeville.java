package sos.us.es;

public class UniversitySeville {
	Long year;
	Long enrolled;
	Long budget;
	Long employability;
	Long studentMigrants;
	
	public UniversitySeville(Long year, Long enrolled,Long budget, Long employability, Long studentMigrants) {
		
		this.year = year;
		this.enrolled = enrolled;
		this.budget = budget;
		this.employability = employability;
		this.studentMigrants = studentMigrants;

	}

	public UniversitySeville(){
		super();
	}

	public Long getEnrolled() {
		return enrolled;
	}

	public void setEnrolled(Long enrolled) {
		this.enrolled = enrolled;
	}

	public Long getYear() {
		return year;
	}

	public void setYears(Long year) {
		this.year = year;
	}
	public Long getBudget() {
		return budget;
	}
	public void setBudget(Long budget) {
		this.budget = budget;
	}
	public Long getEmployability() {
		return employability;
	}
	public void setEmployability(Long employability) {
		this.employability = employability;
	}
	public Long getStudentMigrants() {
		return studentMigrants;
	}
	public void setStudentMigrants(Long studentMigrants) {
		this.studentMigrants = studentMigrants;
	}
	public String toString(){
		return "{ year :"+year+", employability : "+this.employability+", enrolled : "+this.enrolled+", budget : "+this.budget+", studentsMigrants : "+this.studentMigrants+"}";
	}
}
