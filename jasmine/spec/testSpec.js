describe('frontEndTests', function() {
   beforeEach(function() {
   });

  it('Test Passwords', function() {
    expect(validPassword("testPassword678!")).toBeTrue();
    expect(validPassword("")).toBeFalse();
  });

  it('Test Usernames', function() {
    expect(validUsername("testUser&098")).toBeTrue();
    expect(validUsername("")).toBeFalse();
  });

  it('Test getting Usernames', function() {
    expect(getUsername()).toEqual("testUsername");
  });

  it('Test getting Password', function() {
    expect(getPassword()).toEqual("testPassword");
  });

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
