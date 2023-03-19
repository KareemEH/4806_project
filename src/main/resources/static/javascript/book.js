
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


function addBookToCart(){
    let quantity = prompt("How many would you like", 1);
    console.log("buying " + quantity + " books!");

    // TODO call backend to add to cart
    // use book_id attribute

    let success = true;

    if(success){
        alert("added successfully");
        document.location.href = '/cart';
    }
    else{
        alert("failure to add to cart");
    }
}