
function getUsername(){
    return document.getElementById("username-field").value;
}

function getPassword(){
    return document.getElementById("password-field").value;
}

// TODO implent username validity check
function validUsername(username){
    return true;
}

// TODO implent password validity check
function validPassword(password){
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
    username = getUsername();
    password = getPassword();

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
            alert("Successful login!");
            document.location.href = "/";
        }
        else{
            console.log("login failure (server side)");
            alert("Login failure (server side)");
        }
    });
}