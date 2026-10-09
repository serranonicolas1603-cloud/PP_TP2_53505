package Modelo.certificacion;
import Modelo.*;

import javax.xml.transform.sax.SAXResult;

public interface Certificable {
    String ENTIDAD_EMISORA= "UTN-Universidad Tecnologica Nacional";


    String generarCertificado(Estudiante estudiante);

}