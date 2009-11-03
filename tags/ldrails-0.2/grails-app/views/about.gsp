<%@ page contentType="application/rdf+xml; charset=utf-8" %>
<!-- TODO Fix hardcoded default namespace and xml:base -->
<rdf:RDF
  xml:base="${request.contextPath}/"
  xmlns:owl="http://www.w3.org/2002/07/owl#"
  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
  xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#">
  
	<owl:Ontology resource="model/realworldmodel"/>
  
	<g:each var="c" in="${grailsApplication.controllerClasses}">
	<g:if test="${c.logicalPropertyName != 'model'}">
	<owl:Imports owl:ontology="${c.logicalPropertyName}/classwebdocument"/> 
	</g:if>
	</g:each>
</rdf:RDF>
