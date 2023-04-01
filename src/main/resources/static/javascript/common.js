document.addEventListener("DOMContentLoaded", () => {
    if(sessionStorage.getItem("loggedIn") === 'true'){
        const btns = document.getElementsByClassName('login-reg-btn');
        Array.from(btns).map((btn) => btn.setAttribute("hidden", 'true'));

        const log_out_btn = document.getElementById("log-out-btn");
        log_out_btn.removeAttribute("hidden");

        const cart_btn = document.getElementById("cart-btn");
        cart_btn.removeAttribute("hidden");

        const order_btn = document.getElementById("order-btn");
        order_btn.removeAttribute("hidden");

        const order_text = document.getElementById("order-text");
        order_text.innerHTML = 'Orders';

        const log_out_text = document.getElementById("log-out-text");
        log_out_text.innerHTML = 'Logout: ' + sessionStorage.getItem("username");
    }
})

function goHome(){
    document.location.href = "/"; 
}

function goToLogin(bookName){
    document.location.href = `/login`; 
}

function goToCart(){
    document.location.href = `/cart`; 
}

function goToOrders(){
    document.location.href = `/order`;
}
function goToRegister(bookName){
    document.location.href = `/register`; 
}

function goToBook(id){
    document.location.href = `/book?id=${id}`; 
}

function generateBookTable(books, tableId, searchable){
    const table = document.createElement('table');
    table.setAttribute("id", tableId);
    const header = table.createTHead();
    const body = table.createTBody();
    const row = header.insertRow(0);

    // Creating book table headers
    const bookImg = row.insertCell(0);
    const isbn = row.insertCell(1);
    const bookTitle = row.insertCell(2);
    const bookAuthor = row.insertCell(3);
    const description = row.insertCell(4);
    const genre = row.insertCell(5);
    const price = row.insertCell(6);

    bookImg.innerHTML = '<b>Book</b>'
    isbn.innerHTML = '<b>ISBN</b>';
    bookTitle.innerHTML = '<b>Title</b>';
    bookAuthor.innerHTML = '<b>Author</b>';
    description.innerHTML = '<b>Description</b>';
    genre.innerHTML = '<b>Genre</b>';
    price.innerHTML = '<b>Price</b>';

    books.forEach((element) => {  
        const button = document.createElement('button');
        button.classList.add("book-button");
        button.setAttribute("onclick", `goToBook(${element.id})`);
           
        const img = document.createElement('img');
        img.classList.add("book-button-img");
        img.setAttribute("src", `images/${element.title.toLowerCase().replaceAll(" ", "_")}.jpg`);
        img.setAttribute("alt", "Book picture not found");
        img.setAttribute("style","width:50px;height:80px;");
       
        const row = body.insertRow(-1);
        const bookImgCell = row.insertCell(0);
        const isbnCell = row.insertCell(1);
        const bookTitleCell = row.insertCell(2);
        const bookAuthorCell = row.insertCell(3);
        const descriptionCell = row.insertCell(4);
        const genreCell = row.insertCell(5);
        const priceCell = row.insertCell(6);

        button.appendChild(img);

        bookImgCell.appendChild(button);
        isbnCell.innerHTML = element.isbn;
        bookTitleCell.innerHTML = element.title;
        bookAuthorCell.innerHTML = element.author;
        descriptionCell.innerHTML = element.description;
        genreCell.innerHTML = element.genre;
        priceCell.innerHTML = '$' + element.price;
    });
    if (searchable){
        const homepage_books = document.getElementById("homepage-books");
        homepage_books.appendChild(table);

        $("#"+tableId).DataTable({
            "columnDefs": [{
                "targets": 0,
                "orderable": false // Disable sorting for book image column
            }],
            language: {
                sInfo: "Showing _START_ to _END_ of _TOTAL_ books"
            }
        });
    }else{
        const recommend_books = document.getElementById("recommend-books");
        let p = document.createElement("h3");
        p.innerHTML = "Similar users have purchased these books, take a look!"
        recommend_books.appendChild(p);
        recommend_books.appendChild(table);

        $("#"+tableId).DataTable({
            "columnDefs": [{
                "targets": 0,
                "orderable": false // Disable sorting for book image column
            }],
            searching : false,
            paging: false,
            language: {
                sInfo: "Recommending _TOTAL_ books"
            }
        });
    }
}

function getUsername(){
    return document.getElementById("username-field").value;
}

function getPassword(){
    return document.getElementById("password-field").value;
}

function validUsername(username){
    if(username === ""){
        return false;
    }
    
    return true;
}

function validPassword(password){
    if(password === ""){
        return false;
    }

    return true;
}

async function login(username, password){
    return fetch("/verify_login", {
        method: "POST",
        headers: {
            'Content-Type': "application/json",
            'Accept': "application/json",
        },
        body: JSON.stringify({
            username,
            password,
        }),
    })
}

function attemptLogin(){
    let username = getUsername();
    let password = getPassword();
    getAndRememberUserId(username, password);
    // sessionStorage.getItem("userId");

    if(!validUsername || !validPassword()){
        // TODO more graceful failiure
        console.log("login failure (client side)");
        alert("login failure (client side)");

        return;
    }

    login(username, password)
    .then((resp) => {
        return resp.json();
    })
    .then((obj) => {
        if(obj.success){
            setLoggedIn(username, password)
            alert("Successful login!");
            document.location.href = "/";
        }
        else{
            console.log("login failure (server side)");
            alert("Login failure (server side)");
        }
    });
}

function setLoggedIn(username, password){
    sessionStorage.setItem("loggedIn", "true");
    sessionStorage.setItem("username", username);
    sessionStorage.setItem("password", password);
}

async function registerNewUser(username, password){
    return fetch("/new_user", {
        method: "POST",
        headers: {
            'Content-Type': "application/json",
            'Accept': "application/json",
        },
        body: JSON.stringify({
            username,
            password,
        }),
    })
}

function getAndRememberUserId(username, password){
    fetch("/getUserByUsername", {
        method: "POST",
        headers: {
            'Content-Type': "application/json",
            'Accept': "application/json",
        },
        body: JSON.stringify({
            username,
            password,
        }),
    }).
    then((payload) => payload.json()).
    then((json) => {
        sessionStorage.setItem("userId", json.id);
    });
}

function attemptRegistration(){
    let username = getUsername();
    let password = getPassword();

    if(!validUsername || !validPassword()){
        // TODO more graceful failiure
        console.log("registration error (client side)");
        alert("registration error (client side)");

        return;
    }

    registerNewUser(username, password)
    .then((resp) => {
        return resp.json();
    })
    .then((obj) => {
        if(obj.success){
            alert("Successful registration!");
            document.location.href = "/";
        }
        else{
            console.log("registration error (server side)");
            alert("registration error (server side)");
        }
    });
}


function logout(){
    setLoggedOut();
    document.location.href = "/";
}

function setLoggedOut(){
    sessionStorage.setItem("loggedIn", "false");
    sessionStorage.setItem("username", '');
    sessionStorage.setItem("password", '');
    sessionStorage.setItem("userId", '');
}

function getRecommendedBooks(){
    return fetch("/recommendations?userid=" + sessionStorage.getItem("userId"), {
        method: "POST",
        headers: {
            'Content-Type': "application/json",
            'Accept': "application/json",
        },
        body: JSON.stringify({
        }),
    })
        .then((payload) => payload.json())
        .then((json) => {
            return json.map(book => {
                return {
                    id: book.id,
                    title: book.title,
                    author: book.author,
                    description: book.description,
                    price: book.price,
                    genre: book.genre,
                    publisher: book.publisher,
                    isbn: book.isbn,
                };
            });
        });
}