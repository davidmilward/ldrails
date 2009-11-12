class AncestryController {
	// OK for now
	def index = {
		redirect(uri: "/")
	}
	
	// Establish an HTTP URI for a real world model
    def realworldmodel = {
		if (params.id) {
			response.sendError(404)
		} else {
			def url
			withFormat {
				html {
					// OK for now
					url = "${request.contextPath}/"
				}
				rdf {
					url = g.createLink(action: 'about')
				}
			}
			response.status = 303
			response.setHeader("Location", url)
			response.contentType = "text/plain"
			response.outputStream << "real world model"
			response.outputStream.flush()
		}
    }

	def realworldclass = {
		// Redirect to the Real World Model URI
		redirect(action: 'realworldmodel')
	}

	// Produce an RDF representation of the model
	def about = {
		if (params.id) {
			response.sendError(404)
		} else {
			render(view: '/about', contentType: 'application/rdf+xml', encoding: 'utf-8')
		}
	}
}
