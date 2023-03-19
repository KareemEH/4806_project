
document.addEventListener("DOMContentLoaded", () => {
    fetch("/frontPageBooks").
    then((payload) => payload.json()).
    then((json) => json.forEach(element => {
        addBookButton(element);        
    }));

    if(sessionStorage.getItem("loggedIn") === 'true'){
        const btns = document.getElementsByClassName('login-reg-btn');
        Array.from(btns).map((btn) => btn.setAttribute("hidden", 'true'));

        const log_out_btn = document.getElementById("log-out-btn");
        log_out_btn.toggleAttribute("hidden");
        const log_out_text = document.getElementById("log-out-text");
        log_out_text.innerHTML = 'Logout: ' + sessionStorage.getItem("username");
    }
})
