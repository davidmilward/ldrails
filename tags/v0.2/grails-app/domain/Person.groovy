class Person {
	// Attributes
	String name
	Date birthDate

	// Relationships
	Person hasMother
	Person hasFather

	static constraints = {
		name(blank: false)
		birthDate()
		hasMother(nullable: true)
		hasFather(nullable: true)
	}

	String toString() {
		"${name}"
	}
}
