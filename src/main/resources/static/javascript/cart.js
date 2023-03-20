
document.addEventListener("DOMContentLoaded", () => {
    // do fetch and call add cartItem()
    // fetch("/frontPageBooks").
    // then((payload) => payload.json()).
    // then((json) => json.forEach(element => {
    //     addBookButton(element);        
    // }));

    addCartItem(1, 3);
    addCartItem(2, 5);

    if(sessionStorage.getItem("loggedIn") === 'true'){
        const btns = document.getElementsByClassName('login-reg-btn');
        Array.from(btns).map((btn) => btn.setAttribute("hidden", 'true'));

        const log_out_btn = document.getElementById("log-out-btn");
        log_out_btn.toggleAttribute("hidden");
        const log_out_text = document.getElementById("log-out-text");
        log_out_text.innerHTML = 'Logout: ' + sessionStorage.getItem("username");
    }
})

function addCartItem(bookID, quantity){
    fetch("/bookByID?id=" + bookID).
    then((payload) => payload.json()).
    then((json) => {
        console.log(json.title + " * " + quantity);

        const p = document.createElement('p');
        
        p.innerHTML = json.title + " * " + quantity;

        const cart_items = document.getElementById("cart-items");
        cart_items.appendChild(p);
    });
}