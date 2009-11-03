<%@ page contentType="application/rdf+xml; charset=utf-8" import="java.lang.reflect.Modifier"%>
<!-- TODO Fix hardcoded default namespace and xml:base -->

<rdf:RDF
  xmlns="${request.contextPath}/"
  xml:base="${request.contextPath}/"
  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
  xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
  xmlns:property="model/${params.controller}#"
  xmlns:xsd="http://www.w3.org/2001/XMLSchema#">
  <${params.controller} rdf:about="${params.controller}/realworldinstance/${params.id}">
	<rdf:type rdf:resource="${params.controller}/realworldclass" />
	<rdfs:label>${instance}</rdfs:label>
	
	<!-- TODO Add other XSD mappings -->
	<g:each var="field" in="${instance.getClass().declaredFields}">
	<g:if test="${!field.synthetic && !Modifier.isStatic(field.modifiers) && !Modifier.isTransient(field.modifiers)}">

	<g:if test="${field.type.name=='java.lang.String'}">
	<${'property:'+field.name} rdf:datatype="http://www.w3.org/2001/XMLSchema#string">${instance["$field.name"]}</${'property:'+field.name}>
	</g:if>
	<g:elseif test="${field.type.name=='java.lang.Long'}">
	<${'property:'+field.name} rdf:datatype="http://www.w3.org/2001/XMLSchema#integer">${instance["$field.name"]}</${'property:'+field.name}>
	</g:elseif>
	<g:elseif test="${field.type.name=='java.util.Date'}">
	<${'property:'+field.name} rdf:datatype="http://www.w3.org/2001/XMLSchema#dateTime">${instance["$field.name"]}</${'property:'+field.name}>
	</g:elseif>
	<g:elseif test="${instance[field.name]}">
	<${'property:'+field.name} rdf:resource="${instance["$field.name"].class.name.toLowerCase() + '/realworldinstance/' + instance["$field.name"].id}" />
	</g:elseif>
	</g:if>
	</g:each>
  </${params.controller}>
</rdf:RDF>
