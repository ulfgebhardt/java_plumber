package de.tu_darmstadt.gdi1.plumber.controller.options;
public class Option{
	private String name;
	private String value;

	/** einzelene Option mit Wertzuweisung
	 * @param name
	 * @param value
	 */
	public Option(String name, String value)
	{
		this.name = name;  
		this.value = value;  //Wert kann sich ändern
	}

	public String getName() {
		return name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
