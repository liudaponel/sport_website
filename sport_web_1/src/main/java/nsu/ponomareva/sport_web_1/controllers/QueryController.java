package nsu.ponomareva.sport_web_1.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import nsu.ponomareva.sport_web_1.DTO.QueryDTO;
import nsu.ponomareva.sport_web_1.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/query")
@CrossOrigin(origins = "${url_frontend}")
public class QueryController {
    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Transactional
    @PostMapping
    public List getQuery(@RequestBody QueryDTO request){
        Query query = entityManager.createNativeQuery(request.getQuery());

        logger.info(request.getQuery());
        logger.info(query.toString());

        if(request.getQuery().charAt(0) == 'S' || request.getQuery().charAt(0) == 's'){
            return query.getResultList();
        }

        query.executeUpdate();
        return null;
    }
}
