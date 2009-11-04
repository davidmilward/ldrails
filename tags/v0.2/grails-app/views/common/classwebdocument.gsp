<%@ page contentType="application/rdf+xml; charset=utf-8" import="java.lang.reflect.Modifier"%>
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
<rdf:RDF
  xml:base="${request.contextPath}/"
  xmlns:xsd="http://www.w3.org/2001/XMLSchema#"
  xmlns:owl="http://www.w3.org/2002/07/owl#"
  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
  xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#">
  
	<owl:Class rdf:about="${params.controller}/realworldclass">
		<owl:Label>${classObj.name}</owl:Label>
		<g:if test="${classObj.superclass.name == 'java.lang.Object'}">
		<rdfs:subClassOf rdf:resource="http://www.w3.org/2002/07/owl#Thing" />
		</g:if>
		<g:else>
		<rdfs:subClassOf rdf:resource="${classObj.superclass.name.toLowerCase()}/realworldclass" />
		</g:else>
	</owl:Class>

	<g:each var="field" in="${classObj.declaredFields}">
	<g:if test="${!field.synthetic && !Modifier.isStatic(field.modifiers) && !Modifier.isTransient(field.modifiers)}">
	<g:if test="${field.type.name.contains('.')}">
	<!-- Assume that field types in a package are references to datatype properties -->
	<owl:DatatypeProperty rdf:about="${params.controller + '/realworldclass#'+field.name}">
		<rdfs:domain rdf:resource="${params.controller}/realworldclass" />
		
		<g:if test="${field.type.name=='java.lang.String'}">
		<rdfs:range rdf:resource="http://www.w3.org/2001/XMLSchema#string" />
		</g:if>
		<g:elseif test="${field.type.name=='java.lang.Long'}">
		<rdfs:range rdf:resource="http://www.w3.org/2001/XMLSchema#integer" />
		</g:elseif>
		<g:elseif test="${field.type.name=='java.util.Date'}">
		<rdfs:range rdf:resource="http://www.w3.org/2001/XMLSchema#dateTime" />
		</g:elseif>
		<g:else>
		<rdfs:range rdf:resource="${field.type.name.toLowerCase()}/realworldclass" />
		</g:else>
	</owl:DatatypeProperty>
	</g:if>
	<g:else>
	<!-- Assume that field types in the default package are references to object properties -->
	<owl:ObjectProperty rdf:about="${params.controller + '/realworldclass#'+field.name}">
		<rdfs:domain rdf:resource="${params.controller}/realworldclass" />
		
		<g:if test="${field.type.name=='java.lang.String'}">
		<rdfs:range rdf:resource="http://www.w3.org/2001/XMLSchema#string" />
		</g:if>
		<g:elseif test="${field.type.name=='java.lang.Long'}">
		<rdfs:range rdf:resource="http://www.w3.org/2001/XMLSchema#integer" />
		</g:elseif>
		<g:elseif test="${field.type.name=='java.util.Date'}">
		<rdfs:range rdf:resource="http://www.w3.org/2001/XMLSchema#dateTime" />
		</g:elseif>
		<g:else>
		<rdfs:range rdf:resource="${field.type.name.toLowerCase()}/realworldclass" />
		</g:else>
	</owl:ObjectProperty>
	</g:else>
	</g:if>
	</g:each>
</rdf:RDF>
