package aacp.server.user.repository;

import aacp.server.user.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class RefreshTokenRepository {

    private final EntityManager em;

    public Long save(RefreshToken refreshToken){
        em.persist(refreshToken);
        return refreshToken.getId();
    }

    public RefreshToken findById(Long id){
        return em.find(RefreshToken.class, id);
    }

    public List<RefreshToken> findByUserIdentifier(String userIdentifier){
        return em.createQuery(
                        "select r from RefreshToken r" +
                                " where exists" +
                                " (select u from r.user u where u.identifier =: identifier)"
                        , RefreshToken.class)
                .setParameter("identifier", userIdentifier)
                .getResultList();
    }

}
