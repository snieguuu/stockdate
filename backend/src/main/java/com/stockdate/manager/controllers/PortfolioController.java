package com.stockdate.manager.controllers;

import com.stockdate.manager.dao.PortfolioRepository;
import com.stockdate.manager.dto.SharesPacketInfoUpdateDto;
import com.stockdate.manager.model.Portfolio;
import com.stockdate.manager.model.SharesPacket;
import com.stockdate.manager.model.SharesPacketUpdateType;
import com.stockdate.manager.services.PortfolioService;
import com.stockdate.manager.services.SharesPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * {@link Portfolio} REST controller
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
     * Method getting {@link SharesPacket} with particular ticker for particular user. If shares packet with
     * particular ticker doesn't exist, new shares packet is created for this user and is's returned with HttpStatus.PARTIAL_CONTENT.
     *
     * @param username name of the portfolio owner
     * @param ticker   ticker of {@link SharesPacket}
     * @return {@link ResponseEntity} with {@link SharesPacket} and appropriate {@link HttpStatus} code
     */
    @GetMapping("/users/{username}/portfolio/{ticker}")
    public ResponseEntity<SharesPacket> getSharesPacket(@PathVariable String username, @PathVariable String ticker) {
        Portfolio portfolio = portfolioService.getPortfolioCreateIfDoesNotExist(username);
        SharesPacket sharesPacket = portfolioService.getSharesPacket(portfolio, ticker);
        if (sharesPacket.getTicker().isEmpty()) {
            return new ResponseEntity<>(sharesPacket, HttpStatus.PARTIAL_CONTENT);
        }
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
    public ResponseEntity<Void> buyNewSharesPacket(
            @PathVariable String username, @RequestBody SharesPacket sharesPacket) {
        Portfolio portfolio = portfolioService.getPortfolioCreateIfDoesNotExist(username);
        portfolioService.addSharesPacket(portfolio, sharesPacket);
        portfolioService.recalculatePortfolio(portfolio);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{ticker}").buildAndExpand(sharesPacket.getTicker())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

//    @DeleteMapping("/users/{username}/portfolio/{ticker}")
//    public ResponseEntity<Void> deleteSharesPacket(@PathVariable String username, @PathVariable String ticker) {
//        Portfolio portfolio = portfolioService.getPortfolioCreateIfDoesNotExist(username);
//        if (portfolio.deleteSharesPacket(ticker)) {
//            portfolioService.recalculatePortfolio();
//            portfolioRepository.save(portfolio);
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.notFound().build();
//    }

    /**
     * Put mapping method updates {@link SharesPacket} in the {@link Portfolio}.
     *
     * @param username      portfolio username
     * @param ticker        ticker of shares packet
     * @param infoUpdateDto DTO with info sent from frontend to update {@link SharesPacket}
     * @return true if shares packet was updated, false otherwise
     */
    @PutMapping("/users/{username}/portfolio/{ticker}")
    public boolean updateSharesPacket(
            @PathVariable String username, @PathVariable String ticker, @RequestBody SharesPacketInfoUpdateDto infoUpdateDto) {
        Portfolio portfolio = portfolioService.getPortfolioCreateIfDoesNotExist(username);
        boolean isUpdated = false;

        SharesPacket sharesPacket = portfolioService.getSharesPacket(portfolio, ticker);
        if (!sharesPacket.getTicker().equals(ticker)) {
            return false;
        }
        sharesPacket.addPurchaseDate(infoUpdateDto.getPurchaseDate());
        if (infoUpdateDto.getUpdateType().equals(SharesPacketUpdateType.BUY)) {
            //todo: check the updating result and return appropriate response
            sharesPacketService.updateSharesPacketAfterBuy(sharesPacket, infoUpdateDto.getSharesAmount(), infoUpdateDto.getPurchaseOrSellingPrice());
            portfolioService.updateSharesPacketInPortfolio(portfolio, sharesPacket);
            isUpdated = true;
        } else if (infoUpdateDto.getUpdateType().equals(SharesPacketUpdateType.SELL)) {
            //todo: check the updating result and return appropriate response
            sharesPacketService.updateSharesPacketAfterSell(sharesPacket, infoUpdateDto.getSharesAmount());
            portfolioService.updateSharesPacketInPortfolio(portfolio, sharesPacket);
            isUpdated = true;
        }
        if (isUpdated) {
            portfolioService.recalculatePortfolio(portfolio);
            return true;
        }
        return false;
    }

}
