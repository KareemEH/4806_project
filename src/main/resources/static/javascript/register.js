
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

function attemptRegistration(){
    username = getUsername();
    password = getPassword();

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
