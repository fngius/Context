import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddImport;
//import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
//import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLObjectProperty;
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
		
		/* Classes */
		
		OWLClass Resource = factory.getOWLClass(IRI.create(contextOntIRI + "#Resource"));
		OWLAxiom ResourceAx = factory.getOWLDeclarationAxiom(Resource);
		manager.addAxiom(ontology, ResourceAx);
		OWLAnnotation commentRes = factory.getOWLAnnotation(factory.getRDFSComment(), factory.getOWLLiteral("A class which represents resources of the industry", "en"));
		OWLAxiom commentResAx = factory.getOWLAnnotationAssertionAxiom(Resource.getIRI(), commentRes);
		manager.addAxiom(ontology, commentResAx);
		
		OWLClass Product = factory.getOWLClass(IRI.create(contextOntIRI + "#Product"));
		OWLAxiom ProductAx = factory.getOWLDeclarationAxiom(Product);
		manager.addAxiom(ontology, ProductAx);
		OWLAxiom subClassProdResoAx = factory.getOWLSubClassOfAxiom(Product, Resource);
		manager.addAxiom(ontology, subClassProdResoAx);
		
		OWLClass Part = factory.getOWLClass(IRI.create(contextOntIRI + "#Part"));
		OWLAxiom PartAx = factory.getOWLDeclarationAxiom(Part);
		manager.addAxiom(ontology, PartAx);
		OWLAxiom subClassPartProdAx = factory.getOWLSubClassOfAxiom(Part,Product);
		manager.addAxiom(ontology, subClassPartProdAx);
		
		OWLClass Component = factory.getOWLClass(IRI.create(contextOntIRI + "#Component"));
		OWLAxiom ComponentAx = factory.getOWLDeclarationAxiom(Component);
		manager.addAxiom(ontology, ComponentAx);
		OWLAxiom subClassCompPartAx = factory.getOWLSubClassOfAxiom(Component, Part);
		manager.addAxiom(ontology, subClassCompPartAx);
		
		OWLClass ManufacturingFacility = factory.getOWLClass(IRI.create(contextOntIRI + "#ManufacturingFacility"));
		OWLAxiom ManufacturingFacilityAx = factory.getOWLDeclarationAxiom(ManufacturingFacility);
		manager.addAxiom(ontology, ManufacturingFacilityAx);
		OWLAxiom subClassMFResoAx = factory.getOWLSubClassOfAxiom(ManufacturingFacility, Resource);
		manager.addAxiom(ontology, subClassMFResoAx);
		
		OWLClass Line = factory.getOWLClass(IRI.create(contextOntIRI + "#Line"));
		OWLAxiom LineAx = factory.getOWLDeclarationAxiom(Line);
		manager.addAxiom(ontology, LineAx);
		OWLAxiom subClassLineMFAx = factory.getOWLSubClassOfAxiom(Line, ManufacturingFacility);
		manager.addAxiom(ontology, subClassLineMFAx);
		
		OWLClass Cell = factory.getOWLClass(IRI.create(contextOntIRI + "#Cell"));
		OWLAxiom CellAx = factory.getOWLDeclarationAxiom(Cell);
		manager.addAxiom(ontology, CellAx);
		OWLAxiom subClassCellLineAx = factory.getOWLSubClassOfAxiom(Cell, Line);
		manager.addAxiom(ontology, subClassCellLineAx);
		
		OWLClass WorkStation = factory.getOWLClass(IRI.create(contextOntIRI + "#WorkStation"));
		OWLAxiom WorkStationAx = factory.getOWLDeclarationAxiom(WorkStation);
		manager.addAxiom(ontology, WorkStationAx);
		OWLAxiom subClassWSCellAx = factory.getOWLSubClassOfAxiom(WorkStation,Cell);
		manager.addAxiom(ontology, subClassWSCellAx);
		
		OWLClass Machine = factory.getOWLClass(IRI.create(contextOntIRI + "#Machine"));
		OWLAxiom MachineAx = factory.getOWLDeclarationAxiom(Machine);
		manager.addAxiom(ontology, MachineAx);
		OWLAxiom subClassMachMFAx = factory.getOWLSubClassOfAxiom(Machine, ManufacturingFacility);
		manager.addAxiom(ontology, subClassMachMFAx);
		
		OWLClass AssemblingMachine = factory.getOWLClass(IRI.create(contextOntIRI + "#AssemblingMachine"));
		OWLAxiom AssemblingMachineAx = factory.getOWLDeclarationAxiom(AssemblingMachine);
		manager.addAxiom(ontology, AssemblingMachineAx);
		OWLAxiom subClassAMMachAx = factory.getOWLSubClassOfAxiom(AssemblingMachine, Machine);
		manager.addAxiom(ontology, subClassAMMachAx);
		
		OWLClass TestingMachine = factory.getOWLClass(IRI.create(contextOntIRI + "#TestingMachine"));
		OWLAxiom TestingMachineAx = factory.getOWLDeclarationAxiom(TestingMachine);
		manager.addAxiom(ontology, TestingMachineAx);
		OWLAxiom subClassTMMachAx = factory.getOWLSubClassOfAxiom(TestingMachine, Machine);
		manager.addAxiom(ontology, subClassTMMachAx);
		
		OWLClass ProcessingMachine = factory.getOWLClass(IRI.create(contextOntIRI + "#ProcessingMachine"));
		OWLAxiom ProcessingMachineAx = factory.getOWLDeclarationAxiom(ProcessingMachine);
		manager.addAxiom(ontology, ProcessingMachineAx);
		OWLAxiom subClassPMMachAx = factory.getOWLSubClassOfAxiom(ProcessingMachine, Machine);
		manager.addAxiom(ontology, subClassPMMachAx);
		
		OWLClass Staff = factory.getOWLClass(IRI.create(contextOntIRI + "#Staff"));
		OWLAxiom StaffAx = factory.getOWLDeclarationAxiom(Staff);
		manager.addAxiom(ontology, StaffAx);
		OWLAxiom subClassStaffResoAx = factory.getOWLSubClassOfAxiom(Staff, Resource);
		manager.addAxiom(ontology, subClassStaffResoAx);
		
		OWLClass Operator = factory.getOWLClass(IRI.create(contextOntIRI + "#Operator"));
		OWLAxiom OperatorAx = factory.getOWLDeclarationAxiom(Operator);
		manager.addAxiom(ontology, OperatorAx);
		OWLAxiom subClassOpStaffAx = factory.getOWLSubClassOfAxiom(Operator, Staff);
		manager.addAxiom(ontology, subClassOpStaffAx);
		
		OWLClass Manager = factory.getOWLClass(IRI.create(contextOntIRI + "#Manager"));
		OWLAxiom ManagerAx = factory.getOWLDeclarationAxiom(Manager);
		manager.addAxiom(ontology, ManagerAx);
		OWLAxiom subClassManaStaffAx = factory.getOWLSubClassOfAxiom(Manager, Staff);
		manager.addAxiom(ontology, subClassManaStaffAx);
		
		OWLClass MaintenanceTechnician = factory.getOWLClass(IRI.create(contextOntIRI + "#MaintenanceTechnician"));
		OWLAxiom MaintenanceTechnicianAx = factory.getOWLDeclarationAxiom(MaintenanceTechnician);
		manager.addAxiom(ontology, MaintenanceTechnicianAx);
		OWLAxiom subClassMTStaffAx = factory.getOWLSubClassOfAxiom(MaintenanceTechnician, Staff);
		manager.addAxiom(ontology, subClassMTStaffAx);
		
		//OWLClass Sensor = factory.getOWLClass(IRI.create(contextOntIRI + "#Sensor"));
		//OWLAxiom SensorAx = factory.getOWLDeclarationAxiom(Sensor);
		//manager.addAxiom(ontology, SensorAx);
		
		OWLClass Location = factory.getOWLClass(IRI.create(contextOntIRI + "#Location"));
		OWLAxiom LocationAx = factory.getOWLDeclarationAxiom(Location);
		manager.addAxiom(ontology, LocationAx);
		
		//OWLClass Time = factory.getOWLClass(IRI.create(contextOntIRI + "#Time"));
		//OWLAxiom TimeAx = factory.getOWLDeclarationAxiom(Time);
		//manager.addAxiom(ontology, TimeAx);
		
		OWLClass Process = factory.getOWLClass(IRI.create(contextOntIRI + "#Process"));
		OWLAxiom ProcessAx = factory.getOWLDeclarationAxiom(Process);
		manager.addAxiom(ontology, ProcessAx);
		
		OWLClass ManufacturingProcess = factory.getOWLClass(IRI.create(contextOntIRI + "#ManufacturingProcess"));
		OWLAxiom ManufacturingProcessAx = factory.getOWLDeclarationAxiom(ManufacturingProcess);
		manager.addAxiom(ontology, ManufacturingProcessAx);
		OWLAxiom subClassMPProcAx = factory.getOWLSubClassOfAxiom(ManufacturingProcess, Process);
		manager.addAxiom(ontology, subClassMPProcAx);
		
		OWLClass HumanOperation = factory.getOWLClass(IRI.create(contextOntIRI + "#HumanOperation"));
		OWLAxiom HumanOperationAx = factory.getOWLDeclarationAxiom(HumanOperation);
		manager.addAxiom(ontology, HumanOperationAx);
		OWLAxiom subClassHOProcAx = factory.getOWLSubClassOfAxiom(HumanOperation, Process);
		manager.addAxiom(ontology, subClassHOProcAx);
		
		OWLClass LogisticProcess = factory.getOWLClass(IRI.create(contextOntIRI + "#LogisticProcess"));
		OWLAxiom LogisticProcessAx = factory.getOWLDeclarationAxiom(LogisticProcess);
		manager.addAxiom(ontology, LogisticProcessAx);
		OWLAxiom subClassLPProcAx = factory.getOWLSubClassOfAxiom(LogisticProcess, Process);
		manager.addAxiom(ontology, subClassLPProcAx);
		
		OWLClass Maintenance = factory.getOWLClass(IRI.create(contextOntIRI + "#Maintenance"));
		OWLAxiom MaintenanceAx = factory.getOWLDeclarationAxiom(Maintenance);
		manager.addAxiom(ontology, MaintenanceAx);
		OWLAxiom subClassMainLPAx = factory.getOWLSubClassOfAxiom(Maintenance, LogisticProcess);
		manager.addAxiom(ontology, subClassMainLPAx);
		
		OWLClass Handling = factory.getOWLClass(IRI.create(contextOntIRI + "#Handling"));
		OWLAxiom HandlingAx = factory.getOWLDeclarationAxiom(Handling);
		manager.addAxiom(ontology, HandlingAx);
		OWLAxiom subClassHanLPAx = factory.getOWLSubClassOfAxiom(Handling, LogisticProcess);
		manager.addAxiom(ontology, subClassHanLPAx);
		
		OWLClass Situation = factory.getOWLClass(IRI.create(contextOntIRI + "#Situation"));
		OWLAxiom SituationAx = factory.getOWLDeclarationAxiom(Situation);
		manager.addAxiom(ontology, SituationAx);
		
		OWLClass Sit_Diagnosis = factory.getOWLClass(IRI.create(contextOntIRI + "#Sit_Diagnosis"));
		OWLAxiom Sit_DiagnosisAx = factory.getOWLDeclarationAxiom(Sit_Diagnosis);
		manager.addAxiom(ontology, Sit_DiagnosisAx);
		OWLAxiom subClassSitDiagSitAx = factory.getOWLSubClassOfAxiom(Sit_Diagnosis, Situation);
		manager.addAxiom(ontology, subClassSitDiagSitAx);
		
		OWLClass Sit_Assessment = factory.getOWLClass(IRI.create(contextOntIRI + "#Sit_Assessment"));
		OWLAxiom Sit_AssessmentAx = factory.getOWLDeclarationAxiom(Sit_Assessment);
		manager.addAxiom(ontology, Sit_AssessmentAx);
		OWLAxiom subClassSitAssesSitAx = factory.getOWLSubClassOfAxiom(Sit_Assessment, Situation);
		manager.addAxiom(ontology, subClassSitAssesSitAx);
		
		OWLClass Sit_Monitoring = factory.getOWLClass(IRI.create(contextOntIRI + "#Sit_Monitoring"));
		OWLAxiom Sit_MonitoringAx = factory.getOWLDeclarationAxiom(Sit_Monitoring);
		manager.addAxiom(ontology, Sit_MonitoringAx);
		OWLAxiom subClassSitMonSitAx = factory.getOWLSubClassOfAxiom(Sit_Monitoring, Situation);
		manager.addAxiom(ontology, subClassSitMonSitAx);
		
		OWLClass Sit_Prediction = factory.getOWLClass(IRI.create(contextOntIRI + "#Sit_Prediction"));
		OWLAxiom Sit_PredictionAx = factory.getOWLDeclarationAxiom(Sit_Prediction);
		manager.addAxiom(ontology, Sit_PredictionAx);
		OWLAxiom subClassSitPredSitAx = factory.getOWLSubClassOfAxiom(Sit_Prediction, Situation);
		manager.addAxiom(ontology, subClassSitPredSitAx);
		
		OWLClass Sit_Classification = factory.getOWLClass(IRI.create(contextOntIRI + "#Sit_Classification"));
		OWLAxiom Sit_ClassificationAx = factory.getOWLDeclarationAxiom(Sit_Classification);
		manager.addAxiom(ontology, Sit_ClassificationAx);
		OWLAxiom subClassSitClassSitAx = factory.getOWLSubClassOfAxiom(Sit_Classification, Situation);
		manager.addAxiom(ontology, subClassSitClassSitAx);
		
		/* Imported Ontologies */
		
		//OWLImportsDeclaration importDeclarationSWRLTO = manager.getOWLDataFactory().getOWLImportsDeclaration(IRI.create("http://swrl.stanford.edu/ontologies/built-ins/3.3/temporal.owl"));
		//manager.applyChange(new AddImport(ontology, importDeclarationSWRLTO));
		OWLImportsDeclaration importDeclarationSWRLTO = manager.getOWLDataFactory().getOWLImportsDeclaration(IRI.create("http://www.w3.org/2006/time"));
		manager.applyChange(new AddImport(ontology, importDeclarationSWRLTO));		
			
		OWLImportsDeclaration importDeclaration = manager.getOWLDataFactory().getOWLImportsDeclaration(IRI.create("http://www.w3.org/ns/ssn/"));
		manager.applyChange(new AddImport(ontology, importDeclaration));
		
		//OWLClass SSNSensor = factory.getOWLClass(IRI.create("https://www.w3.org/TR/vocab-ssn/#SOSASensor"));
		
		//OWLClass SSNProperty = factory.getOWLClass(IRI.create("https://www.w3.org/TR/vocab-ssn/#SSNProperty"));
		//OWLAxiom SSNPropertyAx = factory.getOWLDeclarationAxiom(SSNProperty);
		//manager.addAxiom(ontology, SSNPropertyAx);
		
		//OWLEquivalentClassesAxiom sensorEquiv = factory.getOWLEquivalentClassesAxiom(Sensor, SSNSensor);
		//manager.addAxiom(ontology, sensorEquiv);
		
		/* Object Properties */
		
		OWLObjectProperty isInLocation = factory.getOWLObjectProperty(IRI.create(contextOntIRI + "#isInLocation"));
		OWLObjectPropertyDomainAxiom domisInLocationAx = factory.getOWLObjectPropertyDomainAxiom(isInLocation, Resource);
		OWLObjectPropertyRangeAxiom   ranisInLocationAx = factory.getOWLObjectPropertyRangeAxiom(isInLocation, Location);
		manager.addAxiom(ontology, domisInLocationAx);
		manager.addAxiom(ontology, ranisInLocationAx);
        
		OWLObjectProperty appliesTo = factory.getOWLObjectProperty(IRI.create(contextOntIRI + "#appliesTo"));
		OWLObjectPropertyDomainAxiom domappliesToAx = factory.getOWLObjectPropertyDomainAxiom(appliesTo, Process);
		OWLObjectPropertyRangeAxiom   ranappliesToAx = factory.getOWLObjectPropertyRangeAxiom(appliesTo, Resource);
		manager.addAxiom(ontology, domappliesToAx);
		manager.addAxiom(ontology, ranappliesToAx);
		
		OWLObjectProperty performsProcess = factory.getOWLObjectProperty(IRI.create(contextOntIRI + "#performsProcess"));
		OWLObjectPropertyDomainAxiom domperformsProcessAx = factory.getOWLObjectPropertyDomainAxiom(performsProcess, Resource);
		OWLObjectPropertyRangeAxiom   ranperformsProcessAx = factory.getOWLObjectPropertyRangeAxiom(performsProcess, Process);
		manager.addAxiom(ontology, domperformsProcessAx);
		manager.addAxiom(ontology, ranperformsProcessAx);
		
		OWLObjectProperty happensIn = factory.getOWLObjectProperty(IRI.create(contextOntIRI + "#happensIn"));
		OWLObjectPropertyDomainAxiom domhappensInAx = factory.getOWLObjectPropertyDomainAxiom(happensIn, Process);
		OWLObjectPropertyRangeAxiom   ranhappensInAx = factory.getOWLObjectPropertyRangeAxiom(happensIn, Location);
		manager.addAxiom(ontology, domhappensInAx);
		manager.addAxiom(ontology, ranhappensInAx);
		
		OWLObjectProperty hasTime1 = factory.getOWLObjectProperty(IRI.create(contextOntIRI + "#hasTime1"));
		OWLObjectPropertyDomainAxiom domhasTime1Ax = factory.getOWLObjectPropertyDomainAxiom(hasTime1, Process);
		OWLObjectPropertyRangeAxiom   ranhasTime1Ax = factory.getOWLObjectPropertyRangeAxiom(hasTime1, factory.getOWLClass(IRI.create("http://www.w3.org/2006/time#TemporalEntity")));
		manager.addAxiom(ontology, domhasTime1Ax);
		manager.addAxiom(ontology, ranhasTime1Ax);
		
		OWLObjectProperty hasTime2 = factory.getOWLObjectProperty(IRI.create(contextOntIRI + "#hasTime2"));
		OWLObjectPropertyDomainAxiom domhasTime2Ax = factory.getOWLObjectPropertyDomainAxiom(hasTime2, Situation);
		OWLObjectPropertyRangeAxiom   ranhasTime2Ax = factory.getOWLObjectPropertyRangeAxiom(hasTime2, factory.getOWLClass(IRI.create("http://www.w3.org/2006/time#TemporalEntity")));
		manager.addAxiom(ontology, domhasTime2Ax);
		manager.addAxiom(ontology, ranhasTime2Ax);
		
		OWLObjectProperty hasTime3 = factory.getOWLObjectProperty(IRI.create(contextOntIRI + "#hasTime3"));
		OWLObjectPropertyDomainAxiom domhasTime3Ax = factory.getOWLObjectPropertyDomainAxiom(hasTime3, factory.getOWLClass(IRI.create("http://www.w3.org/ns/sosa/Sensor")));
		OWLObjectPropertyRangeAxiom   ranhasTime3Ax = factory.getOWLObjectPropertyRangeAxiom(hasTime3, factory.getOWLClass(IRI.create("http://www.w3.org/2006/time#TemporalEntity")));
		manager.addAxiom(ontology, domhasTime3Ax);
		manager.addAxiom(ontology, ranhasTime3Ax);
		
		OWLObjectProperty hasSubProcess = factory.getOWLObjectProperty(IRI.create(contextOntIRI + "#hasSubProcess"));
		OWLObjectPropertyDomainAxiom domhasSubProcessAx = factory.getOWLObjectPropertyDomainAxiom(hasSubProcess, Process);
		OWLObjectPropertyRangeAxiom   ranhasSubProcessAx = factory.getOWLObjectPropertyRangeAxiom(hasSubProcess, Process);
		manager.addAxiom(ontology, domhasSubProcessAx);
		manager.addAxiom(ontology, ranhasSubProcessAx);
		
		OWLObjectProperty hasProcess = factory.getOWLObjectProperty(IRI.create(contextOntIRI + "#hasProcess"));
		OWLObjectPropertyDomainAxiom domhasProcessAx = factory.getOWLObjectPropertyDomainAxiom(hasProcess, Situation);
		OWLObjectPropertyRangeAxiom   ranhasProcessAx = factory.getOWLObjectPropertyRangeAxiom(hasProcess, Process);
		manager.addAxiom(ontology, domhasProcessAx);
		manager.addAxiom(ontology, ranhasProcessAx);
		
		OWLObjectProperty hasSensor = factory.getOWLObjectProperty(IRI.create(contextOntIRI + "#hasSensor"));
		OWLObjectPropertyDomainAxiom domhasSensorAx = factory.getOWLObjectPropertyDomainAxiom(hasSensor, Resource);
		OWLObjectPropertyRangeAxiom   ranhasSensorAx = factory.getOWLObjectPropertyRangeAxiom(hasSensor, factory.getOWLClass(IRI.create("http://www.w3.org/ns/sosa/Sensor")));
		manager.addAxiom(ontology, domhasSensorAx);
		manager.addAxiom(ontology, ranhasSensorAx);
		
		OWLObjectProperty locatedIn = factory.getOWLObjectProperty(IRI.create(contextOntIRI + "#locatedIn"));
		OWLObjectPropertyDomainAxiom domlocatedInAx = factory.getOWLObjectPropertyDomainAxiom(locatedIn, factory.getOWLClass(IRI.create("http://www.w3.org/ns/sosa/Sensor")));
		OWLObjectPropertyRangeAxiom   ranlocatedInAx = factory.getOWLObjectPropertyRangeAxiom(locatedIn, Location);
		manager.addAxiom(ontology, domlocatedInAx);
		manager.addAxiom(ontology, ranlocatedInAx);
		
		OWLObjectProperty hasProperty = factory.getOWLObjectProperty(IRI.create(contextOntIRI + "#hasProperty"));
		OWLObjectPropertyDomainAxiom domhasPropertyAx = factory.getOWLObjectPropertyDomainAxiom(hasProperty, Resource);
		OWLObjectPropertyRangeAxiom   ranhasPropertyAx = factory.getOWLObjectPropertyRangeAxiom(hasProperty, factory.getOWLClass(IRI.create("http://www.w3.org/ns/ssn/Property")));
		manager.addAxiom(ontology, domhasPropertyAx);
		manager.addAxiom(ontology, ranhasPropertyAx);
		
		OWLObjectProperty hasObject = factory.getOWLObjectProperty(IRI.create(contextOntIRI + "#hasObject"));
		OWLObjectPropertyDomainAxiom domhasObjectAx = factory.getOWLObjectPropertyDomainAxiom(hasObject, Situation);
		OWLObjectPropertyRangeAxiom   ranhasObjectAx = factory.getOWLObjectPropertyRangeAxiom(hasObject, Resource);
		manager.addAxiom(ontology, domhasObjectAx);
		manager.addAxiom(ontology, ranhasObjectAx);
		
		OWLObjectProperty hasSensorLoc = factory.getOWLObjectProperty(IRI.create(contextOntIRI + "#hasSensorLoc"));
		OWLObjectPropertyDomainAxiom domhasSensorLocAx = factory.getOWLObjectPropertyDomainAxiom(hasSensorLoc, Location);
		OWLObjectPropertyRangeAxiom   ranhasSensorLocAx = factory.getOWLObjectPropertyRangeAxiom(hasSensorLoc, factory.getOWLClass(IRI.create("http://www.w3.org/ns/sosa/Sensor")));
		manager.addAxiom(ontology, domhasSensorLocAx);
		manager.addAxiom(ontology, ranhasSensorLocAx);
		
		OWLObjectProperty operates = factory.getOWLObjectProperty(IRI.create(contextOntIRI + "#operates"));
		OWLObjectPropertyDomainAxiom domoperatesAx = factory.getOWLObjectPropertyDomainAxiom(operates, Staff);
		OWLObjectPropertyRangeAxiom   ranoperatesAx = factory.getOWLObjectPropertyRangeAxiom(operates, ManufacturingFacility);
		manager.addAxiom(ontology, domoperatesAx);
		manager.addAxiom(ontology, ranoperatesAx);
		
		OWLObjectProperty contains = factory.getOWLObjectProperty(IRI.create(contextOntIRI + "#contains"));
		OWLObjectPropertyDomainAxiom domcontainsAx = factory.getOWLObjectPropertyDomainAxiom(contains, WorkStation);
		OWLObjectPropertyRangeAxiom   rancontainsAx = factory.getOWLObjectPropertyRangeAxiom(contains, Machine);
		manager.addAxiom(ontology, domcontainsAx);
		manager.addAxiom(ontology, rancontainsAx);
		
		/* Save ontology */
		
		File file = new File("/home/franco/Repositories/Context/ContextOntology.owl");
		manager.saveOntology(ontology, IRI.create(file.toURI()));
		manager.saveOntology(ontology, System.out);
		
		
		//System.out.println("Working Directory = " + System.getProperty("user.dir"));		
		//ontology.saveOntology(new FunctionalSyntaxDocumentFormat(), System.out);

	}

}