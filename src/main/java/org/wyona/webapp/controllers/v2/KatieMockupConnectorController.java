package org.wyona.webapp.controllers.v2;

import io.swagger.annotations.*;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import org.wyona.webapp.models.katie.Domain;
import org.wyona.webapp.models.katie.QnA;

import technology.semi.weaviate.client.Config;
import technology.semi.weaviate.client.WeaviateClient;
import technology.semi.weaviate.client.v1.data.model.WeaviateObject;
import technology.semi.weaviate.client.base.Result;
import technology.semi.weaviate.client.v1.misc.model.Meta;

/**
 * 'Katie Mockup Connector' Controller
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v2")
@AllArgsConstructor
public class KatieMockupConnectorController implements KatieConnectorController {

    private static final String weaviateHost = "localhost:8080";
    private static final String weaviateProtocol = "http";

    /**
     * @see org.wyona.webapp.controllers.v2.KatieConnectorController#createTenant(Domain)
     */
    @PostMapping("/tenant")
    @ApiOperation(value = "Create tenant")
    public ResponseEntity<String> createTenant(@RequestBody Domain domain) {
        return createTenantWeaviateImpl(domain);

        //log.info("TODO: Create tenant associated with Katie domain ID '" + domain.getId() + "' ...");
        //return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    /**
     * @see org.wyona.webapp.controllers.v2.KatieConnectorController#deleteTenant(String)
     */
    @DeleteMapping("/tenant/{domain-id}")
    @ApiOperation(value = "Delete tenant")
    public ResponseEntity<?> deleteTenant(
            @ApiParam(name = "domain-id", value = "Katie domain ID", required = true)
            @PathVariable(name = "domain-id", required = true) String domainId
    ){
        return deleteTenantWeaviateImpl(domainId);

        //log.info("TODO: Delete tenant associated with Katie domain ID '" + domainId + "' ...");
        //return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @see org.wyona.webapp.controllers.v2.KatieConnectorController#train(QnA)
     */
    @PostMapping("/qna")
    @ApiOperation(value = "Add QnA")
    public ResponseEntity<String> train(@RequestBody QnA qna) {
        log.info("TODO: Train QnA ...");
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    /**
     *
     */
    private ResponseEntity<String> deleteTenantWeaviateImpl(String domainId) {
        log.info("Weaviate Impl: Delete tenant associated with Katie domain ID '" + domainId + "' ...");
        Config config = new Config(weaviateProtocol, weaviateHost);
        WeaviateClient client = new WeaviateClient(config);

        Result<Boolean> result = client.data().deleter()
                .withID(domainId)
                .run();

        if (result.hasErrors()) {
            log.error("" + result.getError().getMessages());
        } else {
            log.info("" + result.getResult());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     */
    private ResponseEntity<String> createTenantWeaviateImpl(Domain domain) {
        log.info("Weaviate Impl: Create tenant associated with Katie domain ID '" + domain.getId() + "' ...");
        Config config = new Config(weaviateProtocol, weaviateHost);
        WeaviateClient client = new WeaviateClient(config);

        java.util.Map<String, Object> properties = new java.util.HashMap<>();
        properties.put("name", domain.getName());

        Result<WeaviateObject> result = client.data().creator()
                .withClassName("Tenant")
                .withID(domain.getId())
                .withProperties(properties)
                .run();

        if (result.hasErrors()) {
            log.error("" + result.getError().getMessages());
        } else {
            log.info("" + result.getResult());
        }

        /*
        Result<Meta> meta = client.misc().metaGetter().run();
        if (meta.getError() == null) {
            log.info("meta.hostname: " + meta.getResult().getHostname());
            log.info("meta.version: " + meta.getResult().getVersion());
            log.info("meta.modules: " + meta.getResult().getModules());
        } else {
            log.error("" + meta.getError().getMessages());
        }
         */

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
