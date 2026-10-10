package dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "etudiants")
@XmlAccessorType(XmlAccessType.FIELD)
public class EtudiantsXml {

    @XmlElement(name = "etudiant")
    private List<EtudiantXml> etudiants = new ArrayList<>();

    public EtudiantsXml() {}

    public EtudiantsXml(List<EtudiantXml> etudiants) {
        this.etudiants = etudiants;
    }

    public List<EtudiantXml> getEtudiants() { return etudiants; }
    public void setEtudiants(List<EtudiantXml> etudiants) { this.etudiants = etudiants; }
}