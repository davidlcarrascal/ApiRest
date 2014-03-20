package sos.us.es;

public class SpainStd {
	
	Integer year;
	Integer population;
	Integer unemployed;
	Integer pib;
	Integer emigrants;
	Integer educationBudget;  
	
	
	public SpainStd() {
		super();
	}
	public SpainStd(Integer year, Integer population, Integer unemployed, Integer pib, Integer emigrants, Integer educationBudget) {
		this.year = year;
		this.population=population;
		this.unemployed=unemployed;
		this.pib=pib;
		this.emigrants=emigrants;
		this.educationBudget=educationBudget;
	}
	
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getPopulation() {
		return population;
	}
	public void setPopulation(Integer population) {
		this.population = population;
	}
	public Integer getUnemployed() {
		return unemployed;
	}
	public void setUnemployed(Integer unemployed) {
		this.unemployed = unemployed;
	}
	public Integer getPib() {
		return pib;
	}
	public void setPib(Integer pib) {
		this.pib = pib;
	}
	public Integer getEmigrants() {
		return emigrants;
	}
	public void setEmigrants(Integer emigrants) {
		this.emigrants = emigrants;
	}
	public Integer getEducationBudget() {
		return educationBudget;
	}
	public void setEducationBudget(Integer educationBudget) {
		this.educationBudget = educationBudget;
	}
	
	


}
