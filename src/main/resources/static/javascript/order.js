document.addEventListener("DOMContentLoaded", () => {
    getUserOrders();

    if(sessionStorage.getItem("loggedIn") === 'true'){
        const btns = document.getElementsByClassName('login-reg-btn');
        Array.from(btns).map((btn) => btn.setAttribute("hidden", 'true'));

        const log_out_btn = document.getElementById("log-out-btn");
        log_out_btn.toggleAttribute("hidden");
        const log_out_text = document.getElementById("log-out-text");
        log_out_text.innerHTML = 'Logout: ' + sessionStorage.getItem("username");

        const order_btn = document.getElementById("order-btn");
        order_btn.toggleAttribute("hidden");
        const order_text = document.getElementById("order-text");
        order_text.innerHTML = 'Orders';
    }
})

function getUserOrders(){
    fetch("/getOrders?userid=" + sessionStorage.getItem("userId"))
        .then((payload) => payload.json())
        .then((json) => {
            const table = document.createElement('table');
            const header = table.createTHead();
            const row = header.insertRow(0);
            const OrderNum = row.insertCell(0);
            const Date = row.insertCell(1);
            const priceHeader = row.insertCell(2)
            OrderNum.innerHTML = '<b>OrderNumber</b>';
            Date.innerHTML = '<b>Date</b>';
            priceHeader.innerHTML = '<b>Total</b>';

            json.forEach((bookArray) => {
                const orderid = bookArray[0];
                const date = bookArray[1];
                const price = bookArray[2];
                const row = table.insertRow(-1);
                const titleCell = row.insertCell(0);
                const authorCell = row.insertCell(1);
                const priceCell = row.insertCell(2);
                titleCell.innerHTML = orderid;
                authorCell.innerHTML = date;
                priceCell.innerHTML = price;
            });

            const cartItemsDiv = document.getElementById("order-items");
            cartItemsDiv.innerHTML = ""; // clear the div contents
            cartItemsDiv.appendChild(table); // add the table to the div
        });
}