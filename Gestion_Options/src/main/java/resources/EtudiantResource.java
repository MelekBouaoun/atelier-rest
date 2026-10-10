package resources;

import dto.EtudiantXml;
import dto.EtudiantsXml;
import entities.Etudiant;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/etudiants")
public class EtudiantResource {

    private EtudiantBusiness business = new EtudiantBusiness();
    private OptionBusiness optionBusiness = new OptionBusiness();

    // 1) POST /etudiants  (404 si l'option n'existe pas)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response ajouter(Etudiant etudiant) {
        System.out.println("POST etudiant=" + etudiant
                + " option=" + (etudiant == null ? "null" : etudiant.getOption()));
        if (etudiant == null || etudiant.getOption() == null
                || optionBusiness.getOptionByCode(etudiant.getOption().getCodeOption()) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        business.addEtudiant(etudiant);
        return Response.ok(etudiant).build();
    }

    // 2) GET /etudiants
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response lister() {
        return Response.ok(business.getAllEtudiants()).build();
    }

    // 6) GET /etudiants/option?codeOption=1  -> XML
    @GET
    @Path("/option")
    @Produces(MediaType.APPLICATION_XML)
    public Response parOption(@QueryParam("codeOption") int codeOption) {
        Option option = optionBusiness.getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<EtudiantXml> liste = new ArrayList<>();
        for (Etudiant e : business.getEtudiantsByOption(option)) {
            liste.add(new EtudiantXml(e.getIdentifiant(), e.getNom(), e.getPrenom(),
                    e.getAnneeEtude(), e.getEmail()));
        }
        return Response.ok(new EtudiantsXml(liste)).build();
    }

    // 3) GET /etudiants/{id}
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getParId(@PathParam("id") String id) {
        Etudiant e = business.getEtudiantByIdentifiant(id);
        if (e == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(e).build();
    }

    // 5) PUT /etudiants/{id}
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modifier(@PathParam("id") String id, Etudiant etudiant) {
        if (etudiant != null && business.updateEtudiant(id, etudiant)) {
            return Response.ok(etudiant).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 4) DELETE /etudiants/{id}
    @DELETE
    @Path("/{id}")
    public Response supprimer(@PathParam("id") String id) {
        if (business.deleteEtudiant(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}