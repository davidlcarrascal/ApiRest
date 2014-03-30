package sos.us.es;

public class SpainStd {
	
	Long year;
	Long population;
	Long unemployed;
	Long pib;
	Long emigrants;
	Long educationBudget;  
	
	
	public SpainStd() {
		super();
	}
	public SpainStd(Long year, Long population, Long unemployed, Long pib, Long emigrants, Long educationBudget) {
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
	public Long getPib() {
		return pib;
	}
	public void setPib(Long pib) {
		this.pib = pib;
	}
	public Long getEmigrants() {
		return emigrants;
	}
	public void setEmigrants(Long emigrants) {
		this.emigrants = emigrants;
	}
	public Long getEducationBudget() {
		return educationBudget;
	}
	public void setEducationBudget(Long educationBudget) {
		this.educationBudget = educationBudget;
	}
	
	


}
