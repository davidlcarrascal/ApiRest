package sos.us.es;

public class Seville {

	private Integer year;
	private Integer population;
	private Integer unemployed;
	private Double educationBudget;
	private Integer migrants;
	private Double pib;

	public Seville(Integer year, Integer population, Integer unemployed,
			Double educationBudget, Integer migrants, Double pib) {
		super();
		this.year = year;
		this.population = population;
		this.unemployed = unemployed;
		this.educationBudget = educationBudget;
		this.migrants = migrants;
		this.pib = pib;
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

	public Double getEducationBudget() {
		return educationBudget;
	}

	public void setEducationBudget(Double educationBudget) {
		this.educationBudget = educationBudget;
	}

	public Integer getMigrants() {
		return migrants;
	}

	public void setMigrants(Integer migrants) {
		this.migrants = migrants;
	}

	public Double getPib() {
		return pib;
	}

	public void setPib(Double pib) {
		this.pib = pib;
	}

	public Integer getYear() {
		return year;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seville other = (Seville) obj;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Seville [year=" + year + ", population=" + population
				+ ", unemployed=" + unemployed + ", educationBudget="
				+ educationBudget + ", migrants=" + migrants + ", pib=" + pib
				+ "]";
	}

}
