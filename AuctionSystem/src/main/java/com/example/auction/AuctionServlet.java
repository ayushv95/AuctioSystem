package com.example.auction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/AuctionServlet")
public class AuctionServlet extends HttpServlet {
    private boolean auctionEnded = false;
    private double minBid = 0.0;
    private double highestBid = 0.0;
    private String highestBidder = null;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("placeBid".equals(action)) {
            placeBid(request, response);
        } else if ("endAuction".equals(action)) {
            endAuction(request, response);
        }
    }

    private void placeBid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (auctionEnded) {
            request.setAttribute("alertMessage", "Auction has ended. No more bids can be placed.");
        } else {
            // Retrieve input values
            double bidAmount = Double.parseDouble(request.getParameter("bidAmount"));
            String bidderName = request.getParameter("bidderName");
            if (bidAmount < minBid) {
                request.setAttribute("alertMessage", "Bid must be at least $" + String.format("%.2f", minBid));
            } else if (bidAmount > highestBid) {
                // Update highest bid and bidder
                highestBid = bidAmount;
                highestBidder = bidderName;
                request.setAttribute("alertMessage", "New highest bid: $" + String.format("%.2f", highestBid) + " by " + highestBidder);
            } else {
                request.setAttribute("alertMessage", "Bid is not high enough. Current highest bid is $" + String.format("%.2f", highestBid) + " by " + highestBidder);
            }
        }
        // Forward to index.jsp
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private void endAuction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        auctionEnded = true;

        // Determine the winner message
        String winnerMessage;
        if (highestBidder == null) {
            winnerMessage = "No winning bid";
        } else {
            winnerMessage = "Winner: " + highestBidder + " with a bid of $" + String.format("%.2f", highestBid);
        }

        // Set the winner message attribute
        request.setAttribute("winnerMessage", winnerMessage);
        
        // Forward to index.jsp
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
