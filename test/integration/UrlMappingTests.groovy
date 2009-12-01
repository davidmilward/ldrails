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
import grails.test.*

/**
Test the URI patterns. Note that in order for this to work, use
'grails generate-controller <domain-class-name>' rather than 
'grails create-controller <domain-class-name>'.
*/

class UrlMappingTests extends GrailsUrlMappingsTestCase {
    protected void setUp() {
        super.setUp()
        def instance = new Person(
        	name: 'Me')
        if (instance.validate()) {
        	instance.save()
        } else {
        	println instance
        }
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testRealWorldModel() {
    	assertUrlMapping("/ancestry/realworldmodel",
    		controller: 'ancestry',
    		action: 'realworldmodel')
    }

    void testModelWebDocument() {
    	assertUrlMapping("/ancestry/about",
    		controller: 'ancestry',
    		action: 'about')
    }

    void testRealWorldClass() {
    	assertUrlMapping("/person/realworldclass",
    		controller: 'person',
    		action: 'realworldclass')
    }

    void testClassWebDocument() {
    	assertUrlMapping("/person/classwebdocument",
    		controller: 'person',
    		action: 'classwebdocument')
    }

    void testRealWorldInstance() {
    	assertUrlMapping("/person/realworldinstance/1",
    		controller: 'person',
    		action: 'realworldinstance') {
    		id = 1
    	}
    }

    void testInstanceWebDocument() {
    	assertUrlMapping("/person/about/1",
    		controller: 'person',
    		action: 'about') {
    		id = 1
    	}
    }
}
