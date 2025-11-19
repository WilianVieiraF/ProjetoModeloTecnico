package com.example.projetomodelowilian.resource;

import com.example.projetomodelowilian.model.Tecnico;
import com.example.projetomodelowilian.service.TecnicoService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;


@Path("/tecnicos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TecnicoResource {

    @Inject
    private TecnicoService tecnicoService;

    @GET
    public Response listarTodos() {
        List<Tecnico> tecnicos = tecnicoService.listarTodos();
        return Response.ok(tecnicos).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Tecnico tecnico = tecnicoService.buscarPorId(id);
        if (tecnico != null) {
            return Response.ok(tecnico).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response criar(Tecnico tecnico) {
        Tecnico novoTecnico = tecnicoService.salvar(tecnico);
        URI uri = URI.create("/tecnicos/" + novoTecnico.getId());
        return Response.created(uri).entity(novoTecnico).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, Tecnico tecnico) {
        Tecnico tecnicoAtualizado = tecnicoService.atualizar(id, tecnico);
        if (tecnicoAtualizado != null) {
            return Response.ok(tecnicoAtualizado).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        tecnicoService.deletar(id);
        return Response.noContent().build();
    }
}
