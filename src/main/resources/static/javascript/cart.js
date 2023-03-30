
document.addEventListener("DOMContentLoaded", () => {
    getUserCart();

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

function getUserCart(){
    fetch("/getUserCart?userid=" + sessionStorage.getItem("userId"), {
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
            table.setAttribute("id", "cart");
            const header = table.createTHead();
            const body = table.createTBody();
            const footer = table.createTFoot();
            const headerRow = header.insertRow(0);
            const titleHeader = headerRow.insertCell(0);
            const authorHeader = headerRow.insertCell(1);
            const priceHeader = headerRow.insertCell(2);
            const quantityHeader = headerRow.insertCell(3);
            headerRow.insertCell(4);
            titleHeader.innerHTML = '<b>Title</b>';
            authorHeader.innerHTML = '<b>Author</b>';
            priceHeader.innerHTML = '<b>Price</b>';
            quantityHeader.innerHTML = '<b>Quantity</b>';

            let totalPrice = 0; // initialize total price to zero
            json.forEach((bookArray) => {
                const title = bookArray[0];
                const author = bookArray[1];
                const price = bookArray[2];
                const quantity = bookArray[3];
                const row = body.insertRow(-1);
                const titleCell = row.insertCell(0);
                const authorCell = row.insertCell(1);
                const priceCell = row.insertCell(2);
                const quantityCell = row.insertCell(3);
                const actionCell = row.insertCell(4);
                titleCell.innerHTML = title;
                authorCell.innerHTML = author;
                priceCell.innerHTML = price;
                quantityCell.innerHTML = quantity;
                totalPrice += price * quantity; // add book price to total
                const removeButton = document.createElement('button');
                removeButton.innerHTML = 'Remove';
                removeButton.addEventListener('click', () => {
                    // Handle remove button click event
                    removeFromCart(bookArray[4], bookArray[0]);
                });
                actionCell.appendChild(removeButton);
            });

            // Add row for total price
            const row1 = footer.insertRow(-1);
            const totalHeader = row1.insertCell(0);
            const totalCell = row1.insertCell(1);
            // Adding empty cells to keep the table consistent with column numbers
            row1.insertCell(2).innerHTML;
            row1.insertCell(3).innerHTML;
            row1.insertCell(4).innerHTML;
            totalHeader.innerHTML = '<b>Total Price:</b>';
            totalCell.innerHTML = "$" + totalPrice.toFixed(2); // display total with 2 decimal places

            const cartItemsDiv = document.getElementById("cart-items");
            cartItemsDiv.innerHTML = ""; // clear the div contents
            cartItemsDiv.appendChild(table); // add the table to the div
            $('#cart').DataTable();
        });
}
async function deleteFromCart(user_id, book_id){
    return fetch("/deleteFromCart?userid=" + user_id + " &bookId=" + book_id, {
        method: "DELETE",
        headers: {
            'Content-Type': "application/json",
            'Accept': "application/json",
        },
        body: JSON.stringify({
            username: sessionStorage.getItem("username"),
            password: sessionStorage.getItem("password"),
        }),
    })
    .catch((error) => {
        console.log("Error deleting from cart:", error);
        throw error;
    });
}

function removeFromCart(book_id, book_title) {
    let user_id = sessionStorage.getItem("userId")

    deleteFromCart(user_id, book_id)
        .then((resp) => {
            return resp.json();
        })
        .then((obj) => {
            if (obj.success) {
                alert( "Removed " + book_title + " form cart!");
                document.location.href = "/cart";
            } else {
                console.log("Could not remove book from cart (server side)");
                alert("Could not remove book from cart (server side)");
            }
        });
}

async function checkout(user_id){
    console.log(user_id);
    return fetch("/checkoutCart?userid=" + user_id, {
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
}

function checkoutCart() {
    let user_id = sessionStorage.getItem("userId");

    checkout(user_id)
        .then((resp) => {
            return resp.json();
        })
        .then((obj) => {
            if (obj.success) {
                alert("Order has been placed succesfully!");
                document.location.href = `/cart`;
            } else {
                console.log("Could not place your order (server side)");
                alert("Could not place your order (server side)");
            }
        });
}