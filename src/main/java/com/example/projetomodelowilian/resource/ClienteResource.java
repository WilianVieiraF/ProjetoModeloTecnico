package com.example.projetomodelowilian.resource;

import com.example.projetomodelowilian.model.Cliente;
import com.example.projetomodelowilian.service.ClienteService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    private ClienteService clienteService;

    @GET
    public Response listarTodos() {
        List<Cliente> clientes = clienteService.listarTodos();
        return Response.ok(clientes).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        if (cliente != null) {
            return Response.ok(cliente).build();
        }
    
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response criar(Cliente cliente) {
        Cliente novoCliente = clienteService.salvar(cliente);
    
        URI uri = URI.create("/clientes/" + novoCliente.getId());
        return Response.created(uri).entity(novoCliente).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, Cliente cliente) {
        Cliente clienteAtualizado = clienteService.atualizar(id, cliente);
        if (clienteAtualizado != null) {
            return Response.ok(clienteAtualizado).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        clienteService.deletar(id);
    
        return Response.noContent().build();
    }
}
