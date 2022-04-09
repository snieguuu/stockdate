package com.stockdate.manager.controllers;

import com.stockdate.manager.dao.PortfolioRepository;
import com.stockdate.manager.model.Portfolio;
import com.stockdate.manager.model.SharesPacket;
import com.stockdate.manager.services.PortfolioService;
import com.stockdate.manager.services.SharesPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * {@link Portfolio} controller
 */
@RestController
public class PortfolioController {

    @Autowired
    SharesPacketService sharesPacketService;

    @Autowired
    PortfolioService portfolioService;

    @Autowired
    PortfolioRepository portfolioRepository;

    /**
     * Method getting {@link Portfolio} for particular user.
     *
     * @param username name of the portfolio owner
     * @return portfolio for particular user
     */
    @GetMapping("/users/{username}/portfolio")
    public Portfolio getPortfolio(@PathVariable String username) {
        return portfolioService.getPortfolioCreateIfDoesNotExist(username);
    }

    /**
     * Method getting {@link SharesPacket} with particular ticker for particular user.
     *
     * @param username name of the portfolio owner
     * @param ticker   ticker of {@link SharesPacket}
     * @return {@link ResponseEntity} with {@link SharesPacket} and appropriate {@link HttpStatus} code
     */
    public ResponseEntity<SharesPacket> getSharesPacket(@PathVariable String username, @RequestBody String ticker) {
        SharesPacket sharesPacket = portfolioService.getSharesPacket(username, ticker);
        if (sharesPacket.getTicker().equals(ticker)) {
            return new ResponseEntity<>(sharesPacket, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(sharesPacket, HttpStatus.PARTIAL_CONTENT);
        }
    }

    /**
     * Post mapping method adds {@link SharesPacket} to the {@link Portfolio}.
     *
     * @param username     portfolio username
     * @param sharesPacket shares packet to add
     * @return {@link ResponseEntity}
     */
    @PostMapping("/users/{username}/portfolio")
    public ResponseEntity<Void> addSharesPacket(
            @PathVariable String username, @RequestBody SharesPacket sharesPacket) {
        Portfolio portfolio = portfolioService.getPortfolioCreateIfDoesNotExist(username);

        HashMap<String, Double> resultValuesMap = portfolioService.recalculatePortfolio(sharesPacket, portfolio);
        sharesPacketService.setSharesPacketValuesAfterChange(sharesPacket, resultValuesMap);
        portfolioService.setPortfolioValuesAfterChange(portfolio, sharesPacket, resultValuesMap, false);
        portfolioRepository.save(portfolio);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{username}/portfolio/{id}")
    public ResponseEntity<Void> deleteSharesPacket(@PathVariable long id) {
        portfolioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Put mapping method updates {@link SharesPacket} in the {@link Portfolio}.
     *
     * @param username     portfolio username
     * @param sharesPacket shares packet to update
     * @param id           id of shares packet
     * @return true if shares packet was updated, false otherwise
     */
    @PutMapping("/users/{username}/portfolio/{id}")
    public boolean updateSharesPacket(
            @PathVariable String username, @RequestBody SharesPacket sharesPacket, @PathVariable long id) {
        Portfolio portfolio = portfolioService.getPortfolioCreateIfDoesNotExist(username);
        boolean isUpdated = portfolio.updateSharesPacket(sharesPacket);
        if (isUpdated) {
            HashMap<String, Double> resultValuesMap = portfolioService.recalculatePortfolio(sharesPacket, portfolio);
            sharesPacketService.setSharesPacketValuesAfterChange(sharesPacket, resultValuesMap);
            portfolioService.setPortfolioValuesAfterChange(portfolio, sharesPacket, resultValuesMap, true);
            portfolioRepository.save(portfolio);
        }
        portfolioRepository.save(portfolio);
        return isUpdated;
    }

}
