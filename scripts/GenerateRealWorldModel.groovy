includeTargets << grailsScript("Init")

target(main: "Generate domain classes from an XMI domain model") {
	def dataTypes = [
	'string': 'String',
	'dateTime': 'Date'
	]
	def ns = [:]
    ns.UML = "omg.org/UML1.3"

    depends(parseArguments)
    
    def xmiFileName = "${argsMap.params ? argsMap.params[0] : 'realworldmodel.xmi'}"
        
	def XMI = new XmlSlurper().parse(new File(xmiFileName))

	XMI.declareNamespace(ns)

	def dModel = XMI."XMI.content".'UML:Model'."UML:Namespace.ownedElement".'UML:Package'
	println("modelName: " + dModel.@name)
	def dClasses = dModel."UML:Namespace.ownedElement".'UML:Package'."UML:Namespace.ownedElement".'UML:Package'."UML:Namespace.ownedElement"
	dClasses.each {dClass->
		def file = new File('grails-app/domain/' + dClass.'UML:Class'.@name.text()+".groovy")
		if (file.exists()) {
			echo "Error: Domain class \'${dClass.'UML:Class'.@name.text()}\' already exists"
			return -1
		}
		
		file << "class ${dClass.'UML:Class'.@name} {\n"
		
		def attributes = dClass.'UML:Class'."UML:Classifier.feature"."UML:Attribute"
		file << '\t// Attributes\n'
		attributes.each {attribute ->
			def dataType = dataTypes[attribute."UML:ModelElement.taggedValue"."UML:TaggedValue".find {it.@tag.text()=='type'}.@value.text()]
			file << "\t${dataType} ${attribute.@name}\n"
		}

		def relationships = dClass."UML:Association"
		file << '\n\t// Relationships\n'
		relationships.each {relationship ->
			def eaTargetName = relationship."UML:ModelElement.taggedValue"."UML:TaggedValue".find {it.@tag=='ea_targetName'}.@value
			file << "\t${eaTargetName} ${relationship.@name}\n"
		}

		file << '\n\tstatic constraints = {\n'
		attributes.each {attribute ->
			file << "\t\t${attribute.@name}(${attribute."UML:ModelElement.constraint"."UML:Constraint".@name})\n"
		}
		relationships.each {relationship ->
			file << "\t\t${relationship.@name}(nullable: true)\n"
		}
		file << '\t}\n\n'

		// Use the first attribute as the display string
		if (attributes[0]) {
			file << '\tString toString() {\n'
			file << '\t\t\"${' + "${attributes[0].@name}" + '}\"\n'
			file << '\t}\n'
		}
		
		file << '}\n'
	}
	
//	def packagedElements = model.packagedElement.'**'.findAll {it.'@xmi:type'.text() == 'uml:Class'}
//	packagedElements.each {
//		println(it.@name)
//	}
}

setDefaultTarget(main)
