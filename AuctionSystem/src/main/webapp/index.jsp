<!DOCTYPE html>
<html>
<head>
    <title>Online Auction</title>
    <script>
        function placeBid() {
            // Trigger form submission for placing a bid
            document.getElementById('auctionForm').action.value = 'placeBid';
            document.getElementById('auctionForm').submit();
        }

        function endAuction() {
            // Trigger form submission for ending the auction
            document.getElementById('auctionForm').action.value = 'endAuction';
            document.getElementById('auctionForm').submit();
        }

        // Display alerts on page load
        window.onload = function() {
            const alertMessage = '<%= request.getAttribute("alertMessage") %>';
            if (alertMessage) {
                alert(alertMessage);
            }

            const winnerMessage = '<%= request.getAttribute("winnerMessage") %>';
            if (winnerMessage) {
                document.getElementById('winner').innerText = winnerMessage;
            }
        };
    </script>
</head>
<body>
    <h1>Online Auction</h1>
    <form id="auctionForm" action="AuctionServlet" method="POST">
        <input type="hidden" name="action" value="">
        <!-- Create Auction Section -->
        <h2>Create Auction</h2>
        Item Name: <input type="text" name="itemName" required><br>
        Item Description: <input type="text" name="itemDesc" required><br>
        Minimum Bid: <input type="number" name="minBid" step="0.01" required><br>

        <!-- Place Bid Section -->
        <h2>Place Bid</h2>
        Bid Amount: <input type="number" name="bidAmount" step="0.01" required><br>
        Bidder Name: <input type="text" name="bidderName" required><br>
        <button type="button" onclick="placeBid()">Place Bid</button><br>

        <!-- End Auction Section -->
        <h2>End Auction</h2>
        <button type="button" onclick="endAuction()">End Auction</button>

        <!-- Auction Winner Section -->
        <h2>Auction Winner</h2>
        <p id="winner">
            <% String winnerMessage = (String) request.getAttribute("winnerMessage");
            if (winnerMessage != null) {
                out.print(winnerMessage);
            } %>
   
