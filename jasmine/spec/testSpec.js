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
});
