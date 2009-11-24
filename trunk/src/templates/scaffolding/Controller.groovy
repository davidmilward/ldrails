

<%=packageName ? "package ${packageName}\n\n" : ''%>class ${className}Controller {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ ${propertyName}List: ${className}.list( params ), ${propertyName}Total: ${className}.count() ]
    }

    def show = {
        def ${propertyName} = ${className}.get( params.id )

        if(!${propertyName}) {
            flash.message = "${className} not found with id \${params.id}"
            redirect(action:list)
        }
        else { return [ ${propertyName} : ${propertyName} ] }
    }

    def delete = {
        def ${propertyName} = ${className}.get( params.id )
        if(${propertyName}) {
            try {
                ${propertyName}.delete(flush:true)
                flash.message = "${className} \${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${className} \${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "${className} not found with id \${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def ${propertyName} = ${className}.get( params.id )

        if(!${propertyName}) {
            flash.message = "${className} not found with id \${params.id}"
            redirect(action:list)
        }
        else {
            return [ ${propertyName} : ${propertyName} ]
        }
    }

    def update = {
        def ${propertyName} = ${className}.get( params.id )
        if(${propertyName}) {
            if(params.version) {
                def version = params.version.toLong()
                if(${propertyName}.version > version) {
                    <%def lowerCaseName = grails.util.GrailsNameUtils.getPropertyName(className)%>
                    ${propertyName}.errors.rejectValue("version", "${lowerCaseName}.optimistic.locking.failure", "Another user has updated this ${className} while you were editing.")
                    render(view:'edit',model:[${propertyName}:${propertyName}])
                    return
                }
            }
            ${propertyName}.properties = params
            if(!${propertyName}.hasErrors() && ${propertyName}.save()) {
                flash.message = "${className} \${params.id} updated"
                redirect(action:show,id:${propertyName}.id)
            }
            else {
                render(view:'edit',model:[${propertyName}:${propertyName}])
            }
        }
        else {
            flash.message = "${className} not found with id \${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def ${propertyName} = new ${className}()
        ${propertyName}.properties = params
        return ['${propertyName}':${propertyName}]
    }

    def save = {
        def ${propertyName} = new ${className}(params)
        if(!${propertyName}.hasErrors() && ${propertyName}.save()) {
            flash.message = "${className} \${${propertyName}.id} created"
            redirect(action:show,id:${propertyName}.id)
        }
        else {
            render(view:'create',model:[${propertyName}:${propertyName}])
        }
    }

    // Begin Linked Data scaffold extensions
	
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
        def instance = ${className}.get( params.id )
        
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
		render(view: '/common/classwebdocument', model: [classObj: ${className}.class], contentType: "application/rdf+xml", encoding: "utf-8")
	}

	// This action controls an Instance Web Document (RDF) resource
	def about = {
		// dispatch to the about.gsp view
        def instance = ${className}.get( params.id )

        if(!instance) {
        	response.sendError(404)
        }
        else {
        	// Let about.gsp produce the RDF representation
        	render(view: '/common/about', model: [instance : instance ], contentType: "application/rdf+xml", encoding: "utf-8")
	    }
	}
}
