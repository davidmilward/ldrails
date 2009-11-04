<%@ page contentType="application/rdf+xml; charset=utf-8" %>
<!--
Copyright 2009 OCLC Online Computer Library Center Licensed under the Apache
License, Version 2.0 (the "License"); you may not use this file except in
compliance with the License. You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or
agreed to in writing, software distributed under the License is distributed on
an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
or implied. See the License for the specific language governing permissions and
limitations under the License.
-->
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
