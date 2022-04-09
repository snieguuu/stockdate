package com.stockdate.manager.dao;

import com.stockdate.manager.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data access object for {@link Portfolio}.
 */
@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    /**
     * Find portfolio by username
     *
     * @param username name of the owner of portfolio
     * @return portfolio
     */
    Portfolio findByUsername(String username);
}
