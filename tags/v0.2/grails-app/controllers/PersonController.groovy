class PersonController {
	def scaffold = true
	
	// Establish an HTTP URI for a real world class
    def realworldclass = {
		def url
		withFormat {
			html {
				url = g.createLink(action: 'list')
			}
			rdf {
				url = g.createLink(action: 'classwebdocument')
			}
		}
		response.status = 303
		response.setHeader("Location", url)
		response.contentType = "text/plain"
		response.outputStream << "real world class"
		response.outputStream.flush()
    }

	// Establish an HTTP URI for a real world isntance
    def realworldinstance = {
        def instance = Person.get( params.id )
        
        if(!instance) {
        	response.sendError(404)
        }
        else {
        	def url
        	withFormat {
        		html {
        			url = g.createLink(action: 'show', id: params.id)
        		}
        		rdf {
        			url = g.createLink(action: 'about', id: params.id)
        		}
        	}
        	// Redirect to the Instance Generic Document. Let it do conneg.
			response.status = 303
			response.setHeader("Location", url)
			response.contentType = "text/plain"
			response.outputStream << "real world instance"
			response.outputStream.flush()
	    }
    }

	// This action controls a Class Web Document (RDF) resource
	def classwebdocument = {
		render(view: '/common/classwebdocument', model: [classObj: Person.class], contentType: "application/rdf+xml", encoding: "utf-8")
	}

	// This action controls an Instance Web Document (RDF) resource
	def about = {
		// dispatch to the about.gsp view
        def instance = Person.get( params.id )

        if(!instance) {
        	response.sendError(404)
        }
        else {
        	// Let about.gsp produce the RDF representation
        	render(view: '/common/about', model: [instance : instance ], contentType: "application/rdf+xml", encoding: "utf-8")
	    }
	}
}
