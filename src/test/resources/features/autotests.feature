Feature: Authentication and Players automation

  Scenario: Full autotest
    When User login with email "user_email" and "user_password"
    Then Status code is 200 and response contains access token
    When Create 1 players
    Then Status code is 200 and all created players match with schema
    When Create 12 players
    Then Status code is 201 and all created players match with schema
    When Get random created player by email
    Then Status code is 200 and player match with schema
    When Get all players and sort by name
    When Delete all players
    Then All players are deleted

  Scenario: Login
    When User login with email "user_email" and "user_password"
    Then Status code is 200 and response contains access token

  Scenario: Create players
    When User login with email "user_email" and "user_password"
    When Create 12 players
    Then Status code is 201 and all created players match with schema

  Scenario: Get player by email
    When User login with email "user_email" and "user_password"
    When Create 1 players
    When Get random created player by email
    Then Status code is 200 and player match with schema

  Scenario: All players
    When User login with email "user_email" and "user_password"
    When Create 3 players
    When Get all players and sort by name
    When Delete all players
    Then All players are deleted