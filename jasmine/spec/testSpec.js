describe('frontEndTests', function() {
   beforeEach(function() {
   });

  it('Test password validity', function() {
    expect(validPassword("testPassword678!")).toBeTrue();
    expect(validPassword("")).toBeFalse();
  });

  it('Test username validity', function() {
    expect(validUsername("testUser&098")).toBeTrue();
    expect(validUsername("")).toBeFalse();
  });
});

describe("Test fetch calls on logging in and registering", () => {
  it('Test that fetch is called when logging in', function() {
    fetch = jasmine.createSpy();
    login("testUser", "testPass");
    expect(fetch).toHaveBeenCalledWith("/verify_login", {
      method: "POST",
      headers: {
          'Content-Type': "application/json",
          'Accept': "application/json",
      },
      body: JSON.stringify({username: "testUser", password: "testPass"})
    });

    fetch = jasmine.createSpy();
    login("", "");
    expect(fetch).toHaveBeenCalledWith("/verify_login", {
      method: "POST",
      headers: {
          'Content-Type': "application/json",
          'Accept': "application/json",
      },
      body: JSON.stringify({username: "", password: ""})
    });
  });

  it('Test that fetch is called when registering', function() {
    fetch = jasmine.createSpy();
    registerNewUser("testUser", "testPass");
    expect(fetch).toHaveBeenCalledWith("/new_user", {
      method: "POST",
      headers: {
          'Content-Type': "application/json",
          'Accept': "application/json",
      },
      body: JSON.stringify({username: "testUser", password: "testPass"})
    });

    fetch = jasmine.createSpy();
    registerNewUser("", "");
    expect(fetch).toHaveBeenCalledWith("/new_user", {
      method: "POST",
      headers: {
          'Content-Type': "application/json",
          'Accept': "application/json",
      },
      body: JSON.stringify({username: "", password: ""})
    });
  });
});

describe('Test session storage on log in/out', function() {
  it('Should end up logged out', function() {
    setLoggedOut();
    expect(sessionStorage.loggedIn).toEqual("false");
  });

  it('Should end up logged in', function() {
    setLoggedIn("username", "password");
    expect(sessionStorage.loggedIn).toEqual("true");
  });

  it('Should end up logged out again', function() {
    setLoggedOut();
    expect(sessionStorage.loggedIn).toEqual("false");
  });
});

describe('Test input getters', function() {
  beforeEach(function () {
    $('#fixture').remove();
    $('body').append('<div id="fixture">...</div>');
    $('#fixture').append('<input hidden id="username-field" type="text" value="testUsername">');
    $('#fixture').append('<input hidden id="password-field" type="text" value="testPassword">');
  });


  it("Test that we can extract the password from the form", () => {
    expect(getPassword()).toEqual('testPassword');
  });

  it("Test that we can extract the username from the form", () => {
    expect(getUsername()).toEqual('testUsername');
  });


  afterAll(function () {
    $('#fixture').remove();
  });
});