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

function addBookButton(element){
    const button = document.createElement('button');
    button.classList.add("book-button");
    button.setAttribute("onclick", `goToBook(${element.id})`);

    const img = document.createElement('img');
    img.classList.add("book-button-img");
    img.setAttribute("src", `images/${element.title.toLowerCase().replace(" ", "_")}.jpg`);
    img.setAttribute("alt", "Book picture not found");

    button.appendChild(img);

    const homepage_books = document.getElementById("homepage-books");
    homepage_books.appendChild(button);
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
    getUserId(username);
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

function getUserId(username){
    fetch("/getUserByUsername?username=" + username).
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