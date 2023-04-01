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
    fetch("/getOrders?userid=" + sessionStorage.getItem("userId"), {
        method: "POST",
        headers: {
            'Content-Type': "application/json",
            'Accept': "application/json",
        },
        body: JSON.stringify({
            username: sessionStorage.getItem("username"),
            password: sessionStorage.getItem("password"),
        }),
    })
        .then((payload) => payload.json())
        .then((json) => {
            const table = document.createElement('table');
            table.setAttribute("id", "orders");
            const header = table.createTHead();
            const body = table.createTBody();
            const row = header.insertRow(0);
            const OrderNum = row.insertCell(0);
            const Date = row.insertCell(1);
            const priceHeader = row.insertCell(2)
            OrderNum.innerHTML = '<b>OrderNumber</b>';
            Date.innerHTML = '<b>Total</b>';
            priceHeader.innerHTML = '<b>Date</b>';

            json.forEach((bookArray) => {
                const orderId = bookArray[0];
                const date = bookArray[1];
                const price = bookArray[2];
                const row = body.insertRow(-1);
                const orderIdCell = row.insertCell(0);
                const orderTotalCell = row.insertCell(1);
                const orderDateCell = row.insertCell(2);
                orderIdCell.innerHTML = orderId;
                orderTotalCell.innerHTML = "$" + price;
                orderDateCell.innerHTML = date;
            });

            const cartItemsDiv = document.getElementById("order-items");
            cartItemsDiv.innerHTML = ""; // clear the div contents
            cartItemsDiv.appendChild(table); // add the table to the div
            $('#orders').DataTable();
        });
}