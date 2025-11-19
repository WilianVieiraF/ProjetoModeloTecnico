package com.example.projetomodelowilian.resource;

import com.example.projetomodelowilian.model.Chamado;
import com.example.projetomodelowilian.service.ChamadoService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/chamados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChamadoResource {

    
    @Inject
    private ChamadoService chamadoService;

    @GET
    public Response listarTodos() {
        List<Chamado> chamados = chamadoService.listarTodos();
        return Response.ok(chamados).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Chamado chamado = chamadoService.buscarPorId(id);
        if (chamado != null) {
            return Response.ok(chamado).build();
        }
 
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response criar(Chamado chamado) {
        Chamado novoChamado = chamadoService.salvar(chamado);

        URI uri = URI.create("/chamados/" + novoChamado.getId());
        return Response.created(uri).entity(novoChamado).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, Chamado chamado) {
        Chamado chamadoAtualizado = chamadoService.atualizar(id, chamado);
        if (chamadoAtualizado != null) {
            return Response.ok(chamadoAtualizado).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        chamadoService.deletar(id);

        return Response.noContent().build();
    }
}