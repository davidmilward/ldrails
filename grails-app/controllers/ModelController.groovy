/**
 * Copyright 2009 OCLC Online Computer Library Center Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
class ModelController {
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

	// Produce an RDF representation of the model
	def about = {
		if (params.id) {
			response.sendError(404)
		} else {
			render(view: '/about', contentType: 'application/rdf+xml', encoding: 'utf-8')
		}
	}
}
