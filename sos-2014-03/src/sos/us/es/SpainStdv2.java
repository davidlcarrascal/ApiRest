package sos.us.es;

public class SpainStdv2 {
	
	Long year;
	Long population;
	Long unemployed;
	Double pib;
	Long emigrants;
	Double educationBudget;  
	
	
	public SpainStdv2() {
		super();
	}
	public SpainStdv2(Long year, Long population, Long unemployed, Double pib, Long emigrants, Double educationBudget) {
		this.year = year;
		this.population=population;
		this.unemployed=unemployed;
		this.pib=pib;
		this.emigrants=emigrants;
		this.educationBudget=educationBudget;
	}
	
	public Long getYear() {
		return year;
	}
	public void setYear(Long year) {
		this.year = year;
	}
	public Long getPopulation() {
		return population;
	}
	public void setPopulation(Long population) {
		this.population = population;
	}
	public Long getUnemployed() {
		return unemployed;
	}
	public void setUnemployed(Long unemployed) {
		this.unemployed = unemployed;
	}
	public Double getPib() {
		return pib;
	}
	public void setPib(Double pib) {
		this.pib = pib;
	}
	public Long getEmigrants() {
		return emigrants;
	}
	public void setEmigrants(Long emigrants) {
		this.emigrants = emigrants;
	}
	public Double getEducationBudget() {
		return educationBudget;
	}
	public void setEducationBudget(Double educationBudget) {
		this.educationBudget = educationBudget;
	}
	
	


}
