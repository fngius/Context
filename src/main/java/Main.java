import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AddImport;
//import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
//import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class Main {

	
	public static void main(String[] args) throws OWLOntologyCreationException, OWLOntologyStorageException, IOException {
		
		/* Context Ontology */
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		String contextOntIRI = "ContextOnt.owl";
		OWLOntology ontology = manager.createOntology(IRI.create(contextOntIRI));
		OWLDataFactory factory = manager.getOWLDataFactory();

		OntologyAssistant oa = new OntologyAssistant();
		
		/* Imported Ontologies */
		OWLImportsDeclaration importDeclarationTO = manager.getOWLDataFactory().getOWLImportsDeclaration(IRI.create("http://www.w3.org/2006/time"));
		manager.applyChange(new AddImport(ontology, importDeclarationTO));		
			
		OWLImportsDeclaration importDeclaration = manager.getOWLDataFactory().getOWLImportsDeclaration(IRI.create("http://www.w3.org/ns/ssn/"));
		manager.applyChange(new AddImport(ontology, importDeclaration));

		/* Classes */
		OWLClass Resource = oa.createClass(ontology,manager,factory,contextOntIRI+"#Resource");
		oa.addAnnotationComment(ontology, manager, factory, Resource, "A class which represents resources of the industry");

		OWLClass Product = oa.createClass(ontology,manager,factory,contextOntIRI+"#Product");
		oa.subClass(ontology, manager, factory, Product, Resource);
		
		OWLClass Part = oa.createClass(ontology,manager,factory,contextOntIRI+"#Part");
		oa.subClass(ontology, manager, factory, Part, Product);
		
		OWLClass Component = oa.createClass(ontology,manager,factory,contextOntIRI+"#Component");
		oa.subClass(ontology, manager, factory, Component, Part);
		
		OWLClass ManufacturingFacility = oa.createClass(ontology,manager,factory,contextOntIRI+"#ManufacturingFacility");
		oa.subClass(ontology, manager, factory, ManufacturingFacility, Resource);
		
		OWLClass Line = oa.createClass(ontology,manager,factory,contextOntIRI+"#Line");
		oa.subClass(ontology, manager, factory, Line, ManufacturingFacility);
		
		OWLClass Cell = oa.createClass(ontology,manager,factory,contextOntIRI+"#Cell");
		oa.subClass(ontology, manager, factory, Cell, Line);
		
		OWLClass WorkStation = oa.createClass(ontology,manager,factory,contextOntIRI+"#WorkStation");
		oa.subClass(ontology, manager, factory, WorkStation, Cell);
		
		OWLClass Machine = oa.createClass(ontology,manager,factory,contextOntIRI+"#Machine");
		oa.subClass(ontology, manager, factory, Machine, ManufacturingFacility);
		
		OWLClass AssemblingMachine = oa.createClass(ontology,manager,factory,contextOntIRI+"#AssemblingMachine");
		oa.subClass(ontology, manager, factory, AssemblingMachine, Machine);
		
		OWLClass TestingMachine = oa.createClass(ontology,manager,factory,contextOntIRI+"#TestingMachine");
		oa.subClass(ontology, manager, factory, TestingMachine, Machine);
		
		OWLClass ProcessingMachine = oa.createClass(ontology,manager,factory,contextOntIRI+"#ProcessingMachine");
		oa.subClass(ontology, manager, factory, ProcessingMachine, Machine);
		
		OWLClass Staff = oa.createClass(ontology,manager,factory,contextOntIRI+"#Staff");
		oa.subClass(ontology, manager, factory, Staff, Resource);
		
		OWLClass Operator = oa.createClass(ontology,manager,factory,contextOntIRI+"#Operator");
		oa.subClass(ontology, manager, factory, Operator, Staff);
		
		OWLClass Manager = oa.createClass(ontology,manager,factory,contextOntIRI+"#Manager");
		oa.subClass(ontology, manager, factory, Manager, Staff);
		
		OWLClass MaintenanceTechnician = oa.createClass(ontology,manager,factory,contextOntIRI+"#MaintenanceTechnician");
		oa.subClass(ontology, manager, factory, MaintenanceTechnician, Staff);
		
		OWLClass Process = oa.createClass(ontology,manager,factory,contextOntIRI+"#Process");
		
		OWLClass ManufacturingProcess = oa.createClass(ontology,manager,factory,contextOntIRI+"#ManufacturingProcess");
		oa.subClass(ontology, manager, factory, ManufacturingProcess, Process);
		
		OWLClass HumanOperation = oa.createClass(ontology,manager,factory,contextOntIRI+"#HumanOperation");
		oa.subClass(ontology, manager, factory, HumanOperation, Process);

		OWLClass LogisticProcess = oa.createClass(ontology,manager,factory,contextOntIRI+"#LogisticProcess");
		oa.subClass(ontology, manager, factory, LogisticProcess, Process);
		
		OWLClass Maintenance = oa.createClass(ontology,manager,factory,contextOntIRI+"#Maintenance");
		oa.subClass(ontology, manager, factory, Maintenance, LogisticProcess);
		
		OWLClass Handling = oa.createClass(ontology,manager,factory,contextOntIRI+"#Handling");
		oa.subClass(ontology, manager, factory, Handling, LogisticProcess);
		
		OWLClass Situation = oa.createClass(ontology,manager,factory,contextOntIRI+"#Situation");
		
		OWLClass Sit_Diagnosis = oa.createClass(ontology,manager,factory,contextOntIRI+"#Sit_Diagnosis");
		oa.subClass(ontology, manager, factory, Sit_Diagnosis, Situation);
		
		OWLClass Sit_Assessment = oa.createClass(ontology,manager,factory,contextOntIRI+"#Sit_Assessment");
		oa.subClass(ontology, manager, factory, Sit_Assessment, Situation);
		
		OWLClass Sit_Monitoring = oa.createClass(ontology,manager,factory,contextOntIRI+"#Sit_Monitoring");
		oa.subClass(ontology, manager, factory, Sit_Monitoring, Situation);
		
		OWLClass Sit_Prediction = oa.createClass(ontology,manager,factory,contextOntIRI+"#Sit_Prediction");
		oa.subClass(ontology, manager, factory, Sit_Prediction, Situation);
		
		OWLClass Sit_Classification = oa.createClass(ontology,manager,factory,contextOntIRI+"#Sit_Classification");
		oa.subClass(ontology, manager, factory, Sit_Classification, Situation);

		// Replace this class
		OWLClass Location = oa.createClass(ontology,manager,factory,contextOntIRI+"#Location");
		
		String sensor = "http://www.w3.org/ns/sosa/Sensor";
		OWLClass Sensor = factory.getOWLClass(IRI.create(sensor));
		OWLClass Property = factory.getOWLClass(IRI.create("http://www.w3.org/ns/ssn/Property"));
		
		String time = "http://www.w3.org/2006/time#TemporalEntity";
		OWLClass Time = factory.getOWLClass(IRI.create(time));
		
		
		/* Object Properties */
		OWLObjectProperty isInLocation = oa.createObjectProperty(ontology, manager, factory, contextOntIRI + "#isInLocation", Resource, Location);
		OWLObjectProperty appliesTo = oa.createObjectProperty(ontology, manager, factory, contextOntIRI + "#appliesTo", Process, Resource);
		OWLObjectProperty performsProcess = oa.createObjectProperty(ontology, manager, factory, contextOntIRI + "#performsProcess", Resource, Process);
		OWLObjectProperty happensIn = oa.createObjectProperty(ontology, manager, factory, contextOntIRI + "#happensIn", Process, Location);
		OWLObjectProperty hasTime1 = oa.createObjectProperty(ontology, manager, factory, contextOntIRI + "#hasTime1", Process, Time);
		OWLObjectProperty hasTime2 = oa.createObjectProperty(ontology, manager, factory, contextOntIRI + "#hasTime2", Situation, Time);
		OWLObjectProperty hasTime3 = oa.createObjectProperty(ontology, manager, factory, contextOntIRI + "#hasTime3", Sensor, Time);
		OWLObjectProperty hasSubProcess = oa.createObjectProperty(ontology, manager, factory, contextOntIRI + "#hasSubProcess", Process, Process);
		OWLObjectProperty hasProcess = oa.createObjectProperty(ontology, manager, factory, contextOntIRI + "#hasProcess", Situation, Process);
		OWLObjectProperty hasSensor = oa.createObjectProperty(ontology, manager, factory, contextOntIRI + "#hasSensor", Resource, Sensor);
		OWLObjectProperty locatedIn = oa.createObjectProperty(ontology, manager, factory, contextOntIRI + "#locatedIn", Sensor, Location);
		OWLObjectProperty hasProperty = oa.createObjectProperty(ontology, manager, factory, contextOntIRI + "#hasProperty", Resource, Property);
		OWLObjectProperty hasObject = oa.createObjectProperty(ontology, manager, factory, contextOntIRI + "#hasObject", Situation, Resource);
		OWLObjectProperty hasSensorLoc = oa.createObjectProperty(ontology, manager, factory, contextOntIRI + "#hasSensorLoc", Location, Sensor);
		OWLObjectProperty operates = oa.createObjectProperty(ontology, manager, factory, contextOntIRI + "#operates", Staff, ManufacturingFacility);
		OWLObjectProperty contains = oa.createObjectProperty(ontology, manager, factory, contextOntIRI + "#contains", WorkStation, Machine);
		
		/* Datatype Properties */
		
		
		/* Individuals */
		OWLIndividual Machine_1 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#Machine_1", Machine);
		OWLIndividual Operator_1 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#Operator_1", Operator);

		OWLIndividual Temperature = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#Temperature", Property);

		OWLIndividual SensorTemp = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#SensorTemp", Sensor);
		
		oa.relateIndividuals(ontology, manager, factory, hasProperty, Machine_1, Temperature);
		oa.relateIndividuals(ontology, manager, factory, hasSensor, Machine_1, SensorTemp);
		
		oa.relateIndividuals(ontology, manager, factory, operates, Operator_1, Machine_1);
		
		OWLDataProperty value = oa.createDataProperty(ontology, manager, factory, contextOntIRI + "#value", Property, factory.getIntegerOWLDatatype());
	    
		oa.assignValueToDataTypeProperty(ontology, manager, factory, value, Temperature, factory.getOWLLiteral(33.2));
		
		/* Save ontology */
		File file = new File("/home/franco/Repositories/Context/ContextOntology.owl");
		manager.saveOntology(ontology, IRI.create(file.toURI()));
		manager.saveOntology(ontology, System.out);
	}

}