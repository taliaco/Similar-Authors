package dal;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import Authors.recommendAuthor;




@Component
public class AuthorDal {
	// Injected database connection:
    @PersistenceContext 
     EntityManager em;
 
    // Stores a new recommendAuthor:
    @Transactional
    public void persist(recommendAuthor author) {
        em.persist(author);
    }
 
    // Retrieves all the recommendAuthor:
    public ArrayList<recommendAuthor> getAllAuthor() {
        TypedQuery<recommendAuthor> query = em.createQuery(
            "SELECT a FROM recommendAuthor a ORDER BY a.id", recommendAuthor.class);
        return new ArrayList<recommendAuthor>(query.getResultList());
    }
}
