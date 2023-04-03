
let book_id;


document.addEventListener("DOMContentLoaded", () => {
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
    else{
        const buy_btn = document.getElementById("buy-btn");
        buy_btn.toggleAttribute("hidden");
    }
    
    book_id = document.getElementById("book-id");
    const book_title = document.getElementById("book-title");
    const book_isbn = document.getElementById("book-isbn");
    const book_description = document.getElementById("book-description");
    const book_author = document.getElementById("book-author");
    const book_publisher = document.getElementById("book-publisher");
    const book_price = document.getElementById("book-price");
    const book_stock = document.getElementById("book-stock");
    const book_img = document.getElementById("book-picture");
    const buy_btn = document.getElementById("buy-btn");
    const restock_btn = document.getElementById("restock-btn");
    if(sessionStorage.username === "Admin"){
        restock_btn.toggleAttribute("hidden"); // show it if admin
    }

    console.log(book_id.textContent);

    fetch("/bookByID?id=" + book_id.textContent).
    then((payload) => payload.json()).
    then((json) => {
        book_img.setAttribute("src", "/getBookCover?filename=" + json.coverFilename);
        book_title.innerHTML = "<strong>Title:</strong> " + json.title;
        book_isbn.innerHTML = "<strong>ISBN:</strong> " + json.isbn;
        book_description.innerHTML = "<strong>Description:</strong> " + json.description;
        book_author.innerHTML = "<strong>Author:</strong> " + json.author;
        book_publisher.innerHTML = "<strong>Publisher:</strong> " + json.publisher;
        book_price.innerHTML = "<strong>Price:</strong> $" + json.price;
        if(json.stock == 0){
            book_stock.innerHTML = "<strong style='color: red;'>OUT OF STOCK</strong>";
            buy_btn.disabled = true;
        } else {
            book_stock.innerHTML = "<strong>Stock: </strong>" + json.stock;
            buy_btn.disabled = false;
        }
        sessionStorage.setItem("stock",json.stock);
    });
});

async function getBookQuantityInCartAsync(user_id, book_id){
    return fetch("/getBookQuantityInCart?userid=" + user_id + "&bookid=" + book_id, {
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

function getBookQuantityInCart() {
    let user_id = sessionStorage.getItem("userId");
    let book_id = document.getElementById("book-id").textContent;

    return getBookQuantityInCartAsync(user_id, book_id)
        .then((resp) => {
            return resp.json();
        })
        .then((obj) => {
            return obj.quantity;
        });
}

async function addToCart(user_id, book_id, quantity){
    return fetch("/addToCart?userid=" + user_id + "&bookId=" + book_id + "&quantity=" + quantity, {
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

function addBookToCart() {
    let user_id = sessionStorage.getItem("userId");
    let book_id = document.getElementById("book-id").textContent;
    let stock = parseInt(sessionStorage.getItem("stock"));
    let userQuantity = parseInt(prompt("How many would you like?", 0));
    getBookQuantityInCart()
        .then((quantity) => {
            console.log(userQuantity+" > "+stock+" - "+quantity);
            if(userQuantity > (stock - quantity)){
                alert("Not enough books in stock! Try again.");
            } else if (userQuantity <= 0){
                alert("Invalid input, try again!");
            } else {
                addToCart(user_id, book_id, userQuantity)
                    .then((resp) => {
                        return resp.json();
                    })
                    .then((obj) => {
                        if (obj.success) {
                            alert(userQuantity + " books were added successfully!");
                        } else {
                            console.log("Could not add book to cart (server side)");
                            alert("Could not add book to cart (server side)");
                        }
                    });
            }
        });
}

async function restock(){
    let restockQuantity = parseInt(prompt("How much should current stock increase by?", 0));

    return fetch("/restock?bookId=" + book_id.textContent + "&quantity=" + restockQuantity, {
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