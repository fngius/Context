import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.checkerframework.checker.i18nformatter.qual.I18nFormat;
import org.checkerframework.dataflow.cfg.node.GreaterThanNode;
import org.nfunk.jep.function.If;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.change.CreateValuePartition;
import org.semanticweb.owlapi.io.StringDocumentTarget;
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
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
//import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.InferredAxiomGenerator;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;
import org.semanticweb.owlapi.util.InferredSubClassAxiomGenerator;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.owl2rl.OWL2RLEngine;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.ui.view.owl2rl.OWL2RLControlView;

import com.google.common.collect.Lists;
//import com.google.common.io.Files;
import org.swrlapi.owl2rl.OWL2RLEngine;

public class Main {

	
	public static void main(String[] args) throws OWLOntologyCreationException, OWLOntologyStorageException, IOException, SQWRLException, SWRLParseException {
		
		/* Context Ontology */
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		String contextOntIRI = "ContextOnt";
		OWLOntology ontology = manager.createOntology(IRI.create(contextOntIRI));
		OWLDataFactory factory = manager.getOWLDataFactory();

		manager.getOntologyFormat(ontology).asPrefixOWLOntologyFormat().setDefaultPrefix(contextOntIRI);
		
		OntologyAssistant oa = new OntologyAssistant();
		
		
		OWLDatatype integerDatatype = factory.getIntegerOWLDatatype();
		OWLDatatype floatDatatype = factory.getFloatOWLDatatype();
		OWLDatatype doubleDatatype = factory.getDoubleOWLDatatype();
		OWLDatatype booleanDatatype = factory.getBooleanOWLDatatype();
	    OWLDatatype dateTime = factory.getOWLDatatype(IRI.create("http://www.w3.org/2001/XMLSchema#dateTime"));

	    /* Imported Ontologies */
		//OWLImportsDeclaration importDeclarationTO = manager.getOWLDataFactory().getOWLImportsDeclaration(IRI.create("http://www.w3.org/2006/time"));
		String pre_SWRLTO = "http://swrl.stanford.edu/ontologies/built-ins/3.3/temporal.owl";
	    OWLImportsDeclaration importDeclarationTO = manager.getOWLDataFactory().getOWLImportsDeclaration(IRI.create(pre_SWRLTO));
		manager.applyChange(new AddImport(ontology, importDeclarationTO));		
		
		String pre_geoOnt = "http://schemas.opengis.net/geosparql/1.0/geosparql_vocab_all.rdf";
		OWLImportsDeclaration importDeclarationLOC = manager.getOWLDataFactory().getOWLImportsDeclaration(IRI.create(pre_geoOnt));
		manager.applyChange(new AddImport(ontology, importDeclarationLOC));
		
		String pre_SOSAOnt = "http://www.w3.org/ns/sosa/";
		String pre_SSNOnt = "http://www.w3.org/ns/ssn/";
		OWLImportsDeclaration importDeclarationSSN = manager.getOWLDataFactory().getOWLImportsDeclaration(IRI.create(pre_SSNOnt));
		manager.applyChange(new AddImport(ontology, importDeclarationSSN));

		/* Classes */
		OWLClass Resource = oa.createClass(ontology,manager,factory,"#Resource");
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
		
		OWLClass Danger = oa.createClass(ontology,manager,factory,contextOntIRI+"#Danger");
		
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
		String location = pre_geoOnt + "#SpatialObject";
		OWLClass Location = factory.getOWLClass(IRI.create(location));
		
		String featureLoc = pre_geoOnt + "#Feature";
		OWLClass Feature = factory.getOWLClass(IRI.create(featureLoc));
		
		String geometryLoc = pre_geoOnt + "#Geometry";
		OWLClass Geometry = factory.getOWLClass(IRI.create(geometryLoc));
		
		String sensor = pre_SOSAOnt + "Sensor";
		OWLClass Sensor = factory.getOWLClass(IRI.create(sensor));
		OWLClass Property = factory.getOWLClass(IRI.create(pre_SSNOnt + "Property"));
		OWLClass ObservableProperty = factory.getOWLClass(IRI.create(pre_SOSAOnt + "ObservableProperty"));
		OWLClass Observation = factory.getOWLClass(IRI.create(pre_SOSAOnt + "Observation"));
		OWLClass Result = factory.getOWLClass(IRI.create(pre_SOSAOnt + "Result"));
		
		
		//String time = "http://www.w3.org/2006/time#TemporalEntity";
		String time = pre_SWRLTO + "#ValidTime";
		OWLClass Time = factory.getOWLClass(IRI.create(time));
		String validInstant = pre_SWRLTO + "#ValidInstant";
		OWLClass ValidInstant = factory.getOWLClass(IRI.create(validInstant));


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
		
		OWLObjectProperty hasTimeObs = oa.createObjectProperty(ontology, manager, factory, contextOntIRI + "#hasTimeResult", Observation, Time);
		
		OWLObjectProperty observedProperty = factory.getOWLObjectProperty(IRI.create("http://www.w3.org/ns/sosa/observedProperty"));

		OWLObjectProperty madeBySensor = factory.getOWLObjectProperty(IRI.create("http://www.w3.org/ns/sosa/madeBySensor"));

		OWLObjectProperty dif = factory.getOWLObjectProperty(IRI.create("http://www.w3.org/2002/07/owl#differentFrom"));
		
		/* Datatype Properties */
		OWLDataProperty value = oa.createDataProperty(ontology, manager, factory, contextOntIRI + "#value", Property, factory.getIntegerOWLDatatype());
		
		OWLDataProperty hasSimpleResult = factory.getOWLDataProperty(IRI.create("http://www.w3.org/ns/sosa/hasSimpleResult"));
		
		OWLDataProperty resultTime = factory.getOWLDataProperty(IRI.create("http://www.w3.org/ns/sosa/resultTime"));
		
		OWLDataProperty hasTimeT = factory.getOWLDataProperty(IRI.create("http://swrl.stanford.edu/ontologies/built-ins/3.3/temporal.owl#hasTime"));
		
		/* Individuals */
		OWLIndividual ProductionLine_1 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#ProductionLine_1", Line);
		
		OWLIndividual Cell_PL1_1 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#Cell_PL1_1", Cell);
		OWLIndividual Cell_PL1_2 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#Cell_PL1_2", Cell);
		
		OWLIndividual WorkStation_C1_1 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#Workstation_C1_1", WorkStation);
		OWLIndividual WorkStation_C2_1 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#Workstation_C2_1", WorkStation);
		
		OWLIndividual Machine_WS1_C1_1 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#Machine_WS1_C1_1", Machine);
		OWLIndividual Machine_WS1_C1_2 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#Machine_WS1_C1_2", Machine);
		OWLIndividual Machine_WS1_C2_1 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#Machine_WS1_C2_1", Machine);
		OWLIndividual Machine_WS1_C2_2 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#Machine_WS1_C2_2", Machine);
		
		OWLIndividual Operator_1 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#Operator_1", Operator);

		OWLIndividual OProperty_1 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#OProperty_1", ObservableProperty);
		OWLIndividual OProperty_2 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#OProperty_2", ObservableProperty);
		OWLIndividual OProperty_3 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#OProperty_3", ObservableProperty);
		OWLIndividual OProperty_4 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#OProperty_4", ObservableProperty);
		OWLIndividual OProperty_5 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#OProperty_5", ObservableProperty);
		OWLIndividual OProperty_6 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#OProperty_6", ObservableProperty);
		OWLIndividual OProperty_7 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#OProperty_7", ObservableProperty);
		
		OWLIndividual Sensor_1 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#Sensor_1", Sensor);
		OWLIndividual Sensor_2 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#Sensor_2", Sensor);
		OWLIndividual Sensor_3 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#Sensor_3", Sensor);
		OWLIndividual Sensor_4 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#Sensor_4", Sensor);
		OWLIndividual Sensor_5 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#Sensor_5", Sensor);
		OWLIndividual Sensor_6 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#Sensor_6", Sensor);
		OWLIndividual Sensor_7 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#Sensor_7", Sensor);
		OWLIndividual Sensor_8 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#Sensor_8", Sensor);
		OWLIndividual Sensor_9 = oa.createIndividual(ontology, manager, factory, contextOntIRI + "#Sensor_9", Sensor);
		
		oa.relateIndividuals(ontology, manager, factory, hasProperty, ProductionLine_1, OProperty_1);
		oa.relateIndividuals(ontology, manager, factory, hasProperty, Cell_PL1_1, OProperty_2);
		oa.relateIndividuals(ontology, manager, factory, hasProperty, Cell_PL1_2, OProperty_3);
		oa.relateIndividuals(ontology, manager, factory, hasProperty, WorkStation_C1_1, OProperty_4);
		oa.relateIndividuals(ontology, manager, factory, hasProperty, WorkStation_C2_1, OProperty_5);
		oa.relateIndividuals(ontology, manager, factory, hasProperty, Machine_WS1_C1_1, OProperty_6);
		oa.relateIndividuals(ontology, manager, factory, hasProperty, Machine_WS1_C1_2, OProperty_7);
		oa.relateIndividuals(ontology, manager, factory, hasProperty, Machine_WS1_C2_1, OProperty_6);
		oa.relateIndividuals(ontology, manager, factory, hasProperty, Machine_WS1_C2_2, OProperty_7);
		
		oa.relateIndividuals(ontology, manager, factory, hasSensor, ProductionLine_1, Sensor_1);
		oa.relateIndividuals(ontology, manager, factory, hasSensor, Cell_PL1_1, Sensor_2);
		oa.relateIndividuals(ontology, manager, factory, hasSensor, Cell_PL1_2, Sensor_3);
		oa.relateIndividuals(ontology, manager, factory, hasSensor, WorkStation_C1_1, Sensor_4);
		oa.relateIndividuals(ontology, manager, factory, hasSensor, WorkStation_C2_1, Sensor_5);
		oa.relateIndividuals(ontology, manager, factory, hasSensor, Machine_WS1_C1_1, Sensor_6);
		oa.relateIndividuals(ontology, manager, factory, hasSensor, Machine_WS1_C1_2, Sensor_7);
		oa.relateIndividuals(ontology, manager, factory, hasSensor, Machine_WS1_C2_1, Sensor_8);
		oa.relateIndividuals(ontology, manager, factory, hasSensor, Machine_WS1_C2_2, Sensor_9);
		
		oa.relateIndividuals(ontology, manager, factory, operates, Operator_1, Machine_WS1_C1_1);
		
		
		/*
		sosa:resultTime(?x, ?z1) ^ swrlb:greaterThan(?y, "3030.0"^^xsd:double) ^ 
		sosa:Observation(?x) ^ sosa:hasSimpleResult(?x, ?y) 
		-> 
		Danger(?x)
		*/
		
		SWRLVariable obs1 = factory.getSWRLVariable(IRI.create(contextOntIRI + "#x"));
		SWRLVariable obs2 = factory.getSWRLVariable(IRI.create(contextOntIRI + "#x1"));
		SWRLVariable res1 = factory.getSWRLVariable(IRI.create(contextOntIRI + "#y"));
		SWRLVariable res2 = factory.getSWRLVariable(IRI.create(contextOntIRI + "#y1"));
		SWRLVariable time1 = factory.getSWRLVariable(IRI.create(contextOntIRI + "#z1"));
		SWRLVariable time2 = factory.getSWRLVariable(IRI.create(contextOntIRI + "#z2"));

		SWRLVariable validInstant1 = factory.getSWRLVariable(IRI.create(contextOntIRI + "#a"));
		SWRLVariable validInstant2 = factory.getSWRLVariable(IRI.create(contextOntIRI + "#b"));
		SWRLVariable timeVI1 = factory.getSWRLVariable(IRI.create(contextOntIRI + "#c"));
		SWRLVariable timeVI2 = factory.getSWRLVariable(IRI.create(contextOntIRI + "#d"));
		
		SWRLClassAtom atom1 = factory.getSWRLClassAtom(Observation, obs1);
		SWRLClassAtom atom2 = factory.getSWRLClassAtom(Observation, obs2);
		
		//SWRLObjectPropertyAtom atom12 = factory.getSWRLObjectPropertyAtom(dif, obs1, obs2);
		

		SWRLDataPropertyAtom atom3 = factory.getSWRLDataPropertyAtom(hasSimpleResult, obs1, res1);
		SWRLDataPropertyAtom atom4 = factory.getSWRLDataPropertyAtom(hasSimpleResult, obs2, res2);
		SWRLDataPropertyAtom atom5 = factory.getSWRLDataPropertyAtom(resultTime, obs1, time1);
		SWRLDataPropertyAtom atom6 = factory.getSWRLDataPropertyAtom(resultTime, obs2, time2);
		
		//SWRLClassAtom atom7 = factory.getSWRLClassAtom(ValidInstant, validInstant1);
		//SWRLClassAtom atom8 = factory.getSWRLClassAtom(ValidInstant, validInstant2);
		//SWRLDataPropertyAtom atom9 = factory.getSWRLDataPropertyAtom(hasTimeT, validInstant1, timeVI1);
		//SWRLDataPropertyAtom atom10 = factory.getSWRLDataPropertyAtom(hasTimeT, validInstant2, timeVI2);
		
		
		//SWRLLiteralArgument d1 = factory.getSWRLLiteralArgument(factory.getOWLLiteral("2008-07-19T11:55:00.000",OWL2Datatype.XSD_DATE_TIME));
		//SWRLLiteralArgument d2 = factory.getSWRLLiteralArgument(factory.getOWLLiteral("2008-07-19T12:32:00.000",OWL2Datatype.XSD_DATE_TIME));
		//List<SWRLDArgument> messages1 = Arrays.asList(d1, d2);
		List<SWRLDArgument> messages1 = Arrays.asList(time1, time2);
		//List<SWRLDArgument> messages1 = Arrays.asList(timeVI1, timeVI2);
		//List<SWRLDArgument> messages1 = Arrays.asList(validInstant1, validInstant2);
		
		//SWRLBuiltInAtom atom11 = factory.getSWRLBuiltInAtom(IRI.create("http://swrl.stanford.edu/ontologies/built-ins/3.3/temporal.owl#equals"),  messages1);
		SWRLBuiltInAtom atom11 = factory.getSWRLBuiltInAtom(IRI.create("http://swrl.stanford.edu/ontologies/built-ins/3.3/temporal.owl#before"),  messages1);


		OWLLiteral maxValue = factory.getOWLLiteral("3030", doubleDatatype);
        SWRLLiteralArgument maxValueArg = factory.getSWRLLiteralArgument(maxValue);
        List<SWRLDArgument> messages = Arrays.asList(res1,maxValueArg);
		SWRLBuiltInAtom atom = factory.getSWRLBuiltInAtom(IRI.create("http://www.w3.org/2003/11/swrlb#greaterThan"),  messages);
		
		Set<SWRLAtom> antecedent = new HashSet<SWRLAtom>();
		antecedent.add(atom);
		antecedent.add(atom1);
        //antecedent.add(atom2);
        antecedent.add(atom3);
        //antecedent.add(atom4);
        antecedent.add(atom5);
        //antecedent.add(atom6);
        //antecedent.add(atom7);
        //antecedent.add(atom8);
        //antecedent.add(atom9);
        //antecedent.add(atom10);
        //antecedent.add(atom11);
        //antecedent.add(atom12);


		SWRLClassAtom head = factory.getSWRLClassAtom(Danger, obs1);
		SWRLRule rule = factory.getSWRLRule(antecedent, Collections.singleton(head));
		manager.applyChange(new AddAxiom(ontology, rule));

		//SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
		
		/* Populate ontology with data from sensors */
		FileInputStream fstream = new FileInputStream("/home/franco/DataSets/SECOM/secom_final.data");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		int i = 0;
   		int cont = 0;
		while (((strLine = br.readLine()) != null) && (cont<10))   {
   			cont++;
   			i++;
   			String dString = strLine.split(" ")[1];
			String newD = dString.split("-")[2] + "-" + dString.split("-")[1] + "-" + dString.split("-")[0];
			OWLLiteral date = factory.getOWLLiteral(newD + "T" + strLine.split(" ")[2]+".000",OWL2Datatype.XSD_DATE_TIME);
		    oa.createIndividual(ontology, manager, factory, contextOntIRI + "#TI" + Integer.toString(i), ValidInstant);
		    oa.assignValueToDataTypeProperty(ontology, manager, factory, hasTimeT, factory.getOWLNamedIndividual(IRI.create(contextOntIRI + "#TI" + Integer.toString(i))), date);
		    oa.createIndividual(ontology, manager, factory, contextOntIRI + "#O" + Integer.toString(i) , Observation);
   			oa.relateIndividuals(ontology, manager, factory, madeBySensor, factory.getOWLNamedIndividual(IRI.create(contextOntIRI + "#O" + Integer.toString(i))), Sensor_1);
   			oa.relateIndividuals(ontology, manager, factory, observedProperty, factory.getOWLNamedIndividual(IRI.create(contextOntIRI + "#O" + Integer.toString(i))), OProperty_1);
   			oa.assignValueToDataTypeProperty(ontology, manager, factory, hasSimpleResult, factory.getOWLNamedIndividual(IRI.create(contextOntIRI + "#O" + Integer.toString(i))), factory.getOWLLiteral(Double.parseDouble(strLine.split(" ")[3])));
   			oa.assignValueToDataTypeProperty(ontology, manager, factory, resultTime, factory.getOWLNamedIndividual(IRI.create(contextOntIRI + "#O" + Integer.toString(i))), date);
   			oa.relateIndividuals(ontology, manager, factory, hasTimeObs, factory.getOWLNamedIndividual(IRI.create(contextOntIRI + "#O" + Integer.toString(i))), factory.getOWLNamedIndividual(IRI.create(contextOntIRI + "#TI" + Integer.toString(i)))); 

   			System.out.println (newD+ "T" + strLine.split(" ")[2]+".000");
   			
   			SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);   			
   			ruleEngine.infer();

   			//ruleEngine.importAssertedOWLAxioms();
   			//ruleEngine.run();
   			//ruleEngine.exportInferredOWLAxioms();
		}
		br.close();

		/* Save ontology */
		File file = new File("/home/franco/Repositories/Context/ContextOntology.owl");
		manager.saveOntology(ontology, IRI.create(file.toURI()));
		//manager.saveOntology(ontology, System.out);


		OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
		ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
		OWLReasonerConfiguration config = new SimpleConfiguration(progressMonitor);
		OWLReasoner reasoner = reasonerFactory.createReasoner(ontology, config);
		reasoner.precomputeInferences(InferenceType.CLASS_ASSERTIONS);

		NodeSet<OWLClass> subClses = reasoner.getSubClasses(Resource, true);
		Set<OWLClass> clses = subClses.getFlattened();
        for (OWLClass cls : clses) {
        	System.out.println(" " + cls);
		}
        
        String answer;
        
        if (reasoner.isConsistent()) answer = "Ouiiiii!!!"; else answer = "No";
		
        System.out.println ("The ontology is Consistent? " + answer);

		//manager.removeAxiom(ontology, rule);

		/* // To put together data, labels and timestamps.
		FileInputStream fstream = new FileInputStream("/home/franco/DataSets/SECOM/secom.data");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		FileInputStream fstream1 = new FileInputStream("/home/franco/DataSets/SECOM/secom_labels.data");
		BufferedReader br1 = new BufferedReader(new InputStreamReader(fstream1));
		String strLine;
		String strLine1;
		FileWriter fichero = null;
        PrintWriter pw = null;
        fichero = new FileWriter("/home/franco/DataSets/SECOM/secom_final.data");
        pw = new PrintWriter(fichero);
   		while (((strLine = br.readLine()) != null) && ((strLine1 = br1.readLine()) != null))   {
		  System.out.println (strLine1);
		  pw.println(strLine1 + " " + strLine);
		}
		br.close();
		br1.close();
		pw.close(); */
	}
}