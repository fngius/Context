import java.io.File;
import java.io.IOException;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddImport;
//import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
//import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class Main {

	public static void main(String[] args) throws OWLOntologyCreationException, OWLOntologyStorageException, IOException {
		// TODO Auto-generated method stub
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		String contextOntIRI = "ContextOnt.owl";
		OWLOntology ontology = manager.createOntology(IRI.create(contextOntIRI));
		OWLDataFactory factory = manager.getOWLDataFactory();
		
		OWLClass Resource = factory.getOWLClass(IRI.create(contextOntIRI + "#Resource"));
		OWLAxiom ResourceAx = factory.getOWLDeclarationAxiom(Resource);
		manager.addAxiom(ontology, ResourceAx);
		
		OWLClass Product = factory.getOWLClass(IRI.create(contextOntIRI + "#Product"));
		OWLAxiom ProductAx = factory.getOWLDeclarationAxiom(Product);
		manager.addAxiom(ontology, ProductAx);
		
		OWLClass Part = factory.getOWLClass(IRI.create(contextOntIRI + "#Part"));
		OWLAxiom PartAx = factory.getOWLDeclarationAxiom(Part);
		manager.addAxiom(ontology, PartAx);
		
		OWLClass Component = factory.getOWLClass(IRI.create(contextOntIRI + "#Component"));
		OWLAxiom ComponentAx = factory.getOWLDeclarationAxiom(Component);
		manager.addAxiom(ontology, ComponentAx);
		
		OWLClass ManufacturingFacility = factory.getOWLClass(IRI.create(contextOntIRI + "#ManufacturingFacility"));
		OWLAxiom ManufacturingFacilityAx = factory.getOWLDeclarationAxiom(ManufacturingFacility);
		manager.addAxiom(ontology, ManufacturingFacilityAx);
		
		OWLClass Line = factory.getOWLClass(IRI.create(contextOntIRI + "#Line"));
		OWLAxiom LineAx = factory.getOWLDeclarationAxiom(Line);
		manager.addAxiom(ontology, LineAx);
		
		OWLClass Cell = factory.getOWLClass(IRI.create(contextOntIRI + "#Cell"));
		OWLAxiom CellAx = factory.getOWLDeclarationAxiom(Cell);
		manager.addAxiom(ontology, CellAx);
		
		OWLClass WorkStation = factory.getOWLClass(IRI.create(contextOntIRI + "#WorkStation"));
		OWLAxiom WorkStationAx = factory.getOWLDeclarationAxiom(WorkStation);
		manager.addAxiom(ontology, WorkStationAx);
		
		OWLClass Machine = factory.getOWLClass(IRI.create(contextOntIRI + "#Machine"));
		OWLAxiom MachineAx = factory.getOWLDeclarationAxiom(Machine);
		manager.addAxiom(ontology, MachineAx);
		
		OWLClass AssemblingMachine = factory.getOWLClass(IRI.create(contextOntIRI + "#AssemblingMachine"));
		OWLAxiom AssemblingMachineAx = factory.getOWLDeclarationAxiom(AssemblingMachine);
		manager.addAxiom(ontology, AssemblingMachineAx);
		
		OWLClass TestingMachine = factory.getOWLClass(IRI.create(contextOntIRI + "#TestingMachine"));
		OWLAxiom TestingMachineAx = factory.getOWLDeclarationAxiom(TestingMachine);
		manager.addAxiom(ontology, TestingMachineAx);
		
		OWLClass ProcessingMachine = factory.getOWLClass(IRI.create(contextOntIRI + "#ProcessingMachine"));
		OWLAxiom ProcessingMachineAx = factory.getOWLDeclarationAxiom(ProcessingMachine);
		manager.addAxiom(ontology, ProcessingMachineAx);
		
		OWLClass Staff = factory.getOWLClass(IRI.create(contextOntIRI + "#Staff"));
		OWLAxiom StaffAx = factory.getOWLDeclarationAxiom(Staff);
		manager.addAxiom(ontology, StaffAx);
		
		OWLClass Operator = factory.getOWLClass(IRI.create(contextOntIRI + "#Operator"));
		OWLAxiom OperatorAx = factory.getOWLDeclarationAxiom(Operator);
		manager.addAxiom(ontology, OperatorAx);
		
		OWLClass Manager = factory.getOWLClass(IRI.create(contextOntIRI + "#Manager"));
		OWLAxiom ManagerAx = factory.getOWLDeclarationAxiom(Manager);
		manager.addAxiom(ontology, ManagerAx);
		
		OWLClass MaintenanceTechnician = factory.getOWLClass(IRI.create(contextOntIRI + "#MaintenanceTechnician"));
		OWLAxiom MaintenanceTechnicianAx = factory.getOWLDeclarationAxiom(MaintenanceTechnician);
		manager.addAxiom(ontology, MaintenanceTechnicianAx);
		
		OWLClass Sensor = factory.getOWLClass(IRI.create(contextOntIRI + "#Sensor"));
		OWLAxiom SensorAx = factory.getOWLDeclarationAxiom(Sensor);
		manager.addAxiom(ontology, SensorAx);
		
		OWLClass Location = factory.getOWLClass(IRI.create(contextOntIRI + "#Location"));
		OWLAxiom LocationAx = factory.getOWLDeclarationAxiom(Location);
		manager.addAxiom(ontology, LocationAx);
		
		OWLClass Time = factory.getOWLClass(IRI.create(contextOntIRI + "#Time"));
		OWLAxiom TimeAx = factory.getOWLDeclarationAxiom(Time);
		manager.addAxiom(ontology, TimeAx);
		
		OWLClass Process = factory.getOWLClass(IRI.create(contextOntIRI + "#Process"));
		OWLAxiom ProcessAx = factory.getOWLDeclarationAxiom(Process);
		manager.addAxiom(ontology, ProcessAx);
		
		OWLClass ManufacturingProcess = factory.getOWLClass(IRI.create(contextOntIRI + "#ManufacturingProcess"));
		OWLAxiom ManufacturingProcessAx = factory.getOWLDeclarationAxiom(ManufacturingProcess);
		manager.addAxiom(ontology, ManufacturingProcessAx);
		
		OWLClass HumanOperation = factory.getOWLClass(IRI.create(contextOntIRI + "#HumanOperation"));
		OWLAxiom HumanOperationAx = factory.getOWLDeclarationAxiom(HumanOperation);
		manager.addAxiom(ontology, HumanOperationAx);
		
		OWLClass LogisticProcess = factory.getOWLClass(IRI.create(contextOntIRI + "#LogisticProcess"));
		OWLAxiom LogisticProcessAx = factory.getOWLDeclarationAxiom(LogisticProcess);
		manager.addAxiom(ontology, LogisticProcessAx);
		
		OWLClass Maintenance = factory.getOWLClass(IRI.create(contextOntIRI + "#Maintenance"));
		OWLAxiom MaintenanceAx = factory.getOWLDeclarationAxiom(Maintenance);
		manager.addAxiom(ontology, MaintenanceAx);
		
		OWLClass Handling = factory.getOWLClass(IRI.create(contextOntIRI + "#Handling"));
		OWLAxiom HandlingAx = factory.getOWLDeclarationAxiom(Handling);
		manager.addAxiom(ontology, HandlingAx);
		
		
		OWLClass Situation = factory.getOWLClass(IRI.create(contextOntIRI + "#Situation"));
		OWLAxiom SituationAx = factory.getOWLDeclarationAxiom(Situation);
		manager.addAxiom(ontology, SituationAx);
		
		OWLClass Sit_Diagnosis = factory.getOWLClass(IRI.create(contextOntIRI + "#Sit_Diagnosis"));
		OWLAxiom Sit_DiagnosisAx = factory.getOWLDeclarationAxiom(Sit_Diagnosis);
		manager.addAxiom(ontology, Sit_DiagnosisAx);
		
		OWLClass Sit_Assessment = factory.getOWLClass(IRI.create(contextOntIRI + "#Sit_Assessment"));
		OWLAxiom Sit_AssessmentAx = factory.getOWLDeclarationAxiom(Sit_Assessment);
		manager.addAxiom(ontology, Sit_AssessmentAx);
		
		OWLClass Sit_Monitoring = factory.getOWLClass(IRI.create(contextOntIRI + "#Sit_Monitoring"));
		OWLAxiom Sit_MonitoringAx = factory.getOWLDeclarationAxiom(Sit_Monitoring);
		manager.addAxiom(ontology, Sit_MonitoringAx);
		
		OWLClass Sit_Prediction = factory.getOWLClass(IRI.create(contextOntIRI + "#Sit_Prediction"));
		OWLAxiom Sit_PredictionAx = factory.getOWLDeclarationAxiom(Sit_Prediction);
		manager.addAxiom(ontology, Sit_PredictionAx);
		
		OWLClass Sit_Classification = factory.getOWLClass(IRI.create(contextOntIRI + "#Sit_Classification"));
		OWLAxiom Sit_ClassificationAx = factory.getOWLDeclarationAxiom(Sit_Classification);
		manager.addAxiom(ontology, Sit_ClassificationAx);
		
		
		OWLImportsDeclaration importDeclaration = manager.getOWLDataFactory().getOWLImportsDeclaration(IRI.create("http://www.w3.org/ns/ssn/"));
		manager.applyChange(new AddImport(ontology, importDeclaration));
		
		OWLClass SSNSensor = factory.getOWLClass(IRI.create("https://www.w3.org/TR/vocab-ssn/#SOSASensor"));
		
		OWLClass Property = factory.getOWLClass(IRI.create("https://www.w3.org/TR/vocab-ssn/#SSNProperty"));
		OWLAxiom PropertyAx = factory.getOWLDeclarationAxiom(Property);
		manager.addAxiom(ontology, PropertyAx);
		
		OWLEquivalentClassesAxiom sensorEquiv = factory.getOWLEquivalentClassesAxiom(Sensor, SSNSensor);
		manager.addAxiom(ontology, sensorEquiv);
		
		/* Object Properties */
		
		OWLObjectProperty isInLocation = factory.getOWLObjectProperty(IRI.create(contextOntIRI + "#isInLocation"));
		OWLObjectPropertyDomainAxiom domisInLocationAx = factory.getOWLObjectPropertyDomainAxiom(isInLocation, Resource);
		OWLObjectPropertyRangeAxiom   ranisInLocationAx = factory.getOWLObjectPropertyRangeAxiom(isInLocation, Location);
		manager.addAxiom(ontology, domisInLocationAx);
		manager.addAxiom(ontology, ranisInLocationAx);
        
        
		
		File file = new File("/home/franco/Repositorios/ContextOntology.owl");
		manager.saveOntology(ontology, IRI.create(file.toURI()));
		manager.saveOntology(ontology, System.out);
		
		//System.out.println("Working Directory = " + System.getProperty("user.dir"));
		
		//ontology.saveOntology(new FunctionalSyntaxDocumentFormat(), System.out);

	}

}