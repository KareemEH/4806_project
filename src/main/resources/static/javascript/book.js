
let book_id;


document.addEventListener("DOMContentLoaded", () => {
    if(sessionStorage.getItem("loggedIn") === 'true'){
        const btns = document.getElementsByClassName('login-reg-btn');
        Array.from(btns).map((btn) => btn.setAttribute("hidden", 'true'));

        const log_out_btn = document.getElementById("log-out-btn");
        log_out_btn.toggleAttribute("hidden");
        const log_out_text = document.getElementById("log-out-text");
        log_out_text.innerHTML = 'Logout: ' + sessionStorage.getItem("username");
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
    const book_img = document.getElementById("book-picture");

    console.log(book_id.textContent);

    fetch("/bookByID?id=" + book_id.textContent).
    then((payload) => payload.json()).
    then((json) => {
        book_img.setAttribute("src", "images/" + json.title.toLowerCase().replace(" ", "_") + ".jpg");
        book_title.textContent = "Title: " + json.title;
        book_isbn.textContent = "ISBN: " + json.isbn;
        book_description.textContent = "Description: " + json.description;
        book_author.textContent = "Author: " + json.author;
        book_publisher.textContent = "Publisher: " + json.publisher;
        book_price.textContent = "Price: $" + json.price;
    });
});

async function addToCart(user_id, book_id, quantity){
    return fetch("/addToCart?userid=" + user_id + "&bookId=" + book_id + "&quantity=" + quantity, {
        method: "POST",
        headers: {
            'Content-Type': "application/json",
            'Accept': "application/json",
        },
        body: JSON.stringify({
            user_id,
            book_id,
            quantity,
        }),
    })
}

function addBookToCart() {
    let user_id = sessionStorage.getItem("userId");
    let book_id = document.getElementById("book-id").textContent;
    let quantity = prompt("How many would you like", 1);

    addToCart(user_id, book_id, quantity)
        .then((resp) => {
            return resp.json();
        })
        .then((obj) => {
            if (obj.success) {
                alert(quantity + " books were added successfully!");
            } else {
                console.log("Could not add book to cart (server side)");
                alert("Could not add book to cart (server side)");
            }
        });
}