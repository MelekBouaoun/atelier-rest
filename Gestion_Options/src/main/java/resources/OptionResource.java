package resources;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/options")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OptionResource {

    private OptionBusiness business = new OptionBusiness();

    // 2) + 3) GET /options  et  GET /options?domaine=...
    @GET
    public Response lister(@QueryParam("domaine") String domaine) {
        List<Option> result;
        if (domaine == null || domaine.trim().isEmpty()) {
            result = business.getListeOptions();
        } else {
            result = business.getOptionsByDomaine(domaine);
        }
        return Response.ok(result).build();
    }

    // 6) GET /options/{code}
    @GET
    @Path("/{code}")
    public Response getParCode(@PathParam("code") int code) {
        Option o = business.getOptionByCode(code);
        if (o == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(o).build();
    }

    // 1) POST /options
    @POST
    public Response ajouter(Option option) {
        if (option != null && business.addOption(option)) {
            return Response.ok(option).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 5) PUT /options/{code}
    @PUT
    @Path("/{code}")
    public Response modifier(@PathParam("code") int code, Option option) {
        if (option != null && business.updateOption(code, option)) {
            return Response.ok(option).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 4) DELETE /options/{code}
    @DELETE
    @Path("/{code}")
    public Response supprimer(@PathParam("code") int code) {
        if (business.deleteOption(code)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}