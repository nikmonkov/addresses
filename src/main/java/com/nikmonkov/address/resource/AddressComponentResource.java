package com.nikmonkov.address.resource;

import com.nikmonkov.address.model.AddressComponent;
import com.nikmonkov.address.service.AddressService;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/v1/address")
@Produces(MediaType.APPLICATION_JSON)
public class AddressComponentResource {

    @Inject
    AddressService addressService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(name = "createAddressComponent")
    @Counted(name = "createAddressComponentCount")
    public AddressComponent create(AddressComponent addressComponent) {
        if (StringUtils.isEmpty(addressComponent.getName())) {
            throw new BadRequestException("name is not specified");
        }
        return addressService.create(addressComponent);
    }

    @GET
    @Path("/{id}")
    @Timed(name = "getById")
    @Counted(name = "getByIdCount")
    public AddressComponent getById(@PathParam("id") String id) {
        if (StringUtils.isEmpty(id)) {
            throw new BadRequestException("is not specified");
        }
        AddressComponent addressComponent = addressService.getById(id);
        if (addressComponent == null) {
            throw new NotFoundException();
        }
        return addressComponent;
    }

    @GET
    @Path("/search")
    public List<AddressComponent> searchByName(@QueryParam("name") String name) {
        if (StringUtils.isEmpty(name)) {
            throw new BadRequestException("name is not specified");
        }
        return addressService.searchAddress(name);
    }

    @GET
    public List<AddressComponent> getByParent(@QueryParam("parent_id") String parentId) {
        if (StringUtils.isEmpty(parentId)) {
            throw new BadRequestException("parent_id is not specified");
        }
        return addressService.findByParentId(parentId);
    }

}
